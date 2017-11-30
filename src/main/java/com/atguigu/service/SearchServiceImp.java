package com.atguigu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atguigu.bean.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.mapper.SearchMapper;

@Service
public class SearchServiceImp implements SearchServiceInf {

	@Autowired
	SearchMapper searchMapper;

	@Override
	public List<OBJECT_T_MALL_CLASS_SKU> get_sku_by_class_2(int class_2_id) {
		List<OBJECT_T_MALL_CLASS_SKU> list_sku = searchMapper.select_sku_by_class_2(class_2_id);
		return list_sku;
	}

	@Override
	public List<OBJECT_T_MALL_CLASS_SKU> get_sku_by_class_2_attrs(MODEL_SEARCH_ATTR searchParams) {

		Map<String, Object> map = new HashMap<>();
		map.put("class_2_id",searchParams.getClass_2_id());

		if(StringUtils.isNotBlank(searchParams.getOrder())){
			map.put("order",searchParams.getOrder());
		}
		List<T_MALL_SKU_ATTR_VALUE> listAttrVal = searchParams.getList_attr_value();
		if( listAttrVal!= null && !listAttrVal.isEmpty()){
			StringBuilder before = new StringBuilder("AND sku.id IN ( SELECT sku0.sku_id FROM ");
			for(int i = 0; i < listAttrVal.size(); i++){
				before.append(String.format("(SELECT `sku_id` FROM `t_mall_sku_attr_value` " +
						"WHERE `shxm_id` = %d AND `shxzh_id` = %d) sku%d,",
						listAttrVal.get(i).getShxm_id(),listAttrVal.get(i).getShxzh_id(),i));
			}
			String beforeStr = before.substring(0, before.length() - 1);
			StringBuilder where = new StringBuilder(" WHERE");
			String whereStr = "";
			if(listAttrVal.size() > 1){
				for(int i = 0; i < listAttrVal.size() - 1; i++){
					where.append(String.format(" sku%d.sku_id = sku%d.sku_id AND",i,i+1));
				}
				whereStr = where.substring(0, where.length() - 4);
			}

			StringBuilder sql = new StringBuilder(beforeStr).append(whereStr).append(" )");
			map.put("sql",sql.toString());
		}

		return searchMapper.select_sku_by_class_2_attrs(map);
	}

	@Override
	public OBJECT_T_MALL_DETAIL_SKU get_sku_detail_by_sku_id(int sku_id) {
		return searchMapper.select_sku_detail_by_sku_id(sku_id);
	}

	@Override
	public List<T_MALL_SKU> get_sku_by_spu_id(int spu_id) {
		return searchMapper.select_sku_by_spu_id(spu_id);
	}

}
