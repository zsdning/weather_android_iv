package com.iframe.net.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherPriInfo {
    private String city;
    private String cityid;
    private String citycode;
    private String date;
    private String week;
    private String weather;
    private String temp;
    private String temphigh;
    private String templow;
    private String img;
    private String humidity;
    private String pressure;
    private String windspeed;
    private String winddirect;
    private String windpower;
    private String updatetime;
    private ArrayList<WeatherIndex> index;
    private WeatherAqi aqi;
    private ArrayList<WeatherDaily> daily;
    private ArrayList<WeatherHourly> hourly;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
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

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemphigh() {
        return temphigh;
    }

    public void setTemphigh(String temphigh) {
        this.temphigh = temphigh;
    }

    public String getTemplow() {
        return templow;
    }

    public void setTemplow(String templow) {
        this.templow = templow;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getWinddirect() {
        return winddirect;
    }

    public void setWinddirect(String winddirect) {
        this.winddirect = winddirect;
    }

    public String getWindpower() {
        return windpower;
    }

    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public ArrayList<WeatherIndex> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<WeatherIndex> index) {
        this.index = index;
    }

    public WeatherAqi getAqi() {
        return aqi;
    }

    public void setAqi(WeatherAqi aqi) {
        this.aqi = aqi;
    }

    public ArrayList<WeatherDaily> getDaily() {
        return daily;
    }

    public void setDaily(ArrayList<WeatherDaily> daily) {
        this.daily = daily;
    }

    public ArrayList<WeatherHourly> getHourly() {
        return hourly;
    }

    public void setHourly(ArrayList<WeatherHourly> hourly) {
        this.hourly = hourly;
    }
}
