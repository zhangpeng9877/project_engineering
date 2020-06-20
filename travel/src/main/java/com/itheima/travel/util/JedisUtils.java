package com.itheima.travel.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 张鹏
 * @date 2020/5/20 20:55
 */

/**
 * jedis连接池工具类
 */
public class JedisUtils {

    private static JedisPool jedisPool;

    private JedisUtils() {

    }

    static {
        try {
            // 获取类加载读取 jedis.properties 获取io流
            InputStream inputStream = JedisUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
            // 创建properties对象 加载io流
            Properties properties = new Properties();
            properties.load(inputStream);
            // 读取配置文件中的数据
            String host = properties.getProperty("host");   // ip地址
            String port = properties.getProperty("port");   // redis端口号
            String maxTotal = properties.getProperty("maxTotal");   // 最大连接数
            String maxIdle = properties.getProperty("maxIdle");     // 空闲时最大连接数
            String maxWaitMillis = properties.getProperty("maxWaitMillis"); // 最大等待连接时间
            // 创建jedis连接池对象
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            // 设置最大连接数、空闲时最大连接数、最大等待连接时间
            jedisPoolConfig.setMaxTotal(Integer.parseInt(maxTotal));
            jedisPoolConfig.setMaxIdle(Integer.parseInt(maxIdle));
            jedisPoolConfig.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
            // 创建连接池对象
            jedisPool = new JedisPool(jedisPoolConfig, host, Integer.parseInt(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 提供获取jedis连接方法
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

}
