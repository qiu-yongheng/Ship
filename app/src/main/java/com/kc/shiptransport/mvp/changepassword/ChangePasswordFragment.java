package com.kc.shiptransport.mvp.changepassword;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;

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
        activity = (ChangePasswordActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(R.string.title_change_pass);
    }

    @Override
    public void initListener() {
        /** 修改密码 */
        btnCommitChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                public void onCancle(ProgressDialog dialog) {
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
}
