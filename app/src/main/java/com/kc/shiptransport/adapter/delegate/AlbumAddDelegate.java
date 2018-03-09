package com.kc.shiptransport.adapter.delegate;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
import com.kc.shiptransport.view.custom.RatioImageView;
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

    public AlbumAddDelegate(Context context, Fragment fragment) {
        this.context = context;
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
    public void convert(ViewHolder holder, ConstructionAlbumPictureBean.DataBean dataBean, int position) {
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
                                    ImageView ivPreview = (ImageView) view.findViewById(R.id.iv_preview);
                                    RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recycler_view);
                                    EditText etRemark = (EditText) view.findViewById(R.id.et_remark);
                                    Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
                                    Button btnCommit = (Button) view.findViewById(R.id.btn_commit);

                                    for (CommitPictureBean bean : list) {
                                        RatioImageView ratioImageView = new RatioImageView(context);
                                    }
                                }
                            });



                            fragment.pushPicture();
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
}
