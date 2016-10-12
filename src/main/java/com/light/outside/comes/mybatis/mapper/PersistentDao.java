package com.light.outside.comes.mybatis.mapper;

import com.light.outside.comes.model.CouponModel;
import com.light.outside.comes.model.RaffleCouponModel;
import com.light.outside.comes.model.RaffleModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

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

    @Select("select * from comes_coupon where id=#{id}")
    public CouponModel getCouponById(@Param("id") long id);


    @Insert("insert into comes_raffle_coupon(title,cid,price,ctype,winrate,memo,rid,cindex)values" +
            "(#{title},#{cid},#{price},#{ctype},#{winrate},#{memo},#{rid},#{cindex})")
    public void addRaffleCoupon(RaffleCouponModel raffleCouponModel);

    @Select("select * from comes_coupon order by id desc limit #{start},#{size}")
    public List<CouponModel> getCoupons(@Param("start") int start, @Param("size") int size);

    @Select("select * from comes_coupon where status=#{status} order by id desc ")
    public List<CouponModel> getCouponsByStatus(@Param("status") int status);

    @Insert("insert into comes_raffle(title,start_time,end_time,memo,photo,createtime,status,times)" +
            "values(#{title},#{start_time},#{end_time},#{memo},#{photo},#{createtime},#{status},#{times})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addRaffle(RaffleModel raffleModel);

    @Select("select count(1) from comes_coupon ")
    public int couponsTotal();
}
