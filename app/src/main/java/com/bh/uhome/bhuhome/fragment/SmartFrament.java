package com.bh.uhome.bhuhome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.lib.base.base.BaseFragment;
import com.bh.uhome.lib.base.toast.ToastUtil;


/**
 * @author 凌霄
 * @date 2017/8/25.
 * @time 14:48.
 * @description Describe
 */
public class SmartFrament extends BaseFragment implements View.OnClickListener{
    private TextView title_header_title_tv = null;
    private View parentView = null;
    private ImageView title_header_right1_iv = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_smart, container, false);
        initViews();
        initData();
        return parentView;
    }

    private void initViews() {
        title_header_title_tv = parentView.findViewById(R.id.title_header_title_tv);
        title_header_right1_iv = parentView.findViewById(R.id.title_header_right1_iv);
    }

    private void initData() {
        title_header_title_tv.setText("我的地址");
        title_header_right1_iv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_header_right1_iv:
                ToastUtil.showShort(getActivity(),"add");
                break;
        }
    }
}
