package com.kc.shiptransport.mvp.upcominglist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/9/8  18:13
 * @desc ${TODD}
 */

public class UpcomingListActivity extends BaseActivity{

    private static final String PENDID = "PENDID";
    private UpcomingListFragment fragment;
    public String pendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        pendId = bundle.getString(PENDID);

        if (savedInstanceState != null) {
            fragment = (UpcomingListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "UpcomingListFragment");
        } else {
            fragment = new UpcomingListFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new UpcomingListPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "UpcomingListFragment", fragment);
        }
    }

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, UpcomingListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(PENDID, id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
