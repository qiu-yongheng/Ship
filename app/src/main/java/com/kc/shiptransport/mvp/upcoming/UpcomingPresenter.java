package com.kc.shiptransport.mvp.upcoming;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.backlog.BackLog;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/9/8  15:58
 * @desc ${TODD}
 */

public class UpcomingPresenter implements UpcomingContract.Presenter {
    private final Context context;
    private final UpcomingContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public UpcomingPresenter(Context context, UpcomingContract.View view, DataRepository dataRepository) {
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

    /**
     * 获取待办信息
     * @param PageSize
     * @param PageCount
     */
    @Override
    public void getPending(int PageSize, int PageCount) {
        view.showLoading(true);
        dataRepository
                .GetPendingTaskList(PageSize, PageCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BackLog>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<BackLog> list) {
                        view.showPending(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                        view.showError("刷新成功");
                    }
                });
    }
}
