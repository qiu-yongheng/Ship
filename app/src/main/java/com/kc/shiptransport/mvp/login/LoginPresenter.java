package com.kc.shiptransport.mvp.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.data.bean.LoginResult;
import com.kc.shiptransport.data.bean.ShipBean;
import com.kc.shiptransport.data.bean.SubcontractorBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.exception.ErrorLoginException;
import com.kc.shiptransport.util.NetworkUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                // 1. 获取数据, 缓存到数据库
                String subcontractorInfo = remoteDataSource.getSubcontractorInfo(username);
                Log.d("info", subcontractorInfo);
                // 保存到数据库
                List<SubcontractorBean> ls = gson.fromJson(subcontractorInfo, new TypeToken<List<SubcontractorBean>>() {
                }.getType());
                if (ls != null && !ls.isEmpty()) {
                    // 清空数据
                    DataSupport.deleteAll(Subcontractor.class);
                    for (SubcontractorBean bean : ls) {
                        Subcontractor subcontractor = new Subcontractor();
                        subcontractor.setSubcontractorAccount(bean.getSubcontractorAccount());
                        subcontractor.setSubcontractorName(bean.getSubcontractorName());
                        subcontractor.save();
                    }
                }

                /**
                 * ItemID : 1
                 * ShipID : zd881
                 * ShipAccount : shipd
                 * ShipName : 船舶D
                 * ShipType : B类
                 * MaxSandSupplyCount : 2000
                 */
                // 2. 获取数据, 缓存到数据库
                String shipInfo = remoteDataSource.getShipInfo(username);
                List<ShipBean> list = gson.fromJson(shipInfo, new TypeToken<List<ShipBean>>() {
                }.getType());
                if (list != null && !list.isEmpty()) {
                    DataSupport.deleteAll(Ship.class);
                    for (ShipBean listBean : list) {
                        Ship ship = new Ship();
                        ship.setItemID(listBean.getItemID());
                        ship.setShipID(listBean.getShipID());
                        ship.setShipAccount(listBean.getShipAccount());
                        ship.setShipName(listBean.getShipName());
                        ship.setShipType(listBean.getShipType());
                        ship.setMaxSandSupplyCount(listBean.getMaxSandSupplyCount());
                        ship.setSelected("0");
                        ship.save();
                    }
                }

                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showloading(false);
                        view.showNetworkError();
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
        if (NetworkUtil.networkConnected(context)) {
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    String loginInfo = remoteDataSource.getLoginInfo(username, password);
                    e.onNext(loginInfo);
                    e.onComplete();
                }
            })
                    .filter(new Predicate<String>() {
                        @Override
                        public boolean test(String s) throws Exception {
                            LoginResult loginResult = gson.fromJson(s, LoginResult.class);
                            if (loginResult.getMessage() == 0) {
                                throw new ErrorLoginException("账号或密码错误");
                            }
                            return loginResult.getMessage() == 1;
                        }
                    })
                    .map(new Function<String, Boolean>() { // 获取分包商, 船舶信息
                        @Override
                        public Boolean apply(String s) throws Exception {
                            mDataRepository.getSubcontractorInfo(username);
                            mDataRepository.getShipInfo(username);
                            return true;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mCompositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(Boolean value) {
//                            LoginResult loginResult = gson.fromJson(value, LoginResult.class);
//                            view.showResult(loginResult);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e instanceof ErrorLoginException) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            view.showloading(false);
                            view.showNetworkError();
                        }

                        @Override
                        public void onComplete() {
                            Log.d("==", "login onComplete");
                            view.showloading(false);
                            view.navigateToMain();
                        }
                    });
        } else {
            view.showloading(false);
            view.showNetworkError();
        }
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
