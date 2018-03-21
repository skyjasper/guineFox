package com.guinea.web.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.guinea.datadb.mysql.entity.SysResource;
import com.guinea.datadb.mysql.entity.SysRole;
import com.guinea.dic.ResourceTypeEnum;
import com.guinea.profession.service.sys.SysResourceService;
import com.guinea.profession.service.sys.SysRoleService;
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
import java.util.Map;

/**
 * Created by shiky on 2015/12/28.
 */
@Controller
public class SysResourceController extends BaseFormController {

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping(value = "/sys/sysResourceList", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        List<SysResource> sysResources = sysResourceService.selectAll();
        List<TreeNode> treeNodes = Lists.newArrayList();
        if (null != sysResources && sysResources.size() > 0) {
            for (SysResource sysResource : sysResources) {
                if (null != sysResource) {
                    TreeNode treeNode = new TreeNode();
                    if (sysResource.getId() == 1 || sysResource.getId() == 2) {
                        treeNode.setOpen(true);
                    }
                    treeNode.setId(sysResource.getId());
                    treeNode.setpId(sysResource.getParentId());
                    treeNode.setName(sysResource.getName());
                    treeNodes.add(treeNode);
                }
            }
        }
        try {
            model.put("ztreeNodes", objectMapper.writeValueAsString(treeNodes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "sys/sysResourceList";
    }

    @RequestMapping(value = "/sys/sysResourceAdd", method = RequestMethod.GET)
    public String showAdd(Long parentId, HttpServletRequest request, ModelMap model) {
        processShowSelect(model);
        model.put("parentId", parentId);
        return "sys/sysResourceAdd";
    }

    @RequestMapping(value = "/sys/sysResourceAdd", method = {RequestMethod.POST})
    public void processAdd(SysResource sysResource, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        if (null != sysResource) {
            String url = sysResource.getUrl(), permission = sysResource.getPermission(), name = sysResource.getName();
            if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(permission) && StringUtils.isNotBlank(name)) {
                SysResource resDB = sysResourceService.selectByNameForPId(sysResource);
                if (resDB != null) {
                    errorMsg(request, response, "名称已经存在!");
                } else {
                    sysResource.setAvailable(Constents.gen);
                    sysResourceService.insert(sysResource);
                    //更新缓存权限
                    Constents.operatorRoleCount.addAndGet(1);
                    succMesgParam(request, response, objectMapper.writeValueAsString(sysResource));
                }
            } else {
                errorMsg(request, response, "参数不全!");
            }
        } else {
            errorMsg(request, response, "操作失败!");
        }
    }

    @RequestMapping(value = "/sys/sysResourceEdit", method = RequestMethod.GET)
    public String showEdit(Long id, HttpServletRequest request, ModelMap model) {
        processShowSelect(model);
        SysResource sysResource = sysResourceService.selectByPrimaryKey(id);
        model.put("sysResource", sysResource);
        return "sys/sysResourceEdit";
    }

    /****
     * 编辑
     *
     * @param sysResource
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sys/sysResourceEdit", method = {RequestMethod.POST})
    public void processEdit(SysResource sysResource, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        if (null != sysResource) {
            if(sysResource.getId()==1){
                errorMsg(request, response,"系统资源不能修改");
            }else{
                SysResource sysDB = sysResourceService.selectByNameForPId(sysResource);
                if (null != sysDB && !sysDB.getId().equals(sysResource.getId())) {
                    errorMsg(request, response, "名称已经存在!");
                } else {
                    sysResourceService.updateByPrimaryKey(sysResource);
                    //更新缓存权限
                    Constents.operatorRoleCount.addAndGet(1);
                    succMesgParam(request, response, objectMapper.writeValueAsString(sysResource));
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
    @RequestMapping(value = "/sys/sysResourceDelete", method = {RequestMethod.POST})
    public void processDelete(Long id, HttpServletRequest request, HttpServletResponse response) {
        MsgResult msgResult = sysResourceService.deleteByPrimaryKey(id);
            if (msgResult.getKey() == 0) {
                succMesg(request, response, msgResult.getValue());
            } else {
                errorMsg(request, response, msgResult.getValue());
            }
    }

    private void processShowSelect(ModelMap model) {
        Map<String, String> resourceTypeEnumMap = Maps.newHashMap();
        for (ResourceTypeEnum c : ResourceTypeEnum.values()) {
            resourceTypeEnumMap.put(c.getCode(), c.getName());
        }
        model.put("resourceTypeEnumMap", resourceTypeEnumMap);
    }

    /***
     * 加载角色用户权限
     *
     * @param id
     * @param flagt
     * @param model
     * @return
     */
    @RequestMapping(value = "/sys/range/role_user_range", method = RequestMethod.GET)
    public String showUserRange(Long id, String flagt, ModelMap model) {
        SysRole sysRole = new SysRole();
        if (null != id) {
            sysRole = sysRoleService.selectByPrimaryKey(id);
        }
        String temp = flagt + "v";
        model.put("sysRole", sysRole);
        model.put("flagt", temp);
        return "sys/range/role_user_range";
    }

    /***
     * 加载角色用户tree
     *
     * @param roleId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sys/range/role_user_range", method = RequestMethod.POST)
    public void processRoleUserRange(Long roleId, HttpServletRequest request, HttpServletResponse response) {
        try {
            showDataGrid(request, response, objectMapper.writeValueAsString(sysResourceService.processRoleUser(roleId)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /***
     * 处理角色用户授权
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sys/range/role_user_range_process", method = RequestMethod.POST)
    public void processUserRangeProcess(HttpServletRequest request, HttpServletResponse response) {
        String userIds = StringUtils.trim(request.getParameter("userIds")), roleId = StringUtils.trim(request.getParameter("roleId"));
        if (StringUtils.isNotBlank(roleId) && StringUtils.isNumericSpace(roleId)) {
            int i = sysResourceService.rangeRoleToUser(Long.valueOf(roleId), userIds);
            if (i == 0) {
                succMesg(request, response, "授权成功!");
            } else {
                errorMsg(request, response, "操作失败!");
            }
        } else {
            errorMsg(request, response, "参数不全!");
        }
    }

    /****
     * 加载角色资源授权
     *
     * @param id
     * @param flagt
     * @param model
     * @return
     */
    @RequestMapping(value = "/sys/range/role_resource_range", method = RequestMethod.GET)
    public String showRoleRange(Long id, String flagt, ModelMap model) {
        SysRole sysRole = new SysRole();
        if (null != id) {
            sysRole = sysRoleService.selectByPrimaryKey(id);
        }
        String temp = flagt + "v";
        model.put("sysRole", sysRole);
        model.put("flagt", temp);
        return "sys/range/role_resource_range";
    }


    /***
     * 加载角色资源授权tree
     *
     * @param roleId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sys/range/role_resource_range", method = RequestMethod.POST)
    public void processRoleRange(Long roleId, HttpServletRequest request, HttpServletResponse response) {
        try {
            showDataGrid(request, response, objectMapper.writeValueAsString(sysResourceService.processRoleResource(roleId)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /***
     * 处理角色资源授权
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sys/range/role_resource_range_process", method = RequestMethod.POST)
    public void processRoleRangeProcess(HttpServletRequest request, HttpServletResponse response) {
        String resIds = StringUtils.trim(request.getParameter("resIds")), roleId = StringUtils.trim(request.getParameter("roleId"));
        if (StringUtils.isNotBlank(roleId) && StringUtils.isNumericSpace(roleId)) {
            int i = sysResourceService.rangeRoleToResource(Long.valueOf(roleId), resIds);
            if (i == 0) {
                succMesg(request, response, "授权成功!");
            } else {
                errorMsg(request, response, "操作失败!");
            }
        } else {
            errorMsg(request, response, "参数不全!");
        }
    }
}
