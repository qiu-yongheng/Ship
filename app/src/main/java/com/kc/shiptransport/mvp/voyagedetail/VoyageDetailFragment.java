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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.VoyageInfoBean;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.view.actiivty.InputActivity;

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
    @BindView(R.id.rl_ship_location)
    RelativeLayout rlShipLocation;
    @BindView(R.id.rl_ship_date)
    RelativeLayout rlShipDate;
    @BindView(R.id.rl_sample_num)
    RelativeLayout rlSampleNum;
    @BindView(R.id.rl_material_from)
    RelativeLayout rlMaterialFrom;
    @BindView(R.id.rl_start_date)
    RelativeLayout rlStartDate;
    @BindView(R.id.rl_end_date)
    RelativeLayout rlEndDate;
    @BindView(R.id.rl_come_date)
    RelativeLayout rlComeDate;
    @BindView(R.id.rl_exit_date)
    RelativeLayout rlExitDate;
    @BindView(R.id.rl_come_anchor_date)
    RelativeLayout rlComeAnchorDate;
    @BindView(R.id.rl_clean_date)
    RelativeLayout rlCleanDate;
    @BindView(R.id.rl_material_ordar)
    RelativeLayout rlMaterialOrdar;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    Unbinder unbinder;
    @BindView(R.id.tv_ship_date)
    TextView mTvShipDate;
    @BindView(R.id.tv_ship_location)
    TextView mTvShipLocation;
    @BindView(R.id.tv_sample_num)
    TextView mTvSampleNum;
    @BindView(R.id.tv_material_from)
    TextView mTvMaterialFrom;
    @BindView(R.id.tv_start_date)
    TextView mTvStartDate;
    @BindView(R.id.tv_end_date)
    TextView mTvEndDate;
    @BindView(R.id.tv_come_date)
    TextView mTvComeDate;
    @BindView(R.id.tv_exit_date)
    TextView mTvExitDate;
    @BindView(R.id.tv_come_anchor_date)
    TextView mTvComeAnchorDate;
    @BindView(R.id.tv_clean_date)
    TextView mTvCleanDate;
    @BindView(R.id.sp_material_ordar)
    Spinner mSpMaterialOrdar;
    private VoyageDetailContract.Presenter presenter;
    private VoyageDetailActivity activity;
    private String itemID;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String gaga = savedInstanceState.getString("gaga");
            Toast.makeText(activity, gaga, Toast.LENGTH_SHORT).show();
        }
        View view = inflater.inflate(R.layout.fragment_voyage_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // TODO

        return view;
    }

    private void initListener() {
        // 船舶地址
        rlShipLocation.setOnClickListener(this);
        // 装船日期
        rlShipDate.setOnClickListener(this);
        // 基样编号
        rlSampleNum.setOnClickListener(this);
        // 材料来源地
        rlMaterialFrom.setOnClickListener(this);
        // 开始装船时间
        rlStartDate.setOnClickListener(this);
        // 结束装船事件
        rlEndDate.setOnClickListener(this);
        // 抵达码头时间
        rlComeDate.setOnClickListener(this);
        // 离开码头时间
        rlExitDate.setOnClickListener(this);
        // 到达锚地时间
        rlComeAnchorDate.setOnClickListener(this);
        // 清关时间
        rlCleanDate.setOnClickListener(this);
        // 材料分类
        rlMaterialOrdar.setOnClickListener(this);

        // 提交
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (VoyageDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(R.string.title_voyage);





        // 适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.voyage_spinner));
        // 设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 加载适配器
        mSpMaterialOrdar.setAdapter(arr_adapter);
        // 根据上一个界面传过来的position设置当前显示的item
//        mSpMaterialOrdar.setSelection(0);

        // 点击后, 筛选分包商的数据
        mSpMaterialOrdar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // 获取position对应的数据
        presenter.getItemIDForPosition(activity.mPosition);
        presenter.getSubcontractor();
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
            case R.id.rl_ship_location:
                InputActivity.startActivityForResult(activity, getResources().getString(R.string.text_ship_location), mTvShipLocation.getText().toString(), 0);
                break;
            case R.id.rl_ship_date:
                CalendarUtil.showTimePickerDialog(getContext(), mTvShipDate);
                break;
            case R.id.rl_sample_num:
                InputActivity.startActivityForResult(activity, getResources().getString(R.string.text_sample_num), mTvSampleNum.getText().toString(), 2);
                break;
            case R.id.rl_material_from:
                InputActivity.startActivityForResult(activity, getResources().getString(R.string.text_material_from), mTvMaterialFrom.getText().toString(), 3);
                break;
            case R.id.rl_start_date:
                CalendarUtil.showTimePickerDialog(getContext(), mTvStartDate);
                break;
            case R.id.rl_end_date:
                CalendarUtil.showTimePickerDialog(getContext(), mTvEndDate);
                break;
            case R.id.rl_come_date:
                CalendarUtil.showTimePickerDialog(getContext(), mTvComeDate);
                break;
            case R.id.rl_exit_date:
                CalendarUtil.showTimePickerDialog(getContext(), mTvExitDate);
                break;
            case R.id.rl_come_anchor_date:
                CalendarUtil.showTimePickerDialog(getContext(), mTvComeAnchorDate);
                break;
            case R.id.rl_clean_date:
                CalendarUtil.showTimePickerDialog(getContext(), mTvCleanDate);
                break;
            case R.id.rl_material_ordar:
                // TODO 弹出选择框 -> initView()
                break;
            case R.id.btn_commit:
                // 提交
                VoyageInfoBean bean = new VoyageInfoBean();
                bean.setItemID("");
                bean.setSubcontractorInterimApproachPlanID(itemID);
                loadingPlace = mTvShipLocation.getText().toString().trim();
                bean.setLoadingPlace(loadingPlace.equals(DEFAULT) ? "" : loadingPlace);
                loadingDate = mTvShipDate.getText().toString().trim();
                bean.setLoadingDate(loadingDate.equals(DEFAULT) ? "" : loadingDate);
                baseNumber = mTvSampleNum.getText().toString().trim();
                bean.setBaseNumber(baseNumber.equals(DEFAULT) ? "" : baseNumber);
                sourceOfSource = mTvMaterialFrom.getText().toString().trim();
                bean.setSourceOfSource(sourceOfSource.equals(DEFAULT) ? "" : sourceOfSource);
                startLoadingTime = mTvStartDate.getText().toString().trim();
                bean.setStartLoadingTime(startLoadingTime.equals(DEFAULT) ? "" : startLoadingTime);
                endLoadingTime = mTvEndDate.getText().toString().trim();
                bean.setEndLoadingTime(endLoadingTime.equals(DEFAULT) ? "" : endLoadingTime);
                arrivedAtTheDockTime = mTvComeDate.getText().toString().trim();
                bean.setArrivedAtTheDockTime(arrivedAtTheDockTime.equals(DEFAULT) ? "" : arrivedAtTheDockTime);
                leaveTheDockTime = mTvExitDate.getText().toString().trim();
                bean.setLeaveTheDockTime(leaveTheDockTime.equals(DEFAULT) ? "" : leaveTheDockTime);
                arrivaOfAnchorageTime = mTvComeAnchorDate.getText().toString().trim();
                bean.setArrivaOfAnchorageTime(arrivaOfAnchorageTime.equals(DEFAULT) ? "" : arrivaOfAnchorageTime);
                clearanceTime = mTvCleanDate.getText().toString().trim();
                bean.setClearanceTime(clearanceTime.equals(DEFAULT) ? "" : clearanceTime);
                bean.setMaterialClassification(getResources().getStringArray(R.array.voyage_spinner)[mSpMaterialOrdar.getSelectedItemPosition()]);
                bean.setCreator(sub.getSubcontractorAccount());

                presenter.doCommit(bean);
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
        switch (requestCode) {
            case 0:
                mTvShipLocation.setText(str);
                break;
            case 2:
                mTvSampleNum.setText(str);
                break;
            case 3:
                mTvMaterialFrom.setText(str);
                break;

        }
    }

    /**
     * 获取itemID
     * @param itemID
     */
    @Override
    public void showItemID(String itemID) {
        this.itemID = itemID;
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
     * 保存
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("gaga", "gaga");
    }
}
