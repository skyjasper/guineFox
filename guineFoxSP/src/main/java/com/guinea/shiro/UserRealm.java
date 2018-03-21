package com.guinea.shiro;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.guinea.cache.SysCache;
import com.guinea.content.SysProcess;
import com.guinea.datadb.mysql.entity.SysAccount;
import com.guinea.datadb.mysql.model.RoleRangeView;
import com.guinea.web.model.User;

/**
 * Created by shiky on 2015/12/3.
 */
public class UserRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = null;
        if (null != user) {
            authorizationInfo = new SimpleAuthorizationInfo();
            Set<String> roles = Sets.newHashSet(), permissions = Sets.newHashSet();
            processRoleRange(roles, permissions, user.getLoginname());
            // 添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
            authorizationInfo.setRoles(roles);
            // 添加权限
            authorizationInfo.setStringPermissions(permissions);
            logger.debug("username:" + user.getLoginname() + "  || roles:" + roles.toString());
            logger.debug("permissions:" + permissions.toString());
        }
        return authorizationInfo;
    }

    private void processRoleRange(Set<String> roles, Set<String> permissions, String loginname) {
        List<RoleRangeView> roleRangeViews = SysCache.getRoleRangeViewList();
        if (null != roleRangeViews && roleRangeViews.size() > 0) {
            for (RoleRangeView roleRangeView : roleRangeViews) {
                if (null != roleRangeView && StringUtils.equals(roleRangeView.getLoginname(), loginname)) {
                    String roleName = roleRangeView.getRolename(), permission = roleRangeView.getPermission();
                    if (StringUtils.isNotBlank(roleName)) {
                        roles.add(roleName);
                    }
                    if (StringUtils.isNotBlank(permission)) {
                        permissions.add(permission);
                    }
                }
            }
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
        String userName = passwordToken.getUsername();
        SysAccount sysAccount = SysProcess.checkUserIsBlack(userName);
        SimpleAuthenticationInfo authenticationInfo = null;
        if (null != sysAccount) {
            User user = new User(sysAccount);
            // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
            authenticationInfo = new SimpleAuthenticationInfo(user, sysAccount.getPwd(), // 密码
                    ByteSource.Util.bytes(sysAccount.getSalt()), // salt=username+salt
                    getName() // realm name
            );
        } else {
            throw new UnknownAccountException();//没找到帐号
        }
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
