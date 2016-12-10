package com.light.outside.comes.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.light.outside.comes.controller.pay.TenWeChatGenerator;
import com.light.outside.comes.controller.pay.config.TenWeChatConfig;
import com.light.outside.comes.controller.pay.token.TokenThread;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.admin.LoginService;
import com.light.outside.comes.utils.JsonClient;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;


/**
 * 微信分享跳转控制器
 *
 * @author jixiangyun 2016年10月25日 下午6:07:06
 */
@RequestMapping(value = "/wechat")
@Controller
public class WeChatController extends BaseController {

    //APPID
    public static final String APP_ID = String.valueOf(TenWeChatConfig.app_id).trim(); //"wx65db45e85c1be9c4";
    //APP_scret
    public static final String APP_SECRET = String.valueOf(TenWeChatConfig.app_secret).trim(); //"3816ac8f220a3e9150fbab7321064d45";
    //填写为authorization_code
    public static final String GRANT_TYPE = "authorization_code";
    //获取access_token
    public static final String GET_ACCESS_TOKEN = String.valueOf(TenWeChatConfig.tokenUrl).trim(); //"https://api.weixin.qq.com/sns/oauth2/access_token";
    //刷新 access_token
    public static final String REFLASH_DATA = String.valueOf(TenWeChatConfig.refreshtokenUrl).trim();// "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    //获取用户基础信息
    public static final String GET_USER_INFO = String.valueOf(TenWeChatConfig.userinfoUrl).trim();//"https://api.weixin.qq.com/sns/userinfo";

    public static final String BASE_URL = String.valueOf("http://qulk.dssmp.com/#!");//"http://h5.sincebest.com/#!";
    //获取微信语言
    public static final String LANG = "zh_CN";
    //log
    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/weChatJump")
    public ModelAndView weChatJump(HttpServletRequest request, HttpServletResponse response) {

        Map<String, String> map = RequestTools.requestParamToMap(request);
        String code = map.get("code");
        logger.info("code value is : " + code);
        logger.info("code req value is : " + RequestTools.RequestString(request, "code"));
        if (code == null) {
            throw new RuntimeException("回调地址没有带code！");
        }

        JSONObject stepOneJson = getOpenIdStepOne(code);
        UserModel userModel = getAppUserInfo();
        //获取OpenId
        String openId = stepOneJson.getString("openid");
        logger.info("openId value is :" + openId);
        logger.info("openId access_token is :" + stepOneJson.getString("access_token"));
        //获取用户信息
        JSONObject jsonStepThree = this.getWxUserInfo(openId, stepOneJson.getString("access_token"));
        String nickname = jsonStepThree.getString("nickname");
//		nickname = filterEmoji(nickname); //过滤微信昵称特殊字符集
        //点击公众号菜单链接进来，如果有openid就更新  没有就添加用户信息
        //userInfo = userService.wechatLogin(openId, nickname, jsonStepThree.getString("headimgurl"), jsonStepThree.getString("sex"));

        if (userModel == null) {
            logger.error("用户信息获取失败:");
        }
        return new ModelAndView("redirect:" + getUrlWithMap(map));
    }


    @RequestMapping(value = "/wxconfig")
    @ResponseBody
    public String getWxConfig(Map<String, String> data, HttpServletRequest request) {
        //url在JavaScript中是location.href.split('#')[0]获取。
        String url = RequestTools.RequestString(request, "url", "");
        if (Strings.isNullOrEmpty(TenWeChatConfig.access_token)) {
            TenWeChatConfig.access_token = TokenThread.accessToken.getToken();
            if (Strings.isNullOrEmpty(TenWeChatConfig.access_token)) {
                TenWeChatConfig.access_token= TenWeChatGenerator.getAccessTokenModel().getToken();
            }
            TenWeChatConfig.jsapi_ticket = TenWeChatGenerator.getJsapiTicket("", TenWeChatConfig.access_token);
        }
        data = TenWeChatGenerator.sign(TenWeChatConfig.jsapi_ticket, url);
        data.put("app_id", TenWeChatConfig.app_id);
        return JsonTools.jsonSer(data);
    }

    /**
     * 第二步 获取openID  和reflashToken
     *
     * @return
     */
    public JSONObject getOpenIdStepOne(String code) {
        Map<String, String> map = new HashMap<String, String>();
        //todo: 放到配置文件中
        map.put("appid", APP_ID);
        map.put("secret", APP_SECRET);
        map.put("code", code);
        map.put("grant_type", GRANT_TYPE);
        JSONObject json = JsonClient.post(GET_ACCESS_TOKEN, map);
        if (json.containsKey("errcode")) {
            throw new RuntimeException("根据code获取用户OpenId失败 errcode:" + json.getString("errcode"));
        }
        return json;
    }

    /**
     * 第三步  刷新flashToken
     *
     * @return
     */
    public JSONObject refleshData(String refresh_token) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", APP_ID);
        map.put("grant_type", "refresh_token");
        map.put("refresh_token", refresh_token);
        JSONObject json = JsonClient.post(REFLASH_DATA, map);
        if (json.containsKey("errcode")) {
            logger.error("根据第三步的reflash_code 刷新失败 errcode:" + json.getString("errcode"));
        }
        return json;
    }

    /**
     * 第四步获取用户基础信息
     *
     * @return
     */
    public JSONObject getWxUserInfo(String openid, String access_token) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("openid", openid);
        map.put("access_token", access_token);
        map.put("lang", LANG);
        JSONObject json = JsonClient.post(GET_USER_INFO, map);
        if (json.containsKey("errcode")) {
            logger.error("根据第四步的获取用户信息失败 errcode:" + json.getString("errcode"));
        }
        return json;
    }

    /**
     * 获取传过来的参数拼接返回的参数
     *
     * @return
     */
    public String getUrlWithMap(Map<String, String> map) {
        logger.info("weixin  Jump start :" + map);
        String paramsWithUri = "?";
        Set<String> set = map.keySet();
        for (String arg : set) {
            if (arg.equals("path") || arg.equals("code")) {
                continue;
            }
            logger.info(arg + " pathUrl value is : >" + map.get(arg));
            paramsWithUri += "&" + arg + "=" + map.get(arg);
        }
//		paramsWithUri += "&openId=" + openId; 
        paramsWithUri = paramsWithUri.replaceFirst("&", "");
        logger.info("pathUrl paramsWithUri is : " + paramsWithUri);
        Boolean isHaveParam = map.get("path") != null & map.get("path").indexOf("?") > 0 ? true : false;
        logger.info("pathUrl isHaveParam is : " + isHaveParam);
        String pathUrl = BASE_URL + map.get("path");
        pathUrl += isHaveParam ? paramsWithUri.replaceFirst("\\?", "&") : paramsWithUri;

        logger.info("weixin  Jump end  pathUrl  is : " + pathUrl);
        return pathUrl;
    }

//    public static String filterEmoji(String source) { 
//        if(source != null)
//        {
//            Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
//            Matcher emojiMatcher = emoji.matcher(source);
//            if ( emojiMatcher.find())
//            {
//                source = emojiMatcher.replaceAll("*");
//                return source ;
//            }
//            return source;
//        }
//        return source; 
//    }

}
