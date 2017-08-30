package com.bh.uhome.bhuhome.recycleviewmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecycleView 自定义分割线
 * Created by derek on 16/5/18.
 *
 * 使用方法：

 1,添加默认分割线：高度为2px，颜色为灰色

 mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));

 2,添加自定义分割线：可自定义分割线drawable

 mRecyclerView.addItemDecoration(new RecycleViewDivider(
 mContext, LinearLayoutManager.VERTICAL, R.drawable.divider_mileage));

 3,添加自定义分割线：可自定义分割线高度和颜色

 mRecyclerView.addItemDecoration(new RecycleViewDivider(
 mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.divide_gray_color)));

 */

public class RecycleViewDivider extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;
    /**
     *作为Divider的Drawable对象
     * @param context 当前上下文用于获取资源
     * @param resId  color drawable等类型资源文件
     */
    public RecycleViewDivider(Context context, int resId) {
        mDrawable = context.getResources().getDrawable(resId);
    }
    public void onDrawOver(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            //以下计算主要用来确定绘制的位置
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }
    public void getItemOffsets(Rect outRect, int position, RecyclerView parent) {
        outRect.set(0, 0, 0, mDrawable.getIntrinsicWidth());
    }
}

