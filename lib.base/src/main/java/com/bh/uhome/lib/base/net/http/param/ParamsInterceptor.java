package com.bh.uhome.lib.base.net.http.param;


import android.util.Base64;
import android.util.Log;

import com.bh.uhome.lib.base.log.LogUtil;
import com.bh.uhome.lib.base.util.DateUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class ParamsInterceptor implements Interceptor {
    private String token = "";
    private String deviceID = "";
    private String isEncrypt = "0";
    private String userAgent = "";
    private String contentType = "application/json";
    private String isBH = "1";

    @Override
    public Response intercept(Chain chain) throws IOException {
        long timeStamp = System.currentTimeMillis();

        Request request = chain.request();
        String method = request.method();

        Request newRequest = request.newBuilder()
                .method(request.method(), request.body())
                .url(request.url())
                .headers(request.headers())
                .addHeader("token", token)
                .addHeader("deviceID", deviceID)
                .addHeader("isEncrypt", isEncrypt)
                .addHeader("User-Agent", userAgent)
                .addHeader("Content-Type", contentType)
                .addHeader("isBH", isBH)

                .build();

        return chain.proceed(newRequest);
    }


}
