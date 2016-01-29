package com.guinea.profession;

import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.persistence.SysRoleMapper;
import com.guinea.task.UserPageTask;
import com.guinea.util.Constents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    public void initTask() {
        loadRangeRole();
        userPageTask();
        rangeRoleTask();
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
