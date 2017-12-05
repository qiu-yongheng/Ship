package com.kc.shiptransport.mvp.hserectification;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.mvp.hserectificationlist.HseRectificationListActivity;
import com.kc.shiptransport.mvp.hserectificationupdate.HseRectificationUpdateActivity;
import com.kc.shiptransport.util.CalendarUtil;
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

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/11/28  10:11
 * @desc ${TODD}
 */

public class HseRectificationFragment extends BaseFragment<HseRectificationActivity> implements HseRectificationContract.View, View.OnClickListener {
    private static int pageSize = 20;
    private static int pageCount = 1;

    @BindView(R.id.select_1)
    TextView select1;
    @BindView(R.id.select_2)
    TextView select2;
    @BindView(R.id.select_3)
    TextView select3;
    @BindView(R.id.select_4)
    TextView select4;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private HseRectificationContract.Presenter presenter;
    private CommonAdapter<HseDefectListBean> adapter;
    private int deletePosition;
    private List<HseDefectType> defectTypeList;
    private List<HseDefectDeadline> defectDeadlineList;
    private String[] defectStatusList;
    private int defectTypeID;
    private int rectificationDeadline;
    private int isSubmitted = 0; // 默认0, 显示未处理
    private String startDate;
    private String endDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initViews(rootView);
        initListener();

        presenter.subscribe(); // 同步基本数据
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_hse_rectification, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rectification:
                // 跳转到已整改列表
                HseRectificationListActivity.startActivity(getContext());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setEnableHeaderTranslationContent(false);
        select1.setText("提交时间");
        select2.setText("缺陷类别");
        select3.setText("整改期限");
        select4.setVisibility(View.GONE);
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

    @Override
    public void setPresenter(HseRectificationContract.Presenter presenter) {
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
        return R.layout.fragment_hse_list;
    }

    @Override
    public int setTitle() {
        return R.string.title_hse_retification;
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_1: // 提交时间
                PopwindowUtil.showPopwindow(getContext(), getResources().getStringArray(R.array.select_hse_check_time), select1, false, new PopwindowUtil.InitHolder<String>() {
                    @Override
                    public void initHolder(ViewHolder holder, final String s, final int position) {
                        holder.setText(R.id.tv_spinner, s)
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        select1.setText(s);
                                        switch (position) {
                                            case 0:
                                                startDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, -30);
                                                endDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, 1);
                                                break;
                                            case 1:
                                                PopwindowUtil.recyclerView.setVisibility(View.GONE);
                                                PopwindowUtil.ll.setVisibility(View.VISIBLE);
                                                break;
                                        }

                                        if (position != 1) {
                                            refresh(true, false);
                                            PopwindowUtil.hidePopwindow();
                                        }
                                    }
                                });
                    }
                }, new PopwindowUtil.OnPopOKClickListener() {
                    @Override
                    public void onOkClick(String startTime, String endTime) {
                        startDate = startTime;
                        endDate = endTime;
                        refresh(true, false);
                    }
                }, null);
                break;
            case R.id.select_2: // 缺陷类别
                PopwindowUtil.showPopwindow(getContext(), defectTypeList, select1, false, new PopwindowUtil.InitHolder<HseDefectType>() {
                    @Override
                    public void initHolder(ViewHolder holder, final HseDefectType hseDefectType, int position) {
                        holder.setText(R.id.tv_spinner, hseDefectType.getName())
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        select2.setText(hseDefectType.getName());
                                        defectTypeID = hseDefectType.getItemID(); // 缺陷id
                                        refresh(true, false);
                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, new PopwindowUtil.OnHeadClickListener() {
                    @Override
                    public void onHeadClick(View view, RecyclerView.ViewHolder holder, int position) {
                        select1.setText("全部缺陷类型");
                        defectTypeID = 0;
                        refresh(true, false);
                    }
                });
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
                                        refresh(true, false);
                                    }
                                });
                    }
                }, null, new PopwindowUtil.OnHeadClickListener() {
                    @Override
                    public void onHeadClick(View view, RecyclerView.ViewHolder holder, int position) {
                        select3.setText("全部整改期限");
                        rectificationDeadline = 0;
                        refresh(true, false);
                    }
                });
                break;
            case R.id.select_4: // 处理状态
                PopwindowUtil.showPopwindow(getContext(), defectStatusList, select4, false, new PopwindowUtil.InitHolder<String>() {
                    @Override
                    public void initHolder(ViewHolder holder, final String s, final int position) {
                        holder.setText(R.id.tv_spinner, s)
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        select4.setText(s);
                                        isSubmitted = position - 1;
                                        refresh(true, false);
                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, new PopwindowUtil.OnHeadClickListener() {
                    @Override
                    public void onHeadClick(View view, RecyclerView.ViewHolder holder, int position) {
                        select4.setText("全部状态");
                        isSubmitted = 100;
                        refresh(true, false);
                    }
                });
                break;
        }
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
    public void showDefects(List<HseDefectListBean> list, boolean isFirst) {
        if (list == null || list.isEmpty()) {
            // 不能加载更多
            refreshLayout.finishLoadmore();
            refreshLayout.setLoadmoreFinished(true);
        }
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (adapter == null) {
            adapter = new CommonAdapter<HseDefectListBean>(getContext(), R.layout.item_hse_rectification, list) {
                @Override
                protected void convert(ViewHolder holder, final HseDefectListBean bean, final int position) {
                    holder.setText(R.id.tv_defect_date, bean.getSystemDate())
                            .setText(R.id.tv_defect_type, bean.getDefectTypeName())
                            .setText(R.id.tv_defect_project, bean.getDefectItem())
                            .setText(R.id.tv_defect_deadline, bean.getRectificationDeadlineName())
                            .setText(R.id.tv_defect_status, bean.getIsSubmitted() == 1 ? "已处理" : "未处理")
                            .setText(R.id.tv_remark, bean.getRemark())
                            .setText(R.id.tv_deadline_date, bean.getDeadlineTime())
                            .setVisible(R.id.btn_delete, bean.getRectificationRecordID() != 0) // 如果没有提交过整改数据, 不显示删除
                            .setOnClickListener(R.id.btn_detail, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 查看详情
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(HseRectificationUpdateActivity.DATA, bean);
                                    bundle.putInt(HseRectificationUpdateActivity.TYPE, SettingUtil.TYPE_HSE_RECTIFICATION_DEFECT_DETAIL);
                                    HseRectificationUpdateActivity.startActivity(getContext(), bundle);
                                }
                            })
                            .setOnClickListener(R.id.btn_rectification, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO: 整改
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(HseRectificationUpdateActivity.DATA, bean);
                                    bundle.putInt(HseRectificationUpdateActivity.TYPE, SettingUtil.TYPE_HSE_RECTIFICATION_ADD);
                                    HseRectificationUpdateActivity.startActivity(getContext(), bundle);
                                }
                            })
                            .setOnClickListener(R.id.btn_delete, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    activity.showDailog("删除HSE整改", "是否删除整改记录?", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deletePosition = position;
                                            if (bean.getRectificationRecordID() == 0) {
                                                ToastUtil.tip(getContext(), "此缺陷还未整改, 不能删除");
                                            } else {
                                                presenter.deleteForItem(bean.getRectificationRecordID());
                                            }
                                        }
                                    });
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

    @Override
    public void showDeleteResult(boolean isSuccess) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "删除成功");
//            adapter.removeItem(deletePosition); // 不删除记录
            adapter.getDatas().get(deletePosition).setRectificationRecordID(0);
            adapter.notifyItemChanged(deletePosition);
        } else {
            ToastUtil.tip(getContext(), "删除失败, 请重试");
        }
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

        } else {
            ToastUtil.tip(getContext(), "同步数据失败, 请重试");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.autoRefresh();
    }
}
