package com.light.outside.comes.controller.pay.config;

import com.light.outside.comes.controller.pay.util.PropertiesUtil;

/**
 * 公众号微信支付配置
 * <P>File name : TenConfig.java </P>
 * <P>Author : fangxiaowen </P> 
 * <P>Date : 2016年8月24日 </P>
 * wechat_oaut2_url=https://api.weixin.qq.com/sns/oauth2/access_token
 wechat_userinfo_url=https://api.weixin.qq.com/cgi-bin/user/info
 wechat_token_url=https://api.weixin.qq.com/cgi-bin/token
 wechat_sns_userinfo_url=https://api.weixin.qq.com/sns/userinfo
 wechat_refreshtoken_url=https://api.weixin.qq.com/sns/oauth2/refresh_token
 */
public class TenWeChatConfig {

    public static String access_token="";
    public static String jsapi_ticket="";
    /**
     * 公众号id
     */
    public static final String app_id ="wx1e57e69d90a3d054";

    /**
     * 公众号秘钥
     */
    public static final String app_secret="e47b5d1fa1c6277d215aec26647a66cf";

    /**
     * 商户号(mch_id)
     * 1337492301
     * 10020704
     * 1334889901
     */
    public static final String mch_id ="1337492301";
    /**
     * 商户密钥
     *
     * e47b5d1fa1c6277d215aec26647a66cf
     * feer345522qacgfwesddgfgghhuu8899
     * 862481b5bdaf7a4ef0e2065de2a35b09962fc8b4
     */
    public static final String partner_id ="yeYEshiSHIzuiZUIleLE40702001YING";
    /**
     * 支付完成后的回调处理页面
     */
    public static final String notify_url ="http://qubulikou.com/qblk/yeshizuileweixin/pay/weChatPayCallback.action";
    /**
     * 预支付接口
     */
    public static final String orderPayUrl ="https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * OAUT2
     */
    public static final String accessToken="https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * TOKEN_URL
     */
    //public static final String tokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"
    /**
     * 刷新token
     */
    public static final String refreshtokenUrl="https://api.weixin.qq.com/sns/oauth2/refresh_token";

    public static final String tokenUrl="https://api.weixin.qq.com/cgi-bin/token";

    public static final String userinfoUrl="https://api.weixin.qq.com/sns/userinfo";
    /**
     * 查询订单
     */
    public static final String orderQueryUrl="https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 订单退款
     */
    public static final String orderRefundUrl="https://api.mch.weixin.qq.com/secapi/pay/refund";







}
