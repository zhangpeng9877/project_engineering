package com.itheima.travel.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.travel.dao.UserDao;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.UserService;
import com.itheima.travel.util.MyBatisUtils;
import com.itheima.travel.util.SmsUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @author 张鹏
 * @date 2020/5/16 15:53
 */
public class UserServiceImpl implements UserService {
    // 注册用户
    @Override
    public ResultInfo register(User user) {
        // 获取代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        // 对密码进行加密 (糊涂工具包提供 SecureUtil )
        String password = SecureUtil.md5(user.getPassword());
        user.setPassword(password);
        // 初始化头像
        user.setPic("/pic/girl.png");

        try {
            // 保存，注册成功
            userDao.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false);
        } finally {
            // 关闭工厂会话
            MyBatisUtils.close(sqlSession);
        }

        // 返回结果
        return new ResultInfo(true, "注册成功");
    }

    // 指定查询用户信息
    @Override
    public User findByUser(User user) {
        // 获取代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        // 调用方法
        user = userDao.findByUser(user);
        // 关闭工厂会话
        MyBatisUtils.close(sqlSession);
        return user;
    }

    // 发送验证码
    @Override
    public ResultInfo sendSms(String telephone, String codeSms) {
        // 拼接为json数据
        String param = "{\"code\":\"" + codeSms + "\"}";

        // 调用工具类发送短信
        try {

            SendSmsResponse sms = SmsUtils.sendSms(telephone, "清风E旅", "SMS_190272195", param);
            // 判断是否发送成功
            if (sms.getCode().equals("OK")) {
                return new ResultInfo(true, "验证码短信发送成功");
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new ResultInfo(false, "验证码短信发送失败");

        // 测试期间可以写伪代码...
        // return new ResultInfo(true, "验证码短信发送成功");
    }

    // 用户密码登录
    @Override
    public ResultInfo pwdLogin(User user) {
        // 获取代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        // 对密码进行加密,重新赋值到user对象中
        user.setPassword(SecureUtil.md5(user.getPassword()));
        // 查询用户
        User currentUser = userDao.findByUser(user);
        // 关闭会话
        MyBatisUtils.close(sqlSession);
        // 判断是否存在
        if (currentUser == null) {  // 不存在
            return new ResultInfo(false);
        }
        // 表示存在
        return new ResultInfo(true, "登录成功", currentUser);
    }

    // 修改用户基本信息
    @Override
    public void updateInfo(User populate) {
        // 获取代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        userDao.update(populate);

        // 关闭会话
        MyBatisUtils.close(sqlSession);
    }
}
