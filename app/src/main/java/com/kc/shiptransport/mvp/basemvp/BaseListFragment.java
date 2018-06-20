package com.kc.shiptransport.mvp.basemvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.PopwindowUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.security.PublicKey;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/11/22  10:33
 * @desc 通用的列表加载界面
 */

public abstract class BaseListFragment<ACTIVITY extends AppCompatActivity, T, DEVICE> extends BaseFragment<ACTIVITY> implements View.OnClickListener {

    public DataRepository dataRepository = new DataRepository();
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
    private CommonAdapter<T> adapter;
    private String startDate;
    private String endDate;
    private String checkedShipAccount;
    private List<DEVICE> shipList;
    private int deletePosition;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                onAddSelected();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * add menu点击回调
     */
    public abstract void onAddSelected();

    @Override
    public int setView() {
        return R.layout.fragment_hse_list;
    }

    public void initViews(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        initSelect1(select1);
        initSelect2(select2);
        initSelect3(select3);
        initSelect4(select4);
        startDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, -30);
        endDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, 1);
        List<User> users = DataSupport.findAll(User.class);
        creator = users.isEmpty() ? "" : users.get(0).getUserID();
        refreshLayout.setEnableHeaderTranslationContent(false);
    }

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

    public abstract void initSelect1(TextView select1);

    public abstract void initSelect2(TextView select2);

    public abstract void initSelect3(TextView select3);

    public abstract void initSelect4(TextView select4);

    /**
     * 获取列表数据
     *
     * @param isShow     是否显示dialog
     * @param isLoadMore 是否加载更多
     */
    public void refresh(boolean isShow, boolean isLoadMore) {
        if (isLoadMore) {
            ++pageCount;
        } else {
            pageCount = 1;
        }

        if (isShow) {
            showLoading(true);
        }

        Observable.zip(getDataList(pageSize, pageCount, startDate, endDate, isShow), getShipList(), new BiFunction<List<T>, List<DEVICE>, List<T>>() {
            @Override
            public List<T> apply(List<T> ts, List<DEVICE> devices) throws Exception {
                return ts;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<T> ts) {
                        showData(ts, pageCount == 1);
                        if (ts.isEmpty()) {
                            showError("没有数据");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showLoading(false);
                        showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        showLoading(false);
                    }
                });
    }

    public void showLoading(boolean isShow) {
        if (isShow) {
            ((BaseActivity) activity).showProgressDailog("加载中", "请稍等...", new OnDailogCancleClickListener() {
                @Override
                public void onCancel(ProgressDialog dialog) {
                    unsubscribe();
                }
            });
        } else {
            ((BaseActivity) activity).hideProgressDailog();
            refreshLayout.finishRefresh();
        }
    }

    /** 请求列表数据 */
    public abstract Observable<List<T>> getDataList(int pageSize, int pageCount, String startDate, String endDate, boolean isShow);

    /** 获取查询的船舶列表 */
    public abstract Observable<List<DEVICE>> getShipList();

    /**
     * 展示list数据
     */
    public void showData(List<T> list, boolean isFirst) {
        if (list == null || list.isEmpty()) {
            // 不能加载更多
            refreshLayout.finishLoadmore();
            refreshLayout.setLoadmoreFinished(true);
        }

        shipList = getShipListForDB();

        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();


        if (adapter == null) {
            adapter = getAdapter(list);

            recyclerView.setAdapter(adapter);
        } else {
            if (isFirst) {
                adapter.clear();
            }
            adapter.loadmore(list);
        }
    }

    public void showError(String msg) {
        ToastUtil.tip(getContext(), msg);
    }

    /**
     * 取消所有请求
     */
    public void unsubscribe() {
        disposable.clear();
    }

    /** 从数据库获取数据 */
    public abstract List<DEVICE> getShipListForDB();

    /**
     * 获取adapter
     */
    public abstract CommonAdapter<T> getAdapter(List<T> list);

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
        unsubscribe();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_hse_check, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_1:
                /** 时间选择 */
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
                /** 选择设备 */
                PopwindowUtil.showPopwindow(getContext(), shipList, select2, false, new PopwindowUtil.InitHolder<DEVICE>() {
                    @Override
                    public void initHolder(final ViewHolder holder, final DEVICE device, final int position) {
                        holder.setText(R.id.tv_spinner, getSelect2ItemTitle(device))
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        select2.setText(getSelect2ItemTitle(device));
                                        onSelect2itemClick(holder, device, position);
                                        refresh(true, false);
                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, new PopwindowUtil.OnHeadClickListener() {
                    @Override
                    public void onHeadClick(View view, RecyclerView.ViewHolder holder, int position) {
                        select2.setText(getDefaultSelect2Text());
                        onSelect2HeadClick(view, holder, position);
                        refresh(true, false);
                    }
                });
                break;
        }
    }

    /** select2默认描述 */
    public abstract String getDefaultSelect2Text();

    /** 获取select2描述 */
    public abstract String getSelect2ItemTitle(DEVICE device);

    /** select 2 item 点击事件 */
    public abstract void onSelect2itemClick(ViewHolder holder, DEVICE device, int position);

    /** select 2 item head 点击事件 */
    public abstract void onSelect2HeadClick(View view, RecyclerView.ViewHolder holder, int position);
}
