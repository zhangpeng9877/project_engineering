package com.itheima.travel.domain;

import lombok.Data;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/21 12:16
 */
@Data
public class Page<T> {
    public static final Integer PAGE_SIZE = 10; // 当前页显示的数量
    private static final Integer PAGE = 10;     // 规定只显示10页码数

    private Integer totalCount;// 总记录数
    private Integer totalPage;// 总页数
    private List<T> list; // 结果集
    private Integer currentPage;// 当前页
    private Integer pageSize = PAGE_SIZE;   // 当前页显示的数量

    private Integer begin;  // 起始值
    private Integer end;    // 结束值

    public Integer getBegin() {
        // 先调用计算方法
        jiSuan();
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    private void jiSuan() {
        if (totalPage < PAGE) {   // 第一种情况：判断页码不足规定的PAGE页码数
            begin = 1;
            end = totalPage;
        } else {    // 第二种情况：判断页码大于规定的PAGE页码数
            // 前五后四原值
            begin = currentPage - PAGE / 2;
            if (PAGE % 2 == 0) {
                end = currentPage + PAGE / 2 - 1;
            } else {
                end = currentPage + PAGE / 2;
            }


            // 修正当起始值小于1的情况
            if (begin < 1) {
                begin = 1;
                end = PAGE;
            }
            // 修正结束值大于总页数的情况
            if (end > totalPage) {
                end = totalPage;
                begin = end - (PAGE - 1);
            }
        }
    }

}
