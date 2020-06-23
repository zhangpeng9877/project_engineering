package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/22 19:45
 */

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupService.add(checkGroup, checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页查询
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckGroup> list = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
    }

    /**
     * 根据检查组id查询检查项的指定所有id
     */
    @GetMapping("/findByIdWhitCheckItem")
    public Result findByIdWhitCheckItem(Integer id) {
        List<Integer> arrayId = checkGroupService.findByIdWhitCheckItem(id);
        return new Result(true, MessageConstant.CHECKGROUP_ARRAY_ID, arrayId);
    }

    /**
     * 修改检查组和检查项
     */
    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupService.update(checkGroup, checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 删除检查组，并且需要删除中间表关系
     */
    @PostMapping("/delete")
    public Result delete(Integer id) {
        checkGroupService.delete(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }


}
