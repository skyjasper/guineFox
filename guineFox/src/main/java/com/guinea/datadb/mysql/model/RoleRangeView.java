package com.guinea.datadb.mysql.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * @author: shiky
 * @describle:
 * @dateTime: 2016/1/12
 */
public class RoleRangeView implements Serializable{

    private static final long serialVersionUID = -8395644260248026171L;

    private Long uid,rid,resourceid;

    private String loginname,rolename,type,resname,permission;

    private Short locked,isdelete,roleAvailable,resAvailable;

    public Long getUid() {
        return uid;
    }

    public Long getRid() {
        return rid;
    }

    public Long getResourceid() {
        return resourceid;
    }

    public String getLoginname() {
        return loginname;
    }

    public String getRolename() {
        return rolename;
    }

    public String getType() {
        return type;
    }

    public String getResname() {
        return resname;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setResourceid(Long resourceid) {
        this.resourceid = resourceid;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setResname(String resname) {
        this.resname = resname;
    }

    public Short getLocked() {
        return locked;
    }

    public Short getIsdelete() {
        return isdelete;
    }

    public Short getRoleAvailable() {
        return roleAvailable;
    }

    public Short getResAvailable() {
        return resAvailable;
    }

    public void setLocked(Short locked) {
        this.locked = locked;
    }

    public void setIsdelete(Short isdelete) {
        this.isdelete = isdelete;
    }

    public void setRoleAvailable(Short roleAvailable) {
        this.roleAvailable = roleAvailable;
    }

    public void setResAvailable(Short resAvailable) {
        this.resAvailable = resAvailable;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
