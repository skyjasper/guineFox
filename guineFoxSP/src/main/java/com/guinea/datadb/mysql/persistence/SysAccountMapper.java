package com.guinea.datadb.mysql.persistence;

import java.util.List;
import java.util.Map;

import com.guinea.datadb.mysql.entity.SysAccount;

public interface SysAccountMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table sys_account
	 *
	 * @mbggenerated Tue Dec 08 14:29:01 CST 2015
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table sys_account
	 *
	 * @mbggenerated Tue Dec 08 14:29:01 CST 2015
	 */
	int insert(SysAccount record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table sys_account
	 *
	 * @mbggenerated Tue Dec 08 14:29:01 CST 2015
	 */
	SysAccount selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table sys_account
	 *
	 * @mbggenerated Tue Dec 08 14:29:01 CST 2015
	 */
	List<SysAccount> selectAll();

	/***
	 * 所有没有被删除的
	 * 
	 * @return
	 */
	List<SysAccount> selectByNoDelete();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table sys_account
	 *
	 * @mbggenerated Tue Dec 08 14:29:01 CST 2015
	 */
	int updateByPrimaryKey(SysAccount record);

	/***
	 * 修改密码
	 * 
	 * @param record
	 * @return
	 */
	int updatePwdById(SysAccount record);

	/****
	 * 分页查询
	 * 
	 * @param conditions
	 * @return
	 */
	List<SysAccount> findByPage(Map<String, String> conditions);

	/***
	 *
	 * @param conditions
	 * @return
	 */
	List<SysAccount> findByPageDetail(Map<String, String> conditions);

	/***
	 * 查询通过对象
	 * 
	 * @param sysAccount
	 * @return
	 */
	List<SysAccount> selectByObj(SysAccount sysAccount);
}