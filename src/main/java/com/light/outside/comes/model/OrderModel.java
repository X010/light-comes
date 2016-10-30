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
 * 支付订单类
 */
public class OrderModel extends BaseModel {

    /**
     * 支付金额
     */
    private float amount;

    /**
     * 状态
     */
    private int status;

    /**
     * 活动类型
     */
    private int atype;

    /**
     * 活动名称
     */
    private String aname;

    /**
     * 支付类型
     */
    private int ptype;

    /**
     * 支付手号机
     */
    private String phone;

    /**
     * 用户ID
     */
    private long uid;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 支付时间
     */
    private Date paytime;

    /**
     * 订单号
     */
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAtype() {
        return atype;
    }

    public void setAtype(int atype) {
        this.atype = atype;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public int getPtype() {
        return ptype;
    }

    public void setPtype(int ptype) {
        this.ptype = ptype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }
}
