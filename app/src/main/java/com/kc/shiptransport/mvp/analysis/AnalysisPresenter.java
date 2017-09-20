package com.kc.shiptransport.mvp.analysis;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.acceptanceevaluation.AcceptanceEvaluationList;
import com.kc.shiptransport.db.acceptancerank.Rank;
import com.kc.shiptransport.db.analysis.ProgressTrack;
import com.kc.shiptransport.db.exitassessor.ExitAssessor;
import com.kc.shiptransport.db.logmanager.LogManagerList;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/7/26  13:58
 * @desc ${TODD}
 */

public class AnalysisPresenter implements AnalysisContract.Presenter {
    private final Context context;
    private final AnalysisContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public AnalysisPresenter(Context context, AnalysisContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        // 获取供应商列表
        Observable<List<SubcontractorList>> listObservable = dataRepository.getSubcontractorList()
                .subscribeOn(Schedulers.io());

        // 获取供应商进场计划跟踪
        Observable<List<ProgressTrack>> progressObservable = dataRepository
                .GetSubcontractorInterimApproachPlanProgressTracking("", "", "", "")
                .subscribeOn(Schedulers.io());

        view.showLoading(true);
        Observable.zip(listObservable, progressObservable, new BiFunction<List<SubcontractorList>, List<ProgressTrack>, List<ProgressTrack>>() {
            @Override
            public List<ProgressTrack> apply(@NonNull List<SubcontractorList> subcontractorLists, @NonNull List<ProgressTrack> list) throws Exception {
                return list;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ProgressTrack>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<ProgressTrack> ProgressTrack) {
                        view.showSearchResult(ProgressTrack);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    /**
     * 搜索
     *
     * @param startTime
     * @param endTime
     * @param subID
     * @param shipID
     */
    @Override
    public void search(String startTime, String endTime, String subID, String shipID) {
        view.showLoading(true);
        dataRepository
                .GetSubcontractorInterimApproachPlanProgressTracking(subID, shipID, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ProgressTrack>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<ProgressTrack> progressTracks) {
                        view.showSearchResult(progressTracks);
                        view.showError("搜索到" + progressTracks.size() + "条数据");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 获取供应商评价信息
     */
    @Override
    public void getEvaluation(int pageSize, int pageCount, String startTime, String endTime, String subShipAccount, boolean showLoading) {
        if (showLoading) {
            view.showLoading(true);
        }
        dataRepository
                .GetPreAcceptanceEvaluationList(pageSize, pageCount, startTime, endTime, subShipAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AcceptanceEvaluationList>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<AcceptanceEvaluationList> acceptanceEvaluationLists) {
                        view.showEvaluation(acceptanceEvaluationLists);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 获取供应商评价排行
     */
    @Override
    public void getRank(String startTime, String endTime) {
        view. showLoading(true);
        dataRepository
                .GetSubcontractorPreAcceptanceEvaluationRanking(startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Rank>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Rank> list) {
                        view.showRank(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 获取退场反馈
     * @param pageSize
     * @param pageCount
     * @param startTime
     * @param endTime
     * @param shipAccount
     * @param showLoading
     */
    @Override
    public void getExitFeedBack(int pageSize, int pageCount, String startTime, String endTime, String shipAccount, boolean showLoading) {
        if (showLoading) {
            view.showLoading(true);
        }
        dataRepository
                .GetExitAuditedApplicationRecords(pageSize, pageCount, startTime, endTime, shipAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ExitAssessor>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<ExitAssessor> list) {
                        view.showExitFeedBack(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 获取日志管理
     * @param pageSize
     * @param pageCount
     * @param startTime
     * @param endTime
     * @param shipAccount
     */
    @Override
    public void getLogManager(int pageSize, int pageCount, String startTime, String endTime, String shipAccount) {
        view.showLoading(true);
        dataRepository
                .GetConstructionBoatDailyList(pageSize, pageCount, startTime, endTime, shipAccount, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LogManagerList>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<LogManagerList> lists) {
                        view.showLogManager(lists);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 删除停工
     * @param itemID
     */
    @Override
    public void deleteStopLog(int itemID) {
        view.showLoading(true);
        dataRepository
                .DeleteConstructionBoatStopDailyByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showDeleteLogResult(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 删除抛砂
     * @param itemID
     */
    @Override
    public void deleteThreadLog(int itemID) {
        view.showLoading(true);
        dataRepository
                .DeleteConstructionBoatThrowingSandRecordsByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showDeleteLogResult(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 获取明日来船计划量
     * @param startTime
     * @param endTime
     * @param subID
     * @param shipID
     */
    @Override
    public void getTomorrowPlan(String startTime, String endTime, String subID, String shipID) {
        view.showLoading(true);
        dataRepository
                .GetSubcontractorInterimApproachPlanProgressTracking(subID, shipID, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<ProgressTrack>, ObservableSource<List<List<ProgressTrack>>>>() {
                    @Override
                    public ObservableSource<List<List<ProgressTrack>>> apply(@NonNull List<ProgressTrack> list) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<List<List<ProgressTrack>>>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<List<List<ProgressTrack>>> e) throws Exception {
                                // 对数据进行分组
                                List<ProgressTrack> progressTracks = DataSupport.order("SubcontractorAccount asc").find(ProgressTrack.class);
                                ArrayList<List<ProgressTrack>> totalList = new ArrayList<>();

                                Set set = new HashSet();
                                for (ProgressTrack bean : progressTracks) {
                                    String account = bean.getSubcontractorAccount();
                                    if (set.contains(account)) {

                                    } else {
                                        set.add(account);
                                        totalList.add(DataSupport.where("SubcontractorAccount = ?", account).find(ProgressTrack.class));
                                    }
                                }

                                e.onNext(totalList);
                                e.onComplete();
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<List<ProgressTrack>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<List<ProgressTrack>> progressTracks) {
                        view.showTomorrowPlan(progressTracks);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }
}
