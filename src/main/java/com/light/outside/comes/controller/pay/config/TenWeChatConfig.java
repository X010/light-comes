package com.light.outside.comes.controller.pay.config;

import com.light.outside.comes.controller.pay.util.PropertiesUtil;

/**
 * 公众号微信支付配置
 * <P>File name : TenConfig.java </P>
 * <P>Author : fangxiaowen </P> 
 * <P>Date : 2016年8月24日 </P>
 */
public class TenWeChatConfig {
	
    /**
     * 公众号id
     */
    public static final String app_id ="wx1e57e69d90a3d054";
    /**
     * 商户号(mch_id)
     */
    public static final String mch_id ="1337492301";
    /**
     * 密钥
     * e47b5d1fa1c6277d215aec26647a66cf
     */
    public static final String partner_id ="feer345522qacgfwesddgfgghhuu8899";
    /**
     * 支付完成后的回调处理页面
     */
    public static final String notify_url ="http://www.weixin.qq.com/wxpay/pay.php";
    /**
     * 预支付接口
     */
    public static final String orderPayUrl ="https://api.mch.weixin.qq.com/pay/unifiedorder";
}
