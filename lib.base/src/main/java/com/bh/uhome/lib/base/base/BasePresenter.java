package com.bh.uhome.lib.base.base;

/**
 * Presenter 接口基本定义.
 */
public interface BasePresenter {

    /**
     * 订阅.
     */
    void subscribe();

    /**
     * 取消订阅.
     */
    void unsubscribe();

    /**
     * 跳转页面
     */
    void toActivity(String uri);

}
