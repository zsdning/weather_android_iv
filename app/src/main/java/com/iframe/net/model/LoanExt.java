package com.iframe.net.model;

import java.io.Serializable;

/**
 * Created by zsdning on 2016/6/12.
 */
public class LoanExt implements Serializable {
    private String type;
    private String name;
    private String id;

    public LoanExt() {
    }

    public LoanExt(String type, String name, String id) {
        this.type = type;
        this.name = name;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
