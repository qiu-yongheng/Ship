package com.kc.shiptransport.mvp.acceptancedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/1  16:09
 * @desc ${TODD}
 */

public class AcceptanceDetailFragment extends Fragment implements AcceptanceDetailContract.View {

    Unbinder unbinder;
    @BindView(R.id.toolbar_supply_detail)
    Toolbar toolbarSupplyDetail;
    @BindView(R.id.tv_ship_name)
    TextView tvShipName;
    @BindView(R.id.tv_ship_id)
    TextView tvShipId;
    @BindView(R.id.tv_subontractor)
    TextView tvSubontractor;
    @BindView(R.id.tv_total_voyage)
    TextView tvTotalVoyage;
    @BindView(R.id.tv_total_value)
    TextView tvTotalValue;
    @BindView(R.id.tv_avg_value)
    TextView tvAvgValue;
    @BindView(R.id.tv_ship_max_draft)
    TextView tvShipMaxDraft;
    @BindView(R.id.tv_ship_current_tide)
    TextView tvShipCurrentTide;
    @BindView(R.id.ll_supply_detail)
    LinearLayout llSupplyDetail;
    @BindView(R.id.tv_acceptance_time)
    TextView tvAcceptanceTime;
    @BindView(R.id.btn_acceptance_cancel)
    AppCompatButton btnAcceptanceCancel;
    @BindView(R.id.btn_acceptance_commit)
    AppCompatButton btnAcceptanceCommit;
    @BindView(R.id.ll)
    LinearLayout ll;
    private AcceptanceDetailContract.Presenter presenter;
    private AcceptanceDetailActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceptance_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // TODO
        return view;
    }

    private void initListener() {
        /* 点击弹出时间选择器 */
        tvAcceptanceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /* 取消 */
        btnAcceptanceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cancle();
            }
        });

        /* 提交 */
        btnAcceptanceCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.commit();
            }
        });
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (AcceptanceDetailActivity) getActivity();
        // 可以显示menu
        setHasOptionsMenu(true);
        activity.setSupportActionBar(toolbarSupplyDetail);
        activity.getSupportActionBar().setTitle("预验收管理");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void setPresenter(AcceptanceDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * 显示选中任务的详细信息
     */
    @Override
    public void showShipDetail() {

    }

    @Override
    public void showAcceptanceTime(String currentDate) {
        tvAcceptanceTime.setText(currentDate);
    }

    @Override
    public void showLoading(boolean active) {
        if (active) {
            activity.showProgressDailog("提交中", "提交中...");
        } else {
            activity.hideProgressDailog();
        }
    }

    @Override
    public void showError() {
        Toast.makeText(activity, "提交失败, 请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
