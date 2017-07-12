package com.kc.shiptransport.mvp.exitapplicationdetail;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

/**
 * @author qiuyongheng
 * @time 2017/7/12  10:03
 * @desc ${TODD}
 */

public class ExitApplicationDetailPresenter implements ExitApplicationDetailContract.Presenter{
    private final Context context;
    private final ExitApplicationDetailContract.View view;
    private final DataRepository dataRepository;

    public ExitApplicationDetailPresenter(Context context, ExitApplicationDetailContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getDates() {

    }

    @Override
    public void commit() {

    }
}
