package com.kc.shiptransport.mvp.supplydetail;

import android.content.Context;
import android.widget.Toast;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/1  11:18
 * @desc ${TODD}
 */

public class SupplyDetailPresenter implements SupplyDetailContract.Presenter {
    private final Context context;
    private final SupplyDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;
    private final int success = 1;
    private final int fault = 0;

    public SupplyDetailPresenter(Context context, SupplyDetailContract.View view) {
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

    /**
     * 获取供沙详情
     * 1. 先从DB获取
     * 2. 如果没有缓存, 发送网络请求
     */
    @Override
    public void getShipDetail(int itemID) {
        view.showLoading(true);
        dataRepository
                .getAcceptanceByItemID(itemID, true)
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
    public void getSupplyTime() {
        view.showSupplyTime(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));
    }

    @Override
    public void getTotalVolume(String ship, String deck) {
        try {
            double i1 = ship.equals("") ? 0 : Double.valueOf(ship);
            double i2 = deck.equals("") ? 0 : Double.valueOf(deck);
            view.showTotalVolume(String.valueOf(i1 + i2));
        } catch (Exception e) {
            Toast.makeText(context, "请输入数字", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 提交
     */
    @Override
    public void commit(final int itemID, String ReceptionSandTime, String Capacity, String DeckGauge) {
        view.showLoading(true);
        dataRepository
                .updateForReceptionSandTime(itemID, ReceptionSandTime, Capacity, DeckGauge)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, Observable<Boolean>>() { // 同步
                    @Override
                    public Observable<Boolean> apply(Integer integer) throws Exception {
                        if (integer == success) {
                            return dataRepository.doRefresh(SharePreferenceUtil.getInt(context, SettingUtil.WEEK_JUMP_SUPPLY));
                        } else {
                            return null;
                        }
                    }
                })
                .flatMap(new Function<Boolean, Observable<Acceptance>>() { // 更新
                    @Override
                    public Observable<Acceptance> apply(Boolean aBoolean) throws Exception {
                        return dataRepository.getAcceptanceByItemID(itemID, false);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Acceptance>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Acceptance value) {
                        view.showCommitResult(true);
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
    public void start(int itemID) {
        // 1.
        getShipDetail(itemID);

        // 2.
        getSupplyTime();
    }
}
