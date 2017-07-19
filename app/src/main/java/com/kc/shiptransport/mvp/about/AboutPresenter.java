package com.kc.shiptransport.mvp.about;

import com.kc.shiptransport.download.DownloadService;
import com.kc.shiptransport.util.AppInfoUtils;

/**
 * @author qiuyongheng
 * @time 2017/7/17  9:56
 * @desc ${TODD}
 */

public class AboutPresenter {
    private final AboutActivity activity;
    private int version = 0;

    public AboutPresenter(AboutActivity activity) {
        this.activity = activity;
    }

    /**
     * 检查是否需要更新
     *
     */
    public void checkVersionCode() {
        //判断版本
        if (version > AppInfoUtils.getVersionCode(activity) || true) {
            /** 更新版本, 弹窗提醒 */
            activity.showDialog();
        } else {
            /** 不需要更新 */
        }
    }

    /**
     * 取消更新
     *
     * @param downloadBinder
     */
    public void cancelDownload(DownloadService.DownloadBinder downloadBinder) {
        downloadBinder.cancelDownload();
    }

    /**
     * 开始下载
     * @param downloadBinder
     */
    public void startDownload(DownloadService.DownloadBinder downloadBinder) {
        String url = "https://dl.hdslb.com/mobile/latest/iBiliPlayer-bili.apk";
        downloadBinder.startDownload(url);
    }
}