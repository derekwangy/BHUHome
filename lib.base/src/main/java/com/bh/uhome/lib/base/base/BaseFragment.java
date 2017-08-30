package com.bh.uhome.lib.base.base;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @author 凌霄
 * @date 2017/8/23.
 * @time 16:07.
 * @description Describe
 */
public class BaseFragment extends Fragment {
    protected <T extends View> T findView(@IdRes int id) {
        return (T) getActivity().findViewById(id);
    }
}
