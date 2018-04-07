package com.iframe.realm.module;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2016/10/10.
 */
public class NextDays extends RealmObject implements Serializable{
    private String date;
    private String state;
    private String lowtemp;
    private String hightemp;
    private String windDirect;
    private String windPower;

    public NextDays() {
    }

    public NextDays(String date, String state, String lowtemp, String hightemp, String windDirect, String windPower) {
        this.date = date;
        this.state = state;
        this.lowtemp = lowtemp;
        this.hightemp = hightemp;
        this.windDirect = windDirect;
        this.windPower = windPower;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLowtemp() {
        return lowtemp;
    }

    public void setLowtemp(String lowtemp) {
        this.lowtemp = lowtemp;
    }

    public String getHightemp() {
        return hightemp;
    }

    public void setHightemp(String hightemp) {
        this.hightemp = hightemp;
    }

    public String getWindDirect() {
        return windDirect;
    }

    public void setWindDirect(String windDirect) {
        this.windDirect = windDirect;
    }

    public String getWindPower() {
        return windPower;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

}
