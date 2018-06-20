package com.kc.shiptransport.mvp.mechanical;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

import io.reactivex.disposables.CompositeDisposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Edison on 2018/5/8.
 */

public class MechanicalPresenter implements MechanicalContract.Presenter {
    private final MechanicalContract.View view;
    private final CompositeDisposable compositeDisposable;
    private final Context context;
    private final DataRepository dataRepository;

    public MechanicalPresenter(Context context, MechanicalContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
