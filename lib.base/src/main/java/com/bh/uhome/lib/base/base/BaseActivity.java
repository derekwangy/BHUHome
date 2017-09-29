package com.bh.uhome.lib.base.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author 凌霄
 * @date 2017/8/23.
 * @time 16:07.
 * @description Describe
 */
public abstract class BaseActivity extends RxBaseActivity {

    protected <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initViews();
        initData();
        initpermission();
    }
    /**
     * 权限控制
     */
    private void initpermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
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
