package com.guinea.web.model;

import com.guinea.dic.LevelEnum;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * @title 提醒消息
 * @author: shiky
 * @describe:
 * @date: 2016/7/8
 */
public class MessagePlanTask implements Serializable {

    private static final long serialVersionUID = 5493724983875016287L;

    private Long userId;
    /***
     * 0,游离;1,普通;2,重要;
     * 3,重大;
     */
    private Short type;

    private String title;

    private String content;

    private String time;

    public Short getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
