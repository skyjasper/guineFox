package com.guinea.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.guinea.datadb.mysql.entity.SysAccount;
import com.guinea.datadb.mysql.entity.task.PlanTask;
import com.guinea.datadb.mysql.model.RoleRangeView;
import com.guinea.dic.ExecuteTypeEnum;
import com.guinea.dic.FinishEnum;
import com.guinea.dic.LevelEnum;
import com.guinea.dic.PastEnum;

/**
 * Created by shiky on 2015/12/7.
 */
public class SysCache {

    private static Logger logger = LoggerFactory.getLogger(SysCache.class);

    /***
     * user info
     * loginName,user
     */
    private static Map<String, SysAccount> userMap;

    //角色权限
    private static List<RoleRangeView> roleRangeViewList;

    /****
     * 过时状态,执行类型,级别,完成状态
     */
    private static Map<String, String>
            pastMap = null,
            executeTypeMap = null,
            levelMap = null,
            finishMap = null;
//    com.mysql.cj.jdbc.Driver

    //------------------------public----------------------

    //    //计划任务：固定日期，定时
    public static Map<Long, PlanTask> planTaskMapFinal, planTaskMapEvery;

    //客户端发送消息
//    public static BlockingQueue <String> messagePlanTasks;

    //假期
    public static List<Integer> vacationList;

    /***
     * validateData
     * 验证码
     */
    public static Map<String, String> validataMap;

    public static void initLoading() {
        roleRangeViewList = Lists.newArrayList();
        vacationList = Lists.newArrayList();
        validataMap = Maps.newHashMap();
        userMap = Maps.newConcurrentMap();
        pastMap = Maps.newHashMap();
        executeTypeMap = Maps.newHashMap();
        levelMap = Maps.newHashMap();
        finishMap = Maps.newHashMap();
        planTaskMapFinal = Maps.newConcurrentMap();
        planTaskMapEvery = Maps.newConcurrentMap();
//        messagePlanTasks =  Queues.newLinkedBlockingQueue();
        logger.debug("-----");
        initDicData();
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> abc = Queues.newLinkedBlockingQueue();
        abc.put("rrr");
        System.out.println(abc.size());
        String str = abc.take();
        System.out.println("str:" + str);
        System.out.println(abc.size());
    }

    /**
     * 初始化数据字典
     */
    private static void initDicData() {
        for (PastEnum c : PastEnum.values()) {
            pastMap.put(c.getCode() + "", c.getName());
        }
        for (ExecuteTypeEnum c : ExecuteTypeEnum.values()) {
            executeTypeMap.put(c.getCode() + "", c.getName());
        }
        for (LevelEnum c : LevelEnum.values()) {
            levelMap.put(c.getCode() + "", c.getName());
        }
        for (FinishEnum c : FinishEnum.values()) {
            finishMap.put(c.getCode() + "", c.getName());
        }
    }

    //过时状态
    public static Map<String, String> getPastMap() {
        return pastMap;
    }

    //执行类型
    public static Map<String, String> getExecuteTypeMap() {
        return executeTypeMap;
    }

    //级别
    public static Map<String, String> getLevelMap() {
        return levelMap;
    }

    //完成状态
    public static Map<String, String> getFinishMap() {
        return finishMap;
    }

    //用户
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

    public static SysAccount getUserMapByid(Long id) {
        SysAccount sysAccount = new SysAccount();
        if (null != id) {
            for (Map.Entry<String, SysAccount> entry : userMap.entrySet()) {
                if ((null != entry && id.equals(entry.getValue().getId()))) {
                    sysAccount = entry.getValue();
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

    public static List<RoleRangeView> getRoleRangeViewList() {
        return roleRangeViewList;
    }

}
