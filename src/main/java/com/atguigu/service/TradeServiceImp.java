package com.atguigu.service;

import com.atguigu.bean.T_MALL_TRADE_MARK;
import com.atguigu.mapper.TradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Shuangquan.Xu on 2017/11/28.
 */
@Service
public class TradeServiceImp implements TradeServiceInf {

    @Autowired
    private TradeMapper tradeMapper;
    @Override
    public List<T_MALL_TRADE_MARK> get_trade_class2(int class_2_id) {
        return tradeMapper.select_trade_class_2_id(class_2_id);
    }
}
