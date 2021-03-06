package com.kc.shiptransport.mvp.scannerimgselect;

import android.content.Context;
import android.util.Log;

import com.kc.shiptransport.data.bean.CommitPictureBean;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.SettingUtil;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * @author 邱永恒
 * @time 2017/7/1 11:19
 * @desc ${TODO}
 */

public class ScannerImgSelectPresenter implements ScannerImgSelectContract.Presenter {
    private final Context context;
    private final ScannerImgSelectContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable mCompositeDisposable;

    public ScannerImgSelectPresenter(Context context, ScannerImgSelectContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getImgList(int subID, int typeID, int activityID) {
        view.showLoading(true);
        Observable<List<ScannerImgListByTypeBean>> observable = null;
        switch (activityID) {
            case SettingUtil.TYPE_SCANNER: // 供应商航次信息扫描件
                observable = dataRepository
                        .GetSubcontractorPerfectBoatScannerAttachmentRecordByAttachmentTypeID(subID, typeID);
                break;
            case SettingUtil.TYPE_RECORDEDSAND: // 过砂记录
                observable = dataRepository
                        .GetOverSandAttachmentRecordsBySubcontractorInterimApproachPlanID(subID);
                break;
        }


        if (observable != null) {
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<ScannerImgListByTypeBean>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            mCompositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(@NonNull List<ScannerImgListByTypeBean> scannerImgListByTypeBeen) {
                            view.showImgList(scannerImgListByTypeBeen);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            view.showLoading(false);
                            view.showError(e.toString());

                            // TODO: 暂时返回空集合
                            view.showImgList(new ArrayList<ScannerImgListByTypeBean>());
                        }

                        @Override
                        public void onComplete() {
                            view.showLoading(false);
                        }
                    });
        } else {
            view.showLoading(false);
            view.showError("被观察者为空!");
        }
    }

    @Override
    public void commit(ImageMultipleResultEvent imageMultipleResultEvent, final int subID, final int typeID, String shipAccount, final int activity_type) {
        view.showLoading(true);
        dataRepository
                .getScanImgList(imageMultipleResultEvent, subID, typeID, shipAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<CommitPictureBean>>() {
                    @Override
                    public void accept(@NonNull List<CommitPictureBean> scanCommitBeen) throws Exception {
                        // 设置最大进度
                        view.showProgress(scanCommitBeen.size());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<CommitPictureBean>, ObservableSource<CommitPictureBean>>() {
                    @Override
                    public ObservableSource<CommitPictureBean> apply(@NonNull List<CommitPictureBean> scanCommitBeen) throws Exception {
                        return Observable.fromIterable(scanCommitBeen);
                    }
                })
                .map(new Function<CommitPictureBean, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CommitPictureBean commitPictureBean) throws Exception {
                        // 上传图片
                        commitImg(commitPictureBean, activity_type);
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        view.hideProgress();
                        Log.d("==", "上传图片完成");
                    }
                });

    }

    @Override
    public void getCommitImgList(ImageMultipleResultEvent imageMultipleResultEvent) {

    }

    /**
     * 上传图片, 更新进度
     *
     * @param bean
     * @param activity_type
     */
    @Override
    public void commitImg(CommitPictureBean bean, int activity_type) {
        Observable<Boolean> observable = null;
        switch (activity_type) {
            case SettingUtil.TYPE_SCANNER:
                observable = dataRepository.InsertSubcontractorPerfectBoatScannerAttachment(bean);
                break;
            case SettingUtil.TYPE_RECORDEDSAND:
                observable = dataRepository.InsertOverSandAttachment(bean);
                break;
        }

        if (observable == null) {
            view.showError("图片提交失败, 请重试");
            return;
        }

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (aBoolean) {
                            view.updateProgress();
                        } else {
                            // 提交失败, 保存
                            view.showError("图片提交失败, 请重试");
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

    @Override
    public void deleteImg(int itemID, int activity_type) {
        view.showLoading(true);

        Observable<Boolean> observable = null;
        switch (activity_type) {
            case SettingUtil.TYPE_SCANNER:
                observable = dataRepository.DeleteSubcontractorPerfectBoatScannerAttachmentByItemID(itemID);
                break;
            case SettingUtil.TYPE_RECORDEDSAND:
                observable = dataRepository.DeleteOverSandAttachmentRecordsByItemID(itemID);
                break;
        }

        if (observable == null) {
            view.showError("图片删除失败, 请重试");
            return;
        }

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showDeleteResult(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError("图片删除失败, 请重试");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 提交PDF
     *
     * @param path
     * @param subID
     * @param typeID
     * @param shipAccount
     */
    @Override
    public void commitPDF(String path, int subID, int typeID, String shipAccount) {
        view.showLoading(true);
        dataRepository
                .getPDFCommit(path, subID, typeID, shipAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<CommitPictureBean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull CommitPictureBean commitPictureBean) throws Exception {
                        return dataRepository.InsertSubcontractorPerfectBoatScannerAttachment(commitPictureBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showCommitPDFResult(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void downloadPDF(final String url) {
        view.showLoading(true);
        Disposable disposable = RxDownload.getInstance(context)
                .download(url)                       //只传url即可
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadStatus>() {
                    @Override
                    public void accept(DownloadStatus status) throws Exception {
                        //DownloadStatus为下载进度
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //下载失败
                        view.showLoading(false);
                        view.showError("下载失败, 请重试");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //下载成功
                        view.showLoading(false);
                        view.showPDF(url);
                    }
                });
    }
}
