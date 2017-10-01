package com.bh.uhome.lib.base.net.listener;

import rx.Observable;


/**
 * 回调ober对象
 *
 * @author derek
 * @date 2017/8/14.
 * @time 11:30.
 * @description Describe
 */
public interface HttpOnNextSubListener {

    /**
     * ober成功回调
     * @param observable
     * @param method
     */
    void onNext(Observable observable, String method);
}
