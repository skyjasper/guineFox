package com.guinea.web.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by shiky on 2015/12/22.
 */
public class OnLineUser implements Serializable{

	private static final long serialVersionUID = -3728319876784914678L;
	private String id;
    private String lastAccessTime;
    private String startTimestamp;
    private String username;
    private String hostIP;

    public OnLineUser() {
	}
    
    public String getId() {
        return id;
    }

    public String getLastAccessTime() {
        return lastAccessTime;
    }

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastAccessTime(String lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
