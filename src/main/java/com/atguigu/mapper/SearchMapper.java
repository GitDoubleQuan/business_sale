package com.atguigu.mapper;

import com.atguigu.bean.OBJECT_T_MALL_CLASS_SKU;
import com.atguigu.bean.OBJECT_T_MALL_DETAIL_SKU;
import com.atguigu.bean.T_MALL_SKU;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shuangquan.Xu on 2017/11/26.
 */
public interface SearchMapper {

    List<OBJECT_T_MALL_CLASS_SKU> select_sku_by_class_2(int class_2_id);

    List<OBJECT_T_MALL_CLASS_SKU> select_sku_by_class_2_attrs(Map<String, Object> params);

    OBJECT_T_MALL_DETAIL_SKU select_sku_detail_by_sku_id(int sku_id);

    List<T_MALL_SKU> select_sku_by_spu_id(int spu_id);
}
