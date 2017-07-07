package com.kc.shiptransport.mvp.threadsand;

import android.content.Context;

import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.PartitionSBBean;
import com.kc.shiptransport.data.source.DataRepository;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

    @Override
    public void getDates(String CurrentDate, String CurrentBoatAccount) {
        view.showLoading(true);
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
}
