package com.itheima.travel.util;

/**
 * @author 张鹏
 * @date 2020/5/21 12:08
 */

/**
 * string字符串转换为int工具类
 */
public class StringToIntUtils {
    private StringToIntUtils() {

    }


    public static Integer parseInt(String strNum, Integer defaults) {
        Integer num = defaults;
        try {
            num = Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
        }
        return num;
    }

    public static Integer parseInt(String strNum) {
        return parseInt(strNum, 0);
    }

}
