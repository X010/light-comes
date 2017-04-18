package com.light.outside.comes.utils;

import java.math.BigDecimal;

/**
 * Created by b3st9u on 17/1/19.
 */
public class ArithUtil {
    private static final int DEF_DIV_SCALE=10;

    private ArithUtil(){}
    //相加
    public static float add(float f1,float f2){
        BigDecimal b1=new BigDecimal(Float.toString(f1));
        BigDecimal b2=new BigDecimal(Float.toString(f2));
        return b1.add(b2).floatValue();

    }
    //相减
    public static float sub(float f1,float f2){
        BigDecimal b1=new BigDecimal(Double.toString(f1));
        BigDecimal b2=new BigDecimal(Double.toString(f2));
        return b1.subtract(b2).floatValue();

    }
    //相乘
    public static float mul(float f1,float f2){
        BigDecimal b1=new BigDecimal(Double.toString(f1));
        BigDecimal b2=new BigDecimal(Double.toString(f2));
        return b1.multiply(b2).floatValue();

    }
    //相除
    public static double div(double d1,double d2){
        return div(d1,d2,DEF_DIV_SCALE);

    }

    public static double div(double d1,double d2,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();

    }
}
