package com.kc.shiptransport.mvp.acceptancedetail;

import android.content.ContentValues;
import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.util.CalendarUtil;

import org.litepal.crud.DataSupport;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/1  16:09
 * @desc ${TODD}
 */

public class AcceptanceDetailPresenter implements AcceptanceDetailContract.Presenter{
    private final Context context;
    private final AcceptanceDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;
    private final int success = 1;
    private final int fault = 0;

    public AcceptanceDetailPresenter(Context context, AcceptanceDetailContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        dataRepository = new DataRepository();
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
    public void getShipDetail(int itemID) {
        view.showLoading(true);
        dataRepository
                .getAcceptanceByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Acceptance>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Acceptance value) {
                        view.showShipDetail(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void getAcceptanceTime() {
        view.showAcceptanceTime(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));
    }

    @Override
    public void commit(final int itemID, String PassReceptionSandTime) {
        view.showLoading(true);
        dataRepository
                .UpdateForPassReceptionSandTime(itemID, PassReceptionSandTime)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {

                        ContentValues values = new ContentValues();
                        values.put("isAcceptance", integer);
                        DataSupport.updateAll(Acceptance.class, values, "ItemID = ?", String.valueOf(itemID));

                        return integer;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer value) {
                        view.showCommitResult(value == success);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showCommitError();
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void cancle() {
        view.showCancle();
    }

    @Override
    public void start(int itemID) {
        getShipDetail(itemID);
        getAcceptanceTime();
    }
}