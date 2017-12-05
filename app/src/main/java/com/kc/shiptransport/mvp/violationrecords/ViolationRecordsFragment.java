package com.kc.shiptransport.mvp.violationrecords;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.violationrecord.ViolationRecordBean;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.BaseFragment;
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
 * @time 2017/12/4  16:17
 * @desc ${TODD}
 */

public class ViolationRecordsFragment extends BaseFragment<ViolationRecordsActivity> implements ViolationRecordsContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private ViolationRecordsContract.Presenter presenter;
    private CommonAdapter<ViolationRecordBean> adapter;

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

    public void refresh(boolean isShow, boolean isLoadMore) {
        if (isLoadMore) {
            ++pageCount;
        } else {
            pageCount = 1;
        }

        presenter.getDatas(pageSize, pageCount);
    }

    @Override
    public void setPresenter(ViolationRecordsContract.Presenter presenter) {
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
        return R.layout.fragment_violation_records;
    }

    @Override
    public int setTitle() {
        return R.string.title_violation_records;
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
    public void showDatas(List<ViolationRecordBean> list, boolean isFirst) {
        if (list == null || list.isEmpty()) {
            // 不能加载更多
            refreshLayout.finishLoadmore();
            refreshLayout.setLoadmoreFinished(true);
        }
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();

        if (adapter == null) {
            adapter = new CommonAdapter<ViolationRecordBean>(getContext(), R.layout.item_violation_record, list) {
                @Override
                protected void convert(ViewHolder holder, ViolationRecordBean bean, int position) {
                    holder.setText(R.id.tv_violation_date, bean.getViolationDate())
                            .setText(R.id.tv_violation_unit, bean.getViolationUnit())
                            .setText(R.id.tv_violation_type, bean.getViolationType())
                            .setText(R.id.tv_remark, bean.getRemark());
                }
            };

            recyclerView.setAdapter(adapter);
        } else {
            if (isFirst) {
                adapter.clear();
            }
            adapter.loadmore(list);
        }
    }
}
