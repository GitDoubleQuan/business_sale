package com.atguigu.mapper;

import com.atguigu.bean.T_MALL_TRADE_MARK;

import java.util.List;

/**
 * Created by Shuangquan.Xu on 2017/11/28.
 */
public interface TradeMapper {

    List<T_MALL_TRADE_MARK> select_trade_class_2_id(int class_2_id);
}
