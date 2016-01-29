package com.guinea.profession.service.sys;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.guinea.datadb.mysql.entity.*;
import com.guinea.datadb.mysql.model.UserOrganizationView;
import com.guinea.datadb.mysql.persistence.*;
import com.guinea.util.Constents;
import com.guinea.util.EasyTreeUtil;
import com.guinea.util.profession.EasyTreeUserOrgUtil;
import com.guinea.web.MsgResult;
import com.guinea.web.model.EasyTree;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by shiky on 2015/12/28.
 */
@Service("sysResourceService")
public class SysResourceService {

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Autowired
    private UserOrganizationMapper userOrganizationMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    public List<SysResource> selectAll() {
        return sysResourceMapper.selectAll();
    }

    public int insert(SysResource record) {
        return sysResourceMapper.insert(record);
    }

    public SysResource selectByNameForPId(SysResource record) {
        SysResource sysResource = null;
        List<SysResource> sysResources = sysResourceMapper.selectByNameForPId(record);
        if (null != sysResources && sysResources.size() > 0) {
            sysResource = sysResources.get(0);
        }
        return sysResource;
    }

    /***
     * 删除
     * @param id
     * @return
     */
    public MsgResult deleteByPrimaryKey(Long id) {
        MsgResult msgResult = new MsgResult();
        if(null!=id){
            if(id==1){
                msgResult.put(2,"根资源不能删除!");
            }else{
                List<RoleResource> roleResources = roleResourceMapper.selectByResourceId(id);
                if(null!=roleResources&&roleResources.size()>0){
                    msgResult.put(3,"资源被角色授权无法删除!");
                }else{
                    sysResourceMapper.deleteByPrimaryKey(id);
                    roleResourceMapper.deleteByResourceId(id);
                    //更新缓存权限
                    Constents.operatorRoleCount.addAndGet(1);
                    msgResult.put(0,"删除成功!");
                }
            }
        }else{
            msgResult.put(1,"参数不全!");
        }
        return msgResult;
    }

    public SysResource selectByPrimaryKey(Long id) {
        return sysResourceMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(SysResource record) {
        return sysResourceMapper.updateByPrimaryKey(record);
    }

    /***
     * 处理角色用户授权
     *
     * @param roleId
     * @param userIds
     * @return
     */
    public int rangeRoleToUser(Long roleId, String userIds) {
        int i = -1;
        if (StringUtils.isNotBlank(userIds) && StringUtils.indexOf(userIds, ",") > 0) {
            List<UserRole> userRoles = processInsertUserRoleData(StringUtils.split(userIds, ","), roleId);
            if (null != userRoles && userRoles.size() > 0) {
                userRoleMapper.deleteByRoleId(roleId);
                userRoleMapper.bathInsert(userRoles);
                //更新缓存权限
                Constents.operatorRoleCount.addAndGet(1);
                i = 0;
            }
        } else {
            userRoleMapper.deleteByRoleId(roleId);
            i = 0;
        }
        return i;
    }

    /***
     * 处理用户角色数据
     *
     * @param userIds
     * @param roleId
     * @return
     */
    private List<UserRole> processInsertUserRoleData(String[] userIds, Long roleId) {
        List<UserRole> userRoles = Lists.newArrayList();
        if (ArrayUtils.isNotEmpty(userIds)) {
            Map<Long, Long> resIdMap = Maps.newHashMap();
            for (String userId : userIds) {
                if (StringUtils.isNumeric(StringUtils.trim(userId))) {
                    resIdMap.put(Long.valueOf(userId), roleId);
                }
            }
            resIdMap.forEach((key, val) -> {
                        UserRole userRole = new UserRole();
                        userRole.setuId(key);
                        userRole.setrId(roleId);
                        userRoles.add(userRole);
                    }
            );
        }
        return userRoles;
    }

    /***
     * 角色资源授权
     *
     * @param roleId
     * @param resIds
     * @return
     */
    public int rangeRoleToResource(Long roleId, String resIds) {
        int i = -1;
        if (StringUtils.isNotBlank(resIds) && StringUtils.indexOf(resIds, ",") > 0) {
            List<RoleResource> roleResources = processInsertData(StringUtils.split(resIds, ","), roleId);
            if (null != roleResources && roleResources.size() > 0) {
                roleResourceMapper.deleteByRoleId(roleId);
                roleResourceMapper.bathInsert(roleResources);
                //更新缓存权限
                Constents.operatorRoleCount.addAndGet(1);
                i = 0;
            }
        } else {
            roleResourceMapper.deleteByRoleId(roleId);
            i = 0;
        }
        return i;
    }

    /****
     * 角色资源授权数据处理
     *
     * @param resIdas
     * @param roleId
     * @return
     */
    private List<RoleResource> processInsertData(String[] resIdas, Long roleId) {
        List<RoleResource> roleResources = Lists.newArrayList();
        if (ArrayUtils.isNotEmpty(resIdas)) {
            Map<Long, Long> resIdMap = Maps.newHashMap();
            for (String resId : resIdas) {
                if (StringUtils.isNumeric(StringUtils.trim(resId))) {
                    resIdMap.put(Long.valueOf(resId), roleId);
                }
            }
            resIdMap.forEach((key, val) -> {
                        RoleResource roleResource = new RoleResource();
                        roleResource.setResourceId(key);
                        roleResource.setRoleId(val);
                        roleResources.add(roleResource);
                    }
            );
        }
        return roleResources;
    }

    /***
     * 角色用户树
     *
     * @param roleId
     * @return
     */
    public List<EasyTree> processRoleUser(Long roleId) {
        return sysOrgUserToEasyTree(roleId);
    }

    /***
     * 角色用户树拼装
     *
     * @param roleId
     * @return
     */
    private List<EasyTree> sysOrgUserToEasyTree(Long roleId) {
        Map<Long, List<SysAccount>> userListMap = obtainOrgUser();
        List<EasyTree> easyTrees = Lists.newArrayList();
        List<SysOrganization> sysOrganizations = sysOrganizationMapper.selectAll();
        Map<Long,Long> userRoleMap=obtainUserRoleRange(roleId);
        if (null != sysOrganizations && sysOrganizations.size() > 0) {
            sysOrganizations.forEach(ev -> {
                if (null != ev) {
                    EasyTree easyTree = new EasyTree();
                    easyTree.setId(ev.getId());
                    easyTree.setParentId(ev.getParentId());
                    easyTree.setText(ev.getDescription());
                    easyTree.setType("d");
                    easyTrees.add(easyTree);
                }
            });
        }
        return EasyTreeUserOrgUtil.getfatherNodeToOrgUser(easyTrees, userListMap,userRoleMap);
    }

    private Map<Long,Long> obtainUserRoleRange(Long roleId){
        List<UserRole> userRoles= userRoleMapper.selectByRoleId(roleId);
        Map<Long,Long> userRoleMap = Maps.newHashMap();
        if(null!=userRoles&&userRoles.size()>0){
            userRoles.forEach(ev -> {
                if (null != ev) {
                    userRoleMap.put(ev.getuId(),roleId);
                }
            });
        }
        return userRoleMap;
    }

    /***
     * 角色资源树
     *
     * @param roleId
     */
    public List<EasyTree> processRoleResource(Long roleId) {
        return EasyTreeUtil.getfatherNode(sysResourcetoEasyTree(roleId));
    }

    /***
     * 获取用户组织map
     *
     * @return
     */
    private Map<Long, List<SysAccount>> obtainOrgUser() {
        List<UserOrganizationView> userOrganizationViews = userOrganizationMapper.selectUserOrgIdAll();
        Map<Long, List<SysAccount>> userListMap = Maps.newHashMap();
        if (null != userOrganizationViews && userOrganizationViews.size() > 0) {
            userOrganizationViews.forEach(ev -> {
                if (null != ev) {
                    Long orgId = ev.getOrgId();
                    List<SysAccount> tempUser = userListMap.get(orgId);
                    if (null != tempUser && tempUser.size() > 0) {
                        SysAccount sysAccount = new SysAccount();
                        sysAccount.setId(ev.getUserId());
                        sysAccount.setKawyiname(ev.getKawyiname());
                        tempUser.add(sysAccount);
                    } else {
                        tempUser = Lists.newArrayList();
                        SysAccount sysAccount = new SysAccount();
                        sysAccount.setId(ev.getUserId());
                        sysAccount.setKawyiname(ev.getKawyiname());
                        tempUser.add(sysAccount);
                        userListMap.put(orgId, tempUser);
                    }
                }
            });
        }
        return userListMap;
    }

    /***
     * 拼装角色资源树
     *
     * @param roleId
     * @return
     */
    private List<EasyTree> sysResourcetoEasyTree(Long roleId) {
        List<EasyTree> easyTrees = Lists.newArrayList();
        List<SysResource> sysResources = sysResourceMapper.selectAll();
        List<RoleResource> roleResources = roleResourceMapper.selectByRoleId(roleId);
        Map<Long, Long> roleResourceMap = Maps.newHashMap();
        if (null != roleResources && roleResources.size() > 0) {
            roleResources.forEach(ev -> {
                if (null != ev) {
                    roleResourceMap.put(ev.getResourceId(), ev.getRoleId());
                }
            });
        }
        if (null != sysResources && sysResources.size() > 0) {
            sysResources.forEach(ev -> {
                if (null != ev) {
                    EasyTree easyTree = new EasyTree();
                    easyTree.setId(ev.getId());
                    easyTree.setParentId(ev.getParentId());
                    easyTree.setText(ev.getName());
                    if (roleId.equals(roleResourceMap.get(ev.getId()))) {
                        easyTree.setChecked(true);
                    }
                    easyTrees.add(easyTree);
                }
            });
        }
        return easyTrees;
    }
}
