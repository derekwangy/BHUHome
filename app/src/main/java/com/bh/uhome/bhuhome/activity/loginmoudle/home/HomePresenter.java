package com.bh.uhome.bhuhome.activity.loginmoudle.home;

/**
 * @author 凌霄
 * @date 2017/6/15.
 * @time 15:50.
 * @description Describe
 */
public class HomePresenter implements HomeContract.IHomePresenter{

    private HomeContract.IHomeView mIHomeView;

    public HomePresenter(HomeContract.IHomeView mIHomeView){
        this.mIHomeView = mIHomeView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void toActivity(String uri) {

    }
}
