package com.kc.shiptransport.mvp.about;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.versionupdate.VersionUpdate;
import com.kc.shiptransport.download.DownloadService;
import com.kc.shiptransport.util.AppInfoUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/7/17  9:56
 * @desc ${TODD}
 */

public class AboutPresenter {
    private final AboutActivity activity;
    private int version = 0;
    private final DataRepository dataRepository;

    public AboutPresenter(AboutActivity activity) {
        this.activity = activity;
        dataRepository = new DataRepository();
    }

    /**
     * 检查是否需要更新
     *
     */
    public void checkVersionCode() {
        dataRepository
                .GetNewVersion(AppInfoUtils.getVersionCode(activity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VersionUpdate>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull VersionUpdate versionUpdate) {
                        if (versionUpdate.getItemID() == 0) {
                            // 当前版本已是最新
                        } else {
                            /** 更新版本, 弹窗提醒 */
                            activity.showDialog(versionUpdate);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
     * @param versionUpdate
     */
    public void startDownload(DownloadService.DownloadBinder downloadBinder, VersionUpdate versionUpdate) {
        String url = versionUpdate.getFilePath();
        downloadBinder.startDownload(url);
    }
}