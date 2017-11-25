package com.kc.shiptransport.mvp.downtime;

import android.content.Context;

import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.downlog.DownLogBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.down.StopOption;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/7/6  17:19
 * @desc ${TODD}
 */

public class DowntimePresenter implements DowntimeContract.Presenter {
    private final Context context;
    private final DowntimeContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public DowntimePresenter(Context context, DowntimeContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getStopOptions() {
        view.showLoading(true);
        dataRepository
                .GetStopOptions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<StopOption>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<StopOption> stopOptions) {
                        view.showGetStopOptions(stopOptions);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void getStartDate(String CurrentDate, String CurrentBoatAccount) {
        dataRepository
                .GetConstructionBoatDefaultStartTime(CurrentDate, CurrentBoatAccount)
                .subscribeOn(Schedulers.io())
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
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void stop(final int itemID, final String userID, final String startTime, final String endTime, final String id, final int type, final String remark, final String pumpShipID, final boolean isUpdate) {
        view.showLoading(true);
        dataRepository
                .IsCurrentDataInTimeRangeForBoatDaily(userID, startTime, endTime)
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean || isUpdate) {
                            return dataRepository.InsertConstructionBoatStopDaily(itemID, userID, startTime, endTime, id, type, remark, pumpShipID);
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
                        view.showStopResult(aBoolean);
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
     * 回显详细数据
     *
     * @param itemID
     */
    @Override
    public void getDetailData(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetConstructionBoatStopDaily(itemID, "", "", "", "", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DownLogBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<DownLogBean> list) {
                        view.showDetailData(list);
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
