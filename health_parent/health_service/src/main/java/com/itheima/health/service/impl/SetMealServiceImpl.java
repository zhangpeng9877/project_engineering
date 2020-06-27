package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.SetMealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/23 13:48
 */

@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;

    /**
     * 分页查询
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        // 开启分页查询模式
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 判断是否存在模糊查询
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 调用dao层查询
        Page<Setmeal> listPage = setMealDao.findPage(queryPageBean.getQueryString());
        // 把封装到实体类中
        return new PageResult<Setmeal>(listPage.getTotal(), listPage.getResult());
    }


    /**
     * 查询检查项所有数据
     */
    @Override
    public List<CheckGroup> findAllCheckGroup() {
        return setMealDao.findAllCheckGroup();
    }

    /**
     * 添加套餐管理，关联检查组数据
     */
    @Transactional
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 添加套餐管理
        setMealDao.add(setmeal);
        // 获取到新添加数据的id
        Integer setmealId = setmeal.getId();
        // 遍历检查组的id
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                // 对套餐管理和检查组进行中间表的关联添加
                setMealDao.addSetMealIdCheckGroupId(setmealId, checkgroupId);
            }
        }
    }

    /**
     * 根据套餐管理的id获取套餐统计数据
     */
    @Override
    public List<Integer> findSetMealByIdCheckGroupId(Integer setmealId) {
        return setMealDao.findSetMealByIdCheckGroupId(setmealId);
    }

    /**
     * 修改套餐管理数据
     */
    @Transactional
    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        // 需要先删除套餐管理和检查组中间表的关联关系
        setMealDao.deleteSetmealByIdCheckGroup(setmeal.getId());
        // 修改套餐管理的数据
        setMealDao.update(setmeal);
        // 套餐和检查组建立关联关系
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                setMealDao.addSetMealIdCheckGroupId(setmeal.getId(), checkgroupId);
            }
        }
    }

    /**
     * 删除套餐管理
     */
    @Transactional
    @Override
    public void delete(Integer id) {
        // 先判断和订单表（主表）是否存在关联关系
        if (setMealDao.findSetMealCountOrder(id) > 0) {
            throw new HealthException(MessageConstant.SETMEAL_ORDER_FAIL);
        }
        // 需要先删除套餐管理和检查组中间表的关联关系
        setMealDao.deleteSetmealByIdCheckGroup(id);
        // 然后再删除套餐管理的数据
        setMealDao.delete(id);
    }


    /**
     * 指定id查询项目组信息
     */
    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }


    /**
     * 查询所有
     */
    @Override
    public List<Setmeal> findAll() {
        return setMealDao.findAll();
    }


    /**
     * 查询套餐的详情
     */
    @Override
    public Setmeal findDetailById(Integer id) {
        return setMealDao.findDetailById(id);
    }
}
