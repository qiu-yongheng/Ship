package com.kc.shiptransport.mvp.scannerimgselect;

import android.content.Context;
import android.util.Log;

import com.kc.shiptransport.data.bean.ScanCommitBean;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.data.source.DataRepository;

import java.util.List;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
    public void getImgList(int subID, int typeID) {
        view.showLoading(true);
        dataRepository
                .GetSubcontractorPerfectBoatScannerAttachmentRecordByAttachmentTypeID(subID, typeID)
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
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void commit(ImageMultipleResultEvent imageMultipleResultEvent, final int subID, final int typeID, String shipAccount) {
        dataRepository
                .getScanImgList(imageMultipleResultEvent, subID, typeID, shipAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<ScanCommitBean>>() {
                    @Override
                    public void accept(@NonNull List<ScanCommitBean> scanCommitBeen) throws Exception {
                        // 设置最大进度
                        view.showProgress(scanCommitBeen.size());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<ScanCommitBean>, ObservableSource<ScanCommitBean>>() {
                    @Override
                    public ObservableSource<ScanCommitBean> apply(@NonNull List<ScanCommitBean> scanCommitBeen) throws Exception {
                        return Observable.fromIterable(scanCommitBeen);
                    }
                })
                .map(new Function<ScanCommitBean, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull ScanCommitBean scanCommitBean) throws Exception {
                        // 上传图片
                        commitImg(scanCommitBean);
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
     */
    @Override
    public void commitImg(ScanCommitBean bean) {
        dataRepository
                .InsertSubcontractorPerfectBoatScannerAttachment(bean)
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
    public void deleteImg(int itemID) {
        view.showLoading(true);
        dataRepository
                .DeleteSubcontractorPerfectBoatScannerAttachmentByItemID(itemID)
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
}
