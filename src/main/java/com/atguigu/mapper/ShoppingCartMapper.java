package com.atguigu.mapper;

import java.util.List;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;

public interface ShoppingCartMapper {

	List<T_MALL_SHOPPINGCAR> select_list_cart_by_user_id(T_MALL_USER_ACCOUNT user);

	void insert_cart(T_MALL_SHOPPINGCAR cart);

	void update_cart(T_MALL_SHOPPINGCAR cart);

	void delete_cart(List<Integer> cart_list);

}
