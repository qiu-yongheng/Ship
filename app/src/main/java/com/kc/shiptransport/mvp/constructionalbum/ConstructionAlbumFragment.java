package com.kc.shiptransport.mvp.constructionalbum;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/3/8  9:59
 * @desc ${TODD}
 */

public class ConstructionAlbumFragment extends Fragment implements ConstructionAlbumContract.View {
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
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.getAlbumList(1000, 1);
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
    }

    @Override
    public void ShowAlbumList(ConstructionAlbumBean bean) {
        swipeRefreshLayout.finishRefresh();
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
                                                        presenter.updateAlbum(dataBean.getItemID(), name, remark, position);
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
                    // TODO: 单击跳转
                    Bundle bundle = new Bundle();
                    ConstructionAlbumBean.DataBean dataBean = adapter.getDatas().get(position);

                    bundle.putString(ConstructionAlbumPictureActivity.ALBUM_NAME, dataBean.getAlbumName());
                    bundle.putString(ConstructionAlbumPictureActivity.ALBUM_REMARK, dataBean.getRemark());
                    bundle.putInt(ConstructionAlbumPictureActivity.ALBUM_ITEM, dataBean.getItemID());
                    ConstructionAlbumPictureActivity.startActivity(getContext(), bundle);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, final int position) {
                    // TODO: 长按
                    activity.showDailog("删除相册", "删除相册不能撤销, 是否删除?", "取消", "确定删除", new DialogInterface.OnClickListener() {
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
                                    presenter.deleteAlbum("ConstructionPictureAlbumRecords", String.valueOf(adapter.getDatas().get(position).getItemID()), "ConstructionPictureAttachmentRecords", "AlbumID", position);
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
}
