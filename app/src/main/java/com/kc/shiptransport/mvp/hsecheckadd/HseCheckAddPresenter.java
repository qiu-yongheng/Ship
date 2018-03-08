package com.kc.shiptransport.mvp.hsecheckadd;

import android.content.Context;

import com.kc.shiptransport.data.bean.hse.HseCheckAddBean;
import com.kc.shiptransport.data.bean.hse.HseCheckListBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/11/22  14:32
 * @desc ${TODD}
 */

public class HseCheckAddPresenter implements HseCheckAddContract.Presenter{
    private final Context context;
    private final HseCheckAddContract.View view;
    private final CompositeDisposable compositeDisposable;
    private final DataRepository dataRepository;

    public HseCheckAddPresenter(Context context, HseCheckAddContract.View view, DataRepository dataRepository) {
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
    public void commit(List<HseCheckAddBean> list) {
        view.showLoading(true);
        dataRepository
                .InsertSafeHSECheckedRecord(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        view.commitResult(aBoolean);
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
    public void getDetail(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetSafeHSECheckedRecordByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<HseCheckListBean>() {
                    @Override
                    protected void next(HseCheckListBean bean) {
                        view.showDetail(bean);
                    }

                    @Override
                    protected void error(String message) {
                        view.showLoading(false);
                        view.showError(message);
                    }

                    @Override
                    protected void complete() {
                        view.showLoading(false);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }
}
