package com.guinea.profession.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.guinea.datadb.mysql.entity.RoleResource;
import com.guinea.datadb.mysql.entity.SysRole;
import com.guinea.datadb.mysql.entity.UserRole;
import com.guinea.datadb.mysql.persistence.RoleResourceMapper;
import com.guinea.datadb.mysql.persistence.SysRoleMapper;
import com.guinea.datadb.mysql.persistence.UserRoleMapper;
import com.guinea.dic.AvailableEnum;
import com.guinea.util.Constents;
import com.guinea.web.MsgResult;
import com.guinea.web.PageMsg;

@Service("sysRoleService")
public class SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private RoleResourceMapper roleResourceMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	public int insert(SysRole record) {
		return sysRoleMapper.insert(record);
	}

	public MsgResult deleteByPrimaryKey(Long id) {
		MsgResult msgResult = new MsgResult();
		if(null!=id){
            if(id==1){
				msgResult.put(2,"根角色不能删除!");
			}else{
				List<RoleResource> roleResources = roleResourceMapper.selectByRoleId(id);
				if(null!=roleResources&&roleResources.size()>0){
					msgResult.put(3,"角色被资源授权无法删除!");
				}else{
					List<UserRole> userRoles= userRoleMapper.selectByRoleId(id);
					if(null!=userRoles&&userRoles.size()>0){
						msgResult.put(3,"角色被用户授权无法删除!");
					}else{
						sysRoleMapper.deleteByPrimaryKey(id);
						roleResourceMapper.deleteByRoleId(id);
						userRoleMapper.deleteByRoleId(id);
						//更新缓存权限
						Constents.operatorRoleCount.addAndGet(1);
						msgResult.put(0,"删除成功!");
					}
				}
			}
		}else{
			msgResult.put(1,"参数不全!");
		}
		return msgResult;
	}

	public int updateByPrimaryKey(SysRole record) {
		return sysRoleMapper.updateByPrimaryKey(record);
	}

	/****
	 * @param name
	 * @return
	 */
	public SysRole selectByName(String name) {
		List<SysRole> sysRoles = sysRoleMapper.selectByName(name);
		SysRole sysRoler = null;
		if (null != sysRoles && sysRoles.size() > 0) {
			sysRoler = sysRoles.get(0);
		}
		return sysRoler;
	}

	/***
	 * 分页查询
	 * 
	 * @param conditions
	 * @return
	 */
	public PageMsg<SysRole> findByPage(Map<String, String> conditions) {
		int pagenumber = Integer.valueOf(conditions.get(Constents.PAGENUMBER_)),
				pagesize = Integer.valueOf(conditions.get(Constents.PAGESIZE_));
		PageInfo<SysRole> page = PageHelper.startPage(pagenumber, pagesize)
				.doSelectPageInfo(() -> sysRoleMapper.findByPage(conditions));
		PageMsg<SysRole> pageMsg = new PageMsg<>();
		pageMsg.setTotal(page.getTotal());
		pageMsg.setRows(processView(page.getList()));
		return pageMsg;
	}

	private List<SysRole> processView(List<SysRole> sysRoles) {
		sysRoles.forEach(ev -> {
			String availableName = AvailableEnum.getName(ev.getAvailable());
			if (!Strings.isNullOrEmpty(availableName))
				ev.setAvailableName(availableName);
		});
		return sysRoles;
	}

	public SysRole selectByPrimaryKey(Long id) {
		return sysRoleMapper.selectByPrimaryKey(id);
	}
}
