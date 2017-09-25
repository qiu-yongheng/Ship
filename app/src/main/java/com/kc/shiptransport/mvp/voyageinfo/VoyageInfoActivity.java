package com.kc.shiptransport.mvp.voyageinfo;

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

public class VoyageInfoActivity extends BaseActivity{

    private VoyageFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        if (savedInstanceState != null) {
            fragment = (VoyageFragment) getSupportFragmentManager().getFragment(savedInstanceState, "VoyageFragment");
        } else {
            fragment = new VoyageFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new VoyagePresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "VoyageFragment", fragment);
        }
    }

    /**
     * 启动
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void navigateToVoyageInfoActivity(Context context) {
        Intent intent = new Intent(context, VoyageInfoActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
