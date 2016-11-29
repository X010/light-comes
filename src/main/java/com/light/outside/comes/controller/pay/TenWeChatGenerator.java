package com.light.outside.comes.controller.pay;

import com.light.outside.comes.controller.pay.client.TenpayHttpClient;
import com.light.outside.comes.controller.pay.config.TenWeChatConfig;
import com.light.outside.comes.controller.pay.util.Sha1Util;
import com.light.outside.comes.controller.pay.util.XMLUtil;
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
    public static Map<String, Object> genPayOrder(String body, String tradeNo, String payMoney, String openid, String spbillCreateIp) throws Exception {
        String prepay_id = unifiedOrder(body, tradeNo, payMoney, openid, spbillCreateIp);
        logger.info("genPayOrder(): prepay id: " + prepay_id);
        long timeStamp = System.currentTimeMillis() / 1000;
        String noncestr2 = Sha1Util.getNonceStr();
        SortedMap<String, String> beanMap = new TreeMap();
        beanMap.put("appId", TenWeChatConfig.app_id);
        beanMap.put("timeStamp", timeStamp + "");
        beanMap.put("nonceStr", noncestr2);
        beanMap.put("package", "prepay_id=" + prepay_id);
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
        return resultMap;
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

        Document curDocument = DocumentHelper.createDocument();
        Element rootElement = DocumentHelper.createElement("xml");
        for (String key : packageParams.keySet()) {
            Element curElement = DocumentHelper.createElement(key);
            curElement.setText(packageParams.get(key));
            rootElement.add(curElement);
        }
        String sign = Sha1Util.genWXPackageSign(packageParams);
        System.out.println(sign);
        System.out.println();
        Element signElement = DocumentHelper.createElement("sign");
        signElement.setText(sign);
        rootElement.add(signElement);
        curDocument.setRootElement(rootElement);
        String xmlParams = curDocument.getRootElement().asXML(); //XMLUtil.toXml(packageParams,sign);
        System.out.println(xmlParams);
        TenpayHttpClient httpClient = new TenpayHttpClient();
        if (httpClient.callHttpPost(TenWeChatConfig.orderPayUrl, xmlParams)) {
            String resContent = httpClient.getResContent();
            System.out.println("response:"+resContent);
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
    public Map<String, String> getNewSing(String url, String access_token) {
        String jsapi_ticket = getJsapiTicket("", access_token);
        Map<String, String> map = sign(jsapi_ticket, url);
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
    public String getJsapiTicket(String url, String token) {
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
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
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

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
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
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
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
     * 查询订单状态
     *
     * @return
     */
//	public Map orderQuery(String trade_no) {
//		String nonce_str = Sha1Util.getNonceStr();
//		SortedMap<String, String> beanMap = new TreeMap();
//		beanMap.put("appid", PropertiesUtil.getString("wechat_app_id"));
//		beanMap.put("mch_id", PropertiesUtil.getString("wechat_mch_id"));
//		beanMap.put("nonce_str", nonce_str);
//		beanMap.put("transaction_id",trade_no);
//		String sign = Sha1Util.genWXPackageSign(beanMap);
//		String xml = "<xml>" +
//					 "<appid>"+PropertiesUtil.getString("wechat_app_id")+"</appid>" +
//					 "<mch_id>"+PropertiesUtil.getString("wechat_mch_id")+"</mch_id>" +
//					 "<nonce_str>" + nonce_str + "</nonce_str>" +
//					 "<transaction_id>" + trade_no + "</transaction_id>" +
//					 "<sign>" + sign + "</sign>" +
//					 "</xml>";
//		TenpayHttpClient httpClient = new TenpayHttpClient();
//		if (httpClient.callHttpPost(PropertiesUtil.getString("wechat_orderquery"), xml)) {
//			String resContent = httpClient.getResContent();
//			System.out.println(resContent);
//			try {
//				Map result = XMLUtil.doXMLParse(resContent);
//				System.out.println(result.get("result_code")+"  >> "+result.get("trade_state"));
//				return result;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
    public static void main(String[] args) {
        TenWeChatGenerator tt = new TenWeChatGenerator();
        //	Map ss = tt.orderQuery("4006652001201610288016305969");
    }

}

