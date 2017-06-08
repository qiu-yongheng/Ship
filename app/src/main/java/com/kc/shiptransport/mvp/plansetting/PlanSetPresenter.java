package com.kc.shiptransport.mvp.plansetting;

import android.content.Context;
import android.util.Log;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.Ship;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/5/17  17:13
 * @desc ${TODD}
 */

public class PlanSetPresenter implements PlanSetContract.Presenter{
    private final Context context;
    private final PlanSetContract.View view;
    private final DataRepository mDataRepository;
    private final CompositeDisposable compositeDisposable;

    public PlanSetPresenter(Context context, PlanSetContract.View view) {
        this.context = context;
        this.view = view;
        mDataRepository = new DataRepository();
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }

    /**
     * 获取船的数据, 进行分类
     * 获取当前选中日期的计划任务
     * @param date
     */
    @Override
    public void getShipCategory(final String date) {
        Log.d("==", "----PlanSetPresenter: 获取所有ship, 分类, 传递当前时间----");
        mDataRepository
                .getShipCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<List<Ship>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<List<Ship>> value) {
                        // 传递当前选中日期
                        view.showShipCategory(value, date);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取指定日期计划船舶数量
     */
    @Override
    public void getShipCount(String date) {
        mDataRepository
                .getShipCount(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer value) {
                        view.showShipcount(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取指定日期计划量
     */
    @Override
    public void getPlanMeasure(String date) {
        mDataRepository
                .getPlanMeasure(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Double>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Double value) {
                        view.showPlanMeasure(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}