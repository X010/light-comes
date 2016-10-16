package com.light.outside.comes.controller;

import com.light.outside.comes.model.FocusImageModel;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.model.RaffleModel;
import com.light.outside.comes.service.FocusImageService;
import com.light.outside.comes.service.RaffleService;
import com.light.outside.comes.utils.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("raffle")
public class RaffleController {

    @Autowired
    private RaffleService raffleService;


    @Autowired
    private FocusImageService focusImageService;

    /**
     * 抽奖列表
     *
     * @return
     */
    @RequestMapping("lottery.action")
    public String lottery(Map<String, Object> data) {
        //输出焦点图
        List<FocusImageModel> focusImageModelList = this.focusImageService.queryFocusImageByColumn(CONST.FOCUS_RAFFLE);
        if (focusImageModelList != null) {
            data.put("focus", focusImageModelList);
        }

        //输出抽奖活动列表
        PageModel pageModel = new PageModel();
        pageModel.setPage(1);
        pageModel.setSize(Integer.MAX_VALUE);
        PageResult<RaffleModel> raffleModelPageResult = this.raffleService.getRaffles(pageModel);
        List<RaffleModel> raffleModels = raffleModelPageResult.getData();
        if (raffleModels != null) {
            data.put("raffles", raffleModels);
        }
        return "lottery";
    }

    /**
     * 抽奖
     *
     * @return
     */
    @RequestMapping("lottery_d.action")
    public String lottery_d() {
        return "lottery_d";
    }
}
