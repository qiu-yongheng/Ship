package com.kc.shiptransport.mvp.upcominglist;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.upcoming.UpcomingContract;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author 邱永恒
 * @time 2017/9/8  18:16
 * @desc ${TODD}
 */

public class UpcomingListPresenter implements UpcomingContract.Presenter{
    private final Context context;
    private final UpcomingContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public UpcomingListPresenter(Context context, UpcomingContract.View view, DataRepository dataRepository) {
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
    public void getPending(int PageSize, int PageCount) {

    }
}
