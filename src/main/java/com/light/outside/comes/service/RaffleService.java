package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.light.outside.comes.model.*;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.CouponCardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class RaffleService {


    @Autowired
    private PersistentDao persistentDao;


    /**
     * 添加劵
     *
     * @param couponModel
     */
    public void addCoupon(CouponModel couponModel) {
        this.persistentDao.addCoupon(couponModel);
    }


    /**
     * 生成劵
     *
     * @param id
     */
    public void generateCoupon(long id) {
        Preconditions.checkNotNull(id > 0);
        CouponModel couponModel = this.persistentDao.getCouponById(id);

        if (couponModel != null && couponModel.getStatus() == CONST.RAFFLE_STATUS_INIT) {
            //只有为初始状态才能生成
            int cardNum = couponModel.getNum();
            for (int i = 0; i < cardNum; i++) {
                String cardNo = CouponCardUtil.produceCouponCardNo(id);
                //将该卡信息进行存储
                CouponRecordModel couponRecordModel = new CouponRecordModel();
                couponRecordModel.setCardno(cardNo);
                couponRecordModel.setCid(couponModel.getId());
                couponRecordModel.setCreatetime(new Date());
                couponRecordModel.setCtype(couponModel.getCtype());
                couponRecordModel.setMid(couponModel.getMid());
                couponRecordModel.setPrice(couponModel.getPrice());
                couponRecordModel.setStatus(CONST.RAFFLE_STATUS_NORMAL);
                couponRecordModel.setTitle(couponModel.getTitle());
                couponRecordModel.setUse_end_time(couponModel.getUse_end_time());
                couponRecordModel.setUse_start_time(couponModel.getUse_start_time());

                //进行存储
                this.persistentDao.addCouponRecord(couponRecordModel);
            }
            couponModel.setStatus(CONST.RAFFLE_STATUS_NORMAL);
            this.persistentDao.editCoupon(couponModel);
        }
    }


    /**
     * 根据状态获取优惠劵
     *
     * @param status
     * @return
     */
    public List<CouponModel> getCouponsByStatus(int status) {
        return this.persistentDao.getCouponsByStatus(status);
    }


    /**
     * 保存抽奖活动
     *
     * @param raffleModel
     * @param raffleCouponModels
     */

    public void save_raffle(RaffleModel raffleModel, List<RaffleCouponModel> raffleCouponModels) {
        Preconditions.checkNotNull(raffleModel);

        long rid = this.persistentDao.addRaffle(raffleModel);

        if (rid > 0) {
            for (RaffleCouponModel raffleCouponModel : raffleCouponModels) {
                if (raffleCouponModel.getCid() > 0) {
                    //根据ID获取Coupon信息
                    CouponModel couponModel = this.persistentDao.getCouponById(raffleCouponModel.getCid());
                    raffleCouponModel.setCid(couponModel.getId());
                    raffleCouponModel.setCtype(couponModel.getCtype());
                    raffleCouponModel.setPrice(couponModel.getPrice());
                    raffleCouponModel.setTitle(couponModel.getTitle());
                    raffleCouponModel.setRid(0);
                    this.persistentDao.addRaffleCoupon(raffleCouponModel);
                }
            }
        }
    }

    /**
     * 分页获取数据
     *
     * @param pageModel
     * @return
     */
    public PageResult<CouponModel> getCoupons(PageModel pageModel) {
        //获取记录数
        int total = this.persistentDao.couponsTotal();

        //记录
        List<CouponModel> couponModels = this.persistentDao.getCoupons(pageModel.getStart(), pageModel.getSize());

        PageResult<CouponModel> couponModelPageResult = new PageResult<CouponModel>();
        couponModelPageResult.setData(couponModels);
        couponModelPageResult.setPageModel(pageModel);
        couponModelPageResult.setTotal(total);

        return couponModelPageResult;
    }


    /**
     * 分页获取抽奖活动
     *
     * @param pageModel
     * @return
     */
    public PageResult<RaffleModel> getRaffles(PageModel pageModel) {
        int total = this.persistentDao.rafflesTotal();

        List<RaffleModel> raffleModels = this.persistentDao.getRaffles(pageModel.getStart(), pageModel.getSize());

        PageResult<RaffleModel> raffleModelPageResult = new PageResult<RaffleModel>();
        raffleModelPageResult.setData(raffleModels);
        raffleModelPageResult.setPageModel(pageModel);
        raffleModelPageResult.setTotal(total);

        return raffleModelPageResult;
    }

    /**
     * 根据ID获取Raffle对象
     *
     * @param id
     * @return
     */
    public RaffleModel getRaffleById(long id) {
        Preconditions.checkArgument(id > 0);
        RaffleModel raffleModel = this.persistentDao.getRaffleById(id);
        if (raffleModel != null) {
            List<RaffleCouponModel> raffleCouponModels = this.persistentDao.getRaffleCouponByRaffleId(id);
            if (raffleCouponModels != null) {
                raffleModel.setRaffleCouponModels(raffleCouponModels);
            }
        }
        return raffleModel;
    }


    public void deleteCoupon(long id) {
        CouponModel couponModel = this.persistentDao.getCouponById(id);
        if (couponModel != null) {
            couponModel.setStatus(CONST.RAFFLE_STATUS_DELETE);
            this.persistentDao.editCoupon(couponModel);

            //更新CouponRecord状态根据CouponID
            this.persistentDao.editCouponRecordStatus(id, CONST.RAFFLE_STATUS_DELETE);
        }
    }


    public void deleteRaffle(long id) {
        RaffleModel raffleModel = this.persistentDao.getRaffleById(id);
        if (raffleModel != null) {
            raffleModel.setStatus(CONST.RAFFLE_STATUS_DELETE);
            this.persistentDao.editRaffle(raffleModel);
        }
    }
}
