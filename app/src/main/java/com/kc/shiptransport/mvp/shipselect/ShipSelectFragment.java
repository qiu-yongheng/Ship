package com.kc.shiptransport.mvp.shipselect;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.ship.Ship;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/5/18  22:47
 * @desc ${TODD}
 */

public class ShipSelectFragment extends Fragment implements ShipSelectContract.View {
    @BindView(R.id.toolbar_ship_select)
    Toolbar toolbarShipSelect;
    @BindView(R.id.recyclerview_ship_select)
    RecyclerView recyclerviewShipSelect;
    @BindView(R.id.btn_ship_cancel)
    AppCompatButton btnShipCancel;
    @BindView(R.id.btn_ship_commit)
    AppCompatButton btnShipCommit;
    @BindView(R.id.ll)
    LinearLayout ll;
    Unbinder unbinder;
    private ShipSelectActivity activity;
    private ShipSelectContract.Presenter presenter;
    private ShipSelectAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ship_select, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // 获取船舶类型对应的数据
        presenter.getShip(activity.currentSelectShipType);
        return view;
    }

    public void initListener() {
        // 取消选择,
        btnShipCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //presenter.doCancle(activity.currentSelectShipType, activity.mCurrentSelectDate);
                getActivity().onBackPressed();
            }
        });

        // 发送提交的网络请求, 刷新任务计划列表 (两个网络请求)
        btnShipCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doCommit(activity.currentSelectShipType, activity.mCurrentSelectDate);
            }
        });
    }

    @Override
    public void initViews(View view) {
        activity = (ShipSelectActivity)getActivity();
        setHasOptionsMenu(true);
        activity.setSupportActionBar(toolbarShipSelect);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle("新增船舶");

        recyclerviewShipSelect.setLayoutManager(new GridLayoutManager(activity, 2));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(ShipSelectContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showShip(Ship value) {
        if (adapter == null) {
            // 传递船分类数据, 当前选择日期
            adapter = new ShipSelectAdapter(getActivity(), value, activity.mCurrentSelectDate);
            recyclerviewShipSelect.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void cancle() {
        adapter.notifyDataSetChanged();
        Toast.makeText(activity, "取消成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void commit() {
        activity.onBackPressed();
    }

    @Override
    public void showLoading(boolean active) {
        if (active) {
            activity.showProgressDailog("提交中", "提交中...", new OnDailogCancleClickListener() {
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

    }

    @Override
    public void showError() {
        activity.showDailog("提交失败", "提交失败, 是否重试?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.doCommit(activity.currentSelectShipType, activity.mCurrentSelectDate);
            }
        });
    }

    @Override
    public void showSuccess() {
        Toast.makeText(activity, "提交成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeDailogInfo() {
        activity.changeProgressDailogInfo("同步数据", "正在同步数据");
    }

    @Override
    public void navigateToPlanSet() {
        activity.onBackPressed();
    }

    @Override
    public void showCommitSuccess() {
        int jumpWeek = SharePreferenceUtil.getInt(getActivity(), SettingUtil.WEEK_JUMP);
        presenter.doRefresh(jumpWeek);
    }
}
