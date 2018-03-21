package com.guinea.profession;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.guinea.cache.SysCache;
import com.guinea.communications.netty.webSocket.PlanTaskNettyServer;
import com.guinea.datadb.mysql.entity.task.PlanTask;
import com.guinea.datadb.mysql.persistence.SysRoleMapper;
import com.guinea.datadb.mysql.persistence.task.PlanTaskMapper;
import com.guinea.dic.ExecuteTypeEnum;
import com.guinea.task.RemindTask;
import com.guinea.task.UserPageTask;
import com.guinea.util.Constents;
import com.guinea.util.date.JodatimeUtil;

/**
 * @author: shiky
 * @describle:初始化线程
 * @dateTime: 2016/1/14
 */
@Component("taskComponent")
public class TaskComponent {

    private static Logger logger = LoggerFactory.getLogger(TaskComponent.class);

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private PlanTaskMapper planTaskMapper;

    public void initTask() {
        loadRangeRole();
        userPageTask();
        rangeRoleTask();
        //刷刷结算
//        cardTransactratePayment();
        //任务提醒
        planTaskMind();
    }


    /***
     * 初始化计划任务
     */
    private void initPlanTask() {
        DateTime minusa = new DateTime().minusMinutes(30);
        Map<String, Object> conditions = Maps.newHashMap(),
                conditionst = Maps.newHashMap();
        conditions.put("nowtime", minusa.toString(JodatimeUtil.fmtSS));
        conditions.put("is_finish", "0");//未完成
        conditions.put("is_past", "0");//未过时
        conditions.put("execute_type", "2");//定时

        conditionst.put("execute_typet", Arrays.asList(0, 1));//每天、每月
        conditionst.put("is_finish", "0");//未完成
        conditionst.put("is_past", "0");//未过时
        List<PlanTask> planTasks = planTaskMapper.selectByTask(conditions),
                planTaskst = planTaskMapper.selectByTask(conditionst);
        if (null != planTasks && planTasks.size() > 0) {
            for (PlanTask planTask : planTasks) {
                SysCache.planTaskMapFinal.put(planTask.getId(), planTask);
            }
        }
        if (null != planTaskst && planTaskst.size() > 0) {
            for (PlanTask planTask : planTaskst) {
                SysCache.planTaskMapEvery.put(planTask.getId(), planTask);
            }
        }
    }

    /**
     * 每天早上2点执行一次
     * 需要执行的任务
     */
    private void executeEightAtNightPerDay() {
        initPlanTask();
        DateTime nowTime = new DateTime();
        long oneDay = 24 * 60 * 60 * 1000;
        String nowString = nowTime.toString("yyyy-MM-dd") + " 2:00:00";
        DateTime temp = JodatimeUtil.getDateTimeForStr(nowString, JodatimeUtil.fmtSS);
        long initDelay = JodatimeUtil.getTimeMillis(nowTime, temp);
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        initPlanTask();
                    }
                },
                initDelay,
                oneDay,
                TimeUnit.MILLISECONDS);
    }

    /**
     * 每天早上3点执行一次
     * 任务自动过期
     */
    private void executeTaskAlready() {
        initPlanTask();
        DateTime nowTime = new DateTime();
        long oneDay = 24 * 60 * 60 * 1000;
        String nowString = nowTime.toString("yyyy-MM-dd") + " 3:00:00";
        DateTime temp = JodatimeUtil.getDateTimeForStr(nowString, JodatimeUtil.fmtSS);
        long initDelay = JodatimeUtil.getTimeMillis(nowTime, temp);
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        batchguaqi();
                    }
                },
                initDelay,
                oneDay,
                TimeUnit.MILLISECONDS);


    }

    /***
     * 自动过期或者挂起
     */
    private void batchguaqi() {
        DateTime nowTime = new DateTime();
        Map<String, Object> conditions = Maps.newHashMap();
        conditions.put("is_finish", "0");//未完成
        conditions.put("is_past", "0");//未过时
        List<PlanTask> planTasks = planTaskMapper.selectByTask(conditions);
        List<String> already = Lists.newArrayList(), guaqi = Lists.newArrayList();
        if (null != planTasks && planTasks.size() > 0) {
            for (PlanTask planTask : planTasks) {
                Date endTime = planTask.getEndTime();
                if (null != endTime) {
                    //已经过期
                    if (nowTime.isAfter(new DateTime(endTime))) {
                        already.add(planTask.getId() + "");
                    }
                } else {
                    //定时
                    if (planTask.getExecuteType().equals(ExecuteTypeEnum.TIMING.getCode())) {
                        if (nowTime.isAfter(new DateTime(planTask.getStartTime()))) {
                            //标记挂起
                            guaqi.add(planTask.getId() + "");
                        }
                    }
                }
            }
        }
        if (null != already && already.size() > 0) {
            int i = planTaskMapper.batchUpdateAlreadyPast(already);
            logger.debug("====执行定时过期任务:" + i + "=====");
        }

        if (null != guaqi && guaqi.size() > 0) {
            int i = planTaskMapper.batchUpdateGuaqiPast(guaqi);
            logger.debug("====执行定时挂起任务:" + i + "=====");
        }
    }


    /***
     * 启动服务端监听线程
     */
    private void planTaskMind() {

        //初始化计划任务加载
        executeEightAtNightPerDay();

        //过时任务自动挂起线程
        executeTaskAlready();

        //启动服务端监听线程
        ScheduledExecutorService serverSocket = Executors.newScheduledThreadPool(1);
        serverSocket.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                new PlanTaskNettyServer(Constents.netty_web_socket_port).run();
            }
        }, 0, 3, TimeUnit.DAYS);

        /***
         * 定时任务，消息提醒任务
         */
        ScheduledExecutorService remindTask = Executors.newScheduledThreadPool(1);
        remindTask.scheduleWithFixedDelay(new RemindTask(), 6, 32, TimeUnit.SECONDS);
    }


    /***
     * 角色权限管理任务
     */
    private void rangeRoleTask() {
        ScheduledExecutorService rangeRole = Executors.newScheduledThreadPool(1);
        rangeRole.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                if (Constents.operatorRoleCount.get() > 0) {
                    SysCache.setRoleRangeViewList(sysRoleMapper.selectByRoleRange());
                    Constents.operatorRoleCount.addAndGet(-1);
                    logger.debug("===执行角色权限全量更新线程===");
                }
            }
        }, 12, 8, TimeUnit.SECONDS);
    }

    /***
     * 用户分页条数管理任务
     */
    private void userPageTask() {
        ScheduledExecutorService executorUserPage = Executors.newScheduledThreadPool(1);
        executorUserPage.scheduleWithFixedDelay(new UserPageTask(), 8, 3, TimeUnit.MINUTES);
    }

    /***
     * 角色权限初始化加载
     */
    private void loadRangeRole() {
        SysCache.setRoleRangeViewList(sysRoleMapper.selectByRoleRange());
    }
}
