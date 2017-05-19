package com.kc.shiptransport.mvp.shipselect;

import android.content.ContentValues;
import android.content.Context;

import com.kc.shiptransport.db.Ship;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/5/18  22:48
 * @desc ${TODD}
 */

public class ShipSelectPresenter implements ShipSelectContract.Presenter{
    private final Context context;
    private final ShipSelectContract.View view;

    public ShipSelectPresenter(Context context, ShipSelectContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getShip(final String type) {
        Observable.create(new ObservableOnSubscribe<List<Ship>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Ship>> e) throws Exception {
                List<Ship> list = DataSupport.where("ShipType = ?", type).find(Ship.class);
                e.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Ship>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Ship> value) {
                        view.showShip(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void doCancle(final String itemID) {
        Observable.create(new ObservableOnSubscribe<List<Ship>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Ship>> e) throws Exception {
                ContentValues values = new ContentValues();
                values.put("selected", "0");
                DataSupport.updateAll(Ship.class, values, "ItemID = ?", itemID);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Ship>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Ship> value) {
                        view.showShip(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        view.cancle();
                    }
                });
    }

    @Override
    public void doCommit() {
        view.commit();
    }
}
