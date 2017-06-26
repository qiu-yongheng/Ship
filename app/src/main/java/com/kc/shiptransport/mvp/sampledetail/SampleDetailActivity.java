package com.kc.shiptransport.mvp.sampledetail;

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
 * @time 2017/6/14  10:55
 * @desc ${TODD}
 */

public class SampleDetailActivity extends BaseActivity{
    private SampleDetailFragment fragment;
    public int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);


        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("SampleDetailActivity_position");


        if (savedInstanceState != null) {
            fragment = (SampleDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "SampleDetailFragment");
        } else {
            fragment = new SampleDetailFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new SampleDetailPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "SampleDetailFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Activity activity, int position) {
        Intent intent = new Intent(activity, SampleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("SampleDetailActivity_position", position);
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
