package com.kc.shiptransport.mvp.exitapplicationassessor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;

/**
 * @author 邱永恒
 * @time 2017/8/31  15:46
 * @desc ${TODD}
 */

public class ExitApplicationAssessorFragment extends Fragment implements ExitApplicationAssessorContract.View{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exit_application_assessor, container, false);
        initViews(view);
        initListener();

        return view;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void setPresenter(ExitApplicationAssessorContract.Presenter presenter) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void showError(String msg) {

    }
}
