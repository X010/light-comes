package com.light.outside.comes.qbkl.service;

import com.google.common.base.Preconditions;
import com.light.outside.comes.qbkl.dao.ReadDao;
import com.light.outside.comes.qbkl.model.Commodity;
import com.light.outside.comes.qbkl.model.CommodityCategory;
import com.light.outside.comes.qbkl.model.UserModel;
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
public class QblkService {

    @Autowired
    private ReadDao readDao;


    /**
     * 根据手机号码获取用户
     *
     * @param phone
     * @return
     */
    public UserModel getUserByPhone(String phone) {
        return this.readDao.getUserByPhone(phone);
    }


    /**
     * 根据关键字查询商品
     *
     * @param keyword
     * @return
     */
    public List<Commodity> getCommodityByKeyword(String keyword) {
        Preconditions.checkNotNull(keyword);
        List<Commodity> commodities = this.readDao.getCommodityByKeyword("%" + keyword + "%", 10);

        return commodities;
    }

    /**
     * 获取商品
     *
     * @param id
     * @return
     */
    public Commodity getCommodityById(long id) {
        return this.readDao.getCommodityById(id);
    }


    public List<CommodityCategory> getCommodityCategoryByCategoryName(String categoryname) {
        Preconditions.checkNotNull(categoryname);
        List<CommodityCategory> commodityCategories = this.readDao.getCommodityCategoryByParentName(categoryname);
        return commodityCategories;
    }

    /**
     * 获取一分类名称
     *
     * @return
     */
    public List<CommodityCategory> getParentCommodityCategory() {
        return this.readDao.getParentCommodityCateory();
    }


}
