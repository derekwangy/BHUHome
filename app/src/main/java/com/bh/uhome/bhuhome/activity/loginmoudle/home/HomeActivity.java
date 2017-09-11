package com.bh.uhome.bhuhome.activity.loginmoudle.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.adapter.FragPagerAdapter;
import com.bh.uhome.bhuhome.entity.MainAdsInfo;
import com.bh.uhome.bhuhome.fragment.MallFragment;
import com.bh.uhome.bhuhome.fragment.MyFragment;
import com.bh.uhome.bhuhome.fragment.SmartFrament;
import com.bh.uhome.bhuhome.util.CommonUtil;
import com.bh.uhome.bhuhome.util.UpdateVersionUtil;
import com.bh.uhome.bhuhome.widget.UnScrollViewPager;
import com.bh.uhome.lib.base.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 凌霄
 * @date 2017/6/15.
 * @time 14:17.
 * @description Describe
 */
public class HomeActivity extends BaseActivity implements HomeContract.IHomeView,RadioGroup.OnCheckedChangeListener{

    private HomeContract.IHomePresenter mHomePresenter;

    private static final String KEY_PAGEINDEX = "key_page_index";

    private static final String PAGE_INDEX = "page_index";
    private static final String CURRENT_INDEX = "current_index";


    /**
     * The View pager.
     */
    UnScrollViewPager viewPager;

    /**
     * The Radio group.
     */
    RadioGroup radioGroup;
    /**
     * The Fragments.
     */
    private List<Fragment> fragments = new ArrayList<>();
    private FragPagerAdapter mFragPagerAdapter = null;
    /**
     * The constant pageIndex.
     */
    private int pageIndex = 0;

    public static void actionStart(BaseActivity activity, int pageIndex) {
        activity.startActivity(new Intent(activity, HomeActivity.class).putExtra(KEY_PAGEINDEX, pageIndex));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_INDEX, pageIndex);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_home);

        mHomePresenter = new HomePresenter(this);

    }

    @Override
    protected void initViews() {
        viewPager = findView(R.id.layout_container);
        radioGroup = findView(R.id.radio_group);


    }

    @Override
    protected void initData() {
        fragments.add(new SmartFrament());
//        fragments.add(new ServiceFrament());
        fragments.add(new MallFragment());
        fragments.add(new MyFragment());

        //radio和viewpager实例化
        viewPager.setOffscreenPageLimit(3);
        mFragPagerAdapter = new FragPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mFragPagerAdapter);
        radioGroup.setOnCheckedChangeListener(this);
        ((RadioButton) radioGroup.getChildAt(pageIndex)).setChecked(true);

//        checkVersion();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        pageIndex = getIntent().getIntExtra(PAGE_INDEX, 0);
    }

    @Override
    public void setPresenter(HomeContract.IHomePresenter presenter) {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    /**
     * 检查版本更新
     */
    public void checkVersion() {
        MainAdsInfo mainAdsInfo = new MainAdsInfo();
        MainAdsInfo.AdsData data = new MainAdsInfo.AdsData();
        mainAdsInfo.setData(data);
        mainAdsInfo.getData().getVersion().setDownloadUrl("http://update.myweimai.com/wemay.apk");
        mainAdsInfo.getData().getVersion().setUpdateType("0");
        mainAdsInfo.getData().getVersion().setVersionCode(2);
        mainAdsInfo.getData().getVersion().setVersionName("1.0.1");

        try {
            int localVersionCode = CommonUtil.getAppVersionCode(this);
            int dbVersionCode = mainAdsInfo.getData().getVersion().getVersionCode();
            if (dbVersionCode > localVersionCode) {
                new UpdateVersionUtil(this, mainAdsInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            CommonUtil.exit(HomeActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//        if (checkedId == R.id.radio_one) {
//            if (viewPager.getCurrentItem() != 0) {
//                viewPager.setCurrentItem(0, false);
//                pageIndex = 0;
//            }
//        } else if (checkedId == R.id.radio_two) {
//            if (viewPager.getCurrentItem() != 1) {
//                viewPager.setCurrentItem(1, false);
//                pageIndex = 1;
//            }
//        } else if (checkedId == R.id.radio_three) {
//            if (viewPager.getCurrentItem() != 2) {
//                viewPager.setCurrentItem(2, false);
//                pageIndex = 2;
//            }
//        } else if (checkedId == R.id.radio_four) {
//            if (viewPager.getCurrentItem() != 3) {
//                viewPager.setCurrentItem(3, false);
//                pageIndex = 3;
//            }
//        }

        if (checkedId == R.id.radio_one) {
            if (viewPager.getCurrentItem() != 0) {
                viewPager.setCurrentItem(0, false);
                pageIndex = 0;
            }
        }else if (checkedId == R.id.radio_three) {
            if (viewPager.getCurrentItem() != 1) {
                viewPager.setCurrentItem(1, false);
                pageIndex = 1;
            }
        } else if (checkedId == R.id.radio_four) {
            if (viewPager.getCurrentItem() != 2) {
                viewPager.setCurrentItem(2, false);
                pageIndex = 2;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pageIndex > -1 && pageIndex < 3) {
            ((RadioButton) radioGroup.getChildAt(pageIndex)).setChecked(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragments.get(viewPager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
    }
}
