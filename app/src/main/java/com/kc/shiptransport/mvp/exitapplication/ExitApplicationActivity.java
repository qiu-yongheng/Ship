package com.kc.shiptransport.mvp.exitapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/7/12  11:40
 * @desc 退场申请
 */

public class ExitApplicationActivity extends BaseActivity{

    private ExitApplicationFragment fragment;
    public int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        if (savedInstanceState != null) {
            fragment = (ExitApplicationFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ExitApplicationFragment");
        } else {
            fragment = new ExitApplicationFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new ExitApplicationPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {

        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ExitApplicationActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
