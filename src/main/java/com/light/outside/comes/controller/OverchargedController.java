package com.light.outside.comes.controller;

import com.google.common.base.Strings;
import com.light.outside.comes.controller.admin.LoginController;
import com.light.outside.comes.controller.pay.TenWeChatGenerator;
import com.light.outside.comes.model.*;
import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.OverchargedService;
import com.light.outside.comes.service.admin.FocusImageService;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.DateUtils;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@Controller
@RequestMapping("oc")
public class OverchargedController extends BaseController {

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
        PageResult<OverchargedModel> auctionModelPageResult = overchargedService.getOverchargeds(pageModel);
        List<OverchargedModel> overchargedModels = auctionModelPageResult.getData();
        if (overchargedModels != null) {
            data.put("oc", overchargedModels);
        }
        return "overcharged";
    }

    /**
     * 前端分页获取接口
     *
     * @param data
     * @param pageModel
     * @return
     */
    @ResponseBody
    @RequestMapping("overcharged_list.action")
    public String overcharged_list(Map<String, Object> data, PageModel pageModel) {
        PageResult<OverchargedModel> overchargedModelPageResult = this.overchargedService.getOverchargeds(pageModel);
        List<OverchargedModel> overchargedModels = overchargedModelPageResult.getData();
        if (overchargedModels != null && overchargedModels.size() > 0) {
            return JsonTools.jsonSer(overchargedModels);
        } else {
            return "";
        }
    }

    /**
     * 砍价说情页面
     *
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("overcharged_d.action")
    public String overcharged_d(Map<String, Object> data, HttpServletRequest request, @RequestParam("aid") long aid,@RequestParam(value="sponsor",required=false) Long sponsor) {
        String url="http://www.qubulikou.com/qblk/oc/overcharged_d.action";
        String queryString=request.getQueryString();
        if(!Strings.isNullOrEmpty(queryString)){
            url=url+"?"+queryString;
        }
        data.putAll(TenWeChatGenerator.getWxConfig(url));
        try {
            if (aid > 0) {
                //输出基本信息
                UserModel userModel = (UserModel) request.getSession().getAttribute(LoginController.SESSION_KEY_APP_USERINFO);
                if(sponsor==null||sponsor==0){
                    sponsor=userModel.getId();
                }
                OverchargedModel overchargedModel = this.overchargedService.getOverchargedModel(aid);
                if (overchargedModel != null) {
                        long seconds = DateUtils.endSeconds(overchargedModel.getEnd_time());
                    overchargedModel.setTime_second((int) seconds);
                    data.put("seconds", seconds);
                    data.put("oc", overchargedModel);
                    data.put("sponsor",sponsor);
                    data.put("uid", userModel.getId());
                    //获取该用户是否已经砍过价
                    boolean isJoin = this.overchargedService.isJoinOvercharged(aid,userModel.getId(), sponsor);
                    data.put("join", isJoin);
                    //获取当前价格
                    double nowPrice=this.overchargedService.getOverchargedNowPrice(aid, sponsor);
                    BigDecimal   b   =   new   BigDecimal(nowPrice);
                    nowPrice   =   b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    //当前砍掉价格
                    double subtractPrice= this.overchargedService.getOverchargedSubtractPrice(aid, sponsor);
                    BigDecimal   b2   =   new   BigDecimal(subtractPrice);
                    subtractPrice   =   b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    //获取砍价清单
                    List<OverchargedRecordModel> orms = this.overchargedService.getOverchargedRecordsByAidUid(aid, sponsor);
                    data.put("now_price",nowPrice);//当前价格
                    double diff_price=nowPrice-overchargedModel.getOver_amount();
                    BigDecimal   b3   =   new   BigDecimal(diff_price);
                    diff_price   =   b3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    data.put("difference_price",diff_price);//还差多少
                    if (orms != null) {
                        data.put("orms", orms);
                        data.put("now_count",orms.size());
                        data.put("subtract_price",subtractPrice);
                    }
                    String link="http://www.qubulikou.com/qblk/oc/overcharged_d.action?aid="+overchargedModel.getId()+"&sponsor="+ userModel.getId();
                    data.put("link",link);
                    OverchargedRecordModel orm=this.overchargedService.getOverchargedRecordsByAid(overchargedModel.getId());
                    if(orm!=null&&orm.getUid()==userModel.getId()&&diff_price<=0){
                        data.put("overcharged",true);
                    }else{
                        data.put("overcharged",false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "overcharged_d";
    }

    /**
     * 帮别人砍价
     * @param data
     * @param request
     * @param aid
     * @param sponsor
     * @return
     */
    @RequestMapping("other_overcharged_d.action")
    public String otherOvercharged_d(Map<String, Object> data, HttpServletRequest request, @RequestParam("aid") long aid,@RequestParam("sponsor") long sponsor) {
        try {
            if (aid > 0) {
                //输出基本信息
                UserModel userModel = (UserModel) request.getSession().getAttribute(LoginController.SESSION_KEY_APP_USERINFO);
                OverchargedModel overchargedModel = this.overchargedService.getOverchargedModel(aid);
                if (overchargedModel != null) {
                    long seconds = DateUtils.endSeconds(overchargedModel.getEnd_time());

                    overchargedModel.setTime_second((int) seconds);
                    data.put("seconds", seconds);
                    data.put("oc", overchargedModel);

                    //获取该用户是否已经帮朋友砍过价
                    boolean isJoin = false;
                    if(userModel.getId()!=sponsor) {
                        isJoin = this.overchargedService.isJoinOvercharged(aid,userModel.getId(), sponsor);
                    }
                    data.put("join", isJoin);
                    //获取当前价格
                    double nowPrice=this.overchargedService.getOverchargedNowPrice(aid, sponsor);
                    //当前砍掉价格
                    double subtractPrice= this.overchargedService.getOverchargedSubtractPrice(aid,sponsor);
                    //获取砍价清单
                    List<OverchargedRecordModel> orms = this.overchargedService.getOverchargedRecords(aid);
                    data.put("now_price",nowPrice);//当前价格
                    data.put("difference_price",nowPrice-overchargedModel.getOver_amount());//还差多少
                    if (orms != null) {
                        data.put("orms", orms);
                        data.put("now_count",orms.size());
                        data.put("subtract_price",subtractPrice);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "other_overcharged_d";
    }


//    @ResponseBody
//    @RequestMapping("send_overcharged.action")
//    public String send_overcharged(Map<String, Object> data, HttpServletRequest request, @RequestParam("aid") long aid) {
//        String res = "";
//        if (aid > 0) {
//            try {
//                UserModel userModel = (UserModel) request.getSession().getAttribute(LoginController.SESSION_KEY_APP_USERINFO);
//                if (userModel != null) {
//                    OverchargedRecordModel orm = this.overchargedService.overchargedRecordModel(aid, userModel);
//                    res = JsonTools.jsonSer(orm);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return res;
//    }

    @ResponseBody
    @RequestMapping("send_overcharged.action")
    public String send_overcharged(Map<String, Object> data, HttpServletRequest request, @RequestParam("aid") long aid,@RequestParam(value="sponsor",required = false) Long sponsor) {
        String res = "";
        if (aid > 0) {
            try {
                UserModel userModel = (UserModel) request.getSession().getAttribute(LoginController.SESSION_KEY_APP_USERINFO);
                if (userModel != null) {
                    if(sponsor==null){
                        sponsor=userModel.getId();
                    }
                    OverchargedRecordModel orm = this.overchargedService.overcharged(aid,sponsor,userModel);
                    res = JsonTools.jsonSer(orm);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 我的砍价记录
     *
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("mine_overcharged_list.action")
    @ResponseBody
    public String mine_overcharged_list(Map<String, Object> data, HttpServletRequest request) {
        int status = RequestTools.RequestInt(request, "status", 0);
        int page = RequestTools.RequestInt(request, "page", 1);
        int size = RequestTools.RequestInt(request, "size", Integer.MAX_VALUE);
        PageModel pageModel = new PageModel();
        pageModel.setPage(page);
        pageModel.setSize(size);
        UserModel userModel = getAppUserInfo();
        PageResult<OverchargedRecordViewModel> overchargedRecordPage = this.overchargedService.getOverchargedRecordPage(userModel.getId(), status, pageModel);
        List<OverchargedRecordViewModel> list=overchargedRecordPage.getData();
        if(list!=null&&list.size()>0)
            return JsonTools.jsonSer(list);
        else
            return "";
    }

    /**
     * 分享页面
     *
     * @return
     */
    @RequestMapping("share_overcharged.action")
    public String share(@RequestParam("phone") String phone,Map<String,Object> data,HttpServletRequest request) {
        String url="http://www.qubulikou.com/qblk/pt/share_overcharged.action";
        String queryString=request.getQueryString();
        if(!Strings.isNullOrEmpty(queryString)){
            url=url+"?"+queryString;
        }
        data.putAll(TenWeChatGenerator.getWxConfig(url));
        return "share_overcharged";
    }

    @ResponseBody
    @RequestMapping("query_overcharged.action")
    public String queryOvercharged(Map<String, Object> data,@RequestParam("uid") long uid,@RequestParam("goodsid") long goodsid)
    {
        OverchargedModel overchargedModel=overchargedService.queryOverchargedByUidGoodsid(uid, goodsid);
        if(overchargedModel!=null) {
            data.put("code",200);
            data.put("data",overchargedModel);
            data.put("shopid",1);
            return JsonTools.jsonSer(data);
        }
        else {
            data.put("code",404);
            data.put("data",overchargedModel);
            data.put("shopid",1);
            return JsonTools.jsonSer(data);
        }
    }
}
