package com.kc.shiptransport.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import com.kc.shiptransport.util.LogUtil;

/**
 * @author 邱永恒
 * @time 2017/9/30  9:30
 * @desc ${TODD}
 */

public class TextEditTextView extends EditText {
    public TextEditTextView(Context context) {
        super(context);
    }

    public TextEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1) {
            LogUtil.d("键盘向下");
            super.onKeyPreIme(keyCode, event);
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
