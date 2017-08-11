package com.kc.shiptransport.mvp.analysis;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.acceptanceevaluation.AcceptanceEvaluationList;
import com.kc.shiptransport.db.acceptancerank.Rank;
import com.kc.shiptransport.db.analysis.ProgressTrack;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
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
        // 获取分包商列表
        Observable<List<SubcontractorList>> listObservable = dataRepository.getSubcontractorList()
                .subscribeOn(Schedulers.io());

        // 获取分包商进场计划跟踪
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
     * 获取分包商评价信息
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
     * 获取分包商评价排行
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


}
