package com.guinea.web.controller.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.entity.task.PlanTask;
import com.guinea.dic.ExecuteTypeEnum;
import com.guinea.profession.service.task.PlanTaskService;
import com.guinea.util.date.JodatimeUtil;
import com.guinea.web.BaseFormController;
import com.guinea.web.controller.trace.TracePointController;
import com.guinea.web.model.User;

/**
 * @title: 计划任务
 * @author: shiky
 * @describe:
 * @date: 2016/6/24
 */
@Controller
public class PlanTaskController extends BaseFormController {

    private static Logger logger = LoggerFactory.getLogger(TracePointController.class);

    @Autowired
    private PlanTaskService planTaskService;

    /***
     * 测试web Socket
     *
     * @return
     */
    @RequestMapping(value = "/task/taskSocketTest", method = RequestMethod.GET)
    public String test_socket() {
        logger.debug("taskSocketTest start:");
        return "task/taskSocketTest";
    }

    /***
     * 测试location_histroy
     *
     * @return
     */
    @RequestMapping(value = "/task/location_histroy", method = RequestMethod.GET)
    public String test_location_history() {
        logger.debug("location_histroy start:");
        return "task/location_histroy";
    }

    @RequestMapping(value = "/task/task_plan_remind_list", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        showList(model, logger.hashCode());
        return "task/task_plan_remind_list";
    }

    @RequestMapping(value = "/task/task_plan_remind_list", method = {RequestMethod.POST})
    public void findBpage(HttpServletRequest request, HttpServletResponse response) {
        try {
            showDataGrid(request, response, objectMapper
                    .writeValueAsString(
                            planTaskService.findByPage(processParam(request.getParameterMap(), logger.hashCode()))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/task/task_plan_remind_add", method = RequestMethod.GET)
    public String show_add(ModelMap model) {
        processShowSelect(model);
        return "task/task_plan_remind_add";
    }

    @RequestMapping(value = "/task/task_plan_remind_add", method = {RequestMethod.POST})
    public void process_add(HttpServletRequest request, HttpServletResponse response) {
        String executeType_s = StringUtils.trim(request.getParameter("executeType")),
                startTime_s = StringUtils.trim(request.getParameter("startTime")),
                endTime_s = StringUtils.trim(request.getParameter("endTime")),
                keyword_s = StringUtils.trim(request.getParameter("keyword")),
                content_s = StringUtils.trim(request.getParameter("content")),
                discrible_s = StringUtils.trim(request.getParameter("discrible")),
                level_s = StringUtils.trim(request.getParameter("level"));
        if (StringUtils.isNotBlank(executeType_s) && StringUtils.isNumeric(executeType_s)
                && StringUtils.isNotBlank(level_s) && StringUtils.isNumeric(level_s) &&
                StringUtils.isNotBlank(startTime_s) && StringUtils.isNotBlank(keyword_s)) {
            PlanTask planTask = new PlanTask();
            planTask.setStartTime(JodatimeUtil.getDateForStr(startTime_s, JodatimeUtil.fmtSS));
            if (StringUtils.isNotBlank(endTime_s)) {
                planTask.setEndTime(JodatimeUtil.getDateForStr(endTime_s, JodatimeUtil.fmtSS));
            }
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            planTask.setKeyword(keyword_s);
            planTask.setContent(content_s);
            planTask.setDiscrible(discrible_s);
            planTask.setIsFinish((short) 0);
            planTask.setIsPast((short) 0);
            planTask.setExecuteType(Short.parseShort(executeType_s));
            planTask.setLevel(Short.parseShort(level_s));
            planTask.setUserId(user.getId());
            planTaskService.insert(planTask);
            syncCache(planTask);
            succMesg(request, response);
        } else {
            errorMsg(request, response);
        }
    }

    private void syncCache(PlanTask planTask) {
        if (planTask.getExecuteType().equals(ExecuteTypeEnum.TIMING.getCode())) {
            SysCache.planTaskMapFinal.put(planTask.getId(), planTask);
        } else {
            SysCache.planTaskMapEvery.put(planTask.getId(), planTask);
        }
    }

    /***
     * 删除
     *
     * @param ids
     * @param request
     * @param response
     */
    @RequestMapping(value = "/task/task_plan_remind_delete", method = {RequestMethod.POST})
    public void processDelete(String ids, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(ids) && StringUtils.indexOf(ids, ",") > 0) {
            String arryIds[] = StringUtils.split(ids, ",");
            List<Long> listId = Lists.newArrayList();
            for (String strId : arryIds) {
                Long id = Long.valueOf(strId);
                listId.add(id);
                SysCache.planTaskMapFinal.remove(id);
                SysCache.planTaskMapEvery.remove(id);
            }
            planTaskService.deleteByPrimaryKey(listId);
            succMesg(request, response, "删除成功!");
        }
    }

    /***
     * 完成
     *
     * @param ids
     * @param request
     * @param response
     */
    @RequestMapping(value = "/task/task_plan_remind_finish", method = {RequestMethod.POST})
    public void processFinish(String ids, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(ids) && StringUtils.indexOf(ids, ",") > 0) {
            String arryIds[] = StringUtils.split(ids, ",");
            List<Long> listId = Lists.newArrayList();
            for (String strId : arryIds) {
                Long id = Long.valueOf(strId);
                listId.add(id);
                PlanTask planTaskf = SysCache.planTaskMapFinal.get(id);
                if (null != planTaskf) {
                    SysCache.planTaskMapFinal.remove(id);
                }
                PlanTask planTaske = SysCache.planTaskMapEvery.get(id);
                if (null != planTaske) {
                    SysCache.planTaskMapEvery.remove(id);
                }
            }
            planTaskService.finishByPrimaryKey(listId);
            succMesg(request, response);
        } else {
            errorMsg(request, response, "参数不全!");
        }
    }

    private void processShowSelect(ModelMap model) {
        model.put("executeTypeMap", SysCache.getExecuteTypeMap());
        model.put("levelMap", SysCache.getLevelMap());
    }


    @RequestMapping(value = "/loginf", method = RequestMethod.GET)
    public String show_hello( HttpServletRequest request) {
        System.out.println("--我进来了--");
        request.setAttribute("path",request.getContextPath());
        return "/loginf";
    }


}
