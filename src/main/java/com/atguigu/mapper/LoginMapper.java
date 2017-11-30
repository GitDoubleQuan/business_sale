package com.atguigu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.bean.T_MALL_PRODUCT;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;

public interface LoginMapper {

	T_MALL_USER_ACCOUNT select_user(T_MALL_USER_ACCOUNT user);

}
