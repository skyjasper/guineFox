package com.guinea.datadb.mongo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;

/**
 * @author: shiky
 * @describle:
 * @dateTime: 2016/2/26
 */
public class BaseMongoEntity implements Serializable{

    private static final long serialVersionUID = -1974176876733305953L;

    @Id
    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
