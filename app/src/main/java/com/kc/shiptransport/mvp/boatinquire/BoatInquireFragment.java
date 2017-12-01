package com.kc.shiptransport.mvp.boatinquire;

import android.app.ProgressDialog;
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
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireBean;
import com.kc.shiptransport.db.hse.HseCheckShip;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.mvp.boatinquireadd.BoatInquireAddActivity;
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
 * @time 2017/11/29  10:08
 * @desc ${TODD}
 */

public class BoatInquireFragment extends BaseFragment<BoatInquireActivity> implements BoatInquireContract.View, View.OnClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.select_1)
    TextView select1;
    @BindView(R.id.select_2)
    TextView select2;
    @BindView(R.id.select_3)
    TextView select3;
    @BindView(R.id.select_4)
    TextView select4;
    private BoatInquireContract.Presenter presenter;
    private String startDate;
    private String endDate;
    private String shipAccount;
    private List<HseCheckShip> hseCheckShips;
    private CommonAdapter<BoatInquireBean> adapter;

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_hse_check, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Bundle bundle = new Bundle();
                bundle.putInt(BoatInquireAddActivity.TYPE, SettingUtil.TYPE_BOAT_INQUIRE_ADD);
                bundle.putInt(BoatInquireAddActivity.ITEMID, 0);
                BoatInquireAddActivity.startActivity(getContext(), bundle);
                break;
        }
        return super.onOptionsItemSelected(item);
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

        startDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, -30);
        endDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, 1);

        select1.setText("检查日期");
        select2.setText("受检船舶");
    }

    private void refresh(boolean isShow, boolean isLoadMore) {
        if (isLoadMore) {
            ++pageCount;
        } else {
            pageCount = 1;
        }
        presenter.getDatas(pageSize,
                pageCount,
                new BoatInquireBean(0,
                        0,
                        shipAccount,
                        null,
                        null,
                        null,
                        creator,
                        null,
                        null),
                startDate,
                endDate,
                isShow);
    }


    @Override
    public void initListener() {
        select1.setOnClickListener(this);
        select2.setOnClickListener(this);
        select3.setVisibility(View.GONE);
        select4.setVisibility(View.GONE);

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
    public void setPresenter(BoatInquireContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("请稍等", "请稍等", new OnDailogCancleClickListener() {
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
        return R.layout.fragment_boat_inquire;
    }

    @Override
    public int setTitle() {
        return R.string.title_boat_inquire;
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
    public void showDatas(List<BoatInquireBean> list, boolean isFirst) {
        if (list == null || list.isEmpty()) {
            // 不能加载更多
            refreshLayout.finishLoadmore();
            refreshLayout.setLoadmoreFinished(true);
        }
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (adapter == null) {
            adapter = new CommonAdapter<BoatInquireBean>(getContext(), R.layout.item_boat_inquire, list) {
                @Override
                protected void convert(ViewHolder holder, final BoatInquireBean bean, int position) {
                    holder.setText(R.id.tv_ship_name, bean.getShipName())
                            .setText(R.id.tv_check_date, bean.getCheckDate())
                            .setText(R.id.tv_checker, bean.getCreatorName())
                            .setText(R.id.tv_remark, bean.getRemark())
                            .setVisible(R.id.ll_btn, bean.getIsAllowUpdate() == 1)
                            .setOnClickListener(R.id.btn_update, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 更新
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(BoatInquireAddActivity.TYPE, SettingUtil.TYPE_BOAT_INQUIRE_UPDATE);
                                    bundle.putInt(BoatInquireAddActivity.ITEMID, bean.getItemID());
                                    BoatInquireAddActivity.startActivity(getContext(), bundle);
                                }
                            })
                            .setOnClickListener(R.id.btn_delete, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 删除
                                }
                            })
                            .setOnClickListener(R.id.card_view, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 查看
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(BoatInquireAddActivity.TYPE, SettingUtil.TYPE_BOAT_INQUIRE_READ_ONLY);
                                    bundle.putInt(BoatInquireAddActivity.ITEMID, bean.getItemID());
                                    BoatInquireAddActivity.startActivity(getContext(), bundle);
                                }
                            });
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

    @Override
    public void showSyncResult(boolean isSuccess) {
        if (isSuccess) {
            hseCheckShips = DataSupport.findAll(HseCheckShip.class);
        } else {
            ToastUtil.tip(getContext(), "获取数据失败");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_1: // 检查日期
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
            case R.id.select_2: // 船舶账号
                PopwindowUtil.showPopwindow(getContext(), hseCheckShips, select2, false, new PopwindowUtil.InitHolder<HseCheckShip>() {
                    @Override
                    public void initHolder(ViewHolder holder, final HseCheckShip hseCheckShip, int position) {
                        holder.setText(R.id.tv_spinner, hseCheckShip.getShipName())
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        select2.setText(hseCheckShip.getShipName());
                                        shipAccount = hseCheckShip.getShipAccount();
                                        refresh(true, false);
                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, new PopwindowUtil.OnHeadClickListener() {
                    @Override
                    public void onHeadClick(View view, RecyclerView.ViewHolder holder, int position) {
                        select2.setText("全部受检船舶");
                        shipAccount = "";
                        refresh(true, false);
                    }
                });
                break;
        }
    }
}
