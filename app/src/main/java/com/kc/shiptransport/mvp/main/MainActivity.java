package com.kc.shiptransport.mvp.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

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
    }

//    private void initView() {
//        sp = PreferenceManager.getDefaultSharedPreferences(this);
//    }
//
//    private void initListener() {
//        /* 添加计划 */
//        btnPlan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navigateToPlanActivity();
//            }
//        });
//
//        /* 验沙 */
//        btnSupplySand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navigateToSupplyActivity();
//            }
//        });
//
//        /* 验收 */
//        btnAcceptance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navigateToAcceptanceActivity();
//            }
//        });
//
//        /* 离开 */
//        btnExit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences.Editor edit = sp.edit();
//                edit.putString(SettingUtil.DATA_USERNAME, "");
//                edit.putString(SettingUtil.DATA_PASSWORD, "");
//                edit.putBoolean(SettingUtil.KEY_REMEMBER_PASSWORD, false);
//                edit.putBoolean(SettingUtil.KEY_AUTHOR_LOGIN, false);
//                edit.apply();
//
//                navigateToLoginActivity();
//            }
//        });
//    }





    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再点一次，退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mainFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "MainFragment", mainFragment);
        }
    }
}
