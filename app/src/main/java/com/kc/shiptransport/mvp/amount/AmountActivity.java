package com.kc.shiptransport.mvp.amount;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/6/13  15:36
 * @desc ${TODD}
 */

public class AmountActivity extends BaseActivity{

    private AmountFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        if (savedInstanceState != null) {
            fragment = (AmountFragment) getSupportFragmentManager().getFragment(savedInstanceState, "AmountFragment");
        } else {
            fragment = new AmountFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new AmountPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ScannerFragment", fragment);
        }
    }

    /**
     * 启动
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void navigateToAmountActivity(Context context) {
        Intent intent = new Intent(context, AmountActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
