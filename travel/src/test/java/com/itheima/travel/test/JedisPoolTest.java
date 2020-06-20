package com.itheima.travel.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 张鹏
 * @date 2020/5/20 20:42
 */
public class JedisPoolTest {

    // 测试jedis内置的连接池
    @Test
    public void test01() throws Exception{
        // 1. 连接池配置对象
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(100);    // 设置最大连接数
        poolConfig.setMaxWaitMillis(3000);  // 设置最大等待时间，单位是毫秒
        poolConfig.setMaxIdle(10);      // 设置最大空闲连接数

        // 2. 创建连接池对象
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        // 3. 从池中获取连接
        Jedis jedis = jedisPool.getResource();
        // 4. 操作api
        String hget = jedis.hget("user", "age");
        System.out.println(hget);
        // 5. 归还到连接池
        jedis.close();
    }


}
