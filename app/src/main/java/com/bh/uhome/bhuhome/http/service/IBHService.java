package com.bh.uhome.bhuhome.http.service;

import com.bh.uhome.bhuhome.http.api.CameraDeviceInfAPI;
import com.bh.uhome.bhuhome.http.api.DeviceInfAPI;
import com.bh.uhome.bhuhome.http.api.HeartBeatAPI;
import com.bh.uhome.bhuhome.http.api.VersionAPI;
import com.bh.uhome.bhuhome.http.api.YSTokenApi;

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

    //设备信息
    @GET(DeviceInfAPI.method)
    Observable<String> getDeviceInfo(@Query("username") String username);

    //搜索前端可管理监控设备
    @GET(CameraDeviceInfAPI.method)
    Observable<String> getCameraDeviceInf(@Query("username") String username);

    //心跳包
    @GET(HeartBeatAPI.method)
    Observable<String> getHeartBeat(@Query("username") String username);
}
