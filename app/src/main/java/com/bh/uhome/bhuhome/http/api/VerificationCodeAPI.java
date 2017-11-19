package com.bh.uhome.bhuhome.http.api;

import com.bh.uhome.bhuhome.http.service.IBHService;
import com.bh.uhome.lib.base.net.Api.BaseApi;
import com.bh.uhome.lib.base.net.constant.CommURL;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * @author 凌霄
 * @date 2017/11/19.
 * @time 17:26.
 * @description Describe
 */
public class VerificationCodeAPI extends BaseApi {
    public static final String METHOD = "getCheckCode";
    private String userName;
    public VerificationCodeAPI(String userName){
        //设置URL
        setBaseUrl(CommURL.BASE_URL);
        //是否取消加载框
        setCancel(false);
        //设置方法
        setMethod(METHOD);

        this.userName = userName;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        IBHService service = retrofit.create(IBHService.class);
        return service.getVerificationCode(userName);
    }
}
