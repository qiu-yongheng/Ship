package com.kc.shiptransport.mvp.constructionalbumpicture;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;
import com.kc.shiptransport.adapter.AlbumAdapter;
import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/3/8  17:10
 * @desc ${TODD}
 */

public class ConstructionAlbumPictureFragment extends Fragment implements ConstructionAlbumPictureContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SmartRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private ConstructionAlbumPictureContract.Presenter presenter;
    private ConstructionAlbumPictureActivity activity;
    private AlbumAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_construction_album_picture, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.autoRefresh();
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (ConstructionAlbumPictureActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle(activity.albumName);

        swipeRefreshLayout.setEnableHeaderTranslationContent(false);
        swipeRefreshLayout.setEnableLoadmore(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.getImgList(activity.albumItem);
            }
        });
    }

    @Override
    public void setPresenter(ConstructionAlbumPictureContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中", new OnDailogCancleClickListener() {
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
        swipeRefreshLayout.finishRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void deleteImg(int itemID, int position) {
        presenter.deleteImg("ConstructionPictureAttachmentRecords", String.valueOf(itemID), "", "", position);
    }

    /**
     * 提交图片
     */
    public void pushPicture() {

    }

    @Override
    public void showImgList(ConstructionAlbumPictureBean bean) {
        swipeRefreshLayout.finishRefresh();
        List<ConstructionAlbumPictureBean.DataBean> data = bean.getData();
        if (adapter == null) {
            adapter = new AlbumAdapter(getActivity(), this, data);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.loadmore(data);
        }
    }

    @Override
    public void showDeleteResult(boolean isSuccess, int position) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "删除相册成功!");
            adapter.removeItem(position);
        } else {
            showError("删除失败, 请重试");
        }
    }
}
