package com.kc.shiptransport.mvp.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.mvp.acceptance.AcceptanceActivity;
import com.kc.shiptransport.mvp.login.LoginActivity;
import com.kc.shiptransport.mvp.plan.PlanActivity;
import com.kc.shiptransport.mvp.supply.SupplyActivity;
import com.kc.shiptransport.util.SettingUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private SharedPreferences sp;
    private long exitTime;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            getSupportFragmentManager().getFragment(savedInstanceState, "MainFragment");
        } else {
            mainFragment = new MainFragment();
        }

        if (!mainFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, mainFragment)
                    .commit();
        }

        initView();
        initListener();
    }

    private void initView() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void initListener() {
        /* 添加计划 */
        btnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPlanActivity();
            }
        });

        /* 验沙 */
        btnSupplySand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSupplyActivity();
            }
        });

        /* 验收 */
        btnAcceptance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAcceptanceActivity();
            }
        });

        /* 离开 */
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putString(SettingUtil.DATA_USERNAME, "");
                edit.putString(SettingUtil.DATA_PASSWORD, "");
                edit.putBoolean(SettingUtil.KEY_REMEMBER_PASSWORD, false);
                edit.putBoolean(SettingUtil.KEY_AUTHOR_LOGIN, false);
                edit.apply();

                navigateToLoginActivity();
            }
        });
    }

    /**
     * 跳转到loginActivity
     */
    private void navigateToLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        // 在新的栈启动activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    /**
     * 跳转到planActivity
     */
    private void navigateToPlanActivity() {
        Intent i = new Intent(this, PlanActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再点一次，退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 跳转到供沙界面
     */
    private void navigateToSupplyActivity() {
        Intent i = new Intent(this, SupplyActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    /**
     * 跳转到审核界面
     */
    private void navigateToAcceptanceActivity() {
        Intent i = new Intent(this, AcceptanceActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mainFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "MainFragment", mainFragment);
        }
    }
}
