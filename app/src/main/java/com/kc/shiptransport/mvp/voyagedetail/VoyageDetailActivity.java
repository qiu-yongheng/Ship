package com.kc.shiptransport.mvp.voyagedetail;

import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/6/13  17:13
 * @desc ${TODD}
 */

public class VoyageDetailActivity extends BaseActivity{

    private VoyageDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        if (savedInstanceState != null) {
            fragment = (VoyageDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "VoyageDetailFragment");
        } else {
            fragment = new VoyageDetailFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new VoyageDetailPresenter(this, fragment, new DataRepository());
    }
}
