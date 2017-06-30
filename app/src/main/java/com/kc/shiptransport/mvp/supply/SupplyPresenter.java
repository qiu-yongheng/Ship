package com.kc.shiptransport.mvp.supply;

import android.content.Context;
import android.util.Log;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.WeekTask;
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
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/5/31  17:03
 * @desc ${TODD}
 */

public class SupplyPresenter implements SupplyContract.Presenter {
    private final Context context;
    private final SupplyContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public SupplyPresenter(Context context, SupplyContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        dataRepository = new DataRepository();
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
     * 获取验沙人名
     */
    @Override
    public void getSupplyManName() {
        List<Subcontractor> subcontractorList = DataSupport.findAll(Subcontractor.class);
        view.showSupplyMan(subcontractorList.get(0).getSubcontractorName());
    }

    /**
     * 获取当前时间
     *
     * @param jumpWeek
     */
    @Override
    public void getCurrentDate(int jumpWeek) {
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
                        view.showCurrentDate(value);
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
     * 待验沙船数
     */
    @Override
    public void getStaySupplyShip() {
        dataRepository
                .getStayNum(SettingUtil.TYPE_SUPPLY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        view.showStaySupplyShip(String.valueOf(value));
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
     * 获取供沙计划
     *
     * @param jumpWeek
     */
    @Override
    public void getWeekTask(final int jumpWeek) {
        dataRepository
                .getWeekTask()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WeekTask>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<WeekTask> value) {
                        // 获取一周日期数据
                        List<String> dates = CalendarUtil.getdateOfWeek("dd", jumpWeek);
                        Log.d("==", dates.toString());
                        view.showWeekTask(dates, value);
                        view.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        // 统计每日计划量
                        getDayCount();

                        // 统计未验收量
                        getStaySupplyShip();
                    }
                });
    }

    /**
     * 统计每日计划量
     */
    @Override
    public void getDayCount() {
        dataRepository
                .getDayCount(SettingUtil.TYPE_SUPPLY)
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
                .flatMap(new Function<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> apply(Boolean aBoolean) throws Exception {
                        // 删除未验收数据后, 进行重新排序
                        return dataRepository.getWeekTaskSort(jumpWeek);
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

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        getWeekTask(jumpWeek);
                    }
                });
    }

    @Override
    public void start(int jumpWeek, String subcontractorAccount) {
        // 1. 获取当前月
        getCurrentDate(jumpWeek);

        // 2. 刷新数据
        doRefresh(jumpWeek, subcontractorAccount);

        // 3. 验收人
//        getSupplyManName();
    }

    /**
     * 获取所有分包商
     */
    @Override
    public void getSubcontractor() {
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

                    }

                    @Override
                    public void onNext(List<SubcontractorList> value) {
                        view.showSpinner(value);
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
