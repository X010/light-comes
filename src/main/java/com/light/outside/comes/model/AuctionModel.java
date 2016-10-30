package com.light.outside.comes.model;

import com.google.common.base.Strings;
import com.light.outside.comes.utils.CONST;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class AuctionModel extends BaseModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 金额
     */
    private float amount;


    /**
     * 状态
     */
    private int status;

    /**
     * 保证金
     */
    private float deposit;

    /**
     * 加价
     */
    private float setp_amount;

    /**
     * 创建时间
     */
    private Date create_time;


    /**
     * 读秒时间
     */
    private int time_second;

    /**
     * 商品ID
     */
    private long goodsid;

    /**
     * 时间范围
     */
    private String rang_time;

    /**
     * 开始时间
     */
    private Date start_time;

    /**
     * 结束时间
     */
    private Date end_time;

    /**
     * 商品图片
     */
    private String good_photo;

    /**
     * 商品名称
     */
    private String good_name;

    /**
     * 说明
     */
    private String memo;

    /**
     * 获奖者ID
     */
    private long win_uid;

    /**
     * 获取者手机号
     */
    private String win_phone;

    /**
     * 最终出价
     */
    private float win_price;

    public long getWin_uid() {
        return win_uid;
    }

    public void setWin_uid(long win_uid) {
        this.win_uid = win_uid;
    }

    public String getWin_phone() {
        return win_phone;
    }

    public void setWin_phone(String win_phone) {
        this.win_phone = win_phone;
    }

    public float getWin_price() {
        return win_price;
    }

    public void setWin_price(float win_price) {
        this.win_price = win_price;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getGood_photo() {
        if (!this.good_photo.contains(CONST.QBLK_PHOTO_URL)) {
            return CONST.QBLK_PHOTO_URL + good_photo;
        }
        return this.good_photo;
    }

    public void setGood_photo(String good_photo) {
        this.good_photo = good_photo;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public long getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(long goodsid) {
        this.goodsid = goodsid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public float getSetp_amount() {
        return setp_amount;
    }

    public void setSetp_amount(float setp_amount) {
        this.setp_amount = setp_amount;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getTime_second() {
        return time_second;
    }

    public void setTime_second(int time_second) {
        this.time_second = time_second;
    }

    public String getRang_time() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return String.format("%s-%s", simpleDateFormat.format(this.start_time), simpleDateFormat.format(this.end_time));
    }

    public void setRang_time(String rang_time) {
        this.rang_time = rang_time;
    }


    public void rangle_time() {
        if (!Strings.isNullOrEmpty(rang_time)) {
            String[] times = rang_time.split("-");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            try {
                this.start_time = simpleDateFormat.parse(times[0]);
                this.end_time = simpleDateFormat.parse(times[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
