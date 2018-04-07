package com.iframe.net.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Administrator on 2016/7/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDaily {
    private String date;
    private String week;
    private String sunrise;
    private String sunset;
    private DailyNight night;
    private DailyDay day;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DailyNight getNight() {
        return night;
    }

    public void setNight(DailyNight night) {
        this.night = night;
    }

    public DailyDay getDay() {
        return day;
    }

    public void setDay(DailyDay day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
