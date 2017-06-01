package com.kc.shiptransport.mvp.supplydetail;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/6/1  11:08
 * @desc ${TODD}
 */

public class SupplyDetailActivity extends BaseActivity{

    private SupplyDetailFragemnt supplyDetailFragemnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_plan);

        if (savedInstanceState != null) {
            supplyDetailFragemnt = (SupplyDetailFragemnt) getSupportFragmentManager().getFragment(savedInstanceState, "SupplyDetailFragment");
        } else {
            supplyDetailFragemnt = new SupplyDetailFragemnt();
        }

        if (!supplyDetailFragemnt.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, supplyDetailFragemnt)
                    .commit();
        }

        new SupplyDetailPresenter(this, supplyDetailFragemnt);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (supplyDetailFragemnt.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "SupplyDetailFragment", supplyDetailFragemnt);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, SupplyDetailActivity.class);
        Bundle bundle = new Bundle();
        // TODO 要传过来的数据
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }
}
