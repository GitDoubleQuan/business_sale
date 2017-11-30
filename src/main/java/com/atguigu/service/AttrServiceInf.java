package com.atguigu.service;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;

import java.util.List;

public interface AttrServiceInf {

	public List<OBJECT_T_MALL_ATTR> get_attr_list_by_class_2(int class_2_id);

	public void save_attr(List<OBJECT_T_MALL_ATTR> list_attr, int class_2_id);

	public List<Integer> get_value_by_attr(int i);
}
