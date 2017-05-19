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
import com.kc.shiptransport.mvp.login.LoginActivity;
import com.kc.shiptransport.mvp.plan.PlanActivity;
import com.kc.shiptransport.util.SettingUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_plan)
    AppCompatButton btnPlan;
    @BindView(R.id.btn_exit)
    AppCompatButton btnExit;
    private SharedPreferences sp;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void initListener() {
        btnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPlanActivity();
            }
        });

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
}
