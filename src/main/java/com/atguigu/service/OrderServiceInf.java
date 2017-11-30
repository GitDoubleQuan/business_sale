package com.atguigu.service;

import com.atguigu.bean.T_MALL_ORDER;

import javax.servlet.http.HttpSession;

/**
 * Created by Shuangquan.Xu on 2017/11/30.
 */
public interface OrderServiceInf {

    void add_order(T_MALL_ORDER order);

    void submit_order(HttpSession session, Integer address_id);
}
