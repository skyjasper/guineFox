package com.guinea.util;

import java.util.List;

import com.google.common.collect.Lists;
import com.guinea.web.model.EasyTree;

/***
 * shiky
 */
public class EasyTreeUtil {
    /***
     * 方法描述: 父节点
     *
     * @param treeDataList
     * @return
     */
    public static List<EasyTree> getfatherNode(List<EasyTree> treeDataList) {
        List<EasyTree> newTreeDataList = Lists.newArrayList();
        if (null != treeDataList && treeDataList.size() > 0) {
            for (EasyTree jsonTreeData : treeDataList) {
                if (Constents.rootId.equals(jsonTreeData.getParentId())) {
                    //获取父节点下的子节点
                    jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
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
    private final static List<EasyTree> getChildrenNode(Long pid, List<EasyTree> treeDataList) {
        List<EasyTree> newTreeDataList = Lists.newArrayList();
        if (null != treeDataList && treeDataList.size() > 0) {
            for (EasyTree jsonTreeData : treeDataList) {
                if (Constents.rootId.equals(jsonTreeData.getParentId())) {
                    continue;
                }
                //这是一个子节点
                if (jsonTreeData.getParentId().equals(pid)) {
                    //递归获取子节点下的子节点
                    jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
                    newTreeDataList.add(jsonTreeData);
                }
            }
        }
        return newTreeDataList;
    }
}
