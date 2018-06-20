package com.kc.shiptransport.adapter.delegate;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.interfaze.OnInitDialogViewListener;
import com.kc.shiptransport.mvp.constructionalbumpicture.ConstructionAlbumPictureActivity;
import com.kc.shiptransport.mvp.constructionalbumpicture.ConstructionAlbumPictureFragment;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.actiivty.ImgViewPageActivity;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/3/9  10:34
 * @desc 相册图片展示类别
 */

public class AlbumPictureDelegate implements ItemViewDelegate<ConstructionAlbumPictureBean.DataBean> {
    private final Context context;
    private final ConstructionAlbumPictureActivity activity;
    private final ConstructionAlbumPictureFragment fragment;
    private ArrayList<ImgList> imgLists = new ArrayList<>();
    private List<ConstructionAlbumPictureBean.DataBean> datas;

    public AlbumPictureDelegate(Context context, int albumItem, Fragment fragment) {
        this.context = context;
        this.activity = ((ConstructionAlbumPictureActivity) context);
        this.fragment = ((ConstructionAlbumPictureFragment) fragment);
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_album_picture_show;
    }

    @Override
    public boolean isForViewType(List<ConstructionAlbumPictureBean.DataBean> dates, int position) {
        this.datas = dates;
        return position != dates.size();
    }

    @Override
    public void convert(final ViewHolder holder, final ConstructionAlbumPictureBean.DataBean dataBean, final int position) {
        ImageView iv = holder.getView(R.id.iv_img);
        RxGalleryUtil.showImage(context, dataBean.getFilePath(), null, null, iv);
        holder.setTextLine(R.id.tv_remark, TextUtils.isEmpty(dataBean.getSummary()) ? "暂无关键字" : dataBean.getSummary())
                .setOnClickListener(R.id.tv_remark, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.showCustomDialog(R.layout.dialog_add_album_picture, new OnInitDialogViewListener() {
                            @Override
                            public void init(View view) {
                                ImageView ivSmall = (ImageView) view.findViewById(R.id.iv_small);
                                EditText etRemark = (EditText) view.findViewById(R.id.et_remark);
                                Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
                                Button btnUpdate = (Button) view.findViewById(R.id.btn_update);
                                RxGalleryUtil.showImage(context, dataBean.getFilePath(), null, null, ivSmall);
                                etRemark.setText(TextUtils.isEmpty(dataBean.getSummary()) ? "暂无关键字" : dataBean.getSummary());

                                // TODO : 暂时取消编辑
                                etRemark.setFocusable(false);
                                etRemark.setFocusableInTouchMode(false);

//                                ivSmall.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        RxGalleryUtil.getImagMultiple(context, 8, new OnRxGalleryRadioListener() {
//                                            @Override
//                                            public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
//                                                // 提交图片
//                                                LogUtil.d("多选图片");
//                                            }
//
//                                            @Override
//                                            public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
//                                                // 单选回调
//                                                LogUtil.d("单选图片");
//                                            }
//                                        });
//                                    }
//                                });
                                btnCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ((ConstructionAlbumPictureActivity) context).hideCustomDialog();
                                    }
                                });
                                btnUpdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // TODO: 提交
                                        ToastUtil.tip(context, "功能未完善");
                                        ((ConstructionAlbumPictureActivity) context).hideCustomDialog();
                                    }
                                });

                            }
                        });
                    }
                })
                .setVisible(R.id.tv_bg, fragment.positionSet.contains(position))
                .setOnClickListener(R.id.iv_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fragment.actionMode != null) {
                            // 如果当前处于多选状态，则进入多选状态的逻辑
                            // 维护当前已选的position
                            fragment.addOrRemove(position);
                        } else {
                            // 如果不是多选状态，则进入点击事件的业务逻辑
                            imgLists.clear();
                            for (ConstructionAlbumPictureBean.DataBean bean : datas) {
                                ImgList imgList = new ImgList();
                                imgList.setItemID(bean.getItemID());
                                imgList.setPath(bean.getFilePath());
                                imgList.setRemark(bean.getSummary());
                                imgLists.add(imgList);
                            }
                            ImgViewPageActivity.startActivity(context, imgLists, position);
                        }
                    }
                })
                .setOnLongClickListener(R.id.iv_img, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        // 长按触发多选删除
                        if (fragment.actionMode == null) {
                            // 创建actionMode
                            fragment.actionMode = activity.startSupportActionMode(fragment);
                        }
                        return false;
                    }
                });
    }
}
