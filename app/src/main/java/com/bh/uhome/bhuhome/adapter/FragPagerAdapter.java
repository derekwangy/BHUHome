package com.bh.uhome.bhuhome.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author 凌霄
 * @date 2017/8/25.
 * @time 14:59.
 * @description Describe
 */
public class FragPagerAdapter extends FragmentPagerAdapter {
    /**
     * Fragment 集合
     */
    private final List<? extends Fragment> fragments;

    public FragPagerAdapter(FragmentManager fm, List<? extends Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
