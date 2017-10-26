package com.bh.uhome.bhuhome.http.api;

import com.bh.uhome.bhuhome.http.service.IBHService;
import com.bh.uhome.lib.base.net.Api.BaseApi;
import com.bh.uhome.lib.base.net.constant.CommURL;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * @author 凌霄
 * @date 2017/10/26.
 * @time 10:18.
 * @description Describe
 */
public class VersionAPI extends BaseApi {
    public static final String method = "getVersion";

    public VersionAPI() {
        //设置URL
        setBaseUrl(CommURL.BASE_URL);
        //是否取消加载框
        setCancel(false);
        //设置方法
        setMethod(method);
    }
    @Override
    public Observable getObservable(Retrofit retrofit) {
        IBHService httpService = retrofit.create(IBHService.class);
        return httpService.getVersion();
    }
}
