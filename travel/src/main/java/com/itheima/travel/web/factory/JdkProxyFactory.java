package com.itheima.travel.web.factory;

import cn.hutool.core.io.file.FileWriter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author 张鹏
 * @date 2020/5/24 20:08
 */
public class JdkProxyFactory {
    // 提供一个生产日志记录的代理对象方法
    public static Object createLogProxy(Object target) {
        // proxy代理对象
        Object proxy = null;

        // 根据工具类，创建代理对象
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        proxy = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            // 实现方法的增强
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 实现日志记录
                StringBuffer stringBuffer = new StringBuffer();
                // 记录日志时间
                stringBuffer.append("【执行时间】" + LocalDateTime.now());    // jdk1.8 提供获取当前时间的新特性
                // 记录哪个类执行
                stringBuffer.append("【执行目标类】" + target.getClass().getName());
                // 记录执行哪个方法
                stringBuffer.append("【执行目标方法】" + method.getName());
                // 记录方法传递的哪个参数
                stringBuffer.append("【方法实际参数】" + Arrays.toString(args));
                // 先调用目标对象原有的功能
                Object invoke = null;
                try {
                    invoke = method.invoke(target, args);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 记录异常信息
                    stringBuffer.append("【异常信息】" + e.getCause().getMessage());
                }
                // 将日志信息保存到文件中
                String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));  // 获取当前的日期时间
                FileWriter fileWriter = new FileWriter("E:/travel" + format + ".log", "utf-8");
                fileWriter.append(stringBuffer.toString() + "\n");
                return invoke;
            }
        });
        return proxy;
    }
}
