package com.kc.shiptransport.mvp.voyagedetail;

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
 * @time 2017/6/13  17:13
 * @desc ${TODD}
 */

public class VoyageDetailActivity extends BaseActivity{

    private static final String TYPE = "TYPE";
    private VoyageDetailFragment fragment;
    private static final String TAG_POSITION = "VoyageDetailActivity_position";
    public int mPosition;
    public int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // 获取position
        Bundle extras = getIntent().getExtras();
        mPosition = extras.getInt(TAG_POSITION);
        type = extras.getInt(TYPE);

        if (savedInstanceState != null) {
            fragment = (VoyageDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "VoyageDetailFragment");
        } else {
            fragment = new VoyageDetailFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new VoyageDetailPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "VoyageDetailFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Activity activity, int position, int type) {
        Intent intent = new Intent(activity, VoyageDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TAG_POSITION, position);
        bundle.putInt(TYPE, type);
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
