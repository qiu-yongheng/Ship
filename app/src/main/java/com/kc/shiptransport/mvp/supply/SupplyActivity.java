package com.kc.shiptransport.mvp.supply;

import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/5/31  17:02
 * @desc ${TODD}
 */

public class SupplyActivity extends BaseActivity{

    private SupplyFragment supplyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_plan);
        if (savedInstanceState != null) {
            supplyFragment = (SupplyFragment) getSupportFragmentManager().getFragment(savedInstanceState, "SupplyFragemnt");
        } else {
            supplyFragment = new SupplyFragment();
        }

        if (!supplyFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_repository, supplyFragment)
                    .commit();
        }

        new SupplyPresenter(this, supplyFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (supplyFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "SupplyFragment", supplyFragment);
        }
    }
}
