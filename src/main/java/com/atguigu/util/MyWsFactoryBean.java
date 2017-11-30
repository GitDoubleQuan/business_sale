package com.atguigu.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.FactoryBean;

public class MyWsFactoryBean<T> implements FactoryBean<T> {

	private Class<T> t;
	private String url;

	public Class<T> getT() {
		return t;
	}

	public void setT(Class<T> t) {
		this.t = t;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static <T> T getMyWs(String url, Class<T> t) {

		JaxWsProxyFactoryBean jax = new JaxWsProxyFactoryBean();

		jax.setAddress(url);

		jax.setServiceClass(t);

		// 加入客户端安全拦截代理
		if (1 == 2) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
			map.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordText");
			map.put(WSHandlerConstants.PW_CALLBACK_CLASS, MyPasswordCallBack.class.getName());
			map.put(WSHandlerConstants.USER, "username");
			WSS4JOutInterceptor wss4jOutInterceptor = new WSS4JOutInterceptor(map);

			jax.getOutInterceptors().add(wss4jOutInterceptor);
		}

		T create = (T) jax.create();

		return create;
	}

	@Override
	public T getObject() throws Exception {
		// TODO Auto-generated method stub
		return getMyWs(this.url, t);
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return t;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

}
