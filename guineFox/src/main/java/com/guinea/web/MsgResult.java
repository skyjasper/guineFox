package com.guinea.web;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * @author: shiky
 * @describle:
 * @dateTime: 2016/1/12
 */
public class MsgResult implements Serializable {

    private static final long serialVersionUID = -4860385383087905153L;

    public void put(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    private Integer key;
    private String value;

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
