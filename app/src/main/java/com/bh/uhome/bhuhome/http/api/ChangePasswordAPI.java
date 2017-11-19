package com.bh.uhome.bhuhome.http.api;

import com.bh.uhome.lib.base.net.Api.BaseApi;
import com.bh.uhome.lib.base.net.constant.CommURL;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * @author 凌霄
 * @date 2017/11/19.
 * @time 18:36.
 * @description Describe
 */
public class ChangePasswordAPI extends BaseApi {
    public static final String METHOD = "updatePassword";
    private String userName,oldpassword,newpassword;
    public ChangePasswordAPI(String userName,String oldpassword,String newpassword){
        //设置URL
        setBaseUrl(CommURL.BASE_URL);
        //是否取消加载框
        setCancel(false);
        //设置方法
        setMethod(METHOD);

        this.userName = userName;
        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
    }
    @Override
    public Observable getObservable(Retrofit retrofit) {
        return null;
    }
}
