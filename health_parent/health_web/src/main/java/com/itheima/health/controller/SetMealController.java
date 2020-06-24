package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetMealService;
import com.itheima.health.util.QiniuUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author 张鹏
 * @date 2020/6/23 13:45
 */

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    /**
     * 分页查询
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> list = setMealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, list);
    }

    /**
     * 查询检查项所有数据
     */
    @GetMapping("/findAllCheckGroup")
    public Result findAllCheckGroup() {
        List<CheckGroup> list = setMealService.findAllCheckGroup();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
    }

    /**
     * 添加套餐管理，关联检查组数据
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        setMealService.add(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            // 获取初始的文件名
            String filename = imgFile.getOriginalFilename();
            // 获取文件的后缀名
            filename = filename.substring(filename.lastIndexOf("."));
            // 使用uuid生成一个唯一的文件名
            filename = UUID.randomUUID().toString() + filename;
            // 调用七牛云工具类上传文件到空间中
            QiniuUtils.uploadFileQiniu(imgFile.getBytes(), filename);
            // 图片上传成功，把文件名返回过去
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, filename);
        } catch (IOException e) {
            e.printStackTrace();
            // 图片上传失败
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 根据套餐管理的id获取套餐统计数据
     */
    @GetMapping("/findSetMealByIdCheckGroupId")
    public Result findSetMealByIdCheckGroupId(Integer setmealId) {
        List<Integer> arrayIds = setMealService.findSetMealByIdCheckGroupId(setmealId);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL, arrayIds);
    }

    /**
     * 修改套餐管理数据
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        setMealService.update(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.UPDATE_SETMEAL_SUCCESS);
    }

    /**
     * 删除套餐管理
     */
    @PostMapping("/delete")
    public Result delete(Integer id, String fileName) {
        setMealService.delete(id);
        // 删除存储在七牛云的文件
        QiniuUtils.deleteFileFromQiniu(fileName);
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

}
