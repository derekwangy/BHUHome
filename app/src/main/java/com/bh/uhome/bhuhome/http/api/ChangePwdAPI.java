package com.bh.uhome.bhuhome.http.api;

import com.bh.uhome.bhuhome.http.api.body.ChangePwdBody;
import com.bh.uhome.bhuhome.http.api.body.RegisterBody;
import com.bh.uhome.bhuhome.http.service.IBHService;
import com.bh.uhome.lib.base.net.Api.BaseApi;
import com.bh.uhome.lib.base.net.constant.CommURL;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * @author 凌霄
 * @date 2017/11/19.
 * @time 19:33.
 * @description Describe
 */
public class ChangePwdAPI extends BaseApi {
    public static final String METHOD = "updatePassword";
    private ChangePwdBody body;
    public ChangePwdAPI(String userName, String oldPwd, String password){
        //设置URL
        setBaseUrl(CommURL.BASE_URL);
        //是否取消加载框
        setCancel(false);
        //设置方法
        setMethod(METHOD);

        body = new ChangePwdBody();
        body.setOldpassword(oldPwd);
        body.setNewpassword(password);
        body.setUserName(userName);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        IBHService service = retrofit.create(IBHService.class);
        return service.getChangePwd(body);
    }
}
