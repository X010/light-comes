package com.light.outside.comes.model;

import com.alibaba.fastjson.annotation.JSONField;
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
public class BanquetModel extends BaseModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 金额
     */
    private float amount;

    /**
     * 人数
     */
    private int outnumber;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 开始时间
     */
    private Date start_time;


    /**
     * 结束时间
     */
    private Date end_time;

    /**
     * 承办人名称
     */
    private String author_nickname;

    /**
     * 承办人电话
     */
    private String author_telephone;

    /**
     * 备注
     */
    private String memo;

    /**
     * 状态
     */
    private int status;

    /**
     * 承办地址
     */
    private String author_address;

    /**
     * 酒水礼品说明
     */
    @JSONField(serialize = false)
    private String info;

    /**
     * 解析时间
     */
    private String rang_time;

    /**
     * 图片
     */
    private String photo;

    private int time_second;

    /**
     * 报名人数
     */
    private int enroll_num;

    /**
     * 是否可以成功举行
     */
    private int hold;
    /**
     * 举行时间
     */
    private Date banquet_time;
    /**
     * 总人数
     */
    private int total_number;

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public Date getBanquet_time() {
        return banquet_time;
    }

    public void setBanquet_time(Date banquet_time) {
        this.banquet_time = banquet_time;
    }

    public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }

    public int getEnroll_num() {
        return enroll_num;
    }

    public void setEnroll_num(int enroll_num) {
        this.enroll_num = enroll_num;
    }

    public int getTime_second() {
        return time_second;
    }

    public void setTime_second(int time_second) {
        this.time_second = time_second;
    }

    public String getPhoto() {
        if (!Strings.isNullOrEmpty(this.photo) && this.photo.contains(CONST.SITE_URL)) {
            return photo;
        } else {
            return CONST.SITE_URL + this.photo;
        }
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public int getOutnumber() {
        return outnumber;
    }

    public void setOutnumber(int outnumber) {
        this.outnumber = outnumber;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
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

    public String getAuthor_nickname() {
        return author_nickname;
    }

    public void setAuthor_nickname(String author_nickname) {
        this.author_nickname = author_nickname;
    }

    public String getAuthor_telephone() {
        return author_telephone;
    }

    public void setAuthor_telephone(String author_telephone) {
        this.author_telephone = author_telephone;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAuthor_address() {
        return author_address;
    }

    public void setAuthor_address(String author_address) {
        this.author_address = author_address;
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
