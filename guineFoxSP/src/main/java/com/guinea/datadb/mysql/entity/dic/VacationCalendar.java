package com.guinea.datadb.mysql.entity.dic;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 假期
 */
public class VacationCalendar implements Serializable{
    private static final long serialVersionUID = 7162228319587156920L;

    private Long id;

    /**
     * 1:假期
     * 3:最晚交易时间
     */
    private Short type;

    /***
     * 用数字表示日期/时间
     */
    private Integer date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}