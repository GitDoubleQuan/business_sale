package com.atguigu.route;

/**
 * Created by Shuangquan.Xu on 2017/12/4.
 */
public class DataSourceToken {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getAndRemoveToken(){
        String token = threadLocal.get();
        threadLocal.remove();
        return token;
    }

    public static void setToken(String token){
        threadLocal.set(token);
    }
}
