package com.atguigu.manager.cart;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.service.ShoppingCartServiceInf;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Shuangquan.Xu on 2017/11/29.
 */
public class CartContext {

    private String cookie_cat;
    private T_MALL_SHOPPINGCAR cart;
    private HttpServletResponse response;
    private HttpSession session;
    private ShoppingCartServiceInf shoppingCartService;

    private CartManager cartManager;

    public void addCart(){
        cartManager.addCar(cookie_cat,cart, response,session, shoppingCartService);
    }

    public CartContext(String cookie_cat, T_MALL_SHOPPINGCAR cart, HttpServletResponse response, HttpSession session, ShoppingCartServiceInf shoppingCartService, CartManager cartManager) {
        this.cookie_cat = cookie_cat;
        this.cart = cart;
        this.response = response;
        this.session = session;
        this.shoppingCartService = shoppingCartService;
        this.cartManager = cartManager;
    }


}
