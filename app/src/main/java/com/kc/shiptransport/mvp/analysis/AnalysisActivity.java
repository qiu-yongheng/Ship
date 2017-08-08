package com.kc.shiptransport.mvp.analysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/7/26  13:55
 * @desc ${TODD}
 */

public class AnalysisActivity extends BaseActivity{

    private static final String TYPE = "TYPE";
    private AnalysisFragment fragment;
    public int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(TYPE);

        if (savedInstanceState != null) {
            fragment = (AnalysisFragment) getSupportFragmentManager().getFragment(savedInstanceState, "AnalysisFragment");
        } else {
            fragment = new AnalysisFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new AnalysisPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "AnalysisFragment", fragment);
        }
    }

    public static void startActivity(Context context, int type) {
        Intent intent = new Intent(context, AnalysisActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
