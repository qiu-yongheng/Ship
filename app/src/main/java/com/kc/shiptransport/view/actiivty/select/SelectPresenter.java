package com.kc.shiptransport.view.actiivty.select;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.hse.HseCheckShip;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/12/8  13:48
 * @desc ${TODD}
 */

public class SelectPresenter implements SelectContract.Presenter{

    private final CompositeDisposable compositeDisposable;
    private final Context context;
    private final SelectContract.View view;
    private final DataRepository dataRepository;

    public SelectPresenter(Context context, SelectContract.View view, DataRepository dataRepository) {
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
    public void search(String msg, boolean isSearchAll) {
        dataRepository
                .searchHseCheckShip(msg, isSearchAll)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<List<HseCheckShip>>() {
                    @Override
                    protected void next(List<HseCheckShip> hseCheckShips) {
                        view.showHseShipData(hseCheckShips);
                    }

                    @Override
                    protected void error(String message) {
                        view.showError(message);
                    }

                    @Override
                    protected void complete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }

    @Override
    public void getHseShipData() {
        List<HseCheckShip> checkShips = DataSupport.findAll(HseCheckShip.class);
        view.showHseShipData(checkShips);
    }
}
