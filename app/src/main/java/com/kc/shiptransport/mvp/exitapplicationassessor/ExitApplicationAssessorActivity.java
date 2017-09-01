package com.kc.shiptransport.mvp.exitapplicationassessor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/8/31  15:42
 * @desc ${TODD}
 */

public class ExitApplicationAssessorActivity extends BaseActivity{

    private ExitApplicationAssessorFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            fragment = (ExitApplicationAssessorFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ExitApplicationAssessorFragment");
        } else {
            fragment = new ExitApplicationAssessorFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(fragment, "ExitApplicationAssessorFragment")
                    .commit();
        }

        new ExitApplicationAssessorPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ExitApplicationAssessorFragment", fragment);
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ExitApplicationAssessorActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
