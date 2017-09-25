package com.kc.shiptransport.mvp.analysisdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/7/27  13:40
 * @desc ${TODD}
 */

public class AnalysisDetailActivity extends BaseActivity{

    private static final String ITEMID = "ITEMID";
    private AnalysisDetailFragment fragment;
    public int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Bundle bundle = getIntent().getExtras();
        itemID = bundle.getInt(ITEMID);

        if (savedInstanceState != null) {
            fragment = (AnalysisDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "AnalysisDetailFragment");
        } else {
            fragment = new AnalysisDetailFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new AnalysisDetailPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "AnalysisDetailFragment", fragment);
        }
    }

    public static void startActivity(Context context, int itemID) {
        Intent intent = new Intent(context, AnalysisDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMID, itemID);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
