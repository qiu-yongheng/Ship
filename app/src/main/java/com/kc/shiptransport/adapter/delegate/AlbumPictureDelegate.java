package com.kc.shiptransport.adapter.delegate;

import android.content.Context;
import android.content.DialogInterface;
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
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.mvp.constructionalbumpicture.ConstructionAlbumPictureActivity;
import com.kc.shiptransport.mvp.constructionalbumpicture.ConstructionAlbumPictureFragment;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.view.actiivty.ImgViewPageActivity;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

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

    public AlbumPictureDelegate(Context context, Fragment fragment) {
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
        holder.setTextLine(R.id.tv_remark, TextUtils.isEmpty(dataBean.getSummary()) ? "添加摘要" : dataBean.getSummary())
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
                                etRemark.setText(TextUtils.isEmpty(dataBean.getSummary()) ? "" : dataBean.getSummary());

                                ivSmall.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        RxGalleryUtil.getImagMultiple(context, 8, new OnRxGalleryRadioListener() {
                                            @Override
                                            public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                                // 提交图片
                                                LogUtil.d("多选图片");
                                            }

                                            @Override
                                            public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                                                // 单选回调
                                                LogUtil.d("单选图片");
                                            }
                                        });
                                    }
                                });
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

                                        ((ConstructionAlbumPictureActivity) context).hideCustomDialog();
                                    }
                                });

                            }
                        });
                    }
                })
                .setOnClickListener(R.id.iv_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 展示图片
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
                })
                .setOnLongClickListener(R.id.iv_img, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        activity.showDailog("删除图片", "删除图片不能撤销, 是否删除?", "取消", "删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 取消
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 删除
                                fragment.deleteImg(dataBean.getItemID(), position);
                            }
                        });
                        return true;
                    }
                });
    }
}
