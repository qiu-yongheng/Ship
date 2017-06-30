package com.kc.shiptransport.mvp.mine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.login.LoginActivity;
import com.kc.shiptransport.util.SettingUtil;

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
    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());

    }

    public void initListener() {
        btnMineExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
