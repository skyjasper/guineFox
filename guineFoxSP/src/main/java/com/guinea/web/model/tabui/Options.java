package com.guinea.web.model.tabui;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * shiky on 2016/5/16.
 */
public class Options implements Serializable {
    private static final long serialVersionUID = -3074216822236714572L;
    private String validType;//email
    private boolean on;
    private boolean off;

    public boolean isOff() {
        return off;
    }

    public String getValidType() {
        return validType;
    }

    public boolean isOn() {
        return on;
    }

    public void setOff(boolean off) {
        this.off = off;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
