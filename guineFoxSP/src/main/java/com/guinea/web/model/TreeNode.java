package com.guinea.web.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by shiky on 2015/12/28.
 * @describe ztree 的treenode 对象
 */
public class TreeNode implements Serializable{

    private static final long serialVersionUID = 5684001575383293538L;

    private Long id,pId;

    private String name;
    /***
     * checked:选中;open:打开;isParent:是否是父节点;expand:不可展开;collapse:不可折叠
     */
    private Boolean checked,open,isParent,expand,collapse;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getChecked() {
        return checked;
    }

    public Boolean getOpen() {
        return open;
    }

    public Boolean getParent() {
        return isParent;
    }

    public Boolean getExpand() {
        return expand;
    }

    public Boolean getCollapse() {
        return collapse;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public void setCollapse(Boolean collapse) {
        this.collapse = collapse;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
