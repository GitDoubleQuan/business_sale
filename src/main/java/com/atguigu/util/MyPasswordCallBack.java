package com.atguigu.util;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class MyPasswordCallBack implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

		WSPasswordCallback pwdcb = (WSPasswordCallback) callbacks[0];

		// 对usernametoken进行处理
		pwdcb.setIdentifier("a");
		pwdcb.setPassword(MD5Util.md5("1" + MyDataUtil.getMyDateString()));

	}

}
