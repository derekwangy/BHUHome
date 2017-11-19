package com.bh.uhome.bhuhome.http.api;

import android.app.Activity;

import com.bh.uhome.bhuhome.http.api.body.LoginCodeRequestBody;
import com.bh.uhome.bhuhome.http.api.body.LoginRequestBody;
import com.bh.uhome.bhuhome.http.service.IBHService;
import com.bh.uhome.lib.base.net.Api.BaseApi;
import com.bh.uhome.lib.base.net.constant.CommURL;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * @author 凌霄
 * @date 2017/10/16.
 * @time 17:18.
 * @description Describe
 */
public class LoginCodeApi extends BaseApi {
    public static final String METHOD = "loginCodeReq";

    private String userName = "";
    private String code = "";
    private LoginCodeRequestBody requestBody;

    public LoginCodeApi(String userName, String code) {
        //设置URL
        setBaseUrl(CommURL.BASE_URL);
        //是否取消加载框
        setCancel(false);
        //设置方法
        setMethod(METHOD);

        this.userName = userName;
        this.code = code;

        requestBody = new LoginCodeRequestBody();
        requestBody.setUserName(userName);
        requestBody.setCheckCode(code);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        IBHService httpService = retrofit.create(IBHService.class);
        return httpService.getCodeLogin(requestBody);
    }


}
