package com.bh.uhome.bhuhome.widget;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/1/21.
 */
public class MsgTextWatcher implements TextWatcher {
    private TextView textView;

    public MsgTextWatcher(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        if(!TextUtils.isEmpty(str)){
            if(str.contains("+")){
                //···
                //•••
            }else {
                try {
                    Long count = Long.valueOf(str);
                    if(count > 99){
                        str= "99+";
                        textView.setText(str);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

    }
}
