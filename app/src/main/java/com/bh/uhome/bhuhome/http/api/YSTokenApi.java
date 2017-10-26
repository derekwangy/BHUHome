package com.bh.uhome.bhuhome.http.api;

import com.bh.uhome.bhuhome.constant.APPConstant;
import com.bh.uhome.bhuhome.http.service.IBHService;
import com.bh.uhome.lib.base.net.Api.BaseApi;
import com.bh.uhome.lib.base.net.constant.CommURL;
import com.bh.uhome.lib.base.net.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * @author 凌霄
 * @date 2017/9/29.
 * @time 14:01.
 * @description Describe
 */
public class YSTokenApi extends BaseApi {
    public static final String method = "api/lapp/token/get";

    public YSTokenApi() {
        //设置URL
        setBaseUrl(CommURL.ysUrl);
        //是否取消加载框
        setCancel(false);
        //设置方法
        setMethod(method);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        IBHService httpService = retrofit.create(IBHService.class);
        return httpService.getYSToken(APPConstant.YS_APP_KEY,APPConstant.YS_APP_SECRET);
    }

}
