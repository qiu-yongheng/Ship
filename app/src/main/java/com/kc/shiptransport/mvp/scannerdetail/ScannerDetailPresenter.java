package com.kc.shiptransport.mvp.scannerdetail;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:58
 * @desc ${TODD}
 */

public class ScannerDetailPresenter implements ScannerDetailContract.Presenter{
    private final Context context;
    private final ScannerDetailContract.View view;
    private final DataRepository dataRepository;

    public ScannerDetailPresenter(Context context, ScannerDetailContract.View view, DataRepository dataRepository) {
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
}
