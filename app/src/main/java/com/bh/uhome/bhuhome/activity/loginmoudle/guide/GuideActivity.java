package com.bh.uhome.bhuhome.activity.loginmoudle.guide;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.activity.loginmoudle.home.HomeActivity;
import com.bh.uhome.bhuhome.adapter.GuideAdapter;
import com.bh.uhome.lib.base.base.BaseActivity;


/**
 * @author &#x51cc;&#x9704;
 * @date 2017/6/15.
 * @time 10:13.
 * @description Describe
 */
public class GuideActivity extends BaseActivity implements GuideContract.IGuideView{

    private GuideContract.IGuidePresenter mPresenter;

    private static final String KEY_ONLINE = "key_online";
    private int[] images = new int[]{R.mipmap.guide_one, R.mipmap.guide_two,R.mipmap.guide_three};
    private LinearLayout layoutPointers;
    private ImageView imageButton;
    private ViewPager viewPager = null;
    private LinearLayout.LayoutParams dotParams = null;

    public static void actionStart(BaseActivity activity){
        activity.startActivity(new Intent(activity,GuideActivity.class));
    }


    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_guide);

        mPresenter = new GuidePresenter(this);
    }

    @Override
    protected void initViews() {
        viewPager = findView(R.id.view_pager);
        layoutPointers = findView(R.id.layout_pointers);
        imageButton = findView(R.id.image_view_start);

    }

    @Override
    protected void initData() {
        viewPager.setAdapter(new GuideAdapter(images));
        // 圆点设置
        for (int i = 0; i < images.length; i++) {
            ImageView dot = new ImageView(this);
            // 布局参数
            dotParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dot.setImageResource(R.drawable.guid_pointer);
            layoutPointers.addView(dot,dotParams);
            dot.setSelected(i == 0);
        }
        // 监听切换

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < layoutPointers.getChildCount(); i++) {
                    layoutPointers.getChildAt(i).setSelected(i == position);
                }
                imageButton.setVisibility(position > 0 && position == images.length - 1 ? View.VISIBLE : View.GONE);
                layoutPointers.setVisibility(position == images.length - 1 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 点击开始
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:保存版本
//                UserPreferences.$(getApplication()).saveCurrentVersion();
//                if(getIntent().getBooleanExtra(KEY_ONLINE,false)){
//                    HomeActivity.actionStart(GuideActivity.this,0);
//                }else {
//                    LoginActivity.actionStart(GuideActivity.this);
//                }
                HomeActivity.actionStart(GuideActivity.this,0);
                finish();
            }
        });
    }



    @Override
    public void setPresenter(GuideContract.IGuidePresenter presenter) {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }
}
