package com.bh.uhome.bhuhome.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author 凌霄
 * @date 2017/6/15.
 * @time 16:25.
 * @description Describe
 */
public class PhoneTextWatcher implements TextWatcher {
    private EditText text;

    public PhoneTextWatcher(EditText text) {
        this.text = text;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s != null && s.length() != 0) {
            StringBuilder sb = new StringBuilder();

            int index;
            for(index = 0; index < s.length(); ++index) {
                if(index == 3 || index == 8 || s.charAt(index) != 32) {
                    sb.append(s.charAt(index));
                    if((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != 32) {
                        sb.insert(sb.length() - 1, ' ');
                    }
                }
            }

            if(!sb.toString().equals(s.toString())) {
                index = start + 1;
                if(sb.charAt(start) == 32) {
                    if(before == 0) {
                        ++index;
                    } else {
                        --index;
                    }
                } else if(before == 1) {
                    --index;
                }

                this.text.setText(sb.toString());
                this.text.setSelection(index);
            }

        }
    }

    public void afterTextChanged(Editable s) {
    }
}

