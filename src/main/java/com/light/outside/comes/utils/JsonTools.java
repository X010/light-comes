package com.light.outside.comes.utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.util.Date;
import java.util.List;


/**
 * Created by b3st9u on 16/8/20.
 */

public class JsonTools {
    /**
     * 最简单序列化为Json数据
     *
     * @param ojbObject
     * @return
     */
    public static String simpleJson(Object ojbObject) {
        SerializeConfig sc = new SerializeConfig();
        sc.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        return JSON.toJSONString(ojbObject, sc);
    }

    /**
     * 最简单序列化为Json数据
     *
     * @param ojbObject
     * @return
     */
    public static String jsonSer(Object ojbObject) {
        return JSON.toJSONString(ojbObject);
    }

    public static Object parser(String content, TypeReference typeReference) {
        return JSON.parseObject(content, typeReference);
    }


    /**
     * 返序列化对象
     *
     * @param content
     * @return
     */
    public static Object StringToJsonVideo(String content, Class classZ) {
        return JSON.parseObject(content, classZ);
    }


    /**
     * 返序列化数组
     *
     * @param text
     * @param tClass
     * @param <T>
     * @return
     */
    public static final <T> List<T> parseArray(String text, Class<T> tClass) {
        return JSON.parseArray(text, tClass);
    }

}

