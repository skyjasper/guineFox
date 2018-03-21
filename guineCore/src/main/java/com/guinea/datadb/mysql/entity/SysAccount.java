package com.guinea.datadb.mysql.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/****
 * 系统账户
 * @author shiky
 *
 */
public class SysAccount implements Serializable{

	private static final long serialVersionUID = 3406702495125197964L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.id
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.sex
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private Short sex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.pwd
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private String pwd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.salt
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private String salt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.locked
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private Short locked;
    
    private String lockedName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.isDelete
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private Short isdelete;
    
    private String isdeleteName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.loginName
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private String loginname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.createTime
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.email
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.phoneNumber
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private String phonenumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.createIP
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private String createip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.createCity
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private String createcity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_account.kawyiName
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    private String kawyiname;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_account.id
     *
     * @return the value of sys_account.id
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    
    private String sexName;
    
    private String createBro;
    
    private String careateSystem;

    private String type;
    
    public String getLockedName() {
		return lockedName;
	}

	public void setLockedName(String lockedName) {
		this.lockedName = lockedName;
	}

	public String getIsdeleteName() {
		return isdeleteName;
	}

	public void setIsdeleteName(String isdeleteName) {
		this.isdeleteName = isdeleteName;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSexName() {
		return sexName;
	}

	public String getCreateBro() {
		return createBro;
	}

	public void setCreateBro(String createBro) {
		this.createBro = createBro;
	}

	public String getCareateSystem() {
		return careateSystem;
	}

	public void setCareateSystem(String careateSystem) {
		this.careateSystem = careateSystem;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_account.id
     *
     * @param id the value for sys_account.id
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_account.sex
     *
     * @return the value of sys_account.sex
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public Short getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_account.sex
     *
     * @param sex the value for sys_account.sex
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public void setSex(Short sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_account.pwd
     *
     * @return the value of sys_account.pwd
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_account.pwd
     *
     * @param pwd the value for sys_account.pwd
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_account.salt
     *
     * @return the value of sys_account.salt
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public String getSalt() {
        return salt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_account.salt
     *
     * @param salt the value for sys_account.salt
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Short getLocked() {
        return locked;
    }

    public void setLocked(Short locked) {
        this.locked = locked;
    }

    public Short getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Short isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_account.loginName
     *
     * @return the value of sys_account.loginName
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public String getLoginname() {
        return loginname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_account.loginName
     *
     * @param loginname the value for sys_account.loginName
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_account.createTime
     *
     * @return the value of sys_account.createTime
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_account.createTime
     *
     * @param createtime the value for sys_account.createTime
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_account.email
     *
     * @return the value of sys_account.email
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_account.email
     *
     * @param email the value for sys_account.email
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_account.phoneNumber
     *
     * @return the value of sys_account.phoneNumber
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_account.phoneNumber
     *
     * @param phonenumber the value for sys_account.phoneNumber
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_account.createIP
     *
     * @return the value of sys_account.createIP
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public String getCreateip() {
        return createip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_account.createIP
     *
     * @param createip the value for sys_account.createIP
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public void setCreateip(String createip) {
        this.createip = createip == null ? null : createip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_account.createCity
     *
     * @return the value of sys_account.createCity
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public String getCreatecity() {
        return createcity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_account.createCity
     *
     * @param createcity the value for sys_account.createCity
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public void setCreatecity(String createcity) {
        this.createcity = createcity == null ? null : createcity.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_account.kawyiName
     *
     * @return the value of sys_account.kawyiName
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public String getKawyiname() {
        return kawyiname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_account.kawyiName
     *
     * @param kawyiname the value for sys_account.kawyiName
     *
     * @mbggenerated Tue Dec 08 14:29:01 CST 2015
     */
    public void setKawyiname(String kawyiname) {
        this.kawyiname = kawyiname == null ? null : kawyiname.trim();
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}