package com.light.outside.comes.model;

import java.util.Date;

/**
 * Created by b3st9u on 16/11/15.
 */
public class CouponUsedRecord {
    private long id;
    private long coupon_coupon_record_id;
    private String cardno;
    private long uid;
    private Date used_time;
    private long source_uid;

    public long getSource_uid() {
        return source_uid;
    }

    public void setSource_uid(long source_uid) {
        this.source_uid = source_uid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCoupon_coupon_record_id() {
        return coupon_coupon_record_id;
    }

    public void setCoupon_coupon_record_id(long coupon_coupon_record_id) {
        this.coupon_coupon_record_id = coupon_coupon_record_id;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Date getUsed_time() {
        return used_time;
    }

    public void setUsed_time(Date used_time) {
        this.used_time = used_time;
    }
}
