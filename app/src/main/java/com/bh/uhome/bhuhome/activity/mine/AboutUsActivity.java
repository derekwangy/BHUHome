package com.bh.uhome.bhuhome.activity.mine;

import android.view.View;
import android.widget.ImageView;
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
    private TextView txtVersionName,txtMidTitle;
    private ImageView imgBack;
    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_about_us);
    }

    @Override
    protected void initViews() {
        imgBack = findView(R.id.imgBack);
        txtVersionName = findView(R.id.txt_version_name);
        txtMidTitle = findView(R.id.txtMidTitle);

    }

    @Override
    protected void initData() {
        txtMidTitle.setText("关于我们");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtVersionName.setText("V"+CommonUtil.getLocalVersionName(AboutUsActivity.this));
    }
}
