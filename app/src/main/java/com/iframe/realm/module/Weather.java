package com.iframe.realm.module;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by zsdning on 2016/7/16.
 */
public class Weather extends RealmObject{
    @PrimaryKey
    private String id;
    private String area;   //地区
    private String date;
    private String week;
    private String lunardate; //农历时间
    private String temp;  //当前温度
    private String lowtemp;  //最低温
    private String hightemp; //最高温
    private String state;   //晴、多云...
    private String windDirect;
    private String windPower;
    private String quality;  //空气质量
    private String humidity;  //湿度
    private String tip;  //建议

    private String stateTwo;  //明天状态
    private String lowtempTwo;
    private String hightempTwo;
    private String windDirectTwo;
    private String windPowerTwo;

    private String stateThree; //后天状态
    private String lowtempThree;
    private String hightempThree;
    private String windDirectThree;
    private String windPowerThree;

    //未来一周天气情况，不能采用普通的List
    private RealmList<NextDays> nextDays;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getLunardate() {
        return lunardate;
    }

    public void setLunardate(String lunardate) {
        this.lunardate = lunardate;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getStateTwo() {
        return stateTwo;
    }

    public void setStateTwo(String stateTwo) {
        this.stateTwo = stateTwo;
    }

    public String getLowtempTwo() {
        return lowtempTwo;
    }

    public void setLowtempTwo(String lowtempTwo) {
        this.lowtempTwo = lowtempTwo;
    }

    public String getHightempTwo() {
        return hightempTwo;
    }

    public void setHightempTwo(String hightempTwo) {
        this.hightempTwo = hightempTwo;
    }

    public String getWindDirectTwo() {
        return windDirectTwo;
    }

    public void setWindDirectTwo(String windDirectTwo) {
        this.windDirectTwo = windDirectTwo;
    }

    public String getWindPowerTwo() {
        return windPowerTwo;
    }

    public void setWindPowerTwo(String windPowerTwo) {
        this.windPowerTwo = windPowerTwo;
    }

    public String getStateThree() {
        return stateThree;
    }

    public void setStateThree(String stateThree) {
        this.stateThree = stateThree;
    }

    public String getLowtempThree() {
        return lowtempThree;
    }

    public void setLowtempThree(String lowtempThree) {
        this.lowtempThree = lowtempThree;
    }

    public String getHightempThree() {
        return hightempThree;
    }

    public void setHightempThree(String hightempThree) {
        this.hightempThree = hightempThree;
    }

    public String getWindDirectThree() {
        return windDirectThree;
    }

    public void setWindDirectThree(String windDirectThree) {
        this.windDirectThree = windDirectThree;
    }

    public String getWindPowerThree() {
        return windPowerThree;
    }

    public void setWindPowerThree(String windPowerThree) {
        this.windPowerThree = windPowerThree;
    }

    public RealmList<NextDays> getNextDays() {
        return nextDays;
    }

    public void setNextDays(RealmList<NextDays> nextDays) {
        this.nextDays = nextDays;
    }
}
