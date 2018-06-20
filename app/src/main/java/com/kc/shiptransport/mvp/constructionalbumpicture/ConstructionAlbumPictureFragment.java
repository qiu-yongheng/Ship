package com.kc.shiptransport.mvp.constructionalbumpicture;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.adapter.AlbumAdapter;
import com.kc.shiptransport.adapter.delegate.AlbumAddDelegate;
import com.kc.shiptransport.data.bean.CommitPictureBean;
import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnInitDialogViewListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.litepal.crud.DataSupport;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * @author 邱永恒
 * @time 2018/3/8  17:10
 * @desc ${TODD}
 */

public class ConstructionAlbumPictureFragment extends Fragment implements ConstructionAlbumPictureContract.View, ActionMode.Callback {
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
    private CommitPictureBean commitPictureBean;
    private boolean isSlidingToLast;
    private int pageSize = 20;
    private int pageCount = 1;
    public ActionMode actionMode;
    public Set<Integer> positionSet = new HashSet<>();

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_hse_check, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_add:
                User user = DataSupport.findFirst(User.class);
                if (!"1".equals(user.getIsConstructionPictureAdmin()) && !user.getUserID().equals(activity.albumCreator)) {
                    // 不是管理员 and 不是自己创建的相册
                    showError("这不是您创建的相册, 不能添加图片");
                } else {
                    addPicture();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addPicture() {
        RxGalleryUtil.getImagMultiple(getContext(), 8, new OnRxGalleryRadioListener() {
            @Override
            public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                // 提交图片
                final List<CommitPictureBean> list = RxGalleryUtil.multipleToCommitPictureList(imageMultipleResultEvent);
                activity.showCustomDialog(R.layout.dialog_commit_picture, new OnInitDialogViewListener() {
                    @Override
                    public void init(View view) {
                        final ImageView ivPreview = (ImageView) view.findViewById(R.id.iv_preview);
                        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recycler_view);
                        final EditText etRemark = (EditText) view.findViewById(R.id.et_remark);
                        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
                        Button btnCommit = (Button) view.findViewById(R.id.btn_commit);

                        // 初始化图片列表
                        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        final AlbumAddDelegate.PictureAdapter pictureAdapter = new AlbumAddDelegate.PictureAdapter(getContext(), R.layout.item_album_picture_edit, list, new AlbumAddDelegate.OnSelectListener() {
                            @Override
                            public void onSelect(CommitPictureBean bean) {
                                // 显示选中图片
                                commitPictureBean = bean;
                                RxGalleryUtil.showImage(getContext(), bean.getFilePath(), null, null, ivPreview);

                                // 显示摘要
                                etRemark.setText(bean.getSummary());
                            }
                        });
                        recyclerview.setAdapter(pictureAdapter);

                        // 设置editText监听
                        etRemark.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                // 更新备注
                                String remark = s.toString();
                                if (commitPictureBean != null) {
                                    commitPictureBean.setSummary(remark);
                                }
                            }
                        });

                        // 点击监听
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activity.hideCustomDialog();
                            }
                        });
                        btnCommit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activity.hideCustomDialog();
                                pushPicture(activity.albumItem, list);
                            }
                        });
                    }
                });
            }

            @Override
            public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                // 单选回调
                LogUtil.d("单选图片");
            }
        });
    }

    @Override
    public void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageCount = 1;
                presenter.getImgList(pageSize, pageCount, activity.albumItem);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 当滑动到最后一个item时, 加载更多
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取布局管理器
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取最后一个完全显示的item position
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    // 获取当前显示的item的数量
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多
                        presenter.getImgList(pageSize, ++pageCount, activity.albumItem);
                    }
                }
            }

            /**
             * 判断是否下滑
             * @param recyclerView
             * @param dx
             * @param dy
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
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

        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.finishRefresh();
        }
        if (presenter != null) {
            presenter.unsubscribe();
        }
    }

    public void deleteImg(List<ConstructionAlbumPictureBean.DataBean> datas, Set<Integer> positionSet) {
        presenter.deleteImg("ConstructionPictureAttachmentRecords", activity.albumCreator, datas, positionSet);
    }

    /**
     * 提交图片
     */
    public void pushPicture(int albumID, List<CommitPictureBean> list) {
        presenter.commitPicture(albumID, list);
    }

    @Override
    public void showImgList(ConstructionAlbumPictureBean bean) {
        swipeRefreshLayout.finishRefresh();
        List<ConstructionAlbumPictureBean.DataBean> data = bean.getData();
        if (adapter == null) {
            adapter = new AlbumAdapter(getActivity(), this, activity.albumItem, data);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.loadmore(data);
        }
    }

    @Override
    public void showImgMore(ConstructionAlbumPictureBean bean) {
        List<ConstructionAlbumPictureBean.DataBean> data = bean.getData();
        if (adapter != null && data != null && data.size() > 0) {
            adapter.loadmore(data);
        } else {
            ToastUtil.tip(getContext(), "没有更多数据");
        }
    }

    @Override
    public void showDeleteResult(boolean isSuccess, Set<Integer> position) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "删除相册成功!");
            swipeRefreshLayout.autoRefresh();
//            for (int item : position) {
//                adapter.removeItem(item);
//            }
//            if (position < 0) {
//                showError("多选删除");
//                swipeRefreshLayout.autoRefresh();
//            } else {
//
//            }
        } else {
            showError("删除失败, 请重试");
        }
    }

    @Override
    public void showCommitPictureResult(boolean isSuccess) {

    }

    @Override
    public void showProgress(int size) {
        activity.progressDialog("提交图片", size, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 取消任务
                presenter.unsubscribe();
            }
        });
    }

    @Override
    public void updateProgress() {
        activity.updataProgress(new OnProgressFinishListener() {
            @Override
            public void onFinish() {
                // 全部上传成功的回调
                swipeRefreshLayout.autoRefresh(100);
            }
        });
    }

    /**
     * 添加选中的position到HashSet
     *
     * @param position
     */
    public void addOrRemove(int position) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            positionSet.remove(position);
        } else {
            // 如果不包含，则添加
            positionSet.add(position);
        }
        if (positionSet.size() == 0) {
            // 如果没有选中任何的item，则退出多选模式
            actionMode.finish();
        } else {
            // 设置ActionMode标题
            actionMode.setTitle(positionSet.size() + " 已选择");
            // 更新列表界面，否则无法显示已选的item
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        if (actionMode == null) {
            actionMode = mode;
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_delete, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                final HashSet<Integer> tempSet;
                tempSet = new HashSet<>();
                tempSet.addAll(positionSet);
                activity.showDailog("删除图片", "删除图片不能撤销, 是否删除?", "取消", "删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 取消
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 删除
                        deleteImg(adapter.getDatas(), tempSet);
                    }
                });

                mode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        positionSet.clear();
        adapter.notifyDataSetChanged();
    }
}
