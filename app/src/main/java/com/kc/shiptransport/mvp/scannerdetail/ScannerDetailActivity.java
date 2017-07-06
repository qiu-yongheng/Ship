package com.kc.shiptransport.mvp.scannerdetail;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:56
 * @desc ${TODD}
 */

public class ScannerDetailActivity extends BaseActivity{

    private static final String POSITION = "ScannerDetailActivity_position";
    private static final String TYPE = "TYPE";
    private ScannerDetailFragment fragment;
    public int position;
    public int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt(POSITION);
        type = bundle.getInt(TYPE);

        if (savedInstanceState != null) {
            fragment = (ScannerDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ScannerDetailFragment");
        } else {
            fragment = new ScannerDetailFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new ScannerDetailPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ScannerDetailFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Activity activity, int position, int type) {
        Intent intent = new Intent(activity, ScannerDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putInt(TYPE, type);
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }
}
