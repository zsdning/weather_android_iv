package com.iframe.net.model;

/**
 * Created by Administrator on 2016/7/21.
 */
public class Scene {
    private String link;
    private String title;

    public Scene(){}

    public Scene(String link, String title) {
        this.link = link;
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
