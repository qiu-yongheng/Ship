package com.kc.shiptransport.mvp.shipselect;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.db.Subcontractor;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/5/18  22:48
 * @desc ${TODD}
 */

public class ShipSelectPresenter implements ShipSelectContract.Presenter {
    private final Context context;
    private final ShipSelectContract.View view;
    private final Gson gson;
    private final RemoteDataSource remoteDataSource;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public ShipSelectPresenter(Context context, ShipSelectContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        gson = new Gson();
        remoteDataSource = new RemoteDataSource();
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

    /**
     * 根据type获取数据
     *
     * @param type
     */
    @Override
    public void getShip(final String type) {
        dataRepository
                .getShipByType(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Ship>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
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

    /**
     * 取消修改
     * 1. 根据type查询船舶数据, 全部设置 select = 0
     * 2. 根据当前日期, type查询计划数据, 全部设置为select = 1
     *
     * @param type
     */
    @Override
    public void doCancle(final String type, final String date) {
        dataRepository
                .doCancle(type, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean value) {

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

    /**
     * 发送网络请求
     * 1. 判断新计划数据: select = 1, itemID = ""
     * 2. 判断取消的数据: select = 0, itemID != ""
     */
    @Override
    public void doCommit(final String type, final String date) {
        view.showLoading(true);
        dataRepository
                .doCommit(type, date)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> apply(String s) throws Exception {
                        if (s.equals("1")) {
                        // 提交后, 更新船舶列表
                        String subcontractorAccount = DataSupport.findAll(Subcontractor.class).get(0).getSubcontractorAccount();
                        return dataRepository.getShip(subcontractorAccount);

                        } else {
                            return Observable.create(new ObservableOnSubscribe<Boolean>() {
                                @Override
                                public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                                    e.onNext(false);
                                    e.onComplete();
                                }
                            });
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean value) {
                        if (value) {
                            // 同步数据
                            view.showCommitSuccess();
                            view.changeDailogInfo();
                        } else {
                            view.showError();
                            view.showLoading(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("==", "提交错误" + e.toString());
                        // 服务器返回null报错, 暂时屏蔽
                        view.showLoading(false);
                        view.showError();
//                        view.showCommitSuccess();
//                        view.changeDailogInfo();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void doRefresh(final int jumpWeek) {
        dataRepository
                .doRefresh(jumpWeek)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                        view.showSuccess();
                        view.navigateToPlanSet();
                    }
                });
    }
}
