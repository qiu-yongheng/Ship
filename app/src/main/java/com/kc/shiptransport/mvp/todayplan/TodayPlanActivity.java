package com.kc.shiptransport.mvp.todayplan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/11/2  15:21
 * @desc ${TODD}
 */

public class TodayPlanActivity extends BaseActivity{

    private TodayPlanFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        if (savedInstanceState != null) {
            fragment = (TodayPlanFragment) getSupportFragmentManager().getFragment(savedInstanceState, "TodayPlanFragment");
        } else {
            fragment = new TodayPlanFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new TodayPlanPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment != null && fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "TodayPlanFragment", fragment);
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TodayPlanActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
