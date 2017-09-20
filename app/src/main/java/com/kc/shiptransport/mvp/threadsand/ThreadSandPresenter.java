package com.kc.shiptransport.mvp.threadsand;

import android.content.Context;

import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.PartitionSBBean;
import com.kc.shiptransport.data.bean.threadsandlog.ThreadSandLogBean;
import com.kc.shiptransport.data.source.DataRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/7/7  8:50
 * @desc ${TODD}
 */

public class ThreadSandPresenter implements ThreadSandContract.Presenter{
    private final Context context;
    private final ThreadSandContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public ThreadSandPresenter(Context context, ThreadSandContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        this.dataRepository = dataRepository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    /**
     * 获取显示数据, 分区
     * @param CurrentDate
     * @param CurrentBoatAccount
     */
    @Override
    public void getDates(String CurrentDate, String CurrentBoatAccount) {
        view.showLoading(true);
        // 抛砂分区
        Observable<Boolean> observable = dataRepository
                .GetConstructionLayerOptions()
                .subscribeOn(Schedulers.io());

        // 供砂船
        Observable<Boolean> conship = dataRepository
                .GetBoatShipItemNum(1000, 1, CurrentBoatAccount)
                .subscribeOn(Schedulers.io());

        // 回显数据
        Observable<LogCurrentDateBean> log = dataRepository
                .GetConstructionBoatDefaultStartTime(CurrentDate, CurrentBoatAccount)
                .subscribeOn(Schedulers.io());

        Observable.zip(observable, conship, log, new Function3<Boolean, Boolean, LogCurrentDateBean, LogCurrentDateBean>() {
            @Override
            public LogCurrentDateBean apply(@NonNull Boolean aBoolean, @NonNull Boolean aBoolean2, @NonNull LogCurrentDateBean bean) throws Exception {
                return bean;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogCurrentDateBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull LogCurrentDateBean bean) {
                        view.showStartDate(bean);
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

    /**
     * 获取施工分区
     * @param userID
     */
    @Override
    public void getPartition(String userID) {
        dataRepository
                .getPartitionSBBean(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PartitionSBBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull PartitionSBBean partitionSBBean) {
                        view.showPartition(partitionSBBean);
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
    public void commit(String json) {
        view.showLoading(true);
        dataRepository
                .InsertConstructionBoatThrowingSandRecord(json)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showCommitResult(aBoolean);
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

    /**
     * 回显详细数据
     * @param itemID
     */
    @Override
    public void getDetailData(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetConstructionBoatThrowingSandList(itemID, "", "", "", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ThreadSandLogBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<ThreadSandLogBean> threadSandLogBeen) {
                        view.showDetailData(threadSandLogBeen);
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
    public void getConShip() {

    }
}
