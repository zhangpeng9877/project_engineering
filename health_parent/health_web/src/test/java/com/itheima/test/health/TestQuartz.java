package com.itheima.test.health;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 张鹏
 * @date 2020/6/25 15:50
 */

public class TestQuartz {

    /**
     * Quartz定时任务调度测试
     * @throws Exception
     */
    @Test
    public void test01() throws Exception{
        new ClassPathXmlApplicationContext("spring-quartz.xml");
        System.in.read();
    }
}
