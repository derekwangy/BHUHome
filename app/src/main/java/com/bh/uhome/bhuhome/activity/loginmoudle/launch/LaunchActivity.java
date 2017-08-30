package com.bh.uhome.bhuhome.activity.loginmoudle.launch;

import android.os.SystemClock;
import android.widget.ImageView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.activity.loginmoudle.guide.GuideActivity;
import com.bh.uhome.bhuhome.activity.loginmoudle.home.HomeActivity;
import com.bh.uhome.bhuhome.constant.APPConstant;
import com.bh.uhome.lib.base.base.BaseActivity;


/**
 * 启动页
 * @author 凌霄
 * @date 2017/6/15.
 * @time 10:11.
 * @description Describe
 */
public class LaunchActivity extends BaseActivity implements LaunchContract.ILaunchView {
    private static final String TAG = "LaunchActivity";

    private LaunchContract.ILaunchPresenter mPresenter = null;

    private long currentTime;
    private ImageView imageLauch;


    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_launch);
        mPresenter = new LaunchPresenter(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        imageLauch = findView(R.id.image_launch);
        currentTime = SystemClock.currentThreadTimeMillis();

        // 获取广告图片
        mPresenter.getADImage();
        // 检查登录
        mPresenter.checkLogin();

        checkLoginOver(false);

    }



    @Override
    public void setPresenter(LaunchContract.ILaunchPresenter presenter) {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void adSuccess(String url) {
        //TODO 图片库添加
//        AppClient.getInstance().getClient()
//                .load(url)
//                .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//                .fit()
//                .error(R.mipmap.image_ad)
//                .into(imageLauch);

        //网络库添加后可去掉
        imageLauch.setImageResource(R.mipmap.image_ad);
    }

    @Override
    public void adFailed() {
        imageLauch.setImageResource(R.mipmap.image_ad);
    }

    @Override
    public void checkLoginOver(final boolean online) {
        // 广告页停留1.5秒
        long interval = SystemClock.currentThreadTimeMillis() - currentTime;
        if(interval >= 1500){
            nextPage();
        }else {
            imageLauch.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextPage();
                }
            },1500 - interval);
        }
    }

    private void nextPage(){
        if(APPConstant.isFirstLaunch){
            // 引导页
            GuideActivity.actionStart(this);
        }else{
            // 无需登录
            HomeActivity.actionStart(this,0);
        }
        finish();
    }

}
