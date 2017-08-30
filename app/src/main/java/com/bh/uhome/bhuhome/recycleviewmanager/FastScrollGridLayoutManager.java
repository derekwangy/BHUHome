package com.bh.uhome.bhuhome.recycleviewmanager;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.bh.uhome.lib.base.log.LogUtil;


/**
 * 类用途
 *
 * @version V2.6 <描述当前版本功能>
 * @FileName: com.yuyuetech.yuyue.widget.recycleviewmanager.FastScrollLinearLayoutManager.java
 * @author: derek
 * @date: 2016-10-20 15:52
 */
public class FastScrollGridLayoutManager extends GridLayoutManager {


    public FastScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FastScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public FastScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return  FastScrollGridLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            //该方法控制速度。
            //if returned value is 2 ms, it means scrolling 1000 pixels with LinearInterpolation should take 2 seconds.
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                /*
                     控制单位速度,  毫秒/像素, 滑动1像素需要多少毫秒.

                     默认为 (25F/densityDpi) 毫秒/像素

                     mdpi上, 1英寸有160个像素点, 25/160,
                     xxhdpi,1英寸有480个像素点, 25/480,
                  */

                //return 10F / displayMetrics.densityDpi;//可以减少时间，默认25F
                return super.calculateSpeedPerPixel(displayMetrics);
            }

            //该方法计算滑动所需时间。在此处间接控制速度。
            //Calculates the time it should take to scroll the given distance (in pixels)
            @Override
            protected int calculateTimeForScrolling(int dx) {
               /*
                   控制距离, 然后根据上面那个方(calculateSpeedPerPixel())提供的速度算出时间,

                   默认一次 滚动 TARGET_SEEK_SCROLL_DISTANCE_PX = 10000个像素,

                   在此处可以减少该值来达到减少滚动时间的目的.
                */

                //间接计算时提高速度，也可以直接在calculateSpeedPerPixel提高
                if (dx > 3000) {
                    dx = 3000;
                }

                int time = super.calculateTimeForScrolling(dx);
                LogUtil.d("FastTime:",time+"");//打印时间看下

                return time;
            }
        };

        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
}
