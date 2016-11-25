package com.light.outside.comes.controller.pay.util;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Star
 * Date: 12-7-2
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
//todo 修改成getLong，getString，getInt
public class PropertiesUtil {
    private static final String DEFAULT_RESOURCE_PATTERN = "classpath:global.properties";
    private static final Map<Object, Object> all = new HashMap<Object, Object>();
    private static Logger logger = Logger.getLogger(PropertiesUtil.class);

    public static Object get(String key) {
        return all.get(key);
    }

    public static String getString(String key) {
        return (String) all.get(key);
    }

    public static Long getLong(String key) {
        long res = 0;
        try {
            res = Long.parseLong((String) all.get(key));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return res;
    }

    public static int getInt(String key) {
        int res = 0;
        try {
            res = Integer.parseInt((String) all.get(key));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return res;
    }

    public static Map<Object, Object> getAll() {
        return all;
    }

    static {
        try {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(DEFAULT_RESOURCE_PATTERN);
            if (resources != null) {
                for (Resource r : resources) {
                    Reader in = new InputStreamReader(r.getInputStream(), "UTF-8");
                    Properties p = new Properties();
                    p.load(in);
                    all.putAll(p);
                    in.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
