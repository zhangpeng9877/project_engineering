package com.itheima.travel.test;

import com.itheima.travel.dao.UserDao;
import com.itheima.travel.domain.User;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author 张鹏
 * @date 2020/5/24 19:45
 */
public class ArrayTest {
    public static void main(String[] args) {
        int[] arr = {10, 27, 8, 5, 2, 1, 3, 55, 88};
        int[] newArr = new int[5];
        System.arraycopy(arr, 2, newArr, 0, 5);
        System.out.println(Arrays.toString(newArr));
    }

    @Test
    public void test01() throws Exception{
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setUid(1);
        System.out.println(mapper.findByUser(user));
    }
}
