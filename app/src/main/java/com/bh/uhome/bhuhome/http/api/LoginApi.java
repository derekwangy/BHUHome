package com.bh.uhome.bhuhome.http.api;

import android.app.Activity;


import com.bh.uhome.bhuhome.http.api.body.LoginRequestBody;
import com.bh.uhome.bhuhome.http.service.IBHService;
import com.bh.uhome.lib.base.net.Api.BaseApi;
import com.bh.uhome.lib.base.net.constant.CommURL;

import java.util.ArrayList;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * @author 凌霄
 * @date 2017/10/16.
 * @time 17:18.
 * @description Describe
 */
public class LoginApi extends BaseApi {
    public static final String METHOD = "loginReq";

    private String userName = "";
    private String pwd = "";
    private LoginRequestBody requestBody;

    public LoginApi(Activity activity, String userName, String pwd) {
        //设置URL
        setBaseUrl(CommURL.BASE_URL);
        //是否取消加载框
        setCancel(false);
        //设置方法
        setMethod(METHOD);

        this.userName = userName;
        this.pwd = pwd;

        requestBody = new LoginRequestBody();
        requestBody.setUserName(userName);
        requestBody.setPassword(pwd);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        IBHService httpService = retrofit.create(IBHService.class);
        return httpService.getLogin(requestBody);
    }


}
