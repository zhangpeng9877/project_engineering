package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetMealService;
import com.itheima.health.util.QiNiuUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/27 21:26
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetMealService setMealService;

    /**
     * 查询所有
     */
    @PostMapping("/getSetmeal")
    public Result getSetmeal() {
        // 查询所有的套餐
        List<Setmeal> list = setMealService.findAll();
        // 设置图片的全路径
        list.forEach(setmeal -> {
            setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        });
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, list);
    }

    /**
     * 查询套餐的详情
     */
    @PostMapping("/findDetailById")
    public Result findDetailById(Integer id) {
        // 调用服务查询详情
        Setmeal setmeal = setMealService.findDetailById(id);
        // 设置图片的完整路径
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }

}
