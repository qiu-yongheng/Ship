package com.kc.shiptransport.mvp.homedetail;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/6/30 21:44
 * @desc 从主页模块列表跳转进来, 显示模块下的小模块列表
 */

public class HomeDetailActivity extends BaseActivity{

    private static final String APPPID = "APPPID";
    private HomeDetailFragment fragment;
    public int mAppPID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        mAppPID = bundle.getInt(APPPID);

        if (savedInstanceState != null) {
            fragment = (HomeDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "HomeDetailFragment");
        } else {
            fragment = new HomeDetailFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new HomeDetailPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "HomeDetailFragment", fragment);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActiviyt(Context context, int appPID) {
        Intent intent = new Intent(context, HomeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(APPPID, appPID);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
