package com.kc.shiptransport.mvp.hsecheckdefect;

import android.content.Context;

import com.kc.shiptransport.data.bean.hse.HseDefectDeadlineBean;
import com.kc.shiptransport.data.bean.hse.HseDefectListBean;
import com.kc.shiptransport.data.bean.hse.HseDefectTypeBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.hse.HseDefectDeadline;
import com.kc.shiptransport.db.hse.HseDefectType;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/11/22  17:20
 * @desc ${TODD}
 */

public class HseCheckDefectPresenter implements HseCheckDefectContract.Presenter{

    private final CompositeDisposable compositeDisposable;
    private final Context context;
    private final HseCheckDefectContract.View view;
    private final DataRepository dataRepository;

    public HseCheckDefectPresenter(Context context, HseCheckDefectContract.View view, DataRepository dataRepository) {
        this.context= context;
        this.view = view;
        this.dataRepository = dataRepository;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        // 整改期限
        Observable<List<HseDefectDeadline>> deadlineObservable = dataRepository
                .GetSafeRectificationDeadlineOptions(10000, 1, new HseDefectDeadlineBean(""))
                .subscribeOn(Schedulers.io());
        // 缺陷类别
        Observable<List<HseDefectType>> typeObservable = dataRepository
                .GetSafeDefectType(10000, 1, new HseDefectTypeBean("", 0))
                .subscribeOn(Schedulers.io());

        Observable.zip(deadlineObservable, typeObservable, new BiFunction<List<HseDefectDeadline>, List<HseDefectType>, Boolean>() {
            @Override
            public Boolean apply(List<HseDefectDeadline> hseDefectDeadlines, List<HseDefectType> hseDefectTypes) throws Exception {
                if (hseDefectDeadlines.isEmpty() || hseDefectTypes.isEmpty()) {
                    return false;
                }
                return true;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<Boolean>() {
                    @Override
                    protected void next(Boolean aBoolean) {
                        view.showSyncResult(aBoolean);
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

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getDefects(int pageSize, final int pageCount, HseDefectListBean bean, boolean isShow) {
        if (isShow) {
            view.showLoading(true);
        }
        dataRepository
                .GetSafeDefectRecords(pageSize, pageCount, bean, null, null)
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

    @Override
    public void deleteForItem(int itemID) {
        view.showLoading(true);
        dataRepository
                .DeleteDefectRecordByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<Boolean>() {
                    @Override
                    protected void next(Boolean aBoolean) {
                        view.showDeleteResult(aBoolean);
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
