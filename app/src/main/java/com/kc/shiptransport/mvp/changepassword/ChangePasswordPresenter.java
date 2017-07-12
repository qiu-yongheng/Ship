package com.kc.shiptransport.mvp.changepassword;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author qiuyongheng
 * @time 2017/7/12  16:09
 * @desc ${TODD}
 */

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter{
    private final Context context;
    private final ChangePasswordContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public ChangePasswordPresenter(Context context, ChangePasswordContract.View view, DataRepository dataRepository) {
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
