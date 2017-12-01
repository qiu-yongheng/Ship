package com.kc.shiptransport.mvp.certificatesupervision;

import android.app.ProgressDialog;
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

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.certificatesupervision.CertificateBean;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.util.LogUtil;
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
 * @time 2017/11/30  17:30
 * @desc ${TODD}
 */
public class CertificateSupervisionFragment extends BaseFragment<CertificateSupervisionActivity> implements CertificateSupervisionContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private CertificateSupervisionContract.Presenter presenter;
    private SearchView searchView;
    private CommonAdapter<CertificateBean> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initViews(rootView);
        initListener();

        presenter.getDatas();
        return rootView;
    }

    @Override
    public int setView() {
        return R.layout.fragment_certificate_supervision;
    }

    @Override
    public int setTitle() {
        return R.string.title_certificate_supervision;
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
    public void initViews(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void setPresenter(CertificateSupervisionContract.Presenter presenter) {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showDatas(List<CertificateBean> list) {
        LogUtil.d("显示数据");
        adapter = new CommonAdapter<CertificateBean>(getContext(), R.layout.item_certification, list) {
            @Override
            protected void convert(ViewHolder holder, CertificateBean bean, int position) {
                holder.setText(R.id.tv_ship_name, bean.getShipName())
                        .setText(R.id.tv_sub_name, bean.getSubcontractorName())
                        .setText(R.id.tv_certificate, bean.getCertificateOptions())
                        .setText(R.id.tv_modify_date, bean.getModifyTimeForStr())
                        .setText(R.id.tv_name, bean.getUserName())
                        .setText(R.id.tv_creator, bean.getCreatorName())
                        .setText(R.id.tv_remark, bean.getRemark());
            }
        };
        EmptyWrapper<CertificateBean> emptyWrapper = new EmptyWrapper<>(adapter);
        emptyWrapper.setEmptyView(R.layout.empty_view);
        recyclerView.setAdapter(emptyWrapper);
    }
}
