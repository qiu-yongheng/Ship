package com.kc.shiptransport.mvp.scannerdetail;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.ScannerImage;
import com.kc.shiptransport.db.WeekTask;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:58
 * @desc ${TODD}
 */

public class ScannerDetailPresenter implements ScannerDetailContract.Presenter{
    private final Context context;
    private final ScannerDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public ScannerDetailPresenter(Context context, ScannerDetailContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getTitle(int position) {
        dataRepository
                .getWeekTaskForPosition(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeekTask>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull WeekTask weekTask) {
                        view.showTitle(weekTask.getShipName());
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
     * 根据itemID获取数据
     * @param position
     */
    @Override
    public void getDetailForItemID(int position) {
        view.showLoading(true);
        dataRepository
                .getWeekTaskForPosition(position)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<WeekTask, Observable<ScannerImage>>() {
                    @Override
                    public Observable<ScannerImage> apply(@NonNull WeekTask weekTask) throws Exception {
                        // 判断提交情况, 做不同处理
                        return dataRepository.getScannerImageByItemID(weekTask);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ScannerImage>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ScannerImage scannerImage) {
                        view.showDatas(scannerImage);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError("数据获取失败");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void start(int position) {
        // 获取船名
        getTitle(position);
        // 根据itemID获取数据
        getDetailForItemID(position);
    }
}
