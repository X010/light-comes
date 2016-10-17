package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.light.outside.comes.model.AuctionModel;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.model.Commodity;
import com.light.outside.comes.qbkl.service.QblkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

            this.persistentDao.addAuction(auctionModel);
        }
    }

}
