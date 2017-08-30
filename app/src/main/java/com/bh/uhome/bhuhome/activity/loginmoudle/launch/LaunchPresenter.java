package com.bh.uhome.bhuhome.activity.loginmoudle.launch;

import android.text.TextUtils;


/**
 * @author 凌霄
 * @date 2017/6/15.
 * @time 10:11.
 * @description Describe
 */
public class LaunchPresenter implements LaunchContract.ILaunchPresenter{

    private LaunchContract.ILaunchView mILaunchView;

    public LaunchPresenter(LaunchContract.ILaunchView mILaunchView){
        this.mILaunchView = mILaunchView;
    }

    @Override
    public void subscribe() {
        getADImage();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void toActivity(String uri) {

    }

    @Override
    public void getADImage() {

//       NetWork.getWeimaiApi()
//                .getADImage()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ADImageInfo>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(@NonNull ADImageInfo adImageInfo) {
//                        try {
//                            if (adImageInfo != null && adImageInfo.getData() != null){
//                                mILaunchView.adSuccess(adImageInfo.getData().getAds().get(0));
//                            }else {
//                                mILaunchView.adFailed();
//                            }
//                        } catch (Exception e) {
//                            mILaunchView.adFailed();
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        mILaunchView.adFailed();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });

    }

    @Override
    public void checkLogin() {


    }
}
