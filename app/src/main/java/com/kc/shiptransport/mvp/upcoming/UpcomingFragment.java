package com.kc.shiptransport.mvp.upcoming;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.backlog.BackLog;
import com.kc.shiptransport.db.backlog.ListBean;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.main.MainActivity;
import com.kc.shiptransport.mvp.main.MainFragment;
import com.kc.shiptransport.mvp.upcominglist.UpcomingListActivity;
import com.kc.shiptransport.util.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.badgeview.QBadgeView;

/**
 * @author 邱永恒
 * @time 2017/6/6 20:40
 * @desc ${TODO}
 */
public class UpcomingFragment extends Fragment implements UpcomingContract.View {
    Unbinder unbinder;
    @BindView(R.id.toolbar_home)
    Toolbar toolbarHome;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    private MainActivity activity;
    private UpcomingContract.Presenter presenter;
    private UpcomingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        if (presenter != null) {
            presenter.getPending(10000, 1);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        activity = (MainActivity) getActivity();
        activity.setSupportActionBar(toolbarHome);

        List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
        tvUser.setText("登录用户: " + all.get(0).getSubcontractorName());
    }

    @Override
    public void initListener() {
        /** 刷新 */
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getPending(10000, 1);
            }
        });
    }

    @Override
    public void setPresenter(UpcomingContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中...", new OnDailogCancleClickListener() {
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

    /**
     * 显示待办信息
     *
     * @param list
     */
    @Override
    public void showPending(List<BackLog> list) {
        View badge = MainFragment.getBadge();
        if (badge != null) {
            List<ListBean> all = DataSupport.findAll(ListBean.class);
            new QBadgeView(getContext()).bindTarget(badge).setBadgeNumber(all.size()).setGravityOffset(20, 0, true);
        }

        if (adapter == null) {
            adapter = new UpcomingAdapter(getContext(), list);
            adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    String pendingID = adapter.list.get(position).getPendingID();
                    /** 跳转界面 */
                    UpcomingListActivity.startActivity(getContext(), pendingID);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            // TODO
            //            int type = SharePreferenceUtil.getInt(getContext(), SettingUtil.SP_KEY_UPCOMING);
            //            switch (type) {
            //                case SettingUtil.UPCOMING_DB:
            //                    // 从数据库获取数据
            //                    presenter.getPendingForDB();
            //                    break;
            //                case SettingUtil.UPCOMING_NET:
            //                    // 从网络获取数据
            //                    presenter.getPending(10000, 1);
            //                    break;
            //                default:
            //                    // 默认从网络获取数据
            //                    presenter.getPending(10000, 1);
            //                    break;
            //            }

            presenter.getPendingForDB();
        }
    }
}
