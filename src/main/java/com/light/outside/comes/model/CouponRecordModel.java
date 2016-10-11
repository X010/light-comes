package com.light.outside.comes.model;

import java.util.Date;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class CouponRecordModel extends BaseModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 可以使用开始时间
     */
    private Date use_start_time;


    /**
     * 到效时间
     */
    private Date use_end_time;


    /**
     * 卡的编号
     */
    private String cardno;

    /**
     * 用户ID
     */
    private long uid;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 状态
     */
    private int status;

    /**
     * 金额
     */
    private float price;


    /**
     * 商品或类型
     */
    private long mid;


    /**
     * 卡类型
     */
    private int  ctype;

    /**
     * 劵ID
     */
    private long cid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUse_start_time() {
        return use_start_time;
    }

    public void setUse_start_time(Date use_start_time) {
        this.use_start_time = use_start_time;
    }

    public Date getUse_end_time() {
        return use_end_time;
    }

    public void setUse_end_time(Date use_end_time) {
        this.use_end_time = use_end_time;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public int getCtype() {
        return ctype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }
}
