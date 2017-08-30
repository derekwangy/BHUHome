package com.bh.uhome.bhuhome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.lib.base.base.BaseFragment;


/**
 * @author 凌霄
 * @date 2017/8/25.
 * @time 14:57.
 * @description Describe
 */
public class MyFragment extends BaseFragment {
    private TextView title_header_title_tv = null;
    private View parentView = null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_mine, container, false);
        initViews();
        initData();
        return parentView;
    }

    private void initViews() {
        title_header_title_tv = parentView.findViewById(R.id.title_header_title_tv);

    }

    private void initData() {
        title_header_title_tv.setText("我");
    }
}
