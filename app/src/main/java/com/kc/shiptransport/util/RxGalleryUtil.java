package com.kc.shiptransport.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
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
        RxGalleryFinalApi.getInstance(activity).setImageMultipleResultEvent(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
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
        RxGalleryFinalApi.getInstance(activity).setImageRadioResultEvent(new RxBusResultSubscriber<ImageRadioResultEvent>() {
            @Override
            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                listener.onEvent(imageRadioResultEvent);
            }
        }).open();
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
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {

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
        //errorDrawable = (errorDrawable == null) ? context.getResources().getDrawable(R.mipmap.load_error) : errorDrawable;
        //网络请求获取图片并设置
        Glide.with(context)
                .load(path) //图片地址
                .asBitmap()
                .placeholder(defaultDrawable)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) // 开启缓存
                .error(errorDrawable)
                .fitCenter()
                .thumbnail(0.2f)
                .into(imageView);
    }
}
