package com.kc.shiptransport.mvp.downtimelog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/7/10  15:05
 * @desc ${TODD}
 */

public class DowntimeLogActivity extends BaseActivity{

    private static final String TYPE = "TYPE";
    private DowntimeLogFragment fragment;
    public int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(TYPE);

        if (savedInstanceState != null) {
            fragment = (DowntimeLogFragment) getSupportFragmentManager().getFragment(savedInstanceState, "DowntimeLogFragment");
        } else {
            fragment = new DowntimeLogFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new DowntimeLogPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "DowntimeLogFragment", fragment);
        }
    }

    public static void startActivity(Context context, int type) {
        Intent intent = new Intent(context, DowntimeLogActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}

