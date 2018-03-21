package com.guinea.web.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Maps;
import com.guinea.datadb.mysql.entity.SysRole;
import com.guinea.dic.AvailableEnum;
import com.guinea.profession.service.sys.SysRoleService;
import com.guinea.util.Constents;
import com.guinea.web.BaseFormController;
import com.guinea.web.MsgResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/***
 * 角色管理
 *
 * @author shiky
 */
@Controller
public class SysRoleController extends BaseFormController {

    private static Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysRoleService roleService;

    @RequestMapping(value = "/sys/sysRoleList", method = RequestMethod.GET)
    public String printWelcome(HttpServletRequest request, ModelMap model) {
        showList(model, logger.hashCode());
        processShowSelect(model);
        return "sys/sysRoleList";
    }

    @RequestMapping(value = "/sys/sysRoleList", method = {RequestMethod.POST})
    public void findBpage(HttpServletRequest request, HttpServletResponse response) {
        try {
            showDataGrid(request, response,
                    objectMapper
                            .writeValueAsString(roleService.findByPage(processParam(request.getParameterMap(), logger.hashCode()))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processShowSelect(ModelMap model) {
        Map<String, String> availableEnumMap = Maps.newHashMap();
        for (AvailableEnum c : AvailableEnum.values()) {
            availableEnumMap.put(c.getCode() + "", c.getName());
        }
        model.put("availableEnumMap", availableEnumMap);
    }

    @RequestMapping(value = "/sys/sysRoleAdd", method = RequestMethod.GET)
    public String showAdd(HttpServletRequest request, ModelMap model) {
        processShowSelect(model);
        return "sys/sysRoleAdd";
    }

    @RequestMapping(value = "/sys/sysRoleAdd", method = {RequestMethod.POST})
    public void processAdd(SysRole sysRole, HttpServletRequest request, HttpServletResponse response) {
        if (null != sysRole) {
            String roleName = sysRole.getRoleName();
            SysRole temp = roleService.selectByName(roleName);
            if (null != temp) {
                errorMsg(request, response, "角色名已存在!");
            } else {
                roleService.insert(sysRole);
                //更新缓存权限
                Constents.operatorRoleCount.addAndGet(1);
                succMesg(request, response);
            }
        } else {
            errorMsg(request, response);
        }
    }

    @RequestMapping(value = "/sys/sysRoleEdit", method = RequestMethod.GET)
    public String showEdit(HttpServletRequest request, Long id, ModelMap model) {
        if (null != id) {
            SysRole sysRole = roleService.selectByPrimaryKey(id);
            processShowSelect(model);
            model.put("sysRole", sysRole);
        }
        processShowSelect(model);
        return "sys/sysRoleEdit";
    }

    @RequestMapping(value = "/sys/sysRoleEdit", method = {RequestMethod.POST})
    public void processEdit(SysRole sysRole, HttpServletRequest request, HttpServletResponse response) {
        if (null != sysRole) {
            String roleNamew = sysRole.getRoleName();
            if (StringUtils.isNotBlank(roleNamew) && StringUtils.isNotBlank(sysRole.getDescription())) {
                SysRole temp = roleService.selectByName(roleNamew);
                if (null != temp && !temp.getId().equals(sysRole.getId())) {
                    errorMsg(request, response, "角色名称已经存在!");
                } else {
                    roleService.updateByPrimaryKey(sysRole);
                    //更新缓存权限
                    Constents.operatorRoleCount.addAndGet(1);
                    succMesg(request, response, "更新成功!");
                }
            } else {
                errorMsg(request, response, "参数不全!");
            }
        } else {
            errorMsg(request, response, "操作失败!");
        }
    }

    /**
     * 删除
     *
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sys/sysRoleRemove", method = {RequestMethod.POST})
    public void processDelete(Long id, HttpServletRequest request, HttpServletResponse response) {
        MsgResult msgResult = roleService.deleteByPrimaryKey(id);
        if (msgResult.getKey() == 0) {
            succMesg(request, response, msgResult.getValue());
        } else {
            errorMsg(request, response, msgResult.getValue());
        }
    }
}
