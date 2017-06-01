package com.kc.shiptransport.mvp.acceptancedetail;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.mvp.plansetting.PlanSetActivity;

/**
 * @author qiuyongheng
 * @time 2017/6/1  16:05
 * @desc ${TODD}
 */

public class AcceptanceDetailActivity extends BaseActivity{

    private AcceptanceDetailFragment acceptanceDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_plan);

        if (savedInstanceState != null) {
            acceptanceDetailFragment = (AcceptanceDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "AcceptanceDetailFragment");
        } else {
            acceptanceDetailFragment = new AcceptanceDetailFragment();
        }

        if (!acceptanceDetailFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, acceptanceDetailFragment)
                    .commit();
        }

        new AcceptanceDetailPresenter(this, acceptanceDetailFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (acceptanceDetailFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "AcceptanceDetailFragment", acceptanceDetailFragment);
        }
    }

    /**
     * 暴露给外界的启动方法
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, AcceptanceDetailActivity.class);
        Bundle bundle = new Bundle();
        // TODO 要传过来的数据
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }
}
