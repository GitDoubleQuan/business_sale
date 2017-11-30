package com.atguigu.controller;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.manager.cart.CartContext;
import com.atguigu.manager.cart.LoginCart;
import com.atguigu.manager.cart.LogoutCart;
import com.atguigu.service.ShoppingCartServiceInf;
import com.atguigu.util.MyJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shuangquan.Xu on 2017/11/29.
 */
@Controller
public class CartController {

    @Autowired
    private ShoppingCartServiceInf shoppingCartService;


    @RequestMapping("item_check")
    public String item_check(int sku_id,String shfxz,HttpSession session,HttpServletResponse response,@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, ModelMap map){

        List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
        T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");

        if (user == null) {
            list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
        } else {
            list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
        }

        for(T_MALL_SHOPPINGCAR sku : list_cart){
            if(sku_id == sku.getSku_id()){
                sku.setShfxz(shfxz);
                if(user == null){
                    String cart_json = MyJsonUtil.list_to_json(list_cart);
                    Cookie cookie = new Cookie("list_cart_cookie", cart_json);
                    cookie.setMaxAge(60*60*24);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }else {
                    shoppingCartService.update_cart(sku);
                }
                break;
            }
        }

        map.put("sum",sumJG(list_cart));
        map.put("list_cart",list_cart);

        return "sale_search_cart_list_inner";
    }

    @RequestMapping("goto_cart_list")
    public String goto_cart_list(HttpSession session,@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, ModelMap map) {

        List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
        T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");

        // 根据用户是否登陆选择cookie或者session中的数据
        if (user == null) {
            list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
        } else {
            list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
        }

        BigDecimal sum = sumJG(list_cart);

        map.put("list_cart", list_cart);
        map.put("sum", sum);
        return "sale_search_cart_list";
    }


    /**
     * 添加购物车涉及到复杂的条件分支判断，这种情况最好用策略模式
     * @param cookie_cat
     * @param cart
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("add_cart")
    public String addCart(@CookieValue(value = "list_cart_cookie", required = false) String cookie_cat, T_MALL_SHOPPINGCAR cart, HttpSession session, HttpServletResponse response){

        T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT)session.getAttribute("user");
        CartContext cartContext = null;
        if(user == null){//未登录
            cartContext = new CartContext(cookie_cat, cart, response, session, shoppingCartService, new LogoutCart());
        }else {//登陆
            cartContext = new CartContext(cookie_cat,cart,response,session,shoppingCartService,new LoginCart());
        }
        cartContext.addCart();
        return "redirect:/cart_success.do";

    }


    @RequestMapping("miniCart.do")
    public String miniCart(HttpSession session,@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie, ModelMap map) {

        List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
        T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");

        // 根据用户是否登陆选择cookie或者session中的数据
        if (user == null) {
            list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
        } else {
            list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
        }
        BigDecimal sumJG = sumJG(list_cart);
        int sumSku = sumSku(list_cart);

        map.put("sumJG",sumJG);
        map.put("sumSku",sumSku);
        map.put("list_cart", list_cart);
        return "sale_search_miniCart_list";
    }

    private BigDecimal sumJG(List<T_MALL_SHOPPINGCAR> cart){
        BigDecimal sum = new BigDecimal("0");
        if(cart == null || cart.isEmpty()){
            return sum;
        }
        for(T_MALL_SHOPPINGCAR sku : cart){
            if("1".equals(sku.getShfxz())){
                sum = sum.add(new BigDecimal(sku.getHj() + ""));
            }
        }
        return  sum;
    }

    private int sumSku(List<T_MALL_SHOPPINGCAR> cart){
        int sum = 0;
        if(cart == null || cart.isEmpty()){
            return sum;
        }
        for(T_MALL_SHOPPINGCAR sku : cart){
            sum = sum + sku.getTjshl();
        }
        return sum;
    }

    @RequestMapping("cart_success")
    public String cart_success() {
        return "sale_cart_success";
    }
}
