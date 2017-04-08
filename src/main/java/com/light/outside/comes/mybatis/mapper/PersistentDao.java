package com.light.outside.comes.mybatis.mapper;

import com.light.outside.comes.model.*;
import org.apache.ibatis.annotations.*;

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
public interface PersistentDao {

    @Insert("insert into comes_coupon(title,createtime,use_start_time,use_end_time,num,ctype,mid,price,status)" +
            "values(#{title},#{createtime},#{use_start_time},#{use_end_time},#{num},#{ctype},#{mid},#{price},#{status})")
    public void addCoupon(CouponModel couponModel);

    @Update("update comes_coupon set title=#{title},use_start_time=#{use_start_time},use_end_time=#{use_end_time},status=#{status} where id=#{id}")
    public void editCoupon(CouponModel couponModel);

    @Update("update comes_conpon_records set status=#{status} where cid=#{cid}")
    public void editCouponRecordStatus(@Param("cid") long cid, @Param("status") int status);

    @Insert("insert into comes_coupon_records_used(coupon_record_id,cardno,uid,used_time,source_uid,source_phone,to_phone,`status`,coupon_title,price) " +
            " values (#{coupon_record_id},#{cardno},#{uid},now(),#{source_uid},#{source_phone},#{to_phone},#{status},#{coupon_title},#{price})")
    public int addCouponUsedRecord(CouponUsedRecord couponUsedRecord);

    @Update("update comes_conpon_records set status=#{status},uid=#{uid},phone=#{phone}, updatetime=now() where id=#{id}")
    public void editCouponRecordStatusByUser(@Param("id") long id, @Param("status") int status, @Param("uid") long uid, @Param("phone") String phone);

    @Update("update comes_conpon_records set status=#{status} where cardno=#{cardno}")
    public void editCouponRecordStatusByCardno(@Param("cardno") String cardno, @Param("status") int status);

    @Select("select * from comes_conpon_records where id=#{id}")
    public CouponRecordModel getCouponRecordById(@Param("id") long id);

    @Select("select * from comes_conpon_records where cid=#{cid} and status=#{status} and uid=0  order  by id desc limit #{star},#{size}")
    public List<CouponRecordModel> getCouponRecordModelByCid(@Param("cid") long cid, @Param("status") int status, @Param("star") int star, @Param("size") int size);

    @Select("select * from comes_coupon where id=#{id}")
    public CouponModel getCouponById(@Param("id") long id);


    @Insert("insert into comes_conpon_records(title,createtime,use_start_time,use_end_time,cardno,uid,phone,status,price,mid,ctype,cid)" +
            "values(#{title},#{createtime},#{use_start_time},#{use_end_time},#{cardno},#{uid},#{phone},#{status},#{price},#{mid},#{ctype},#{cid})")
    public void addCouponRecord(CouponRecordModel couponRecordModel);


    @Insert("insert into comes_raffle_coupon(title,cid,price,ctype,winrate,memo,rid,cindex)values" +
            "(#{title},#{cid},#{price},#{ctype},#{winrate},#{memo},#{rid},#{cindex})")
    public void addRaffleCoupon(RaffleCouponModel raffleCouponModel);

    @Select("select * from comes_coupon where status<>9  order by id desc limit #{start},#{size}")
    public List<CouponModel> getCoupons(@Param("start") int start, @Param("size") int size);

    @Select("select * from comes_coupon where status=#{status} order by id desc ")
    public List<CouponModel> getCouponsByStatus(@Param("status") int status);

    @Select("select * from comes_raffle where id=#{id}")
    public RaffleModel getRaffleById(@Param("id") long id);

    @Select("select * from comes_raffle where status<>9 order by id desc limit  #{start},#{size}")
    public List<RaffleModel> getRaffles(@Param("start") int start, @Param("size") int size);


    @Select("select crc.*,count(ccr.id) quantity  from comes_raffle_coupon crc left join `comes_conpon_records` ccr " +
            "on crc.cid=ccr.cid " +
            "WHERE crc.rid=#{rid} " +
            "and ccr.`status`<>9 " +
            "group by rid,cid")
    public List<RaffleCouponModel> getRaffleCouponsByRaffleId(@Param("rid") long rid);

    @Select("select * from comes_raffle_coupon where rid=#{rid}")
    public List<RaffleCouponModel> getRaffleCouponByRaffleId(@Param("rid") long rid);


    /**
     * 查询优惠券
     *
     * @param uid
     * @param status
     * @return
     */
    @Select("select * from comes_conpon_records where uid=#{uid} and `status`=#{status}")
    public List<CouponRecordViewModel> getRaffleCouponByUserStatus(@Param("uid") long uid, @Param("status") int status);

    /**
     * 分页我的查询优惠券
     *
     * @param uid
     * @param status
     * @param start
     * @param size
     * @return
     */
    @Select("select * from comes_conpon_records where uid=#{uid} and `status`=#{status} limit #{start},#{size}")
    public List<CouponRecordViewModel> getRaffleCouponPageByUserStatus(@Param("uid") long uid, @Param("status") int status, @Param("start") int start, @Param("size") int size);

    /**
     * 分页查询所有状态优惠券
     *
     * @param uid
     * @param start
     * @param size
     * @return
     */
    @Select("select * from comes_conpon_records where uid=#{uid}  limit #{start},#{size}")
    public List<CouponRecordViewModel> getRaffleCouponPageByUser(@Param("uid") long uid, @Param("start") int start, @Param("size") int size);

    /**
     * 查询收到的优惠券
     *
     * @param uid
     * @param start
     * @param size
     * @return
     */
    //@Select("select * from commes_coupon_records_useds where uid=#{uid}  limit #{start},#{size}")
    @Select("select r.* from comes_coupon_records_used ru,comes_conpon_records r " +
            " where r.id=ru.coupon_record_id and ru.uid=#{uid} limit #{start},#{size}")
    public List<CouponRecordViewModel> getUsedRaffleCouponPageByUser(@Param("uid") long uid, @Param("start") int start, @Param("size") int size);

    /**
     * 查询所有优惠券
     *
     * @param uid
     * @return
     */
    @Select("select * from comes_conpon_records where uid=#{uid}")
    public List<CouponRecordViewModel> getRaffleCouponByUser(@Param("uid") long uid);

    /**
     * 根据优惠劵ID获取
     *
     * @param cid
     * @return
     */
    @Select("select count(1) from  comes_conpon_records where cid=#{cid}")
    public int getCouponRecordByCidTotal(@Param("cid") long cid);


    @Select("select * from comes_conpon_records where cid=#{cid} order by id desc limit #{start},#{size}")
    public List<CouponRecordModel> getCouponRecordByCid(@Param("cid") long cid, @Param("start") int start, @Param("size") int size);

    /**
     * 查询抽奖次数
     *
     * @param rid
     * @param uid
     * @return
     */
    @Select("select * from comes_raffle_user where uid=#{uid} and rid=#{rid} and raffle_date=current_date()")
    public RaffleUserModel getRaffleUserByRaffleId(@Param("rid") long rid, @Param("uid") long uid);

    /**
     * 增加或者新建抽奖次数
     *
     * @param uid
     * @param rid
     * @param count
     * @return
     */
    @Insert("insert into comes_raffle_user(uid,rid,count,raffle_date) values(#{uid},#{rid},#{count},current_date()) ON DUPLICATE KEY UPDATE count=count+1")
    public int updateRaffleUserByRaffleId(@Param("uid") long uid, @Param("rid") long rid, @Param("count") int count);

    @Select("select distinct ccr.id id,ccr.title title,concat(left(ccr.phone,3),'****',right(phone,4)) as phone,ccr.uid uid,ccr.cid cid from comes_conpon_records ccr, comes_raffle_coupon crc " +
            "where ccr.cid=crc.cid " +
            "and crc.rid=#{rid} " +
            "and ccr.`status`=#{status} " +
            "and ccr.uid>0 " +
            "order by ccr.createtime desc " +
            "limit #{start},#{size}")
    public List<CouponRecordModel> getRaffleCouponByRaffleIdAndStatus(@Param("rid") long rid, @Param("status") int status, @Param("start") int start, @Param("size") int size);

    @Select("select distinct ccr.id id,ccr.title title,concat(left(ccr.phone,3),'****',right(phone,4)) as phone,ccr.uid uid,ccr.cid cid from comes_conpon_records ccr, comes_raffle_coupon crc " +
            "where ccr.cid=crc.cid " +
            "and crc.rid=#{rid} " +
            "and ccr.uid>0 " +
            "order by ccr.createtime desc " +
            "limit #{start},#{size}")
    public List<CouponRecordModel> getRaffleCouponRecordByRaffleId(@Param("rid") long rid, @Param("start") int start, @Param("size") int size);

    @Delete("delete from comes_raffle_coupon where rid=#{rid}")
    public void deleteRaffleCouponByRaffleId(@Param("rid") long rid);

    @Select("select * from comes_raffle_coupon where id=#{id}")
    public RaffleCouponModel getRaffleCouponById(@Param("id") long id);

    @Insert("insert into comes_raffle(title,start_time,end_time,memo,photo,createtime,status,times)" +
            "values(#{title},#{start_time},#{end_time},#{memo},#{photo},#{createtime},#{status},#{times})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addRaffle(RaffleModel raffleModel);

    @Update("update comes_raffle set title=#{title},start_time=#{start_time},end_time=#{end_time},memo=#{memo}," +
            "photo=#{photo},status=#{status},times=#{times} where id=#{id}")
    public void editRaffle(RaffleModel raffleModel);

    @Select("select count(1) from comes_raffle where status<>9")
    public int rafflesTotal();

    @Select("select count(1) from comes_coupon where status<>9")
    public int couponsTotal();

    @Select("select count(1) from comes_banquet where status<>9")
    public int banquetTotal();

    @Select("select * from comes_banquet where status<>9 order  by id desc  limit #{start},#{size}")
    public List<BanquetModel> getBanquets(@Param("start") int start, @Param("size") int size);

    @Select("select * from comes_banquet where id=#{id}")
    public BanquetModel getBanquetById(@Param("id") long id);

    @Insert("insert into comes_banquet(title,amount,outnumber,create_time,start_time,author_nickname,author_telephone,memo,status,author_address,info,end_time,photo,banquet_time,total_number)" +
            "values(#{title},#{amount},#{outnumber},#{create_time},#{start_time},#{author_nickname},#{author_telephone},#{memo},#{status},#{author_address},#{info},#{end_time},#{photo},#{banquet_time},#{total_number})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addBanquet(BanquetModel banquetModel);


    @Update("update comes_banquet set  title=#{title},amount=#{amount},outnumber=#{outnumber},start_time=#{start_time},end_time=#{end_time},author_nickname=#{author_nickname}," +
            "author_telephone=#{author_telephone},memo=#{memo},status=#{status},author_address=#{author_address},info=#{info},photo=#{photo},banquet_time=#{banquet_time},total_number=#{total_number} where id=#{id}")
    public void updateBanquet(BanquetModel banquetModel);

    @Select("select count(1) from comes_auction where status<>9")
    public int auctionTotal();

    @Select("select * from comes_auction where status<>9 order by id desc limit #{start},#{size}")
    public List<AuctionModel> getAuctions(@Param("start") int start, @Param("size") int size);

    @Select("select * from comes_auction where id=#{id}")
    public AuctionModel getAuctionById(@Param("id") long id);

    @Insert("insert into comes_auction(title,amount,status,deposit,setp_amount,time_second,create_time,goodsid,start_time,end_time,good_photo,good_name,memo)" +
            "values(#{title},#{amount},#{status},#{deposit},#{setp_amount},#{time_second},#{create_time},#{goodsid},#{start_time},#{end_time},#{good_photo},#{good_name},#{memo})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addAuction(AuctionModel auctionModel);

    @Update("update comes_auction  set title=#{title},amount=#{amount},status=#{status},deposit=#{deposit},setp_amount=#{setp_amount},time_second=#{time_second}," +
            "start_time=#{start_time},end_time=#{end_time},memo=#{memo},win_uid=#{win_uid},win_phone=#{win_phone},win_price=#{win_price} where id=#{id}")
    public void updateAuction(AuctionModel auctionModel);

    @Insert("insert into comes_overcharged(create_time,amount,subtract_price,title,status,goodsid,start_time,end_time,good_photo,good_name,over_amount)" +
            "values(#{create_time},#{amount},#{subtract_price},#{title},#{status},#{goodsid},#{start_time},#{end_time},#{good_photo},#{good_name},#{over_amount})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addOvercharged(OverchargedModel overchargedModel);


    @Update("update comes_overcharged set amount=#{amount},subtract_price=#{subtract_price},title=#{title},status=#{status},start_time=#{start_time},end_time=#{end_time},over_amount=#{over_amount}  where id=#{id}")
    public void updateOvercharged(OverchargedModel overchargedModel);

    @Select("select count(1) from comes_overcharged where status<>9")
    public int overchargedTotal();

    @Select("select * from comes_overcharged where status<>9 order by id desc limit #{start},#{size}")
    public List<OverchargedModel> getOverchargeds(@Param("start") int start, @Param("size") int size);

    @Select("select * from comes_overcharged where id=#{id}")
    public OverchargedModel getOverchargedById(@Param("id") long id);

    @Insert("insert into comes_blacklist(status,createtime,phone,ctype)values(#{status},#{createtime},#{phone},#{ctype})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addBackList(BackList backList);

    @Select("select * from  comes_blacklist where id=#{id}")
    public BackList getBackListById(@Param("id") long id);

    @Update("update comes_blacklist set status=#{status} where id=#{id}")
    public void updateBackList(BackList backList);

    @Select("select * from comes_blacklist where phone=#{phone} and ctype=#{ctype} limit 1")
    public BackList getBackListByPhoneAndCtype(@Param("phone") String phone, @Param("ctype") int ctype);

    @Select("select * from comes_blacklist where status<>9 order  by id desc  limit #{start},#{size}")
    public List<BackList> getBackLists(@Param("start") int start, @Param("size") int size);

    @Select("select count(1) from comes_blacklist where status<>9")
    public int totalBackList();

    @Insert("insert into comes_order(amount,status,atype,aname,ptype,phone,uid,createtime,paytime,aid,orderno,tradeno)" +
            "values(#{amount},#{status},#{atype},#{aname},#{ptype},#{phone},#{uid},#{createtime},#{paytime},#{aid},#{orderNo},#{tradeno})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addOrder(OrderModel orderModel);

    @Select("select * from comes_order where id=#{id}")
    public OrderModel getOrderById(@Param("id") long id);

    @Update("update comes_order set status=#{status},paytime=#{paytime} where id=#{id}")
    public void updateOrder(OrderModel orderModel);

    @Update("update comes_order set status=#{status},paytime=#{paytime},tradeno=${tradeno},transactionId=${transactionId} where orderno=#{orderNo}")
    public void updateOrderByOrderno(OrderModel orderModel);

    /**
     * 根据用户ID和拍卖ID查询
     *
     * @param uid
     * @param aid
     * @return
     */
    @Select("select * from comes_order where uid=#{uid} and aid=#{aid} and atype=#{atype}")
    public OrderModel getOrderByUidAndAid(@Param("uid") long uid, @Param("aid") long aid,@Param("atype") int atype);

    /**
     * 根据订单ID查询
     * @param orderno
     * @return
     */
    @Select("select * from comes_order where orderno=#{orderno}")
    public OrderModel getOrderByOrderNo(@Param("orderno") String orderno);


    @Select("select * from comes_order where tradeno=#{tradeno}")
    public OrderModel getOrderByTradeno(@Param("tradeno") String tradeno);

    @Select("select count(1) from comes_conpon_records where cid=#{cid} and phone  is not null")
    public int getCouponSendNum(@Param("cid") long id);

    /**
     * 使用过优惠劵
     *
     * @param id
     * @return
     */
    @Select("select count(1) from comes_conpon_records where cid=#{cid} and status=#{status} and  phone  is not null")
    public int getCouponUseNum(@Param("cid") long id, @Param("status") int status);


    /**
     * 根据手机号与状态获取消费过的优惠劵
     *
     * @param phone
     * @param status
     * @return
     */
    @Select("select * from comes_coupon_records_used where to_phone=#{phone} and status=#{status} order by used_time desc")
    public List<CouponUsedRecord> getCouponeUsedRecordByPhone(@Param("phone") String phone, @Param("status") int status);

    /**
     * 根据ID获取记录
     *
     * @param id
     * @return
     */
    @Select("select * from comes_coupon_records_used where id=#{id}")
    public CouponUsedRecord getCouponUsedRecordById(@Param("id") long id);


    @Insert("insert into comes_coupon_balance_bill(total_price,create_time,phone)values(#{total_price},#{create_time},#{phone})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addCouponBill(CouponBill couponBill);


    @Update("update comes_coupon_records_used set status=#{status},bill_id=#{bill_id} where id=#{id}")
    public void updateCouponeUsedRecord(CouponUsedRecord couponUsedRecord);


    @Select("select * from comes_coupon_records_used where bill_id=#{bill_id} order by id desc")
    public List<CouponUsedRecord> getCouponUsedByBillid(@Param("bill_id") long bill_id);

    @Select("select count(1) from comes_coupon_balance_bill")
    public int couponBillTotal();


    @Select("select * from comes_coupon_balance_bill limit #{start},#{size}")
    public List<CouponBill> getCouponBill(@Param("start") int start, @Param("size") int size);


    @Select("select * from comes_past where id=#{id} limit 1")
    public PastModel getPastById(@Param("id") long id);


    @Insert("insert into comes_past(id,interval_day,min_drunk,max_drunk,total_drunk,past_times,coupon_id,prizes_name,past_type,fix_drunk)values(#{id}," +
            "#{interval_day},#{min_drunk},#{max_drunk},#{total_drunk},#{past_times},#{coupon_id},#{prizes_name},#{past_type},#{fix_drunk})")
    public void addPast(PastModel pastModel);


    @Update("update comes_past set interval_day=#{interval_day},min_drunk=#{min_drunk},max_drunk=#{max_drunk},total_drunk=#{total_drunk},past_times=#{past_times}," +
            "coupon_id=#{coupon_id},prizes_name=#{prizes_name},past_type=#{past_type},fix_drunk=#{fix_drunk}  where id=#{id}")
    public void updatePast(PastModel pastModel);

    /**
     * 查询用户干杯数量
     *
     * @param uid
     * @return
     */
    @Select("select * from comes_past_total where uid=#{uid}")
    public PastTotal getPastTotalByUser(@Param("uid") long uid);

    /**
     * 根据用户手机号获取用户的干杯信息
     *
     * @param phone
     * @return
     */
    @Select("select * from comes_past_total where phone=#{phone}")
    public PastTotal getPastTotalByPhone(@Param("phone") String phone);

    /**
     * 修改总数
     *
     * @param pastTotal
     * @return
     */
    @Update("update comes_past_total set today_times=#{today_times},today_drunk=#{today_drunk},cycle_times=#{cycle_times},cycle_drunk=#{cycle_drunk}" +
            ",today_other_times=#{today_other_times},today_other_drunk=#{today_other_drunk},update_time=now()" +
            " where phone=#{phone}")
    public int updatePastTotal(PastTotal pastTotal);

    /**
     * 添加PastTotal信息
     *
     * @param pastTotal
     * @return
     */
    @Insert("insert into comes_past_total(uid,phone,today_times,today_drunk,cycle_times,cycle_drunk,today_other_times,today_other_drunk,update_time)values" +
            "(#{uid},#{phone},#{today_times},#{today_drunk},#{cycle_times},#{cycle_drunk},#{today_other_times},#{today_other_drunk},now())")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addPastTotal(PastTotal pastTotal);

    /**
     * 详情
     *
     * @param pastDetail
     * @return
     */
    @Insert("insert into comes_past_detail(create_time,uid,phone,friend_uid,friend_phone,drunk_type,drunk_num)" +
            "values(now(),#{uid},#{phone},#{friend_uid},#{friend_phone},#{drunk_type},#{drunk_num})")
    public int addPastDetail(PastDetail pastDetail);

    /**
     * 统计当前签到次数
     *
     * @param phone
     * @param start_time
     * @param end_time
     * @return
     */
    @Select("select count(1) from comes_past_detail where phone=#{phone} and create_time>=#{start_time} and create_time<=#{end_time}")
    public int countPastDetailByPhoneAndTime(@Param("phone") String phone, @Param("start_time") String start_time, @Param("end_time") String end_time);


    @Select("select count(1) from comes_past_detail where phone=#{phone} and friend_phone=#{other}  and create_time>=#{start_time} and create_time<=#{end_time}")
    public int countPastDetailByPhoneAndOtherPhoneAndTime(@Param("phone") String phone,@Param("other") String other, @Param("start_time") String start_time, @Param("end_time") String end_time);

    @Update("update comes_past_total set today_other_times=0,today_other_drunk=0,today_drunk=0,today_times=0")
    public void clearPastTotal();

    /**
     * 清空周期签到数据
     */
    @Update("update comes_past_total set cycle_times=0,cycle_drunk=0")
    public void clearCyclePastTotal();


    @Update("update comes_past_total set today_other_times=0,today_other_drunk=0,today_drunk=0,today_times=0 where phone=#{phone}")
    public void clearPastTotalForPhone(@Param("phone") String phone);

    /**
     * 清空周期签到数据
     */
    @Update("update comes_past_total set cycle_times=0,cycle_drunk=0 where phone=#{phone}")
    public void clearCyclePastTotalForPhone(@Param("phone") String phone);

    /**
     * 根据号码与时间范围内的数据
     *
     * @param phone
     * @param start_time
     * @param end_time
     */
    @Delete("delete from comes_past_detail where phone=#{phone} and create_time>=#{start_time} and  create_time<=#{end_time}")
    public void deletePastDetailForPhoneandTime(@Param("phone") String phone, @Param("start_time") String start_time, @Param("end_time") String end_time);

    /**
     * 统计签到信息总数
     *
     * @return
     */
    @Select("select count(1) from comes_past_total")
    public int totalPastTotal();


    /**
     * 分页获取PastTotal信息
     *
     * @param start
     * @param size
     * @return
     */
    @Select("select * from comes_past_total order by today_times desc limit #{start},#{size}")
    public List<PastTotal> getPastTotalByPage(@Param("start") int start, @Param("size") int size);
}
