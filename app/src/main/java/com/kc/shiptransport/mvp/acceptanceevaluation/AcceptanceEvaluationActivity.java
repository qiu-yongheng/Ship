package com.kc.shiptransport.mvp.acceptanceevaluation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;


/**
 * @author 邱永恒
 * @time 2017/8/7  9:24
 * @desc ${TODD}
 */

public class AcceptanceEvaluationActivity extends BaseActivity{

    private AcceptanceEvaluationFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        if (savedInstanceState != null) {
            fragment = (AcceptanceEvaluationFragment) getSupportFragmentManager().getFragment(savedInstanceState, "AcceptanceEvaluationFragment");
        } else {
            fragment = new AcceptanceEvaluationFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new AcceptanceEvaluationPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "AcceptanceEvaluationFragment", fragment);
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AcceptanceEvaluationActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
