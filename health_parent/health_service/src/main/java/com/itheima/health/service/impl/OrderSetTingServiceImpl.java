package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.OrderSetTingDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSetTingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张鹏
 * @date 2020/6/26 20:27
 */

@Service
public class OrderSetTingServiceImpl implements OrderSetTingService {

    @Autowired
    private OrderSetTingDao orderSetTingDao;

    /**
     * 批量导入预约
     *
     * @param orderSettingList
     */
    @Transactional
    @Override
    public void add(List<OrderSetting> orderSettingList) {
        // 遍历
        for (OrderSetting orderSetting : orderSettingList) {
            // 判断是否已经存在数据库，根据日期查找，注意：日期里是否有时分秒，数据库里的日期是没有时分秒的
            OrderSetting osInDB = orderSetTingDao.findByOrderDate(orderSetting.getOrderDate());
            if (osInDB != null) {   // 表示已经存在，修改数据的数量
                // 判断已经存在数据库中的预约的人数不能大于重新修改的总预约人数
                if (osInDB.getReservations() > orderSetting.getNumber()) {
                    throw new HealthException(MessageConstant.ORDERSETTING_NO_NUMBER);
                }
                orderSetTingDao.updateNumber(orderSetting);
            } else {    // 表示不存在，添加新数据
                orderSetTingDao.add(orderSetting);
            }
        }
    }


    /**
     * 通过月份来获取预约设置信息
     */
    @Override
    public List<Map<String, Integer>> getOrderSetTingByMonth(String month) {
        // 组织查询，dateBegin表示月份开始时间，dateEnd月份结束时间
        String monthBegin = month + "-1";   // 变为yyyy-MM-1格式
        String monthEnd = month + "-31";   // 变为yyyy-MM-31格式
        // 调用dao层查询出在当前月所有的预约信息
        List<OrderSetting> list = orderSetTingDao.getOrderSetTingByMonth(monthBegin, monthEnd);
        List<Map<String, Integer>> mapList = new ArrayList<>();
        // 遍历出查询出的所有数据，添加到map集合中
        list.forEach(orderSetting -> {
            Map orderSetTingMap = new HashMap();
            orderSetTingMap.put("date", orderSetting.getOrderDate().getDate());   // 获取当前几号
            orderSetTingMap.put("number", orderSetting.getNumber()); // 添加可预约的人数
            orderSetTingMap.put("reservations", orderSetting.getReservations()); // 添加已经预约的人数
            mapList.add(orderSetTingMap);   // 完成一个，添加进map类型的list集合中
        });
        return mapList;
    }


    /**
     * 通过日历来设置预约的数量
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        // 先判断需要设置的日期是否存在数据库中
        OrderSetting byOrderDate = orderSetTingDao.findByOrderDate(orderSetting.getOrderDate());
        if (byOrderDate == null) {  // 表示不存在，则添加
            orderSetTingDao.add(orderSetting);
        } else {    // 表示存在，则修改数量
            // 已经预约的数量必须小于修改的预约数量，否则报错
            if (byOrderDate.getReservations() > orderSetting.getNumber()) {
                throw new HealthException(MessageConstant.ORDERSETTING_FAIL);
            }
            // 如果符合规范，则进行修改
            orderSetTingDao.updateNumber(orderSetting);
        }
    }
}
