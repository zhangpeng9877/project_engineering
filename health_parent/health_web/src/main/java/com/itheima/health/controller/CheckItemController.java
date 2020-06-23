package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckItem> list = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
    }

    /**
     * 添加数据项
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 修改数据项
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem) {
        checkItemService.update(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 删除数据项
     */
    @PostMapping("/delete")
    public Result delete(Integer id) {
        checkItemService.delete(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 分页查询
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckItem> page = checkItemService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, page);
    }

}
