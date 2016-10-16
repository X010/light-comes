package com.light.outside.comes.qbkl.dao;

import com.light.outside.comes.qbkl.model.Commodity;
import com.light.outside.comes.qbkl.model.CommodityCategory;
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

    /**
     * 根据关键字查询
     *
     * @param keyword
     * @param size
     * @return
     */
    @Select("select * from t_goods where name like #{keyword} limit 1,#{size}")
    public List<Commodity> getCommodityByKeyword(@Param("keyword") String keyword, @Param("size") int size);


    /**
     * 根据父级名称获取子级名称
     * @param name
     * @return
     */
    @Select("select * from t_goods_category where category1=#{name}")
    public List<CommodityCategory> getCommodityCategoryByParentName(@Param("name") String name);

    /**
     * 获取一级分类名称
     * @return
     */
    @Select("select DISTINCT(category1) as category1  from t_goods_category")
    public List<CommodityCategory> getParentCommodityCateory();
}
