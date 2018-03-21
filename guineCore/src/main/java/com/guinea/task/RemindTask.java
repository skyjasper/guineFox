package com.guinea.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guinea.cache.SysCache;
import com.guinea.communications.netty.webSocket.Global;
import com.guinea.datadb.mysql.entity.task.PlanTask;
import com.guinea.util.date.JodatimeUtil;
import com.guinea.web.model.MessagePlanTask;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @title:
 * @describle:
 * @createBy" shikaiyuan
 * @date: 2016/6/28.
 */
public class RemindTask extends Thread {

    private static Logger logger = LoggerFactory.getLogger(RemindTask.class);

    public void run() {
        try {
            /***
             * 每月的，每天的，固定的(开始时间小于当前时间，结束时间大于等于当前时间，并且未完成)
             */
            //当前时间,加31分钟后时间
            DateTime nowTime = new DateTime(), afterTime = nowTime.plusMinutes(31), afterTime_ = nowTime.plusMinutes(46);
            //固定任务
            processGuding(SysCache.planTaskMapFinal, nowTime, new Interval(nowTime, afterTime));
            //定时任务
            processDingshi(SysCache.planTaskMapEvery, nowTime, afterTime_);
        } catch (JsonProcessingException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /***
     * 固定任务
     */
    private void processGuding(Map<Long, PlanTask> planTaskMapFinal, DateTime nowTime, Interval i) throws JsonProcessingException, InterruptedException {
        if (null != planTaskMapFinal && planTaskMapFinal.size() > 0) {
            for (Map.Entry<Long, PlanTask> entry : planTaskMapFinal.entrySet()) {
                PlanTask planTask = entry.getValue();
                //没有完成并且没有过时的
                if(planTask.getIsFinish().equals(Short.valueOf("0"))&&planTask.getIsPast().equals(Short.valueOf("0"))){
                    DateTime startTime = new DateTime(planTask.getStartTime());
                    if (i.contains(startTime)) {
                        if (null != planTask.getEndTime() && nowTime.isAfter(new DateTime(planTask.getEndTime()))) {
                            continue;
                        }
                        String message = changeMessage(planTask);
                        logger.debug("message_final:" + message);
                        sendMessageToClient(message);
                    }
                }
            }
        }
    }

    /***
     * 定时任务处理
     *
     * @param planTaskMapEvery
     * @param nowTime
     */
    private void processDingshi(Map<Long, PlanTask> planTaskMapEvery, DateTime nowTime, DateTime afterTime_) throws JsonProcessingException, InterruptedException {
        if (null != planTaskMapEvery && planTaskMapEvery.size() > 0) {
            Interval i_m = new Interval(nowTime, afterTime_);
            for (Map.Entry<Long, PlanTask> entry : planTaskMapEvery.entrySet()) {
                PlanTask planTask = entry.getValue();
                //没有完成并且没有过时的
                if(planTask.getIsFinish().equals(Short.valueOf("0"))&&planTask.getIsPast().equals(Short.valueOf("0"))){
                    Short type = planTask.getExecuteType();
                    DateTime startTime = new DateTime(planTask.getStartTime());
                    if (null != planTask.getEndTime() && nowTime.isAfter(new DateTime(planTask.getEndTime()))) {
                        continue;
                    }
                    if (type.equals(Short.valueOf("1"))) {//每月
                        String m_yy = afterTime_.toString("yyyy-MM-");
                        String m_mm_dd = startTime.toString("dd HH:mm:ss");
                        DateTime temp = JodatimeUtil.getDateTimeForStr(m_yy + m_mm_dd, JodatimeUtil.fmtSS);
                        if (i_m.contains(temp)) {
                            String message = changeMessage(planTask);
                            sendMessageToClient(message);
                            logger.debug("message_month:" + message);
                        }
                    } else if (type.equals(Short.valueOf("0"))) { //每日
                        String m_dd = afterTime_.toString("yyyy-MM-dd");
                        String m_hh = startTime.toString(" HH:mm:ss");
                        DateTime temp = JodatimeUtil.getDateTimeForStr(m_dd + m_hh, JodatimeUtil.fmtSS);
                        if (i_m.contains(temp)) {
                            String message = changeMessage(planTask);
                            sendMessageToClient(message);
                            logger.debug("message_month:" + message);
                        }
                    }
                }
            }
        }
    }

    //转化为json 字符串
    private String changeMessage(PlanTask planTask) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MessagePlanTask messagePlanTask = new MessagePlanTask();
        messagePlanTask.setContent(planTask.getContent());
        messagePlanTask.setTitle(planTask.getKeyword());
        messagePlanTask.setType(planTask.getLevel());
        messagePlanTask.setTime(new DateTime(planTask.getStartTime()).toString(JodatimeUtil.fmtSS));
        messagePlanTask.setUserId(planTask.getUserId());
        return objectMapper.writeValueAsString(messagePlanTask);
    }

    /***
     * 发送消息到客户端
     */
    private void sendMessageToClient(String msg) throws InterruptedException {
//        发送消息到客户端
        TextWebSocketFrame tws = new TextWebSocketFrame(msg);
        // 群发
        Global.group.writeAndFlush(tws);
        Thread.sleep(1866);
    }
}
