package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义全局异常捕获
 */

// 与前端约定好的，返回的都是json数据,并且定义好包被扫描
@RestControllerAdvice
public class HealExceptionAdvice {

    /**
     * 自定义招出的异常处理
     * @param he
     * @return
     */
    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException he) {
        return new Result(false, he.getMessage());
    }

    /**
     * 所有未知异常处理捕获
     * @param he
     * @return
     */
    //@ExceptionHandler(Exception.class)
    public Result handleException(Exception he){
        return new Result(false, "发生未知错误，操作失败，请联系管理员");
    }
}
