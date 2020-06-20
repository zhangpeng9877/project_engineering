package com.itheima.travel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.Cart;
import com.itheima.travel.domain.User;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * @author 张鹏
 * @date 2020/5/22 13:39
 */
public class CartUtils {
    private CartUtils() {

    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    // 将购物车设置到redis中
    public static void setCartToRedis(User user, Cart cart) {
        Jedis jedis = null;
        try {
            // 获取jedis连接对象
            jedis = JedisUtils.getJedis();
            // 将cart转换为json
            String json = objectMapper.writeValueAsString(cart);
            // 保存到redis中
            jedis.set("cart_" + user.getUsername(), json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            // 归还连接
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    // 从redis中获取购物车对象
    public static Cart getRedisToCart(User user) {
        // 连接jedis对象
        Jedis jedis = null;
        Cart cart = new Cart();
        try {
            jedis = JedisUtils.getJedis();
            // 判断是否存在
            if (jedis.exists("cart_" + user.getUsername())) {
                String json = jedis.get("cart_" + user.getUsername());
                // 转换为cart对象
                cart = objectMapper.readValue(json, Cart.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 归还连接
            if (jedis != null) {
                jedis.close();
            }
        }
        return cart;
    }
}
