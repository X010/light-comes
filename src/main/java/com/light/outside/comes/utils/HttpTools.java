package com.light.outside.comes.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
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
        stringEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(stringEntity);
        HttpResponse httpResponse = client.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();
        String body = EntityUtils.toString(entity);
        return body;
    }

    public static String post(String url, Map<String, String> params) throws IOException {
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(params);
        HttpPost httpPost = new HttpPost(url);
        List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
        for (String key : params.keySet()) {
            data.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));
        HttpResponse httpResponse = client.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();
        String body = EntityUtils.toString(entity);
        return body;
    }

    public static void main(String[]args){
        try {
            System.out.println(post("http://120.55.241.127:8070/index.php?r=user/create-coupon", "{\"endtime\":\"2017-05-20 00:00:00\",\"amount\":\"10.0\",\"id\":\"2992\",\"categoryid\":\"0\",\"shopid\":\"0\",\"starttime\":\"2017-05-08 00:00:00\",\"token\":\"facf240548d9a3cdc45feac76a15fce1\",\"promotionid\":\"0\",\"userid\":\"12347\",\"title\":\"hahaha\",\"remark\":\"满减券\"}"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
