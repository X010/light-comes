package com.light.outside.comes.mybatis.mapper;

import com.light.outside.comes.model.BanquetRecordModel;
import org.apache.ibatis.annotations.*;

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
public interface BanquetDao {

    @Select("select * from comes_banquet_records where aid=#{aid} and phone=#{phone} limit 1")
    public BanquetRecordModel getBanquetRecordByAidAndPhone(@Param("aid") long aid, @Param("phone") String phone);


    @Update("update comes_banquet_records set status=#{status},orderNo=#{orderNo} where id=#{id}")
    public void updateBanquetRecordModel(BanquetRecordModel banquetRecordModel);


    @Insert("insert into comes_banquet_records(status,title,amount,phone,uid,aid,orderNo,createtime)" +
            "values(#{status},#{title},#{amount},#{phone},#{uid},#{aid},#{orderNo},#{createtime})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public void addBanquetRecordModel(BanquetRecordModel banquetRecordModel);


}
