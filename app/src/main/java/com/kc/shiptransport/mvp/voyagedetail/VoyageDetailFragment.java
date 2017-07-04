package com.kc.shiptransport.mvp.voyagedetail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.StoneSource;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.db.voyage.PerfectBoatRecordInfo;
import com.kc.shiptransport.db.voyage.WashStoneSource;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.view.actiivty.InputActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/13  17:14
 * @desc ${TODD}
 */

public class VoyageDetailFragment extends Fragment implements VoyageDetailContract.View, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sp_ship_name)
    Spinner spShipName;
    @BindView(R.id.tv_ship_name)
    TextView tvShipName;
    @BindView(R.id.rl_ship_name)
    RelativeLayout rlShipName;
    @BindView(R.id.sp_Captain)
    Spinner spCaptain;
    @BindView(R.id.tv_Captain)
    TextView tvCaptain;
    @BindView(R.id.rl_Captain)
    RelativeLayout rlCaptain;
    @BindView(R.id.sp_CaptainPhone)
    Spinner spCaptainPhone;
    @BindView(R.id.tv_CaptainPhone)
    TextView tvCaptainPhone;
    @BindView(R.id.rl_CaptainPhone)
    RelativeLayout rlCaptainPhone;
    @BindView(R.id.sp_LoadingDate)
    Spinner spLoadingDate;
    @BindView(R.id.tv_LoadingDate)
    TextView tvLoadingDate;
    @BindView(R.id.rl_LoadingDate)
    RelativeLayout rlLoadingDate;
    @BindView(R.id.sp_AIS_MMSI_Num)
    Spinner spAISMMSINum;
    @BindView(R.id.tv_AIS_MMSI_Num)
    TextView tvAISMMSINum;
    @BindView(R.id.rl_AIS_MMSI_Num)
    RelativeLayout rlAISMMSINum;
    @BindView(R.id.sp_CompartmentQuantity)
    Spinner spCompartmentQuantity;
    @BindView(R.id.tv_CompartmentQuantity)
    TextView tvCompartmentQuantity;
    @BindView(R.id.rl_CompartmentQuantity)
    RelativeLayout rlCompartmentQuantity;
    @BindView(R.id.sp_GoodsName)
    Spinner spGoodsName;
    @BindView(R.id.tv_GoodsName)
    TextView tvGoodsName;
    @BindView(R.id.rl_GoodsName)
    RelativeLayout rlGoodsName;
    @BindView(R.id.sp_DeadweightTons)
    Spinner spDeadweightTons;
    @BindView(R.id.tv_DeadweightTons)
    TextView tvDeadweightTons;
    @BindView(R.id.rl_DeadweightTons)
    RelativeLayout rlDeadweightTons;
    @BindView(R.id.sp_StoreID)
    Spinner spStoreID;
    @BindView(R.id.tv_StoreID)
    TextView tvStoreID;
    @BindView(R.id.rl_StoreID)
    RelativeLayout rlStoreID;
    @BindView(R.id.sp_WashStoreAddressID)
    Spinner spWashStoreAddressID;
    @BindView(R.id.tv_WashStoreAddressID)
    TextView tvWashStoreAddressID;
    @BindView(R.id.rl_WashStoreAddressID)
    RelativeLayout rlWashStoreAddressID;
    @BindView(R.id.sp_LeaveStoreTime)
    Spinner spLeaveStoreTime;
    @BindView(R.id.tv_LeaveStoreTime)
    TextView tvLeaveStoreTime;
    @BindView(R.id.rl_LeaveStoreTime)
    RelativeLayout rlLeaveStoreTime;
    @BindView(R.id.sp_ClearanceEndTime)
    Spinner spClearanceEndTime;
    @BindView(R.id.tv_ClearanceEndTime)
    TextView tvClearanceEndTime;
    @BindView(R.id.rl_ClearanceEndTime)
    RelativeLayout rlClearanceEndTime;
    @BindView(R.id.sp_ArrivaOfAnchorageTime)
    Spinner spArrivaOfAnchorageTime;
    @BindView(R.id.tv_ArrivaOfAnchorageTime)
    TextView tvArrivaOfAnchorageTime;
    @BindView(R.id.rl_ArrivaOfAnchorageTime)
    RelativeLayout rlArrivaOfAnchorageTime;
    @BindView(R.id.sp_MaterialClassification)
    Spinner spMaterialClassification;
    @BindView(R.id.tv_MaterialClassification)
    TextView tvMaterialClassification;
    @BindView(R.id.rl_MaterialClassification)
    RelativeLayout rlMaterialClassification;
    @BindView(R.id.sp_Receiver)
    Spinner spReceiver;
    @BindView(R.id.tv_Receiver)
    TextView tvReceiver;
    @BindView(R.id.rl_Receiver)
    RelativeLayout rlReceiver;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_return)
    Button btnReturn;
    private VoyageDetailContract.Presenter presenter;
    private VoyageDetailActivity activity;
    private int itemID;
    private Subcontractor sub;
    private String loadingPlace;
    private final String DEFAULT = "未填写";
    private String loadingDate;
    private String baseNumber;
    private String sourceOfSource;
    private String startLoadingTime;
    private String endLoadingTime;
    private String arrivedAtTheDockTime;
    private String leaveTheDockTime;
    private String arrivaOfAnchorageTime;
    private String clearanceTime;
    private Unbinder unbinder;
    private WeekTask weekTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voyage_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);

        // TODO
        presenter.start(activity.mPosition);
        return view;
    }

    public void initListener() {
        rlShipName.setOnClickListener(this);
        rlCaptain.setOnClickListener(this);
        rlCaptainPhone.setOnClickListener(this);
        rlLoadingDate.setOnClickListener(this);
        rlAISMMSINum.setOnClickListener(this);
        rlCompartmentQuantity.setOnClickListener(this);
        rlGoodsName.setOnClickListener(this);
        rlDeadweightTons.setOnClickListener(this);
        rlStoreID.setOnClickListener(this);
        rlWashStoreAddressID.setOnClickListener(this);
        rlLeaveStoreTime.setOnClickListener(this);
        rlClearanceEndTime.setOnClickListener(this);
        rlArrivaOfAnchorageTime.setOnClickListener(this);
        rlMaterialClassification.setOnClickListener(this);
        rlReceiver.setOnClickListener(this);

        // 提交
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (VoyageDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /** 材料分类 */
        // 适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.voyage_spinner));
        // 设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 加载适配器
        spMaterialClassification.setAdapter(arr_adapter);
        // 根据上一个界面传过来的position设置当前显示的item
        //        mSpMaterialOrdar.setSelection(0);

        // 点击后, 筛选材料分类
//        spMaterialClassification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (perfectBoatRecord != null) {
//                    if (i != 0) {
//                        // 保存材料分类
//                        perfectBoatRecord.setMaterialClassification(getResources().getStringArray(R.array.voyage_spinner)[i]);
//                    }
//                    // 保存材料分类position
//                    perfectBoatRecord.setSp_material_position(i);
//                    perfectBoatRecord.save();
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        /** 料源石场 */
        spStoreID.setVisibility(View.VISIBLE);
        tvStoreID.setVisibility(View.GONE);

        /** 洗石场 */
        spWashStoreAddressID.setVisibility(View.VISIBLE);
        tvWashStoreAddressID.setVisibility(View.GONE);

        /** 材料分类 */
        spMaterialClassification.setVisibility(View.VISIBLE);
        tvMaterialClassification.setVisibility(View.GONE);

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
    public void setPresenter(VoyageDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_ship_name:
                // 船名
                break;
            case R.id.rl_Captain:
                // 船长姓名
                InputActivity.startActivityForResult(activity, "船长姓名", tvCaptain.getText().toString(), 1);
                break;
            case R.id.rl_CaptainPhone:
                // 船长电话
                InputActivity.startActivityForResult(activity, "船长电话", tvCaptainPhone.getText().toString(), 2);
                break;
            case R.id.rl_LoadingDate:
                // 装仓时间
                CalendarUtil.showTimePickerDialog(getContext(), tvLoadingDate, new OnTimePickerSureClickListener() {
                    @Override
                    public void onSure(String str) {
                        // 保存数据
                    }
                });
                break;
            case R.id.rl_AIS_MMSI_Num:
                //
                break;
            case R.id.rl_CompartmentQuantity:
                // 隔舱数量
                InputActivity.startActivityForResult(activity, "隔舱数量", tvCompartmentQuantity.getText().toString(), 5);
                break;
            case R.id.rl_GoodsName:
                // 货物名称
                InputActivity.startActivityForResult(activity, "货物名称", tvGoodsName.getText().toString(), 6);
                break;
            case R.id.rl_DeadweightTons:
                // 载重吨
                InputActivity.startActivityForResult(activity, "载重吨", tvDeadweightTons.getText().toString(), 7);
                break;
            case R.id.rl_StoreID:
                // 料源石场

                break;
            case R.id.rl_WashStoreAddressID:
                // 洗石场

                break;
            case R.id.rl_LeaveStoreTime:
                // 离开石场
                CalendarUtil.showTimePickerDialog(getContext(), tvLeaveStoreTime, new OnTimePickerSureClickListener() {
                    @Override
                    public void onSure(String str) {

                    }
                });
                break;
            case R.id.rl_ClearanceEndTime:
                // 清关时间
                CalendarUtil.showTimePickerDialog(getContext(), tvClearanceEndTime, new OnTimePickerSureClickListener() {
                    @Override
                    public void onSure(String str) {

                    }
                });
                break;
            case R.id.rl_ArrivaOfAnchorageTime:
                // 到达锚地时间
                CalendarUtil.showTimePickerDialog(getContext(), tvArrivaOfAnchorageTime, new OnTimePickerSureClickListener() {
                    @Override
                    public void onSure(String str) {

                    }
                });
                break;
            case R.id.rl_MaterialClassification:
                // 材料分类

                break;
            case R.id.rl_Receiver:
                // 收货方
                InputActivity.startActivityForResult(activity, "收货方", tvReceiver.getText().toString(), 14);
                break;
            case R.id.btn_commit:
                break;
            case R.id.btn_return:
                getActivity().onBackPressed();
                break;
        }
    }

    /**
     * 获取返回的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String str = data.getStringExtra(InputActivity.TAG);
//        if (!TextUtils.isEmpty(str)) {
//            if (perfectBoatRecord == null) {
//                return;
//            }
//            switch (requestCode) {
//                case 1:
//                    break;
//                case 2:
//                    break;
//                case 5:
//                    break;
//                case 6:
//                    break;
//                case 7:
//                    break;
//                case 14:
//                    break;
//            }
//        }
    }

    /**
     * 获取itemID
     *
     * @param weekTask
     */
    @Override
    public void showItemID(WeekTask weekTask) {
        this.weekTask = weekTask;
        this.itemID = weekTask.getItemID();
    }

    @Override
    public void showSubcontractor(Subcontractor sub) {
        this.sub = sub;
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommitResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(activity, "提交成功", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(activity, "提交失败! 请重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoading(boolean isShow) {
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

    /**
     * 显示船名
     *
     * @param name
     */
    @Override
    public void showShipName(String name) {
        activity.getSupportActionBar().setTitle(name);
    }

    /**
     * 回显数据
     *
     * @param perfectBoatRecord
     */
    @Override
    public void showDatas(PerfectBoatRecordInfo perfectBoatRecord, boolean isPerfect) {
//        this.perfectBoatRecord = perfectBoatRecord;

        //        String loadingPlace = perfectBoatRecord.getLoadingPlace();
        //        String loadingDate = perfectBoatRecord.getLoadingDate();
        //        String baseNumber = perfectBoatRecord.getBaseNumber();
        //        String sourceOfSource = perfectBoatRecord.getSourceOfSource();
        //        String startLoadingTime = perfectBoatRecord.getStartLoadingTime();
        //        String endLoadingTime = perfectBoatRecord.getEndLoadingTime();
        //        String arrivedAtTheDockTime = perfectBoatRecord.getArrivedAtTheDockTime();
        //        String leaveTheDockTime = perfectBoatRecord.getLeaveTheDockTime();
        //        String arrivaOfAnchorageTime = perfectBoatRecord.getArrivaOfAnchorageTime();
        //        String clearanceTime = perfectBoatRecord.getClearanceTime();
        //        String materialClassification = perfectBoatRecord.getMaterialClassification();
        //
        //
        //        mTvShipLocation.setText(loadingPlace == null ? "未填写" : loadingPlace);
        //        mTvShipDate.setText(loadingDate == null ? "未填写" : loadingDate);
        //        mTvSampleNum.setText(baseNumber == null ? "未填写" : baseNumber);
        //        //mTvMaterialFrom.setText(sourceOfSource == null ? "未填写" : sourceOfSource);
        //        mTvStartDate.setText(startLoadingTime == null ? "未填写" : startLoadingTime);
        //        mTvEndDate.setText(endLoadingTime == null ? "未填写" : endLoadingTime);
        //        mTvComeDate.setText(arrivedAtTheDockTime == null ? "未填写" : arrivedAtTheDockTime);
        //        mTvExitDate.setText(leaveTheDockTime == null ? "未填写" : leaveTheDockTime);
        //        mTvComeAnchorDate.setText(arrivaOfAnchorageTime == null ? "未填写" : arrivaOfAnchorageTime);
        //        mTvCleanDate.setText(clearanceTime == null ? "未填写" : clearanceTime);
        //        if (!TextUtils.isEmpty(materialClassification) && !materialClassification.equals("请选择材料分类") && isPerfect) {
        //            // 有数据
        //            mSpMaterialOrdar.setVisibility(View.GONE);
        //            tvMaterialOrdar.setVisibility(View.VISIBLE);
        //            tvMaterialOrdar.setText(materialClassification);
        //        } else {
        //            // 空
        //            mSpMaterialOrdar.setVisibility(View.VISIBLE);
        //            tvMaterialOrdar.setVisibility(View.GONE);
        //            mSpMaterialOrdar.setSelection(perfectBoatRecord.getSp_material_position());
        //        }
        //
        //        if (!TextUtils.isEmpty(sourceOfSource) && !sourceOfSource.equals("请选择料源石场") && isPerfect) {
        //            mSpMaterialFrom.setVisibility(View.GONE);
        //            mTvMaterialFrom.setVisibility(View.VISIBLE);
        //            mTvMaterialFrom.setText(sourceOfSource);
        //        } else {
        //            mSpMaterialFrom.setVisibility(View.VISIBLE);
        //            mTvMaterialFrom.setVisibility(View.GONE);
        //            //mSpMaterialFrom.setSelection(perfectBoatRecord.getSp_stone_source_position());
        //        }

        if (isPerfect) {
            // 已完善
            btnReturn.setOnClickListener(this);
            btnCommit.setVisibility(View.GONE);
            btnReturn.setVisibility(View.VISIBLE);
        } else {
            // 未完善
            initListener();
            btnCommit.setVisibility(View.VISIBLE);
            btnReturn.setVisibility(View.GONE);
        }

    }

    /**
     * 获取料源石场数据
     *
     * @param list
     */
    @Override
    public void showStoneSource(List<StoneSource> list) {
        final List<String> dates = new ArrayList<>();
        dates.add("请选择料源石场");

        for (StoneSource bean : list) {
            dates.add(bean.getName());
        }


        // 适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dates);
        // 设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 加载适配器
        spStoreID.setAdapter(arr_adapter);

        // 点击后, 筛选分包商的数据
//        spStoreID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (perfectBoatRecord != null) {
//                    if (i != 0) {
//                        //                                perfectBoatRecord.setSourceOfSource(dates.get(i));
//                        //                                perfectBoatRecord.setSp_stone_source_position(i);
//                        //                                perfectBoatRecord.save();
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        if (perfectBoatRecord != null) {
//            spStoreID.setSelection(perfectBoatRecord.getSp_stone_source_position());
//        }
    }

    /**
     * 显示洗石场
     *
     * @param list
     */
    @Override
    public void showWashStoneSource(List<WashStoneSource> list) {
        final List<String> dates = new ArrayList<>();
        dates.add("请选择洗石场");

        for (WashStoneSource bean : list) {
            dates.add(bean.getWashStoreAddress());
        }

        // 适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dates);
        // 设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 加载适配器
        spWashStoreAddressID.setAdapter(arr_adapter);

        // 点击后, 筛选分包商的数据
//        spWashStoreAddressID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (perfectBoatRecord != null) {
//                    if (i != 0) {
//                        //                        perfectBoatRecord.setSourceOfSource(dates.get(i));
//                        //                        perfectBoatRecord.setSp_stone_source_position(i);
//                        //                        perfectBoatRecord.save();
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        if (perfectBoatRecord != null) {
//            spWashStoreAddressID.setSelection(perfectBoatRecord.getSp_stone_source_position());
//        }
    }

    /**
     * 保存
     */
    @Override
    public void onPause() {
        super.onPause();
    }
}
