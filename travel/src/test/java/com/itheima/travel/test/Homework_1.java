package com.itheima.travel.test;


import java.math.BigDecimal;
import java.math.RoundingMode;

/*
编程题【BigDecimal类】

有以下double数组：
	double[] arr = {0.1,0.2,2.1,3.2,5.56,7.21};请编程计算它们的总值及平均值(四舍五入保留小数点后2位)
*/
public class Homework_1 {
    public static void main(String[] args) {
        //创建BigDecimal对象存储总和,初始值 0
        BigDecimal sum = new BigDecimal(0);
        //遍历double[]数组
        double[] arr = {0.1, 0.2, 2.1, 3.2, 5.56, 7.21};
        for (int i = 0; i < arr.length; i++) {
            //创建BigDecimal对象存储总arr数组中的元素
            BigDecimal aa = new BigDecimal(arr[i]);
            //将转换为BigDecimal的arr数组中的元素,累加到sum变量中
            // sum = sum.add(aa);
            sum.add(aa);//<------数据结果都是0   ? 为什么
        }
        System.out.println("总和是 : " + sum);
        BigDecimal max = new BigDecimal(arr.length);//定义被除数据
        BigDecimal x = sum.divide(max, 2, RoundingMode.HALF_UP);
        System.out.println("平均值 : " + x);
    }
}
