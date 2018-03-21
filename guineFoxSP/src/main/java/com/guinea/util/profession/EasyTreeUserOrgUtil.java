package com.guinea.util.profession;

import com.google.common.collect.Lists;
import com.guinea.datadb.mysql.entity.SysAccount;
import com.guinea.util.Constents;
import com.guinea.web.model.EasyTree;

import java.util.List;
import java.util.Map;

/**
 * Created by shiky on 2016/1/8.
 */
public class EasyTreeUserOrgUtil {
    /***
     * 方法描述: 父节点
     *
     * @param treeDataList
     * @return
     */
    public static List<EasyTree> getfatherNodeToOrgUser(List<EasyTree> treeDataList, Map<Long, List<SysAccount>> userListMap,Map<Long,Long> userRoleMap) {
        List<EasyTree> newTreeDataList = Lists.newArrayList();
        if (null != treeDataList && treeDataList.size() > 0) {
            for (EasyTree jsonTreeData : treeDataList) {
                if (Constents.rootId.equals(jsonTreeData.getParentId())) {
                    //获取父节点下的子节点
                    jsonTreeData.setChildren(getChildrenNodeToOrgUser(jsonTreeData.getId(), treeDataList, userListMap,userRoleMap));
                    newTreeDataList.add(jsonTreeData);
                }
            }
        }
        return newTreeDataList;
    }

    /****
     * 方法描述: 子节点
     *
     * @param pid
     * @param treeDataList
     * @return
     */
    private final static List<EasyTree> getChildrenNodeToOrgUser(Long pid, List<EasyTree> treeDataList, Map<Long, List<SysAccount>> userListMap,Map<Long,Long> userRoleMap) {
        List<EasyTree> newTreeDataList = Lists.newArrayList();
        if (null != treeDataList && treeDataList.size() > 0) {
            for (EasyTree jsonTreeData : treeDataList) {
                if (Constents.rootId.equals(jsonTreeData.getParentId())) {
                    processToChildren(jsonTreeData,userListMap,userRoleMap);
                    continue;
                }
                //这是一个子节点
                if (jsonTreeData.getParentId().equals(pid)) {
                    //递归获取子节点下的子节点
                    jsonTreeData.setChildren(getChildrenNodeToOrgUser(jsonTreeData.getId(), treeDataList, userListMap,userRoleMap));
                    processToChildren(jsonTreeData,userListMap,userRoleMap);
                    newTreeDataList.add(jsonTreeData);
                }
            }
        }
        return newTreeDataList;
    }


    private static void processToChildren(EasyTree jsonTreeData, Map<Long, List<SysAccount>> userListMap,Map<Long,Long> userRoleMap) {
        Long id = jsonTreeData.getId();
        List<SysAccount> sysAccounts = userListMap.get(id);
        if (null != sysAccounts && sysAccounts.size() > 0) {
            List<EasyTree> temp = Lists.newArrayList();
            for (SysAccount sysAccount : sysAccounts) {
                if (null != sysAccount) {
                    Long uid= sysAccount.getId();
                    EasyTree easyTree = new EasyTree();
                    easyTree.setId(uid);
                    easyTree.setParentId(id);
                    easyTree.setType("u");
                    easyTree.setText(sysAccount.getKawyiname());
                    if(null!=userRoleMap.get(uid)){
                        easyTree.setChecked(true);
                    }
                    temp.add(easyTree);
                }
            }
            jsonTreeData.setChildren(temp);
        }
    }
}
