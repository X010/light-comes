package com.light.outside.comes.controller.pay;

import com.google.common.base.Strings;
import com.light.outside.comes.controller.pay.client.TenpayHttpClient;
import com.light.outside.comes.controller.pay.config.TenWeChatConfig;
import com.light.outside.comes.controller.pay.token.AccessToken;
import com.light.outside.comes.controller.pay.token.TokenThread;
import com.light.outside.comes.controller.pay.util.HttpClientUtil;
import com.light.outside.comes.controller.pay.util.Sha1Util;
import com.light.outside.comes.controller.pay.util.XMLUtil;
import com.light.outside.comes.utils.JsonClient;
import com.light.outside.comes.utils.RequestTools;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 微信支付生成订单
 * 参考http://www.cnblogs.com/javatochen/p/5553403.html
 * http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
 * http://www.cnblogs.com/txw1958/category/624506.html
 */
public class TenWeChatGenerator {

    protected static final Logger logger = LoggerFactory.getLogger(TenWeChatGenerator.class);

    /**
     * 生成预支付订单
     *
     * @param body
     * @param tradeNo
     * @param payMoney
     * @param openid
     * @param spbillCreateIp
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genPayOrder(String url,String body, String tradeNo, String payMoney, String openid, String spbillCreateIp) throws Exception {
        String prepay_id = unifiedOrder(body, tradeNo, payMoney, openid, spbillCreateIp);
        logger.info("genPayOrder(): prepay id: " + prepay_id);
        long timeStamp = System.currentTimeMillis() / 1000;
        String noncestr2 = Sha1Util.getNonceStr();
        SortedMap<String, String> beanMap = new TreeMap();
        beanMap.put("appId", TenWeChatConfig.app_id);
        beanMap.put("timeStamp", timeStamp + "");
        beanMap.put("nonceStr", noncestr2);
        beanMap.put("package", "prepay_id="+prepay_id);
        beanMap.put("signType", "MD5");
        Element rootElement = DocumentHelper.createElement("xml");
        for (String key : beanMap.keySet()) {
            Element curElement = DocumentHelper.createElement(key);
            curElement.setText(beanMap.get(key));
            rootElement.add(curElement);
        }
        String paySign = Sha1Util.genWXPackageSign(beanMap);
        beanMap.put("paySign", paySign);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("appId", TenWeChatConfig.app_id);
        resultMap.put("timeStamp", timeStamp + "");
        resultMap.put("nonceStr", noncestr2);
        resultMap.put("package", "prepay_id=" + prepay_id);
        resultMap.put("signType", "MD5");
        resultMap.put("paySign", paySign);
        String signature=sign(TenWeChatConfig.access_token,url,noncestr2,timeStamp+"");
        resultMap.put("signature",signature);
        return resultMap;
    }

    /**
     * 获取wx.config
     * @param url
     * @return
     */
    public static Map<String,Object> getWxConfig(String url){
        long exp=(System.currentTimeMillis()/1000)-TokenThread.accessToken.getTokenTime();
        if (Strings.isNullOrEmpty(TenWeChatConfig.access_token)||exp>TokenThread.accessToken.getExpiresIn()) {
            if(TokenThread.accessToken!=null) {
                TenWeChatConfig.access_token = TokenThread.accessToken.getToken();
            }else{
                //重新生成
                TenWeChatConfig.access_token= TenWeChatGenerator.getAccessTokenModel().getToken();
            }
            TenWeChatConfig.jsapi_ticket = TenWeChatGenerator.getJsapiTicket("", TenWeChatConfig.access_token);
        }
        Map<String,Object> data = TenWeChatGenerator.sign(TenWeChatConfig.jsapi_ticket, url);
        data.put("app_id", TenWeChatConfig.app_id);
        return data;
    }

    /**
     * 统一下单 get prepay_id
     *
     * @param body
     * @param tradeNo
     * @param payMoney
     * @param openid
     * @param spbillCreateIp
     * @return
     * @throws Exception
     */
    private static String unifiedOrder(String body, String tradeNo, String payMoney, String openid, String spbillCreateIp) throws Exception {
        String noncestr = Sha1Util.getNonceStr();
        String prepay_id = "";
        SortedMap<String, String> packageParams = new TreeMap();
        packageParams.put("appid", TenWeChatConfig.app_id);
        packageParams.put("mch_id", TenWeChatConfig.mch_id);
        packageParams.put("nonce_str", noncestr);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", tradeNo);
        packageParams.put("total_fee", Integer.valueOf((int) (Double.parseDouble(payMoney) * 100)) + "");
        packageParams.put("spbill_create_ip", spbillCreateIp);        //订单生成的机器IP
        packageParams.put("notify_url", TenWeChatConfig.notify_url);
        packageParams.put("trade_type", "JSAPI");
        packageParams.put("openid", openid);
        String sign = Sha1Util.genWXPackageSign(packageParams);
        System.out.println("sign:"+sign);
        System.out.println();
        Element signElement = DocumentHelper.createElement("sign");
        signElement.setText(sign);
//        packageParams.put("device_info","WEB");
        Document curDocument = DocumentHelper.createDocument();
        Element rootElement = DocumentHelper.createElement("xml");
        for (String key : packageParams.keySet()) {
            Element curElement = DocumentHelper.createElement(key);
            curElement.setText(packageParams.get(key));
            rootElement.add(curElement);
        }
        rootElement.add(signElement);
        curDocument.setRootElement(rootElement);
        String xmlParams = curDocument.getRootElement().asXML(); //XMLUtil.toXml(packageParams,sign);
        System.out.println(xmlParams);
        TenpayHttpClient httpClient = new TenpayHttpClient();
        if (httpClient.callHttpPost(TenWeChatConfig.orderPayUrl, xmlParams)) {
            String resContent = httpClient.getResContent();
            System.out.println("response:" + resContent);
            try {
                Map result = XMLUtil.doXMLParse(resContent);
                String msg = (String) result.get("return_msg");
                if ("OK".equals(msg)) {
                    prepay_id = (String) result.get("prepay_id");
                } else {
                    throw new OrderException("pay order error:" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (prepay_id.equals("")) {
            throw new OrderException("get prepay_id error");
        }
        return prepay_id;
    }

    /**
     * 获取微信分享权限的签名
     *
     * @param url
     * @return
     */
    public Map<String, Object> getNewSing(String url, String access_token) {
        String jsapi_ticket = getJsapiTicket("", access_token);
        Map<String, Object> map = sign(jsapi_ticket, url);
        return map;
    }

    /**
     * 获取字符串
     *
     * @param url
     * @return
     */
    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }

    /**
     * 获取JsapiTicket
     *
     * @return
     */
    public static String getJsapiTicket(String url, String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        String url1 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
        // String url1= String.valueOf(PropertiesUtil.get("tenwechat_jsapiticket_url")).replace("{token}", token).trim();
        String jsapi_ticket = loadJSON(url1);
        map.put("jsapi_ticket", jsapi_ticket);
        JSONObject jsonObject = JSONObject.fromObject(jsapi_ticket);
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            map.put(key, jsonObject.get(key));
        }
        if ("ok".equals(map.get("errmsg"))) {
            return (String) map.get("ticket");
        }
        return "";
    }

    /**
     * 生成签名算法
     *
     * @param jsapi_ticket
     * @param url
     * @return
     */
    public static Map<String, Object> sign(String jsapi_ticket, String url) {
        Map<String, Object> ret = new HashMap<String, Object>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);
        signature=sign(jsapi_ticket,url,nonce_str,timestamp);
//        try {
//            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//            crypt.reset();
//            crypt.update(string1.getBytes("UTF-8"));
//            signature = byteToHex(crypt.digest());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    public static String sign(String jsapi_ticket, String url,String nonce_str,String timestamp ) {
        Map<String, Object> ret = new HashMap<String, Object>();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(string1+"  singature:"+signature);
        return signature;
    }

    /**
     * 生成签名用到的算法
     *
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 获取noncestr
     *
     * @return
     */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取timestamp
     *
     * @return
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 刷新 Token 这里预留 现在未实现，后期看看用不用
     * 不用就删除
     *
     * @return
     */
    public String refreshToken() {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + TenWeChatConfig.app_id + "&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
        //String url = String.valueOf(PropertiesUtil.get("tenwechat_refreshtoken_url")).trim();
        String ass = loadJSON(url);
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject jsonObject = JSONObject.fromObject(ass);
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());

            map.put(key, jsonObject.get(key));
        }
        if ((Integer) map.get("expires_in") == 7200) {
            return (String) map.get("access_token");
        }
        return "";
    }


    /**
     * 获取access token
     *
     * @return
     * @throws Exception
     */
    public static String getAccessToken() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("grant_type", "client_credential");
        map.put("appid", TenWeChatConfig.app_id);
        map.put("secret", TenWeChatConfig.app_secret);
        com.alibaba.fastjson.JSONObject json = JsonClient.post("https://api.weixin.qq.com/cgi-bin/token", map);
        if (json.containsKey("errcode")) {
            throw new Exception("get token fail" + json);
        }
        String accessToken = json.getString("access_token");
        return accessToken;
    }

    /**
     *get方式 获取token
     * @return
     */
    public static String getAccessToken2() {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        String appid = TenWeChatConfig.app_id;//第三方用户唯一凭证
        String secret = TenWeChatConfig.app_secret;//    第三方用户唯一凭证密钥，即appsecret
        requestUrl = requestUrl.replace("APPID", appid);
        requestUrl = requestUrl.replace("APPSECRET", secret);
        com.alibaba.fastjson.JSONObject jsonObject = JsonClient.get(requestUrl);
        String access_token = jsonObject.getString("access_token");
        return access_token;
    }

    public static AccessToken getAccessTokenModel() {
        AccessToken accessToken = new AccessToken();
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        String appid = TenWeChatConfig.app_id;//第三方用户唯一凭证
        String secret = TenWeChatConfig.app_secret;//    第三方用户唯一凭证密钥，即appsecret
        requestUrl = requestUrl.replace("APPID", appid);
        requestUrl = requestUrl.replace("APPSECRET", secret);
        com.alibaba.fastjson.JSONObject jsonObject = JsonClient.get(requestUrl);
        String access_token = jsonObject.getString("access_token");
        //设置accessToken
        accessToken.setToken(access_token);
        accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
        accessToken.setTokenTime(System.currentTimeMillis()/1000);
        TokenThread.accessToken = accessToken;

        return accessToken;
    }

    /**
     * 第二步 获取openID  和reflashToken
     *
     * @return
     */
    public static com.alibaba.fastjson.JSONObject getOpenIdStepOne(String code) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", TenWeChatConfig.app_id);
        map.put("secret", TenWeChatConfig.app_secret);
        map.put("code", code);
        map.put("grant_type", "authorization_code");
        com.alibaba.fastjson.JSONObject json = JsonClient.post(TenWeChatConfig.accessToken, map);
        if (json.containsKey("errcode")) {
            throw new RuntimeException("根据code获取用户OpenId失败 errcode:" + json.getString("errcode"));
        }
        return json;
    }

    /**
     * 查询订单状态
     *
     * @return
     */
	public static Map orderQuery(String transaction_id) {
		String nonce_str = Sha1Util.getNonceStr();
		SortedMap<String, String> beanMap = new TreeMap();
		beanMap.put("appid", TenWeChatConfig.app_id);
		beanMap.put("mch_id", TenWeChatConfig.mch_id);
		beanMap.put("nonce_str", nonce_str);
		beanMap.put("transaction_id",transaction_id);
		String sign = Sha1Util.genWXPackageSign(beanMap);
		String xml = "<xml>" +
					 "<appid>"+TenWeChatConfig.app_id+"</appid>" +
					 "<mch_id>"+TenWeChatConfig.mch_id+"</mch_id>" +
					 "<nonce_str>" + nonce_str + "</nonce_str>" +
					 "<transaction_id>" + transaction_id + "</transaction_id>" +
					 "<sign>" + sign + "</sign>" +
					 "</xml>";
		TenpayHttpClient httpClient = new TenpayHttpClient();
		if (httpClient.callHttpPost(TenWeChatConfig.orderQueryUrl, xml)) {
			String resContent = httpClient.getResContent();
			System.out.println(resContent);
			try {
				Map result = XMLUtil.doXMLParse(resContent);
				System.out.println(result.get("result_code")+"  >> "+result.get("trade_state"));
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

    /**
     * 根据商户订单号查询
     * @param out_trade_no
     * @return
     */
    public static Map orderQueryTradeNo(String out_trade_no) {
        String nonce_str = Sha1Util.getNonceStr();
        SortedMap<String, String> beanMap = new TreeMap();
        beanMap.put("appid", TenWeChatConfig.app_id);
        beanMap.put("mch_id", TenWeChatConfig.mch_id);
        beanMap.put("nonce_str", nonce_str);
        beanMap.put("out_trade_no",out_trade_no);
        String sign = Sha1Util.genWXPackageSign(beanMap);
        String xml = "<xml>" +
                "<appid>"+TenWeChatConfig.app_id+"</appid>" +
                "<mch_id>"+TenWeChatConfig.mch_id+"</mch_id>" +
                "<nonce_str>" + nonce_str + "</nonce_str>" +
                "<out_trade_no>" + out_trade_no + "</out_trade_no>" +
                "<sign>" + sign + "</sign>" +
                "</xml>";
        TenpayHttpClient httpClient = new TenpayHttpClient();
        if (httpClient.callHttpPost(TenWeChatConfig.orderQueryUrl, xml)) {
            String resContent = httpClient.getResContent();
            System.out.println(resContent);
            try {
                Map result = XMLUtil.doXMLParse(resContent);
                System.out.println(result.get("result_code")+"  >> "+result.get("trade_state"));
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        TenWeChatGenerator tt = new TenWeChatGenerator();
        String accessToken = TenWeChatGenerator.getAccessToken2();
        TenWeChatGenerator.getAccessTokenModel();
        System.out.println(accessToken);
        tt.getJsapiTicket("", accessToken);
        //	Map ss = tt.orderQuery("4006652001201610288016305969");
    }

}

