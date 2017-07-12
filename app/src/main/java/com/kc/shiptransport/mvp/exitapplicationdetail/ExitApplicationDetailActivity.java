package com.kc.shiptransport.mvp.exitapplicationdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/7/12  9:57
 * @desc
 */

public class ExitApplicationDetailActivity extends BaseActivity{

    private static final String ITEMID = "ITEMID";
    private ExitApplicationDetailFragment fragment;
    public int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        itemID = bundle.getInt(ITEMID);

        if (savedInstanceState != null) {
            fragment = (ExitApplicationDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ExitApplicationDetailFragment");
        } else {
            fragment = new ExitApplicationDetailFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new ExitApplicationDetailPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ExitApplicationDetailFragment", fragment);
        }
    }

    public static void startActivity(Context context, int itemID) {
        Intent intent = new Intent(context, ExitApplicationDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMID, itemID);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
