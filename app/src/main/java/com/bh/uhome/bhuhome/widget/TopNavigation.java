package com.bh.uhome.bhuhome.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;


/**
 * @author 凌霄
 * @date 2017/6/28.
 * @time 14:09.
 * @description Describe
 */
public class TopNavigation extends Toolbar {
    private TextView textView;

    public Paint getPaint() {
        Paint paint = new Paint(1);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(getResources().getColor(R.color.color_cccccc));
        return paint;
    }

    public TopNavigation(Context context) {
        super(context);
        this.init();
    }

    public TopNavigation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public TopNavigation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
//        this.setBackgroundResource(R.drawable.bg_gradient);
        if(Build.VERSION.SDK_INT >= 21) {
            this.setElevation(4.0F);
        }

    }

    public void setTitle(CharSequence title) {
        super.setTitle("");
        if(this.textView == null) {
            this.textView = new TextView(this.getContext());
            LayoutParams lp = new LayoutParams(-2, -2);
            lp.gravity = 17;
            this.textView.setTextSize(2, 20.0F);
            this.textView.setTextColor(-1);
            this.textView.setEllipsize(TextUtils.TruncateAt.END);
            this.textView.setMaxLines(1);
            this.textView.setLayoutParams(lp);
            this.addView(this.textView);
        }

        if(TextUtils.isEmpty((CharSequence)title)) {
            title = "";
        }

        this.textView.setText((CharSequence)title);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(Build.VERSION.SDK_INT < 21) {
            int h = this.getHeight();
            canvas.drawLine(0.0F, (float)h, (float)this.getWidth(), (float)h, this.getPaint());
        }

    }
}
