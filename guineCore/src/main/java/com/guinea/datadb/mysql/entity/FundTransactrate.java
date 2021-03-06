package com.guinea.datadb.mysql.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/****
 * 股票交易流水
 * 
 * @author shiky
 *
 */
public class FundTransactrate implements Serializable{
	private static final long serialVersionUID = -9022004083264617556L;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column fund_transactrate.id
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	private Long id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column fund_transactrate.scode
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	private String scode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column fund_transactrate.scountg
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	private Integer scountg;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column fund_transactrate.scounts
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	private Short scounts;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column fund_transactrate.tRatio
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	private BigDecimal tratio;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column fund_transactrate.tType
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	private Short ttype;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column fund_transactrate.tTime
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	private Date ttime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column fund_transactrate.mvalue
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	private BigDecimal mvalue;

	/***
	 * 股票名称
	 */
	private String ssname;

	/***
	 * trator type
	 */
	private String ttypeName;

	private Long uid;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column fund_transactrate.id
	 *
	 * @return the value of fund_transactrate.id
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column fund_transactrate.id
	 *
	 * @param id
	 *            the value for fund_transactrate.id
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column fund_transactrate.scode
	 *
	 * @return the value of fund_transactrate.scode
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public String getScode() {
		return scode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column fund_transactrate.scode
	 *
	 * @param scode
	 *            the value for fund_transactrate.scode
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public void setScode(String scode) {
		this.scode = scode == null ? null : scode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column fund_transactrate.scountg
	 *
	 * @return the value of fund_transactrate.scountg
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public Integer getScountg() {
		return scountg;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column fund_transactrate.scountg
	 *
	 * @param scountg
	 *            the value for fund_transactrate.scountg
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public void setScountg(Integer scountg) {
		this.scountg = scountg;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column fund_transactrate.scounts
	 *
	 * @return the value of fund_transactrate.scounts
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public Short getScounts() {
		return scounts;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column fund_transactrate.scounts
	 *
	 * @param scounts
	 *            the value for fund_transactrate.scounts
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public void setScounts(Short scounts) {
		this.scounts = scounts;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column fund_transactrate.tRatio
	 *
	 * @return the value of fund_transactrate.tRatio
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public BigDecimal getTratio() {
		return tratio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column fund_transactrate.tRatio
	 *
	 * @param tratio
	 *            the value for fund_transactrate.tRatio
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public void setTratio(BigDecimal tratio) {
		this.tratio = tratio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column fund_transactrate.tType
	 *
	 * @return the value of fund_transactrate.tType
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public Short getTtype() {
		return ttype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column fund_transactrate.tType
	 *
	 * @param ttype
	 *            the value for fund_transactrate.tType
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public void setTtype(Short ttype) {
		this.ttype = ttype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column fund_transactrate.tTime
	 *
	 * @return the value of fund_transactrate.tTime
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public Date getTtime() {
		return ttime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column fund_transactrate.tTime
	 *
	 * @param ttime
	 *            the value for fund_transactrate.tTime
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public void setTtime(Date ttime) {
		this.ttime = ttime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column fund_transactrate.mvalue
	 *
	 * @return the value of fund_transactrate.mvalue
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public BigDecimal getMvalue() {
		return mvalue;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column fund_transactrate.mvalue
	 *
	 * @param mvalue
	 *            the value for fund_transactrate.mvalue
	 *
	 * @mbggenerated Wed Sep 23 14:11:10 CST 2015
	 */
	public void setMvalue(BigDecimal mvalue) {
		this.mvalue = mvalue;
	}

	public String getTtypeName() {
		return ttypeName;
	}

	public void setTtypeName(String ttypeName) {
		this.ttypeName = ttypeName;
	}

	public String getSsname() {
		return ssname;
	}

	public void setSsname(String ssname) {
		this.ssname = ssname;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}