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
    public int itemID;
    public boolean isSupply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // 获取传过来的数据
        Bundle bundle = getIntent().getExtras();
        itemID = bundle.getInt("SupplyDetailActivity_itemID");
        isSupply = bundle.getBoolean("SupplyDetailActivity_isSupply");


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
    public static void startActivity(Activity activity, int itemID, boolean isSupply) {
        Intent intent = new Intent(activity, SupplyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("SupplyDetailActivity_itemID", itemID);
        bundle.putBoolean("SupplyDetailActivity_isSupply", isSupply);
        // TODO 要传过来的数据
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }
}
