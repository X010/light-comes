package com.light.outside.comes.model;

/**
 * Created by b3st9u on 16/11/5.
 */
public class CouponRecordViewModel extends CouponRecordModel {
    private String limit;
    private long mid;
    private float price;

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public void setPrice(float price) {
        this.price = price;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    @Override
    public long getMid() {
        return mid;
    }

    @Override
    public void setMid(long mid) {
        this.mid = mid;
    }
}
