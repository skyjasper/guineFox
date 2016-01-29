package com.guinea.profession.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guinea.datadb.mysql.entity.SysAccountDetail;
import com.guinea.datadb.mysql.persistence.SysAccountDetailMapper;

/**
 * Created by shiky on 2015/12/8.
 */
@Service("sysAccountDetailService")
public class SysAccountDetailService {

    @Autowired
    private SysAccountDetailMapper accountDetailMapper;

    public void save(SysAccountDetail accountDetail) {
        accountDetailMapper.insert(accountDetail);
    }

    public List<SysAccountDetail> selectByObj(SysAccountDetail sysAccountDetail) {
        return accountDetailMapper.selectByObj(sysAccountDetail);
    }

    public int updateByPrimaryKey(SysAccountDetail sysAccountDetail){
        return accountDetailMapper.updateByPrimaryKey(sysAccountDetail);
    }
    
    public SysAccountDetail selectByPrimaryKey(Long id){
    	return accountDetailMapper.selectByPrimaryKey(id);
    }
}
