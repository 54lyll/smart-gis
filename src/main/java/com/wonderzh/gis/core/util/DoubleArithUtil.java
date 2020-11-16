package com.wonderzh.gis.core.util;

import java.math.BigDecimal;

/**
 * @Author: wonderzh
 * @Date: 2018/12/7 16:27
 * @Description: BigDecimal计算Double数据
 * @Version: 1.0
 */

public class DoubleArithUtil {
    
    //默认运算精度
    private static final int DEF_DIV_SCALE = 10;
    
    //这个类不能实例化
    private DoubleArithUtil() {
    
    }
    
    /**
     * 提供精确的加法运算
     *
     * @param v1
     *            被加数
     * @param v2
     *            加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供可以null 的加法运算
     * @param v1
     * @param v2
     * @return
     */
    public static Double addNullAble(Double v1, Double v2) {
        if (v1 == null) {
            if (v2 == null) {
                return null;
            } else {
                return v2;
            }
        } else {
            if (v2 == null) {
                return v1;
            } else {
                return DoubleArithUtil.add(v1, v2);
            }
        }
    }
    /**
     * 提供精确的减法运算
     * @param v1
     *            被減数
     * @param v2
     *            減数
     * @return两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    
    /**
     * 提供精确的乘法运算
     *
     * @param v1
     *            被乘数
     * @param v2
     *            乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
    
    /**
     * 提供（相对）精确的除非运算，当发生除不尽的情况时，精确到小数点以后10位，以后的数字四舍五入
     * @param v1
     *            被除數
     * @param v2
     *            除數
     * @return 兩個參數的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }
    
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
     * @param v1
     *            被除數
     * @param v2
     *            除數
     * @param scale
     *            表示表示需要精確到小數點以後位数。
     * @return 兩個參數的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 提供精確的小數位四捨五入處理。
     * 提供精确的小数位四舍五入处理
     *
     * @param v
     *            需要四捨五入的數位
     * @param scale
     *            小數點後保留幾位
     * @return 四捨五入後的結果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}