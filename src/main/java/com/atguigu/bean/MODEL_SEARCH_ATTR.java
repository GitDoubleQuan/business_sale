package com.atguigu.bean;

import java.util.List;

/**
 * Created by Shuangquan.Xu on 2017/11/26.
 */
public class MODEL_SEARCH_ATTR {
    private int class_2_id;
    private String order;
    List<T_MALL_SKU_ATTR_VALUE> list_attr_value;

    public int getClass_2_id() {
        return class_2_id;
    }

    public void setClass_2_id(int class_2_id) {
        this.class_2_id = class_2_id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<T_MALL_SKU_ATTR_VALUE> getList_attr_value() {
        return list_attr_value;
    }

    public void setList_attr_value(List<T_MALL_SKU_ATTR_VALUE> list_attr_value) {
        this.list_attr_value = list_attr_value;
    }
}
