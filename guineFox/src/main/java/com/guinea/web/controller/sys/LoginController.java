package com.guinea.web.controller.sys;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.guinea.cache.SysCache;
import com.guinea.content.SysProcess;
import com.guinea.datadb.mysql.entity.SysAccount;
import com.guinea.dic.SexEnum;
import com.guinea.profession.service.sys.SysAccountService;
import com.guinea.util.CommonToolsUtil;
import com.guinea.util.Constents;
import com.guinea.util.EndecryptUtils;
import com.guinea.util.PingYingUtil;
import com.guinea.util.WebUtil;
import com.guinea.util.verify.ValidateCode;
import com.guinea.web.BaseFormController;
import com.guinea.web.model.User;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

/**
 * Created by shiky on 2015/12/3.
 */
@Controller
public class LoginController extends BaseFormController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SysAccountService sysAccountService;

    /***
     * 登录页
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showSysLogin(HttpServletRequest request, ModelMap model) {
        model.put("path", request.getContextPath());
        return "login";
    }

    /***
     * 登录验证
     *
     * @param request
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void sysLoginSub(HttpServletRequest request, HttpServletResponse response) {
        try {
            String verifycode = StringUtils.trim(request.getParameter("verifycode")),
                    username = StringUtils.trim(request.getParameter("loginName")),
                    password = StringUtils.trim(request.getParameter("password")),
                    verify = (String) WebUtils.getSessionAttribute(request, ValidateCode.RANDOMCODEKEY),
                    valiData = SysCache.validataMap.get(verify);
            request.getSession().removeAttribute(ValidateCode.RANDOMCODEKEY);
            String result = checkLogin(verifycode, username, password, valiData);
            if (StringUtils.isBlank(result)) {
                SysAccount sysAccount = SysProcess.checkUserIsBlack(username);
                if (null != sysAccount) {
                    String passwordery = EndecryptUtils.encrytm(password, sysAccount.getSalt());
                    if (StringUtils.equals(passwordery, sysAccount.getPwd())) {
                        if (Constents.gen.equals(sysAccount.getLocked())) {
                            UsernamePasswordToken token = new UsernamePasswordToken(username, passwordery);
                            Subject subject = SecurityUtils.getSubject();
                            subject.login(token);
                            HttpSession session = request.getSession();
                            session.setAttribute("path", request.getContextPath());
                            session.setAttribute("username", sysAccount.getLoginname());
                            session.setAttribute("userid", sysAccount.getId());
                            Constents.operatorPageMap.put(sysAccount.getLoginname(), new DateTime());
                            logger.debug("username:--login success ---" + username);
                            succMesg(request, response);
                        } else {
                            errorMsg(request, response, "用户被锁定，无法登录!");
                        }
                    } else {
                        errorMsg(request, response, "用户名或密码错误!");
                    }
                } else {
                    errorMsg(request, response, "用户不存在!");
                }
            } else {
                errorMsg(request, response, result);
            }
        } catch (AuthenticationException e1) {
            limitMsg(request, response);
            e1.printStackTrace();
        }
    }

    private String checkLogin(String verifycode, String username, String password, String valiData) {
        String result = "";
        if (StringUtils.isNotBlank(username)) {
            if (StringUtils.isNotBlank(password)) {
                if (StringUtils.isBlank(verifycode)) {
                    result = "验证码为空!";
                } else if (!StringUtils.equalsIgnoreCase(verifycode, valiData)) {
                    result = "验证码不匹配!";
                }
            } else {
                result = "密码为空!";
            }
        } else {
            result = "用户名为空!";
        }
        return result;
    }

    /***
     * index 登录后首页
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexEng(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("path", request.getContextPath());
        return "/main_index";
    }

    /***
     * 验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/guinea/verify", method = RequestMethod.GET)
    public void verify(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        ValidateCode randomValidateCode = new ValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);// 输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****
     * 登录后首页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/main_index", method = RequestMethod.GET)
    public ModelAndView main_index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String path = request.getContextPath();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String kwyname = user.getKawyiname();
        if (Strings.isNullOrEmpty(kwyname)) {
            kwyname = user.getLoginname();
        }
        modelAndView.addObject("path", path);
        modelAndView.addObject("kwyname", kwyname);
        return modelAndView;
    }

    /****
     * 注册
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/sys/register", method = RequestMethod.GET)
    public String register(HttpServletRequest request, ModelMap model) {
        model.put("path", request.getContextPath());
        UserAgent agent = WebUtil.obtainClientInfo(request);
        Browser browser = agent.getBrowser();
        Version version = agent.getBrowserVersion();
        if (null == version) {
            version = new Version("", "", "");
        }
        OperatingSystem os = agent.getOperatingSystem();
        Map<String, String> browerMap = Maps.newConcurrentMap(), systemMap = Maps.newConcurrentMap();
        browerMap.put("名称", browser.getName());
        browerMap.put("厂商", browser.getManufacturer().toString());
        browerMap.put("产品系列", browser.getGroup().toString());
        browerMap.put("引擎", browser.getRenderingEngine().toString());
        browerMap.put("版本", version.getVersion());
        systemMap.put("操作系统名称", os.getName());
        systemMap.put("设备类型", os.getDeviceType().toString());
        systemMap.put("产品系列", os.getGroup().toString());
        systemMap.put("生产厂商", os.getManufacturer().toString());
        processShowSelect(model);
        model.put("browerMap", browerMap);
        model.put("systemMap", systemMap);
        model.put("systemOS", os.getName());
        model.put("browserName", browser.getName());
        model.put("browserVersion", version.getMajorVersion());
        return "sys/register";
    }

    /***
     * 处理注册业务
     *
     * @param request
     * @Describe: 注册
     */
    @RequestMapping(value = "/sys/register", method = RequestMethod.POST)
    public void registerSub(HttpServletRequest request, HttpServletResponse response) {
        boolean flag = false;
        String kwy_name = StringUtils.trim(request.getParameter("kwy_name")),
                login_name = StringUtils.trim(request.getParameter("login_name")),
                phoneNumber = StringUtils.trim(request.getParameter("phoneNumber")),
                pwd = StringUtils.trim(request.getParameter("pwd")),
                email = StringUtils.trim(request.getParameter("email")),
                sex = StringUtils.trim(request.getParameter("sex")), ip = StringUtils.trim(request.getParameter("ip")),
                city = StringUtils.trim(request.getParameter("city")),
                browserVersion = StringUtils.trim(request.getParameter("browserVersion")),
                browserName = StringUtils.trim(request.getParameter("browserName")),
                systemOS = StringUtils.trim(request.getParameter("systemOS"));
        if (StringUtils.isNotBlank(kwy_name)) {
            if (StringUtils.isNotBlank(login_name)) {
                if (StringUtils.isNotBlank(phoneNumber) && phoneNumber.length() == 11
                        && StringUtils.isNumericSpace(phoneNumber)) {
                    if (StringUtils.isNotBlank(pwd)) {
                        if (StringUtils.isNotBlank(email) && CommonToolsUtil.checkEmail(email)) {
                            flag = true;
                        } else {
                            errorMsg(request, response, "邮箱为空或格式不正确!");
                        }
                    } else {
                        errorMsg(request, response, "密码不能为空!");
                    }
                } else {
                    errorMsg(request, response, "手机号码为空或格式不正确!");
                }
            } else {
                errorMsg(request, response, "登录名不能为空!");
            }
        } else {
            errorMsg(request, response, "昵称不能为空!");
        }
        if (flag) {
            try {
                String salt = EndecryptUtils.createKey(), password = EndecryptUtils.encrytm(pwd, salt);
                Short sexes = Short.valueOf("-1");
                if (StringUtils.isNumeric(sex)) {
                    sexes = Short.valueOf(sex);
                }
                String type = "";
                if (StringUtils.containsIgnoreCase(systemOS, "android")) {
                    type = Constents.WEB_A;
                } else if (StringUtils.containsIgnoreCase(systemOS, "ios")) {
                    type = Constents.WEB_IOS;
                } else {
                    type = Constents.WEB_PC;
                }
                SysAccount sysAccount = new SysAccount();
                sysAccount.setKawyiname(kwy_name);
                sysAccount.setLoginname(login_name);
                sysAccount.setPhonenumber(phoneNumber);
                sysAccount.setEmail(email);
                sysAccount.setPwd(password);
                sysAccount.setSex(sexes);
                sysAccount.setSalt(salt);
                sysAccount.setCreateip(ip);
                sysAccount.setCreatecity(city);
                sysAccount.setType(type);
                sysAccount.setCreatetime(new Date());
                sysAccount.setIsdelete(Constents.gen);
                sysAccount.setLocked(Constents.gen);
                if (processCheckUser(request, response, sysAccount)) {
                    // 注册
                    sysAccountService.registerUser(sysAccount, browserName, browserVersion, systemOS);
                    succMesg(request, response);
                }
            } catch (Exception e) {
                errorMsg(request, response);
                e.printStackTrace();
            }
        }
    }

    private void processShowSelect(ModelMap model) {
        Map<String, String> sexEnumMap = Maps.newHashMap();
        for (SexEnum c : SexEnum.values()) {
            sexEnumMap.put(c.getCode() + "", c.getName());
        }
        model.put("sexEnumMap", sexEnumMap);
    }

    /****
     * 检查用户是否重复
     *
     * @param request
     * @param response
     * @param sysAccount
     * @return
     */
    private Boolean processCheckUser(HttpServletRequest request, HttpServletResponse response, SysAccount sysAccount) {
        String userName = sysAccount.getLoginname(), phone = sysAccount.getPhonenumber(),
                kwyName = sysAccount.getKawyiname();
        Map<String, SysAccount> sysAccountMap = SysCache.getUserMap();
        Boolean flagt = false, flagp = false, flagk = false, falg = true;
        for (Map.Entry<String, SysAccount> entry : sysAccountMap.entrySet()) {
            if (StringUtils.equals(StringUtils.trim(entry.getKey()), userName)) {
                flagt = true;
                break;
            }
            if (StringUtils.equals(StringUtils.trim(entry.getValue().getPhonenumber()), phone)) {
                flagp = true;
                break;
            }
            if (StringUtils.equals(StringUtils.trim(entry.getValue().getKawyiname()), kwyName)) {
                flagk = true;
                break;
            }
        }
        if (flagt || flagp || flagk) {
            falg = false;
            if (flagt) {
                errorMsg(request, response, "登录名已存在！");
            } else if (flagp) {
                errorMsg(request, response, "手机号码已存在！");
            } else if (flagk) {
                errorMsg(request, response, "昵称已经已存在！");
            }
        }
        return falg;
    }

    /***
     * 汉字转化成拼音
     *
     * @param request
     */
    @RequestMapping(value = "/interface/changeToPinYin", method = RequestMethod.POST)
    public void changeToPinYin(HttpServletRequest request, HttpServletResponse response) {
        String v_kayname = request.getParameter("v_kayname_"), pinyinName = "";
        if (StringUtils.isNotBlank(v_kayname)) {
            pinyinName = PingYingUtil.getPinYin(v_kayname);
        }
        succMesg(request, response, pinyinName);
    }

    /***
     * 左菜单
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/main_left_menu", method = RequestMethod.GET)
    public String leftMenu(ModelMap model, HttpServletRequest request) {
        model.put("path", request.getContextPath());
        return "main_left_menu";
    }

    /***
     * 首tab
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/main_menu", method = RequestMethod.GET)
    public String quickMenu(ModelMap model, HttpServletRequest request) {
        model.put("path", request.getContextPath());
        return "main_menu";
    }

    /***
     * 首tab
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public ModelAndView exit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (null != user) {
            Constents.operatorPageMap.remove(user.getLoginname());
        }
        session.removeAttribute("path");
        session.removeAttribute("username");
        session.removeAttribute("userid");
        SecurityUtils.getSubject().logout();
        return new ModelAndView("redirect:login");
    }
}
