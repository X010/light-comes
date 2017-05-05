package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.light.outside.comes.model.*;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.qbkl.service.QblkService;
import com.light.outside.comes.utils.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
public class PastService {

    @Autowired
    private PersistentDao persistentDao;

    @Autowired
    private QblkService qblkService;

    @Autowired
    private RaffleService raffleService;

    @Autowired
    private CouponService couponService;

    public PastModel getPastModelById() {
        return this.persistentDao.getPastById(CONST.PAST_ID);
    }

    public List<PastDetail> getPastDetailByUid(long uid) {
        return this.persistentDao.getPastDetailByUid(uid);
    }

    public List<PastDetail> getPastDetailByPhone(String phone) {
        return this.persistentDao.getPastDetail(phone);
    }

    /**
     * 保存签到活动信息
     *
     * @param pastModel
     * @return
     */
    public PastModel svePastModel(PastModel pastModel) {
        Preconditions.checkNotNull(pastModel);
        pastModel.setId(CONST.PAST_ID);
        PastModel oldPast = this.persistentDao.getPastById(CONST.PAST_ID);

        if (oldPast != null) {
            //更新
            this.persistentDao.updatePast(pastModel);
        } else {
            //添加
            pastModel.setCreate_time(new Date());
            this.persistentDao.addPast(pastModel);
        }
        return pastModel;
    }


    /**
     * 根据用户的手机号码获取用户的周期的干杯信息
     *
     * @param userModel
     * @return
     */
    public PastTotal getPastTotalByPhone(UserModel userModel){
        Preconditions.checkNotNull(userModel);
        PastTotal pastTotal = this.persistentDao.getPastTotalByPhone(userModel.getPhone());
        if (pastTotal == null) {
            //加入本周期的相关信息
            pastTotal = new PastTotal();
            pastTotal.setPhone(userModel.getPhone());
            pastTotal.setUid(userModel.getUserid());
            pastTotal.setCycle_drunk(0);
            pastTotal.setCycle_times(0);
            pastTotal.setToday_drunk(0);
            pastTotal.setToday_other_drunk(0);
            pastTotal.setToday_other_times(0);
            pastTotal.setToday_times(0);
            this.persistentDao.addPastTotal(pastTotal);
        }

        //根据信息判断用户当前的签到状态
        PastModel pastModel = this.getPastModelById();
        if (pastModel != null) {
            int times = this.getTodayDrunkTimes(userModel.getPhone());
            pastTotal.setToday_times(times);
            pastTotal.setToday_have_times(pastModel.getPast_times() - times);
            pastTotal.setTotal_drunk(pastModel.getTotal_drunk());
        }

        return pastTotal;
    }

    /**
     * 根据用户的手机号码获取和朋友的干杯信息
     *
     * @param userModel
     * @return
     */
    public PastTotal getPastTotalByPhoneAndFriend(UserModel userModel,UserModel friend) {
        Preconditions.checkNotNull(userModel);
        PastTotal pastTotal = this.persistentDao.getPastTotalByPhone(friend.getPhone());
        if (null==pastTotal) {
            //加入本周期的相关信息
            pastTotal = new PastTotal();
            pastTotal.setPhone(userModel.getPhone());
            pastTotal.setUid(userModel.getUserid());
            pastTotal.setCycle_drunk(0);
            pastTotal.setCycle_times(0);
            pastTotal.setToday_drunk(0);
            pastTotal.setToday_other_drunk(0);
            pastTotal.setToday_other_times(0);
            pastTotal.setToday_times(0);
            this.persistentDao.addPastTotal(pastTotal);
        }

        //根据信息判断用户当前的签到状态
        PastModel pastModel = this.getPastModelById();
        if (pastModel != null) {
            //修改为帮朋友干杯次数
            int times = this.getTodayOtherDrunkTimes(userModel.getPhone(), friend.getPhone());
            pastTotal.setToday_times(times);
            pastTotal.setToday_have_times(pastModel.getPast_times() - times);
            pastTotal.setTotal_drunk(pastModel.getTotal_drunk());
        }

        return pastTotal;
    }

    /**
     * 签到并获取最后的信息
     *
     * @param userModel
     * @return
     */
    public PastTotal pastSelf(UserModel userModel) {
        Preconditions.checkNotNull(userModel);
        PastModel pastModel = this.getPastModelById();
        if (pastModel != null) {
            //判断今天次数是否达到
            int times = this.getTodayDrunkTimes(userModel.getPhone());
            if (times < pastModel.getPast_times()) {
                //对PastTotal进行更新
                PastTotal pastTotal = this.persistentDao.getPastTotalByPhone(userModel.getPhone());

                PastDetail pastDetail = new PastDetail();
                pastDetail.setCreate_time(new Date());
                pastDetail.setDrunk_type(CONST.DRUNK_SELF);
                pastDetail.setPhone(userModel.getPhone());
                pastDetail.setUid(userModel.getUserid());
                int drunkNum=0;
                if (pastModel.getPast_type() == 1) {
                    //固定值
                    pastDetail.setDrunk_num(pastModel.getFix_drunk());
                    drunkNum=pastModel.getFix_drunk();
                } else {
                    //随机值
                    Random random = new Random();
                    drunkNum= random.nextInt((pastModel.getMax_drunk() - pastModel.getMin_drunk() + 1)) + pastModel.getMin_drunk();
                    pastDetail.setDrunk_num(drunkNum);
                }
                this.persistentDao.addPastDetail(pastDetail);

                if (pastTotal != null) {
                    pastTotal.setDrunk_num(drunkNum);
                    pastTotal.setCycle_drunk(pastTotal.getCycle_drunk() + pastDetail.getDrunk_num());
                    pastTotal.setToday_drunk(pastTotal.getToday_drunk() + pastDetail.getDrunk_num());
                    pastTotal.setCycle_times(pastTotal.getCycle_times() + 1);
                    pastTotal.setToday_times(pastTotal.getToday_times() + 1);
                    this.persistentDao.updatePastTotal(pastTotal);
                    if (pastTotal.getCycle_drunk() + pastDetail.getDrunk_num() >= pastModel.getTotal_drunk()) {
                        long couponId = pastModel.getCoupon_id();
                        CouponRecordModel couponRecordModel=couponService.getCouponBlanceByCouponId(couponId);
                        if(couponRecordModel!=null) {
                            raffleService.drawRaffleByRage(pastModel.getId(), couponRecordModel.getId(), userModel.getId(), userModel.getPhone());//发放优惠券
                        }
                    }
                }

            }
        }
        return this.getPastTotalByPhone(userModel);
    }

    /**
     * 与朋友相互干怀
     *
     * @param userModel
     * @param phone
     * @return
     */
    public PastTotal otherPast(UserModel userModel, String phone) {
            Preconditions.checkNotNull(userModel);
        Preconditions.checkNotNull(phone);
        UserModel mainUser = this.qblkService.getUserByPhone(phone);
        int total = this.getTodayOtherDrunkTimes(phone, userModel.getPhone());
        PastTotal pastTotal = this.persistentDao.getPastTotalByPhone(phone);
        if (total <= 0) {
            PastModel pastModel = getPastModelById();
            PastDetail pastDetail = new PastDetail();
            pastDetail.setCreate_time(new Date());
            pastDetail.setDrunk_type(CONST.DRUNK_OTHER);
            pastDetail.setPhone(phone);
            pastDetail.setUid(0);
            pastDetail.setFriend_phone(userModel.getPhone());
            pastDetail.setFriend_uid(userModel.getId());
            int drunk_num=0;
            if (pastModel.getPast_type() == 1) {
                //固定值
                pastDetail.setDrunk_num(pastModel.getFix_drunk());
                drunk_num=pastModel.getFix_drunk();
            } else {
                //随机值
                Random random = new Random();
                drunk_num=random.nextInt((pastModel.getMax_drunk() - pastModel.getMin_drunk() + 1)) + pastModel.getMin_drunk();
                pastDetail.setDrunk_num(drunk_num);
            }this.persistentDao.addPastDetail(pastDetail);

            if (pastTotal != null) {
                pastTotal.setDrunk_num(drunk_num);
                pastTotal.setCycle_drunk(pastTotal.getCycle_drunk() + drunk_num);
                pastTotal.setToday_other_drunk(pastTotal.getToday_other_drunk() + drunk_num);
                pastTotal.setCycle_times(pastTotal.getCycle_times() + 1);
                pastTotal.setToday_other_times(pastTotal.getToday_other_times() + 1);
                this.persistentDao.updatePastTotal(pastTotal);

                if (pastTotal.getCycle_drunk() + pastDetail.getDrunk_num() >= pastModel.getTotal_drunk()) {
                    long couponId = pastModel.getCoupon_id();
                    CouponModel couponModel=couponService.getCouponByCouponId(couponId);
                    CouponRecordModel couponRecordModel=couponService.getCouponBlanceByCouponId(couponId);
                    if (couponRecordModel != null&&couponModel!=null) {
                        raffleService.sendCoupon(couponModel,couponRecordModel, mainUser.getId(), phone);//发放优惠券
                    }
                }
            }

        }

        return getPastTotalByPhone(mainUser);
    }

    /**
     * 分页读取签到信息
     *
     * @param pageModel
     * @return
     */
    public PageResult<PastTotal> getPastTotalByPage(PageModel pageModel) {
        Preconditions.checkNotNull(pageModel);
        int total = this.persistentDao.totalPastTotal();

        List<PastTotal> pastTotals = this.persistentDao.getPastTotalByPage(pageModel.getStart(), pageModel.getSize());
        PageResult<PastTotal> pastTotalPageResult = new PageResult<PastTotal>();
        pastTotalPageResult.setData(pastTotals);
        pastTotalPageResult.setPageModel(pageModel);
        pastTotalPageResult.setTotal(total);
        return pastTotalPageResult;
    }

    /**
     * 清空每天的签到信息
     */
    public void clearEveryDayPastInfo() {
        //清空周期，判断是否已经在周期界点的了，如果是测周期清空
        PastModel pastModel = this.getPastModelById();
        if (pastModel != null) {
            int dayTotal = 0;
            if (pastModel.getCreate_time() != null) {
                try {
                    dayTotal = daysBetween(pastModel.getCreate_time(), new Date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (dayTotal % pastModel.getInterval_day() == 0) {
                //清空周期天数
                this.persistentDao.clearCyclePastTotal();
            }
        }

        //清空每在的数据情况
        this.persistentDao.clearPastTotal();
    }


    /**
     * 清除签到信息
     *
     * @param phone
     * @param status
     */
    public void clearPastInfo(String phone, int status) {
        Preconditions.checkNotNull(phone);
        Preconditions.checkNotNull(status > 0);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = null;
        switch (status) {
            case 1:
                //删除今天签到信息
                this.persistentDao.clearPastTotalForPhone(phone);
                //删除详情信息

                time = simpleDateFormat.format(new Date());
                this.persistentDao.deletePastDetailForPhoneandTime(phone, time + " 00:00:01", time + " 23:59:59");
                break;

            case 2:

                PastModel pastModel = this.getPastModelById();

                if (pastModel != null) {
                    int dayTotal = 0;
                    if (pastModel.getStart_time() != null) {
                        try {
                            dayTotal = daysBetween(pastModel.getStart_time(), new Date());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    int days = dayTotal % pastModel.getInterval_day();//签到周期
                    if (days >= 0) {//签到周期未完成
                        //删除周期签到信息
                        this.persistentDao.clearPastTotalForPhone(phone);
                        this.persistentDao.clearCyclePastTotalForPhone(phone);
                        //删除详情信息
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        calendar.add(Calendar.DAY_OF_YEAR, -1 * days);
                        String start_time = simpleDateFormat.format(calendar.getTime());
                        String end_time = simpleDateFormat.format(new Date());

                        this.persistentDao.deletePastDetailForPhoneandTime(phone, start_time + " 00:00:01", end_time + " 23:59:59");
                    }

                }

                break;

        }
    }

    public int getTodayDrunkTimes(String phone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(new Date());

        return this.persistentDao.countPastDetailByPhoneAndTime(phone, time + " 00:00:01", time + " 23:59:59");
    }

    /**
     * 获取两人互相干杯资数
     *
     * @param phone
     * @param otherPhone
     * @return
     */
    public int getTodayOtherDrunkTimes(String phone, String otherPhone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(new Date());
        return this.persistentDao.countPastDetailByPhoneAndOtherPhoneAndTime(phone, otherPhone, time + " 00:00:01", time + " 23:59:59");
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
