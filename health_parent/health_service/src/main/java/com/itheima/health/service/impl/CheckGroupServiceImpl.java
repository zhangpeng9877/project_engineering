package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/22 19:45
 */
@Service
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     */
    @Transactional  // 两个添加sql语句，需要事务管理
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 添加数据到检查组中
        checkGroupDao.add(checkGroup);
        // 获取到检查组的id
        Integer checkGroupId = checkGroup.getId();
        // 遍历checkitem表检查项中的id，添加检查组与检查项之间的表关系
        if (checkitemIds != null) {
            for (Integer checkitemId : checkitemIds) {
                // 调用方法，把checkitem检查项表和checkgroup检查组的表，id组合成关系，储存在t_checkgroup_checkitem关系表中
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }
    }


    /**
     * 分页查询
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        // 开启分页查询模式
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {  // 条件不为空进入if里面
            // 拼接%，模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 查询结果的返回值
        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());
        // 对结果集进行封装返回
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    /**
     * 根据检查组id查询检查项的指定所有id
     */
    @Override
    public List<Integer> findByIdWhitCheckItem(Integer id) {
        return checkGroupDao.findByIdWhitCheckItem(id);
    }

    /**
     * 修改检查组和检查项
     */
    @Transactional
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1：根据检查组id删除中间表数据（清理原有关联关系）
        checkGroupDao.deleteCheckGroupCheckItemById(checkGroup.getId());
        // 2.重新给中间表建立关系
        for (Integer checkitemId : checkitemIds) {
            checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), checkitemId);
        }
        // 修改检查组信息
        checkGroupDao.update(checkGroup);
    }

    /**
     * 删除检查组，先判断和主表是否有关联，有则不能删除，如果没有，则先解除和从表的关系，在删除
     */
    @Transactional
    @Override
    public void delete(Integer id) {
        // 判断和套餐(主)表是否有关联关系
        if (checkGroupDao.findCheckGroupCountBySetMeal(id) > 0) {
            // 需要在这个接口的方法中抛出HealthException异常才能被全局捕获，因为没有指定异常抛出，在web层会被运行时异常捕获
            throw new HealthException(MessageConstant.CHECKGROUP_SETMEAL_FAIL);
        }
        // 先删除与检查项（副）表中间表的关系
        checkGroupDao.deleteCheckGroupCheckItemById(id);
        // 在删除检查组的数据
        checkGroupDao.delete(id);
    }
}
