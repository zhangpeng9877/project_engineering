package com.itheima.health.job;

import com.itheima.health.constant.RedisConstant;
import com.itheima.health.util.QiNiuUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 自定义任务quartz
 * 清理储存在七牛的垃圾图片
 */
@Component
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void runClear() {
        // 获取 redis的连接
        Jedis jedis = jedisPool.getResource();
        // 计算2个set集合的差集 所有七牛图片-保存到数据库
        // 获取需要删除的垃圾图片
        Set<String> need2Delete = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        // 调用七牛删除
        QiNiuUtils.removeFiles(need2Delete.toArray(new String[]{}));
        // 删除redis上的图片, 两边的图片已经同步了
        jedis.del(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        // 关闭连接
        jedis.close();
    }


}
