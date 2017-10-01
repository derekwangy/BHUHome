package com.bh.uhome.lib.base.net.listener;


import com.bh.uhome.lib.base.net.exception.ApiException;

/**
 * 成功回调处理
 *
 * @author derek
 * @date 2017/8/14.
 * @time 11:10.
 * @description Describe
 */
public interface HttpOnNextListener {
    /**
     * 成功后回调方法
     *
     * @param resulte
     * @param method
     */
    void onNext(String resulte, String method);

    /**
     * 失败
     * 失败或者错误方法
     * 自定义异常处理
     *
     * @param e
     * @param method
     */
    void onError(ApiException e, String method);
}
