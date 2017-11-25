package com.kc.shiptransport.mvp.changepassword;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/7/12  16:09
 * @desc ${TODD}
 */

public class ChangePasswordFragment extends Fragment implements ChangePasswordContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_ori_pass)
    AppCompatEditText etOriPass;
    @BindView(R.id.et_change_pass)
    AppCompatEditText etChangePass;
    @BindView(R.id.et_new_pass_again)
    AppCompatEditText etNewPassAgain;
    Unbinder unbinder;
    @BindView(R.id.btn_commit_change)
    Button btnCommitChange;
    private ChangePasswordActivity activity;
    private ChangePasswordContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();


        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (ChangePasswordActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle(R.string.title_change_pass);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initListener() {
        /** 修改密码 */
        btnCommitChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 判断原密码是否为空
                if (TextUtils.isEmpty(etOriPass.getText().toString())) {
                    Toast.makeText(getContext(), "请输入原密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 判断新密码是否为空
                if (TextUtils.isEmpty(etChangePass.getText().toString()) || TextUtils.isEmpty(etNewPassAgain.getText().toString())) {
                    Toast.makeText(getContext(), "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 判断新密码是否一致
                if (!etChangePass.getText().toString().equals(etNewPassAgain.getText().toString())) {
                    Toast.makeText(getContext(), "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }


                User user = DataSupport.findAll(User.class).get(0);
                // 发送网络请求
                presenter.changePassword(user.getUserID(), etOriPass.getText().toString(), etChangePass.getText().toString());
            }
        });
    }

    @Override
    public void setPresenter(ChangePasswordContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("修改中", "修改中", new OnDailogCancleClickListener() {
                @Override
                public void onCancel(ProgressDialog dialog) {
                    presenter.unsubscribe();
                }
            });
        } else {
            activity.hideProgressDailog();
        }
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showChangeResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "修改密码成功", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), "原密码错误, 请重试", Toast.LENGTH_SHORT).show();
        }
    }
}
