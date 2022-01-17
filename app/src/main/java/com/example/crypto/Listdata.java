package com.example.crypto;

public class Listdata {

    private String buy_sell;
    private String price;
    private String filled;
    private String fee;
    private String total;
    private String timenow;


    public Listdata(String buy_sell, String price, String filled, String fee, String total, String timenow) {
        this.buy_sell = buy_sell;
        this.price = price;
        this.filled = filled;
        this.fee = fee;
        this.total = total;
        this.timenow = timenow;
    }


    public String getBuy_sell() {
        return buy_sell;
    }

    public void setBuy_sell(String buy_sell) {
        this.buy_sell = buy_sell;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFilled() {
        return filled;
    }

    public void setFilled(String filled) {
        this.filled = filled;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTimenow() {
        return timenow;
    }

    public void setTimenow(String timenow) {
        this.timenow = timenow;
    }
}
