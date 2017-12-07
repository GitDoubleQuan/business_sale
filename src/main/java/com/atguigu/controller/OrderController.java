package com.atguigu.controller;

import com.atguigu.bean.*;
import com.atguigu.manager.order.OrderManager;
import com.atguigu.route.DataSourceToken;
import com.atguigu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Shuangquan.Xu on 2017/11/30.
 */
@Controller
public class OrderController {

    @Autowired
    AddressServerInf addressServerInf;

    //因为加了事务，所以注入的是OrderServiceImp的代理对象，所以只能用接口类型注入
    @Autowired
    private OrderServiceInf orderServiceInf;


    @RequestMapping("goto_check_order")
    public String goto_check_order(BigDecimal sum, HttpSession session, ModelMap map) {

        // 获得购物车
        // 获得用户信息
        List<T_MALL_SHOPPINGCAR> list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");

        T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
        if(user == null){
            return "redirect:/goto_login.do";
        }

        OBJECT_T_MALL_ORDER order = OrderManager.check_order(user, sum, list_cart);

        session.setAttribute("order",order);

        //如果service层加了事务，那么切换数据源只能在controller中；如果没加事务，则可以在获取数据源连接之前切换
        //因为开启事务需要数据源连接，set autocommit=off,
        // spring管理的事务在service中就已经开启了，所以只能在controller中切换数据源
        DataSourceToken.setToken("user");
        List<T_MALL_ADDRESS> list_address = addressServerInf.get_addresses_by_user_id(user);

        map.put("order", order);
        map.put("list_address", list_address);
        map.put("sum", sum);

        return "sale_order_check";
    }


    @RequestMapping("save_order.do")
    public String save_order(HttpSession session,Integer address_id){
        if(address_id == null){
            throw new RuntimeException("no address_id");
        }
        orderServiceInf.submit_order(session,address_id);

        return "redirect:/order_pay.do";
    }

    @RequestMapping("order_pay")
    public String order_pay(@ModelAttribute("order") OBJECT_T_MALL_ORDER order) {

        return "sale_order_pay";
    }
}
