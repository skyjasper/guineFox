package com.guinea.profession.service.sys;

import com.guinea.datadb.mysql.entity.SysOrganization;
import com.guinea.datadb.mysql.entity.UserOrganization;
import com.guinea.datadb.mysql.persistence.SysOrganizationMapper;
import com.guinea.datadb.mysql.persistence.UserOrganizationMapper;
import com.guinea.web.MsgResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: shiky
 * @describle:
 * @dateTime: 2016/1/11
 */
@Service("sysOrganizationService")
public class SysOrganizationService {

    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Autowired
    private UserOrganizationMapper userOrganizationMapper;

    public MsgResult deleteByPrimaryKey(Long id) {
        MsgResult msgResult = new MsgResult();
        if (null != id) {
            if (id == 1) {
                msgResult.put(2, "根资源不能删除!");
            } else {
                List<UserOrganization> userOrganizations = userOrganizationMapper.selectByOrgId(id);
                if (null != userOrganizations && userOrganizations.size() > 0) {
                    msgResult.put(3, "组织下存在用户无法删除!");
                } else {
                    sysOrganizationMapper.deleteByPrimaryKey(id);
                    userOrganizationMapper.deleteByOrgId(id);
                    msgResult.put(0, "删除成功!");
                }
            }
        } else {
            msgResult.put(1, "参数不全!");
        }
        return msgResult;
    }

    public int insert(SysOrganization record) {
        return sysOrganizationMapper.insert(record);
    }

    public SysOrganization selectByPrimaryKey(Long id) {
        return sysOrganizationMapper.selectByPrimaryKey(id);
    }

    public List<SysOrganization> selectAll() {
        return sysOrganizationMapper.selectAll();
    }

    public int updateByPrimaryKey(SysOrganization record) {
        return sysOrganizationMapper.updateByPrimaryKey(record);
    }

    public List<SysOrganization> selectByNameForPId(SysOrganization record) {
        return sysOrganizationMapper.selectByNameForPId(record);
    }
}
