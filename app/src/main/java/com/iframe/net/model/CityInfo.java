package com.iframe.net.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityInfo {
    private String status;
    private String msg;
    private ArrayList<CityPriInfo> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<CityPriInfo> getResult() {
        return result;
    }

    public void setResult(ArrayList<CityPriInfo> result) {
        this.result = result;
    }
}
