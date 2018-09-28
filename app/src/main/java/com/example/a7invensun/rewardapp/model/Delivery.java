package com.example.a7invensun.rewardapp.model;

/**
 * Created by 7invensun on 2018/9/28.
 */

public class Delivery {
    private String com;
    private String num;

    public Delivery(String com, String num) {
        this.com = com;
        this.num = num;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
