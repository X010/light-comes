package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.light.outside.comes.model.OverchargedModel;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.dao.ReadDao;
import com.light.outside.comes.qbkl.model.Commodity;
import com.light.outside.comes.utils.CONST;
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
public class OverchargedService {


    @Autowired
    private PersistentDao persistentDao;


    @Autowired
    private ReadDao readDao;

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
}
