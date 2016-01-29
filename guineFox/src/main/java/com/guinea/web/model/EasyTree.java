package com.guinea.web.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shiky on 2016/1/6.
 * @describe easyuitree
 */
public class EasyTree implements Serializable{

    private static final long serialVersionUID = -3123698990492218583L;

    private Long id;
    private String text;

    private String type;
    /***
     * open,closed
     */
    private String state;
    private Boolean checked;
    private String target;
    private Integer priority;
    private Long parentId;
    private Boolean animate;
    private List<EasyTree> children;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getState() {
        return state;
    }

    public List<EasyTree> getChildren() {
        return children;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setChildren(List<EasyTree> children) {
        this.children = children;
    }

    public String getTarget() {
        return target;
    }

    public Integer getPriority() {
        return priority;
    }

    public Long getParentId() {
        return parentId;
    }

    public Boolean getAnimate() {
        return animate;
    }

    public void setAnimate(Boolean animate) {
        this.animate = animate;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
