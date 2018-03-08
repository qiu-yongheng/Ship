package com.kc.shiptransport.mvp.hserectificationlist;

import android.content.Context;

import com.kc.shiptransport.data.bean.hse.HseDefectListBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/11/29  8:39
 * @desc ${TODD}
 */

public class HseRectificationListPresenter implements HseRectificationListContract.Presenter{

    private final CompositeDisposable compositeDisposable;
    private final Context context;
    private final HseRectificationListContract.View view;
    private final DataRepository dataRepository;

    public HseRectificationListPresenter(Context context, HseRectificationListContract.View view, DataRepository dataRepository) {
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
    public void getDefects(int pageSize, final int pageCount, HseDefectListBean bean, String startDate, String endDate, boolean isShow) {
        if (isShow) {
            view.showLoading(true);
        }
        dataRepository
                .GetSafeDefectRecords(pageSize, pageCount, bean, startDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<List<HseDefectListBean>>() {
                    @Override
                    protected void next(List<HseDefectListBean> listBeans) {
                        view.showDefects(listBeans, pageCount == 1);
                        if (listBeans.isEmpty()) {
                            view.showError("没有数据");
                        }
                    }

                    @Override
                    protected void error(String message) {
                        view.showError(message);
                        view.showLoading(false);
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
