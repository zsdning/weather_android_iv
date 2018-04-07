package com.iframe.realm.module;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by zsdning on 2016/5/12. 数据缓存类
 */
public class Person extends RealmObject implements Serializable{
    @PrimaryKey
    String personId;
    private int code;
    private String desc;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

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

}
