package com.kc.shiptransport.mvp.analysis;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

import io.reactivex.disposables.CompositeDisposable;

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

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    /**
     * 搜索
     * @param startTime
     * @param endTime
     * @param subID
     * @param shipID
     */
    @Override
    public void search(String startTime, String endTime, String subID, String shipID) {

    }
}
