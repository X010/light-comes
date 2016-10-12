package com.light.outside.comes.service;

import com.light.outside.comes.model.CouponModel;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
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
public class RaffleService {


    @Autowired
    private PersistentDao persistentDao;


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
     * @param couponModel
     */
    public void generateCoupon(CouponModel couponModel) {

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
}
