package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.OrderSetTingDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSetTingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
