package com.itheima.travel.dao;

import com.itheima.travel.domain.User;

/**
 * @author 张鹏
 * @date 2020/5/16 15:53
 */
public interface UserDao {


    // 可以指定查询用户信息
    User findByUser(User user);

    // 添加用户信息
    void save(User user);

    // 修改用户信息
    void update(User populate);
}
