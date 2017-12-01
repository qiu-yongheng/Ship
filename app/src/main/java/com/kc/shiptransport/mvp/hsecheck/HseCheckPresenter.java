package com.kc.shiptransport.mvp.hsecheck;

import android.content.Context;

import com.kc.shiptransport.data.bean.hse.HseCheckListBean;
import com.kc.shiptransport.data.bean.hse.HseCheckSelectBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.hse.HseCheckShip;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author 邱永恒
 * @time 2017/11/22  10:33
 * @desc ${TODD}
 */

public class HseCheckPresenter implements HseCheckContract.Presenter {
    private final Context context;
    private final HseCheckContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public HseCheckPresenter(Context context, HseCheckContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = checkNotNull(view, "view cannot be null");
        this.dataRepository = checkNotNull(dataRepository, "dataRepository cannot be null");
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
    public void getDates(int PAGESIZE, final int PAGECOUNT, HseCheckSelectBean bean, boolean isShowDialog) {
        if (isShowDialog) {
            view.showLoading(true);
        }
        Observable<List<HseCheckListBean>> listObservable = dataRepository
                .GetSafeHSECheckedRecords(PAGESIZE, PAGECOUNT, bean)
                .subscribeOn(Schedulers.io());

        Observable<List<HseCheckShip>> shipObservable = dataRepository
                .GetSafeCheckedShip()
                .subscribeOn(Schedulers.io());

        Observable.zip(listObservable, shipObservable, new BiFunction<List<HseCheckListBean>, List<HseCheckShip>, List<HseCheckListBean>>() {
            @Override
            public List<HseCheckListBean> apply(List<HseCheckListBean> hseCheckListBeans, List<HseCheckShip> hseCheckShipListBeans) throws Exception {
                return hseCheckListBeans;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<HseCheckListBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<HseCheckListBean> hseCheckListBeans) {
                        view.showDates(hseCheckListBeans, PAGECOUNT == 1);
                        if (hseCheckListBeans.isEmpty()) {
                            view.showError("没有数据");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void delete(int itemID) {
        view.showLoading(true);
        dataRepository
                .DeleteSafeHSECheckedRecordByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<Boolean>() {
                    @Override
                    protected void _onNext(Boolean aBoolean) {
                        view.showDeleteResult(aBoolean);
                    }

                    @Override
                    protected void _onError(String message) {
                        view.showLoading(false);
                        view.showError(message);
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
