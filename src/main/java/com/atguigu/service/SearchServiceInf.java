package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.MODEL_SEARCH_ATTR;
import com.atguigu.bean.OBJECT_T_MALL_CLASS_SKU;
import com.atguigu.bean.OBJECT_T_MALL_DETAIL_SKU;
import com.atguigu.bean.T_MALL_SKU;

public interface SearchServiceInf {

	List<OBJECT_T_MALL_CLASS_SKU> get_sku_by_class_2(int class_2_id);

	List<OBJECT_T_MALL_CLASS_SKU> get_sku_by_class_2_attrs(MODEL_SEARCH_ATTR searchParams);

	OBJECT_T_MALL_DETAIL_SKU get_sku_detail_by_sku_id(int sku_id);

	List<T_MALL_SKU> get_sku_by_spu_id(int spu_id);

}
