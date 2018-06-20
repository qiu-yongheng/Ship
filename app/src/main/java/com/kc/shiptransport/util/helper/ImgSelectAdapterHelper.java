package com.kc.shiptransport.util.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.mvp.basemvp.ImgSelectAdapter;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.view.actiivty.ImgViewPageActivity;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * @author 邱永恒
 * @time 2018/6/10  17:32
 * @desc 对图片选择adapter操作逻辑的封装
 */

public class ImgSelectAdapterHelper {
    private ImgSelectAdapter adapter;
    /** 需要请求删除的图片列表 */
    private List<ImgList> deleteImgLists = new ArrayList<>();

    /**
     * 获取adapter
     *
     * @param context
     * @param imgLists
     * @param isCanChange
     * @return
     */
    public ImgSelectAdapter getSelectAdapter(final Context context, List<ImgList> imgLists, boolean isCanChange) {
        adapter = new ImgSelectAdapter(context, imgLists, isCanChange);
        adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClick(View view, final int position, int... type) {
                switch (type[0]) {
                    case 0: // 预览
                        ImgViewPageActivity.startActivity(context, (ArrayList<ImgList>) adapter.getDatas(), position);
                        break;
                    case SettingUtil.ITEM_DELETE: // 删除
                        ((BaseActivity) context).showDailog("删除图片", "是否删除图片", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 如果itemID不为0, 保存到待删除列表
                                if (adapter.getDatas().get(position).getItemID() != 0) {
                                    deleteImgLists.add(adapter.getDatas().get(position));
                                }

                                // 从图片列表删除
                                adapter.removeItem(position);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // 弹出图片选择器
                RxGalleryUtil.getImagMultiple(context, SettingUtil.NUM_IMAGE_SELECTION, new OnRxGalleryRadioListener() {
                    @Override
                    public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                        List<ImgList> list = RxGalleryUtil.MultipletoImgList(imageMultipleResultEvent);
                        adapter.loadmore(list);
                    }

                    @Override
                    public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                    }
                });
            }
        });

        return adapter;
    }

    /**
     * 获取图片列表
     *
     * @return
     */
    public List<ImgList> getImgList() {
        return adapter.getDatas();
    }

    /**
     * 获取未提交的图片列表
     *
     * @return
     */
    public List<ImgList> getNoCommitImgList() {
        return RxGalleryUtil.getNoCommitImg(adapter.getDatas());
    }

    /**
     * 获取删除图片数据集
     *
     * @return
     */
    public List<ImgList> getDeleteImgList() {
        return deleteImgLists;
    }

    /**
     * 添加图片
     * @param data
     */
    public void addData(List<ImgList> data) {
        adapter.loadmore(data);
    }

    /**
     * 设置是否可以编辑
     * @param isCanChange
     */
    public void isCanChange(boolean isCanChange) {
        adapter.setIsCanChange(isCanChange);
        adapter.notifyDataSetChanged();
    }
}
