package com.kc.shiptransport.mvp.acceptancedetail;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/6/1  16:05
 * @desc ${TODD}
 */

public class AcceptanceDetailActivity extends BaseActivity{

    private AcceptanceDetailFragment acceptanceDetailFragment;
    public int itemID;
    public boolean isAcceptance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // 获取传过来的数据
        Bundle bundle = getIntent().getExtras();
        itemID = bundle.getInt("AcceptanceDetailActivity_itemID");
        isAcceptance = bundle.getBoolean("AcceptanceDetailActivity_isAcceptance");

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
     * @param itemID
     * @param isAcceptance
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Activity activity, int itemID, boolean isAcceptance) {
        Intent intent = new Intent(activity, AcceptanceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("AcceptanceDetailActivity_itemID", itemID);
        bundle.putBoolean("AcceptanceDetailActivity_isAcceptance", isAcceptance);
        // TODO 要传过来的数据
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }
}
