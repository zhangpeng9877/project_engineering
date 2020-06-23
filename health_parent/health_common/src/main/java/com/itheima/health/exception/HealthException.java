package com.itheima.health.exception;

/**
 * 自定义异常处理
 */
public class HealthException extends RuntimeException {

    public HealthException(String message) {
        super(message);
    }
}
