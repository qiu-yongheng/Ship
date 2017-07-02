package com.kc.shiptransport.interfaze;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * @author qiuyongheng
 * @time 2017/6/20  16:25
 * @desc 选择图片的回调
 */

public interface OnRxGalleryRadioListener {
    void onEvent(ImageMultipleResultEvent imageMultipleResultEvent);

    void onEvent(ImageRadioResultEvent imageRadioResultEvent);
}
