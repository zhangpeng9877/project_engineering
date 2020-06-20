package com.itheima.travel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.dao.CategoryDao;
import com.itheima.travel.domain.Category;
import com.itheima.travel.service.CategoryService;
import com.itheima.travel.util.JedisUtils;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/18 22:36
 */
public class CategoryServiceImpl implements CategoryService {

    // 查询导航条所有内容
    @Override
    public List<Category> findAll() {
        List<Category> categoryList = null;
        ObjectMapper objectMapper = new ObjectMapper();

        // 1.先查缓存是否存在
        Jedis jedis = JedisUtils.getJedis();
        // 判断redis是否存在导航分类的缓存
        if (jedis.exists("travel_category")) {  // 存在
            // 查询缓存的数据
            String json = jedis.get("travel_category");
            // 将json转为java对象
            try {
                categoryList = objectMapper.readValue(json, List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {    // 不存在，则查询数据库
            // 创建代理对象
            SqlSession sqlSession = MyBatisUtils.openSession();
            CategoryDao mapper = sqlSession.getMapper(CategoryDao.class);
            // 调用dao接口查询数据
            categoryList = mapper.findAll();
            // 关闭工厂会话
            MyBatisUtils.close(sqlSession);

            // 同步到redis缓存中
            try {
                // 把集合转换为json数据，以json数据的形式保存到redis中的String类型中
                String json = objectMapper.writeValueAsString(categoryList);
                jedis.set("travel_category", json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        // 归还连接池
        jedis.close();
        return categoryList;
    }

    @Override
    public Category findByCid(Integer cid) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        CategoryDao categoryDao = sqlSession.getMapper(CategoryDao.class);
        Category category = categoryDao.findByCid(cid);
        MyBatisUtils.close(sqlSession);
        return category;
    }
}
