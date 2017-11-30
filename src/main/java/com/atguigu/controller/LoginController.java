package com.atguigu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.service.ShoppingCartServiceInf;
import com.atguigu.util.MyJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.mapper.LoginMapper;

@Controller
public class LoginController {

	@Autowired
	LoginMapper loginMapper;
	@Autowired
	private ShoppingCartServiceInf shoppingCartService;

	@RequestMapping("goto_out")
	public String goto_out(HttpSession session, ModelMap map) {
		// 注销session
		session.invalidate();
		return "redirect:/goto_login.do";
	}

	@RequestMapping("goto_login")
	public String goto_login() {
		return "sale_login";
	}

	@RequestMapping("login")
	public String login(String dataSource_type,
			@CookieValue(value = "list_cart_cookie", required = false) String cookie_cart,
			HttpServletResponse response, HttpSession session, ModelMap map, T_MALL_USER_ACCOUNT user) {
		T_MALL_USER_ACCOUNT user_login = null;
		if (dataSource_type.equals("1")) {

			user_login = loginMapper.select_user(user);
		}

		if (user_login == null) {
			// 提示错误
			map.put("err", "用户名或者密码错误");
			return "sale_login";
		} else {
			session.setAttribute("user", user_login);

			// 向客户端的cookie中放入用户数据
			Cookie cookie = null;
			try {
				cookie = new Cookie("yh_mch", URLEncoder.encode(user_login.getYh_nch(), "utf-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			// 设置过期时间
			cookie.setMaxAge(60 * 60 * 24);
			// 设置cookie存放目录
			cookie.setPath("/");
			response.addCookie(cookie);

		}

		//合并购物车数据
		merge_cart(user_login,cookie_cart,session,response);

		return "redirect:/index.do";
	}
	//合并购物车
	private void merge_cart(T_MALL_USER_ACCOUNT user_login,String cookie_cart,HttpSession session,HttpServletResponse response){

		List<T_MALL_SHOPPINGCAR> list_cart_cookie = MyJsonUtil.json_to_list(cookie_cart, T_MALL_SHOPPINGCAR.class);
		//将db中的购物车数据加载到缓存
		List<T_MALL_SHOPPINGCAR> list_cart_db = shoppingCartService.get_list_cart_by_user(user_login);
		if(list_cart_db == null || list_cart_db.isEmpty()){
			list_cart_db = new ArrayList<>();
		}
		session.setAttribute("list_cart_session",list_cart_db);
		//如果没cookie没有数据直接返回
		if(list_cart_cookie == null || list_cart_cookie.isEmpty()){
			return;
		}
		//db没有购物车数据,直接把cookie中的数据插入db和缓存
		if(list_cart_db == null || list_cart_db.isEmpty()){
			for(T_MALL_SHOPPINGCAR cart_cookie : list_cart_cookie){
				cart_cookie.setYh_id(user_login.getId());
				list_cart_db.add(cart_cookie);
				shoppingCartService.add_cart(cart_cookie);
				Cookie cookie_cart_remove = new Cookie("list_cart_cookie", "");
				cookie_cart_remove.setMaxAge(0);
				response.addCookie(cookie_cart_remove);
				return;
			}
		}

		//db中有购物车数据，cookie中的数据合并的缓存和db
            for(T_MALL_SHOPPINGCAR cart_cookie : list_cart_cookie){
                cart_cookie.setYh_id(user_login.getId());
                boolean isRep = false;
                for(int i = 0; i < list_cart_db.size(); i++){
                    if(cart_cookie.getSku_id() == list_cart_db.get(i).getSku_id()){//跟db中的重复了，cookie中的数据覆盖db
                        isRep = true;
                        list_cart_db.remove(i);
                        list_cart_db.add(cart_cookie);
                        shoppingCartService.update_cart(cart_cookie);
                        break;
                    }
                }
			if(!isRep){
				//没重复
				list_cart_db.add(cart_cookie);
				shoppingCartService.add_cart(cart_cookie);
			}
		}
		Cookie cookie_cart_remove = new Cookie("list_cart_cookie", "");
		cookie_cart_remove.setMaxAge(0);
		response.addCookie(cookie_cart_remove);
	}

}
