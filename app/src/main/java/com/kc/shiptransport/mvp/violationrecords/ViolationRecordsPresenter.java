package com.kc.shiptransport.mvp.violationrecords;

import android.content.Context;

import com.kc.shiptransport.data.bean.violationrecord.ViolationRecordBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/12/4  16:18
 * @desc ${TODD}
 */

public class ViolationRecordsPresenter implements ViolationRecordsContract.Presenter{

    private final CompositeDisposable compositeDisposable;
    private final Context context;
    private final ViolationRecordsContract.View view;
    private final DataRepository dataRepository;

    public ViolationRecordsPresenter(Context context, ViolationRecordsContract.View view, DataRepository dataRepository) {
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

    @Override
    public void getDatas(int pageSize, final int pageCount) {
        dataRepository
                .GetSafeViolationRecords(pageSize, pageCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<List<ViolationRecordBean>>() {
                    @Override
                    protected void next(List<ViolationRecordBean> list) {
                        view.showDatas(list, pageCount == 1);
                    }

                    @Override
                    protected void error(String message) {
                        view.showError(message);
                    }

                    @Override
                    protected void complete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }
}
