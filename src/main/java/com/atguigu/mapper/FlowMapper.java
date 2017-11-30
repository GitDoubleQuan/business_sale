package com.atguigu.mapper;

import com.atguigu.bean.T_MALL_FLOW;

import java.util.List;

/**
 * Created by Shuangquan.Xu on 2017/11/30.
 */
public interface FlowMapper {

    void insert_flow_list(List<? extends T_MALL_FLOW> flowList);
}
