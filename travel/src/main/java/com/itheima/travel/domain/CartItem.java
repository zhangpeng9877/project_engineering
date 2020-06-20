package com.itheima.travel.domain;

import lombok.Data;

/**
 * @author 张鹏
 * @date 2020/5/22 12:34
 */
@Data
public class CartItem {

    private Route route;    // 商品
    private Integer num;    // 商品数量
    private Double subTotal;    // 小计金额

    // 计算小计金额的get方法
    public Double getSubTotal() {
        subTotal = route.getPrice() * num;
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}
