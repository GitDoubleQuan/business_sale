package com.atguigu.service;

import com.atguigu.bean.*;
import com.atguigu.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shuangquan.Xu on 2017/11/30.
 */
@Service
public class OrderServiceImp implements OrderServiceInf {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    AddressServerInf addressServerInf;

    @Autowired
    private FlowServiceInf flowServiceInf;
    @Autowired
    private OrderInfoServiceInf orderInfoServiceInf;
    @Autowired
    private ShoppingCartServiceInf shoppingCartServiceInf;


    @Override
    public void add_order(T_MALL_ORDER order) {

        orderMapper.insert_order(order);
    }

    @Transactional
    @Override
    public void submit_order(HttpSession session, Integer address_id){
        T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
        T_MALL_ADDRESS address = addressServerInf.get_addresses_by_id(address_id);

        OBJECT_T_MALL_ORDER order = (OBJECT_T_MALL_ORDER)session.getAttribute("order");
        order.setShhr(address.getShjr());
        order.setChjshj(new Date());
        order.setDzh_id(address.getId());
        order.setDzh_mch(address.getYh_dz());
        orderMapper.insert_order(order);

        List<OBJECT_T_MALL_FLOW> list_flow = order.getList_flow();
        for (OBJECT_T_MALL_FLOW flow : list_flow){
            flow.setChjshj(new Date());
            flow.setDd_id(order.getId());
        }
        flowServiceInf.add_flow_list(list_flow);

        ArrayList<Integer> cart_id_list = new ArrayList<>();
        for (OBJECT_T_MALL_FLOW flow : list_flow){
            List<T_MALL_ORDER_INFO> list_info = flow.getList_info();
            for (T_MALL_ORDER_INFO info : list_info){
                cart_id_list.add(info.getGwch_id());
                info.setFlow_id(flow.getId());
                info.setDd_id(order.getId());
                info.setChjshj(new Date());
            }
            orderInfoServiceInf.addOrderInfoList(list_info);
        }

        //清除购物车
        shoppingCartServiceInf.delete_cart(cart_id_list);
        List<T_MALL_SHOPPINGCAR> new_list_cart = shoppingCartServiceInf.get_list_cart_by_user(user);
        session.setAttribute("list_cart_session",new_list_cart);
    }
}
