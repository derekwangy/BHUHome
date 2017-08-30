package com.bh.uhome.bhuhome.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author 凌霄
 * @date 2017/6/15.
 * @time 15:07.
 * @description Describe
 */
public class GuideAdapter extends PagerAdapter {
    private int[] images;
    private SparseArray<ImageView> arr;

    public GuideAdapter(int[] images){
        this.images = images;

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(arr == null){
            arr = new SparseArray<>();
        }
        ImageView imageView = arr.get(position);
        if(imageView == null){
            imageView = new ImageView(container.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            // 设置布局参数
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[position]);
            arr.put(position, imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(arr.get(position));
    }
}
