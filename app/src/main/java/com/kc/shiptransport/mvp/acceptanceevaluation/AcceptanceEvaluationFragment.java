package com.kc.shiptransport.mvp.acceptanceevaluation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;

/**
 * @author 邱永恒
 * @time 2017/8/7  14:40
 * @desc ${TODD}
 */


public class AcceptanceEvaluationFragment extends Fragment implements AcceptanceEvaluationContract.View{

    private AcceptanceEvaluationActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceptance_evaluation, container, false);
        initViews(view);
        initListener();

        // TODO
        return view;
    }

    @Override
    public void initViews(View view) {
        activity = (AcceptanceEvaluationActivity) getActivity();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void setPresenter(AcceptanceEvaluationContract.Presenter presenter) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void showError(String msg) {

    }
}
