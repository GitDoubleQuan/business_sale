package com.atguigu.controller;

import com.atguigu.bean.*;
import com.atguigu.service.AttrServiceInf;
import com.atguigu.service.SearchServiceInf;
import com.atguigu.service.TradeServiceInf;
import com.atguigu.util.MyCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shuangquan.Xu on 2017/11/26.
 */
@Controller
public class SearchController {

    @Autowired
    SearchServiceInf searchService;

    @Autowired
    AttrServiceInf attrService;

    @Autowired
    TradeServiceInf tradeService;

    @RequestMapping("goto_search_class")
    public String goto_search_class(int class_2_id, ModelMap map) {

//        检索属性列表
        List<OBJECT_T_MALL_ATTR> list_attr = attrService.get_attr_list_by_class_2(class_2_id);
//        检索品牌列表
        List<T_MALL_TRADE_MARK> list_trade = tradeService.get_trade_class2(class_2_id);
//        检索商品列表
//        先查缓存
        String key = "class_2_" +  class_2_id;
        List<OBJECT_T_MALL_CLASS_SKU> list_sku = MyCacheUtil.get_redis_key(key, OBJECT_T_MALL_CLASS_SKU.class);
        if(list_attr == null || list_attr.isEmpty()){
//            查数据库
            list_sku = searchService.get_sku_by_class_2(class_2_id);
//            同步缓存
            MyCacheUtil.refresh_redis_key(key, list_sku);
        }

        map.put("list_attr",list_attr);
        map.put("list_trade",list_trade);
        map.put("list_sku",list_sku);
        map.put("class_2_id",class_2_id);
        return "sale_search";
    }

    @RequestMapping("attr_search")
    public String attr_search(MODEL_SEARCH_ATTR searchParams, Map map,int[] trade_id ){
        List<OBJECT_T_MALL_CLASS_SKU> list_sku = null;
        //redis zset取交集
        String dis_key = MyCacheUtil.inter_redis_key(searchParams.getClass_2_id(), searchParams.getList_attr_value());

        list_sku = MyCacheUtil.get_redis_key(dis_key, OBJECT_T_MALL_CLASS_SKU.class);
        if(list_sku == null || list_sku.isEmpty()){

            list_sku = searchService.get_sku_by_class_2_attrs(searchParams);
            MyCacheUtil.refresh_redis_key(dis_key,list_sku);
        }
        map.put("list_sku",list_sku);
        return "sale_search_sku_list";
    }

    @RequestMapping("goto_sku_detail")
    public String goto_sku_detail(int sku_id, int spu_id, Map map){
        OBJECT_T_MALL_DETAIL_SKU obj_sku = searchService.get_sku_detail_by_sku_id(sku_id);
        List<T_MALL_SKU> list_sku = searchService.get_sku_by_spu_id(spu_id);
        map.put("obj_sku",obj_sku);
        map.put("list_sku",list_sku);
        return "sale_search_detail";
    }

}
