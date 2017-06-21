package com.kc.shiptransport.mvp.voyagedetail;

import android.content.Context;

import com.kc.shiptransport.data.bean.VoyageInfoBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.PerfectBoatRecord;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.WeekTask;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/13  17:15
 * @desc ${TODD}
 */

public class VoyageDetailPresenter implements VoyageDetailContract.Presenter {
    private final Context context;
    private final VoyageDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public VoyageDetailPresenter(Context context, VoyageDetailContract.View view, DataRepository dataRepository) {
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
    public void getItemIDForPosition(int position) {
        dataRepository
                .getWeekTaskForPosition(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeekTask>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(WeekTask value) {
                        view.showItemID(String.valueOf(value.getItemID()));
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
    public void getSubcontractor() {
        dataRepository
                .getCurrentSubcontractor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Subcontractor>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Subcontractor value) {
                        view.showSubcontractor(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void doCommit(VoyageInfoBean bean) {
        view.showLoading(true);
        dataRepository
                .InsertPerfectBoatRecord(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean value) {
                        view.showCommitResult(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError("提交失败!");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void getShipName() {

    }

    @Override
    public void start(int position) {
        getSubcontractor();
        getItemIDForPosition(position);
        getVoyageDetailForItemID(position);
    }

    /**
     * 获取条目ID对应的数据(条目ID在完善信息后获取)
     */
    @Override
    public void getVoyageDetailForItemID(int position) {
        view.showLoading(true);
        dataRepository
                .getWeekTaskForPosition(position) // 获取position对应的计划数据
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<WeekTask>() {
                    @Override
                    public void accept(@NonNull WeekTask weekTask) throws Exception {
                        view.showShipName(weekTask.getShipName());
                    }})
                .observeOn(Schedulers.io())
                .flatMap(new Function<WeekTask, Observable<PerfectBoatRecord>>() { // 获取完善信息
                    @Override
                    public Observable<PerfectBoatRecord> apply(@NonNull WeekTask weekTask) throws Exception {
                        if (weekTask.getIsPerfect() == 1) {
                            // 已完善, 如果本地没有缓存, 发送网络请求
                            return dataRepository.getPerfectBoatRecordByItemID(weekTask, true);
                        } else {
                            // 未完善, 读取本地缓存, 或者创建
                            return dataRepository.getPerfectBoatRecordByItemID(weekTask, false);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PerfectBoatRecord>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull PerfectBoatRecord perfectBoatRecord) {
                        view.showDatas(perfectBoatRecord, perfectBoatRecord.getIsPerfect() == 1);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError("数据获取失败");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }
}
