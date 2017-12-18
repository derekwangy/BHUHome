package com.bh.uhome.bhuhome.http.api;

import com.bh.uhome.bhuhome.http.api.body.ControlDeviceBody;
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
public class ControlDeviceAPI extends BaseApi {
    public static final String METHOD = "controlDevice";
    private ControlDeviceBody body;
    public ControlDeviceAPI(String userName, String homeName, String deviceID,String state){
        //设置URL
        setBaseUrl(CommURL.BASE_URL);
        //是否取消加载框
        setCancel(false);
        //设置方法
        setMethod(METHOD);

        body = new ControlDeviceBody();
        body.setUserName(userName);
        body.setDeviceID(deviceID);
        body.setHomeName(homeName);
        body.setState(state);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        IBHService service = retrofit.create(IBHService.class);
        return service.requestControlDevice(body);
    }
}
