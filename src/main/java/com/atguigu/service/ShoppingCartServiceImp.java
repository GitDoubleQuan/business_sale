package com.atguigu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.mapper.ShoppingCartMapper;

@Service
public class ShoppingCartServiceImp implements ShoppingCartServiceInf {
	@Autowired
	ShoppingCartMapper shoppingCartMapper;

	public List<T_MALL_SHOPPINGCAR> get_list_cart_by_user(T_MALL_USER_ACCOUNT user) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();

		list_cart = shoppingCartMapper.select_list_cart_by_user_id(user);
		return list_cart;
	}

	public void add_cart(T_MALL_SHOPPINGCAR cart) {
		shoppingCartMapper.insert_cart(cart);

	}

	public void update_cart(T_MALL_SHOPPINGCAR cart) {
		shoppingCartMapper.update_cart(cart);

	}

	@Override
	public void delete_cart(List<Integer> cart_id_list) {
		shoppingCartMapper.delete_cart(cart_id_list);
	}

}
