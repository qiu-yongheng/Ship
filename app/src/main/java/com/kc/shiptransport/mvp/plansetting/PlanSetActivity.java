package com.kc.shiptransport.mvp.plansetting;

import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/5/17  17:11
 * @desc ${TODD}
 */

public class PlanSetActivity extends BaseActivity{
    private PlanSetFragment planSetFragment;
    public int currentSelectItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_set);

        // 获取传过来的数据
        Bundle bundle = getIntent().getExtras();
        currentSelectItem = bundle.getInt("PlanSetActivity");

        if (savedInstanceState != null) {
            planSetFragment = (PlanSetFragment) getSupportFragmentManager().getFragment(savedInstanceState, "PlanSetFragment");
        } else {
            planSetFragment = new PlanSetFragment();
        }

        if (!planSetFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_plan_set, planSetFragment)
                    .commit();
        }

        new PlanSetPresenter(this, planSetFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (planSetFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "PlanSetFragment", planSetFragment);
        }
    }
}
