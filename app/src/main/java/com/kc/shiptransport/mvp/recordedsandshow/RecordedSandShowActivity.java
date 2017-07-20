package com.kc.shiptransport.mvp.recordedsandshow;

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
 * @time 2017/6/19  11:17
 * @desc ${TODD}
 */

public class RecordedSandShowActivity extends BaseActivity{

    private static final String ITEMID = "RecordedSandShowActivity_itemID";
    private RecordedSandShowFragment fragment;
    public int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        itemID = bundle.getInt(ITEMID);

        if (savedInstanceState != null) {
            fragment = (RecordedSandShowFragment) getSupportFragmentManager().getFragment(savedInstanceState, "RecordedSandShowFragment");
        } else {
            fragment = new RecordedSandShowFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new RecordedSandShowPresenter(this, fragment, new DataRepository());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "RecordedSandShowFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Context context, int itemID) {
        Intent intent = new Intent(context, RecordedSandShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMID, itemID);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
