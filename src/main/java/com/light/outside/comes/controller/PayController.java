package com.light.outside.comes.controller;

import com.light.outside.comes.service.WeiXinPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
@Controller
@RequestMapping("yeshizuileweixin/Cart")
public class PayController {

    @Autowired
    private WeiXinPayService weiXinPayService;


    private static final Logger LOG = LoggerFactory.getLogger(PayController.class);


    /**
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("h5weixin.action")
    public String h5WeixinPay(Map<String, Object> data, HttpServletRequest request) {

        return "H5Weixin";
    }


}
