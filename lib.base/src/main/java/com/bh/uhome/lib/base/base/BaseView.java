package com.bh.uhome.lib.base.base;

/**
 * View 接口基本定义.
 */
public interface BaseView<T> {

    /**
     * 注入 Presenter.
     *
     * @param presenter Presenter 对象.
     */
    void setPresenter(T presenter);

    /**
     * 展示加载框
     */
    void showLoadingView();

    /**
     * 隐藏加载框
     */
    void hideLoadingView();
}
