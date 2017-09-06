package com.kc.shiptransport.mvp.exitassessor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/9/6  15:29
 * @desc ${TODD}
 */

public class ExitAssessorActivity extends BaseActivity{

    private ExitAssessorFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        if (savedInstanceState != null) {
            fragment = (ExitAssessorFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ExitAssessorFragment");
        } else {
            fragment = new ExitAssessorFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new ExitAssessorPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ExitAssessorFragment", fragment);
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ExitAssessorActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
