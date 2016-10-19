package com.light.outside.comes.controller;

import com.light.outside.comes.model.*;
import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.service.admin.FocusImageService;
import com.light.outside.comes.service.RaffleService;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
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

//    private List<GiftType> gifts = new LinkedList<GiftType>();

    @RequestMapping("lottery_draw.action")
    @ResponseBody
    public String lottery_draw(Map<String, Object> data,HttpServletRequest request,HttpServletRequest response){
        long rid= RequestTools.RequestInt(request,"rid",13);
        int code=0;
        String msg="谢谢参与!";
        RaffleCouponModel raffleCouponModel=raffleService.drawRaffle(rid);
        if(raffleCouponModel!=null){
            code=1;
            msg="恭喜你，抽中"+raffleCouponModel.getTitle();
            data.put("id",raffleCouponModel.getId());
        }
        data.put("code",code);
        data.put("msg", msg);
        String result=JsonTools.jsonSer(data);
        System.out.println(result);
        return result;
    }

    /**
     * 初始化所有奖品
     */

    private void initGift(){
        System.out.println("init gifts........");
       // raffleService.initRaffle(13);
//        GiftType giftType=new GiftType("1","1000",1,1);
//
//        GiftType giftType1=new GiftType("2","100",1,1);
//
//        GiftType giftType2=new GiftType("3","50",10,2);
//
//        GiftType giftType3=new GiftType("4","20",20,10);
//
//        GiftType giftType4=new GiftType("5","10",50,20);
//
//        GiftType giftType5=new GiftType("6","1",999,50);
//
//        GiftType giftType6=new GiftType("7","1",999,80);
//
//        GiftType giftType7=new GiftType("8","谢谢参与",9999,98);
//
//        gifts.add(giftType);
//        gifts.add(giftType1);
//        gifts.add(giftType2);
//        gifts.add(giftType3);
//        gifts.add(giftType4);
//        gifts.add(giftType5);
//        gifts.add(giftType6);
//        gifts.add(giftType7);

    }

    // 抽奖
//    public synchronized GiftType getGift() {
//        int randomNumber = (int) (Math.random() * total());
//        int priority = 0;
//        for (GiftType g : gifts) {
//            priority += g.getPriority();
//            if (priority >= randomNumber) {
//                // 从奖品库移出奖品
//                //gifts.remove(g);
//                g.quantity--;
//                return g;
//            }
//        }
//        // 抽奖次数多于奖品时谢谢参与
//        return gifts.get(gifts.size()-1);
//    }
//
//    private int total() {
//        int result = 0;
//        for (GiftType g : gifts) {
//            result += g.getQuantity();
//        }
//        return result;
//    }
//
//
//    /**
//     * 奖品信息
//     */
//    class GiftType {
//        private String id;
//        // 名字
//        private String name;
//        // 这种奖品的数量，数量越大越容易抽到
//        private int quantity;
//        // 这种奖品的优先级，最小为1，数越大越容易抽到
//        private int priority;
//
//        public GiftType(String id,String name, int quantity, int priority) {
//            this.id=id;
//            this.name = name;
//            this.quantity = quantity;
//            this.priority = priority;
//        }
//
//        public int getPriority() {
//            return priority;
//        }
//
//        @Override
//        public String toString() {
//            return "GiftType [id="+ id +"name=" + name + ", quantity=" + quantity + ", priority=" + priority + "]";
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public int getQuantity() {
//            return quantity;
//        }
//
//        public void setQuantity(int quantity) {
//            this.quantity = quantity;
//        }
//
//        public void setPriority(int priority) {
//            this.priority = priority;
//        }
//    }

}
