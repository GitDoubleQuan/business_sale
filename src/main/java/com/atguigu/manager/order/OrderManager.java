package com.atguigu.manager.order;

import com.atguigu.bean.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Shuangquan.Xu on 2017/11/30.
 */
public class OrderManager {

    // 拆单业务
    public static OBJECT_T_MALL_ORDER check_order(T_MALL_USER_ACCOUNT user,BigDecimal sum,List<T_MALL_SHOPPINGCAR> list_cart){

        OBJECT_T_MALL_ORDER order = new OBJECT_T_MALL_ORDER();
        // 写入初始化dd信息
        order.setJdh(1);
        order.setYh_id(user.getId());
        order.setZje(sum);

        // 获得库存地址，对购物车库存地址进行去重
        Set<String> list_kcdz = new HashSet<String>();
        for (int i = 0; i < list_cart.size(); i++) {
            list_kcdz.add(list_cart.get(i).getKcdz());
        }

        // 根据库存生成物流包裹
        List<OBJECT_T_MALL_FLOW> list_flow = new ArrayList<OBJECT_T_MALL_FLOW>();
        Iterator<String> iterator = list_kcdz.iterator();
        while (iterator.hasNext()) {
            String kcdz = iterator.next();
            OBJECT_T_MALL_FLOW flow = new OBJECT_T_MALL_FLOW();
            // 写入初始化物流信息
            flow.setPsfsh("硅谷快递");
            flow.setYh_id(user.getId());
            flow.setMqdd("商品未出库");

            // 将商品放入包裹
            List<T_MALL_ORDER_INFO> list_info = new ArrayList<T_MALL_ORDER_INFO>();

            for (int i = 0; i < list_cart.size(); i++) {
                if (list_cart.get(i).getShfxz().equals("1") && list_cart.get(i).getKcdz().equals(kcdz)) {
                    T_MALL_ORDER_INFO info = new T_MALL_ORDER_INFO();
                    info.setGwch_id(list_cart.get(i).getId());
                    info.setShp_tp(list_cart.get(i).getShp_tp());
                    info.setSku_id(list_cart.get(i).getSku_id());
                    info.setSku_jg(list_cart.get(i).getSku_jg());
                    info.setSku_kcdz(list_cart.get(i).getKcdz());
                    info.setSku_mch(list_cart.get(i).getSku_mch());
                    info.setSku_shl(list_cart.get(i).getTjshl());

                    list_info.add(info);
                }
            }

            flow.setList_info(list_info);
            list_flow.add(flow);
        }

        order.setList_flow(list_flow);
        return order;
    }
}
