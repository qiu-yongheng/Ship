package com.kc.shiptransport.mvp.amountdetail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
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
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.util.CalendarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:57
 * @desc ${TODD}
 */

public class AmountDetailFragment extends Fragment implements AmountDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.tv_warehouse_space)
    TextView tvWarehouseSpace;
    @BindView(R.id.ll_supply_detail)
    LinearLayout llSupplyDetail;
    @BindView(R.id.et_ship_volume)
    EditText etShipVolume;
    @BindView(R.id.et_deck_volume)
    EditText etDeckVolume;
    @BindView(R.id.tv_total_volume)
    TextView tvTotalVolume;
    @BindView(R.id.tv_supply_time)
    TextView tvSupplyTime;
    @BindView(R.id.btn_cancel)
    AppCompatButton btnCancel;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;
    @BindView(R.id.ll)
    LinearLayout ll;
    Unbinder unbinder;
    private AmountDetailContract.Presenter presenter;
    private AmountDetailActivity activity;
    private String capacity = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amount_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // TODO
        presenter.start(activity.itemID);
        return view;
    }

    private void initListener() {
        // 取消
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               activity.onBackPressed();
            }
        });

        // 提交
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // TODO
                // 量方时间
                String theAmountTime = tvSupplyTime.getText().toString().trim();
                // 甲板方
                String deck = etDeckVolume.getText().toString().trim();
                // 扣方
                String dedu = etShipVolume.getText().toString().trim();

                presenter.commit(activity.itemID, theAmountTime, capacity, deck, dedu);
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

                presenter.getTotalVolume(ship, deck, capacity);
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
                presenter.getTotalVolume(ship, deck, capacity);
            }
        });

        // 选择时间
        tvSupplyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showTimePickerDialog(getContext(), tvSupplyTime);
            }
        });
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (AmountDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle(R.string.title_amount_detail);

        // TODO 根据是否验收, 改变布局
        if (activity.isAmount) {
            // 禁止软键盘
            etShipVolume.setInputType(InputType.TYPE_NULL);
            etDeckVolume.setInputType(InputType.TYPE_NULL);
            btnCommit.setVisibility(View.GONE);
            btnCancel.setText(R.string.btn_return);
        } else {
            // 开启软键盘
            etShipVolume.setInputType(InputType.TYPE_CLASS_TEXT);
            etDeckVolume.setInputType(InputType.TYPE_CLASS_TEXT);
            btnCommit.setVisibility(View.VISIBLE);
            btnCancel.setText(R.string.btn_cancle);
        }
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
    public void setPresenter(AmountDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showShipDetail(Acceptance value) {
        String shipItemNum = value.getShipItemNum();
        capacity = String.valueOf(value.getDefaultCapacity());
        tvShipName.setText(value.getShipName());
        tvShipId.setText("船次: " + (shipItemNum == null ? "" : shipItemNum));
        tvSubontractor.setText("供应商: " + value.getSubcontractorName());
        tvTotalVoyage.setText("累计完成航次: " + value.getTotalCompleteRide() + "次");
        tvTotalValue.setText("累计完成方量: " + value.getTotalCompleteSquare() + "㎡");
        tvAvgValue.setText("平均航次方量: " + value.getAvgSquare() + "㎡");
        tvWarehouseSpace.setText("舱容: " + (capacity == null ? "" : capacity));

        etShipVolume.setText(String.valueOf(value.getDeduction() == null ? 0 : value.getDeduction()));
        etDeckVolume.setText(value.getDeckGauge() == null ? "0" : value.getDeckGauge());
    }

    @Override
    public void showSupplyTime(String currentDate) {
        tvSupplyTime.setText(currentDate);
    }

    @Override
    public void showTotalVolume(String volume) {
        tvTotalVolume.setText(volume);
    }

    @Override
    public void showLoading(boolean active) {
        if (active) {
            activity.showProgressDailog("提交中", "提交中...", new OnDailogCancleClickListener() {
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
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommitResult(boolean active) {
        Toast.makeText(activity, "提交成功!", Toast.LENGTH_SHORT).show();
        btnCancel.setText(R.string.btn_return);
    }
}
