package com.bh.uhome.bhuhome.activity.loginmoudle.guide;

/**
 * @author 凌霄
 * @date 2017/6/15.
 * @time 10:13.
 * @description Describe
 */
public class GuidePresenter implements GuideContract.IGuidePresenter{

    private GuideContract.IGuideView mIGuideView;

    public GuidePresenter(GuideContract.IGuideView mIGuideView){
        this.mIGuideView = mIGuideView;
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
