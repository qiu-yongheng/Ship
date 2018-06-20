package com.kc.shiptransport.mvp.constructionalbum;

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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.album.ConstructionAlbumBean;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnInitDialogViewListener;
import com.kc.shiptransport.mvp.constructionalbumpicture.ConstructionAlbumPictureActivity;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/3/8  9:59
 * @desc ${TODD}
 */

public class ConstructionAlbumFragment extends Fragment implements ConstructionAlbumContract.View{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.swipe_refresh_layout)
    SmartRefreshLayout swipeRefreshLayout;
    private ConstructionAlbumActivity activity;
    private ConstructionAlbumContract.Presenter presenter;
    private CommonAdapter<ConstructionAlbumBean.DataBean> adapter;
    private boolean isSlidingToLast;
    private int pageSize = 20;
    private int pageCount = 1;
    public ActionMode actionMode;
    public Set<Integer> positionSet = new HashSet<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_construction_album, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.autoRefresh();
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (ConstructionAlbumActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle(R.string.title_album);

        swipeRefreshLayout.setEnableHeaderTranslationContent(false);
        swipeRefreshLayout.setEnableLoadmore(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
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
                activity.showCustomDialog(R.layout.dialog_add_album, new OnInitDialogViewListener() {
                    @Override
                    public void init(View view) {
                        final EditText etName = (EditText) view.findViewById(R.id.et_name);
                        final EditText etRemark = (EditText) view.findViewById(R.id.et_remark);
                        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
                        Button btnCreate = (Button) view.findViewById(R.id.btn_create);

                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activity.hideCustomDialog();
                            }
                        });

                        btnCreate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = etName.getText().toString().trim();
                                String remark = etRemark.getText().toString().trim();
                                if (!TextUtils.isEmpty(name)) {
                                    presenter.insertAlbum(name, remark);
                                    activity.hideCustomDialog();
                                } else {
                                    ToastUtil.tip(getContext(), "请填写相册名");
                                }
                            }
                        });
                    }
                });
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initListener() {
        // 下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageCount = 1;
                presenter.getAlbumList(pageSize, pageCount);
            }
        });

        // 加载更多
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
                        presenter.getAlbumList(pageSize, ++pageCount);
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
    public void setPresenter(ConstructionAlbumContract.Presenter presenter) {
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

    @Override
    public void showAlbumList(ConstructionAlbumBean bean) {
        ToastUtil.tip(getContext(), "加载成功");
        swipeRefreshLayout.finishRefresh();
        final User user = DataSupport.findFirst(User.class);
        final List<ConstructionAlbumBean.DataBean> data = bean.getData();
        if (adapter == null) {
            adapter = new CommonAdapter<ConstructionAlbumBean.DataBean>(getContext(), R.layout.item_album, data) {

                @Override
                protected void convert(ViewHolder holder, final ConstructionAlbumBean.DataBean dataBean, final int position) {
                    try {
                        String attachment = dataBean.getAttachment();
                        String[] split = attachment.split(",");
                        ImageView iv = holder.getView(R.id.iv_img);
                        RxGalleryUtil.showImage(getContext(), split[0], null, null, iv);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.tip(getContext(), e.getMessage());
                    }
                    holder.setText(R.id.tv_count, dataBean.getTotalNumber() + "张")
                            .setText(R.id.tv_name, dataBean.getAlbumName())
                            // 如果是当前用户, 或者是管理员, 可以修改
                            .setVisible(R.id.tv_update, user.getUserID().equals(dataBean.getCreator()) || "1".equals(user.getIsConstructionPictureAdmin()))
                            .setOnClickListener(R.id.tv_update, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    activity.showCustomDialog(R.layout.dialog_add_album, new OnInitDialogViewListener() {
                                        @Override
                                        public void init(View view) {
                                            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
                                            final EditText etName = (EditText) view.findViewById(R.id.et_name);
                                            final EditText etRemark = (EditText) view.findViewById(R.id.et_remark);
                                            Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
                                            Button btnCreate = (Button) view.findViewById(R.id.btn_create);
                                            tvTitle.setText(dataBean.getAlbumName());
                                            btnCreate.setText("修改");
                                            etName.setText(TextUtils.isEmpty(dataBean.getAlbumName()) ? "" : dataBean.getAlbumName());
                                            etRemark.setText(TextUtils.isEmpty(dataBean.getRemark()) ? "" : dataBean.getRemark());

                                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    activity.hideCustomDialog();
                                                }
                                            });

                                            btnCreate.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String name = etName.getText().toString().trim();
                                                    String remark = etRemark.getText().toString().trim();
                                                    if (!TextUtils.isEmpty(name)) {
                                                        // TODO
                                                        presenter.updateAlbum(dataBean.getItemID(), dataBean.getCreator(), name, remark, position);
                                                        activity.hideCustomDialog();
                                                    } else {
                                                        ToastUtil.tip(getContext(), "请填写相册名");
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                }
            };
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                    if (actionMode != null) {
//                        // 如果当前处于多选状态，则进入多选状态的逻辑
//                        // 维护当前已选的position
//                        addOrRemove(position);
//                    } else {
                        // 如果不是多选状态，则进入点击事件的业务逻辑
                        // TODO: 单击跳转
                        Bundle bundle = new Bundle();
                        ConstructionAlbumBean.DataBean dataBean = adapter.getDatas().get(position);

                        bundle.putString(ConstructionAlbumPictureActivity.ALBUM_NAME, dataBean.getAlbumName());
                        bundle.putString(ConstructionAlbumPictureActivity.ALBUM_REMARK, dataBean.getRemark());
                        bundle.putInt(ConstructionAlbumPictureActivity.ALBUM_ITEM, dataBean.getItemID());
                        bundle.putString(ConstructionAlbumPictureActivity.ALBUM_CREATOR, dataBean.getCreator());
                        ConstructionAlbumPictureActivity.startActivity(getContext(), bundle);
//                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, final int position) {
//                    if (actionMode == null) {
//                        // 创建actionMode
//                        actionMode = activity.startSupportActionMode(ConstructionAlbumFragment.this);
//                    }

                    // TODO: 长按
                    activity.showDailog("删除相册", "删除相册不能撤销, 是否删除?", "取消", "删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 取消
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 再次询问是否删除
                            activity.showDailog("删除相册", "真的要删除吗?", "还是算了", "确定删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 取消
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 删除
                                    if (Integer.valueOf(adapter.getDatas().get(position).getTotalNumber()) > 0) {
                                        presenter.deleteAlbum("ConstructionPictureAlbumRecords", String.valueOf(adapter.getDatas().get(position).getItemID()), "", "", adapter.getDatas().get(position).getCreator(), position);
                                    } else {
                                        presenter.deleteAlbum("ConstructionPictureAlbumRecords", String.valueOf(adapter.getDatas().get(position).getItemID()), "ConstructionPictureAttachmentRecords", "AlbumID", adapter.getDatas().get(position).getCreator(), position);
                                    }
                                }
                            });
                        }
                    });
                    return true;
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.loadmore(data);
        }
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

    /**
     * 加载更多
     * @param bean
     */
    @Override
    public void showAlbumMore(ConstructionAlbumBean bean) {
        List<ConstructionAlbumBean.DataBean> data = bean.getData();
        if (adapter != null && data != null && data.size() > 0) {
            ToastUtil.tip(getContext(), "加载成功");
            adapter.loadmore(data);
        } else {
            ToastUtil.tip(getContext(), "没有更多数据");
        }
    }

    @Override
    public void showInsertResult(boolean isSuccess, String albumName, String remark, int position) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "修改相册成功!");
            adapter.getDatas().get(position).setAlbumName(albumName);
            adapter.getDatas().get(position).setRemark(remark);
            adapter.notifyItemChanged(position);
        } else {
            showError("修改失败, 请重试");
        }
    }

    @Override
    public void showUpdateResult(boolean isSuccess) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "新增相册成功!");
            swipeRefreshLayout.autoRefresh(500);
        } else {
            showError("新增失败, 请重试");
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

//    @Override
//    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//        if (actionMode == null) {
//            actionMode = mode;
//            MenuInflater inflater = mode.getMenuInflater();
//            inflater.inflate(R.menu.menu_delete, menu);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//        return false;
//    }
//
//    @Override
//    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_delete:
//                activity.showDailog("删除相册", "删除相册不能撤销, 是否删除?", "取消", "删除", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 取消
//                    }
//                }, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 删除
//                        HashSet<Integer> tempSet;
//                        tempSet = new HashSet<>();
//                        tempSet.addAll(positionSet);
//                        deleteAlbum(adapter.getDatas(), tempSet);
//                    }
//                });
//
//                mode.finish();
//                return true;
//            default:
//                return false;
//        }
//    }
//
//    @Override
//    public void onDestroyActionMode(ActionMode mode) {
//        actionMode = null;
//        positionSet.clear();
//        adapter.notifyDataSetChanged();
//    }
}
