package com.kc.shiptransport.mvp.voyagedetail;

import android.content.Context;

import com.kc.shiptransport.data.bean.VoyageDetailBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.db.backlog.BackLog;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

    /**
     * 获取信息完善数据
     * @param position
     */
    @Override
    public void getVoyageDates(int position, int type) {
        view.showLoading(true);
        Observable<VoyageDetailBean> observable = null;
        if (type == 0) {
            // 发送的是position
            observable = dataRepository
                    .getWeekTaskForPosition(position)
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Function<WeekTask, ObservableSource<VoyageDetailBean>>() {
                        @Override
                        public ObservableSource<VoyageDetailBean> apply(@NonNull WeekTask weekTask) throws Exception {
                            // 根据itemID获取信息完善数据
                            return dataRepository.GetPerfectBoatRecordBySubcontractorInterimApproachPlanID(weekTask.getItemID());
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        } else if (type == 1) {
            // 发送的是itemID
            observable = dataRepository
                    .GetPerfectBoatRecordBySubcontractorInterimApproachPlanID(position)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else if (type == 2) {
            // 发送的是itemID
            observable = dataRepository
                    .GetPerfectBoatRecordBySubcontractorInterimApproachPlanID(position)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        if (observable != null) {
            observable
                    .subscribe(new Observer<VoyageDetailBean>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(@NonNull VoyageDetailBean bean) {
                            view.showVoyageDates(bean);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            view.showLoading(false);
                            view.showError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            view.showLoading(false);
                        }
                    });
        } else {
            view.showError("获取数据失败!");
        }
    }

    /**
     * 提交数据
     * @param bean
     */
    @Override
    public void commit(VoyageDetailBean bean) {
        view.showLoading(true);
        dataRepository
                .InsertPerfectBoatRecord(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Boolean, ObservableSource<List<BackLog>>>() {
                    @Override
                    public ObservableSource<List<BackLog>> apply(@NonNull Boolean aBoolean) throws Exception {
                        // 更新待办
                        return dataRepository.GetPendingTaskList(10000, 1);
                    }
                })
                .flatMap(new Function<List<BackLog>, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull final List<BackLog> list) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Boolean>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                                if (list.isEmpty()) {
                                    e.onNext(false);
                                } else {
                                    e.onNext(true);
                                }

                                e.onComplete();
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showCommitResult(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }
}
