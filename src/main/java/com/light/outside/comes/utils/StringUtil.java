package com.light.outside.comes.utils;

import com.alibaba.fastjson.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by b3st on 2017/5/9.
 */
public class StringUtil {
    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    private static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        String returnStr = "";
        for (int i = 0; i < chars.length; i++) {
            returnStr += "\\u" + Integer.toString(chars[i], 16);
        }
        return returnStr;
    }

    public static void main(String[] args) {
//        JSONObject params=new JSONObject();
//        params.put("title",string2Unicode("标题"));
//        System.out.println(params.toJSONString());
        String a="{\"endtime\":\"2017-05-20 00:00:00\",\"amount\":\"10.0\",\"id\":\"99998\",\"categoryid\":\"0\",\"shopid\":\"0\",\"starttime\":\"2017-05-08 00:00:00\",\"token\":\"facf240548d9a3cdc45feac76a15fce1\",\"promotionid\":\"0\",\"userid\":\"7\",\"title\":\"只是一个测试\",\"remark\":\"满减券\"}";
        System.out.println(URLEncoder.encode(a));
//        System.out.println(URLDecoder.decode(URLEncoder.encode(a)));
        //System.out.println(string2Unicode("{\"endtime\":\"2017-05-20 00:00:00\",\"amount\":\"10.0\",\"id\":\"99998\",\"categoryid\":\"0\",\"shopid\":\"0\",\"starttime\":\"2017-05-08 00:00:00\",\"token\":\"facf240548d9a3cdc45feac76a15fce1\",\"promotionid\":\"0\",\"userid\":\"7\",\"title\":\"只是一个测试\",\"remark\":\"满减券\"}"));
    }
}
