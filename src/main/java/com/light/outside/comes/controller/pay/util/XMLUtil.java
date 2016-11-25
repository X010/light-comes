package com.light.outside.comes.controller.pay.util;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * xml工具类
 */
public class XMLUtil {
    public static void main(String[] args) {
        try {
            Map m=doXMLParse("<xml><return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "<return_msg><![CDATA[OK]]></return_msg>\n" +
                    "<appid><![CDATA[wxaefd60484179adb0]]></appid>\n" +
                    "<mch_id><![CDATA[1226977401]]></mch_id>\n" +
                    "<nonce_str><![CDATA[0rY86zr5cptq3MRe]]></nonce_str>\n" +
                    "<sign><![CDATA[D6D167CF7C214DEC2788E8D158FAFBCE]]></sign>\n" +
                    "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                    "<prepay_id><![CDATA[wx201504281450414451d2c71a0966053069]]></prepay_id>\n" +
                    "<trade_type><![CDATA[APP]]></trade_type>\n" +
                    "</xml>\n");
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param xmlStr
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Map<String, String> doXMLParse(String xmlStr) throws JDOMException, IOException {
        if (null == xmlStr || "".equals(xmlStr)) {
            return null;
        }

        Map<String, String> m = new HashMap<String, String>();
        InputStream in = String2Inputstream(xmlStr);
        try{
            SAXBuilder builder = new SAXBuilder();
            Document doc =builder.build(in);
            Element root = doc.getRootElement();
            List list = root.getChildren();
            for (Object aList : list) {
                Element e = (Element) aList;
                String k = e.getName();

                String v;
                List children = e.getChildren();
                if (children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = XMLUtil.getChildrenText(children);
                }
                System.out.println("k="+k+" v="+v);
                m.put(k, v);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        //关闭流
        in.close();

        return m;
    }


    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuilder sb = new StringBuilder();
        if (!children.isEmpty()) {
            for (Object aChildren : children) {
                Element e = (Element) aChildren;
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<").append(name).append(">");
                if (!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</").append(name).append(">");
            }
        }

        return sb.toString();
    }


    /**
     * 获取xml编码字符集
     *
     * @param strxml
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    public static String getXMLEncoding(String strxml) throws JDOMException, IOException {
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        in.close();
        return (String) doc.getProperty("encoding");
    }


    public static String toXml(SortedMap<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        
        for (SortedMap.Entry<String, String> entry : params.entrySet()) {
            sb.append("<").append(entry.getKey()).append(">");
            sb.append(entry.getValue());
            sb.append("</").append(entry.getKey()).append(">");
        }

        sb.append("</xml>");

        return sb.toString();
    }
    
    public static String toXml(SortedMap<String, String> params,String sign) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("<xml>");
    	
    	for (SortedMap.Entry<String, String> entry : params.entrySet()) {
    		sb.append("<").append(entry.getKey()).append(">");
    		sb.append(entry.getValue());
    		sb.append("</").append(entry.getKey()).append(">");
    	}
		sb.append("<").append("sign").append(">");
		sb.append(sign);
		sb.append("</").append("sign").append(">");
    	sb.append("</xml>");
    	
    	return sb.toString();
    }


    private static InputStream String2Inputstream(String str) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(str.getBytes("utf-8"));
    }
}
