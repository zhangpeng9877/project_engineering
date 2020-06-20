package com.itheima.travel.service;

import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;

/**
 * @author 张鹏
 * @date 2020/5/16 15:53
 */
public interface UserService {

    ResultInfo register(User user);

    User findByUser(User user);
    
    ResultInfo sendSms(String telephone, String codeSms);

    ResultInfo pwdLogin(User user);

    void updateInfo(User populate);
}
