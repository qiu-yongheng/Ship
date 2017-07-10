package com.kc.shiptransport.mvp.threadsandlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/7/10 20:48
 * @desc ${TODO}
 */

public class ThreadSandLogActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        if (savedInstanceState != null) {

        } else {

        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
