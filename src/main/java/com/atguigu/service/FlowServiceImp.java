package com.atguigu.service;

import com.atguigu.bean.T_MALL_FLOW;
import com.atguigu.mapper.FlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Shuangquan.Xu on 2017/11/30.
 */
@Service
public class FlowServiceImp implements FlowServiceInf {

    @Autowired
    private FlowMapper flowMapper;
    @Override
    public void add_flow_list(List<? extends T_MALL_FLOW> flowList) {
        flowMapper.insert_flow_list(flowList);
    }
}
