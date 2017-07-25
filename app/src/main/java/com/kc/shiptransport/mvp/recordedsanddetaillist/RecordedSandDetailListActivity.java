package com.kc.shiptransport.mvp.recordedsanddetaillist;

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

public class RecordedSandDetailListActivity extends BaseActivity{

    private static final String ITEMID = "ITEMID";
    private static final String ISREADONLY = "ISREADONLY";
    private RecordedSandDetailListFragment fragment;
    public int itemID;
    public boolean isReadOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        itemID = bundle.getInt(ITEMID);
        isReadOnly = bundle.getBoolean(ISREADONLY);

        if (savedInstanceState != null) {
            fragment = (RecordedSandDetailListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "RecordedSandDetailListFragment");
        } else {
            fragment = new RecordedSandDetailListFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new RecordedSandDetailListPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "RecordedSandDetailListFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Context context, int itemID, boolean isReadOnly) {
        Intent intent = new Intent(context, RecordedSandDetailListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMID, itemID);
        bundle.putBoolean(ISREADONLY, isReadOnly);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
