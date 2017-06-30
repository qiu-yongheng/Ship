package com.kc.shiptransport.mvp.homedetail;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.AppList;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/6/30 21:47
 * @desc ${TODO}
 */

public class HomeDetailPresenter implements HomeDetailContract.Presenter{

    private final CompositeDisposable mCompositeDisposable;
    private final DataRepository dataRepository;
    private final Context context;
    private final HomeDetailContract.View view;

    public HomeDetailPresenter(Context context, HomeDetailContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        this.dataRepository = dataRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getAppList(int appPID) {
        dataRepository
                .getAppList(appPID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AppList>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<AppList> lists) {
                        view.showAppList(lists);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError("获取数据失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
