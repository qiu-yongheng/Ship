package com.kc.shiptransport.mvp.sampledetail;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.SandSample;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/14  10:58
 * @desc ${TODD}
 */

public class SampleDetailPresenter implements SampleDetailContract.Presenter{
    private final Context context;
    private final SampleDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public SampleDetailPresenter(Context context, SampleDetailContract.View view, DataRepository dataRepository) {
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
    public void getShipInfo(int position) {
        dataRepository
                .getSampleTaskForPosition(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SandSample>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull SandSample sandSample) {
                        view.showTime(sandSample.getPlanDay());
                        view.showShipName(sandSample.getShipName());
                        view.showShipNumber(sandSample.getShipItemNum());
                        view.showItemID(sandSample.getItemID());
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
    public void start(int position) {
        getShipInfo(position);
    }
}
