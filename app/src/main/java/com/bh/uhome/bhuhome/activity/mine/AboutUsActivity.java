package com.bh.uhome.bhuhome.activity.mine;

import android.view.View;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.util.CommonUtil;
import com.bh.uhome.bhuhome.widget.TitleBarView;
import com.bh.uhome.lib.base.base.BaseActivity;

/**
 * @author 凌霄
 * @date 2017/9/27.
 * @time 22:57.
 * @description Describe
 */
public class AboutUsActivity extends BaseActivity {
    private TextView txtVersionName;
    private TitleBarView titleBarView;
    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_about_us);
    }

    @Override
    protected void initViews() {
        titleBarView = findView(R.id.title);
        txtVersionName = findViewById(R.id.txt_version_name);
    }

    @Override
    protected void initData() {
        titleBarView.titleHeaderTitleTv.setText("关于我们");
        titleBarView.titleHeaderLeftIv.setVisibility(View.VISIBLE);
        titleBarView.titleHeaderLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtVersionName.setText("V"+CommonUtil.getLocalVersionName(AboutUsActivity.this));
    }
}
