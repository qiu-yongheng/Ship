package com.kc.shiptransport.mvp.partition;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.partition.PartitionNum;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/7/7  10:21
 * @desc ${TODD}
 */

public class PartitionPresenter implements PartitionContract.Presenter{
    private final Context context;
    private final PartitionContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public PartitionPresenter(Context context, PartitionContract.View view, DataRepository dataRepository) {
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
     * 根据当前登录用户, 查询抛沙分区
     * @param userID
     */
    @Override
    public void getList(String userID) {
        view.showLoading(true);
        dataRepository
                .getPartitionNum(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PartitionNum>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<PartitionNum> list) {
                        view.showList(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }
}
