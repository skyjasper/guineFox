package com.guinea.shiro.web;

import com.guinea.content.SysProcess;
import com.guinea.datadb.mysql.entity.SysAccount;
import com.guinea.util.Constents;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by shiky on 2015/12/3.
 */
public class SysUserFilter extends PathMatchingFilter {
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal(),temp ="";
        SysAccount sysAccount = SysProcess.checkUserIsBlack(username);
        if(null!=sysAccount){
            temp=sysAccount.getLoginname();
        }
        request.setAttribute(Constents.CURRENT_USER,temp);
        return true;
    }
}
