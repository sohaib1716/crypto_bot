package com.example.crypto;

public class information {


    private String takeProfit;

    private String stopLoss;

    private String orderAmount;

    private String coinName;

    private String minimum;

    private String maximum;

    private String timesbuy;

    public String getTimesbuy() {
        return timesbuy;
    }

    public void setTimesbuy(String timesbuy) {
        this.timesbuy = timesbuy;
    }

    public String getPercentInterval() {
        return percentInterval;
    }

    public void setPercentInterval(String percentInterval) {
        this.percentInterval = percentInterval;
    }

    private String percentInterval;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(String takeProfit) {
        this.takeProfit = takeProfit;
    }

    public String getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(String stopLoss) {
        this.stopLoss = stopLoss;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public information() {

    }

}
