/**
 * Copyright (c) 2015 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 */
package com.bh.uhome.bhuhome.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.bh.uhome.bhuhome.R;


/**
 * 带有一键删除的 EditText
 */
public class DeleteEditText extends EditText {

    private Drawable imgAble;
    /**
     * 我的OnFocusChangeListener.
     * 用户覆盖系统的监听器.
     */
    private OnDeleteFocusChangeListener myFocusChangeListener;
    private DeleteTextWatch myTextWatch;
    /**
     * 系统的监听器.
     * 用于接收UI层使用时传入的系统监听器.
     */
    private OnFocusChangeListener systemFocusChangeListener;
    private TextWatcher systemTextWatch;

    public DeleteEditText(Context context) {
        super(context);
        initView();
    }

    public DeleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DeleteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DeleteEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    /**
     * 初始化视图.
     */
    private void initView() {
        imgAble = getResources().getDrawable(R.mipmap.icon_delete);
        // 设置监听器时,替换成了自己的监听器.
        myFocusChangeListener = new OnDeleteFocusChangeListener();
        myTextWatch = new DeleteTextWatch();
        // 传入自己的监听器.
        super.setOnFocusChangeListener(myFocusChangeListener);
        super.addTextChangedListener(myTextWatch);

    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        systemTextWatch = watcher;
    }

    @Override
    public OnFocusChangeListener getOnFocusChangeListener() {
        return systemFocusChangeListener;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        systemFocusChangeListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
//            rect.left = rect.right - 50;
            rect.left = rect.right - getPaddingRight() - getCompoundDrawablePadding() - BitmapFactory.decodeResource(getResources(), R.mipmap.icon_delete).getWidth();
            if (rect.contains(eventX, eventY)) {
                setText("");
            }

        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置“一键删除图片”
     */
    private void setDrawable() {
        if (hasFocus() && length() > 0 && isEnabled()) {
            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], imgAble, getCompoundDrawables()[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
        }
    }

    class OnDeleteFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // 如果UI层传入了监听器.那么触发它的回调.先让UI进行处理.
            if (systemFocusChangeListener != null) {
                // 将处理传递下去,让UI层面继续处理焦点事件.
                systemFocusChangeListener.onFocusChange(v, hasFocus);
            }
            // 在失去焦点的第一时间,对“一键清除”图标进行处理.
            setDrawable();
        }
    }

    class DeleteTextWatch implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (systemTextWatch != null) {
                systemTextWatch.beforeTextChanged(s, start, count, after);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (systemTextWatch != null) {
                systemTextWatch.onTextChanged(s, start, before, count);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (systemTextWatch != null) {
                systemTextWatch.afterTextChanged(s);
            }
            setDrawable();
        }
    }

}
