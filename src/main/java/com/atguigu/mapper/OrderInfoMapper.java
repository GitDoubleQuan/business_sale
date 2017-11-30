package com.atguigu.mapper;

import com.atguigu.bean.T_MALL_ORDER_INFO;

import java.util.List;

/**
 * Created by Shuangquan.Xu on 2017/11/30.
 */
public interface OrderInfoMapper {

    void insert_order_info_list(List<T_MALL_ORDER_INFO> order_Info_list);
}
