package com.kc.shiptransport.mvp.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.mvp.main.MainActivity;
import com.kc.shiptransport.util.SettingUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author qiuyongheng
 * @time 2017/5/16  9:03
 * @desc ${TODD}
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {
    @BindView(R.id.edittext_username)
    AppCompatEditText edittextUsername;
    @BindView(R.id.edittext_password)
    AppCompatEditText edittextPassword;
    @BindView(R.id.radiobtn_remember_password)
    CheckBox radiobtnRememberPassword;
    @BindView(R.id.radiobtn_author_login)
    CheckBox radiobtnAuthorLogin;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    private SharedPreferences sp;
    private LoginContract.Presenter presenter;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 实现背景图与状态栏融合
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        new LoginPresenter(this, this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        init();
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        /** 1. 保存密码 */
        radiobtnRememberPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // 获取填入的密码
                    String password = edittextPassword.getText().toString();
                    SharedPreferences.Editor edit = sp.edit();
                    // 保存选中状态
                    edit.putBoolean(SettingUtil.KEY_REMEMBER_PASSWORD, true);
                    // 保存密码
                    if (password.isEmpty()) {
                        showCheckRememberError();
                        radiobtnRememberPassword.setChecked(false);
                    } else {
                        edit.putString(SettingUtil.DATA_PASSWORD, password);
                    }
                    edit.apply();
                } else {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean(SettingUtil.KEY_REMEMBER_PASSWORD, false);
                    edit.putString(SettingUtil.DATA_PASSWORD, "");
                    edit.apply();
                }
            }
        });

        /** 2. 自动登录 */
        radiobtnAuthorLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (radiobtnRememberPassword.isChecked()) {
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean(SettingUtil.KEY_AUTHOR_LOGIN, true);
                        edit.apply();
                    } else {
                        showCheckAutoError();
                        radiobtnAuthorLogin.setChecked(false);
                    }
                } else {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean(SettingUtil.KEY_AUTHOR_LOGIN, false);
                    edit.apply();
                }
            }
        });

        /** 3. 登录 */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = edittextUsername.getText().toString();
                password = edittextPassword.getText().toString();

                // 保存用户名
                SharedPreferences.Editor edit = sp.edit();
                edit.putString(SettingUtil.DATA_USERNAME, username);
                edit.apply();

                if (username.isEmpty() || password.isEmpty()) {
                    tip("用户名或密码不能为空");
                } else {
                    // 登录
                    presenter.login(username, password);
                }
            }
        });
    }

    /**
     * 数据展示, 初始化操作
     */
    private void init() {
        // 显示用户名
        edittextUsername.setText(sp.getString(SettingUtil.DATA_USERNAME, ""));
        // 显示单选框选中状态
        radiobtnRememberPassword.setChecked(sp.getBoolean(SettingUtil.KEY_REMEMBER_PASSWORD, false));
        radiobtnAuthorLogin.setChecked(sp.getBoolean(SettingUtil.KEY_AUTHOR_LOGIN, false));

        // 是否显示密码
        if (sp.getBoolean(SettingUtil.KEY_REMEMBER_PASSWORD, false)) {
            // 显示密码
            edittextPassword.setText(sp.getString(SettingUtil.DATA_PASSWORD, ""));
        } else {
            // 不显示密码
        }

        // 是否自动登录
        if (sp.getBoolean(SettingUtil.KEY_AUTHOR_LOGIN, false)) {
            // 自动登录, 跳转
            presenter.login(sp.getString(SettingUtil.DATA_USERNAME, ""), sp.getString(SettingUtil.DATA_PASSWORD, ""));
        } else {
            // 不做任何操作
        }
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showloading(boolean active) {
        if (active) {
            showProgressDailog(getResources().getString(R.string.dailog_title_update), "正在登陆");
        } else {
            hideProgressDailog();
        }
    }

    @Override
    public void showNetworkError() {
        Snackbar.make(btnLogin, R.string.login_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.login(edittextUsername.getText().toString(), edittextPassword.getText().toString());
                    }
                }).show();
    }

    @Override
    public void showCheckRememberError() {
        tip("请输入密码");
    }

    @Override
    public void showCheckAutoError() {
        tip("请选择记住密码");
    }

    /**
     * 获取登录结果
     *
     * @param result
     */
    @Override
    public void showResult(boolean result) {
        if (result) {
            // 登录成功, 读取数据
            presenter.loadData(sp.getString(SettingUtil.DATA_USERNAME, ""));
            changeDailogInfo("同步数据", "正在获取分包商数据");
        } else {
            // 登录失败
            tip("账号或密码错误, 请重新输入");
            showloading(false);
        }
    }

    @Override
    public void navigateToMain() {
        navigateToMainActivity();
    }

    @Override
    public void changeDailogInfo(String title, String msg) {
        changeProgressDailogInfo(title, msg);
    }

    @Override
    public void showSyncError() {
        tip("同步数据失败");
    }

    /**
     * 跳转到主界面
     * 保存用户数据
     */
    private void navigateToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        // 在新的栈启动activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    /**
     * 跳转到loginActivity
     */
    public static void navigateToLoginActivity(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        // 在新的栈启动activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
