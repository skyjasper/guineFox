package com.guinea.profession;

import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.entity.SysAccount;
import com.guinea.datadb.mysql.persistence.SysAccountMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shiky on 2015/12/8.
 */
@Service("systemInit")
public class SystemInit implements InitializingBean {

    @Autowired
    private SysAccountMapper sysAccountMapper;

    @Autowired
    private TaskComponent taskComponent;

    @Override
    public void afterPropertiesSet() throws Exception {
        loadIng();
        taskComponent.initTask();
    }

    private void loadIng() {
        loadUser();
    }

    /***
     * 加载用户
     */
    private void loadUser() {
        List<SysAccount> sysAccounts = sysAccountMapper.selectByNoDelete();
        if (null != sysAccounts && sysAccounts.size() > 0) {
            sysAccounts.forEach(ev -> {
                SysCache.putUserMap(ev);
            });
        }
    }

}
