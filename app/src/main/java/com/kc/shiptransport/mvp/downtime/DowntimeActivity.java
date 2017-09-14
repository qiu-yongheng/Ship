package com.kc.shiptransport.mvp.downtime;

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
 * @time 2017/7/6  17:16
 * @desc ${TODD}
 */

public class DowntimeActivity extends BaseActivity{

    private static final String TYPE = "TYPE";
    private static final String ITEMID = "ITEMID";
    private static String CURRENTDATE = "CURRENTDATE";
    private DowntimeFragment fragment;
    public String currentDate;
    public int type;
    public int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        currentDate = bundle.getString(CURRENTDATE);
        type = bundle.getInt(TYPE);
        itemID = bundle.getInt(ITEMID);

        if (savedInstanceState != null) {
            fragment = (DowntimeFragment) getSupportFragmentManager().getFragment(savedInstanceState, "DowntimeFragment");
        } else {
            fragment = new DowntimeFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new DowntimePresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "DowntimeFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Context context, String currentDate, int itemID, int type) {
        Intent intent = new Intent(context, DowntimeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(CURRENTDATE, currentDate);
        bundle.putInt(TYPE, type);
        bundle.putInt(ITEMID, itemID);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
