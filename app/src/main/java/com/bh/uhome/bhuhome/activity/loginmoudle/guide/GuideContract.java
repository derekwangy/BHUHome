package com.bh.uhome.bhuhome.activity.loginmoudle.guide;


import com.bh.uhome.lib.base.base.BasePresenter;
import com.bh.uhome.lib.base.base.BaseView;

/**
 * @author 凌霄
 * @date 2017/6/15.
 * @time 10:14.
 * @description Describe
 */
public interface GuideContract {

    /**
     * 登陆 View 接口定义.
     */
    interface IGuideView extends BaseView<IGuidePresenter> {

    }

    /**
     * 登陆 Presenter 接口定义.
     */
    interface IGuidePresenter extends BasePresenter {

    }
}
