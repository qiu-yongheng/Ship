package com.kc.shiptransport.mvp.boatinquireadd;

import android.content.Context;

import com.kc.shiptransport.data.bean.boatinquire.BoatInquireCommitBean;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireDetailBean;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireItemBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/11/29  14:52
 * @desc ${TODD}
 */

public class BoatInquireAddPresenter implements BoatInquireAddContract.Presenter{
    private final Context context;
    private final BoatInquireAddContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public BoatInquireAddPresenter(Context context, BoatInquireAddContract.View view, DataRepository dataRepository) {
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
    public void getDetailData(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetSafeShipSelfCheckDetailRecordByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<BoatInquireDetailBean>() {
                    @Override
                    protected void _onNext(BoatInquireDetailBean boatInquireDetailBean) {
                        view.showDetailData(boatInquireDetailBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        view.showError(message);
                        view.showLoading(false);
                    }

                    @Override
                    protected void _onComplete() {
                        view.showLoading(false);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }

    @Override
    public void getInquireItem() {
        view.showLoading(true);
        dataRepository
                .GetSafeShipSelfCheckItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<List<BoatInquireItemBean>>() {
                    @Override
                    protected void _onNext(List<BoatInquireItemBean> list) {
                        view.showInquireItem(list);
                    }

                    @Override
                    protected void _onError(String message) {
                        view.showError(message);
                        view.showLoading(false);
                    }

                    @Override
                    protected void _onComplete() {
                        view.showLoading(false);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }

    @Override
    public void commit(BoatInquireCommitBean bean) {
        view.showLoading(true);
        dataRepository
                .InsertSafeShipSelfCheckRecord(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<Boolean>() {
                    @Override
                    protected void _onNext(Boolean aBoolean) {
                        view.showCommitResult(aBoolean);
                    }

                    @Override
                    protected void _onError(String message) {
                        view.showError(message);
                        view.showLoading(false);
                    }

                    @Override
                    protected void _onComplete() {
                        view.showLoading(false);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }
}
