package com.kc.shiptransport.mvp.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.mvp.contactsdetail.ContactDetailActivity;
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
 * @time 2017/9/19  9:55
 * @desc ${TODD}
 */

public class SearchFragment extends Fragment implements SearchContract.View {
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private SearchActivity activity;
    private SearchContract.Presenter presenter;
    private CommonAdapter<Contacts> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (SearchActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);

        searchView.setIconified(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.search(newText);
                return true;
            }
        });
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void showResult(List<Contacts> list) {
        if (list == null) {
            showError("搜索失败");
            return;
        }

        adapter = new CommonAdapter<Contacts>(getContext(), R.layout.item_contact_normal, list) {
            @Override
            protected void convert(ViewHolder holder, final Contacts contacts, int position) {
                holder.setText(R.id.textViewAvatar, TextUtils.isEmpty(contacts.getDepartment()) ? "" : contacts.getDepartment().substring(0, 1).toUpperCase())
                        .setText(R.id.text_view_name, TextUtils.isEmpty(contacts.getDepartment()) ? "" : contacts.getDepartment())
                        .setText(R.id.text_view_office, TextUtils.isEmpty(contacts.getDuties()) ? "" : contacts.getDuties())
                        .setText(R.id.text_view_phone, TextUtils.isEmpty(contacts.getMobile()) ? "" : contacts.getMobile())
                        .setText(R.id.text_view_english_name, TextUtils.isEmpty(contacts.getEnglishName()) ? "" : contacts.getEnglishName())
                        .setOnClickListener(R.id.ll, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO 跳转到用户详情界面
                                ContactDetailActivity.startActivity(getContext(), contacts);
                            }
                        });
            }
        };

        EmptyWrapper<Contacts> emptyWrapper = new EmptyWrapper<>(adapter);
        emptyWrapper.setEmptyView(R.layout.empty_view);

        recyclerView.setAdapter(emptyWrapper);
    }
}
