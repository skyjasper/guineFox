package com.guinea.content;

import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.entity.SysAccount;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by shiky on 2015/12/8.
 */
public class SysProcess {
    public static SysAccount checkUserIsBlack(String userName) {
        SysAccount sysAccount = SysCache.getByUserName(userName);
        if (null == sysAccount) {
            Map<String, SysAccount> sysAccountMap = SysCache.getUserMap();
            for (Map.Entry<String, SysAccount> entry : sysAccountMap.entrySet()) {
                if (StringUtils.equals(entry.getValue().getPhonenumber(), userName)) {
                    sysAccount = entry.getValue();
                    break;
                }
            }
        }
        return sysAccount;
    }
}
