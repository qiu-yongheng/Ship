package com.kc.shiptransport.mvp.mine;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.mvp.changepassword.ChangePasswordActivity;
import com.kc.shiptransport.mvp.login.LoginActivity;
import com.kc.shiptransport.mvp.main.MainActivity;
import com.kc.shiptransport.util.SettingUtil;
import com.umeng.analytics.MobclickAgent;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/6/6 20:37
 * @desc ${TODO}
 */

public class MineFragment extends Fragment {


    @BindView(R.id.btn_mine_exit)
    Button btnMineExit;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_account)
    TextView tvUserAccount;
    @BindView(R.id.tv_change_password)
    TextView tvChangePassword;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.tv_section)
    TextView tvSection;
    @BindView(R.id.rl_section)
    RelativeLayout rlSection;
    @BindView(R.id.tv_duties)
    TextView tvDuties;
    @BindView(R.id.rl_duties)
    RelativeLayout rlDuties;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.rl_email)
    RelativeLayout rlEmail;
    @BindView(R.id.tv_tell)
    TextView tvTell;
    @BindView(R.id.rl_tell)
    RelativeLayout rlTell;
    private SharedPreferences sp;
    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        initListener();

        // TODO 加载数据
        return view;
    }

    private void initView(View view) {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        activity = (MainActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(R.string.title_mine);

        // 添加下划线
        tvChangePassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线

        // 显示用户信息
        User user = DataSupport.findAll(User.class).get(0);
        tvUserName.setText(user.getUserName());
        tvUserAccount.setText(user.getUserID());
    }

    public void initListener() {
        /** 退出登录 */
        btnMineExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 记录登出
                MobclickAgent.onProfileSignOff();


                SharedPreferences.Editor edit = sp.edit();
                edit.putString(SettingUtil.DATA_USERNAME, "");
                edit.putString(SettingUtil.DATA_PASSWORD, "");
                edit.putBoolean(SettingUtil.KEY_REMEMBER_PASSWORD, false);
                edit.putBoolean(SettingUtil.KEY_AUTHOR_LOGIN, false);
                edit.apply();

                LoginActivity.navigateToLoginActivity(getContext());
                getActivity().finish();
          }
        });

        /** 修改密码 */
        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.startActivity(getContext());
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
