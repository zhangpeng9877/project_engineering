package com.itheima.travel.test;

import java.util.Scanner;

/**
 * @author 张鹏
 * @date 2020/5/27 17:39
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println("请输入java文件名：");
        String fileName = new Scanner(System.in).nextLine();
        // 获取文件名第一个字符
        String first = fileName.substring(0, 1);
        System.out.println("文件名的第一个字符：" + first);
        // 获取到文件的后缀名
        int index = fileName.lastIndexOf(".");  // 先获取到 . 的所在索引
        String suffixName = fileName.substring(index);  // 获取到后缀名
        System.out.println("文件后缀名是：" + suffixName);
        // 把文件替换成Test.java
        String newFileName = fileName.replace(fileName, "Test.java");
        System.out.println("新的文件名：" + newFileName);

    }
}
