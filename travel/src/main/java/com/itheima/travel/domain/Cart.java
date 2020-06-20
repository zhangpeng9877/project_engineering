package com.itheima.travel.domain;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * @author 张鹏
 * 购物车对象
 * @date 2020/5/22 12:36
 */
@Data
public class Cart {
    private Integer cartNum;    // 购物车的总数量
    private Double cartTotal;      // 购物车中的总金额
    private LinkedHashMap<Integer, CartItem> cartItemMap = new LinkedHashMap<>();    // 商品购物项集合

    // 计算商品总数量
    public Integer getCartNum() {
        // 遍历集合项
        cartNum = 0;    // 清除操作
        for (CartItem cartItem : cartItemMap.values()) {
            cartNum += cartItem.getNum();
        }
        return cartNum;
    }

    public void setCartNum(Integer cartNum) {
        this.cartNum = cartNum;
    }

    // 计算商品总金额
    public Double getCartTotal() {
        // 清除操作
        cartTotal = 0.0;
        // 遍历集合项
        for (CartItem cartItem : cartItemMap.values()) {
            cartTotal += cartItem.getSubTotal();
        }
        return cartTotal;
    }

    public void setCartTotal(Double cartTotal) {
        this.cartTotal = cartTotal;
    }
}
