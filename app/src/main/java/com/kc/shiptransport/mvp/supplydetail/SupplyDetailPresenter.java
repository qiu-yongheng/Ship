package com.kc.shiptransport.mvp.supplydetail;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.CalendarUtil;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author qiuyongheng
 * @time 2017/6/1  11:18
 * @desc ${TODD}
 */

public class SupplyDetailPresenter implements SupplyDetailContract.Presenter{
    private final Context context;
    private final SupplyDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public SupplyDetailPresenter(Context context, SupplyDetailContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        dataRepository = new DataRepository();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getShipDetail() {

    }

    @Override
    public void getSupplyTime() {
        view.showSupplyTime( CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));
    }

    @Override
    public void getTotalVolume(String ship, String deck) {
        int i = Integer.valueOf(ship) + Integer.valueOf(deck);
        view.showTotalVolume(String.valueOf(i));
    }

    @Override
    public void commit() {
        // TODO
    }
}
