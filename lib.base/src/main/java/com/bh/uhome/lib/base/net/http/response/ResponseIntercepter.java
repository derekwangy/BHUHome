package com.bh.uhome.lib.base.net.http.response;

import android.text.TextUtils;

import com.bh.uhome.lib.base.app.RxRetrofitApp;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 获取返回头部信息
 * @author 凌霄
 * @date 2017/10/17.
 * @time 15:08.
 * @description Describe
 */
public class ResponseIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        Headers headers = originalResponse.headers();
        String token = headers.get("token");
        if (!TextUtils.isEmpty(token) && !token.equals("0")){
            /**
             * 1，登录时token保存
             * 2，如果服务端有新的token替换，直接更新
             */
            RxRetrofitApp.setToken(token);
        }
        return originalResponse;
    }
}
