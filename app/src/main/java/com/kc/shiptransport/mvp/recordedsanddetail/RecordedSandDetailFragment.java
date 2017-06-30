package com.kc.shiptransport.mvp.recordedsanddetail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.RecordedSandUpdataBean;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.RecordList;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.util.CalendarUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/19  11:28
 * @desc ${TODD}
 */

public class RecordedSandDetailFragment extends Fragment implements RecordedSandDetailContract.View {

    private static final String HINT_TAG = "请填写";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_supply_ship)
    TextView tvSupplyShip;
    @BindView(R.id.rl_record_supply_ship)
    RelativeLayout rlRecordSupplyShip;
    @BindView(R.id.rl_record_receive_ship)
    RelativeLayout rlRecordReceiveShip;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.rl_record_start_time)
    RelativeLayout rlRecordStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.rl_record_end_time)
    RelativeLayout rlRecordEndTime;
    @BindView(R.id.et_before_1)
    EditText etBefore1;
    @BindView(R.id.et_before_2)
    EditText etBefore2;
    @BindView(R.id.et_before_3)
    EditText etBefore3;
    @BindView(R.id.et_before_4)
    EditText etBefore4;
    @BindView(R.id.et_after_1)
    EditText etAfter1;
    @BindView(R.id.et_after_2)
    EditText etAfter2;
    @BindView(R.id.et_after_3)
    EditText etAfter3;
    @BindView(R.id.et_after_4)
    EditText etAfter4;
    @BindView(R.id.radiobtn_finish_recorde)
    CheckBox radiobtnFinishRecorde;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_return)
    Button btnReturn;
    Unbinder unbinder;
    @BindView(R.id.sp_receive_ship)
    Spinner spReceiveShip;
    @BindView(R.id.et_actual_amount_sand)
    EditText etActualAmountSand;
    private RecordedSandDetailActivity activity;
    private RecordedSandDetailContract.Presenter presenter;
    private int IsFinish = 0;
    private int spinner_position;
    private RecordList recordList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recordedsand_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        presenter.subscribe();
        presenter.getShip(activity.position);
        return view;
    }

    public void initListener() {
        // 开始时间
        rlRecordStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showTimePickerDialog(getContext(), tvStartTime, new OnTimePickerSureClickListener() {
                    @Override
                    public void onSure(String str) {

                    }
                });
            }
        });

        // 结束时间
        rlRecordEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showTimePickerDialog(getContext(), tvEndTime, new OnTimePickerSureClickListener() {
                    @Override
                    public void onSure(String str) {

                    }
                });
            }
        });

        // 提交
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/6/27
                if (spinner_position == 0 ||
                        tvStartTime.getText().equals(HINT_TAG) ||
                        tvEndTime.getText().equals(HINT_TAG) ||
                        TextUtils.isEmpty(etBefore1.getText()) ||
                        etBefore1.getText().toString().equals(HINT_TAG) ||
                        TextUtils.isEmpty(etBefore2.getText()) ||
                        etBefore2.getText().toString().equals(HINT_TAG) ||
                        TextUtils.isEmpty(etBefore3.getText()) ||
                        etBefore3.getText().toString().equals(HINT_TAG) ||
                        TextUtils.isEmpty(etBefore4.getText()) ||
                        etBefore4.getText().toString().equals(HINT_TAG) ||
                        TextUtils.isEmpty(etAfter1.getText()) ||
                        etAfter1.getText().toString().equals(HINT_TAG) ||
                        TextUtils.isEmpty(etAfter2.getText()) ||
                        etAfter2.getText().toString().equals(HINT_TAG) ||
                        TextUtils.isEmpty(etAfter3.getText()) ||
                        etAfter3.getText().toString().equals(HINT_TAG) ||
                        TextUtils.isEmpty(etAfter4.getText()) ||
                        etAfter4.getText().toString().equals(HINT_TAG) ||
                        TextUtils.isEmpty(etActualAmountSand.getText()) ||
                        etActualAmountSand.getText().toString().equals(HINT_TAG)) {
                    Toast.makeText(activity, "还有数据未填写", Toast.LENGTH_SHORT).show();

                } else {
                    // 数据都填写才能提交
                    RecordedSandUpdataBean bean = new RecordedSandUpdataBean();

                    bean.setItemID(0);

                    bean.setSubcontractorInterimApproachPlanID(recordList.getItemID());

                    bean.setSandHandlingShipID(recordList.getShipAccount());


                    List<ConstructionBoat> boats = DataSupport.findAll(ConstructionBoat.class);
                    bean.setConstructionShipID(boats.get(spinner_position - 1).getShipNum());

                    bean.setStartTime(tvStartTime.getText().toString());

                    bean.setEndTime(tvEndTime.getText().toString());

                    bean.setBeforeOverSandDraft1(Float.valueOf(etBefore1.getText().toString()));
                    bean.setBeforeOverSandDraft2(Float.valueOf(etBefore2.getText().toString()));
                    bean.setBeforeOverSandDraft3(Float.valueOf(etBefore3.getText().toString()));
                    bean.setBeforeOverSandDraft4(Float.valueOf(etBefore4.getText().toString()));

                    bean.setAfterOverSandDraft1(Float.valueOf(etAfter1.getText().toString()));
                    bean.setAfterOverSandDraft2(Float.valueOf(etAfter2.getText().toString()));
                    bean.setAfterOverSandDraft3(Float.valueOf(etAfter3.getText().toString()));
                    bean.setAfterOverSandDraft4(Float.valueOf(etAfter4.getText().toString()));

                    bean.setActualAmountOfSand(Float.valueOf(etActualAmountSand.getText().toString()));

                    bean.setIsFinish(IsFinish);

                    // 当前登录用户
                    List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
                    bean.setCreator(all.get(0).getSubcontractorAccount());

                    presenter.commit(bean);
                }

            }
        });

        // 返回
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        // 监听过砂完成
        radiobtnFinishRecorde.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // 过砂完成
                    IsFinish = 1;
                } else {
                    // 过砂未完成
                    IsFinish = 0;
                }
            }
        });
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (RecordedSandDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle(R.string.recorded_title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(RecordedSandDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示spinner
     *
     * @param list
     */
    @Override
    public void showSpinner(List<String> list) {
        // 适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        // 设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 加载适配器
        spReceiveShip.setAdapter(arr_adapter);
        // 根据上一个界面传过来的position设置当前显示的item
        //        mSpMaterialOrdar.setSelection(0);

        // 点击后, 筛选分包商的数据
        spReceiveShip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO 保存数据
                spinner_position = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void showShip(RecordList recordList) {
        this.recordList = recordList;
        tvSupplyShip.setText(recordList.getShipName());
    }

    @Override
    public void showLoadding(boolean isShow) {
        if (isShow) {
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
    public void showResult(Boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();

            if (IsFinish == 1) {
                btnCommit.setVisibility(View.GONE);
                btnReturn.setVisibility(View.VISIBLE);
                getActivity().onBackPressed();
            } else {
                btnCommit.setVisibility(View.VISIBLE);
                btnReturn.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(getContext(), "提交失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }
}
