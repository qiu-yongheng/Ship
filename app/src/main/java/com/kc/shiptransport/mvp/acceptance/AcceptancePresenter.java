package com.kc.shiptransport.mvp.acceptance;

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
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/1  15:12
 * @desc ${TODD}
 */

public class AcceptancePresenter implements AcceptanceContract.Presenter {
    private final Context context;
    private final AcceptanceContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public AcceptancePresenter(Context context, AcceptanceContract.View view) {
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
     * 验收人名字
     */
    @Override
    public void getAcceptanceManName() {
        List<Subcontractor> subcontractorList = DataSupport.findAll(Subcontractor.class);
        view.showAcceptanceMan(subcontractorList.get(0).getSubcontractorName());
    }

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
     * 待验收航次
     */
    @Override
    public void getStayAcceptanceShip() {
        dataRepository
                .getStayNum(SettingUtil.TYPE_ACCEPT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        view.showStayAcceptanceShip(String.valueOf(value));
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
    public void getWeekTask(final int jumpWeek) {
        dataRepository
                .getWeekTask(jumpWeek)
                .subscribeOn(Schedulers.io())
                .map(new Function<List<WeekTask>, List<WeekTask>>() {
                    @Override
                    public List<WeekTask> apply(@NonNull List<WeekTask> weekTasks) throws Exception {
                        /** 删除不能进行预验砂的任务 (不允许预验收, 且没有通过验收) */
                        DataSupport.deleteAll(WeekTask.class, "IsAllowPreAcceptanceEvaluation = 0 and PreAcceptanceEvaluationStatus  != 1");
                        dataRepository.dataSort(jumpWeek);
                        return DataSupport.findAll(WeekTask.class);
                    }
                })
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
                        getStayAcceptanceShip();
                    }
                });
    }

    @Override
    public void getDayCount() {
        dataRepository
                .getDayCount()
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
                        // TODO 没必要
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
        //        getAcceptanceManName();

        // 4. 获取所有供应商
        //        getSubcontractor();
    }

    /**
     * 获取所有供应商
     */
    @Override
    public void getSubcontractor() {
        view.showLoading(true);
        Observable.create(new ObservableOnSubscribe<List<SubcontractorList>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SubcontractorList>> e) throws Exception {
                dataRepository.getSubcontractorInfo("");
                // 从数据库获取供应商
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
