package com.kc.shiptransport.mvp.bcf;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/9/25  9:32
 * @desc ${TODD}
 */

public class BCFPresenter implements BCFContract.Presenter{
    private final Context context;
    private final BCFContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public BCFPresenter(Context context, BCFContract.View view, DataRepository dataRepository) {
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
    public void getSandShip() {
        view.showLoading(true);
        dataRepository
                .GetBoatShipItemNum(1000, 1, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showSandShipResult(aBoolean);
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

    @Override
    public void commit(int ItemID, String SandHandlingShipID, String SubcontractorAccount, float TotalAmount, String Remark, String Creator, String Date) {
        view.showLoading(true);
        dataRepository
                .InsertBCFToShipRecord(ItemID, SandHandlingShipID, SubcontractorAccount, TotalAmount, Remark, Creator, Date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showCommitResult(aBoolean);
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
