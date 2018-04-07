package com.iframe.net.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    /**
     * 产品ID
     */
    String productId;
    /**
     * 产品名称
     */
    String productName;
    /**
     * 产品类型
     */
    String productType;
    /**
     * 月份
     */
    int months;
    /**
     * 天数
     */
    int days;
    /**
     * 年化收益率
     */
    double yearRate;
    /**
     * 产品总资金
     */
    double totalAmount;
    /**
     * 剩余资金
     */
    double remainAmount;
    /**
     * 最小投资金额
     */
    double minInvestAmount;
    /**
     * 最大投资金额
     */
    double maxInvestAmount;
    /**
     * 起售时间
     */
    String saleStartTime;
    /**
     * 起售截止时间
     */
    String saleEndTime;
    /**
     * 还款类型
     */
    String repayType;
    /**
     * 递增倍数
     */
    double incrementAmount;
    /**
     * 安全保障方式
     */
    String safeguardWay;
    /**
     * 产品状态
     */
    String status;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getYearRate() {
        return yearRate;
    }

    public void setYearRate(double yearRate) {
        this.yearRate = yearRate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(double remainAmount) {
        this.remainAmount = remainAmount;
    }

    public double getMinInvestAmount() {
        return minInvestAmount;
    }

    public void setMinInvestAmount(double minInvestAmount) {
        this.minInvestAmount = minInvestAmount;
    }

    public double getMaxInvestAmount() {
        return maxInvestAmount;
    }

    public void setMaxInvestAmount(double maxInvestAmount) {
        this.maxInvestAmount = maxInvestAmount;
    }

    public String getSaleStartTime() {
        return saleStartTime;
    }

    public void setSaleStartTime(String saleStartTime) {
        this.saleStartTime = saleStartTime;
    }

    public String getSaleEndTime() {
        return saleEndTime;
    }

    public void setSaleEndTime(String saleEndTime) {
        this.saleEndTime = saleEndTime;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public double getIncrementAmount() {
        return incrementAmount;
    }

    public void setIncrementAmount(double incrementAmount) {
        this.incrementAmount = incrementAmount;
    }

    public String getSafeguardWay() {
        return safeguardWay;
    }

    public void setSafeguardWay(String safeguardWay) {
        this.safeguardWay = safeguardWay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
