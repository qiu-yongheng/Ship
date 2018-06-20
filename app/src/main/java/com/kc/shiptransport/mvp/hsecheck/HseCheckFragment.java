package com.kc.shiptransport.mvp.hsecheck;

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
import com.kc.shiptransport.data.bean.hse.HseCheckListBean;
import com.kc.shiptransport.data.bean.hse.HseCheckSelectBean;
import com.kc.shiptransport.db.hse.HseCheckShip;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.mvp.hsecheckadd.HseCheckAddActivity;
import com.kc.shiptransport.mvp.hsecheckdefect.HseCheckDefectActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.LogUtil;
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
 * @time 2017/11/22  10:33
 * @desc ${TODD}
 */

public class HseCheckFragment extends BaseFragment<HseCheckActivity> implements HseCheckContract.View, View.OnClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.select_1)
    TextView select1;
    @BindView(R.id.select_2)
    TextView select2;
    @BindView(R.id.select_3)
    TextView select3;
    @BindView(R.id.select_4)
    TextView select4;
    private CommonAdapter<HseCheckListBean> adapter;
    private HseCheckContract.Presenter presenter;
    private String startDate;
    private String endDate;
    private String checkedShipAccount;

    private List<HseCheckShip> shipList;
    private int deletePosition;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Bundle bundle = new Bundle();
                bundle.putInt(HseCheckAddActivity.TYPE, SettingUtil.TYPE_HSE_CHECK_ADD);
                bundle.putInt(HseCheckAddActivity.ITEMID, 0);
                HseCheckAddActivity.startActivity(getContext(), bundle);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int setView() {
        return R.layout.fragment_hse_list;
    }

    @Override
    public int setTitle() {
        return R.string.title_hse_check;
    }

    @Override
    public void initViews(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        select1.setText("近一月");
        select2.setText("受检船舶");
        select3.setVisibility(View.GONE);
        select4.setVisibility(View.GONE);
        startDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, -30);
        endDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, 1);
        //        List<User> users = DataSupport.findAll(User.class);
        //        creator = users.isEmpty() ? "" : users.get(0).getUserID();
        refreshLayout.setEnableHeaderTranslationContent(false);
    }

    @Override
    public void initListener() {
        select1.setOnClickListener(this);
        select2.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshLayout.finishRefresh(30000, false);
                refresh(false, false);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                LogUtil.d("加载更多");
                refreshlayout.finishLoadmore(30000, false);
                refresh(false, true);
            }
        });
    }

    @Override
    public void setPresenter(HseCheckContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "请稍等...", new OnDailogCancleClickListener() {
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

    public void refresh(boolean isShow, boolean isLoadMore) {
        if (isLoadMore) {
            ++pageCount;
        } else {
            pageCount = 1;
        }
        presenter.getDates(pageSize, pageCount, new HseCheckSelectBean(startDate, endDate, checkedShipAccount, null), isShow);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.autoRefresh();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_hse_check, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showDates(List<HseCheckListBean> hseCheckListBeans, boolean isFirst) {
        if (hseCheckListBeans == null || hseCheckListBeans.isEmpty()) {
            // 不能加载更多
            refreshLayout.finishLoadmore();
            refreshLayout.setLoadmoreFinished(true);
        }

        shipList = DataSupport.findAll(HseCheckShip.class);
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (adapter == null) {
            adapter = new CommonAdapter<HseCheckListBean>(getContext(), R.layout.item_hse_check, hseCheckListBeans) {
                @Override
                protected void convert(ViewHolder holder, final HseCheckListBean bean, final int position) {
                    holder.setText(R.id.tv_ship_name, bean.getCheckedShipName())
                            .setText(R.id.tv_checker, bean.getCreatorName())
                            .setText(R.id.tv_check_time, bean.getCheckedTime())
                            .setText(R.id.tv_check_ship, bean.getCheckedShipName())
                            .setText(R.id.tv_remark, bean.getRemark())
                            .setText(R.id.tv_defect_count, String.valueOf(bean.getDefectCount()))
                            .setText(R.id.tv_rectificationed, String.valueOf(bean.getRectificationDoneCount()))
                            .setText(R.id.tv_rectificationing, String.valueOf(bean.getRectificationDoingCount()))
                            .setTextUnderline(R.id.tv_other, "(缺陷数:" + bean.getDefectCount() + " 已整改:" + bean.getRectificationDoneCount() + " 待整改:" + bean.getRectificationDoingCount() + ")")
                            .setVisible(R.id.btn_delete, bean.getDefectCount() == 0)
                            .setOnClickListener(R.id.tv_other, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(HseCheckDefectActivity.ITEMID, bean.getItemID());
                                    HseCheckDefectActivity.startActivity(getContext(), bundle);
                                }
                            })
                            .setOnClickListener(R.id.btn_update, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) { // 修改
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(HseCheckAddActivity.TYPE, SettingUtil.TYPE_HSE_CHECK_UPDATE);
                                    bundle.putInt(HseCheckAddActivity.ITEMID, bean.getItemID());
                                    HseCheckAddActivity.startActivity(getContext(), bundle);
                                }
                            })
                            .setOnClickListener(R.id.btn_delete, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) { // 删除
                                    activity.showDailog("删除记录", "删除此记录?", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            presenter.delete(bean.getItemID());
                                            // 记录需要删除的position
                                            deletePosition = position;
                                        }
                                    });
                                }
                            })
                            .setOnClickListener(R.id.btn_add_defect, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) { // 新增缺陷
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(HseCheckDefectActivity.ITEMID, bean.getItemID());
                                    HseCheckDefectActivity.startActivity(getContext(), bundle);
                                }
                            });
                }
            };

            // 不能使用EmptyWrapper装饰adapter, 不然删除列表没有变化
            //        EmptyWrapper<String> emptyWrapper = new EmptyWrapper<>(adapter);
            //        emptyWrapper.setEmptyView(R.layout.empty_view);
            recyclerView.setAdapter(adapter);
        } else {
            if (isFirst) {
                adapter.clear();
            }
            adapter.loadmore(hseCheckListBeans);

        }
    }

    @Override
    public void showDeleteResult(boolean isSuccess) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "删除成功");
            // 刷新
            adapter.removeItem(deletePosition);
        } else {
            ToastUtil.tip(getContext(), "删除失败, 请重试");
        }
    }    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_1:
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
            case R.id.select_2:
                PopwindowUtil.showPopwindow(getContext(), shipList, select2, false, new PopwindowUtil.InitHolder<HseCheckShip>() {
                    @Override
                    public void initHolder(ViewHolder holder, final HseCheckShip hseCheckShip, int position) {
                        holder.setText(R.id.tv_spinner, hseCheckShip.getShipName())
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        select2.setText(hseCheckShip.getShipName());
                                        checkedShipAccount = hseCheckShip.getShipAccount();
                                        refresh(true, false);
                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, new PopwindowUtil.OnHeadClickListener() {
                    @Override
                    public void onHeadClick(View view, RecyclerView.ViewHolder holder, int position) {
                        select2.setText("全部受检船舶");
                        checkedShipAccount = "";
                        refresh(true, false);
                    }
                });
                break;
        }
    }


}
