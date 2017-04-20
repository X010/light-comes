package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.light.outside.comes.model.*;
import com.light.outside.comes.mybatis.mapper.OverchargedDao;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.dao.ReadDao;
import com.light.outside.comes.qbkl.model.Commodity;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.DateUtils;
import com.light.outside.comes.utils.OverchargedRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
public class OverchargedService {


    @Autowired
    private PersistentDao persistentDao;


    private Logger LOG = LoggerFactory.getLogger(OverchargedService.class);

    @Autowired
    private OverchargedDao overchargedDao;

    @Autowired
    private ReadDao readDao;

    /**
     * 根据状态获取我参与的砍价活动
     *
     * @param status
     * @param pageModel
     * @param userModel
     * @return
     */
    public List<OverchargedModel> getOverchargedModelByMe(int status, PageModel pageModel, UserModel userModel) {
        return null;
    }

    /**
     * 是否参与过该活动
     *
     * @param aid
     * @param phone
     * @return
     */
    public boolean isJoinOvercharged(long aid, String phone) {
        boolean res = false;
        OverchargedRecordModel overchargedRecordModel = this.overchargedDao.getOverChargedRecordByPhoneAndAid(aid, phone);
        if (overchargedRecordModel != null) {
            res = true;
        }
        return res;
    }

    /**
     * 添加砍价记录
     *
     * @param aid
     * @param userModel
     * @return
     */
    public OverchargedRecordModel overchargedRecordModel(long aid, UserModel userModel) {
        Preconditions.checkNotNull(userModel);
        OverchargedRecordModel orm = new OverchargedRecordModel();
        orm.setStatus(CONST.RAFFLE_STATUS_INIT);
        orm.setAid(aid);
        orm.setUid(userModel.getId());
        orm.setPhone(userModel.getPhone());
        orm.setCreatetime(new Date());

        OverchargedRecordModel ownOrm = this.overchargedDao.getOverChargedRecordByPhoneAndAid(aid, userModel.getPhone());
        if (ownOrm == null) {
            OverchargedModel overchargedModel = this.getOverchargedModel(aid);
            if (overchargedModel != null) {
                orm.setAname(overchargedModel.getTitle());
                //获取前一个出价者
                OverchargedRecordModel preOrm = this.overchargedDao.getWinOverChargedRecordModel(aid);
                double preAmount = overchargedModel.getAmount();
                if (preOrm != null) {
                    preAmount = preOrm.getAmount();
                }
                double currentAmount = preAmount - 2 * overchargedModel.getSubtract_price();
                if (currentAmount < overchargedModel.getOver_amount()) {
                    //已经获取该商品
                    orm.setStatus(CONST.WIN);
                    if (overchargedModel.getOver_amount() > (preAmount - overchargedModel.getSubtract_price())) {
                        orm.setAmount(preAmount - overchargedModel.getSubtract_price());
                    } else {
                        orm.setAmount(overchargedModel.getOver_amount());
                    }

                    this.overchargedDao.addOverchargedRecordModel(orm);
                    //需要标记该活动已结束
                    overchargedModel.setStatus(CONST.RAFFLE_STATUS_OVER);
                    overchargedModel.setEnd_time(new Date());

                    this.updateOvercharged(overchargedModel);
                } else {
                    //该砍价者只是一个路过者
                    orm.setAmount(preAmount - overchargedModel.getSubtract_price());
                    //添加到数据库
                    this.overchargedDao.addOverchargedRecordModel(orm);
                }
            }
        } else {
            //已经出过价了
            orm.setStatus(CONST.RAFFLE_STATUS_OVER);
        }
        return orm;
    }

    /**
     *
     * @param aid
     * @param userModel
     * @return
     */
    public OverchargedRecordModel overcharged(long aid,long sponsor,UserModel userModel){
        double TIMES=2.1;//平均砍价幅度的2倍
        Preconditions.checkNotNull(userModel);
        OverchargedRecordModel orm = new OverchargedRecordModel();
        orm.setStatus(CONST.RAFFLE_STATUS_INIT);
        orm.setAid(aid);
        orm.setUid(userModel.getId());
        orm.setPhone(userModel.getPhone());
        orm.setCreatetime(new Date());
        OverchargedRecordModel ownOrm = this.overchargedDao.getOverChargedRecordByPhoneAndAid(aid,userModel.getPhone());//查询当前砍价用户
        if (ownOrm == null) {
            OverchargedModel overchargedModel = this.getOverchargedModel(aid);//获取砍价活动
            if (overchargedModel != null) {
                if(overchargedModel.getRemain_count()>0) {
                    int count = overchargedModel.getSubtract_count();//砍价力度数字越大力度越小
                    double totlSubtract = overchargedModel.getAmount() - overchargedModel.getOver_amount();//差价
                    double averageAmount = totlSubtract / count;//平均砍价幅度
                    double max = (averageAmount * TIMES);
                    max = max > totlSubtract ? totlSubtract : max;//最大砍掉数额
                    double oTotal = this.overchargedDao.getOverchargeSubtractPrice(aid, sponsor);
                    List<OverchargedRecordModel> list = this.overchargedDao.getOverchargeRecordHistory(aid, sponsor);
                    if (oTotal > 0) {//已砍价金额
                        totlSubtract = totlSubtract - oTotal;//剩余总价
                        if (totlSubtract < 0) {
                            totlSubtract = 0;
                        }
                    }
                    if (list != null && list.size() > 0) {
                        count = count - list.size();//砍价剩余次数
                    }
                    double money = OverchargedRandom.randomRedPacket(totlSubtract, 0.01, max, count);//随机生成砍价金额
                    orm.setAmount(money);//本次砍价金额
                    orm.setAname(overchargedModel.getGood_name());
                    if (count == 1) {
                        orm.setStatus(5);//最后一次砍价获取该商品
                        overchargedModel.setRemain_count(overchargedModel.getRemain_count() - 1);//修改剩余库存
                        this.overchargedDao.updateOvercharged(overchargedModel);
                    }
                    this.overchargedDao.addOverchargedRecordModel(orm);//保存砍价记录
                }else{
                    orm.setStatus(CONST.RAFFLE_STATUS_OVER);//已售完
                }
            }
        } else {
            //已经砍过价了
            orm.setStatus(CONST.RAFFLE_STATUS_OVER);
        }
        return orm;
    }

    /**
     * 获取当前价格
     * @param aid
     * @param uid
     * @return
     */
    public double getOverchargedNowPrice(long aid,long uid){
        OverchargedModel overchargedModel = this.getOverchargedModel(aid);//获取砍价活动
        double oTotal=this.overchargedDao.getOverchargeSubtractPrice(aid, uid);//已砍价格
        return overchargedModel.getAmount()-oTotal;
    }

    /**
     * 获取已砍掉的价格
     * @param aid
     * @param uid
     * @return
     */
    public double getOverchargedSubtractPrice(long aid,long uid){
        return this.overchargedDao.getOverchargeSubtractPrice(aid,uid);
    }

    /**
     * 获取出价列表
     *
     * @param aid
     * @return
     */
    public List<OverchargedRecordModel> getOverchargedRecords(long aid) {
        Preconditions.checkArgument(aid > 0);
        return this.overchargedDao.getOverchargedRecords(aid);
    }

    /**
     * 添加OverChage
     *
     * @param overchargedModel
     */
    public void addOverChage(OverchargedModel overchargedModel) {
        Preconditions.checkNotNull(overchargedModel);

        if (overchargedModel.getGoodsid() > 0) {
            Commodity commodity = this.readDao.getCommodityById(overchargedModel.getGoodsid());
            if (commodity != null) {
                overchargedModel.setGood_photo(commodity.getPicture());
                overchargedModel.setGood_name(commodity.getName());
                this.persistentDao.addOvercharged(overchargedModel);
            }
        }
    }

    /**
     * 获取OverChagred
     *
     * @param pageModel
     * @return
     */
    public PageResult<OverchargedModel> getOverchargeds(PageModel pageModel) {
        int total = this.persistentDao.auctionTotal();
        List<OverchargedModel> overchargedModels = this.persistentDao.getOverchargeds(pageModel.getStart(), pageModel.getSize());
        PageResult<OverchargedModel> overchargedModelPageResult = new PageResult<OverchargedModel>();
        overchargedModelPageResult.setData(overchargedModels);
        overchargedModelPageResult.setPageModel(pageModel);
        overchargedModelPageResult.setTotal(total);

        return overchargedModelPageResult;
    }

    /**
     * 分页获取砍价信息。包括当前现价，和还有多少时间
     *
     * @param pageModel
     * @return
     */
    public PageResult<OverchargedModel> getOverchargedsMoreInfo(PageModel pageModel) {
        int total = this.persistentDao.auctionTotal();
        List<OverchargedModel> overchargedModels = this.persistentDao.getOverchargeds(pageModel.getStart(), pageModel.getSize());
        if (overchargedModels != null) {
            for (OverchargedModel overchargedModel : overchargedModels) {
                OverchargedRecordModel overchargedRecordModel = this.overchargedDao.getWinOverChargedRecordModel(overchargedModel.getId());
                if (overchargedModel.getEnd_time().getTime() >= System.currentTimeMillis()) {
                    int free_time = 0;
                    try {
                        free_time = DateUtils.daysBetween(new Date(), overchargedModel.getEnd_time());
                        overchargedModel.setFree_time(free_time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    overchargedModel.setFree_time(0);
                }

                if (overchargedRecordModel != null) {
                    overchargedModel.setNow_price((float) overchargedRecordModel.getAmount());
                } else {
                    overchargedModel.setNow_price(overchargedModel.getAmount());
                }
            }
        }
        PageResult<OverchargedModel> overchargedModelPageResult = new PageResult<OverchargedModel>();
        overchargedModelPageResult.setData(overchargedModels);
        overchargedModelPageResult.setPageModel(pageModel);
        overchargedModelPageResult.setTotal(total);

        return overchargedModelPageResult;
    }

    public OverchargedModel getOverchargedModel(long id) {
        Preconditions.checkArgument(id > 0);
        return this.persistentDao.getOverchargedById(id);
    }

    /**
     * 更新
     *
     * @param overchargedModel
     */
    public void updateOvercharged(OverchargedModel overchargedModel) {
        Preconditions.checkNotNull(overchargedModel);
        this.persistentDao.updateOvercharged(overchargedModel);
    }

    /**
     * 删除Overcharged对象
     *
     * @param id
     */
    public void deleteOvercharged(long id) {
        Preconditions.checkArgument(id > 0);
        OverchargedModel overchargedModel = this.getOverchargedModel(id);
        if (overchargedModel != null) {
            overchargedModel.setStatus(CONST.RAFFLE_STATUS_DELETE);
            this.updateOvercharged(overchargedModel);
        }
    }

    public void clearOvercharged() {
        List<OverchargedModel> overchargedModels = this.persistentDao.getOverchargeds(1, Integer.MAX_VALUE);
        if (overchargedModels != null) {
            for (OverchargedModel overchargedModel : overchargedModels) {
                if (overchargedModel.getEnd_time().getTime() <= System.currentTimeMillis() && overchargedModel.getStatus() != CONST.RAFFLE_STATUS_OVER) {
                    overchargedModel.setStatus(CONST.RAFFLE_STATUS_OVER);
                    LOG.info("over overcharged id:" + overchargedModel.getId() + " name:" + overchargedModel.getTitle());
                    this.updateOvercharged(overchargedModel);
                }
            }
        }

    }

    /**
     * 查询砍价记录
     *
     * @param uid
     * @param status
     * @param pageModel
     * @return
     */
    public PageResult<OverchargedRecordViewModel> getOverchargedRecordPage(long uid, int status, PageModel pageModel) {
        PageResult<OverchargedRecordViewModel> overchargedRecordModelPageResult = new PageResult<OverchargedRecordViewModel>();
        List<OverchargedRecordViewModel> overchargedRecordModels = Lists.newArrayList();
        if (status > 0) {
            overchargedRecordModels = this.overchargedDao.getOverchargedRecordPageByUidAndStatus(uid, status, pageModel.getStart(), pageModel.getSize());
        } else {
            overchargedRecordModels = this.overchargedDao.getOverchargedRecordPageByUid(uid, pageModel.getStart(), pageModel.getSize());
        }
        overchargedRecordModelPageResult.setData(overchargedRecordModels);
        overchargedRecordModelPageResult.setPageModel(pageModel);
        return overchargedRecordModelPageResult;
    }


    public PageResult<OverchargedRecordModel> getOverchargedRecordByAid(long aid, PageModel pageModel) {
        Preconditions.checkNotNull(pageModel);
        int total = this.overchargedDao.getOverchargedRecordPageByAidTotal(aid);
        List<OverchargedRecordModel> overchargedRecordModels = this.overchargedDao.getOverchargedRecordByAid(aid, pageModel.getStart(), pageModel.getSize());
        PageResult<OverchargedRecordModel> overchargedRecordModelPageResult = new PageResult<OverchargedRecordModel>();
        overchargedRecordModelPageResult.setData(overchargedRecordModels);
        overchargedRecordModelPageResult.setPageModel(pageModel);
        overchargedRecordModelPageResult.setTotal(total);
        return overchargedRecordModelPageResult;
    }
}
