package com.itheima.travel.test;


import java.util.Scanner;

public class Homework_12 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个学员信息 : ");
        String message = sc.nextLine();

        //去掉中间空格
        message = message.replaceAll(" ", "");
        //分割
        String[] split = message.split(",");

        NewStuDent newStuDent = new NewStuDent(split[0], Integer.parseInt(split[1]), split[2].charAt(0));
        String newString = newStuDent.toString();
        System.out.println(newString);


    }
}


