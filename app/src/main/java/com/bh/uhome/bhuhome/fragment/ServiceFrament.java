package com.bh.uhome.bhuhome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.lib.base.base.BaseFragment;


/**
 * @author 凌霄
 * @date 2017/8/25.
 * @time 14:49.
 * @description Describe
 */
public class ServiceFrament extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        return view;
    }
}
