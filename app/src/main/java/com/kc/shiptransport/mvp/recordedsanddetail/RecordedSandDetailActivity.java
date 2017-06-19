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

    private RecordedSandDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

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
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RecordedSandDetailActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
