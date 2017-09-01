package com.kc.shiptransport.mvp.login;

import android.content.Context;

import com.google.gson.Gson;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.util.BaseUrl;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.NetworkUtil;

import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function4;
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
     * 获取供应商与船数据
     */
    @Override
    public void loadData(final String username) {
        // 获取供应商
        //        Observable<Boolean> subcontractor = mDataRepository
        //                .getSubcontractor(username)
        //                .subscribeOn(Schedulers.io());

        /** 获取船 */
        Observable<Boolean> ship = mDataRepository
                .getShip(username)
                .subscribeOn(Schedulers.io());

        /** 获取要显示的模块 */
        Observable<Boolean> appList = mDataRepository
                .getAppList(username)
                .subscribeOn(Schedulers.io());

        //        // 获取施工船舶(用在过砂记录)
        //        Observable<Boolean> construction = mDataRepository
        //                .GetConstructionBoat()
        //                .subscribeOn(Schedulers.io());

        /** 获取用户信息 */
        Observable<Boolean> userInfo = mDataRepository
                .GetUserDataByLoginName(username)
                .subscribeOn(Schedulers.io());

        /** 获取部门信息 */
        Observable<Boolean> department = mDataRepository
                .GetDepartmentsOptions()
                .subscribeOn(Schedulers.io());


        Observable.zip(ship, appList, userInfo, department, new Function4<Boolean, Boolean, Boolean, Boolean, Boolean>() {
            @Override
            public Boolean apply(@NonNull Boolean aBoolean, @NonNull Boolean aBoolean2, @NonNull Boolean aBoolean3, @NonNull Boolean aBoolean4) throws Exception {
                if (aBoolean && aBoolean2 && aBoolean3 && aBoolean4) {
                    return true;
                } else {
                    return false;
                }
            }
        }).flatMap(new Function<Boolean, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> apply(@NonNull Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    // 获取施工船舶, 如果放在zip中执行, 有一定概率服务器响应不回来
                    return mDataRepository.GetConstructionBoat();
                } else {
                    return Observable.create(new ObservableOnSubscribe<Boolean>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                            e.onNext(false);
                            e.onComplete();
                        }
                    });
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean value) {
                        if (value) {
                            view.navigateToMain();
                        } else {
                            view.showSyncError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showloading(false);
                        if (e instanceof SocketTimeoutException) {
                            view.showError("当前网络不稳定, 请稍候重试");
                        }
                        view.showSyncError();
                    }

                    @Override
                    public void onComplete() {
                        view.showloading(false);
                    }
                });
    }

    /**
     * 登录
     */
    @Override
    public void login(final String username, final String password) {
        view.showloading(true);
        if (NetworkUtil.networkConnected(context)) {
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
                            LogUtil.e("当前环境: " + BaseUrl.EndPoint + "\n" + e);
                            view.showloading(false);
                            view.showNetworkError();
                            if (e instanceof SocketTimeoutException) {
                                view.showError("当前网络不稳定, 请稍候重试");
                            }
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            view.showloading(false);
            view.showError("当前没有网络连接, 请检查网络");
        }

    }

    /**
     * 获取供应商
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
