package com.kc.shiptransport.mvp.amountdetail;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:56
 * @desc ${TODD}
 */

public class AmountDetailActivity extends BaseActivity{
    private AmountDetailFragment fragment;
    public int itemID;
    public boolean isAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        itemID = bundle.getInt("itemID");
        isAmount = bundle.getBoolean("isAmount");

        if (savedInstanceState != null) {
            fragment = (AmountDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "AmountDetailFragment");
        } else {
            fragment = new AmountDetailFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new AmountDetailPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "AmountDetailFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Activity activity, int itemID, boolean isAmount) {
        Intent intent = new Intent(activity, AmountDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("itemID", itemID);
        // 是否验收
        bundle.putBoolean("isAmount", isAmount);
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }
}
