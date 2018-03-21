package com.guinea.datadb.mysql.entity.task;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 计划任务
 */
public class PlanTask implements Serializable{

    private static final long serialVersionUID = 1187959745287437997L;

    private Long id;

    /***
     * 用户id
     */
    private Long userId;

    /**
     * 执行类型
     *  0:每天;1:每月;2:定时;
     */
    private Short executeType;

    /***
     * 执行类型名称
     */
    private String executeTypeName;

    /***
     * 任务开始时间
     */
    private Date startTime;

    /***
     * 任务结束时间
     */
    private Date endTime;

    /***
     * 任务级别
     */
    private Short level;

    /***
     * 任务级别名称
     */
    private String levelName;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 任务内容
     */
    private String content;

    /**
     * 任务描述
     */
    private String discrible;

    /**
     *过时状态
     * 0/1/2 未过时/已过时/挂起
     */
    private Short isPast;

    /***
     * 过时状态名称
     */
    private String isPastName;

    /**
     *完成状态
     * 0/1/2 未完成/已完成/挂起
     */
    private Short isFinish;

    /***
     * 完成状态名称
     */
    private String isFinishName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getExecuteType() {
        return executeType;
    }

    public void setExecuteType(Short executeType) {
        this.executeType = executeType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getDiscrible() {
        return discrible;
    }
    
    public void setDiscrible(String discrible) {
        this.discrible = discrible == null ? null : discrible.trim();
    }

    public Short getIsPast() {
        return isPast;
    }

    public void setIsPast(Short isPast) {
        this.isPast = isPast;
    }

    public Short getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Short isFinish) {
        this.isFinish = isFinish;
    }

    public String getIsFinishName() {
        return isFinishName;
    }

    public String getExecuteTypeName() {
        return executeTypeName;
    }

    public String getLevelName() {
        return levelName;
    }

    public String getIsPastName() {
        return isPastName;
    }

    public void setExecuteTypeName(String executeTypeName) {
        this.executeTypeName = executeTypeName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setIsPastName(String isPastName) {
        this.isPastName = isPastName;
    }

    public void setIsFinishName(String isFinishName) {
        this.isFinishName = isFinishName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}