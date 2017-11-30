package com.atguigu.mapper;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.bean.T_MALL_VALUE;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttrMapper {

	List<OBJECT_T_MALL_ATTR> select_attr_list_by_class_2(int class_2_id);

	void insert_attr(@Param("class_2_id") int class_2_id, @Param("attr") OBJECT_T_MALL_ATTR attr);

	void insert_values(@Param("attr_id") int attr_id, @Param("list_value") List<T_MALL_VALUE> list_value);

	List<Integer> select_value_by_attr(int attr_id);

}
