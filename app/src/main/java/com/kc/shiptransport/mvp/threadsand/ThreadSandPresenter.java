package com.kc.shiptransport.mvp.threadsand;

import android.content.Context;

import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.PartitionSBBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.threadsand.ThreadDetailInfo;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/7/7  8:50
 * @desc ${TODD}
 */

public class ThreadSandPresenter implements ThreadSandContract.Presenter {
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
     *
     * @param CurrentDate
     * @param CurrentBoatAccount
     */
    @Override
    public void getDates(final String CurrentDate, final String CurrentBoatAccount) {
        view.showLoading(true);
        // 抛砂分区
        Observable<Boolean> observable = dataRepository
                .GetConstructionLayerOptions()
                .subscribeOn(Schedulers.io());

        // 供砂船
        //        Observable<Boolean> conship =

        // 回显数据
        Observable<LogCurrentDateBean> log = dataRepository
                .GetConstructionBoatDefaultStartTime(CurrentDate, CurrentBoatAccount)
                .subscribeOn(Schedulers.io());

        Observable.zip(observable, log, new BiFunction<Boolean, LogCurrentDateBean, LogCurrentDateBean>() {
            @Override
            public LogCurrentDateBean apply(Boolean aBoolean, LogCurrentDateBean logCurrentDateBean) throws Exception {
                return logCurrentDateBean;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<LogCurrentDateBean, ObservableSource<LogCurrentDateBean>>() {
                    @Override
                    public ObservableSource<LogCurrentDateBean> apply(final LogCurrentDateBean logCurrentDateBean) throws Exception {
                        String startTime = logCurrentDateBean.getStartTime();
                        return dataRepository
                                .GetBoatShipItemNum(1000, 1, CurrentBoatAccount, startTime)
                                .subscribeOn(Schedulers.io())
                                .flatMap(new Function<Boolean, ObservableSource<LogCurrentDateBean>>() {
                                    @Override
                                    public ObservableSource<LogCurrentDateBean> apply(Boolean aBoolean) throws Exception {
                                        return Observable.create(new ObservableOnSubscribe<LogCurrentDateBean>() {
                                            @Override
                                            public void subscribe(ObservableEmitter<LogCurrentDateBean> e) throws Exception {
                                                e.onNext(logCurrentDateBean);
                                                e.onComplete();
                                            }
                                        });
                                    }
                                });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
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
     *
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
    public void commit(final String json, String shipNum, final String startTime, final String endTime, final boolean isUpdate) {
        view.showLoading(true);
        dataRepository
                .IsCurrentDataInTimeRangeForBoatDaily(shipNum, startTime, endTime)
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean || isUpdate) {
                            return dataRepository.InsertConstructionBoatThrowingSandRecord(json);
                        } else {
                            return Observable.create(new ObservableOnSubscribe<Boolean>() {
                                @Override
                                public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                                    e.onError(new RuntimeException("该施工船舶在此工作区间不能提交施工日报"));
                                    e.onComplete();
                                }
                            });
                        }
                    }
                })
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
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void commitBCF(final String json, String shipNum, String startTime, String endTime, final boolean isUpdate) {
        dataRepository
                .IsCurrentDataInTimeRangeForBoatDaily(shipNum, startTime, endTime)
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean || isUpdate) {
                            return dataRepository.InsertBCFBoatThrowingSandRecord(json);
                        } else {
                            return Observable.create(new ObservableOnSubscribe<Boolean>() {
                                @Override
                                public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                                    e.onError(new RuntimeException("该施工船舶在此工作区间不能提交施工日报"));
                                    e.onComplete();
                                }
                            });
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
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
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 抛砂明细
     *
     * @param itemID
     */
    @Override
    public void getDetailThreadInfo(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetConstructionBoatThrowingSandRecordByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThreadDetailInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ThreadDetailInfo threadDetailInfo) {
                        view.showDetailInfo(threadDetailInfo);
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
     * BCF明细
     *
     * @param itemID
     */
    @Override
    public void getDetailBCFInfo(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetBCFBoatThrowingSandRecordByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThreadDetailInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ThreadDetailInfo threadDetailInfo) {
                        view.showDetailInfo(threadDetailInfo);
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

}
