package com.bh.uhome.bhuhome.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author 凌霄
 * @date 2017/11/19.
 * @time 15:56.
 * @description Describe
 */
public class TextTimeDown extends CountDownTimer {
    public interface CountDownListen {
        void OnTick(TextView textView, long l);

        void onFinish(TextView textView);
    }

    private TextView textView;

    public CountDownListen getCountDownListen() {
        return countDownListen;
    }

    public void setCountDownListen(CountDownListen countDownListen) {
        this.countDownListen = countDownListen;
    }

    private CountDownListen countDownListen;

    /**
     * @param textView          需要作用的文本
     * @param millisInFuture    倒计时总时间（单位：毫秒）
     * @param countDownInterval 间隔时间（单位：毫秒）
     */
    public TextTimeDown(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;

    }

    /**
     * @param l 剩余时间
     */
    @Override
    public void onTick(long l) {
        if (countDownListen != null) {
            countDownListen.OnTick(textView, l);
        }
    }

    @Override
    public void onFinish() {
        if (countDownListen != null) {
            countDownListen.onFinish(textView);
        }
    }

}
