package com.sx.ma.utils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean 工具类
 *
 * @author nhh
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
    /**
     * 复制数据
     *
     * @param source 源对象
     * @param tClass 复制类
     * @param <T>    泛型
     * @return 复制的对象
     */
    public static <T> T copyBeanProp(Object source, Class<T> tClass) {
        T result = null;
        try {
            result = tClass.newInstance();
            copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 复制集合数据
     *
     * @param list   源集合
     * @param tClass 复制类
     * @return 复制集合对象
     * @param <O> 泛型
     * @param <T> 泛型
     */
    public static <O, T> List<T> copyBeanPropList(List<O> list, Class<T> tClass) {
        return list.stream()
                .map(o -> copyBeanProp(o, tClass))
                .collect(Collectors.toList());
    }
}