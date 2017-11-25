package com.kc.shiptransport.mvp.hsecheckdefect;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.hse.HseDefectListBean;
import com.kc.shiptransport.db.hse.HseDefectDeadline;
import com.kc.shiptransport.db.hse.HseDefectType;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.mvp.hsecheckdefectadd.HseCheckDefectAddActivity;
import com.kc.shiptransport.util.PopwindowUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/11/22  17:20
 * @desc ${TODD}
 */

public class HseCheckDefectFragment extends BaseFragment<HseCheckDefectActivity> implements HseCheckDefectContract.View, View.OnClickListener {
    private static int pageSize = 20;
    private static int pageCount = 1;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.select_1)
    TextView select1;
    @BindView(R.id.select_2)
    TextView select2;
    @BindView(R.id.select_3)
    TextView select3;
    @BindView(R.id.select_4)
    TextView select4;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private HseCheckDefectContract.Presenter presenter;
    private CommonAdapter<HseDefectListBean> adapter;
    private int defectTypeID;
    private String defectItem;
    private int rectificationDeadline;
    private String creator;
    private int isSubmitted = 100; // 100表示默认全部显示
    private List<HseDefectType> defectTypeList;
    private List<HseDefectDeadline> defectDeadlineList;
    private String[] defectStatusList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initViews(rootView);
        initListener();

        presenter.subscribe();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.autoRefresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_hse_check, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Bundle bundle = new Bundle();
                bundle.putInt(HseCheckDefectAddActivity.TYPE, SettingUtil.TYPE_HSE_DEFECT_ADD);
                bundle.putInt(HseCheckDefectAddActivity.CHECKRECORDID, activity.itemID);
                bundle.putInt(HseCheckDefectAddActivity.ITEMID, 0);
                HseCheckDefectAddActivity.startActivity(getContext(), bundle);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setEnableHeaderTranslationContent(false);
        select1.setText("缺陷类别");
        select2.setText("缺陷项目");
        select3.setText("整改期限");
        select4.setText("处理状态");
    }

    @Override
    public void initListener() {
        select1.setOnClickListener(this);
        select2.setOnClickListener(this);
        select3.setOnClickListener(this);
        select4.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(30000, false);
                refresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(30000, false);
                presenter.getDefects(pageSize, ++pageCount, new HseDefectListBean(activity.itemID, defectTypeID, defectItem, rectificationDeadline, creator, isSubmitted));
            }
        });
    }

    @Override
    public void setPresenter(HseCheckDefectContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void showError(String msg) {
        ToastUtil.tip(getContext(), msg);
    }

    @Override
    public int setView() {
        return R.layout.fragment_hsecheck_defect;
    }

    @Override
    public int setTitle() {
        return R.string.title_hse_check_defect;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_1: // 缺陷类别
                PopwindowUtil.showPopwindow(getContext(), defectTypeList, select1, false, new PopwindowUtil.InitHolder<HseDefectType>() {
                    @Override
                    public void initHolder(ViewHolder holder, final HseDefectType hseDefectType, int position) {
                        holder.setText(R.id.tv_spinner, hseDefectType.getName())
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        select1.setText(hseDefectType.getName());
                                        defectTypeID = hseDefectType.getItemID(); // 缺陷id
                                        refresh();
                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, new PopwindowUtil.OnHeadClickListener() {
                    @Override
                    public void onHeadClick(View view, RecyclerView.ViewHolder holder, int position) {
                        select1.setText("全部缺陷类型");
                        defectTypeID = 0;
                        refresh();
                    }
                });
                break;
            case R.id.select_2:
                break;
            case R.id.select_3: // 整改期限
                PopwindowUtil.showPopwindow(getContext(), defectDeadlineList, select3, false, new PopwindowUtil.InitHolder<HseDefectDeadline>() {
                    @Override
                    public void initHolder(ViewHolder holder, final HseDefectDeadline hseDefectDeadline, int position) {
                        holder.setText(R.id.tv_spinner, hseDefectDeadline.getRectificationDeadlineName())
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        select3.setText(hseDefectDeadline.getRectificationDeadlineName());
                                        rectificationDeadline = hseDefectDeadline.getRectificationDeadlineID();
                                        PopwindowUtil.hidePopwindow();
                                        refresh();
                                    }
                                });
                    }
                }, null, new PopwindowUtil.OnHeadClickListener() {
                    @Override
                    public void onHeadClick(View view, RecyclerView.ViewHolder holder, int position) {
                        select3.setText("全部整改期限");
                        rectificationDeadline = 0;
                        refresh();
                    }
                });
                break;
            case R.id.select_4:
                PopwindowUtil.showPopwindow(getContext(), defectStatusList, select4, false, new PopwindowUtil.InitHolder<String>() {
                    @Override
                    public void initHolder(ViewHolder holder, final String s, final int position) {
                        holder.setText(R.id.tv_spinner, s)
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        select4.setText(s);
                                        isSubmitted = position;
                                        refresh();
                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, new PopwindowUtil.OnHeadClickListener() {
                    @Override
                    public void onHeadClick(View view, RecyclerView.ViewHolder holder, int position) {
                        select4.setText("全部状态");
                        isSubmitted = 100;
                        refresh();
                    }
                });
                break;
        }
    }

    private void refresh() {
        pageCount = 1;
        presenter.getDefects(pageSize, pageCount, new HseDefectListBean(activity.itemID, defectTypeID, defectItem, rectificationDeadline, creator, isSubmitted));
    }

    @Override
    public void showDefects(List<HseDefectListBean> list) {
        if (list == null || list.isEmpty()) {
            // 不能加载更多
            refreshLayout.finishLoadmore();
            refreshLayout.setLoadmoreFinished(true);
        }
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (adapter == null) {
            adapter = new CommonAdapter<HseDefectListBean>(getContext(), R.layout.item_hse_check_defect, list) {
                @Override
                protected void convert(ViewHolder holder, HseDefectListBean bean, int position) {
                    holder.setText(R.id.tv_defect_type, bean.getDefectTypeName())
                            .setText(R.id.tv_defect_project, bean.getDefectItem())
                            .setText(R.id.tv_defect_deadline, bean.getRectificationDeadlineName())
                            .setText(R.id.tv_defect_status, bean.getIsSubmitted() == 1 ? "已处理" : "未处理")
                            .setText(R.id.tv_remark, bean.getRemark())
                            .setOnClickListener(R.id.btn_update, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setOnClickListener(R.id.btn_delete, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                }
            };
        } else {
            adapter.loadmore(list);
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showSyncResult(boolean isSuccess) {
        if (isSuccess) {
            // 缺陷类别
            defectTypeList = DataSupport.findAll(HseDefectType.class);
            // 整改期限
            defectDeadlineList = DataSupport.findAll(HseDefectDeadline.class);
            // 处理状态
            defectStatusList = getResources().getStringArray(R.array.select_hse_defect_status);
            // 缺陷项目

        } else {
            ToastUtil.tip(getContext(), "同步数据失败, 请重试");
        }
    }
}
