package com.guinea.datadb.mysql.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by shiky on 2016/1/8.
 */
public class UserOrganizationView implements Serializable{
    private static final long serialVersionUID = -1918945786418843869L;
    private Long id,userId,orgId;
    private String kawyiname;
    private String loginname;
    private String orgName;
    private String description;

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getOrgId() {
        return orgId;
    }


    public String getLoginname() {
        return loginname;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKawyiname() {
        return kawyiname;
    }

    public void setKawyiname(String kawyiname) {
        this.kawyiname = kawyiname;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
