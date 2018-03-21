package com.guinea.web.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * @author: shiky
 * @describle: 上传文件后缀限制
 * @dateTime: 2016/3/2
 */
public class Mime_types implements Serializable {
    private static final long serialVersionUID = 4476219903772474605L;
    private String title;
    private String extensions;

    public Mime_types() {
    }

    public Mime_types(String title, String extensions) {
        this.title = title;
        this.extensions = extensions;
    }

    public String getTitle() {
        return title;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
