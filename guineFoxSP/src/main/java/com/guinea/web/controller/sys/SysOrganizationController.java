package com.guinea.web.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.guinea.datadb.mysql.entity.SysOrganization;
import com.guinea.profession.service.sys.SysOrganizationService;
import com.guinea.util.Constents;
import com.guinea.web.BaseFormController;
import com.guinea.web.MsgResult;
import com.guinea.web.model.TreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: shiky
 * @describle:
 * @dateTime: 2016/1/11
 */
@Controller
public class SysOrganizationController extends BaseFormController {

    @Autowired
    private SysOrganizationService sysOrganizationService;

    @RequestMapping(value = "/sys/organization/sysOrganizationList", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        List<SysOrganization> sysOrganizations = sysOrganizationService.selectAll();
        List<TreeNode> treeNodes = Lists.newArrayList();
        if (null != sysOrganizations && sysOrganizations.size() > 0) {
            for (SysOrganization sysOrganization : sysOrganizations) {
                if (null != sysOrganization) {
                    TreeNode treeNode = new TreeNode();
                    if (sysOrganization.getId() == 1 || sysOrganization.getId() == 2) {
                        treeNode.setOpen(true);
                    }
                    treeNode.setId(sysOrganization.getId());
                    treeNode.setpId(sysOrganization.getParentId());
                    treeNode.setName(sysOrganization.getDescription());
                    treeNodes.add(treeNode);
                }
            }
        }
        try {
            model.put("ztreeNodes",  objectMapper
                    .writeValueAsString(treeNodes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "sys/organization/sysOrganizationList";
    }

    @RequestMapping(value = "/sys/organization/sysOrganizationAdd", method = RequestMethod.GET)
    public String showAdd(Long parentId, ModelMap model) {
        model.put("parentId", parentId);
        return "sys/organization/sysOrganizationAdd";
    }

    @RequestMapping(value = "/sys/organization/sysOrganizationAdd", method = {RequestMethod.POST})
    public void processAdd(SysOrganization sysOrganization, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        if (null != sysOrganization) {
            String orgName = sysOrganization.getOrgName(), description = sysOrganization.getDescription();
            if (StringUtils.isNotBlank(orgName) && StringUtils.isNotBlank(description)) {
                List<SysOrganization> orgDBs = sysOrganizationService.selectByNameForPId(sysOrganization);
                if (orgDBs != null && orgDBs.size() > 0) {
                    errorMsg(request, response, "名称已经存在!");
                } else {
                    sysOrganization.setAvailable(Constents.gen);
                    sysOrganizationService.insert(sysOrganization);
                    succMesgParam(request, response, objectMapper.writeValueAsString(sysOrganization));
                }
            } else {
                errorMsg(request, response, "参数不全!");
            }
        } else {
            errorMsg(request, response, "操作失败!");
        }
    }

    @RequestMapping(value = "/sys/organization/sysOrganizationEdit", method = RequestMethod.GET)
    public String showEdit(Long id, HttpServletRequest request, ModelMap model) {
        SysOrganization sysOrganization = sysOrganizationService.selectByPrimaryKey(id);
        model.put("sysOrganization", sysOrganization);
        return "sys/organization/sysOrganizationEdit";
    }

    @RequestMapping(value = "/sys/organization/sysOrganizationEdit", method = {RequestMethod.POST})
    public void processEdit(SysOrganization sysOrganization, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        if (null != sysOrganization) {
            if(sysOrganization.getId()==1){
                errorMsg(request, response,"根组织不能修改");
            }else{
                List<SysOrganization> sysDBs = sysOrganizationService.selectByNameForPId(sysOrganization);
                boolean flag = false;
                if (null != sysDBs && sysDBs.size() > 0) {
                    SysOrganization sysDB = sysDBs.get(0);
                    if (null != sysDB && !sysDB.getId().equals(sysOrganization.getId())) {
                        flag = false;
                        errorMsg(request, response, "名称已经存在!");
                    } else {
                        flag = true;
                    }
                } else {
                    flag = true;
                }
                if (flag) {
                    sysOrganizationService.updateByPrimaryKey(sysOrganization);
                    succMesgParam(request, response, objectMapper.writeValueAsString(sysOrganization));
                }
            }
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
    @RequestMapping(value = "/sys/organization/sysOrganizationDelete", method = {RequestMethod.POST})
    public void processDelete(Long id, HttpServletRequest request, HttpServletResponse response) {
        MsgResult msgResult = sysOrganizationService.deleteByPrimaryKey(id);
        if (msgResult.getKey() == 0) {
            succMesg(request, response, msgResult.getValue());
        } else {
            errorMsg(request, response, msgResult.getValue());
        }
    }


}
