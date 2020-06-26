package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSetTingService;
import com.itheima.health.util.POIUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/26 20:26
 */

@RestController
@RequestMapping("/ordersetting")
public class OrderSetTingController {

    @Reference
    private OrderSetTingService orderSetTingService;

    /**
     * 上传excel表格文件
     *
     * @param excelFile
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {

        try {
            // 调用工具包，读取excel中的数据
            List<String[]> readExcel = POIUtils.readExcel(excelFile);
            // 封装到List<OrderSetting>对象中
            List<OrderSetting> orderSettingList = new ArrayList<>();
            // 日期格式解析
            SimpleDateFormat dateFormat = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            Date orderDate = null;
            OrderSetting orderSetting = null;
            // 遍历出读取到excel的数据
            for (String[] strings : readExcel) {
                orderDate = dateFormat.parse(strings[0]);// 解析日期
                int number = Integer.valueOf(strings[1]);   // 转化为int类型的数据
                orderSetting = new OrderSetting(orderDate, number); // 将日期和可预约人数封装进对象
                orderSettingList.add(orderSetting); // 添加进集合中
            }
            // 调用service层业务
            orderSetTingService.add(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HealthException(MessageConstant.ORDERSETTING_NO_NUMBER);
        }
    }

}
