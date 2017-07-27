package com.kc.shiptransport.mvp.analysisdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.analysis.AnalysisDetail;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/7/27  13:43
 * @desc ${TODD}
 */

public class AnalysisDetailFragment extends Fragment implements AnalysisDetailContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_plan)
    ImageView ivPlan;
    @BindView(R.id.tv_sub_name)
    TextView tvSubName;
    @BindView(R.id.tv_ship_name)
    TextView tvShipName;
    @BindView(R.id.tv_ship_type)
    TextView tvShipType;
    @BindView(R.id.tv_plan_time)
    TextView tvPlanTime;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_water)
    TextView tvWater;
    @BindView(R.id.tv_mmsi)
    TextView tvMmsi;
    @BindView(R.id.tv_sand_count)
    TextView tvSandCount;
    @BindView(R.id.ll_plan)
    LinearLayout llPlan;
    @BindView(R.id.iv_info)
    ImageView ivInfo;
    @BindView(R.id.tv_captain_name)
    TextView tvCaptainName;
    @BindView(R.id.tv_captain_phone)
    TextView tvCaptainPhone;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_load_time)
    TextView tvLoadTime;
    @BindView(R.id.tv_AIS_MMIS_NUM)
    TextView tvAISMMISNUM;
    @BindView(R.id.tv_com_num)
    TextView tvComNum;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_weight_quan)
    TextView tvWeightQuan;
    @BindView(R.id.tv_store_location)
    TextView tvStoreLocation;
    @BindView(R.id.tv_leave_store_time)
    TextView tvLeaveStoreTime;
    @BindView(R.id.tv_clear_time)
    TextView tvClearTime;
    @BindView(R.id.tv_arrive_anchor_time)
    TextView tvArriveAnchorTime;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.tv_receiver)
    TextView tvReceiver;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.iv_scanner)
    ImageView ivScanner;
    @BindView(R.id.recycler_view_scanner)
    RecyclerView recyclerViewScanner;
    @BindView(R.id.ll_scanner)
    LinearLayout llScanner;
    @BindView(R.id.iv_acceptance)
    ImageView ivAcceptance;
    @BindView(R.id.tv_acceptance_time)
    TextView tvAcceptanceTime;
    @BindView(R.id.tv_ship_code)
    TextView tvShipCode;
    @BindView(R.id.tv_material_complete)
    TextView tvMaterialComplete;
    @BindView(R.id.tv_material_timely)
    TextView tvMaterialTimely;
    @BindView(R.id.ll_acceptance)
    LinearLayout llAcceptance;
    @BindView(R.id.iv_amount)
    ImageView ivAmount;
    @BindView(R.id.tv_capacity)
    TextView tvCapacity;
    @BindView(R.id.tv_deck)
    TextView tvDeck;
    @BindView(R.id.tv_deduction)
    TextView tvDeduction;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.tv_amount_time)
    TextView tvAmountTime;
    @BindView(R.id.recycler_view_amount)
    RecyclerView recyclerViewAmount;
    @BindView(R.id.ll_amount)
    LinearLayout llAmount;
    @BindView(R.id.iv_sand)
    ImageView ivSand;
    @BindView(R.id.tv_sand_time)
    TextView tvSandTime;
    @BindView(R.id.recycler_view_sand)
    RecyclerView recyclerViewSand;
    @BindView(R.id.ll_sand)
    LinearLayout llSand;
    @BindView(R.id.iv_sample)
    ImageView ivSample;
    @BindView(R.id.tv_sample_time)
    TextView tvSampleTime;
    @BindView(R.id.tv_batch)
    TextView tvBatch;
    @BindView(R.id.recycler_view_sample)
    RecyclerView recyclerViewSample;
    @BindView(R.id.ll_sample)
    LinearLayout llSample;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.recycler_view_record)
    RecyclerView recyclerViewRecord;
    @BindView(R.id.ll_record)
    LinearLayout llRecord;
    @BindView(R.id.iv_exit)
    ImageView ivExit;
    @BindView(R.id.tv_exit_time)
    TextView tvExitTime;
    @BindView(R.id.tv_remnant_amount)
    TextView tvRemnantAmount;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.ll_exit)
    LinearLayout llExit;
    Unbinder unbinder;
    private AnalysisDetailActivity activity;
    private AnalysisDetailContract.Presenter presenter;

    /**
     * 记录是否显示item
     */
    private boolean isPlanShow = true;
    private boolean isInfoShow = true;
    private boolean isScanShow = true;
    private boolean isAcceShow = true;
    private boolean isAmountShow = true;
    private boolean isSandShow = true;
    private boolean isSampleShow = true;
    private boolean isRecordShow = true;
    private boolean isExitShow = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // TODO
        presenter.getDetail(activity.itemID);
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (AnalysisDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(R.string.title_analysis_detail);
    }

    /**
     * 改变显示状态
     * @param isShow
     * @param view
     * @param iv
     */
    public void changeShow(boolean isShow, View view, ImageView iv) {
        view.setVisibility(isShow ? View.VISIBLE : View.GONE);
        iv.setImageResource(isShow ? R.mipmap.icon_show : R.mipmap.icon_hide);
    }

    @Override
    public void initListener() {
        /** 供砂计划 */
        ivPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPlanShow = !isPlanShow;
                changeShow(isPlanShow, llPlan, ivPlan);
            }
        });

        /** 信息完善 */
        ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInfoShow = !isInfoShow;
                changeShow(isInfoShow, llInfo, ivInfo);
            }
        });

        /** 进场材料 */
        ivScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isScanShow = !isScanShow;
                changeShow(isScanShow, llScanner, ivScanner);
            }
        });

        /** 验收 */
        ivAcceptance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAcceShow = !isAcceShow;
                changeShow(isAcceShow, llAcceptance, ivAcceptance);
            }
        });

        /** 量方 */
        ivAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAmountShow = !isAmountShow;
                changeShow(isAmountShow, llAmount, ivAmount);
            }
        });

        /** 验砂 */
        ivSand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSandShow = !isSandShow;
                changeShow(isSandShow, llSand, ivSand);
            }
        });

        /** 取样 */
        ivSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSampleShow = !isSampleShow;
                changeShow(isSampleShow, llSample, ivSample);
            }
        });

        /** 过砂 */
        ivRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRecordShow = !isRecordShow;
                changeShow(isRecordShow, llRecord, ivRecord);
            }
        });

        /** 退场 */
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isExitShow = !isExitShow;
                changeShow(isExitShow, llExit, ivExit);
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
    public void setPresenter(AnalysisDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示数据
     * @param detail
     */
    @Override
    public void showDetail(AnalysisDetail detail) {

    }
}
