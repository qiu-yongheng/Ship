package com.kc.shiptransport.mvp.partition;

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
 * @time 2017/7/7  10:18
 * @desc 施工分区
 */

public class PartitionActivity extends BaseActivity {

    private static final String ITEMID = "ITEMID";
    private static final String TYPE = "TYPE";
    private PartitionFragment fragment;
    public int itemID;
    public int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        itemID = bundle.getInt(ITEMID);
        type = bundle.getInt(TYPE);

        if (savedInstanceState != null) {
            fragment = (PartitionFragment) getSupportFragmentManager().getFragment(savedInstanceState, "PartitionFragment");
        } else {
            fragment = new PartitionFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new PartitionPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "PartitionFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Context context, int itemID, int type) {
        Intent intent = new Intent(context, PartitionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMID, itemID);
        bundle.putInt(TYPE, type);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (null != fragment) {
            boolean b = fragment.onBackPressed();
            if (b) {
                super.onBackPressed();
            }
        }
    }
}
