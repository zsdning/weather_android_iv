package com.iframe.net.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Administrator on 2016/7/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfo {
    private String status;
    private String msg;
    private WeatherPriInfo result;

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

    public WeatherPriInfo getResult() {
        return result;
    }

    public void setResult(WeatherPriInfo result) {
        this.result = result;
    }
}
