package com.kc.shiptransport.mvp.recordedsandshow;

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
 * @time 2017/6/19  11:30
 * @desc ${TODD}
 */

public class RecordedSandShowPresenter implements RecordedSandShowContract.Presenter{


    private final Context context;
    private final RecordedSandShowContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public RecordedSandShowPresenter(Context context, RecordedSandShowContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        this.dataRepository = dataRepository;
        compositeDisposable = new CompositeDisposable();

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getRecordShowList(int itemID) {
        view.showLoadding(true);
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
                        view.showDatas(recordedSandShowLists);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoadding(false);
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoadding(false);
                    }
                });
    }
}
