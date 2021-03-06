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
public class OverchargedModel extends BaseModel {

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 活动初始价
     */
    private float amount;

    /**
     * 砍价范围
     */
    private float subtract_price;

    /**
     * 标题
     */
    private String title;

    /**
     * 状态
     */
    private int status;


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
     * 秒数
     */
    private int time_second;

    /**
     * 底价
     */
    private float over_amount;

    /**
     * 现价
     */
    private float now_price;

    /**
     * 还有多少天
     */
    private int free_time;
    /**
     * 砍价活动说明
     */
    private String info;
    /**
     * 砍价力度
     */
    private int subtract_count;
    /**
     * 商品库存
     */
    private int inventory;
    /**
     * 分享标题
     */
    private String share_title;
    /**
     * 分享图标
     */
    private String share_photo;
    /**
     * 分享描述
     */
    private String share_desc;
    /**
     * 剩余库存
     */
    private int remain_count;

    public int getRemain_count() {
        return remain_count;
    }

    public void setRemain_count(int remain_count) {
        this.remain_count = remain_count;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_photo() {
        return share_photo;
    }

    public void setShare_photo(String share_photo) {
        this.share_photo = share_photo;
    }

    public String getShare_desc() {
        return share_desc;
    }

    public void setShare_desc(String share_desc) {
        this.share_desc = share_desc;
    }

    public String getInfo() {
        return info;
    }

    public int getSubtract_count() {
        return subtract_count;
    }

    public void setSubtract_count(int subtract_count) {
        this.subtract_count = subtract_count;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getFree_time() {
        return free_time;
    }

    public void setFree_time(int free_time) {
        this.free_time = free_time;
    }

    public float getNow_price() {
        return now_price;
    }

    public void setNow_price(float now_price) {
        this.now_price = now_price;
    }

    public float getOver_amount() {
        return over_amount;
    }

    public void setOver_amount(float over_amount) {
        this.over_amount = over_amount;
    }

    public int getTime_second() {
        return time_second;
    }

    public void setTime_second(int time_second) {
        this.time_second = time_second;
    }

    public long getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(long goodsid) {
        this.goodsid = goodsid;
    }

    public String getRang_time() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return String.format("%s-%s", simpleDateFormat.format(this.start_time), simpleDateFormat.format(this.end_time));
    }

    public void setRang_time(String rang_time) {
        this.rang_time = rang_time;
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

    public String getGood_photo() {
        if (!this.good_photo.contains(CONST.QBLK_PHOTO_URL)) {
            return CONST.QBLK_PHOTO_URL + good_photo;
        }
        return this.good_photo;
    }

    public void setGood_photo(String good_photo) {
        this.good_photo = good_photo;
    }

    public String getGood_name() {
        return this.good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }


    public float getSubtract_price() {
        return subtract_price;
    }

    public void setSubtract_price(float subtract_price) {
        this.subtract_price = subtract_price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
