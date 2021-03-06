package com.guinea.datadb.mysql.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/***
 * 用户组织
 */
public class UserOrganization implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4998036885940296332L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_organization.id
     *
     * @mbggenerated Fri Jan 08 13:08:47 CST 2016
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_organization.user_id
     *
     * @mbggenerated Fri Jan 08 13:08:47 CST 2016
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_organization.org_id
     *
     * @mbggenerated Fri Jan 08 13:08:47 CST 2016
     */
    private Long orgId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_organization.id
     *
     * @return the value of user_organization.id
     *
     * @mbggenerated Fri Jan 08 13:08:47 CST 2016
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_organization.id
     *
     * @param id the value for user_organization.id
     *
     * @mbggenerated Fri Jan 08 13:08:47 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_organization.user_id
     *
     * @return the value of user_organization.user_id
     *
     * @mbggenerated Fri Jan 08 13:08:47 CST 2016
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_organization.user_id
     *
     * @param userId the value for user_organization.user_id
     *
     * @mbggenerated Fri Jan 08 13:08:47 CST 2016
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_organization.org_id
     *
     * @return the value of user_organization.org_id
     *
     * @mbggenerated Fri Jan 08 13:08:47 CST 2016
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_organization.org_id
     *
     * @param orgId the value for user_organization.org_id
     *
     * @mbggenerated Fri Jan 08 13:08:47 CST 2016
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}