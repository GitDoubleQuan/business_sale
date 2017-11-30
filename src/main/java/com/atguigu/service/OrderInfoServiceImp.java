package com.atguigu.service;

import com.atguigu.bean.T_MALL_ORDER_INFO;
import com.atguigu.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Shuangquan.Xu on 2017/11/30.
 */
@Service
public class OrderInfoServiceImp implements OrderInfoServiceInf {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public void addOrderInfoList(List<T_MALL_ORDER_INFO> order_info_list) {
        orderInfoMapper.insert_order_info_list(order_info_list);
    }
}
