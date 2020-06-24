package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/23 13:47
 */
public interface SetMealDao {

    /**
     * 分页查询
     */
    Page<Setmeal> findPage(String queryString);

    /**
     * 查询检查项所有数据
     */
    List<CheckGroup> findAllCheckGroup();

    /**
     * 添加套餐管理数据
     */
    void add(Setmeal setmeal);

    /**
     * 对套餐管理和检查组进行中间表的关联添加
     */
    void addSetMealIdCheckGroupId(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    /**
     * 根据套餐管理的id获取套餐统计数据
     */
    List<Integer> findSetMealByIdCheckGroupId(Integer setmealId);

    /**
     * 删除套餐管理和检查组中间表的关联关系
     */
    void deleteSetmealByIdCheckGroup(Integer setmealId);

    /**
     * 修改套餐管理的数据
     */
    void update(Setmeal setmeal);

    /**
     * 删除套餐管理的数据
     */
    void delete(Integer id);

    /**
     * 先判断和订单表（主表）是否存在关联关系
     */
    Integer findSetMealCountOrder(Integer id);
}
