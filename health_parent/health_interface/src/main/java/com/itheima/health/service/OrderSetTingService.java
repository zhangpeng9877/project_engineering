package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;

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
}
