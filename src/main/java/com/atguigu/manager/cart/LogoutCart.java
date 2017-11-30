package com.atguigu.manager.cart;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.service.ShoppingCartServiceInf;
import com.atguigu.util.MyJsonUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shuangquan.Xu on 2017/11/29.
 */
public class LogoutCart extends CartManager {

    private void addCar(String cookie_cat, T_MALL_SHOPPINGCAR cart, HttpServletResponse response) {
        List<T_MALL_SHOPPINGCAR> cars_list = null;
            String cart_json = "";
            if(StringUtils.isNotBlank(cookie_cat)){//cookie不为空
                cars_list = MyJsonUtil.json_to_list(cookie_cat, T_MALL_SHOPPINGCAR.class);
                if(cars_list == null || cars_list.isEmpty()){
                    throw new RuntimeException("解析cookie异常");
                }
                for(T_MALL_SHOPPINGCAR shoppingcar : cars_list){//有重复
                    if(cart.getSku_id() == shoppingcar.getSku_id()){
                        int tjshl = shoppingcar.getTjshl() + cart.getTjshl();
                        double hj = shoppingcar.getHj() + cart.getHj();
                        shoppingcar.setTjshl(tjshl);
                        shoppingcar.setHj(hj);
                        cart_json = MyJsonUtil.list_to_json(cars_list);
                        addCartCookie(cart_json,response);
                        return;
                    }
                }
                //没有重复
                cars_list.add(cart);
                cart_json = MyJsonUtil.list_to_json(cars_list);
                addCartCookie(cart_json,response);
                return;
            }
            //cookie为空
            cars_list  = new ArrayList<>();
            cars_list.add(cart);
            cart_json = MyJsonUtil.list_to_json(cars_list);
            addCartCookie(cart_json,response);
            return;
    }


    public void addCookie(String cookieName,String cookieVal, HttpServletResponse response){
        Cookie cookie = new Cookie(cookieName, cookieVal);
        cookie.setMaxAge(60*60*24);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public  void addCartCookie(String cookieVal, HttpServletResponse response){
        addCookie("list_cart_cookie",cookieVal,response);
    }


    @Override
    public void addCar(String cookie_cat, T_MALL_SHOPPINGCAR cart, HttpServletResponse response, HttpSession session, ShoppingCartServiceInf shoppingCartService) {
       addCar(cookie_cat, cart, response);
    }
}
