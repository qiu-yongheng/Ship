package com.kc.shiptransport.mvp.acceptanceevaluation;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author 邱永恒
 * @time 2017/8/7  14:41
 * @desc ${TODD}
 */


public class AcceptanceEvaluationPresenter implements AcceptanceEvaluationContract.Presenter {
    private final Context context;
    private final AcceptanceEvaluationContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public AcceptanceEvaluationPresenter(Context context, AcceptanceEvaluationContract.View view, DataRepository dataRepository) {
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
        // 取消全部订阅
        compositeDisposable.clear();
    }
}
