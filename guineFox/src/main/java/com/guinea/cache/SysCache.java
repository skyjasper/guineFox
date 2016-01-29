package com.guinea.cache;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.guinea.datadb.mysql.entity.SysAccount;
import com.guinea.datadb.mysql.model.RoleRangeView;

import java.util.List;
import java.util.Map;

/**
 * Created by shiky on 2015/12/7.
 */
public class SysCache {

    /***
     * validateData
     * 验证码
     */
    public static Map<String, String> validataMap = Maps.newHashMap();

    /***
     * user info
     * loginName,user
     */
    private static Map<String, SysAccount> userMap = Maps.newConcurrentMap();

    private static List<RoleRangeView> roleRangeViewList = Lists.newArrayList();

    public static Map<String, SysAccount> getUserMap() {
        return userMap;
    }

    public static SysAccount getByUserName(String userName) {
        String key = "";
        if (!Strings.isNullOrEmpty(userName)) {
            key = userName;
        }
        return userMap.get(key);
    }

    public static void putUserMap(SysAccount sysAccount) {
        if (null != sysAccount) {
            String userName = sysAccount.getLoginname();
            if (!Strings.isNullOrEmpty(userName)) {
                userMap.put(sysAccount.getLoginname(), sysAccount);
            }
        }
    }

    public static SysAccount getUserMapByid(Long id){
        SysAccount sysAccount = new SysAccount();
        if (null != id) {
            for (Map.Entry<String, SysAccount> entry : userMap.entrySet()) {
                if ((null != entry && id.equals(entry.getValue().getId()))) {
                    sysAccount=entry.getValue();
                }
            }
        }
        return sysAccount;
    }

    public static void removeUserMap(Long id) {
        String longinname = "";
        if (null != id) {
            for (Map.Entry<String, SysAccount> entry : userMap.entrySet()) {
                if ((null != entry && id.equals(entry.getValue().getId()))) {
                    longinname = entry.getKey();
                }
            }
        }
        if (!Strings.isNullOrEmpty(longinname)) {
            userMap.remove(longinname);
        }
    }

    public static void setRoleRangeViewList(List<RoleRangeView> temp) {
        roleRangeViewList = temp;
    }

    public static List<RoleRangeView> getRoleRangeViewList(){
        return roleRangeViewList;
    }
}
