package com.kc.shiptransport.mvp.contactsdetail;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author 邱永恒
 * @time 2017/8/16  10:55
 * @desc ${TODD}
 */

public class ContactDetailPresenter implements ContactDetailContract.Presenter{
    private final Context context;
    private final ContactDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public ContactDetailPresenter(Context context, ContactDetailContract.View view, DataRepository dataRepository) {
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
