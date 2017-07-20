package com.kc.shiptransport.mvp.recordedsanddetaillist;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.RecordedSandShowList;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/7/20  13:20
 * @desc ${TODD}
 */

public class RecordedSandDetailListPresenter implements RecordedSandDetailListContract.Presenter{


    private final DataRepository dataRepository;
    private final RecordedSandDetailListContract.View view;
    private final Context context;
    private final CompositeDisposable compositeDisposable;

    public RecordedSandDetailListPresenter(Context context, RecordedSandDetailListContract.View view, DataRepository dataRepository) {
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

    }

    /**
     * 获取过砂记录列表
     * @param itemID
     */
    @Override
    public void getRecordedList(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetOverSandRecordBySubcontractorInterimApproachPlanID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<RecordedSandShowList>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<RecordedSandShowList> recordedSandShowLists) {
                        view.showRecordedList(recordedSandShowLists);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }
}
