package com.kc.shiptransport.mvp.plan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/5/16  19:48
 * @desc ${TODD}
 */

public class PlanActivity extends BaseActivity {
    private PlanFragment planFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        if (savedInstanceState != null) {
            planFragment = (PlanFragment) getSupportFragmentManager().getFragment(savedInstanceState, "PlanFragment");
        } else {
            planFragment = new PlanFragment();
        }

        if (!planFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_repository, planFragment)
                    .commit();
        }

        new PlanPresenter(this, planFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (planFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "PlanFragment", planFragment);
        }
    }

    /**
     * 跳转到planActivity
     */
    public static void navigateToPlanActivity(Context context) {
        Intent i = new Intent(context, PlanActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
