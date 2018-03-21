package com.guinea.web.model.tabui;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 显示个人信息,节点拼装
 * shiky on 2016/5/16.
 */
public class InfoNode implements Serializable {
    private static final long serialVersionUID = -4404546404139201723L;

    public InfoNode() {}

    public InfoNode(String name, String value) {
        this.name=name;
        this.value=value;
    }

    public InfoNode(String name, String value,String group) {
        this.name=name;
        this.value=value;
        this.group=group;
    }

    private String name;
    private String value;
    private String group;
    private Editor editor;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getGroup() {
        return group;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
