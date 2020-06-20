package com.itheima.travel.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * @author 张鹏
 * @date 2020/5/16 15:57
 */
public class WebBeanUtils {

    // 实现快速封装实体类
    public static <T> T populate(T t, Map map) {
        try {
            // 调用apache工具类实现封装
            BeanUtils.populate(t, map);
        } catch (Exception e) {
            throw new RuntimeException("实体封装失败...");
        }
        return t;
    }

}
