package com.atguigu.manager.cart;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.service.ShoppingCartServiceInf;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Shuangquan.Xu on 2017/11/29.
 */
public abstract class CartManager {

    public abstract void addCar(String cookie_cat, T_MALL_SHOPPINGCAR cart, HttpServletResponse response,HttpSession session, ShoppingCartServiceInf shoppingCartService);


}
