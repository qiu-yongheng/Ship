package com.kc.shiptransport.mvp.plansetting;

import android.content.Context;
import android.util.Log;

import com.kc.shiptransport.db.Ship;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/5/17  17:13
 * @desc ${TODD}
 */

public class PlanSetPresenter implements PlanSetContract.Presenter{
    private final Context context;
    private final PlanSetContract.View view;

    public PlanSetPresenter(Context context, PlanSetContract.View view) {
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

    /**
     * 获取船的数据, 进行分类
     * 获取当前选中日期的计划任务
     * @param date
     */
    @Override
    public void getShipCategory(final String date) {
        Log.d("==", "----PlanSetPresenter: 获取所有ship, 分类, 传递当前时间----");
        Observable.create(new ObservableOnSubscribe<List<List<Ship>>>() {
            @Override
            public void subscribe(ObservableEmitter<List<List<Ship>>> e) throws Exception {
                List<List<Ship>> lists = new ArrayList<>();
                List<Ship> all = DataSupport.findAll(Ship.class);
                // 根据type进行分类
                Set set = new HashSet();
                for (Ship ship : all) {
                    String type = ship.getShipType();
                    if (set.contains(type)) {

                    } else {
                        set.add(type);
                        lists.add(DataSupport.where("ShipType = ?", type).find(Ship.class));
                    }
                }

                e.onNext(lists);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<List<Ship>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<List<Ship>> value) {
                        // 传递当前选中日期
                        view.showShipCategory(value, date);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}