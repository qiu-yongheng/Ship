package com.kc.shiptransport.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.hse.HseDefectListBean;
import com.kc.shiptransport.data.bean.hse.imglist.AttachmentListBean;
import com.kc.shiptransport.data.bean.hse.rectification.HseRectificationBean;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.RxGalleryListener;
import cn.finalteam.rxgalleryfinal.ui.base.IMultiImageCheckedListener;

/**
 * @author qiuyongheng
 * @time 2017/6/20  16:21
 * @desc 选择图片, 显示图片的工具类
 */

public class RxGalleryUtil {
    /**
     * 单选图片
     * 不能裁剪
     * 可以预览
     * @param activity
     * @param listener
     */
    public static void getImagRadio(Activity activity, final OnRxGalleryRadioListener listener) {
        RxGalleryFinalApi.getInstance(activity).setImageMultipleResultEvent(new RxBusResultDisposable<ImageMultipleResultEvent>() {
            @Override
            protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                listener.onEvent(imageMultipleResultEvent);
            }
        }).open();
    }

    /**
     * 单选图片, 默认参数, 不可预览
     * @param activity
     * @param listener
     */
    public static void getImagRadioDefault(Activity activity, final OnRxGalleryRadioListener listener) {
        //1.打开单选图片，默认参数
        RxGalleryFinalApi.getInstance(activity).setImageRadioResultEvent(new RxBusResultDisposable<ImageRadioResultEvent>() {
            @Override
            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                listener.onEvent(imageRadioResultEvent);
            }
        }).open();
    }

    /**
     * 自定义多选图片(有图片数量限制)
     * @param context
     * @param currentSize
     * @param maxSize
     * @param listener
     */
    public static void getImagMultiple(final Context context,int currentSize, int maxSize, final OnRxGalleryRadioListener listener) {
        int size = maxSize - currentSize;
        if (size > 0) {
            getImagMultiple(context, maxSize, listener);
        } else {
            ToastUtil.tip(context, "已到达图片选择上限");
        }
    }

    /**
     * 自定义多选图片
     * @param context
     * @param maxSize
     * @param listener
     */
    public static void getImagMultiple(final Context context, int maxSize, final OnRxGalleryRadioListener listener) {
        RxGalleryFinal
                .with(context)
                .image()
                .multiple()
                .maxSize(maxSize)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {

                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        Toast.makeText(context, "已选择" + imageMultipleResultEvent.getResult().size() + "张图片", Toast.LENGTH_SHORT).show();
                        listener.onEvent(imageMultipleResultEvent);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        Toast.makeText(context, "OVER", Toast.LENGTH_SHORT).show();
                    }
                })
                .openGallery();

        /**
         * 自定义的多选事件都会在这里执行
         */
        //得到多选的事件
        RxGalleryListener.getInstance().setMultiImageCheckedListener(new IMultiImageCheckedListener() {
            @Override
            public void selectedImg(Object t, boolean isChecked) {
                //这个主要点击或者按到就会触发，所以不建议在这里进行Toast
            }

            @Override
            public void selectedImgMax(Object t, boolean isChecked, int maxSize) {
                Toast.makeText(context, "你最多只能选择" + maxSize + "张图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 自定义的多选事件都会在这里执行
     */
    public void getMultiListener(final Context context) {
        //得到多选的事件
        RxGalleryListener.getInstance().setMultiImageCheckedListener(new IMultiImageCheckedListener() {
            @Override
            public void selectedImg(Object t, boolean isChecked) {
                //这个主要点击或者按到就会触发，所以不建议在这里进行Toast
            }

            @Override
            public void selectedImgMax(Object t, boolean isChecked, int maxSize) {
                Toast.makeText(context, "你最多只能选择" + maxSize + "张图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 显示图片到控件
     * @param context
     * @param path 图片地址 (本地, 网络)
     * @param defaultDrawable 默认显示图片
     * @param errorDrawable 错误显示的图片
     * @param imageView 图片空间
     */
    public static void showImage(Context context, Object path, Drawable defaultDrawable, Drawable errorDrawable, ImageView imageView) {
        defaultDrawable = (defaultDrawable == null) ? context.getResources().getDrawable(R.mipmap.load) : defaultDrawable;
        errorDrawable = (errorDrawable == null) ? context.getResources().getDrawable(R.mipmap.load_error) : errorDrawable;
        //网络请求获取图片并设置
        Glide.with(context)
                //图片地址
                .load(path)
                .asBitmap()
                .placeholder(defaultDrawable)
                // 开启缓存
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(errorDrawable)
                .fitCenter()
                .thumbnail(0.2f)
                .into(imageView);
    }

    /**
     * 将选择的图片转换成指定列表数据
     * @param imageMultipleResultEvent
     * @return
     */
    public static List<ImgList> MultipletoImgList(ImageMultipleResultEvent imageMultipleResultEvent) {
        List<ImgList> list = new ArrayList<>();
        for (MediaBean bean : imageMultipleResultEvent.getResult()) {
            ImgList imgList = new ImgList();
            imgList.setPath(bean.getOriginalPath());
            list.add(imgList);
        }
        return list;
    }

    /**
     * 图片转换
     * @param list
     * @return
     */
    public static List<ImgList> toImgList(List<AttachmentListBean> list) {
        List<ImgList> imgLists = new ArrayList<>();
        if (list == null) {
            return imgLists;
        }
        for (AttachmentListBean bean : list) {
            ImgList imgList = new ImgList();
            imgList.setItemID(bean.getItemID());
            imgList.setPath(bean.getFilePath());
            imgLists.add(imgList);
        }
        return imgLists;
    }

    /**
     * 整改图片转换
     * @param list
     * @return
     */
    public static List<ImgList> rectificationToImgList(List<HseRectificationBean.RectificationAttachmentListBean> list) {
        List<ImgList> imgLists = new ArrayList<>();
        if (list == null) {
            return imgLists;
        }
        for (HseRectificationBean.RectificationAttachmentListBean bean : list) {
            ImgList imgList = new ImgList();
            imgList.setItemID(bean.getItemID());
            imgList.setPath(bean.getFilePath());
            imgLists.add(imgList);
        }
        return imgLists;
    }

    /**
     * 缺陷图片转换
     * @param list
     * @return
     */
    public static List<ImgList> defectToImgList(List<HseRectificationBean.DefectAttachmentListBean> list) {
        List<ImgList> imgLists = new ArrayList<>();
        if (list == null) {
            return imgLists;
        }
        for (HseRectificationBean.DefectAttachmentListBean bean : list) {
            ImgList imgList = new ImgList();
            imgList.setItemID(bean.getItemID());
            imgList.setPath(bean.getFilePath());
            imgLists.add(imgList);
        }
        return imgLists;
    }

    /**
     * 缺陷图片转换
     * @param list
     * @return
     */
    public static List<ImgList> defectListToImgList(List<HseDefectListBean.DefectAttachmentList> list) {
        List<ImgList> imgLists = new ArrayList<>();
        if (list == null) {
            return imgLists;
        }
        for (HseDefectListBean.DefectAttachmentList bean : list) {
            ImgList imgList = new ImgList();
            imgList.setItemID(bean.getItemID());
            imgList.setPath(bean.getFilePath());
            imgLists.add(imgList);
        }
        return imgLists;
    }

    /**
     * 获取未提交的图片
     * @param source
     * @return
     */
    public static List<ImgList> getNoCommitImg(List<ImgList> source) {
        List<ImgList> list = new ArrayList<>();
        if (source == null) {
            return new ArrayList<>();
        }

        for (ImgList bean : source) {
            if (bean.getItemID() == 0) {
                list.add(bean);
            }
        }

        return list;
    }
}
