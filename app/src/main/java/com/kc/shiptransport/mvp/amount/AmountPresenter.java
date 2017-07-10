package com.kc.shiptransport.mvp.amount;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/13  15:43
 * @desc ${TODD}
 */

public class AmountPresenter implements AmountContract.Presenter{
    private final Context context;
    private final AmountContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public AmountPresenter(Context context, AmountContract.View view, DataRepository dataRepository) {
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
        view.showTitle("量方管理");
    }

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

    @Override
    public void getStayInfo() {
        // TODO 待完善船次
        dataRepository
                .getStayNum(SettingUtil.TYPE_AMOUNT)
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

    @Override
    public void getDayCount() {
        dataRepository
                .getDayCount(SettingUtil.TYPE_AMOUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer[] value) {
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

    @Override
    public void doRefresh(final int jumpWeek, String subcontractorAccount) {
        view.showLoading(true);
        dataRepository
                .doRefresh(jumpWeek, subcontractorAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            getDayCount();
                        }
                    }
                })
                .flatMap(new Function<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> apply(Boolean aBoolean) throws Exception {
                        // 删除未验收数据后, 进行重新排序
                        return dataRepository.getWeekTaskSort(jumpWeek);
                    }
                })
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

                        getStayInfo();
                    }
                });
    }

    @Override
    public void start(int jumpWeek, String subcontractorAccount) {
//        getTitle();
//        getTitleOtherInfo();
        getTime(jumpWeek);
        doRefresh(jumpWeek, subcontractorAccount);
    }

    @Override
    public void getSubcontractor() {
        view.showLoading(true);
        Observable.create(new ObservableOnSubscribe<List<SubcontractorList>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SubcontractorList>> e) throws Exception {
                dataRepository.getSubcontractorInfo("");
                // 从数据库获取分包商
                List<SubcontractorList> subcontractorList = DataSupport.findAll(SubcontractorList.class);

                e.onNext(subcontractorList);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SubcontractorList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<SubcontractorList> value) {
                        view.showSpinner(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError("获取数据失败");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }
}
