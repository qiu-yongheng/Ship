package com.kc.shiptransport.mvp.acceptancedetail;

import android.content.Context;

/**
 * @author qiuyongheng
 * @time 2017/6/1  16:09
 * @desc ${TODD}
 */

public class AcceptanceDetailPresenter implements AcceptanceDetailContract.Presenter{
    private final Context context;
    private final AcceptanceDetailContract.View view;

    public AcceptanceDetailPresenter(Context context, AcceptanceDetailContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
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

    }

    @Override
    public void commit() {

    }

    @Override
    public void cancle() {

    }
}
