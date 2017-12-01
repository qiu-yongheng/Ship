package com.kc.shiptransport.mvp.boatinquire;

import android.content.Context;

import com.kc.shiptransport.data.bean.boatinquire.BoatInquireBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.hse.HseCheckShip;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/11/29  10:09
 * @desc ${TODD}
 */

public class BoatInquirePresenter implements BoatInquireContract.Presenter{

    private final CompositeDisposable compositeDisposable;
    private final Context context;
    private final BoatInquireContract.View view;
    private final DataRepository dataRepository;

    public BoatInquirePresenter(Context context, BoatInquireContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        dataRepository
                .GetSafeCheckedShip()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<List<HseCheckShip>>() {
                    @Override
                    protected void _onNext(List<HseCheckShip> hseCheckShips) {
                        view.showSyncResult(!hseCheckShips.isEmpty());
                    }

                    @Override
                    protected void _onError(String message) {
                        view.showError(message);
                    }

                    @Override
                    protected void _onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getDatas(int pageSize, final int pageCount, BoatInquireBean bean, String startDate, String endDate, boolean isShow) {
        if (isShow) {
            view.showLoading(true);
        }
        dataRepository
                .GetSafeShipSelfCheckRecordsAnalysis(pageSize, pageCount, bean, startDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<List<BoatInquireBean>>() {
                    @Override
                    protected void _onNext(List<BoatInquireBean> list) {
                        view.showDatas(list, pageCount == 1);
                        if (list.isEmpty()) {
                            view.showError("没有数据");
                        }
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
