package com.guinea.web.model;

import java.io.Serializable;

import com.guinea.util.Constents;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.guinea.datadb.mysql.entity.SysAccount;

/**
 * Created by shiky on 2015/12/3.
 */
public class User implements Serializable {
	private static final long serialVersionUID = -3738374772032063760L;
	private Long id; // 编号
	private String loginname; // 登录名
	private String phonenumber;
	private String kawyiname;
	private Short locked = Constents.gen;

	public User() {
	}

	public User(SysAccount sysAccount) {
		if (null != sysAccount) {
			this.id = sysAccount.getId();
			this.loginname = sysAccount.getLoginname();
			this.kawyiname = sysAccount.getKawyiname();
			this.phonenumber = sysAccount.getPhonenumber();
			this.locked = sysAccount.getLocked();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getKawyiname() {
		return kawyiname;
	}

	public void setKawyiname(String kawyiname) {
		this.kawyiname = kawyiname;
	}

	public Short getLocked() {
		return locked;
	}

	public void setLocked(Short locked) {
		this.locked = locked;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
