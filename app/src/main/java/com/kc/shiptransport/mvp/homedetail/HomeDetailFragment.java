package com.kc.shiptransport.mvp.homedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.AppList;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.acceptance.AcceptanceActivity;
import com.kc.shiptransport.mvp.amount.AmountActivity;
import com.kc.shiptransport.mvp.analysis.AnalysisActivity;
import com.kc.shiptransport.mvp.attendance.AttendanceActivity;
import com.kc.shiptransport.mvp.attendanceaudit.AttendanceAuditActivity;
import com.kc.shiptransport.mvp.bcf.BCFActivity;
import com.kc.shiptransport.mvp.boatinquire.BoatInquireActivity;
import com.kc.shiptransport.mvp.certificatesupervision.CertificateSupervisionActivity;
import com.kc.shiptransport.mvp.crew.CrewActivity;
import com.kc.shiptransport.mvp.devicerepair.DeviceRepairActivity;
import com.kc.shiptransport.mvp.exitapplication.ExitApplicationActivity;
import com.kc.shiptransport.mvp.exitassessor.ExitAssessorActivity;
import com.kc.shiptransport.mvp.hsecheck.HseCheckActivity;
import com.kc.shiptransport.mvp.hserectification.HseRectificationActivity;
import com.kc.shiptransport.mvp.mechanical.MechanicalActivity;
import com.kc.shiptransport.mvp.plan.PlanActivity;
import com.kc.shiptransport.mvp.recordedsand.RecordedSandActivity;
import com.kc.shiptransport.mvp.sample.SampleActivity;
import com.kc.shiptransport.mvp.scanner.ScannerActivity;
import com.kc.shiptransport.mvp.supply.SupplyActivity;
import com.kc.shiptransport.mvp.todayplan.TodayPlanActivity;
import com.kc.shiptransport.mvp.training.TrainingActivity;
import com.kc.shiptransport.mvp.violationrecords.ViolationRecordsActivity;
import com.kc.shiptransport.mvp.voyageinfo.VoyageInfoActivity;
import com.kc.shiptransport.util.SettingUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 邱永恒
 * @time 2017/6/30 21:46
 * @desc ${TODO}
 */

public class HomeDetailFragment extends Fragment implements HomeDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private HomeDetailContract.Presenter presenter;
    private HomeDetailActivity activity;
    private HomeDetailAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_home_detail, container, false);
        ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        /** 获取列表数据 根据不同的APPPID, 显示二级界面 */
        presenter.getAppList(activity.mAppPID);
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (HomeDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle(R.string.toolbar_title);

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void setPresenter(HomeDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    /**
     * 显示二级界面 , 根据appid设置点击事件
     *
     * @param lists
     */
    @Override
    public void showAppList(List<AppList> lists) {
        if (adapter == null) {
            adapter = new HomeDetailAdapter(getContext(), lists);
            adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    switch (type[0]) {
                        /** AppPID = 2 供砂管理 */
                        case 10:
                            // 供应商进场计划
                            PlanActivity.navigateToPlanActivity(getContext());
                            break;
                        case 11:
                            // 待验收航次
                            AcceptanceActivity.navigateToAcceptanceActivity(getContext());
                            break;
                        case 12:
                            // 待验砂船次
                            SupplyActivity.navigateToSupplyActivity(getContext());
                            break;
                        case 13:
                            // 量方管理
                            AmountActivity.navigateToAmountActivity(getContext());
                            break;
                        case 14:
                            // 供应商航次信息完善
                            VoyageInfoActivity.navigateToVoyageInfoActivity(getContext());
                            break;
                        case 15:
                            // 供应商航次完善扫描件
                            ScannerActivity.navigateToScannerActivity(getContext());
                            break;
                        case 16:
                            // 验砂取样
                            SampleActivity.navigateToSampleActivity(getContext());
                            break;
                        case 17:
                            // 过砂记录
                            RecordedSandActivity.startActivity(getContext());
                            break;
                        case 21:
                            // 退场申请
                            ExitApplicationActivity.startActivity(getContext());
                            break;
                        case 22:
                            // 供砂进度跟踪
                            AnalysisActivity.startActivity(getContext(), SettingUtil.TYPE_ANALYSIS);
                            break;
                        case 23:
                            // 预验收反馈
                            AnalysisActivity.startActivity(getContext(), SettingUtil.TYPE_ACCEPTANCE_EVALUATION);
                            break;
                        case 24:
                            // 供应商排行
                            AnalysisActivity.startActivity(getContext(), SettingUtil.TYPE_ACCEPTANCE_RANK);
                            break;
                        case 25:
                            // 退场审核
                            ExitAssessorActivity.startActivity(getContext());
                            break;
                        case 26:
                            // 退场反馈
                            AnalysisActivity.startActivity(getContext(), SettingUtil.TYPE_EXIT_FEEDBACK);
                            break;
                        case 27:
                            // 明日来船计划
                            AnalysisActivity.startActivity(getContext(), SettingUtil.TYPE_TOMORROW_PLAN);
                            break;
                        case 28:
                            // BCF来船
                            BCFActivity.startActivity(getContext(), 0, true);
                            break;
                        case 29:
                            // 今日计划
                            TodayPlanActivity.startActivity(getContext());
                            break;

                        /** AppPID = 18 考勤管理 */
                        case 19:
                            // 考勤打卡
                            AttendanceActivity.startActivity(getContext());
                            break;
                        case 20:
                            // 考勤审核
                            AttendanceAuditActivity.startActivity(getContext());
                            break;

                        /** AppPID = 5 安全管理 */
                        case 30:
                            // HSE检查
                            HseCheckActivity.startActivity(getContext());
                            break;
                        case 31:
                            // HSE整改
                            HseRectificationActivity.startActivity(getContext(), null);
                            break;
                        case 32:
                            // 砂船自查
                            BoatInquireActivity.startActivity(getContext());
                            break;
                        case 33:
                            // 制证监督
                            CertificateSupervisionActivity.startActivity(getContext());
                            break;
                        case 34:
                            // 违规记录
                            ViolationRecordsActivity.startActivity(getContext());
                            break;
                        case 35:
                            //机电设备
                            MechanicalActivity.startActivity(getContext());
                            break;
                        case 36:
                            //人员信息
                            CrewActivity.startActivity(getContext());
                            break;
                        case 37:
                            //培训
                            TrainingActivity.startActivity(getContext());
                            break;
                        case 38:
                            //交底
                            break;

                        /** AppPID = 39 设备管理 */
                        case 40:
                            // 设备维修
                            DeviceRepairActivity.startActivity(getContext());
                            break;
                        case 41:
                            // 设备保养
                            break;
                        case 42:
                            // 设备检查
                            break;
                        case 43:
                            // 设备整改
                            break;
                        case 44:
                            // 设备审核
                            break;
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });

            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(lists);
            adapter.notifyDataSetChanged();
        }
    }
}
