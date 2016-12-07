package com.light.outside.comes.utils;


import com.google.common.base.Strings;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by b3st9u on 16/8/20.
 * 请求参数工具类
 */
public class RequestTools {

    /**
     * 请求参数获取Long型
     *
     * @param httpServletRequest
     * @param parameter
     * @param defau
     * @return
     */
    public static long RequestLong(HttpServletRequest httpServletRequest, String parameter, long defau) {
        String defaultValue = httpServletRequest.getParameter(parameter);
        if (Strings.isNullOrEmpty(defaultValue)) return defau;
        return Long.parseLong(defaultValue);
    }

    /**
     * 请求参数获取String类型
     *
     * @param request
     * @param parameter
     * @param defau
     * @return
     */
    public static String RequestString(HttpServletRequest request, String parameter, String defau) {
        String defaultValue = request.getParameter(parameter);
        if (Strings.isNullOrEmpty(defaultValue)) return defau;
        return defaultValue;
    }

    /**
     * 请求参数获取String类型
     *
     * @param request
     * @param parameter
     * @return
     */
    public static String RequestString(HttpServletRequest request, String parameter) {
        String defaultValue = request.getParameter(parameter);
        if (Strings.isNullOrEmpty(defaultValue)) return null;
        return defaultValue;
    }


    /**
     * 请求参数获取整型
     *
     * @param httpServletRequest
     * @param parameter
     * @param defau
     * @return
     */
    public static int RequestInt(HttpServletRequest httpServletRequest, String parameter, int defau) {
        String defaultValue = httpServletRequest.getParameter(parameter);
        if (Strings.isNullOrEmpty(defaultValue)) return defau;
        return Integer.parseInt(defaultValue);
    }


    /**
     * 请求参数获取Date型
     *
     * @param httpServletRequest
     * @param parameter
     * @param format
     * @return
     */
    public static Date RequestDate(HttpServletRequest httpServletRequest, String parameter, String format, Date defaultTime) {
        Date time = defaultTime;
        String defaultValue = httpServletRequest.getParameter(parameter);
        if (!Strings.isNullOrEmpty(defaultValue)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            try {
                time = simpleDateFormat.parse(defaultValue);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
    }

    /**
     * 获取请求参数转换成map数据
     *
     * @param req
     * @return Map<String, String>
     */
    public static Map<String, String> requestParamToMap(HttpServletRequest req) {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> mapReq = req.getParameterMap();
        Set<String> keySet = mapReq.keySet();
        for (String key : keySet) {
            map.put(key, mapReq.get(key)[0]);
        }
        return map;
    }

    public static <T> T getParamBean(HttpServletRequest request, Class<T> clazz) {
        try {
            T bean = clazz.newInstance();
            Map<?, ?> map = request.getParameterMap();
            ConvertUtils.register(new Converter() {
                public Object convert(@SuppressWarnings("rawtypes")
                                              Class type, Object value) {
                    if (value == null) {
                        return null;
                    }
                    String str = ((String) value).trim();
                    if (("").equals(str.trim())) {
                        return null;
                    }
                    Date d = null;
                    SimpleDateFormat format = null;

                    if (str.length() > 10) {
                        if (str.indexOf("-") > 0) {
                            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        } else {
                            format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        }
                    } else if (str.length() == 10) {
                        if (str.indexOf("-") > 0) {
                            format = new SimpleDateFormat("yyyy-MM-dd");
                        } else {
                            format = new SimpleDateFormat("yyyy/MM/dd");
                        }
                    } else if (str.length() == 5) {
                        if (str.indexOf(":") > 0) {
                            format = new SimpleDateFormat("HH:mm");
                        }
                    }
                    try {
                        d = format.parse(str);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    return d;
                }
            }, Date.class);
            Iterator<?> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) entries.next();
                String name = (String) entry.getKey();
                if (name == null) {
                    continue;
                }
                Object object = entry.getValue();
                if (object.getClass().isArray()) {
                    object = StringUtils.join((Object[]) object, ",");
                }
                BeanUtils.setProperty(bean, name, object);
            }
            return bean;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}
