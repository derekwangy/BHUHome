package com.bh.uhome.lib.base.net.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;


/**
 * service-下载接口
 * @author derek
 * @date 2017/8/22.
 * @time 10:18.
 * @description Describe
 */
public interface HttpDownService {

    /*断点续传下载接口*/
    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);
}
