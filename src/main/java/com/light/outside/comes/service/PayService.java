package com.light.outside.comes.service;

import com.light.outside.comes.model.OrderModel;

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
public interface PayService {

    /**
     * 创建需要支付订单
     *
     * @param orderModel
     */
    public long createOrder(OrderModel orderModel);

    /**
     * 修改订单
     * @param orderModel
     * @return
     */
    public long updateOrder(OrderModel orderModel);

    /**
     * 根据ID获取订单
     *
     * @param id
     * @return
     */
    public OrderModel getOrderById(long id);

    /**
     * 根据用户ID和拍卖ID获取订单
     * @param uid
     * @param aid
     * @return
     */
    public OrderModel getOrderByUidAndAid(long uid,long aid,int atype);


    /**
     * 根据ID修改订单状态
     *
     * @param id
     * @param status
     * @return
     */
    public OrderModel updateOrder(long id, int status);

    /**
     * 根据订单号修改订单状态
     * @param orderNo
     * @return
     */
    public OrderModel updateOrderByOrderno(String orderNo,String tradeno,String transaction_id,int status);

    /**
     * 根据商户订单号获取订单
     * @param tradeno
     * @return
     */
    public OrderModel getOrderByTradeno(String tradeno);

    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     */
    public OrderModel getOrderByOrderNo(String orderNo);

    /**
     * 退款
     * @param out_trade_no
     * @param transaction_id
     * @param out_refund_no
     * @param payMoney
     * @param refund_fee
     * @return
     */
    public OrderModel orderRefund(String out_trade_no,String transaction_id,String out_refund_no,String payMoney,String refund_fee);
}

