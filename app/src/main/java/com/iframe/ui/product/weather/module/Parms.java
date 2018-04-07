package com.iframe.ui.product.weather.module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zsdning on 2016/10/10.
 */
public class Parms implements Serializable{
    private List<String> weeks;
    private List<String> states;
    private List<Integer> lowTemps;
    private List<Integer> highTemps;
    private List<String> windDirects;
    private List<String> windPowers;

    public Parms() {
    }

    public Parms(List<String> weeks, List<String> states, List<Integer> lowTemps, List<Integer> highTemps, List<String> windDirects, List<String> windPowers) {
        this.weeks = weeks;
        this.states = states;
        this.lowTemps = lowTemps;
        this.highTemps = highTemps;
        this.windDirects = windDirects;
        this.windPowers = windPowers;
    }

    public List<String> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<String> weeks) {
        this.weeks = weeks;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public List<Integer> getLowTemps() {
        return lowTemps;
    }

    public void setLowTemps(List<Integer> lowTemps) {
        this.lowTemps = lowTemps;
    }

    public List<Integer> getHighTemps() {
        return highTemps;
    }

    public void setHighTemps(List<Integer> highTemps) {
        this.highTemps = highTemps;
    }

    public List<String> getWindDirects() {
        return windDirects;
    }

    public void setWindDirects(List<String> windDirects) {
        this.windDirects = windDirects;
    }

    public List<String> getWindPowers() {
        return windPowers;
    }

    public void setWindPowers(List<String> windPowers) {
        this.windPowers = windPowers;
    }
}
