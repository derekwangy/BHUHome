package com.bh.uhome.lib.base.net.Api;


import com.bh.uhome.lib.base.base.BaseActivity;
import com.bh.uhome.lib.base.net.http.HttpManager;
import com.bh.uhome.lib.base.net.listener.HttpOnNextListener;
import com.bh.uhome.lib.base.net.listener.HttpOnNextSubListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


import retrofit2.Retrofit;
import rx.Observable;


/**
 * 请求数据统一封装类
 * @author derek
 * @date 2017/8/10.
 * @time 19:57.
 * @description Describe
 */
public class HttpManagerApi extends BaseApi {
    private HttpManager manager;

    public HttpManagerApi(HttpOnNextListener onNextListener, BaseActivity appCompatActivity) {
        manager = new HttpManager(onNextListener, appCompatActivity);
    }

    public HttpManagerApi(HttpOnNextSubListener onNextSubListener, BaseActivity appCompatActivity) {
        manager = new HttpManager(onNextSubListener, appCompatActivity);
    }

    protected Retrofit getRetrofit() {
        return  manager.getReTrofit(getConnectionTime(), getBaseUrl());
    }


    protected void doHttpDeal(Observable observable) {
            manager.httpDeal(observable, this);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return null;
    }
}
