package com.kc.shiptransport.mvp.constructionlog;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

/**
 * @author qiuyongheng
 * @time 2017/7/6  15:45
 * @desc ${TODD}
 */

public class ConstructionLogPresenter implements ConstructionLogContract.Presenter{
    private final Context context;
    private final ConstructionLogContract.View view;
    private final DataRepository dataRepository;

    public ConstructionLogPresenter(Context context, ConstructionLogContract.View view, DataRepository dataRepository) {
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
