package com.kc.shiptransport.mvp.supplydetail;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    @BindView(R.id.tv_supply_time)
    TextView tvSupplyTime;
    @BindView(R.id.et_batch)
    EditText etBatch;
    @BindView(R.id.btn_acceptance_cancel)
    AppCompatButton btnAcceptanceCancel;
    @BindView(R.id.btn_acceptance_commit)
    AppCompatButton btnAcceptanceCommit;
    @BindView(R.id.ll)
    LinearLayout ll;
    private Unbinder unbinder;
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
        presenter.start(activity.itemID);
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

        if (activity.isSupply) {
            // 禁止软键盘
            etShipVolume.setInputType(InputType.TYPE_NULL);
            etDeckVolume.setInputType(InputType.TYPE_NULL);
            btnAcceptanceCommit.setVisibility(View.GONE);
            btnAcceptanceCancel.setText(R.string.btn_return);

        } else {
            // 开启软键盘
            etShipVolume.setInputType(InputType.TYPE_CLASS_TEXT);
            etDeckVolume.setInputType(InputType.TYPE_CLASS_TEXT);
            btnAcceptanceCommit.setVisibility(View.VISIBLE);
            btnAcceptanceCancel.setText(R.string.btn_cancle);
        }
    }

    private void initListener() {
        /* 提交 */
        btnAcceptanceCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String batch = etBatch.getText().toString().trim();
                if (batch.equals("")) {
                    Toast.makeText(activity, "BATCH不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.commit(activity.itemID, tvSupplyTime.getText().toString(), batch);
                }
            }
        });

        /* 返回 */
        btnAcceptanceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
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

        /* 点击弹出时间选择器 */
        tvSupplyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        Calendar instance = Calendar.getInstance();
                        instance.set(Calendar.HOUR_OF_DAY, hour);
                        instance.set(Calendar.MINUTE, minute);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String format = df.format(instance.getTime());
                        tvSupplyTime.setText(format);
                    }
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), DateFormat.is24HourFormat(activity));

                timePickerDialog.show();
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
     *
     * @param value
     */
    @Override
    public void showShipDetail(Acceptance value) {
        String shipItemNum = value.getShipItemNum();
        tvShipName.setText(value.getShipName());
        tvShipId.setText("船次: " + (shipItemNum == null ? "" : shipItemNum));
        tvSubontractor.setText("供应商: " + value.getSubcontractorName());
        tvTotalVoyage.setText("累计完成航次: " + value.getTotalCompleteRide() + "次");
        tvTotalValue.setText("累计完成方量: " + value.getTotalCompleteSquare() + "㎡");
        tvAvgValue.setText("平均航次方量: " + value.getAvgSquare() + "㎡");

        etShipVolume.setText(value.getCapacity());
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
    public void showError() {
        Toast.makeText(activity, "数据获取失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommitError() {
        Toast.makeText(activity, "提交失败, 请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommitResult(boolean active) {
        if (active) {
            Toast.makeText(activity, "提交成功!", Toast.LENGTH_SHORT).show();
            btnAcceptanceCancel.setText(R.string.btn_return);
        } else {
            Toast.makeText(activity, "提交失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }


}
