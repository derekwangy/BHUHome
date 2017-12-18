package com.bh.uhome.bhuhome.http.service;

import com.bh.uhome.bhuhome.activity.loginmoudle.login.RegisterActivity;
import com.bh.uhome.bhuhome.http.api.CameraDeviceInfAPI;
import com.bh.uhome.bhuhome.http.api.ChangePwdAPI;
import com.bh.uhome.bhuhome.http.api.ControlDeviceAPI;
import com.bh.uhome.bhuhome.http.api.DeviceInfAPI;
import com.bh.uhome.bhuhome.http.api.FindPwdAPI;
import com.bh.uhome.bhuhome.http.api.HeartBeatAPI;
import com.bh.uhome.bhuhome.http.api.LoginApi;
import com.bh.uhome.bhuhome.http.api.LoginCodeApi;
import com.bh.uhome.bhuhome.http.api.LoginOutAPI;
import com.bh.uhome.bhuhome.http.api.RegisterAPI;
import com.bh.uhome.bhuhome.http.api.VerificationCodeAPI;
import com.bh.uhome.bhuhome.http.api.VersionAPI;
import com.bh.uhome.bhuhome.http.api.YSTokenApi;
import com.bh.uhome.bhuhome.http.api.body.ChangePwdBody;
import com.bh.uhome.bhuhome.http.api.body.ControlDeviceBody;
import com.bh.uhome.bhuhome.http.api.body.LoginCodeRequestBody;
import com.bh.uhome.bhuhome.http.api.body.LoginOutBody;
import com.bh.uhome.bhuhome.http.api.body.LoginRequestBody;
import com.bh.uhome.bhuhome.http.api.body.RegisterBody;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author 凌霄
 * @date 2017/9/29.
 * @time 14:06.
 * @description Describe
 */
public interface IBHService {

    //获取萤石token
    @FormUrlEncoded
    @POST(YSTokenApi.method)
    Observable<String> getYSToken(@Field("appKey") String appKey, @Field("appSecret")String appSecret);

    //版本信息
    @GET(VersionAPI.method)
    Observable<String> getVersion();

    //搜索前端可管理设备-第二排列表展示
    @GET(DeviceInfAPI.method)
    Observable<String> getDeviceInfo(@Query("username") String username);

    //搜索前端可管理监控设备
    @GET(CameraDeviceInfAPI.method)
    Observable<String> getCameraDeviceInf(@Query("username") String username);

    //心跳包
    @GET(HeartBeatAPI.method)
    Observable<String> getHeartBeat(@Query("username") String username);

    //普通登录
    @POST(LoginApi.METHOD)
    Observable<String> getLogin(@Body LoginRequestBody requestBody);

    //验证码登录
    @POST(LoginCodeApi.METHOD)
    Observable<String> getCodeLogin(@Body LoginCodeRequestBody requestBody);

    //获取验证码
    @GET(VerificationCodeAPI.METHOD)
    Observable<String> getVerificationCode(@Query("username") String username);

    @POST(RegisterAPI.METHOD)
    Observable<String> getRegister(@Body RegisterBody requestBody);

    @POST(ChangePwdAPI.METHOD)
    Observable<String> getChangePwd(@Body ChangePwdBody requestBody);

    @POST(LoginOutAPI.METHOD)
    Observable<String> getLoginOut(@Body LoginOutBody requestBody);

    @POST(FindPwdAPI.METHOD)
    Observable<String> getFindPwd(@Body RegisterBody requestBody);

    @POST(ControlDeviceAPI.METHOD)
    Observable<String> requestControlDevice(@Body ControlDeviceBody requestBody);


}
