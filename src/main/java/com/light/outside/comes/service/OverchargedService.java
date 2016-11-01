package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.light.outside.comes.model.OverchargedModel;
import com.light.outside.comes.model.OverchargedRecordModel;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.mybatis.mapper.OverchargedDao;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.dao.ReadDao;
import com.light.outside.comes.qbkl.model.Commodity;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.utils.CONST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class OverchargedService {


    @Autowired
    private PersistentDao persistentDao;


    private Logger LOG = LoggerFactory.getLogger(OverchargedService.class);

    @Autowired
    private OverchargedDao overchargedDao;

    @Autowired
    private ReadDao readDao;

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
                float preAmount = overchargedModel.getAmount();
                if (preOrm != null) {
                    preAmount = preOrm.getAmount();
                }
                float currentAmount = preAmount - 2 * overchargedModel.getSubtract_price();
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
}
