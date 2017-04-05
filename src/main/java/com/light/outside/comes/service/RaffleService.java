package com.light.outside.comes.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.light.outside.comes.controller.pay.token.TokenThread;
import com.light.outside.comes.model.*;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.dao.ReadDao;
import com.light.outside.comes.qbkl.model.Commodity;
import com.light.outside.comes.qbkl.model.CommodityCategory;
import com.light.outside.comes.service.weixin.MD5;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.CouponCardUtil;
import com.light.outside.comes.utils.HttpTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@Service
public class RaffleService {

    private Logger LOG = LoggerFactory.getLogger(RaffleService.class);

    @Autowired
    private PersistentDao persistentDao;
    @Autowired
    private ReadDao readDao;

    /**
     * 品类
     */
    private Map<Long, CommodityCategory> categoryModelMap;
    /**
     * 商品
     */
    private Map<Long, String> commodityMap;

    /**
     * 加载商品类别
     */
    public void initCategory() {
        List<CommodityCategory> goodsCategoryModels = this.readDao.queryCategorys();
        categoryModelMap = new HashMap<Long, CommodityCategory>();
        for (CommodityCategory commodityCategory : goodsCategoryModels) {
            categoryModelMap.put(commodityCategory.getId(), commodityCategory);
        }
        List<Commodity> commodities = this.readDao.queryAllCommodityes();
        commodityMap = new HashMap<Long, String>();
        for (Commodity commodity : commodities) {
            commodityMap.put(commodity.getId(), commodity.getName());
        }
        System.out.println(" init category and commodity");
        //启动刷新token线程
        TokenThread tokenThread=new TokenThread();
        Thread thread=new Thread(tokenThread);
        thread.start();
    }

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

        if (raffleModel.getId() > 0) {

            RaffleModel oldRaffle = this.persistentDao.getRaffleById(raffleModel.getId());

            if (Strings.isNullOrEmpty(raffleModel.getPhoto())) {
                raffleModel.setPhoto(oldRaffle.getPhoto());
            }

            //修改
            this.persistentDao.editRaffle(raffleModel);


            this.persistentDao.deleteRaffleCouponByRaffleId(raffleModel.getId());

            if (raffleCouponModels != null && raffleCouponModels.size() > 0) {
                for (RaffleCouponModel raffleCouponModel : raffleCouponModels) {
                    if (raffleCouponModel.getCid() > 0) {
                        this.addRaffleCoupon(raffleCouponModel, raffleModel.getId());
                    }
                }
            }
        } else {
            //更新
            long rid = this.persistentDao.addRaffle(raffleModel);

            if (rid > 0) {
                for (RaffleCouponModel raffleCouponModel : raffleCouponModels) {
                    if (raffleCouponModel.getCid() > 0) {
                        //根据ID获取Coupon信息
                        this.addRaffleCoupon(raffleCouponModel, rid);
                    }
                }
            }
        }
    }


    private void addRaffleCoupon(RaffleCouponModel raffleCouponModel, long rid) {
        CouponModel couponModel = this.persistentDao.getCouponById(raffleCouponModel.getCid());
        raffleCouponModel.setCid(couponModel.getId());
        raffleCouponModel.setCtype(couponModel.getCtype());
        raffleCouponModel.setPrice(couponModel.getPrice());
        raffleCouponModel.setTitle(couponModel.getTitle());
        raffleCouponModel.setRid(rid);
        this.persistentDao.addRaffleCoupon(raffleCouponModel);
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


    public PageResult<CouponModel> getCouponsMoreInfo(PageModel pageModel) {
        //获取记录数
        int total = this.persistentDao.couponsTotal();
        //记录
        List<CouponModel> couponModels = this.persistentDao.getCoupons(pageModel.getStart(), pageModel.getSize());
        if (couponModels != null) {
            for (CouponModel couponModel : couponModels) {
                //获取使用发送张数与使用张数
                int sendNum = this.persistentDao.getCouponSendNum(couponModel.getId());
                couponModel.setSendnum(sendNum);
                //获取使用使用的张数
                int useNum = this.persistentDao.getCouponUseNum(couponModel.getId(), CONST.COUPON_STATUS_USED);
                couponModel.setUsenum(useNum);
            }
        }
        PageResult<CouponModel> couponModelPageResult = new PageResult<CouponModel>();
        couponModelPageResult.setData(couponModels);
        couponModelPageResult.setPageModel(pageModel);
        couponModelPageResult.setTotal(total);

        return couponModelPageResult;
    }


    public PageResult<CouponRecordModel> getCouponRecordModelByAid(long aid, PageModel pageModel) {
        Preconditions.checkNotNull(pageModel);
        int total = this.persistentDao.getCouponRecordByCidTotal(aid);

        List<CouponRecordModel> couponRecordModels = this.persistentDao.getCouponRecordByCid(aid, pageModel.getStart(), pageModel.getSize());

        PageResult<CouponRecordModel> couponRecordModelPageResult = new PageResult<CouponRecordModel>();
        couponRecordModelPageResult.setData(couponRecordModels);
        couponRecordModelPageResult.setPageModel(pageModel);
        couponRecordModelPageResult.setTotal(total);

        return couponRecordModelPageResult;
    }

    /**
     * 根据id查询优惠券
     *
     * @param id
     * @return
     */
    public CouponRecordModel getCouponRecordById(long id) {
        return this.persistentDao.getCouponRecordById(id);
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

    public List<CouponRecordViewModel> getRaffleCouponByUser(long uid, int status) {
        if (status > 0)
            return this.persistentDao.getRaffleCouponByUserStatus(uid, status);
        else
            return this.persistentDao.getRaffleCouponByUser(uid);
    }

    /**
     * 我的优惠券api
     *
     * @param uid
     * @param status
     * @param pageModel
     * @return
     */
    public PageResult<CouponRecordViewModel> getRaffleCouponPageByUser(long uid, int status, PageModel pageModel) {
        PageResult<CouponRecordViewModel> pageResult = new PageResult<CouponRecordViewModel>();
        List<CouponRecordViewModel> couponRecordModels = Lists.newArrayList();
        if (status > 0) {
            if (status == 10) {
                couponRecordModels = this.persistentDao.getUsedRaffleCouponPageByUser(uid, pageModel.getStart(), pageModel.getSize());
            } else {
                couponRecordModels = this.persistentDao.getRaffleCouponPageByUserStatus(uid, status, pageModel.getStart(), pageModel.getSize());
            }
        } else {
            couponRecordModels = this.persistentDao.getRaffleCouponPageByUser(uid, pageModel.getStart(), pageModel.getSize());
        }
        transfCouponForView(couponRecordModels);
        pageResult.setData(couponRecordModels);
        return pageResult;
    }

    /**
     * @param uid
     * @param pageModel
     * @return
     */
//    public PageResult<CouponRecordViewModel> getUsedRaffleCouponPageByUser(long uid, PageModel pageModel) {
//        PageResult<CouponRecordViewModel> pageResult = new PageResult<CouponRecordViewModel>();
//        List<CouponRecordViewModel> couponRecordModels = Lists.newArrayList();
//        List<CouponUsedRecord> list = this.persistentDao.getUsedRaffleCouponPageByUser(uid, pageModel.getStart(), pageModel.getSize());
//        if (list != null && list.size() > 0) {
//            for (CouponUsedRecord record : list) {
//                CouponRecordViewModel couponRecordViewModel = new CouponRecordViewModel();
//
//            }
//        }
//    }

    /**
     * 转换品类
     *
     * @param recordModels
     */
    public void transfCouponForView(List<CouponRecordViewModel> recordModels) {
        for (CouponRecordViewModel recordModel : recordModels) {
            long mid = recordModel.getMid();
            if (mid > 0) {
                if (recordModel.getCtype() == 2) {
                    CommodityCategory commodityCategory = categoryModelMap.get(mid);
                    if (commodityCategory != null) {
                        recordModel.setLimit(commodityCategory.getCategory1() + "-" + commodityCategory.getCategory2());
                    }
                } else if (recordModel.getCtype() == 3) {
                    String name = commodityMap.get(mid);
                    recordModel.setLimit(name);
                } else {
                    recordModel.setLimit("全品类");
                }
            }
        }
    }

    /**
     * 根据活动ID获取奖券信息
     *
     * @param rid
     * @return
     */
    public List<RaffleCouponModel> getRaffleCoupons(long rid) {
        return this.persistentDao.getRaffleCouponByRaffleId(rid);
    }

    /**
     * 获取用户已经抽奖次数
     *
     * @param uid
     * @param rid
     * @return
     */
    public int getUserRaffleCount(long uid, long rid) {
        RaffleUserModel raffleUserModel = this.persistentDao.getRaffleUserByRaffleId(rid, uid);
        if (raffleUserModel == null) {
            return 0;
        } else {
            return raffleUserModel.getCount();
        }
    }

    /**
     * 查询剩余抽奖次数
     *
     * @param uid
     * @param rid
     * @return
     */
    public int getRaffleCount(long uid, long rid) {
        RaffleUserModel raffleUserModel = this.persistentDao.getRaffleUserByRaffleId(rid, uid);
        if (raffleUserModel == null) {
            return 0;
        } else {
            return raffleUserModel.getCount();
        }
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


    /**
     * 初始化奖品
     */
    Map<Long, List<RaffleCouponModel>> raffleMap = new HashMap<Long, List<RaffleCouponModel>>();

    public void initRaffle() {
        List<RaffleModel> raffleModels = this.persistentDao.getRaffles(0, 100);
        if (raffleModels != null && raffleModels.size() > 0) {
            for (RaffleModel raffleModel : raffleModels) {
                long rid = raffleModel.getId();
                List<RaffleCouponModel> raffleCouponModels = this.persistentDao.getRaffleCouponByRaffleId(rid);
                //需要补全空的奖项
                if (raffleCouponModels.size() < 8) {
                    int sumWinrate = 0;
                    int total = 0;
                    float gapTotal = 0;
                    for (RaffleCouponModel raffleCouponModel : raffleCouponModels) {
                        sumWinrate += raffleCouponModel.getWinrate();
                        total += raffleCouponModel.getQuantity();
                    }
                    float a = sumWinrate / 100f;
                    gapTotal = (total / (a)) - total;
//                    补全剩余百分比
                    if (sumWinrate < 100) {
                        RaffleCouponModel raffleCouponModel = new RaffleCouponModel();
                        raffleCouponModel.setTitle("没有中奖!");
                        raffleCouponModel.setId(0);
                        raffleCouponModel.setWinrate(100 - sumWinrate);
                        raffleCouponModel.setQuantity((int) gapTotal);
                        raffleCouponModels.add(raffleCouponModel);
                    }
                }
                raffleMap.put(rid, raffleCouponModels);
            }
        }


    }

    /**
     * 抽奖
     *
     * @return
     */
    public synchronized RaffleCouponModel drawRaffle(long rid) {
        long uid = 0;
        String phone = "18888888888";
        List<RaffleCouponModel> raffleCouponModels = null;
        if (raffleMap != null && raffleMap.size() > 0) {
            raffleCouponModels = raffleMap.get(rid);
        }
        if (raffleCouponModels != null && raffleCouponModels.size() > 0) {
            int randomNumber = (int) (Math.random() * total(rid));//随机数
            int priority = 0;
            for (RaffleCouponModel g : raffleCouponModels) {
                priority += g.getWinrate();
                if (randomNumber > 0 && priority >= randomNumber) {
                    //奖品数量减少
                    //g.setQuantity(g.getQuantity() - 1);
                    //保存优惠券
                    if (g.getId() > 0) {
                        List<CouponRecordModel> couponRecordModels = this.persistentDao.getCouponRecordModelByCid(g.getCid(), CONST.RAFFLE_STATUS_NORMAL, 0, 1);
                        if (couponRecordModels != null) {
                            CouponRecordModel couponRecordModel = couponRecordModels.get(0);
                            this.persistentDao.editCouponRecordStatusByUser(couponRecordModel.getId(), CONST.RAFFLE_STATUS_BIND, uid, phone);
                            return g;
                        }
                    } else {
                        return null;
                    }
                }
            }
        }
        // 抽奖次数多于奖品时谢谢参与
        return null;
    }

    /**
     * 奖品总数
     *
     * @param rid
     * @return
     */
    private int total(long rid) {
        int result = 0;
        int sum = 0;
        List<RaffleCouponModel> raffleCouponModels = raffleMap.get(rid);
        if (raffleCouponModels != null && raffleCouponModels.size() > 0) {
            for (RaffleCouponModel raffleCouponModel : raffleCouponModels) {
                result += raffleCouponModel.getWinrate();
                sum += raffleCouponModel.getQuantity();
            }
        }
        System.out.println("剩余奖券数量:" + sum);
        return result;
    }

    /**
     * 根据奖品ID抽奖
     *接口地址：
     http://www.qubulikou.com/user/createCoupon

     请求方式：POST
     参数格式: JSON
     具体参数：
     [
     'id' => 11, //新系统优惠券id，必选
     'amount' => 10, //金额, 必选
     'starttime' => '2017-3-2 12:32:34', //开始时间，必选，需小于截止时间
     'endtime' => '2017-4-2 12:32:34', //截止时间，必选，需大于开始时间，且大于现在时间
     'userid' => 13, //用户id，可选，新建的优惠券会发放给此用户, 不发放传0
     'shopid' => 10, //商铺id, 可选，新建的优惠券在此商铺使用（此商铺必须存在），不限制传0
     'promotionid' => 30, //促销活动id, 可选， 新建的优惠券在此促销活动中使用(此促销活动必须存在),不限制传0
     'categoryid' => 12, //分类id, 可选,新建的优惠券在此分类下使用 （此分类必须存在）,不限制传0
     'picture' => 'xxx.jpg', //图片路径, 可选
     'threshold' => 0, //起用门限，可选
     ]

     返回：JSON
     成功:
     ['errcode'=>0,'msg'=>'success', 'data'=>['couponId'=>100]]  //老系统新建的优惠券id

     失败：
     ['errcode'=>1004,'msg'=>'参数shopid错误', 'data'=>[]]
     * @param rcid
     * @return
     */
    public RaffleCouponModel drawRaffleByRage(long rcid, long uid, String phone) {
        //String url="http://www.qubulikou.com/user/createCoupon";
        String url="http://120.55.241.127/user/createCoupon";
        RaffleCouponModel raffleCouponModel = this.persistentDao.getRaffleCouponById(rcid);
        if (raffleCouponModel != null) {
            double rate = raffleCouponModel.getWinrate() / 100.00f;
            int result = percentageRandom(rate);
            if (result > 0) {
                List<CouponRecordModel> couponRecordModels = this.persistentDao.getCouponRecordModelByCid(raffleCouponModel.getCid(), CONST.RAFFLE_STATUS_NORMAL, 0, 1);
                if (couponRecordModels != null) {
                    CouponRecordModel couponRecordModel = couponRecordModels.get(0);
                    this.persistentDao.editCouponRecordStatusByUser(couponRecordModel.getId(), CONST.COUPON_STATUS_NOTUSED, uid, phone);
                    //TODO 请求老系统保存优惠券信息
                    JSONObject params=new JSONObject();
                    params.put("id",String.valueOf(couponRecordModel.getId()));
                    params.put("amount", String.valueOf(couponRecordModel.getPrice()));
                    params.put("starttime", String.valueOf(couponRecordModel.getUse_start_time()));
                    params.put("endtime", String.valueOf(couponRecordModel.getUse_end_time()));
                    params.put("userid", String.valueOf(couponRecordModel.getUid()));
                    params.put("shopid", String.valueOf(0));
                    params.put("promotionid", String.valueOf(rcid));
                    params.put("categoryid", String.valueOf(couponRecordModel.getMid()));
                    String checkToken = MD5.MD5Encode(params.toJSONString());
                    params.put("token",checkToken);
                    System.out.println(params.toJSONString() + "    " + checkToken);
                    try {
                        String response=HttpTools.post(url, params.toJSONString());
                        System.out.println("response:"+response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return raffleCouponModel;
                }
            }
        }
        return null;
    }

    public boolean addRaffleCount(long uid, long rid, int count) {
        return this.persistentDao.updateRaffleUserByRaffleId(uid, rid, count) > 0;
    }


    /**
     * 查询中奖记录
     *
     * @param cid
     * @return
     */
    public List<CouponRecordModel> queryCouponRecords(long rid) {
        //List<CouponRecordModel> couponRecordModels = this.persistentDao.getRaffleCouponByRaffleIdAndStatus(cid, CONST.COUPON_STATUS_NOTUSED, 0, 10);
        List<CouponRecordModel> couponRecordModels = this.persistentDao.getRaffleCouponRecordByRaffleId(rid, 0, 10);
        return couponRecordModels;
    }

    /**
     * 清除过期活动
     */
    public void clearRaffle() {
        List<RaffleModel> raffleModels = this.persistentDao.getRaffles(1, Integer.MAX_VALUE);
        if (raffleModels != null) {
            for (RaffleModel raffleModel : raffleModels) {
                if (raffleModel.getEnd_time().getTime() <= System.currentTimeMillis() && raffleModel.getStatus() != CONST.RAFFLE_STATUS_OVER) {
                    raffleModel.setStatus(CONST.RAFFLE_STATUS_OVER);
                    LOG.info("over raffle id:" + raffleModel.getId() + " name:" + raffleModel.getTitle());
                    this.persistentDao.editRaffle(raffleModel);
                }
            }
        }
    }

    /**
     * 根据概率生成随机数1 中奖 0未中奖
     *
     * @param rate
     * @return
     */
    private static int percentageRandom(double rate) {
        double randomNumber = 0.00f;
        randomNumber = Math.random();
        if (randomNumber >= 0 && randomNumber <= rate) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        int c1 = 0;
        int c0 = 0;
        for (int i = 0; i < 100; i++) {
            int r = percentageRandom(0.10f);
            if (r == 0) {
                c0++;
            } else {
                c1++;
            }
        }
        System.out.println(c0 + "  " + c1);
    }
}
