package com.sx.ma.utils;

/**
 * 类型转换工具
 *
 * @author nhh
 */
public class ConvertUtils {

    /**
     * 小数位数(不可变)
     */
    private static final Integer SCALE = 100;

    /**
     * 转换成Double类型并保留两位小数
     *
     * @param data 数据
     * @return Double类型
     */
    public static double toDouble(String data) {
        double value = Double.parseDouble(data);
        return (double) Math.round(value * SCALE) / SCALE;
    }

    /**
     * 转换成Double类型并保留两位小数
     *
     * @param data Double类型
     * @return Double类型
     */
    public static double toDouble(Double data) {
        return (double) Math.round(data * SCALE) / SCALE;
    }
}