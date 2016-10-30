package com.light.outside.comes.controller;

import com.light.outside.comes.model.OverchargedModel;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.service.OverchargedService;
import com.light.outside.comes.service.admin.FocusImageService;
import com.light.outside.comes.utils.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("oc")
public class OverchargedController {

    @Autowired
    private OverchargedService overchargedService;

    @Autowired
    private FocusImageService focusImageService;

    /**
     * 砍价List
     *
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("overcharged.action")
    public String overcharged(Map<String, Object> data, HttpServletRequest request) {
        //输出焦点图
        List<FocusImageModel> focusImageModelList = this.focusImageService.queryFocusImageByColumn(CONST.FOCUS_OVERCHARGER);
        if (focusImageModelList != null) {
            data.put("focus", focusImageModelList);
        }
        PageModel pageModel = new PageModel();
        pageModel.setPage(1);
        pageModel.setSize(Integer.MAX_VALUE);
        PageResult<OverchargedModel> auctionModelPageResult = overchargedService.getOverchargeds(pageModel);
        List<OverchargedModel> overchargedModels = auctionModelPageResult.getData();
        if (overchargedModels != null) {
            data.put("oc", overchargedModels);
        }
        return "overcharged";
    }

    /**
     * 砍价说情页面
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("overcharged_d.action")
    public String overcharged_d(Map<String,Object> data,HttpServletRequest request)
    {
        return "overcharged_d";
    }
}
