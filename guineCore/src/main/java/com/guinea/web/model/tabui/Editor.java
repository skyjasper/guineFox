package com.guinea.web.model.tabui;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * shiky on 2016/5/16.
 */
public class Editor implements Serializable{
    private static final long serialVersionUID = -7671166244413671470L;
    /**
     * checkbox,validatebox
     */
    private String type;
    private Options options;

    public Options getOptions() {
        return options;
    }

    public String getType() {
        return type;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
