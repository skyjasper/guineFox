package com.guinea.web.controller.sys;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Maps;
import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.entity.SysAccount;
import com.guinea.datadb.mysql.entity.SysAccountDetail;
import com.guinea.dic.SexEnum;
import com.guinea.profession.service.sys.SysAccountDetailService;
import com.guinea.profession.service.sys.SysAccountService;
import com.guinea.util.Constents;
import com.guinea.util.EndecryptUtils;
import com.guinea.web.BaseFormController;
import com.guinea.web.MsgResult;
import com.guinea.web.PageMsg;
import com.guinea.web.model.User;
import com.guinea.web.model.tabui.InfoNode;

/**
 * Created by shiky on 2015/12/8.
 */
@Controller
public class SysAccountController extends BaseFormController {

    private static Logger logger = LoggerFactory.getLogger(SysAccountController.class);

    @Autowired
    private SysAccountService sysAccountService;

    @Autowired
    private SysAccountDetailService sysAccountDetailService;

    @RequestMapping(value = "/sys/sysAccountList", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        processShowSelect(model);
        showList(model, logger.hashCode());
        return "sys/sysaccountList";
    }

    @RequestMapping(value = "/sys/sysAccountList", method = {RequestMethod.POST})
    public void findBpage(HttpServletRequest request, HttpServletResponse response) {
        try {
            showDataGrid(request, response, objectMapper.writeValueAsString(
                    sysAccountService.findByPageDetail(processParam(request.getParameterMap(), logger.hashCode()))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/sys/admin/sysAccountManagerList", method = RequestMethod.GET)
    public String accountManager(ModelMap model) {
        processShow(model);
        showList(model, logger.hashCode() + 189);
        return "sys/admin/sysAccountManagerList";
    }

    @RequestMapping(value = "/sys/admin/sysAccountManagerList", method = {RequestMethod.POST})
    public void accountManagerPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            showDataGrid(request, response, objectMapper.writeValueAsString(
                    sysAccountService.findByPage(processParam(request.getParameterMap(), logger.hashCode() + 189))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processShow(ModelMap model) {
        Map<String, String> isDeleteMap = Maps.newHashMap(), isLockedMap = Maps.newHashMap();
        isDeleteMap.put(Constents.gen + "", "正常");
        isDeleteMap.put(Constents.del + "", "删除");
        isLockedMap.put(Constents.gen + "", "正常");
        isLockedMap.put(Constents.del + "", "锁定");
        model.put("isDeleteMap", isDeleteMap);
        model.put("isLockedMap", isLockedMap);
    }

    private void processShowSelect(ModelMap model) {
        Map<String, String> sexMap = Maps.newHashMap();
        for (SexEnum c : SexEnum.values()) {
            sexMap.put(c.getCode() + "", c.getName());
        }
        model.put("sexEnumMap", sexMap);
    }

    /***
     * 个人密码修改
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/sys/editpwd", method = RequestMethod.GET)
    public String updatepwd(HttpServletRequest request, ModelMap model) {
        logger.debug("个人密码修改");
        return "sys/editpwd";
    }

    /**
     * 个人密码修改
     *
     * @param request
     */
    @RequestMapping(value = "/sys/editpwd", method = RequestMethod.POST)
    public void updatepwdprocess(HttpServletRequest request, HttpServletResponse response) {
        String oldPwd = StringUtils.trim(request.getParameter("oldPwd")),
                newPwd = StringUtils.trim(request.getParameter("newPwd"));
        if (StringUtils.isNotBlank(oldPwd) && StringUtils.isNotBlank(newPwd)) {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            if (null != user) {
                SysAccount sysAccount = SysCache.getByUserName(user.getLoginname());
                String oDBpwd = sysAccount.getPwd(), passwordery = EndecryptUtils.encrytm(oldPwd, sysAccount.getSalt());
                if (StringUtils.equals(oDBpwd, passwordery)) {
                    String passworderyNew = EndecryptUtils.encrytm(newPwd, sysAccount.getSalt());
                    sysAccount.setPwd(passworderyNew);
                    sysAccountService.updatePwdById(sysAccount);
                    SysCache.putUserMap(sysAccount);
                    succMesg(request, response, "修改成功,重新登录生效!");
                } else {
                    errorMsg(request, response, "原密码错误!");
                }
            }
        } else {
            errorMsg(request, response, "参数不全!");
        }
    }

    /****
     * 编辑
     *
     * @param id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/sysAccountEdit", method = RequestMethod.GET)
    public String userEdit(Long id, HttpServletRequest request, ModelMap model) {
        if (null != id) {
            processShowSelect(model);
            SysAccount sysAccount = sysAccountService.selectByPrimaryKey(id);
            model.put("sysAccount", sysAccount);
        }
        return "sys/sysAccountEdit";
    }

    /**
     * 重置密码
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sys/sysaccountReplace", method = {RequestMethod.POST})
    public void replace(HttpServletRequest request, HttpServletResponse response, Long id) {
        if (null != id) {
            SysAccount sysAccount = sysAccountService.selectByPrimaryKey(id);
            String passwordery = EndecryptUtils.encrytm(EndecryptUtils.DEFALUTPWD, sysAccount.getSalt());
            sysAccount.setPwd(passwordery);
            sysAccountService.updatePwdById(sysAccount);
            SysCache.putUserMap(sysAccount);
            succMesg(request, response, "重置成功!");
        } else {
            errorMsg(request, response);
        }
    }

    /***
     * 显示个人信息
     *
     * @return
     */
    @RequestMapping(value = "/meInfo_show", method = RequestMethod.GET)
    public String meinfo_show(ModelMap model) {
        showList(model, logger.hashCode() + 1299);
        List<Integer> pageList = Arrays.asList(100);
        try {
            model.put("pageSize", 100);
            model.put("pageList", objectMapper.writeValueAsString(pageList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "sys/meInfo_show";
    }

    @RequestMapping(value = "/sys/meInfo_show", method = {RequestMethod.POST})
    public void meinfo_show_data(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        SysAccount sysAccount = null;
        SysAccountDetail sysAccountDetail = null;
        if (null != user) {
            sysAccount = SysCache.getByUserName(user.getLoginname());
            SysAccountDetail temp = new SysAccountDetail();
            temp.setUid(sysAccount.getId());
            List<SysAccountDetail> sysAccountDetails = sysAccountDetailService.selectByObj(temp);
            if (null != sysAccountDetails && sysAccountDetails.size() > 0) {
                sysAccountDetail = sysAccountDetails.get(0);
            }
        } else {
            sysAccount = new SysAccount();
            sysAccountDetail = new SysAccountDetail();
        }
        InfoNode infoNode1 = new InfoNode("昵称", sysAccount.getKawyiname(),"基本信息");
        InfoNode infoNode2 = new InfoNode("登录名", sysAccount.getLoginname(),"基本信息");
        InfoNode infoNode5 = new InfoNode("性别", SexEnum.getName(sysAccount.getSex()),"基本信息");
        InfoNode infoNode3 = new InfoNode("邮箱", sysAccount.getEmail(),"联系方式");
        InfoNode infoNode4 = new InfoNode("电话", sysAccount.getPhonenumber(),"联系方式");
        InfoNode infoNode6 = new InfoNode("微信", sysAccountDetail.getWeixin(),"联系方式");
        InfoNode infoNode7 = new InfoNode("QQ", sysAccountDetail.getQq(),"联系方式");
        List<InfoNode> infoNodes = Arrays.asList(infoNode1, infoNode2, infoNode3, infoNode4, infoNode5, infoNode6, infoNode7);
        try {
            showDataGrid(request, response, objectMapper.writeValueAsString(findByPage(infoNodes)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private PageMsg<InfoNode> findByPage(List<InfoNode> infoNodes) {
        PageMsg<InfoNode> pageMsg = new PageMsg<>();
        pageMsg.setTotal(Long.valueOf(infoNodes.size()));
        pageMsg.setRows(infoNodes);
        return pageMsg;
    }

    /***
     * 个人信息管理
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/meInfo", method = RequestMethod.GET)
    public String meinfo(HttpServletRequest request, ModelMap model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        SysAccount sysAccount = null;
        SysAccountDetail sysAccountDetail = null;
        if (null != user) {
            sysAccount = SysCache.getByUserName(user.getLoginname());
            SysAccountDetail temp = new SysAccountDetail();
            temp.setUid(sysAccount.getId());
            List<SysAccountDetail> sysAccountDetails = sysAccountDetailService.selectByObj(temp);
            if (null != sysAccountDetails && sysAccountDetails.size() > 0) {
                sysAccountDetail = sysAccountDetails.get(0);
            }
        } else {
            sysAccount = new SysAccount();
            sysAccountDetail = new SysAccountDetail();
        }
        model.put("sysAccount", sysAccount);
        model.put("sysAccountDetail", sysAccountDetail);
        processShowSelect(model);
        return "sys/meInfo";
    }

    @RequestMapping(value = "/sys/meInfo", method = {RequestMethod.POST})
    public void meinfoProcess(HttpServletRequest request, HttpServletResponse response) {
        String kawyiname = StringUtils.trim(request.getParameter("kawyiname")),
                age = StringUtils.trim(request.getParameter("age")), id = StringUtils.trim(request.getParameter("id")),
                mlike = StringUtils.trim(request.getParameter("mlike")),
                skype = StringUtils.trim(request.getParameter("skype")),
                // sex_s = StringUtils.trim(request.getParameter("sex")),
                weixin = StringUtils.trim(request.getParameter("weixin")),
                address = StringUtils.trim(request.getParameter("address")),
                city = StringUtils.trim(request.getParameter("city"));
        if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
            Short agess = 0;
            // if(StringUtils.isNotBlank(sex_s)&&StringUtils.isNumeric(sex_s)){
            // sexst = Short.parseShort(sex_s);
            // }
            if (StringUtils.isNotBlank(age) && StringUtils.isNumeric(age)) {
                agess = Short.valueOf(age);
            }
            SysAccount sysAccount = SysCache.getUserMapByid(Long.valueOf(id));
            sysAccount.setKawyiname(kawyiname);
            // sysAccount.setSex(sexst);
            sysAccountService.updateByPrimaryKey(sysAccount);
            SysCache.putUserMap(sysAccount);
            SysAccountDetail teq = new SysAccountDetail(), sysAccountDetail = null;
            teq.setUid(sysAccount.getId());
            List<SysAccountDetail> sysAccountDetails = sysAccountDetailService.selectByObj(teq);
            if (null != sysAccountDetails && sysAccountDetails.size() > 0) {
                sysAccountDetail = sysAccountDetails.get(0);
                if (null != sysAccountDetail) {
                    sysAccountDetail.setCity(city);
                    sysAccountDetail.setAddress(address);
                    sysAccountDetail.setWeixin(weixin);
                    sysAccountDetail.setSkype(skype);
                    sysAccountDetail.setMlike(mlike);
                    sysAccountDetail.setAge(agess);
                    sysAccountDetailService.updateByPrimaryKey(sysAccountDetail);
                }
            }
            succMesg(request, response);
        } else {
            errorMsg(request, response);
        }
    }

    /**
     * 删除
     *
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sys/sysAccountDelete", method = {RequestMethod.POST})
    public void processDelete(Long id, HttpServletRequest request, HttpServletResponse response) {
        MsgResult msgResult = sysAccountService.deleteByPrimaryKey(id);
        if (msgResult.getKey() == 0) {
            succMesg(request, response, msgResult.getValue());
        } else {
            errorMsg(request, response, msgResult.getValue());
        }
    }

    /**
     * 锁定
     *
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sys/sysAccountLocked", method = {RequestMethod.POST})
    public void processLocked(Long id, String type, HttpServletRequest request, HttpServletResponse response) {
        MsgResult msgResult = sysAccountService.lockedByPrimaryKey(id, type);
        if (msgResult.getKey() == 0) {
            succMesg(request, response, msgResult.getValue());
        } else {
            errorMsg(request, response, msgResult.getValue());
        }
    }

}
