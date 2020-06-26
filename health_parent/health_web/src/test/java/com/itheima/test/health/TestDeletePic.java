package com.itheima.test.health;

import com.itheima.health.constant.RedisConstant;
import com.itheima.health.util.QiNiuUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.Set;

/**
 * @author 张鹏
 * @date 2020/6/25 14:13
 */

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-redis.xml")
public class TestDeletePic {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 删除redis中两个不同key值储存的图片名称数据
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        Jedis jedis = jedisPool.getResource();
        // 获取到两个set集合中不同数据的数据
        Set<String> noImgNameSets = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        // 把set集合转化为array数组
        String[] arrayImg = noImgNameSets.toArray(new String[noImgNameSets.size()]);
        // 删除存储在七牛中的垃圾图片
        QiNiuUtils.removeFiles(arrayImg);
        // 再删除
        jedis.del(RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        jedis.del(RedisConstant.SETMEAL_PIC_RESOURCES);
        jedis.close();
    }

    @Test
    public void test02() throws Exception{
        Jedis jedis = jedisPool.getResource();
        Set<String> sdiff = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES);
        System.out.println(sdiff);
    }
}
