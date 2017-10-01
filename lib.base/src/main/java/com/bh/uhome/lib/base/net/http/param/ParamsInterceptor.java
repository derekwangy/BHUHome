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
    /**
     * 请求BodyContentType
     */
    public static final String CONTENT_TYPE = "application/json";
    public static final String HEAD_CONTENT_TYPE = "Content-Type";
    //通过Context获得
    public static final String HEAD_DEVICE = "x-weimai-device";
    //String.format("Android %s", android.os.Build.VERSION.RELEASE);
    public static final String HEAD_OS_INFOR = "x-weimai-prgname";
    //动态获取
    public static final String HEAD_EVENT_ID = "x-weimai-eventid";
    //头部信息key
    public static final String HEAD_APP_ID = "x-weimai-appid";
    public static final String HEAD_APP_TYPE = "x-weimai-yylx";
    public static final String APP_ID = "Ew1GMuHDqtAmQoDwfFkbHw==";
    public static final String APP_TYPE = "putongkh_android";

    //"true" or "false" 接口调用时指定
    public static final String HEAD_ENCRYPT = "x-weimai-encrypt";
    //动态获取
    public static final String HEAD_EMECHANISM_CODE = "x-weimai-jgbh";
    //动态获取
    public static final String HEAD_PERSON_CODE = "x-weimai-grbh";
    //动态获取
    public static final String HEAD_LOG_ID = "x-weimai-logid";
    //date为接口调用时传入
    public static final String HEAD_DATE = "Date";

    /**
     * 头包加密 KEY_IV.
     * 专业名词 : initialization vector.
     * 加密的初始化矢量.既,初始化加密函数的变量.
     */
    protected static final String KEY_IV = "626306RQ6Lk=";
    /**
     * 头包加密密钥 KEY
     */
    protected static final String KEY = "Kgat/+DxgLmdij0oaKIh9lKzBA1vKKl6";

    //##################value值##########################

    private String headDeviceValue = "%23990009263788633%23T0QxMDMgU01BUlRJU0FO%0A";
    private String headEncryptValue = "false";
    private String headPersonCodeValue = "0";
    private String headEmechanismCode = "0";
    private String headLogId = "login";
    private String headOSInfo = "#Android 7.1.1#4.5.3";
    private String date = "";

    @Override
    public Response intercept(Chain chain) throws IOException {
        long timeStamp = System.currentTimeMillis();
        String timestamp = String.valueOf(timeStamp);

        Request request = chain.request();
        String method = request.method();
        String url = request.url().toString();

        Map<String, String> headMap = new HashMap<>();
        try {
            date = new String(DateUtil.formatUnixTime(DateUtil.getGMTUnixTimeByCalendar(),
                    DateUtil.FORMAT_MODE_HTTP_GMT).getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("loginWemay", e.getMessage());
        }

        //添加到head，并参与加密 L9
        headMap.put(HEAD_APP_ID, APP_ID);
        //添加到head，并参与加密 L8
        headMap.put(HEAD_DEVICE, headDeviceValue);
        //添加到head，并参与加密 L7
        headMap.put(HEAD_ENCRYPT, headEncryptValue);
        //添加到head，并参与加密 L6
        headMap.put(HEAD_PERSON_CODE, headPersonCodeValue);
        //添加到head，并参与加密 L5
        headMap.put(HEAD_EMECHANISM_CODE, headEmechanismCode);
        //添加到head，并参与加密 L4
        headMap.put(HEAD_OS_INFOR, headOSInfo);
        //添加到head，并参与加密 L3
        headMap.put(HEAD_APP_TYPE, APP_TYPE);
        //添加到head，并参与加密 L2
        headMap.put(HEAD_LOG_ID, headLogId);
        //添加到head，并参与加密 L1
        headMap.put(HEAD_DATE, date);

        Headers headers = request.headers();
        String encryptHead = "";
        try {
            url = "ptgy/weimaihao/dlsms?shoujihm=13671508758";
            encryptHead = getAuthorizationValue(getContent(headMap, method, url));
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("CookiesInterceptor", e.getMessage());
        }

        Request newRequest = request.newBuilder()
                .method(request.method(), request.body())
                .url(request.url())
                .headers(request.headers())
                .addHeader(HEAD_EVENT_ID, UUID.randomUUID().toString())
                .addHeader("Authorization", encryptHead)
                .addHeader(HEAD_APP_ID, APP_ID)
                .addHeader(HEAD_DEVICE, headDeviceValue)
                .addHeader(HEAD_ENCRYPT, headEncryptValue)
                .addHeader(HEAD_PERSON_CODE, headPersonCodeValue)
                .addHeader(HEAD_EMECHANISM_CODE, headEmechanismCode)
                .addHeader(HEAD_OS_INFOR, headOSInfo)
                .addHeader(HEAD_APP_TYPE, APP_TYPE)
                .addHeader(HEAD_LOG_ID, headLogId)
                .addHeader(HEAD_DATE, date)
                .build();

        return chain.proceed(newRequest);
    }


    /**
     * 获取头包验证码.
     *
     * @param encodeData 待加密内容
     * @return 加密后的验证码
     * @throws Exception
     */
    public static String getAuthorizationValue(String encodeData) throws Exception {
        Log.d("authValue base",encodeData);
        String authCode = "WEIMAI" + " " + KEY_IV + ":"
                + Base64.encodeToString
                (
                        encode
                                (
                                        encodeData.getBytes("utf-8"),
                                        KEY.getBytes("utf-8")
                                ), 0);
        // 去除Base64加密后会出现的换行符.
        authCode = authCode.replace("\n", "");
        Log.d("authValue", authCode);
        return authCode;
    }

    public static byte[] encode(byte[] content, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(content);
    }

    public static String getContent(Headers headers, String method, String url) {
        StringBuilder builder = new StringBuilder();
        String contentMD5 = "";
        String contentType = "";
        builder.append(method).append("\n");
        builder.append(contentMD5).append("\n");
        builder.append(contentType).append("\n");
        builder.append(headers.get(HEAD_DATE)).append("\n");
        builder.append(HEAD_APP_ID + ":").append(headers.get(HEAD_APP_ID)).append("\n");
        builder.append(HEAD_DEVICE + ":").append(headers.get(HEAD_DEVICE)).append("\n");
        builder.append(HEAD_ENCRYPT + ":").append(headers.get(HEAD_ENCRYPT)).append("\n");
        builder.append(HEAD_EMECHANISM_CODE + ":").append(headers.get(HEAD_EMECHANISM_CODE)).append("\n");
        builder.append(HEAD_PERSON_CODE + ":").append(headers.get(HEAD_PERSON_CODE)).append("\n");
        builder.append(HEAD_LOG_ID + ":").append(headers.get(HEAD_LOG_ID)).append("\n");
        builder.append(HEAD_OS_INFOR + ":").append("#" + headers.get(HEAD_OS_INFOR)).append("#4.4.4").append("\n");
        builder.append(HEAD_APP_TYPE + ":").append(headers.get(HEAD_APP_TYPE)).append("\n");
        if (url.indexOf("?") > 0) {
            builder.append(url.substring(url.indexOf("api/") + 4, url.indexOf("?")));
            builder.append(url.substring(url.indexOf("?") + 1, url.length()));
        } else {
            builder.append(url.substring(url.indexOf("api/") + 4, url.length()));
        }
        String content = builder.toString();
        Log.i("headContent", content);
        return content;
    }

    public static String getContent(Map<String, String> headers, String method, String url) {
        StringBuilder builder = new StringBuilder();
        String contentMD5 = "";
        String contentType = "";
        builder.append(method).append("\n");
        builder.append(contentMD5).append("\n");
        builder.append(contentType).append("\n");
        builder.append(headers.get(HEAD_DATE)).append("\n");
        builder.append(HEAD_APP_ID + ":").append(headers.get(HEAD_APP_ID)).append("\n");
        builder.append(HEAD_DEVICE + ":").append(headers.get(HEAD_DEVICE)).append("\n");
        builder.append(HEAD_ENCRYPT + ":").append(headers.get(HEAD_ENCRYPT)).append("\n");
        builder.append(HEAD_EMECHANISM_CODE + ":").append(headers.get(HEAD_EMECHANISM_CODE)).append("\n");
        builder.append(HEAD_PERSON_CODE + ":").append(headers.get(HEAD_PERSON_CODE)).append("\n");
        builder.append(HEAD_LOG_ID + ":").append(headers.get(HEAD_LOG_ID)).append("\n");
        builder.append(HEAD_OS_INFOR + ":").append(headers.get(HEAD_OS_INFOR)).append("\n");
        builder.append(HEAD_APP_TYPE + ":").append(headers.get(HEAD_APP_TYPE)).append("\n");
        builder.append(url);
        String content = builder.toString();
        Log.i("headContent", content);
        return content;
    }

}
