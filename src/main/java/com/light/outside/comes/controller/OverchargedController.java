package com.light.outside.comes.controller;

import com.light.outside.comes.model.OverchargedModel;
import com.light.outside.comes.qbkl.model.Commodity;
import com.light.outside.comes.service.OverchargedService;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
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
@RequestMapping("admin")
public class OverchargedController {

    @Autowired
    private OverchargedService overchargedService;

    /**
     * 创建砍价商品
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("create_overcharge.action")
    public String create_overcharge(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {

        return "admin/create_overcharge";
    }

    @RequestMapping("create_overcharge_save.action")
    public String create_overcharge_save(Map<String, Object> data,OverchargedModel overchargedModel, HttpServletRequest request, HttpServletResponse response) {
        long sku_id= RequestTools.RequestLong(request,"sku_id",0);
        if(overchargedModel!=null){
            overchargedModel.setSku_id(sku_id);
            overchargedModel.setCreate_time(new Date());
            overchargedModel.setStatus(0);
            int count=overchargedService.saveOvercharged(overchargedModel);
            System.out.println(count);
        }
        return "admin/create_overcharge";
    }

    @RequestMapping("query_goods.action")
    @ResponseBody
    public String queryGoodsByName(HttpServletRequest request, HttpServletResponse response){
        String name=request.getParameter("name");
        List<Commodity> commodityList= overchargedService.queryCommodityByName(name);
        return JsonTools.jsonSer(commodityList);
    }

    @RequestMapping("overcharged_list.action")
    public String queryOverchargeds(HttpServletRequest request, HttpServletResponse response){

        return "";
    }
}
