package com.kc.shiptransport.mvp.supplydetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/1  11:18
 * @desc ${TODD}
 */

public class SupplyDetailFragemnt extends Fragment implements SupplyDetailContract.View {
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
    @BindView(R.id.ll_supply_detail)
    LinearLayout llSupplyDetail;
    @BindView(R.id.et_ship_volume)
    EditText etShipVolume;
    @BindView(R.id.et_deck_volume)
    EditText etDeckVolume;
    @BindView(R.id.tv_total_volume)
    TextView tvTotalVolume;
    @BindView(R.id.et_supply_time)
    EditText etSupplyTime;
    @BindView(R.id.btn_supply_detail_commit)
    AppCompatButton btnSupplyDetailCommit;
    Unbinder unbinder;
    private SupplyDetailContract.Presenter presenter;
    private SupplyDetailActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supply_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // TODO
        return view;
    }


    @Override
    public void initViews(View view) {
        activity = (SupplyDetailActivity) getActivity();
        // 可以显示menu
        setHasOptionsMenu(true);
        activity.setSupportActionBar(toolbarSupplyDetail);
        activity.getSupportActionBar().setTitle("验方管理(进场)");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListener() {
        /* 提交 */
        btnSupplyDetailCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.commit();
            }
        });

        /* edittext输入监听 */
        etShipVolume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ship = etShipVolume.getText().toString();
                String deck = etDeckVolume.getText().toString();

                presenter.getTotalVolume(ship, deck);
            }
        });

        etDeckVolume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ship = etShipVolume.getText().toString();
                String deck = etDeckVolume.getText().toString();
                presenter.getTotalVolume(ship, deck);
            }
        });
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
    public void setPresenter(SupplyDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示选中任务的详细信息
     */
    @Override
    public void showShipDetail() {

    }

    @Override
    public void showSupplyTime(String currentDate) {
        etSupplyTime.setText(currentDate);
    }

    @Override
    public void showTotalVolume(String volume) {
        tvTotalVolume.setText(volume);
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
}
