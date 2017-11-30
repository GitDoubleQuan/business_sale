package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;

public interface ShoppingCartServiceInf {

	public List<T_MALL_SHOPPINGCAR> get_list_cart_by_user(T_MALL_USER_ACCOUNT user);

	public void add_cart(T_MALL_SHOPPINGCAR cart);

	public void update_cart(T_MALL_SHOPPINGCAR cart);

	void delete_cart(List<Integer> cart_id_list);

}
