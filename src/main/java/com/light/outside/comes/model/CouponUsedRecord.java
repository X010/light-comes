package com.light.outside.comes.model;

import java.util.Date;

/**
 * Created by b3st9u on 16/11/15.
 */
public class CouponUsedRecord {
    private long id;
    private long coupon_record_id;
    private String cardno;
    private long uid;
    private Date used_time;
    private long source_uid;
    private String source_phone;
    private String to_phone;
    private String coupon_titile;

    public String getCoupon_titile() {
        return coupon_titile;
    }

    public void setCoupon_titile(String coupon_titile) {
        this.coupon_titile = coupon_titile;
    }

    public String getSource_phone() {
        return source_phone;
    }

    public void setSource_phone(String source_phone) {
        this.source_phone = source_phone;
    }

    public String getTo_phone() {
        return to_phone;
    }

    public void setTo_phone(String to_phone) {
        this.to_phone = to_phone;
    }

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

    public long getCoupon_record_id() {
        return coupon_record_id;
    }

    public void setCoupon_record_id(long coupon_record_id) {
        this.coupon_record_id = coupon_record_id;
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
