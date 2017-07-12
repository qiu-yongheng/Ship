package com.kc.shiptransport.mvp.changepassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author qiuyongheng
 * @time 2017/7/12  16:05
 * @desc ${TODD}
 */

public class ChangePasswordActivity extends BaseActivity{

    private ChangePasswordFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        if (savedInstanceState != null) {
            fragment = (ChangePasswordFragment)getSupportFragmentManager().getFragment(savedInstanceState, "ChangePasswordFragment");
        } else {
            fragment = new ChangePasswordFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new ChangePasswordPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
