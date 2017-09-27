package com.kc.shiptransport.mvp.threadsand;

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
 * @time 2017/7/7  8:41
 * @desc ${TODD}
 */

public class ThreadSandActivity extends BaseActivity{

    private static final String CURRENTDATE = "CURRENTDATE";
    private static final String ITEMID = "ITEMID";
    private static final String TYPE = "TYPE";
    private static final String ISALLOW = "ISALLOW";
    private ThreadSandFragment fragment;
    public String currentDate;
    public int itemID;
    public int type;
    public boolean isAllow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        currentDate = bundle.getString(CURRENTDATE);
        itemID = bundle.getInt(ITEMID);
        type = bundle.getInt(TYPE);
        isAllow = bundle.getBoolean(ISALLOW);

        if (savedInstanceState  != null) {
            fragment = (ThreadSandFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ThreadSandFragment");
        } else {
            fragment = new ThreadSandFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new ThreadSandPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ThreadSandFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Context context, String currentDate, int itemID, int type, boolean b) {
        Intent intent = new Intent(context, ThreadSandActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(CURRENTDATE, currentDate);
        bundle.putInt(ITEMID, itemID);
        bundle.putInt(TYPE, type);
        bundle.putBoolean(ISALLOW, b);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
