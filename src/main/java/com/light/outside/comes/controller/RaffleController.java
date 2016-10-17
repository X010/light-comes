package com.light.outside.comes.controller;

import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.model.RaffleModel;
import com.light.outside.comes.service.admin.FocusImageService;
import com.light.outside.comes.service.RaffleService;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.JsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
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

    private List<Gift> gifts = new LinkedList<Gift>();

    @RequestMapping("lottery_draw.action")
    @ResponseBody
    public String lottery_draw(Map<String, Object> data,HttpServletRequest request,HttpServletRequest response){
        initGift();
        data.put("code",1);
        Gift gift=getGift();
        System.out.println(gift.toString());
        return JsonTools.jsonSer(data);
    }

    private void initGift(){
        GiftType giftType=new GiftType("1000",1,1);
        Gift gift=new Gift("1",giftType);

        GiftType giftType1=new GiftType("1000",1,1);
        Gift gift1=new Gift("2",giftType1);

        GiftType giftType2=new GiftType("100",10,1);
        Gift gift2=new Gift("3",giftType2);

        GiftType giftType3=new GiftType("50",20,10);
        Gift gift3=new Gift("4",giftType3);

        GiftType giftType4=new GiftType("20",50,20);
        Gift gift4=new Gift("5",giftType4);

        GiftType giftType5=new GiftType("10",99,50);
        Gift gift5=new Gift("6",giftType5);

        GiftType giftType6=new GiftType("1",99999,80);
        Gift gift6=new Gift("7",giftType6);

        GiftType giftType7=new GiftType("谢谢参与",99999999,100);
        Gift gift7=new Gift("8",giftType7);

        gifts.add(gift);
        gifts.add(gift1);
        gifts.add(gift2);
        gifts.add(gift3);
        gifts.add(gift4);
        gifts.add(gift5);
        gifts.add(gift6);
        gifts.add(gift7);

    }

    // 抽奖
    public synchronized Gift getGift() {
        int randomNumber = (int) (Math.random() * total());
        int priority = 0;
        for (Gift g : gifts) {
            priority += g.getType().getPriority();
            if (priority >= randomNumber) {
                // 从奖品库移出奖品
                gifts.remove(g);
                return g;
            }
        }
        // 抽奖次数多于奖品时谢谢参与
        return gifts.get(gifts.size()-1);
    }

    private int total() {
        int result = 0;
        for (Gift g : gifts) {
            result += g.getType().getPriority();
        }
        return result;
    }

    /**
     * 奖品
     */
    class Gift {
        // 奖品ID
        private String id;
        // 这个奖品的类别
        private GiftType type;

        public Gift(String id, GiftType type) {
            this.id = id;
            this.type = type;
        }

        public GiftType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "Gift [id=" + id + ", type=" + type + "]";
        }
    }

    /**
     * 奖品信息
     */
    class GiftType {
        // 名字
        private String name;
        // 这种奖品的数量，数量越大越容易抽到
        private int quantity;
        // 这种奖品的优先级，最小为1，数越大越容易抽到
        private int priority;

        public GiftType(String name, int quantity, int priority) {
            this.name = name;
            this.quantity = quantity;
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        @Override
        public String toString() {
            return "GiftType [name=" + name + ", quantity=" + quantity + ", priority=" + priority + "]";
        }

    }

}
