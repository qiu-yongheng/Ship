package com.kc.shiptransport.mvp.todayplan;

import android.content.Context;

import com.kc.shiptransport.data.bean.todayplan.TodayPlanBean;
import com.kc.shiptransport.data.source.DataRepository;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/11/2  15:25
 * @desc ${TODD}
 */

public class TodayPlanPresenter implements TodayPlanContract.Presenter{


    private final CompositeDisposable compositeDisposable;
    private final DataRepository dataRepository;
    private final TodayPlanContract.View view;
    private final Context context;

    public TodayPlanPresenter(Context context, TodayPlanContract.View view, DataRepository dataRepository) {
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
    public void getTodayPlan(String date) {
        view.showLoading(true);
        dataRepository.GetToShipByCurrentDateAnalysis(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TodayPlanBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(TodayPlanBean todayPlanBean) {
                        view.showTodayPlan(todayPlanBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.toString());
                        view.showLoading(false);
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }
}
