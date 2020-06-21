package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.entity.Result;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 张鹏
 * @date 2020/6/21 18:00
 */

@RestController// 携带响应json数据
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有项
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        return checkItemService.findAll();
    }

}
