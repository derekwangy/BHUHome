package com.bh.uhome.bhuhome.http.service;

import com.bh.uhome.bhuhome.http.api.home.YSTokenApi;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

}
