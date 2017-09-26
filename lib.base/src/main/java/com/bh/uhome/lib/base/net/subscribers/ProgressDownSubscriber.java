package com.bh.uhome.lib.base.net.subscribers;

import com.bh.uhome.lib.base.net.download.DownInfo;
import com.bh.uhome.lib.base.net.download.DownLoadListener.DownloadProgressListener;
import com.bh.uhome.lib.base.net.download.DownState;
import com.bh.uhome.lib.base.net.download.HttpDownManager;
import com.bh.uhome.lib.base.net.listener.HttpDownOnNextListener;
import com.bh.uhome.lib.base.net.utils.DbDownUtil;

import java.lang.ref.SoftReference;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import rx.functions.Action1;

/**
 * &#x65ad;&#x70b9;&#x4e0b;&#x8f7d;&#x5904;&#x7406;&#x7c7b;Subscriber
 * &#x7528;&#x4e8e;&#x5728;Http&#x8bf7;&#x6c42;&#x5f00;&#x59cb;&#x65f6;&#xff0c;&#x81ea;&#x52a8;&#x663e;&#x793a;&#x4e00;&#x4e2a;ProgressDialog
 * &#x5728;Http&#x8bf7;&#x6c42;&#x7ed3;&#x675f;&#x662f;&#xff0c;&#x5173;&#x95ed;ProgressDialog
 * &#x8c03;&#x7528;&#x8005;&#x81ea;&#x5df1;&#x5bf9;&#x8bf7;&#x6c42;&#x6570;&#x636e;&#x8fdb;&#x884c;&#x5904;&#x7406;
 * Created by WZG on 2016/7/16.
 */
public class ProgressDownSubscriber<T> extends Subscriber<T> implements DownloadProgressListener {
    //弱引用结果回调
    private SoftReference<HttpDownOnNextListener> mSubscriberOnNextListener;
    /*下载数据*/
    private DownInfo downInfo;


    public ProgressDownSubscriber(DownInfo downInfo) {
        this.mSubscriberOnNextListener = new SoftReference<>(downInfo.getListener());
        this.downInfo=downInfo;
    }


    public void setDownInfo(DownInfo downInfo) {
        this.mSubscriberOnNextListener = new SoftReference<>(downInfo.getListener());
        this.downInfo=downInfo;
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        if(mSubscriberOnNextListener.get()!=null){
            mSubscriberOnNextListener.get().onStart();
        }
        downInfo.setState(DownState.START);
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        if(mSubscriberOnNextListener.get()!=null){
            mSubscriberOnNextListener.get().onComplete();
        }
        HttpDownManager.getInstance().remove(downInfo);
        downInfo.setState(DownState.FINISH);
        DbDownUtil.getInstance().update(downInfo);
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if(mSubscriberOnNextListener.get()!=null){
            mSubscriberOnNextListener.get().onError(e);
        }
        HttpDownManager.getInstance().remove(downInfo);
        downInfo.setState(DownState.ERROR);
        DbDownUtil.getInstance().update(downInfo);
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }
    }

    @Override
    public void update(long read, long count, boolean done) {
        if(downInfo.getCountLength()>count){
            read=downInfo.getCountLength()-count+read;
        }else{
            downInfo.setCountLength(count);
        }
        downInfo.setReadLength(read);
        if (mSubscriberOnNextListener.get() != null) {
            /*接受进度消息，造成UI阻塞，如果不需要显示进度可去掉实现逻辑，减少压力*/
            rx.Observable.just(read).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                      /*如果暂停或者停止状态延迟，不需要继续发送回调，影响显示*/
                            if(downInfo.getState()==DownState.PAUSE||downInfo.getState()==DownState.STOP)return;
                            downInfo.setState(DownState.DOWN);
                            mSubscriberOnNextListener.get().updateProgress(aLong,downInfo.getCountLength());
                        }
                    });
        }
    }

}