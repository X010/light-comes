package com.light.outside.comes.model;

import java.util.Date;

/**
 * Created by b3st9u on 16/10/20.
 */
public class AuctionRecordsModel {
    private long id;
    private long aid;
    private float price;
    private long uid;
    private String phone;
    private Date crete_time;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCrete_time() {
        return crete_time;
    }

    public void setCrete_time(Date crete_time) {
        this.crete_time = crete_time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
