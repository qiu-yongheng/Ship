package com.kc.shiptransport.view.actiivty.select;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.hse.HseCheckShip;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/12/8  13:48
 * @desc ${TODD}
 */

public class SelectFragment extends BaseFragment<SelectActivity> implements SelectContract.View, View.OnClickListener {
    public static final String SHIPNAME = "SHIPNAME";
    public static final String SHIPACCOUNT = "SHIPACCOUNT";
    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private SelectContract.Presenter presenter;
    private SearchView searchView;
    private CommonAdapter<HseCheckShip> hseShipAdapter;

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
    public void initViews(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        switch (activity.type) {
            case SettingUtil.TYPE_HSE_CHECK_ADD:
                presenter.getHseShipData();
                break;
        }
    }

    @Override
    public void initListener() {
        btnReturn.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        //通过MenuItem得到SearchView
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("请输入搜索关键字");
        //searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LogUtil.d(newText);
                presenter.search(newText, false);
                return true;
            }
        });
        //搜索框展开时后面叉叉按钮的点击事件
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                presenter.search(null, true);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(SelectContract.Presenter presenter) {
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
        return R.layout.fragment_select_list;
    }

    @Override
    public int setTitle() {
        switch (activity.type) {
            case SettingUtil.TYPE_HSE_CHECK_ADD: // HSE检查
                return R.string.title_hse_check_select_ship;
        }
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_return:
                activity.onBackPressed();
                break;
            case R.id.btn_cancel:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(SHIPNAME, null);
                bundle.putString(SHIPACCOUNT, null);
                intent.putExtras(bundle);
                activity.setResult(0, intent);
                activity.onBackPressed();
                break;
        }
    }

    @Override
    public void showHseShipData(List<HseCheckShip> checkShips) {
        hseShipAdapter = new CommonAdapter<HseCheckShip>(getContext(), R.layout.item_spinner, checkShips) {
            @Override
            protected void convert(ViewHolder holder, final HseCheckShip hseCheckShip, int position) {
                holder.setText(R.id.tv_spinner, hseCheckShip.getShipName())
                        .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString(SHIPNAME, hseCheckShip.getShipName());
                                bundle.putString(SHIPACCOUNT, hseCheckShip.getShipAccount());
                                intent.putExtras(bundle);
                                activity.setResult(0, intent);
                                activity.onBackPressed();
                            }
                        });
            }
        };
        EmptyWrapper<HseCheckShip> emptyWrapper = new EmptyWrapper<>(hseShipAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view);
        recyclerView.setAdapter(emptyWrapper);
    }
}
