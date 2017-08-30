package com.bh.uhome.bhuhome.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bh.uhome.bhuhome.R;


/**
 * @author 凌霄
 * @date 2017/6/15.
 * @time 16:29.
 * @description Describe
 */
public class ClearableEditText extends android.support.v7.widget.AppCompatEditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {
    private Drawable _right;
    private OnTouchListener _t;
    private OnFocusChangeListener _f;

    public ClearableEditText(Context context) {
        super(context);
        this.init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this._right = this.getCompoundDrawables()[2];
        if(this._right == null) {
            this._right = ContextCompat.getDrawable(this.getContext(), R.mipmap.icon_delete);
        }

        this._right.setBounds(0, 0, this._right.getIntrinsicWidth(), this._right.getIntrinsicHeight());
        this.setCompoundDrawablePadding(8);
        super.setOnFocusChangeListener(this);
        super.setOnTouchListener(this);
        this.addTextChangedListener(this);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        this._f = l;
    }

    public void setOnTouchListener(OnTouchListener l) {
        this._t = l;
    }

    private void setClearIconVisible(boolean visible) {
        Drawable temp = visible?this._right:null;
        Drawable[] drawables = this.getCompoundDrawables();
        this.setCompoundDrawables(drawables[0], drawables[1], temp, drawables[3]);
    }

    public void onFocusChange(View v, boolean hasFocus) {
        this.setClearIconVisible(hasFocus && !TextUtils.isEmpty(this.getText()));
        if(this._f != null) {
            this._f.onFocusChange(v, hasFocus);
        }

    }

    public boolean onTouch(View v, MotionEvent event) {
        if(this.getCompoundDrawables()[2] != null) {
            boolean tapped = event.getX() > (float)(this.getWidth() - this.getPaddingRight() - this._right.getIntrinsicWidth());
            if(tapped) {
                if(event.getAction() == 1) {
                    this.setText("");
                }

                return true;
            }
        }

        return this._t != null?this._t.onTouch(v, event):false;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
        this.setClearIconVisible(this.isFocused() && !TextUtils.isEmpty(s));
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
    }
}
