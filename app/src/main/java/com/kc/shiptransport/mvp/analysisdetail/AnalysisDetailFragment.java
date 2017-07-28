package com.kc.shiptransport.mvp.analysisdetail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.analysis.AnalysisDetail;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.view.actiivty.ImageActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

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
     *
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
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中", new OnDailogCancleClickListener() {
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
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示数据
     *
     * @param detail
     */
    @Override
    public void showDetail(AnalysisDetail detail) {
        Toast.makeText(getContext(), "获取数据成功", Toast.LENGTH_SHORT).show();
        /** 供砂计划 */
        // 分包商信息
        String subcontractorName = detail.getSubcontractorName();
        // 供砂船舶
        String shipName = detail.getShipName();
        // 船舶类型
        String type = detail.getShipType();
        // 计划日期
        String planDay = detail.getPlanDay();
        // 重量
        String deadweightTon = detail.getDeadweightTon();
        // 最大吃水
        String maxTakeInWater = detail.getMaxTakeInWater();
        // MMSI
        String mmsi = detail.getMMSI();
        // 供砂量
        String sandSupplyCount = detail.getSandSupplyCount();

        tvSubName.setText(TextUtils.isEmpty(subcontractorName) ? "" : subcontractorName);
        tvShipName.setText(TextUtils.isEmpty(shipName) ? "" : shipName);
        tvShipType.setText(TextUtils.isEmpty(type) ? "" : type);
        tvPlanTime.setText(TextUtils.isEmpty(planDay) ? "" : planDay);
        tvWeight.setText(TextUtils.isEmpty(deadweightTon) ? "" : deadweightTon);
        tvWater.setText(TextUtils.isEmpty(maxTakeInWater) ? "" : maxTakeInWater);
        tvMmsi.setText(TextUtils.isEmpty(mmsi) ? "" : mmsi);
        tvSandCount.setText(TextUtils.isEmpty(sandSupplyCount) ? "" : sandSupplyCount);

        /** 信息完善阶段 */
        AnalysisDetail.PerfectBoatRecordListBean perfectBoatRecordList = detail.getPerfectBoatRecordList();
        // 船长姓名
        String captain = perfectBoatRecordList.getCaptain();
        // 船长电话
        String captainPhone = perfectBoatRecordList.getCaptainPhone();
        // 石场名称
        String storeName = perfectBoatRecordList.getStoreName();
        // 装仓时间
        String loadingDate = perfectBoatRecordList.getLoadingDate();
        // AIS MMIS NUM
        String ais_mmsi_num = perfectBoatRecordList.getAIS_MMSI_Num();
        // 隔舱数量
        String compartmentQuantity = perfectBoatRecordList.getCompartmentQuantity();
        // 货物名称
        String goodsName = perfectBoatRecordList.getGoodsName();
        // 载重量
        String deadweightTons = perfectBoatRecordList.getDeadweightTons();
        // 洗石场所在地
        String washStoreAddress = perfectBoatRecordList.getWashStoreAddress();
        // 离开石场时间
        String leaveStoreTime = perfectBoatRecordList.getLeaveStoreTime();
        // 清关结束时间
        String clearanceEndTime = perfectBoatRecordList.getClearanceEndTime();
        // 到达锚地时间
        String arrivaOfAnchorageTime = perfectBoatRecordList.getArrivaOfAnchorageTime();
        // 材料分类
        String materialClassification = perfectBoatRecordList.getMaterialClassification();
        // 供货方
        final String receiver = perfectBoatRecordList.getReceiver();

        tvCaptainName.setText(TextUtils.isEmpty(captain) ? "" : captain);
        tvCaptainPhone.setText(TextUtils.isEmpty(captainPhone) ? "" : captainPhone);
        tvStoreName.setText(TextUtils.isEmpty(storeName) ? "" : storeName);
        tvLoadTime.setText(TextUtils.isEmpty(loadingDate) ? "" : loadingDate);
        tvAISMMISNUM.setText(TextUtils.isEmpty(ais_mmsi_num) ? "" : ais_mmsi_num);
        tvComNum.setText(TextUtils.isEmpty(compartmentQuantity) ? "" : compartmentQuantity);
        tvGoodsName.setText(TextUtils.isEmpty(goodsName) ? "" : goodsName);
        tvWeightQuan.setText(TextUtils.isEmpty(deadweightTon) ? "" : deadweightTons);
        tvStoreLocation.setText(TextUtils.isEmpty(washStoreAddress) ? "" : washStoreAddress);
        tvLeaveStoreTime.setText(TextUtils.isEmpty(leaveStoreTime) ? "" : leaveStoreTime);
        tvClearTime.setText(TextUtils.isEmpty(clearanceEndTime) ? "" : clearanceEndTime);
        tvArriveAnchorTime.setText(TextUtils.isEmpty(arrivaOfAnchorageTime) ? "" : arrivaOfAnchorageTime);
        tvMaterial.setText(TextUtils.isEmpty(materialClassification) ? "" : materialClassification);
        tvReceiver.setText(TextUtils.isEmpty(receiver) ? "" : receiver);

        /** 分包商进场材料 */
        List<AnalysisDetail.PerfectBoatScannerRecordListBean> perfectBoatScannerRecordList = detail.getPerfectBoatScannerRecordList();

        recyclerViewScanner.setLayoutManager(new LinearLayoutManager(getContext()));

        CommonAdapter<AnalysisDetail.PerfectBoatScannerRecordListBean> scannerAdapter = new CommonAdapter<AnalysisDetail.PerfectBoatScannerRecordListBean>(getContext(), R.layout.item_analysis_recycler, perfectBoatScannerRecordList) {
            @Override
            protected void convert(ViewHolder holder, AnalysisDetail.PerfectBoatScannerRecordListBean perfectBoatScannerRecordListBean, int position) {
                holder.setText(R.id.tv_title, perfectBoatScannerRecordListBean.getSubcontractorPerfectBoatScannerAttachmentTypeName());
                RecyclerView recyclerView = holder.getView(R.id.recycler_view);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));


                List<AnalysisDetail.PerfectBoatScannerRecordListBean.PerfectBoatScannerRecordAttachmentListBean> perfectBoatScannerRecordAttachmentList = perfectBoatScannerRecordListBean.getPerfectBoatScannerRecordAttachmentList();
                if (!perfectBoatScannerRecordAttachmentList.isEmpty()) {
                    CommonAdapter<AnalysisDetail.PerfectBoatScannerRecordListBean.PerfectBoatScannerRecordAttachmentListBean> imageAdapter = new CommonAdapter<AnalysisDetail.PerfectBoatScannerRecordListBean.PerfectBoatScannerRecordAttachmentListBean>(getContext(), R.layout.item_imag, perfectBoatScannerRecordAttachmentList) {
                        @Override
                        protected void convert(ViewHolder holder, final AnalysisDetail.PerfectBoatScannerRecordListBean.PerfectBoatScannerRecordAttachmentListBean perfectBoatScannerRecordAttachmentListBean, int position) {
                            ImageView imageView = holder.getView(R.id.iv_image);
                            RxGalleryUtil.showImage(getContext(), perfectBoatScannerRecordAttachmentListBean.getFilePath(), null, null, imageView);

                            holder.setOnClickListener(R.id.iv_image, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ImageActivity.startActivity(getContext(), perfectBoatScannerRecordAttachmentListBean.getFilePath());
                                }
                            });
                        }
                    };

                    recyclerView.setAdapter(imageAdapter);
                }
            }
        };

        recyclerViewScanner.setAdapter(scannerAdapter);


        /** 预验收管理 */
        AnalysisDetail.PreAcceptanceRecordListBean preAcceptanceRecordList = detail.getPreAcceptanceRecordList();
        // 验收时间
        String preAcceptanceTime = preAcceptanceRecordList.getPreAcceptanceTime();
        // 航次编号
        String shipItemNum = preAcceptanceRecordList.getShipItemNum();
        // 材料完整性
        String materialIntegrity = preAcceptanceRecordList.getMaterialIntegrity();
        // 材料及时性
        String materialTimeliness = preAcceptanceRecordList.getMaterialTimeliness();

        tvAcceptanceTime.setText(TextUtils.isEmpty(preAcceptanceTime) ? "" : preAcceptanceTime);
        tvShipCode.setText(TextUtils.isEmpty(shipItemNum) ? "" : shipItemNum);
        tvMaterialComplete.setText(TextUtils.isEmpty(materialIntegrity) ? "" : materialIntegrity);
        tvMaterialTimely.setText(TextUtils.isEmpty(materialTimeliness) ? "" : materialTimeliness);

        /** 量方管理 */
        AnalysisDetail.TheAmountOfTimeRecordListBean theAmountOfTimeRecordList = detail.getTheAmountOfTimeRecordList();
        // 舱容
        String capacity = theAmountOfTimeRecordList.getCapacity();
        // 甲板方
        String deckGauge = theAmountOfTimeRecordList.getDeckGauge();
        // 扣方
        String deduction = theAmountOfTimeRecordList.getDeduction();
        // 合计方
        String totalAmount = theAmountOfTimeRecordList.getTotalAmount();
        // 量方时间
        String theAmountOfTime = theAmountOfTimeRecordList.getTheAmountOfTime();

        tvCapacity.setText(TextUtils.isEmpty(capacity) ? "" : capacity);
        tvDeck.setText(TextUtils.isEmpty(deckGauge) ? "" : deckGauge);
        tvDeduction.setText(TextUtils.isEmpty(deduction) ? "" : deduction);
        tvTotalAmount.setText(TextUtils.isEmpty(totalAmount) ? "" : totalAmount);
        tvAmountTime.setText(TextUtils.isEmpty(theAmountOfTime) ? "" : theAmountOfTime);


        // 量方图片
        List<AnalysisDetail.TheAmountOfTimeRecordListBean.TheAmountOfSideAttachmentListBean> theAmountOfSideAttachmentList = theAmountOfTimeRecordList.getTheAmountOfSideAttachmentList();
        recyclerViewAmount.setLayoutManager(new GridLayoutManager(getContext(), 3));

        if (!theAmountOfSideAttachmentList.isEmpty()) {
            CommonAdapter<AnalysisDetail.TheAmountOfTimeRecordListBean.TheAmountOfSideAttachmentListBean> amountAdapter = new CommonAdapter<AnalysisDetail.TheAmountOfTimeRecordListBean.TheAmountOfSideAttachmentListBean>(getContext(), R.layout.item_imag, theAmountOfSideAttachmentList) {
                @Override
                protected void convert(ViewHolder holder, final AnalysisDetail.TheAmountOfTimeRecordListBean.TheAmountOfSideAttachmentListBean theAmountOfSideAttachmentListBean, int position) {
                    ImageView imageView = holder.getView(R.id.iv_image);
                    RxGalleryUtil.showImage(getContext(), theAmountOfSideAttachmentListBean.getFilePath(), null, null, imageView);

                    holder.setOnClickListener(R.id.iv_image, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ImageActivity.startActivity(getContext(), theAmountOfSideAttachmentListBean.getFilePath());
                        }
                    });
                }
            };

            recyclerViewAmount.setAdapter(amountAdapter);
        }

        /** 验砂管理 */
        AnalysisDetail.ReceptionSandRecordListBean receptionSandRecordList = detail.getReceptionSandRecordList();
        // 验砂时间
        String receptionSandTime = receptionSandRecordList.getReceptionSandTime();

        tvSandTime.setText(TextUtils.isEmpty(receptionSandTime) ? "" : receptionSandTime);

        // 验砂图片
        List<AnalysisDetail.ReceptionSandRecordListBean.ReceptionSandRecordAttachmentListBean> receptionSandRecordAttachmentList = receptionSandRecordList.getReceptionSandRecordAttachmentList();
        recyclerViewSand.setLayoutManager(new GridLayoutManager(getContext(), 3));

        if (!receptionSandRecordAttachmentList.isEmpty()) {
            CommonAdapter<AnalysisDetail.ReceptionSandRecordListBean.ReceptionSandRecordAttachmentListBean> sandAdapter = new CommonAdapter<AnalysisDetail.ReceptionSandRecordListBean.ReceptionSandRecordAttachmentListBean>(getContext(), R.layout.item_imag, receptionSandRecordAttachmentList) {
                @Override
                protected void convert(ViewHolder holder, final AnalysisDetail.ReceptionSandRecordListBean.ReceptionSandRecordAttachmentListBean receptionSandRecordAttachmentListBean, int position) {
                    ImageView imageView = holder.getView(R.id.iv_image);
                    RxGalleryUtil.showImage(getContext(), receptionSandRecordAttachmentListBean.getFilePath(), null, null, imageView);

                    holder.setOnClickListener(R.id.iv_image, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ImageActivity.startActivity(getContext(), receptionSandRecordAttachmentListBean.getFilePath());
                        }
                    });
                }
            };

            recyclerViewSand.setAdapter(sandAdapter);

        }

        /** 取样管理 */
        List<AnalysisDetail.SandSamplingRecordListBean> sandSamplingRecordList = detail.getSandSamplingRecordList();
        if (!sandSamplingRecordList.isEmpty()) {
            AnalysisDetail.SandSamplingRecordListBean sandSamplingRecordListBean = sandSamplingRecordList.get(0);


            // 取样时间
            String sandSamplingDate = sandSamplingRecordListBean.getSandSamplingDate();
            // BATCH
            String batch = sandSamplingRecordListBean.getBatch();

            tvSampleTime.setText(TextUtils.isEmpty(sandSamplingDate) ? "" : sandSamplingDate);
            tvBatch.setText(TextUtils.isEmpty(batch) ? "" : batch);

            // 取样编号
            List<AnalysisDetail.SandSamplingRecordListBean.SandSamplingNumRecordListBean> sandSamplingNumRecordList = sandSamplingRecordListBean.getSandSamplingNumRecordList();
            recyclerViewSample.setLayoutManager(new LinearLayoutManager(getContext()));

            if (!sandSamplingNumRecordList.isEmpty()) {
                CommonAdapter<AnalysisDetail.SandSamplingRecordListBean.SandSamplingNumRecordListBean> adapter = new CommonAdapter<AnalysisDetail.SandSamplingRecordListBean.SandSamplingNumRecordListBean>(getContext(), R.layout.item_analysis_sample, sandSamplingNumRecordList) {
                    @Override
                    protected void convert(ViewHolder holder, AnalysisDetail.SandSamplingRecordListBean.SandSamplingNumRecordListBean sandSamplingNumRecordListBean, int position) {
                        holder.setText(R.id.tv_batch, sandSamplingNumRecordListBean.getSamplingNum())
                                .setText(R.id.tv_ship_name, sandSamplingNumRecordListBean.getConstructionBoatAccountName());

                        // 取样图片
                        List<AnalysisDetail.SandSamplingRecordListBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean> sandSamplingAttachmentRecordList = sandSamplingNumRecordListBean.getSandSamplingAttachmentRecordList();

                        RecyclerView recyclerView = holder.getView(R.id.recycler_view);

                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

                        if (!sandSamplingAttachmentRecordList.isEmpty()) {
                            CommonAdapter<AnalysisDetail.SandSamplingRecordListBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean> sampleAdapter = new CommonAdapter<AnalysisDetail.SandSamplingRecordListBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean>(getContext(), R.layout.item_imag, sandSamplingAttachmentRecordList) {
                                @Override
                                protected void convert(ViewHolder holder, final AnalysisDetail.SandSamplingRecordListBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean sandSamplingAttachmentRecordListBean, int position) {
                                    ImageView imageView = holder.getView(R.id.iv_image);
                                    RxGalleryUtil.showImage(getContext(), sandSamplingAttachmentRecordListBean.getFilePath(), null, null, imageView);

                                    holder.setOnClickListener(R.id.iv_image, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ImageActivity.startActivity(getContext(), sandSamplingAttachmentRecordListBean.getFilePath());
                                        }
                                    });
                                }
                            };

                            recyclerView.setAdapter(sampleAdapter);
                        }
                    }
                };

                recyclerViewSample.setAdapter(adapter);
            }
        }

        /** 过砂管理 */
        List<AnalysisDetail.OverSandRecordListBean> overSandRecordList = detail.getOverSandRecordList();
        recyclerViewRecord.setLayoutManager(new LinearLayoutManager(getContext()));

        if (!overSandRecordList.isEmpty()) {
            CommonAdapter<AnalysisDetail.OverSandRecordListBean> recordAdapter = new CommonAdapter<AnalysisDetail.OverSandRecordListBean>(getContext(), R.layout.item_analysis_record, overSandRecordList) {
                @Override
                protected void convert(ViewHolder holder, AnalysisDetail.OverSandRecordListBean overSandRecordListBean, int position) {
                    holder.setText(R.id.tv_record_num, "第" + (position + 1) + "条过砂记录")
                            .setText(R.id.tv_sand_reality, overSandRecordListBean.getActualAmountOfSand())
                            .setText(R.id.tv_sand_ship, overSandRecordListBean.getSandHandlingShipName())
                            .setText(R.id.tv_cons_ship, overSandRecordListBean.getConstructionShipName())
                            .setText(R.id.tv_start_time, overSandRecordListBean.getStartTime())
                            .setText(R.id.tv_end_time, overSandRecordListBean.getEndTime())
                            .setText(R.id.tv_before_1, overSandRecordListBean.getBeforeOverSandDraft1())
                            .setText(R.id.tv_before_2, overSandRecordListBean.getBeforeOverSandDraft2())
                            .setText(R.id.tv_before_3, overSandRecordListBean.getBeforeOverSandDraft3())
                            .setText(R.id.tv_before_4, overSandRecordListBean.getBeforeOverSandDraft4())
                            .setText(R.id.tv_after_1, overSandRecordListBean.getAfterOverSandDraft1())
                            .setText(R.id.tv_after_2, overSandRecordListBean.getAfterOverSandDraft2())
                            .setText(R.id.tv_after_3, overSandRecordListBean.getAfterOverSandDraft3())
                            .setText(R.id.tv_after_4, overSandRecordListBean.getAfterOverSandDraft4());
                }
            };

            recyclerViewRecord.setAdapter(recordAdapter);
        }


        /** 退场申请 */
        AnalysisDetail.ExitApplicationRecordListBean exitApplicationRecordList = detail.getExitApplicationRecordList();
        // 退场时间
        String exitTime = exitApplicationRecordList.getExitTime();
        // 残余方量
        String remnantAmount = exitApplicationRecordList.getRemnantAmount();
        // 备注
        String remark = exitApplicationRecordList.getRemark();

        tvExitTime.setText(TextUtils.isEmpty(exitTime) ? "" : exitTime);
        tvRemnantAmount.setText(TextUtils.isEmpty(remnantAmount) ? "" : remnantAmount);
        tvRemark.setText(TextUtils.isEmpty(remark) ? "" : remark);
    }
}
