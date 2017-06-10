package com.kc.shiptransport.mvp.plan;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.TaskVolume;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.util.CalendarUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/5/17  10:53
 * @desc ${TODD}
 */
public class PlanPresenter implements PlanContract.Presenter {
    private final Context context;
    private final PlanContract.View view;
    private final RemoteDataSource remoteDataSource;
    private final Gson gson;
    private int day_0 = 0;
    private int day_1 = 0;
    private int day_2 = 0;
    private int day_3 = 0;
    private int day_4 = 0;
    private int day_5 = 0;
    private int day_6 = 0;
    private final DataRepository mDataRepository;

    public PlanPresenter(Context context, PlanContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        remoteDataSource = new RemoteDataSource();
        mDataRepository = new DataRepository();
        gson = new Gson();
    }

    @Override
    public void start(int jumpWeek) {
        // 1. 获取分包商, 周
        getTitle(jumpWeek);

        // 2. 获取当前月
        getCurrentDate(jumpWeek);

        // 3. 刷新
        doRefresh(jumpWeek);

        // 4. 获取分包商计划量
        getTaskVolume(jumpWeek);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    /**
     * 获取标题数据
     */
    @Override
    public void getTitle(final int jumpWeek) {
        mDataRepository
                .getTitle(jumpWeek)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        view.showTitle(value);
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
     * 获取当前月数据
     */
    @Override
    public void getCurrentDate(final int jumpWeek) {
        mDataRepository
                .getCurrentMouthDate(jumpWeek)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

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
     * 获取任务量
     */
    @Override
    public void getTaskVolume(int jumpWeek) {
        mDataRepository
                .getTaskVolume(jumpWeek)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TaskVolume>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TaskVolume value) {
                        view.showTaskVolume(value);
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
     * 获取任务要求
     */
    @Override
    public void getTaskRequire() {

    }

    /**
     * 计算总任务量
     *
     * @param integers
     */
    @Override
    public void getTotalTaskVolume(Double[] integers) {
        int total = 0;
        for (Double integer : integers) {
            total += integer;
        }
        view.showTotalTaskVolume(total);
    }

    /**
     * 从本地数据库获取计划表
     */
    @Override
    public void getWeekTask(final int jumpWeek) {
        mDataRepository
                .getWeekTask()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WeekTask>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

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
                    }

                    @Override
                    public void onComplete() {
                        // 统计每日计划量
                        getDayCount();
                    }
                });
    }

    /**
     * 计算每天任务总量
     */
    @Override
    public void getDayCount() {
        mDataRepository
                .getDayCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Double[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Double[] value) {
                        // 计算总计划量
                        view.showDayCount(value);
                        getTotalTaskVolume(value);
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
     * 提交数据
     */
    @Override
    public void doCommit() {

    }

    /**
     * 网络请求
     * 保存数据到数据库
     *
     */
    @Override
    public void doRefresh(final int jumpWeek) {
        view.showLoading(true);
        mDataRepository
                .doRefresh(jumpWeek)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        getWeekTask(jumpWeek);
                    }
                });
    }
}
