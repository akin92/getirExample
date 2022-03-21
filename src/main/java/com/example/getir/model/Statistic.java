package com.example.getir.model;

public class Statistic {
    private String month;
    private Integer totalOrderCount;
    private Integer totalBookCount;
    private Double totalPurchaseAmount;

    public Statistic() {
    }

    public Statistic(String month, Integer totalOrderCount, Integer totalBookCount, Double totalPurchaseAmount) {
        this.month = month;
        this.totalOrderCount = totalOrderCount;
        this.totalBookCount = totalBookCount;
        this.totalPurchaseAmount = totalPurchaseAmount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(Integer totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public Integer getTotalBookCount() {
        return totalBookCount;
    }

    public void setTotalBookCount(Integer totalBookCount) {
        this.totalBookCount = totalBookCount;
    }

    public Double getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public void setTotalPurchaseAmount(Double totalPurchaseAmount) {
        this.totalPurchaseAmount = totalPurchaseAmount;
    }
}
