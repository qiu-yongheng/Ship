package com.kc.shiptransport.mvp.bcf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/9/25  9:27
 * @desc ${TODD}
 */

public class BCFActivity extends BaseActivity{

    private BCFFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        if (savedInstanceState != null) {
            fragment = (BCFFragment) getSupportFragmentManager().getFragment(savedInstanceState, "BCFFragment");
        } else {
            fragment = new BCFFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new BCFPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            if (fragment != null && fragment.isAdded()) {
                getSupportFragmentManager().putFragment(outState, "BCFFragment", fragment);
            }
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, BCFActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
