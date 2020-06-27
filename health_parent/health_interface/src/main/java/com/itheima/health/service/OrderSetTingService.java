package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author 张鹏
 * @date 2020/6/26 20:27
 */
public interface OrderSetTingService {

    /**
     * 批量导入预约
     *
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList) throws HealthException;

    /**
     * 通过月份来获取预约设置信息
     */
    List<Map<String, Integer>> getOrderSetTingByMonth(String month);

    /**
     * 通过日历来设置预约的数量
     */
    void editNumberByDate(OrderSetting orderSetting) throws HealthException;
}
