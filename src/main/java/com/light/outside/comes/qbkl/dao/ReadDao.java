package com.light.outside.comes.qbkl.dao;

import com.light.outside.comes.qbkl.model.Commodity;
import com.light.outside.comes.qbkl.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface ReadDao {

    /**
     * 根据手机号码获取用户
     *
     * @param phone
     * @return
     */
    UserModel getUserByPhone(@Param("phone") String phone);

    /**
     * 根据商品名查询商品
     * @return
     */
    @Select("select id goodsid,name,price from t_goods where name like #{name}")
    List<Commodity> queryCommodity(@Param("name") String name);
}
