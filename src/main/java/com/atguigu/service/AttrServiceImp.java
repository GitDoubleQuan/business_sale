package com.atguigu.service;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.mapper.AttrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttrServiceImp implements AttrServiceInf {

	@Autowired
	AttrMapper attrMapper;

	public List<OBJECT_T_MALL_ATTR> get_attr_list_by_class_2(int class_2_id) {
		List<OBJECT_T_MALL_ATTR> list_attr = attrMapper.select_attr_list_by_class_2(class_2_id);
		return list_attr;
	}

	public void save_attr(List<OBJECT_T_MALL_ATTR> list_attr, int class_2_id) {
		for (int i = 0; i < list_attr.size(); i++) {
			attrMapper.insert_attr(class_2_id, list_attr.get(i));

			attrMapper.insert_values(list_attr.get(i).getId(), list_attr.get(i).getList_value());
		}
	}

	@Override
	public List<Integer> get_value_by_attr(int i) {
		List<Integer> select_value_by_attr = attrMapper.select_value_by_attr(i);
		return select_value_by_attr;
	}

}
