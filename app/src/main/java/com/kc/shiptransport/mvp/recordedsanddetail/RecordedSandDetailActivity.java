package com.kc.shiptransport.mvp.recordedsanddetail;

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

public class RecordedSandDetailActivity extends BaseActivity{

    private static final String ITEMID = "ITEMID";
    private static final String TYPE = "TYPE";
    private static final String RECORDEDID = "RECORDEDID";
    private static final String ISREADONLY = "ISREADONLY";
    private RecordedSandDetailFragment fragment;
    public int itemID;
    public int type;
    public int recordedID;
    public boolean isReadOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        itemID = bundle.getInt(ITEMID);
        recordedID = bundle.getInt(RECORDEDID);
        type = bundle.getInt(TYPE);
        isReadOnly = bundle.getBoolean(ISREADONLY);

        if (savedInstanceState != null) {
            fragment = (RecordedSandDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "RecordedSandDetailFragment");
        } else {
            fragment = new RecordedSandDetailFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new RecordedSandDetailPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "RecordedSandDetailFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Context context, int itemID, int type, int recordedID, boolean isReadOnly) {
        Intent intent = new Intent(context, RecordedSandDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMID, itemID);
        bundle.putInt(RECORDEDID, recordedID);
        bundle.putInt(TYPE, type);
        bundle.putBoolean(ISREADONLY, isReadOnly);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
