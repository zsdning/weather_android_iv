package com.iframe.net.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    /**
     * 用户登录名
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 注册时间
     */
    private String createTime;
    /**
     * 上次登录时间
     */
    private String loginTime;
    /**
     * 企业名称
     */
    private String companyNameFull;
    /**
     * 企业简称
     */
    private String companyNameShort;
    /**
     * 联系人姓名
     */
    private String legalPersonName;
    /**
     * 联系人手机
     */
    private String legalPersonPhone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getCompanyNameFull() {
        return companyNameFull;
    }

    public void setCompanyNameFull(String companyNameFull) {
        this.companyNameFull = companyNameFull;
    }

    public String getCompanyNameShort() {
        return companyNameShort;
    }

    public void setCompanyNameShort(String companyNameShort) {
        this.companyNameShort = companyNameShort;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getLegalPersonPhone() {
        return legalPersonPhone;
    }

    public void setLegalPersonPhone(String legalPersonPhone) {
        this.legalPersonPhone = legalPersonPhone;
    }
}
