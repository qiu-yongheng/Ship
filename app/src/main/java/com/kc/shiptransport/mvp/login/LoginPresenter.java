package com.kc.shiptransport.mvp.login;

import android.content.Context;

import com.google.gson.Gson;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/5/16  10:03
 * @desc ${TODD}
 */

public class LoginPresenter implements LoginContract.Presenter {
    private final Context context;
    private final LoginContract.View view;
    private final RemoteDataSource remoteDataSource;
    private final Gson gson;
    private final CompositeDisposable mCompositeDisposable;
    private final DataRepository mDataRepository;

    public LoginPresenter(Context context, LoginContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        remoteDataSource = new RemoteDataSource();
        mCompositeDisposable = new CompositeDisposable();
        mDataRepository = new DataRepository();
        gson = new Gson();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        /* 切断水管 */
        mCompositeDisposable.clear();
    }

    /**
     * 获取分包商与船数据
     */
    @Override
    public void loadData(final String username) {
        // 获取分包商
        Observable<Boolean> subcontractor = mDataRepository
                .getSubcontractor(username)
                .subscribeOn(Schedulers.io());

        // 获取船
        Observable<Boolean> ship = mDataRepository
                .getShip(username)
                .subscribeOn(Schedulers.io());

        // 获取要显示的模块
        Observable<Boolean> appList = mDataRepository
                .getAppList(username)
                .subscribeOn(Schedulers.io());

        Observable.zip(subcontractor, ship, appList, new Function3<Boolean, Boolean, Boolean, Boolean>() {
            @Override
            public Boolean apply(Boolean aBoolean, Boolean aBoolean2, Boolean aBoolean3) throws Exception {
                return true;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showloading(false);
                        view.showSyncError();
                    }

                    @Override
                    public void onComplete() {
                        view.showloading(false);
                        view.navigateToMain();
                    }
                });
    }

    /**
     * 登录
     */
    @Override
    public void login(final String username, final String password) {
        view.showloading(true);
        mDataRepository
                .login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean value) {
                        view.showResult(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showloading(false);
                        view.showNetworkError();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 获取分包商
     */
    @Override
    public void getSubcontractor() {

    }

    /**
     * 获取船
     */
    @Override
    public void getShip() {

    }
}
