package com.light.outside.comes.model;

import com.google.common.base.Strings;
import com.light.outside.comes.utils.CONST;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
public class RaffleModel extends BaseModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 开始时间
     */
    private Date start_time;

    /**
     * 结束时间
     */
    private Date end_time;

    /**
     * 备注
     */
    private String memo;

    /**
     * 图片
     */
    private String photo;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 状态
     */
    private int status;

    /**
     * 时间范围
     */
    private String rang_time;

    private List<CouponModel> coupons;

    private List<RaffleCouponModel> raffleCouponModels;

    /**
     * 次数
     */
    private int times;

    public List<RaffleCouponModel> getRaffleCouponModels() {
        return raffleCouponModels;
    }

    public void setRaffleCouponModels(List<RaffleCouponModel> raffleCouponModels) {
        this.raffleCouponModels = raffleCouponModels;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getRang_time() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return String.format("%s-%s", simpleDateFormat.format(this.start_time), simpleDateFormat.format(this.end_time));
    }

    public void setRang_time(String rang_time) {
        this.rang_time = rang_time;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CouponModel> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponModel> coupons) {
        this.coupons = coupons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPhoto() {
        return CONST.SITE_URL+photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void rangle_time() {
        if (!Strings.isNullOrEmpty(rang_time)) {
            String[] times = rang_time.split("-");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                this.start_time = simpleDateFormat.parse(times[0]);
                this.end_time = simpleDateFormat.parse(times[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
