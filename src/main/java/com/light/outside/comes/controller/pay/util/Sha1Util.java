package com.light.outside.comes.controller.pay.util;



import com.light.outside.comes.controller.pay.TenWeChatGenerator;
import com.light.outside.comes.controller.pay.config.TenWeChatConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 *'============================================================================
	'api说明：
	'createSHA1Sign创建签名SHA1
	'getSha1()Sha1签名
	'============================================================================ 
 * <P>File name : Sha1Util.java </P>
 * <P>Author : fangxiaowen </P> 
 * <P>Date : 2016年8月24日 </P>
 */
public class Sha1Util {

    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
    }

    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    //创建签名SHA1
    public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception {
        StringBuffer sb = new StringBuffer();
        Set es = signParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
            //要采用URLENCODER的原始值！
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        System.out.println("sha1 sb:" + params);
        return getSha1(params);
    }

    /**
     * 生成签名
     */
    public static String genPackageSign(SortedMap<String, String> signParams) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : signParams.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(TenWeChatConfig.partner_id);

        //System.out.println("package sign params: " + sb.toString());

        return MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
    }
    
    
    
    /**
     * 生成签名
     */
    public static String genWXPackageSign(SortedMap<String, String> signParams) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : signParams.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(TenWeChatConfig.partner_id);

        System.out.println("package sign params: " + sb.toString());
        return MD5.getMessageDigest(sb.toString().trim().getBytes()).toUpperCase();
    }

    /**
     * 二次签名
     *
     * @param params
     * @return
     */
    public static String genAppSign(SortedMap<String, String> params) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }

        sb.append("key=");
        sb.append(TenWeChatConfig.partner_id);

        return MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
    }
    
    
    
    /**
     * 二次签名
     *
     * @param params
     * @return
     */
    public static String genWXSign(SortedMap<String, String> params) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }

        sb.append("key=");
        sb.append(TenWeChatConfig.partner_id);

        return MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
    }

    //Sha1签名
    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("GBK"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Encodes a string
     *
     * @param str String to encode
     * @return Encoded String
     * @throws NoSuchAlgorithmException
     */
    public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }
    public static void main(String[] args) {
    	
    	String tradeNo= PubUtils.getUniqueSn()+"";
    	try {
			TenWeChatGenerator.genPayOrder("曲不离口-拍卖定金",tradeNo,"20","12345678901","123.12.12.123");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	//String string = "appid=wx1e57e69d90a3d054&body=test&mch_id=1337492301&nonce_str=e3844e186e6eb8736e9f53c0c5889527&notify_url=notify_url&openid=12345678901&out_trade_no=1611291540365106001&spbill_create_ip=0:0:0:0:0:0:0:1&total_fee=2000&trade_type=JSAPI&key=feer345522qacgfwesddgfgghhuu8899";
    	//StringBuilder sb = new StringBuilder("appid=wx1e57e69d90a3d054&body=test&mch_id=1337492301&nonce_str=e3844e186e6eb8736e9f53c0c5889527&notify_url=notify_url&openid=12345678901&out_trade_no=1611291540365106001&spbill_create_ip=0:0:0:0:0:0:0:1&total_fee=2000&trade_type=JSAPI&key=feer345522qacgfwesddgfgghhuu8899");
    	//System.out.println(MD5.getMessageDigest(string.getBytes()).toUpperCase());
    	//System.out.println(MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase());
	}


}
