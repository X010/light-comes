package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.light.outside.comes.model.OrderModel;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.utils.CONST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
public class WeiXinPayService implements PayService {

    @Autowired
    private PersistentDao persistentDao;

    private final static Logger LOG = LoggerFactory.getLogger(WeiXinPayService.class);

    @Override
    public long createOrder(OrderModel orderModel) {
        Preconditions.checkNotNull(orderModel);
        return this.persistentDao.addOrder(orderModel);
    }

    @Override
    public OrderModel getOrderById(long id) {
        Preconditions.checkArgument(id > 0);
        return this.persistentDao.getOrderById(id);
    }

    @Override
    public OrderModel updateOrder(long id, int status) {
        Preconditions.checkArgument(id > 0);
        OrderModel updateModel = this.getOrderById(id);
        if (updateModel != null) {
            updateModel.setStatus(status);
            if (status == CONST.ORDER_PAY) {
                updateModel.setPaytime(new Date());
            }
            this.persistentDao.updateOrder(updateModel);
        }
        return updateModel;
    }
}
