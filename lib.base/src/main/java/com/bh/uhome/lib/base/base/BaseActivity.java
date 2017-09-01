package com.bh.uhome.lib.base.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

/**
 * @author 凌霄
 * @date 2017/8/23.
 * @time 16:07.
 * @description Describe
 */
public abstract class BaseActivity extends FragmentActivity {

    protected <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initViews();
        initData();
    }

    /**
     * 初始化layout
     */
    protected abstract void initContentView();

    /**
     * 初始化 view
     */
    protected abstract void initViews();

    /**
     * 初始化数据
     */
    protected abstract void initData();
}
