package com.iframe.net.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/3.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataInfo implements Serializable{
    private String id;

    public DataInfo(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
