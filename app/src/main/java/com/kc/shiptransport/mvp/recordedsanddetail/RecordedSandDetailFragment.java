package com.kc.shiptransport.mvp.recordedsanddetail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.recordedsand.RecordedSandActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
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
    @BindView(R.id.sp_receive_ship)
    Spinner spReceiveShip;
    @BindView(R.id.et_actual_amount_sand)
    EditText etActualAmountSand;
    @BindView(R.id.tv_receive_ship)
    TextView tvReceiveShip;
    Unbinder unbinder;
    @BindView(R.id.rl_cb)
    RelativeLayout rlCb;
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

        /** itemID是进场计划ID */
        presenter.getShip(activity.itemID);

        /** 判断是否修改 */
        if (activity.type == SettingUtil.TYPE_UPDATE_RECORDED) {
            /** 修改数据, itemID是条目ID */
            presenter.getDetail(activity.recordedID);
        }
        return view;
    }

    public void initListener() {
        /** 判断是否可用修改 */
        if (activity.isReadOnly) {
            // 不能修改
        } else {
            // 可以修改
            // 开始时间
            rlRecordStartTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        CalendarUtil.showTimePickerDialog(getContext(), tvStartTime, new OnTimePickerSureClickListener() {
                            @Override
                            public void onSure(String str) {

                            }
                        }, false);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

            // 结束时间
            rlRecordEndTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        CalendarUtil.showTimePickerDialog(getContext(), tvEndTime, new OnTimePickerSureClickListener() {
                            @Override
                            public void onSure(String str) {

                            }
                        }, false);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

            // 提交
            btnCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2017/6/27
                    if (activity.type == SettingUtil.TYPE_UPDATE_RECORDED) {
                        /** 更新 */
                        activity.showDailog("修改", "真的要修改过砂记录吗", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                commit();
                            }
                        });
                    } else {
                        /** 新增 */
                        commit();
                    }

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


        // 返回
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


    }

    /**
     * 提交过砂记录
     */
    private void commit() {
        if (spinner_position == 0 ||
                TextUtils.isEmpty(tvStartTime.getText().toString()) ||
                TextUtils.isEmpty(tvEndTime.getText().toString()) ||
                etActualAmountSand.getText().toString().equals(HINT_TAG)) {

            Toast.makeText(activity, "请填写开始时间与结束时间", Toast.LENGTH_SHORT).show();

        } else {
            // 数据都填写才能提交
            final RecordedSandUpdataBean bean = new RecordedSandUpdataBean();

            /** 判断是新增还是修改 条目编号 */
            if (activity.type == SettingUtil.TYPE_UPDATE_RECORDED) {
                bean.setItemID(activity.recordedID);
            } else {
                bean.setItemID(0);
            }

            bean.setSubcontractorInterimApproachPlanID(recordList.getItemID());

            // 供砂船
            bean.setSandHandlingShipID(recordList.getShipAccount());
            bean.setSandHandlingShipName(recordList.getShipName());

            // 受砂船
            List<ConstructionBoat> boats = DataSupport.findAll(ConstructionBoat.class);
            bean.setConstructionShipID(boats.get(spinner_position - 1).getShipNum());
            bean.setConstructionShipName(boats.get(spinner_position - 1).getShipName());

            bean.setStartTime(tvStartTime.getText().toString());

            bean.setEndTime(tvEndTime.getText().toString());
            String before1 = etBefore1.getText().toString();
            String before2 = etBefore2.getText().toString();
            String before3 = etBefore3.getText().toString();
            String before4 = etBefore4.getText().toString();
            bean.setBeforeOverSandDraft1(Float.valueOf(TextUtils.isEmpty(before1) ? "0" : before1));
            bean.setBeforeOverSandDraft2(Float.valueOf(TextUtils.isEmpty(before2) ? "0" : before2));
            bean.setBeforeOverSandDraft3(Float.valueOf(TextUtils.isEmpty(before3) ? "0" : before3));
            bean.setBeforeOverSandDraft4(Float.valueOf(TextUtils.isEmpty(before4) ? "0" : before4));


            String after1 = etAfter1.getText().toString();
            String after2 = etAfter2.getText().toString();
            String after3 = etAfter3.getText().toString();
            String after4 = etAfter4.getText().toString();
            bean.setAfterOverSandDraft1(Float.valueOf(TextUtils.isEmpty(after1) ? "0" : after1));
            bean.setAfterOverSandDraft2(Float.valueOf(TextUtils.isEmpty(after2) ? "0" : after2));
            bean.setAfterOverSandDraft3(Float.valueOf(TextUtils.isEmpty(after3) ? "0" : after3));
            bean.setAfterOverSandDraft4(Float.valueOf(TextUtils.isEmpty(after4) ? "0" : after4));

            String amountSand = etActualAmountSand.getText().toString();
            bean.setActualAmountOfSand(Float.valueOf(TextUtils.isEmpty(amountSand) ? "0" : amountSand));

            bean.setIsFinish(IsFinish);

            // 当前登录用户
            List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
            bean.setCreator(all.get(0).getSubcontractorAccount());


            /** 判断是否结束过砂, 弹出相应的提示 */
            if (IsFinish == 1) {
                activity.showDailog("结束过砂", "请确认该砂船已全部卸砂完成，提交后不可修改！", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.commit(bean);
                    }
                });
            } else {
                presenter.commit(bean);
            }
        }
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (RecordedSandDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.setTitle(R.string.recorded_title);

        /** 根据不同的类型, 改变按钮的描述 */
        if (activity.type == SettingUtil.TYPE_UPDATE_RECORDED) {
            btnCommit.setText(R.string.btn_update);
        } else {
            btnCommit.setText(R.string.btn_commit);
        }

        /** 判断是否可以修改 */
        if (activity.isReadOnly) {
            // 不可修改
            tvReceiveShip.setVisibility(View.VISIBLE);
            spReceiveShip.setVisibility(View.GONE);
            btnCommit.setVisibility(View.GONE);
            btnReturn.setVisibility(View.VISIBLE);
            rlCb.setVisibility(View.GONE);
            etAfter1.setFocusable(false);
            etAfter1.setFocusableInTouchMode(false);

            etAfter2.setFocusable(false);
            etAfter2.setFocusableInTouchMode(false);

            etAfter3.setFocusable(false);
            etAfter3.setFocusableInTouchMode(false);

            etAfter4.setFocusable(false);
            etAfter4.setFocusableInTouchMode(false);

            etBefore1.setFocusable(false);
            etBefore1.setFocusableInTouchMode(false);

            etBefore2.setFocusable(false);
            etBefore2.setFocusableInTouchMode(false);

            etBefore3.setFocusable(false);
            etBefore3.setFocusableInTouchMode(false);

            etBefore4.setFocusable(false);
            etBefore4.setFocusableInTouchMode(false);

            etActualAmountSand.setFocusable(false);
            etActualAmountSand.setFocusableInTouchMode(false);

            radiobtnFinishRecorde.setClickable(false);
        } else {
            // 可以修改
        }
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

        // 点击后, 筛选供应商的数据
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
            } else {
                btnCommit.setVisibility(View.VISIBLE);
                btnReturn.setVisibility(View.GONE);
            }

            /** 判断是否结束过砂, 如果结束过砂, 跳转到进场计划界面 */
            if (IsFinish == 1) {
                RecordedSandActivity.startActivity(getContext());
                activity.finish();
            }
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), "提交失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 回显需要修改的数据
     *
     * @param bean
     */
    @Override
    public void showDetail(RecordedSandShowList bean) {
        // 供砂船
        String sandHandlingShipName = bean.getSandHandlingShipName();
        // 施工船
        String constructionShipID = bean.getConstructionShipID();
        String constructionShipName = bean.getConstructionShipName();
        // 开始时间
        String startTime = bean.getStartTime();
        // 结束时间
        String endTime = bean.getEndTime();
        // 过砂前吃水
        float beforeOverSandDraft1 = bean.getBeforeOverSandDraft1();
        float beforeOverSandDraft2 = bean.getBeforeOverSandDraft2();
        float beforeOverSandDraft3 = bean.getBeforeOverSandDraft3();
        float beforeOverSandDraft4 = bean.getBeforeOverSandDraft4();
        // 过砂后吃水
        float afterOverSandDraft1 = bean.getAfterOverSandDraft1();
        float afterOverSandDraft2 = bean.getAfterOverSandDraft2();
        float afterOverSandDraft3 = bean.getAfterOverSandDraft3();
        float afterOverSandDraft4 = bean.getAfterOverSandDraft4();
        // 实际过砂量
        float actualAmountOfSand = bean.getActualAmountOfSand();

        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);

        tvReceiveShip.setText(constructionShipName);

        etBefore1.setText(String.valueOf(beforeOverSandDraft1));
        etBefore2.setText(String.valueOf(beforeOverSandDraft2));
        etBefore3.setText(String.valueOf(beforeOverSandDraft3));
        etBefore4.setText(String.valueOf(beforeOverSandDraft4));

        etAfter1.setText(String.valueOf(afterOverSandDraft1));
        etAfter2.setText(String.valueOf(afterOverSandDraft2));
        etAfter3.setText(String.valueOf(afterOverSandDraft3));
        etAfter4.setText(String.valueOf(afterOverSandDraft4));

        etActualAmountSand.setText(String.valueOf(actualAmountOfSand));

        ConstructionBoat boat = DataSupport.where("ShipNum = ?", constructionShipID).find(ConstructionBoat.class).get(0);

        // 回显选择
        spinner_position = boat.getPosition();

        spReceiveShip.setSelection(spinner_position);
    }
}
