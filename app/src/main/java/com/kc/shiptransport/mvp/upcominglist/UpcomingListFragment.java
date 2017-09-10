package com.kc.shiptransport.mvp.upcominglist;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.backlog.BackLog;
import com.kc.shiptransport.db.backlog.ListBean;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.acceptancedetail.AcceptanceDetailActivity;
import com.kc.shiptransport.mvp.amountdetail.AmountDetailActivity;
import com.kc.shiptransport.mvp.exitapplicationassessor.ExitApplicationAssessorActivity;
import com.kc.shiptransport.mvp.exitapplicationdetail.ExitApplicationDetailActivity;
import com.kc.shiptransport.mvp.recordedsanddetaillist.RecordedSandDetailListActivity;
import com.kc.shiptransport.mvp.sampledetail.SampleDetailActivity;
import com.kc.shiptransport.mvp.scannerdetail.ScannerDetailActivity;
import com.kc.shiptransport.mvp.supplydetail.SupplyDetailActivity;
import com.kc.shiptransport.mvp.voyagedetail.VoyageDetailActivity;
import com.kc.shiptransport.util.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/9/8  18:16
 * @desc ${TODD}
 */

public class UpcomingListFragment extends Fragment implements UpcomingListContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private UpcomingListActivity activity;
    private UpcomingListContract.Presenter presenter;
    private CommonAdapter<ListBean> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        presenter.getUpcomingList(activity.pendId);
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (UpcomingListActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
    public void initListener() {

    }

    @Override
    public void setPresenter(UpcomingListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中...", new OnDailogCancleClickListener() {
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
        ToastUtil.tip(getContext(), msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showUpcomingList(final BackLog backLog) {
        activity.getSupportActionBar().setTitle(backLog.getPendingType());

        List<ListBean> list = backLog.getList();

        if (list == null) {
            list = new ArrayList<>();
        }

        adapter = new CommonAdapter<ListBean>(getContext(), R.layout.item_upcoming, list) {
            @Override
            protected void convert(ViewHolder holder, final ListBean listBean, int position) {
                holder.setText(R.id.tv_ship_name, listBean.getShipName())
                        .setText(R.id.tv_plan_time, listBean.getPlanDay())
                        .setText(R.id.tv_sub_name, listBean.getSubcontractorName())
                        .setText(R.id.tv_max_sand, listBean.getSandSupplyCount())
                        .setOnClickListener(R.id.card_view, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (backLog.getPendingType().equals("待信息完善")) {
                                    VoyageDetailActivity.startActivity(getActivity(), listBean.getSubcontractorInterimApproachPlanID(), 2);
                                } else if (backLog.getPendingType().equals("待填写进场材料")) {
                                    ScannerDetailActivity.startActivity(getActivity(), listBean.getSubcontractorInterimApproachPlanID(), 2);
                                } else if (backLog.getPendingType().equals("待预验收")) {
                                    // TODO 缺少最后一个参数: 评价ID
                                    //ToastUtil.tip(getContext(), "待完善");
                                    AcceptanceDetailActivity.startActivity(getActivity(), listBean.getSubcontractorInterimApproachPlanID(), false, listBean.getSandSubcontractorPreAcceptanceEvaluationID());
                                } else if (backLog.getPendingType().equals("待量方")) {
                                    AmountDetailActivity.startActivity(getActivity(), listBean.getSubcontractorInterimApproachPlanID(), false);
                                } else if (backLog.getPendingType().equals("待验砂")) {
                                    SupplyDetailActivity.startActivity(getActivity(), listBean.getSubcontractorInterimApproachPlanID(), false);
                                } else if (backLog.getPendingType().equals("待验砂取样")) {
                                    // TODO 参数position改为itemID
                                    SampleDetailActivity.startActivity(getActivity(), listBean.getSubcontractorInterimApproachPlanID());
                                } else if (backLog.getPendingType().equals("待过砂")) {
                                    RecordedSandDetailListActivity.startActivity(getContext(), listBean.getSubcontractorInterimApproachPlanID(), false);
                                } else if (backLog.getPendingType().equals("待退场申请")) {
                                    ExitApplicationDetailActivity.startActivity(getContext(), listBean.getSubcontractorInterimApproachPlanID(), 0);
                                } else if (backLog.getPendingType().equals("ExitAuditing")) {
                                    ExitApplicationAssessorActivity.startActivity(getContext(), listBean.getSubcontractorInterimApproachPlanID(), false);
                                }
                            }
                        });
            }
        };

        EmptyWrapper<ListBean> emptyWrapper = new EmptyWrapper<>(adapter);
        emptyWrapper.setEmptyView(R.layout.empty_view);

        recyclerView.setAdapter(emptyWrapper);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            // 刷新数据
            presenter.getPending(10000, 1, activity.pendId);
        }
    }
}
