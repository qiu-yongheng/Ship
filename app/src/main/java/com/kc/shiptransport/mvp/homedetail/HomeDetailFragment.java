package com.kc.shiptransport.mvp.homedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.AppList;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.acceptance.AcceptanceActivity;
import com.kc.shiptransport.mvp.amount.AmountActivity;
import com.kc.shiptransport.mvp.attendance.AttendanceActivity;
import com.kc.shiptransport.mvp.attendanceaudit.AttendanceAuditActivity;
import com.kc.shiptransport.mvp.plan.PlanActivity;
import com.kc.shiptransport.mvp.recordedsand.RecordedSandActivity;
import com.kc.shiptransport.mvp.sample.SampleActivity;
import com.kc.shiptransport.mvp.scanner.ScannerActivity;
import com.kc.shiptransport.mvp.supply.SupplyActivity;
import com.kc.shiptransport.mvp.voyageinfo.VoyageInfoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 邱永恒
 * @time 2017/6/30 21:46
 * @desc ${TODO}
 */

public class HomeDetailFragment extends Fragment implements HomeDetailContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
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
        activity = (HomeDetailActivity) getActivity();

        mRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
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

    /**
     * 显示二级界面 , 根据appid设置点击事件
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
                            // 分包商进场计划
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
                            // 分包商航次信息完善
                            VoyageInfoActivity.navigateToVoyageInfoActivity(getContext());
                            break;
                        case 15:
                            // 分包商航次完善扫描件
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

                        /** AppPID = 18 考勤管理 */
                        case 19:
                            // 考勤打卡
                            AttendanceActivity.startActivity(getContext());
                            break;
                        case 20:
                            // 考勤审核
                            AttendanceAuditActivity.startActivity(getContext());
                            break;
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });

            mRecyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(lists);
            adapter.notifyDataSetChanged();
        }
    }
}
