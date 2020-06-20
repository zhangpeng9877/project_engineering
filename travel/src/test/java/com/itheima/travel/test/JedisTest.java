package com.itheima.travel.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author 张鹏
 * @date 2020/5/20 20:32
 */
public class JedisTest {

    // 快速入门之，向redis设置string类型的数据
    @Test
    public void test01() throws Exception {
        // 1. 创建连接对象(参数是ip地址和redis的端口号)
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 2. 调用set方法设置数据
        String set = jedis.set("name", "周清");
        System.out.println(set);    // 返回ok表示成功...
        // 3. 释放资源
        jedis.close();
    }

    // 快速入门之，查询string类型数据
    @Test
    public void test02() throws Exception{
        // 1. 创建连接对象
        Jedis jedis = new Jedis();  // 在不写参数的时候，默认连接本地ip，和6379端口号
        // 2. 调用get方法获取
        String name = jedis.get("name");
        System.out.println(name);
        // 3. 释放资源
        jedis.close();
    }
}
