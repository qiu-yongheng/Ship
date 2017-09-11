package com.kc.shiptransport.mvp.upcominglist;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.backlog.BackLog;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/9/8  18:16
 * @desc ${TODD}
 */

public class UpcomingListPresenter implements UpcomingListContract.Presenter{
    private final Context context;
    private final UpcomingListContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public UpcomingListPresenter(Context context, UpcomingListContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getUpcomingList(String ID) {
        view.showLoading(true);
        dataRepository
                .getUpcomingList(ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BackLog>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BackLog list) {
                        view.showUpcomingList(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                        view.showError("加载成功");
                    }
                });
    }

    @Override
    public void getPending(int PageSize, int PageCount, final String pendingID) {
        view.showLoading(true);
        dataRepository
                .GetPendingTaskList(PageSize, PageCount)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<BackLog>, ObservableSource<BackLog>>() {
                    @Override
                    public ObservableSource<BackLog> apply(@NonNull List<BackLog> backLogs) throws Exception {
                        //
                        return dataRepository.getUpcomingList(pendingID);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BackLog>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BackLog list) {
                        view.showUpcomingList(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                        view.showError("加载成功");
                    }
                });

    }
}
