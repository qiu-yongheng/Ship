package com.kc.shiptransport.mvp.scannerdetail;

import android.content.Context;

import com.kc.shiptransport.data.bean.ScannerListBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.WeekTask;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
     * 获取扫描件类型
     */
    @Override
    public void getScannerType() {
        view.showLoading(true);
        dataRepository
                .getScannerType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ScannerListBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<ScannerListBean> scannerImage) {
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
        // 获取扫描件类型
        getScannerType();
    }
}
