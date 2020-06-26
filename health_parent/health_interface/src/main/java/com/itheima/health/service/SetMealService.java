package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/23 13:48
 */
public interface SetMealService {

    /**
     * 分页查询
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 查询检查项所有数据
     */
    List<CheckGroup> findAllCheckGroup();


    /**
     * 添加套餐管理，关联检查组数据
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 根据套餐管理的id获取套餐统计数据
     */
    List<Integer> findSetMealByIdCheckGroupId(Integer setmealId);

    /**
     * 修改套餐管理数据
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 删除套餐管理
     */
    void delete(Integer id) throws HealthException;

    /**
     * 指定id查询项目组信息
     */
    Setmeal findById(Integer id);
}
