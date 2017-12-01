package com.kc.shiptransport.mvp.hserectificationlist;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.hse.HseDefectListBean;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.mvp.hserectificationupdate.HseRectificationUpdateActivity;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/11/29  8:37
 * @desc ${TODD}
 */

public class HseRectificationListFragment extends BaseFragment<HseRectificationListActivity> implements HseRectificationListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private HseRectificationListContract.Presenter presenter;
    private int pageSize = 20;
    private int pageCount = 1;
    private int defectTypeID;
    private int rectificationDeadline;
    private int isSubmitted = 1; // 已整改
    private String startDate;
    private String endDate;
    private CommonAdapter<HseDefectListBean> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initViews(rootView);
        initListener();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.autoRefresh();
    }

    @Override
    public void initViews(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setEnableHeaderTranslationContent(false);
    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(30000, false);
                refresh(false, false);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(30000, false);
                refresh(false, true);
            }
        });
    }

    /**
     * 查询条件:
     * 1. 当前船舶账号
     * 2. 提交时间
     * 3. 缺陷类别
     * 4. 整改期限
     *
     * @param isShow
     */
    private void refresh(boolean isShow, boolean isLoadmore) {
        if (isLoadmore) {
            presenter.getDefects(pageSize,
                    ++pageCount,
                    new HseDefectListBean(0,
                            0,
                            0,
                            creator,
                            null,
                            defectTypeID,
                            null,
                            null,
                            rectificationDeadline,
                            null,
                            null,
                            null,
                            null,
                            null,
                            isSubmitted,
                            null,
                            null,
                            0,
                            null),
                    startDate,
                    endDate,
                    isShow);
        } else {
            pageCount = 1;
            presenter.getDefects(pageSize,
                    pageCount,
                    new HseDefectListBean(0,
                            0,
                            0,
                            creator,
                            null,
                            defectTypeID,
                            null,
                            null,
                            rectificationDeadline,
                            null,
                            null,
                            null,
                            null,
                            null,
                            isSubmitted,
                            null,
                            null,
                            0,
                            null),
                    startDate,
                    endDate,
                    isShow);
        }
    }

    @Override
    public void setPresenter(HseRectificationListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("请稍等", "请稍等...", new OnDailogCancleClickListener() {
                @Override
                public void onCancel(ProgressDialog dialog) {
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
    public int setView() {
        return R.layout.fragment_hse_rectification_list;
    }

    @Override
    public int setTitle() {
        return R.string.title_hse_retification_list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
        if (presenter != null) {
            presenter.unsubscribe();
        }
    }

    @Override
    public void showDefects(List<HseDefectListBean> list, boolean isFirst) {
        if (list == null || list.isEmpty()) {
            // 不能加载更多
            refreshLayout.finishLoadmore();
            refreshLayout.setLoadmoreFinished(true);
        }
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (adapter == null) {
            adapter = new CommonAdapter<HseDefectListBean>(getContext(), R.layout.item_hse_rectification_list, list) {
                @Override
                protected void convert(ViewHolder holder, final HseDefectListBean bean, final int position) {
                    holder.setText(R.id.tv_defect_date, bean.getSystemDate())
                            .setText(R.id.tv_rectification_date, bean.getRectificationTime())
                            .setText(R.id.tv_defect_type, bean.getDefectTypeName())
                            .setText(R.id.tv_defect_project, bean.getDefectItem())
                            .setText(R.id.tv_defect_deadline, bean.getRectificationDeadlineName())
                            .setText(R.id.tv_defect_status, bean.getIsSubmitted() == 1 ? "已处理" : "未处理")
                            .setText(R.id.tv_rectification_remark, bean.getRectificationRemark())
                            .setText(R.id.tv_remark, bean.getRemark())
                            .setOnClickListener(R.id.card_view, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(HseRectificationUpdateActivity.DATA, bean);
                                    bundle.putInt(HseRectificationUpdateActivity.TYPE, SettingUtil.TYPE_HSE_RECTIFICATION_READ_ONLY);
                                    HseRectificationUpdateActivity.startActivity(getContext(), bundle);
                                }
                            });
                }
            };
        } else {
            if (isFirst) {
                adapter.clear();
            }
            adapter.loadmore(list);
        }
        recyclerView.setAdapter(adapter);
    }
}
