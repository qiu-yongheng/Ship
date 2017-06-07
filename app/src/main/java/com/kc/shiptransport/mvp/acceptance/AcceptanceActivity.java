package com.kc.shiptransport.mvp.acceptance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/5/31 20:10
 * @desc ${TODO}
 */

public class AcceptanceActivity extends BaseActivity{

    private AcceptanceFragment acceptanceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        if (savedInstanceState != null) {
            acceptanceFragment = (AcceptanceFragment) getSupportFragmentManager().getFragment(savedInstanceState, "AcceptanceFragment");
        } else {
            acceptanceFragment = new AcceptanceFragment();
        }

        if (!acceptanceFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, acceptanceFragment)
                    .commit();
        }

        new AcceptancePresenter(this, acceptanceFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (acceptanceFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "AcceptanceFragment", acceptanceFragment);
        }
    }

    /**
     * 跳转到审核界面
     */
    public static void navigateToAcceptanceActivity(Context context) {
        Intent i = new Intent(context, AcceptanceActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
