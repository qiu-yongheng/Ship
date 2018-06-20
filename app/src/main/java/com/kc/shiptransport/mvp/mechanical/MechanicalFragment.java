package com.kc.shiptransport.mvp.mechanical;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/6/10  16:35
 * @desc 机电设备
 */
public class MechanicalFragment extends BaseFragment<MechanicalActivity> implements MechanicalContract.View, View.OnClickListener {

    MechanicalContract.Presenter presenter;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initViews(rootView);
        initListener();
        return rootView;
    }

    @Override
    public int setView() {
        return R.layout.fragment_mechanical;
    }

    @Override
    public int setTitle() {
        return R.string.title_mechanical;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void setPresenter(MechanicalContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void showError(String msg) {
        ToastUtil.tip(getContext(), msg);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unsubscribe();
        }
    }
}
