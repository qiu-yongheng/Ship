package com.kc.shiptransport.mvp.basemvp;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.CalendarUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/13  15:43
 * @desc ${TODD}
 */

public abstract class BaseMvpPresenter implements BaseMvpContract.Presenter{
    protected final Context context;
    protected final BaseMvpContract.View view;
    protected final DataRepository dataRepository;
    protected final CompositeDisposable compositeDisposable;

    public BaseMvpPresenter(Context context, BaseMvpContract.View view, DataRepository dataRepository) {
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
    public void getTitle() {
        view.showTitle(getTit());
    }

    // 获取标题
    protected abstract String getTit();


    /**
     * 获取操作人
     */
    @Override
    public void getTitleOtherInfo() {
        dataRepository
                .getSubcontractorName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String value) {
                        view.showTitleOtherInfo(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError("分包商信息获取失败");
                    }

                    @Override
                    public void onComplete() {

                    }});
    }

    /**
     * 获取时间
     * @param jumpWeek
     */
    @Override
    public void getTime(int jumpWeek) {
        dataRepository
                .getCurrentMouthDate(jumpWeek)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String value) {
                        view.showTime(value);
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
     * 根据类型显示 待验收船
     * @param type
     */
    @Override
    public void getStayInfo(int type) {
        // TODO 待完善船次
        dataRepository
                .getStayNum(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        view.showStayInfo(String.valueOf(value));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getWeekTask(int jumpWeek) {

    }

    /**
     * 每日计划量统计
     */
    @Override
    public void getDayCount() {
        dataRepository
                .getDayCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Double[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Double[] value) {
                        // 计算总计划量
                        view.showDayCount(value);
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
     * 刷新数据
     * @param jumpWeek
     */
    @Override
    public void doRefresh(final int jumpWeek, final int type) {
        view.showLoading(true);
        dataRepository
                .doRefresh(jumpWeek)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 统计每日计划量
                            getDayCount();
                        }
                    }
                })
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        // 获取待验收船次
                        getStayInfo(type);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean value) {
                        // 获取一周日期数据
                        List<String> dates = CalendarUtil.getdateOfWeek("dd", jumpWeek);
                        view.showWeekTask(dates);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError("数据获取失败!");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void start(int jumpWeek, int type) {
        getTitle();
        getTitleOtherInfo();
        getTime(jumpWeek);
        doRefresh(jumpWeek, type);
    }
}
