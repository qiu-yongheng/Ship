package com.kc.shiptransport.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @author 邱永恒
 * @time 2017/8/29  9:28
 * @desc ${TODD}
 */

public class ToastUtil {
    private static Toast mToast;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void tip(final Context context, final String tip) {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, tip, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(tip);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
            }
        });
    }

    private static void runOnMainThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }
}
