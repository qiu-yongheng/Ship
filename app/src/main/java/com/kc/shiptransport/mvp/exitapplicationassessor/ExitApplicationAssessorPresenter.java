package com.kc.shiptransport.mvp.exitapplicationassessor;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author 邱永恒
 * @time 2017/8/31  15:47
 * @desc ${TODD}
 */

public class ExitApplicationAssessorPresenter implements ExitApplicationAssessorContract.Presenter{
    private final Context context;
    private final ExitApplicationAssessorContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public ExitApplicationAssessorPresenter(Context context, ExitApplicationAssessorContract.View view, DataRepository dataRepository) {
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
}
