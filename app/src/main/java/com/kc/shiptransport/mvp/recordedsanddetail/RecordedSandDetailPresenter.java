package com.kc.shiptransport.mvp.recordedsanddetail;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

/**
 * @author qiuyongheng
 * @time 2017/6/19  11:30
 * @desc ${TODD}
 */

public class RecordedSandDetailPresenter implements RecordedSandDetailContract.Presenter{
    private final Context context;
    private final RecordedSandDetailContract.View view;
    private final DataRepository dataRepository;

    public RecordedSandDetailPresenter(Context context, RecordedSandDetailContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
