package com.light.outside.comes.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by b3st9u on 17/3/27.
 */
public class HttpTools {
    private static final HttpClient client = new DefaultHttpClient();

    static MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();

    {
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setConnectionTimeout(5000);
        params.setSoTimeout(1000);
        params.setMaxTotalConnections(2000);
        params.setDefaultMaxConnectionsPerHost(4000);
        multiThreadedHttpConnectionManager.setParams(params);
    }


    /**
     * 获取GET数据
     *
     * @param url
     * @return
     */
    public static String get(String url) throws IOException {
        int retry = 0;
        HttpResponse response = null;
        String body = null;
        while (true) {
            try {
                Preconditions.checkNotNull(url);
                HttpGet httpGet = new HttpGet(url);
                response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                body = EntityUtils.toString(entity);
            } catch (Exception e) {
                if (retry > 3) {
                    break;
                }
                retry++;
            } finally {
                if (response != null)
                    response.getEntity().getContent().close();
            }
            if (response != null)
                break;
        }
        return body;
    }

    /**
     * 将数据POST到指定的URL
     *
     * @param url
     * @param data
     * @return
     */
    public static String post(String url, String data) throws IOException {
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(data);
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(data);
        stringEntity.setContentType("application/x-www-form-urlencoded");
//        stringEntity.setContentType("application/json");
        stringEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(stringEntity);
        HttpResponse httpResponse = client.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();
        String body = EntityUtils.toString(entity);
        return body;
    }

    /**
     * 转码后提交数据
     *
     * @param url
     * @param data
     * @return
     * @throws IOException
     */
    static int MAXRETRU = 3;

    public static String postUrlEncode(String url, String data) throws IOException {
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(data);
        HttpPost httpPost = new HttpPost(url);
        String decodeStr = URLDecoder.decode(data);
        StringEntity stringEntity = new StringEntity(decodeStr);
        stringEntity.setContentType("application/x-www-form-urlencoded");
//        stringEntity.setContentType("application/json");
        stringEntity.setContentEncoding("UTF-8");
        String body = "";
        int retryTimes = 0;
        while (retryTimes < MAXRETRU + 1) {
            try {
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = client.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                body = EntityUtils.toString(entity);
                if (!Strings.isNullOrEmpty(body)) {
                    return body;
                }
            } catch (IOException ioe) {
                retryTimes++;
                continue;
            }
        }
        return body;
    }

    public static String post(String url, Map<String, String> params) {
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(params);
        HttpPost httpPost = new HttpPost(url);
        List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
        for (String key : params.keySet()) {
            data.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        String body = "";
        int retryTimes = 0;
        while (retryTimes < MAXRETRU + 1) {
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));
                HttpResponse httpResponse = client.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                body = EntityUtils.toString(entity);
                if (!Strings.isNullOrEmpty(body)) {
                    return body;
                } else {
                    System.out.println("post response is null");
                    retryTimes++;
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
                retryTimes++;
            }
        }
        return body;
    }

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

    public static void main(String[] args) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", String.valueOf(162202));
            params.put("amount", String.valueOf(228.0));
            params.put("starttime", "2017-05-13 00:00:01");
            params.put("endtime", "2017-06-30 00:00:00");
            params.put("userid", String.valueOf(7));
            params.put("shopid", String.valueOf(0));
            params.put("promotionid", String.valueOf(0));
<<<<<<< HEAD
            params.put("categoryid", String.valueOf(0));
            params.put("title", "签到50度五粮春");
            params.put("goodid", String.valueOf(1731));
            params.put("remark", "签到50度五粮春");
            System.out.println(JsonTools.jsonSer(params));
            System.out.println(post("http://120.27.154.7:8067/pcfrontend/web/index.php?r=user/create-coupon", params));
=======
            params.put("categoryid", String.valueOf(1));
            params.put("goodid","994");
            params.put("title", "测试标题");
            params.put("remark", "的发送到发送到");
            //System.out.println(post("http://120.55.241.127:8070/index.php?r=user/create-coupon", params));
            System.out.println(post("http://120.27.154.7:8067/pcfrontend/web/index.php?r=user/create-coupon",params));
>>>>>>> 507fd2bddf2c59e98cbcfab29ef88b0eb0117f56
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
