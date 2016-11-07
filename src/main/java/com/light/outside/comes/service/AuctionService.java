package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.light.outside.comes.model.AuctionModel;
import com.light.outside.comes.model.AuctionRecordsModel;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.mybatis.mapper.AuctionDao;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.model.Commodity;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.qbkl.service.QblkService;
import com.light.outside.comes.utils.CONST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class AuctionService {


    @Autowired
    private PersistentDao persistentDao;

    @Autowired
    private QblkService qblkService;

    @Autowired
    private AuctionDao auctionDao;

    private Logger LOG = LoggerFactory.getLogger(AuctionService.class);

    /**
     * 添加拍卖商品
     *
     * @param auctionModel
     */
    public void addAuction(AuctionModel auctionModel) {
        Preconditions.checkNotNull(auctionModel);
        Preconditions.checkArgument(auctionModel.getGoodsid() > 0);

        Commodity commodity = this.qblkService.getCommodityById(auctionModel.getGoodsid());

        if (commodity != null) {
            auctionModel.setGood_photo(commodity.getPicture());
            auctionModel.setGood_name(commodity.getName());
            this.persistentDao.addAuction(auctionModel);
        }
    }

    /**
     * 查询拍卖列表
     *
     * @param pageModel
     * @return
     */
    public PageResult<AuctionModel> getAuctions(PageModel pageModel) {
        int total = this.persistentDao.auctionTotal();
        List<AuctionModel> auctionModels = this.persistentDao.getAuctions(pageModel.getStart(), pageModel.getSize());
        PageResult<AuctionModel> auctionModelPageResult = new PageResult<AuctionModel>();
        auctionModelPageResult.setData(auctionModels);
        auctionModelPageResult.setPageModel(pageModel);
        auctionModelPageResult.setTotal(total);

        return auctionModelPageResult;
    }

    /**
     * 分页面获取更多的拍卖信息
     *
     * @param pageModel
     * @return
     */
    public PageResult<AuctionModel> getAuctionsMoreInfo(PageModel pageModel) {
        int total = this.persistentDao.auctionTotal();
        List<AuctionModel> auctionModels = this.persistentDao.getAuctions(pageModel.getStart(), pageModel.getSize());

        if (auctionModels != null) {
            for (AuctionModel auctionModel : auctionModels) {
                if (auctionModel.getEnd_time().getTime() < System.currentTimeMillis() && !Strings.isNullOrEmpty(auctionModel.getWin_phone())) {
                    auctionModel.setIswin(1);
                }
            }
        }
        PageResult<AuctionModel> auctionModelPageResult = new PageResult<AuctionModel>();
        auctionModelPageResult.setData(auctionModels);
        auctionModelPageResult.setPageModel(pageModel);
        auctionModelPageResult.setTotal(total);

        return auctionModelPageResult;
    }

    /**
     * 根据ID查询拍卖详情
     *
     * @param id
     * @return
     */
    public AuctionModel queryAuctionById(long id) {
        return auctionDao.getAuctionsById(id);
    }

    public List<AuctionRecordsModel> queryAuctionRecordsByUser(long uid, int status) {
        if (status > 0)
            return auctionDao.queryAuctionRecordsByUserStatus(uid, status, 0, 100);
        else
            return auctionDao.queryAuctionRecordsByUser(uid, 0, 100);
    }

    /**
     * 分页查询
     *
     * @param uid
     * @param status
     * @param pageModel
     * @return
     */
    public PageResult<AuctionRecordsModel> queryAuctionRecordsByUserModel(long uid, int status, PageModel pageModel) {
        PageResult<AuctionRecordsModel> auctionModelPageResult = new PageResult<AuctionRecordsModel>();
        List<AuctionRecordsModel> auctionRecordsModels;
        int total = 0;
        if (status > 0)
            auctionRecordsModels = auctionDao.queryAuctionRecordsByUserStatus(uid, status, pageModel.getStart(), pageModel.getSize());
        else
            auctionRecordsModels = auctionDao.queryAuctionRecordsByUser(uid, pageModel.getStart(), pageModel.getSize());
        auctionModelPageResult.setData(auctionRecordsModels);
        auctionModelPageResult.setPageModel(pageModel);
        auctionModelPageResult.setTotal(total);
        return auctionModelPageResult;
    }


    public PageResult<AuctionRecordsModel> getAuctionRecordsByAid(long aid, PageModel pageModel) {
        Preconditions.checkNotNull(pageModel);
        int total = this.auctionDao.getAuctionRecordsByAidTotal(aid);
        List<AuctionRecordsModel> auctionRecordsModels = this.auctionDao.getAcutionRecordsByAid(aid, pageModel.getStart(), pageModel.getSize());
        PageResult<AuctionRecordsModel> auctionRecordsModelPageResult = new PageResult<AuctionRecordsModel>();
        auctionRecordsModelPageResult.setData(auctionRecordsModels);
        auctionRecordsModelPageResult.setPageModel(pageModel);
        auctionRecordsModelPageResult.setTotal(total);
        return auctionRecordsModelPageResult;
    }

    /**
     * 出价
     *
     * @param userModel
     * @param aid
     * @param price
     * @return
     */
    public boolean bidAuction(UserModel userModel, long aid, float price) {
        return auctionDao.addAuctionRecords(aid, price, userModel.getId(), userModel.getPhone()) > 0;
    }

    /**
     * 查询最高出价
     *
     * @param aid
     * @return
     */
    public AuctionRecordsModel queryTopRecord(long aid) {
        return auctionDao.queryAuctionRecords(aid);
    }

    /**
     * 查询出价记录
     *
     * @param aid
     * @return
     */
    public List<AuctionRecordsModel> queryAuctionRecordsByAid(long aid) {
        return auctionDao.selectAuctionRecordsByAid(aid);
    }


    /**
     * @param id
     * @return
     */
    public AuctionModel getAuctionById(long id) {
        Preconditions.checkArgument(id > 0);
        return this.persistentDao.getAuctionById(id);
    }

    /**
     * 更新Auction
     *
     * @param auctionModel
     */
    public void updateAuction(AuctionModel auctionModel) {
        Preconditions.checkNotNull(auctionModel);
        //判断并更攺状态
        AuctionModel oldAuction = this.getAuctionById(auctionModel.getId());
        if (oldAuction != null) {
            if (oldAuction.getStatus() == CONST.RAFFLE_STATUS_OVER && auctionModel.getEnd_time().getTime() >= System.currentTimeMillis()) {
                auctionModel.setStatus(CONST.RAFFLE_STATUS_NORMAL);
            }
        }

        this.persistentDao.updateAuction(auctionModel);
    }


    /**
     * 删除Auction
     *
     * @param id
     */
    public void deleteAuction(long id) {
        Preconditions.checkNotNull(id > 0);

        AuctionModel auctionModel = this.getAuctionById(id);
        if (auctionModel != null) {
            auctionModel.setStatus(CONST.RAFFLE_STATUS_DELETE);
            this.updateAuction(auctionModel);
        }
    }

    /**
     * 清除过期的活动
     */
    public void clearAuction() {
        List<AuctionModel> auctionModels = this.persistentDao.getAuctions(1, Integer.MAX_VALUE);
        if (auctionModels != null) {
            for (AuctionModel auctionModel : auctionModels) {
                if (auctionModel.getEnd_time().getTime() <= System.currentTimeMillis() && auctionModel.getStatus() != CONST.RAFFLE_STATUS_OVER) {
                    auctionModel.setStatus(CONST.RAFFLE_STATUS_OVER);
                    LOG.info("over auction id:" + auctionModel.getId() + " name:" + auctionModel.getTitle());
                    //选出拍卖中的那个人
                    AuctionRecordsModel auctionRecordsModel = this.auctionDao.getWinAuctionRecord(auctionModel.getId());
                    if (auctionRecordsModel != null) {
                        auctionRecordsModel.setStatus(CONST.WIN);
                        //更新AuctionRecord
                        auctionModel.setWin_phone(auctionRecordsModel.getPhone());
                        auctionModel.setWin_price(auctionRecordsModel.getPrice());
                        auctionModel.setWin_uid(auctionRecordsModel.getUid());
                        this.auctionDao.updateWinAuactionRecord(auctionRecordsModel);
                    }

                    this.updateAuction(auctionModel);
                }
            }
        }
    }
}
