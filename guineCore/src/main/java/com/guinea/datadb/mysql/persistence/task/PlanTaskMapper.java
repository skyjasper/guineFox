package com.guinea.datadb.mysql.persistence.task;

import java.util.List;
import java.util.Map;

import com.guinea.datadb.mysql.entity.task.PlanTask;

/***
 * 计划任务
 */
public interface PlanTaskMapper {

    int deleteByPrimaryKey(Long id);

    int batchDeleteByPrimaryKey(List<Long> ids);

    int finishByPrimaryKey(List<Long> ids);

    int insert(PlanTask record);

    PlanTask selectByPrimaryKey(Long id);

    List<PlanTask> selectAll();

    int updateByPrimaryKey(PlanTask record);

    /****
     * 更新状态为过时,完成状态为挂起
     * @param ids
     * @return
     */
    int batchUpdateAlreadyPast(List<String> ids);

    /***
     * 更新状态为挂起,完成状态为挂起
     * @param ids
     * @return
     */
    int batchUpdateGuaqiPast(List<String> ids);

    /****
     * 分页查询
     */
    List<PlanTask> findByPage(Map<String, String> conditions);

    /***
     * 获取未完成的任务
     *
     * @param conditions
     * @return
     */
    List<PlanTask> selectByTask(Map<String, Object> conditions);
}