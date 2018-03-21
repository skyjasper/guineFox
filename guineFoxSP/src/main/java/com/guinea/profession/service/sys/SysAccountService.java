package com.guinea.profession.service.sys;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.entity.SysAccount;
import com.guinea.datadb.mysql.entity.SysAccountDetail;
import com.guinea.datadb.mysql.entity.UserOrganization;
import com.guinea.datadb.mysql.entity.UserRole;
import com.guinea.datadb.mysql.persistence.SysAccountDetailMapper;
import com.guinea.datadb.mysql.persistence.SysAccountMapper;
import com.guinea.datadb.mysql.persistence.UserOrganizationMapper;
import com.guinea.datadb.mysql.persistence.UserRoleMapper;
import com.guinea.dic.SexEnum;
import com.guinea.util.Constents;
import com.guinea.web.MsgResult;
import com.guinea.web.PageMsg;

/**
 * Created by shiky on 2015/12/8.
 */
@Service("sysAccountService")
public class SysAccountService{

	@Autowired
	private SysAccountMapper sysAccountMapper;

	@Autowired
	private SysAccountDetailMapper sysAccountDetailMapper;

	@Autowired
	private UserOrganizationMapper userOrganizationMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	public void save(SysAccount sysAccount) {
		sysAccountMapper.insert(sysAccount);
	}

	public PageMsg<SysAccount> findByPage(Map<String, String> conditions) {
		PageInfo<SysAccount> page = PageHelper
				.startPage(Integer.valueOf(conditions.get(Constents.PAGENUMBER_)),
						Integer.valueOf(conditions.get(Constents.PAGESIZE_)))
				.doSelectPageInfo(() -> sysAccountMapper.findByPage(conditions));
		PageMsg<SysAccount> pageMsg = new PageMsg<>();
		pageMsg.setTotal(page.getTotal());
		pageMsg.setRows(processViewm(page.getList()));
		return pageMsg;
	}

	/***
	 * 分页查询详细信息
	 *
	 * @param conditions
	 * @return
	 */
	public PageMsg<SysAccount> findByPageDetail(Map<String, String> conditions) {
		PageInfo<SysAccount> page = PageHelper
				.startPage(Integer.valueOf(conditions.get(Constents.PAGENUMBER_)),
						Integer.valueOf(conditions.get(Constents.PAGESIZE_)))
				.doSelectPageInfo(() -> sysAccountMapper.findByPageDetail(conditions));
		PageMsg<SysAccount> pageMsg = new PageMsg<>();
		pageMsg.setTotal(page.getTotal());
		pageMsg.setRows(processView(page.getList()));
		return pageMsg;
	}

	private List<SysAccount> processView(List<SysAccount> sysAccount) {
		sysAccount.forEach(ev -> {
			String sexName = SexEnum.getName(ev.getSex());
			if (!Strings.isNullOrEmpty(sexName))
				ev.setSexName(sexName);
		});
		return sysAccount;
	}

	private List<SysAccount> processViewm(List<SysAccount> sysAccount) {
		sysAccount.forEach(ev -> {
			String lockedName = "正常", isdeleteName = "正常";
			if (null != ev) {
				if (Constents.del.equals(ev.getIsdelete())) {
					isdeleteName = "删除";
				}
				if (Constents.del.equals(ev.getLocked())) {
					lockedName = "锁定";
				}
			}
			ev.setIsdeleteName(isdeleteName);
			ev.setLockedName(lockedName);
		});
		return sysAccount;
	}

	public SysAccount selectByPrimaryKey(Long id) {
		return sysAccountMapper.selectByPrimaryKey(id);
	}

	public List<SysAccount> selectAll() {
		return sysAccountMapper.selectAll();
	}

	public int updatePwdById(SysAccount sysAccount) {
		return sysAccountMapper.updatePwdById(sysAccount);
	}

	public void updateByPrimaryKey(SysAccount sysAccount) {
		sysAccountMapper.updateByPrimaryKey(sysAccount);
	}

	public void registerUser(SysAccount sysAccount, String browserName, String browserVersion, String systemOS) {
		sysAccountMapper.insert(sysAccount);
		UserOrganization userOrganization = new UserOrganization();
		userOrganization.setUserId(sysAccount.getId());
		userOrganization.setOrgId(3L);// web 注册用户
		userOrganizationMapper.insert(userOrganization);
		SysAccountDetail accountDetail = new SysAccountDetail();
		accountDetail.setUid(sysAccount.getId());
		accountDetail.setCreatebro(browserName);
		accountDetail.setCreatebroversion(browserVersion);
		accountDetail.setCareatesystem(systemOS);
		UserRole userRole = new UserRole();
		userRole.setuId(sysAccount.getId());
		userRole.setrId(2L);//普通用户
		userRoleMapper.insert(userRole);
		sysAccountDetailMapper.insert(accountDetail);
		SysCache.putUserMap(sysAccount);
		//更新缓存权限
		Constents.operatorRoleCount.addAndGet(1);
	}

	public MsgResult lockedByPrimaryKey(Long id,String type){
		MsgResult msgResult = new MsgResult();
		if (null != id&& StringUtils.isNotBlank(type)) {
			if (id == 2 || id == 6) {
				msgResult.put(2, "系统用户不能锁定!");
			} else {
				SysAccount sysAccount = SysCache.getUserMapByid(id);
				if(StringUtils.equals(type,"L")){
					sysAccount.setLocked(Constents.del);
					msgResult.put(0, "锁定成功!");
				}else{
					sysAccount.setLocked(Constents.gen);
					msgResult.put(0, "解锁成功!");
				}
				sysAccountMapper.updateByPrimaryKey(sysAccount);
				SysCache.putUserMap(sysAccount);
				//更新缓存权限
				Constents.operatorRoleCount.addAndGet(1);
			}
		}else{
			msgResult.put(1, "参数不全!");
		}
		return msgResult;
	}

	public MsgResult deleteByPrimaryKey(Long id) {
		MsgResult msgResult = new MsgResult();
		if (null != id) {
			if (id == 2 || id == 6) {
				msgResult.put(2, "系统用户不能删除!");
			} else {
				List<UserOrganization> userOrganizations = userOrganizationMapper.selectByUserId(id);
				if (null != userOrganizations && userOrganizations.size() > 0) {
					msgResult.put(3, "用户被组织使用无法删除!");
				} else {
					List<UserRole> userRoles = userRoleMapper.selectByUserId(id);
					if (null != userRoles && userRoles.size() > 0) {
						msgResult.put(3, "用户被授权无法删除!");
					} else {
						SysAccount sysAccount = sysAccountMapper.selectByPrimaryKey(id);
						sysAccount.setIsdelete(Constents.del);
						sysAccountMapper.updateByPrimaryKey(sysAccount);
						SysCache.removeUserMap(id);
						//更新缓存权限
						Constents.operatorRoleCount.addAndGet(1);
						//
						// userRoleMapper.deleteByUserId(id);
						// userOrganizationMapper.deleteByUserId(id);
						msgResult.put(0, "删除成功!");
					}
				}
			}
		} else {
			msgResult.put(1, "参数不全!");
		}
		return msgResult;
	}

}
