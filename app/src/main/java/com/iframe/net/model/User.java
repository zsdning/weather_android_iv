package com.iframe.net.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by zsdning on 2016/8/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int code;
    private String desc;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
