package com.atguigu.manager.cart;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.service.ShoppingCartServiceInf;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shuangquan.Xu on 2017/11/29.
 */
public class LoginCart extends CartManager{



    private void addCar(HttpSession session, T_MALL_SHOPPINGCAR cart, ShoppingCartServiceInf shoppingCartService) {
        //登陆，操作db和缓存
        List<T_MALL_SHOPPINGCAR> cars_list = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
        if(cars_list == null || cars_list.isEmpty()){//session中没有购物车信息
            cars_list = new ArrayList<>();
            cars_list.add(cart);
            shoppingCartService.add_cart(cart);
            session.setAttribute("list_cart_session",cars_list);
            return;
        }
        for(T_MALL_SHOPPINGCAR shoppingCart : cars_list){//有重复
            if(cart.getSku_id() == shoppingCart.getSku_id()){
                int tjshl = shoppingCart.getTjshl() + cart.getTjshl();
                double hj = shoppingCart.getHj() + cart.getHj();
                shoppingCart.setTjshl(tjshl);
                shoppingCart.setHj(hj);
                shoppingCartService.update_cart(shoppingCart);
                return;
            }
        }
        //无重复
        cars_list.add(cart);
        shoppingCartService.add_cart(cart);
        return;
    }

    @Override
    public void addCar(String cookie_cat, T_MALL_SHOPPINGCAR cart, HttpServletResponse response, HttpSession session, ShoppingCartServiceInf shoppingCartService) {
        addCar(session, cart, shoppingCartService);
    }
}
