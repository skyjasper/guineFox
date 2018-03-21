package com.guinea.task;

import com.guinea.util.Constents;
import org.joda.time.DateTime;

import java.util.Map;

/****
 * @author shiky
 * @describe 清理userpage
 */
public class UserPageTask implements Runnable {

    public void run() {
        Map<String, Map<Integer, Integer>> userPage = Constents.userPage;
        Map<String, DateTime> operatorPageMap = Constents.operatorPageMap;
        if (userPage.size() > 300) {
            userPage.clear();
        } else {
            //清理超过3分钟没有使用的缓存
            if (null != userPage && userPage.size() > 0 && operatorPageMap != null && operatorPageMap.size() > 0) {
                DateTime nowDateTime = new DateTime(), afTime = nowDateTime.minus(3);
                for (Map.Entry<String, DateTime> entry : operatorPageMap.entrySet()) {
                    if (afTime.isAfter(entry.getValue())) {
                        operatorPageMap.remove(entry.getKey());
                    }
                }
            }
        }
    }
}
