package com.kc.shiptransport.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;

import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

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
        RxGalleryFinalApi.getInstance(activity).setImageMultipleResultEvent(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
            @Override
            protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                listener.onEvent(imageMultipleResultEvent);
            }
        }).open();
    }

    /**
     * 显示图片到控件
     * @param context
     * @param path 图片地址 (本地, 网络)
     * @param defaultDrawable 默认显示图片
     * @param errorDrawable 错误显示的图片
     * @param imageView 图片空间
     */
    public static void showImage(Context context, String path, Drawable defaultDrawable, Drawable errorDrawable, ImageView imageView) {
        //网络请求获取图片并设置
        Glide.with(context)
                .load(path) //图片地址
                .asBitmap()
                .placeholder(defaultDrawable)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) // 开启缓存
                .error(errorDrawable)
                .centerCrop()
                .into(imageView);
    }
}
