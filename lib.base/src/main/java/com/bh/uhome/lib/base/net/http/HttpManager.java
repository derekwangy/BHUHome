package com.bh.uhome.lib.base.net.http;

import android.content.Context;
import android.util.Log;

import com.bh.uhome.lib.base.app.RxRetrofitApp;
import com.bh.uhome.lib.base.base.BaseActivity;
import com.bh.uhome.lib.base.net.Api.BaseApi;
import com.bh.uhome.lib.base.net.exception.RetryWhenNetworkException;
import com.bh.uhome.lib.base.net.http.func.ExceptionFunc;
import com.bh.uhome.lib.base.net.http.func.ResulteFunc;
import com.bh.uhome.lib.base.net.http.param.ParamsInterceptor;
import com.bh.uhome.lib.base.net.http.response.ResponseIntercepter;
import com.bh.uhome.lib.base.net.listener.HttpOnNextListener;
import com.bh.uhome.lib.base.net.listener.HttpOnNextSubListener;
import com.bh.uhome.lib.base.net.subscribers.ProgressSubscriber;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http交互处理类
 *
 * @author derek
 * @date 2017/8/10.
 * @time 11:10.
 * @description Describe
 */
public class HttpManager {
    /*软引用對象*/
    private SoftReference<HttpOnNextListener> onNextListener;
    private SoftReference<HttpOnNextSubListener> onNextSubListener;
    private SoftReference<Context> appCompatActivity;

    public HttpManager(HttpOnNextListener onNextListener, Context appCompatActivity) {
        this.onNextListener = new SoftReference(onNextListener);
        this.appCompatActivity = new SoftReference(appCompatActivity);
    }

    public HttpManager(HttpOnNextSubListener onNextSubListener, Context appCompatActivity) {
        this.onNextSubListener = new SoftReference(onNextSubListener);
        this.appCompatActivity = new SoftReference(appCompatActivity);
    }


    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public void doHttpDeal(final BaseApi basePar) {
        Retrofit retrofit = getReTrofit(basePar.getConnectionTime(), basePar.getBaseUrl());
        httpDeal(basePar.getObservable(retrofit), basePar);
    }


    /**
     * 获取Retrofit对象
     *
     * @param connectTime
     * @param baseUrl
     * @return
     */
    public Retrofit getReTrofit(int connectTime, String baseUrl) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectTime, TimeUnit.SECONDS);
        if (RxRetrofitApp.isDebug()) {
            builder.addInterceptor(getHttpLoggingInterceptor());
        }
        //头部参数
        builder.addInterceptor(new ParamsInterceptor());
        builder.addInterceptor(new ResponseIntercepter());

        /*创建retrofit对象*/
        final Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit;
    }

    /**
     * RxRetrofit处理
     *
     * @param observable
     * @param basePar
     */
    public void httpDeal(Observable observable, BaseApi basePar) {
          /*失败后的retry配置*/
        observable = observable.retryWhen(new RetryWhenNetworkException(basePar.getRetryCount(),
                basePar.getRetryDelay(), basePar.getRetryIncreaseDelay()))
                /*异常处理*/
                .onErrorResumeNext(new ExceptionFunc())
                /*生命周期管理*/
                //.compose(appCompatActivity.get().bindToLifecycle())
                //Note:手动设置在activity onDestroy的时候取消订阅
//                .compose(appCompatActivity.get().bindUntilEvent(ActivityEvent.DESTROY))
                /*返回数据统一判断*/
                .map(new ResulteFunc())
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread());

        /*ober回调，链接式返回*/
        if (onNextSubListener != null && null != onNextSubListener.get()) {
            onNextSubListener.get().onNext(observable, basePar.getMethod());
        }

        /*数据String回调*/
        if (onNextListener != null && null != onNextListener.get()) {
            ProgressSubscriber subscriber = new ProgressSubscriber(basePar, onNextListener, appCompatActivity);
            observable.subscribe(subscriber);
        }
    }

    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit", "Retrofit====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

}
