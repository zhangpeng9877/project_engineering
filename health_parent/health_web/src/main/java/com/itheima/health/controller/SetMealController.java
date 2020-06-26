package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetMealService;
import com.itheima.health.util.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private JedisPool jedisPool;

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
        if (setmeal.getImg() != null && setmeal.getImg().length() > 0) {
            // 添加成功。。需要将存储在数据库的图片名称保存到redis中
            Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
            // 归还连接
            jedis.close();
        }
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
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), filename);
            // 图片上传成功，把文件名返回过去
            Map<String, String> imgMap = new HashMap<>();
            imgMap.put("domain", QiNiuUtils.DOMAIN);
            imgMap.put("imgName", filename);
            // 将上传到七牛空间的文件名另外存储到redis中，基于redis的set集合储存
            Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEAL_PIC_RESOURCES, filename);
            jedis.close();
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, imgMap);
        } catch (IOException e) {
            e.printStackTrace();
            // 图片上传失败
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 指定id查询项目组信息
     */
    @GetMapping("/findById")
    public Result findById(Integer id) {
        Setmeal setmeal = setMealService.findById(id);
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("setmeal", setmeal);
        hashMap.put("imgPath", QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, null, hashMap);
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
        //  获取原有图片的名称，判断图片是否更改了，如果更改了，那么旧的图片应该从有用的集合中移除
        Setmeal oldSetmeal = setMealService.findById(setmeal.getId());
        if (setmeal.getImg() != null) { // 判断用户是否上传图片
            // 先删除储存在redis中旧的图片名字
            Jedis jedis = jedisPool.getResource();
            if (oldSetmeal.getImg() != null) {  // 判断数据库是否存在图片名称
                jedis.srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, oldSetmeal.getImg());
            }
            // 再添加新的图片名字进去redis中
            jedis.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
            jedis.close();
        }
        // 调用service更新数据
        setMealService.update(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.UPDATE_SETMEAL_SUCCESS);
    }

    /**
     * 删除套餐管理
     */
    @PostMapping("/delete")
    public Result delete(Integer id) {
        // 查询出储存在数据库中的图片名称
        Setmeal oldSetmeal = setMealService.findById(id);
        setMealService.delete(id);
        // 在redis保存这张图片名称的数据也删除掉
        if (oldSetmeal.getImg() != null) {
            Jedis jedis = jedisPool.getResource();
            jedis.srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, oldSetmeal.getImg());
            jedis.close();
        }
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

}
