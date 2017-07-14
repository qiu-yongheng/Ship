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

    private static String CURRENTDATE = "CURRENTDATE";
    private DowntimeFragment fragment;
    public String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        currentDate = bundle.getString(CURRENTDATE);

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
    public static void startActivity(Context context, String currentDate) {
        Intent intent = new Intent(context, DowntimeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(CURRENTDATE, currentDate);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
