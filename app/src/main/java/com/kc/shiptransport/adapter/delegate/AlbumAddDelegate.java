package com.kc.shiptransport.adapter.delegate;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.CommitPictureBean;
import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.kc.shiptransport.interfaze.OnInitDialogViewListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.mvp.constructionalbumpicture.ConstructionAlbumPictureActivity;
import com.kc.shiptransport.mvp.constructionalbumpicture.ConstructionAlbumPictureFragment;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * @author 邱永恒
 * @time 2018/3/9  10:35
 * @desc 相册图片添加
 */

public class AlbumAddDelegate implements ItemViewDelegate<ConstructionAlbumPictureBean.DataBean> {
    private final Context context;
    private final ConstructionAlbumPictureActivity activity;
    private final ConstructionAlbumPictureFragment fragment;
    private final int albumID;
    private static int oldPosition;
    private CommitPictureBean commitPictureBean;

    public AlbumAddDelegate(Context context, int albumItem, Fragment fragment) {
        this.context = context;
        this.albumID = albumItem;
        this.activity = ((ConstructionAlbumPictureActivity) context);
        this.fragment = ((ConstructionAlbumPictureFragment) fragment);
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_album_picture_add;
    }

    @Override
    public boolean isForViewType(List<ConstructionAlbumPictureBean.DataBean> dates, int position) {
        Log.d("AlbumAddDelegate", "item类型是否选用: " + (position == dates.size()) + ", position: " + position + ", 数据长度" + dates.size());
        return position == dates.size();
    }

    @Override
    public void convert(ViewHolder holder, final ConstructionAlbumPictureBean.DataBean dataBean, final int position) {
        holder.setOnClickListener(R.id.iv_img, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxGalleryUtil.getImagMultiple(context, 8, new OnRxGalleryRadioListener() {
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
                                recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                                final PictureAdapter pictureAdapter = new PictureAdapter(context, R.layout.item_album_picture_edit, list, new OnSelectListener() {

                                    @Override
                                    public void onSelect(CommitPictureBean bean) {
                                        // 显示选中图片
                                        commitPictureBean = bean;
                                        RxGalleryUtil.showImage(context, bean.getFilePath(), null, null, ivPreview);

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
                                        fragment.pushPicture(albumID, list);
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
        });
    }

    /**
     * adapter
     */
    public static class PictureAdapter extends CommonAdapter<CommitPictureBean> {

        private final OnSelectListener listener;

        public PictureAdapter(Context context, int layoutId, List<CommitPictureBean> datas, OnSelectListener listener) {
            super(context, layoutId, datas);
            this.listener = listener;
            // 默认选中第一张图片
            if (listener != null && datas != null && !datas.isEmpty()) {
                datas.get(0).setSelect(true);
                listener.onSelect(datas.get(0));
                oldPosition = 0;
            }
        }

        @Override
        protected void convert(ViewHolder holder, final CommitPictureBean commitPictureBean, final int position) {
            holder.setBackgroundRes(R.id.rl, commitPictureBean.isSelect() ? R.drawable.btn_red_bg : 0)
                    .setText(R.id.tv_remark, TextUtils.isEmpty(commitPictureBean.getSummary()) ? "暂无关键字" : commitPictureBean.getSummary())
                    .setVisible(R.id.tv_remark, !TextUtils.isEmpty(commitPictureBean.getSummary()))
                    .setOnClickListener(R.id.rl, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 清除旧的选中状态
                            getDatas().get(oldPosition).setSelect(false);
                            notifyItemChanged(oldPosition);

                            // 设置新的选中状态
                            commitPictureBean.setSelect(true);
                            notifyItemChanged(position);

                            oldPosition = position;

                            if (listener != null) {
                                listener.onSelect(commitPictureBean);
                            }
                        }
                    });
            ImageView imageView = holder.getView(R.id.iv_img);
            RxGalleryUtil.showImage(mContext, commitPictureBean.getFilePath(), null, null, imageView);
        }
    }

    /**
     * 选择回调
     */
    public interface OnSelectListener {
        /**
         * 返回选择的图片
         *
         * @param bean
         */
        void onSelect(CommitPictureBean bean);
    }
}
