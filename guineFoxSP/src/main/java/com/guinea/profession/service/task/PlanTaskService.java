package com.guinea.profession.service.task;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.entity.card.CardTransactrateInfo;
import com.guinea.datadb.mysql.entity.task.PlanTask;
import com.guinea.datadb.mysql.persistence.task.PlanTaskMapper;
import com.guinea.profession.TaskComponent;
import com.guinea.util.Constents;
import com.guinea.util.date.JodatimeUtil;
import com.guinea.web.PageMsg;
import com.guinea.web.model.User;
import org.apache.shiro.SecurityUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @title: 计划任务
 * @author: shiky
 * @describe:
 * @date: 2016/6/24
 */
@Service("planTaskService")
public class PlanTaskService {

    private static Logger logger = LoggerFactory.getLogger(PlanTaskService.class);

    @Autowired
    private PlanTaskMapper planTaskMapper;


    public int deleteByPrimaryKey(List<Long> ids) {
        return planTaskMapper.batchDeleteByPrimaryKey(ids);
    }

    public int finishByPrimaryKey(List<Long> ids) {
        return planTaskMapper.finishByPrimaryKey(ids);
    }

    public int insert(PlanTask record) {
        return planTaskMapper.insert(record);
    }

    public PlanTask selectByPrimaryKey(Long id) {
        return planTaskMapper.selectByPrimaryKey(id);
    }

    public List<PlanTask> selectAll() {
        return planTaskMapper.selectAll();
    }

    public int updateByPrimaryKey(PlanTask record) {
        return planTaskMapper.updateByPrimaryKey(record);
    }


    /***
     * 分页查询
     *
     * @param conditions
     * @return
     */
    public PageMsg<PlanTask> findByPage(Map<String, String> conditions) {
        PageInfo<PlanTask> page = PageHelper
                .startPage(Integer.valueOf(conditions.get(Constents.PAGENUMBER_)),
                        Integer.valueOf(conditions.get(Constents.PAGESIZE_)))
                .doSelectPageInfo(() -> planTaskMapper.findByPage(conditions));
        PageMsg<PlanTask> pageMsg = new PageMsg<>();
        pageMsg.setTotal(page.getTotal());
        pageMsg.setRows(processView(page.getList()));
        return pageMsg;
    }

    private List<PlanTask> processView(List<PlanTask> planTasks) {
        planTasks.forEach(ev -> {
            ev.setLevelName(SysCache.getLevelMap().get(ev.getLevel() + ""));
            ev.setExecuteTypeName(SysCache.getExecuteTypeMap().get(ev.getExecuteType() + ""));
            ev.setIsFinishName(SysCache.getFinishMap().get(ev.getIsFinish() + ""));
            ev.setIsPastName(SysCache.getPastMap().get(ev.getIsPast() + ""));
        });
        return planTasks;
    }

}
