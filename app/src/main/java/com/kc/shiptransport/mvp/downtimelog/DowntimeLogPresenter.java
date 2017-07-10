package com.kc.shiptransport.mvp.downtimelog;

import android.content.Context;
import android.widget.Toast;

import com.kc.shiptransport.data.bean.downlog.DownLogBean;
import com.kc.shiptransport.data.bean.threadsandlog.ThreadSandLogBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.SettingUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/7/10  15:10
 * @desc ${TODD}
 */

public class DowntimeLogPresenter implements DowntimeLogContract.Presenter {
    private final Context context;
    private final DowntimeLogContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public DowntimeLogPresenter(Context context, DowntimeLogContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        this.dataRepository = dataRepository;
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
    public void scanner(int ItemID, String ShipAccount, String StartTime, String EndTime, String StopTypeID, String Creator, int type) {
        view.showLoading(true);

        switch (type) {
            case SettingUtil.TYPE_STOP:
                /** 停工 */

                dataRepository
                        .GetConstructionBoatStopDaily(ItemID, ShipAccount, StartTime, EndTime, StopTypeID, Creator)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<DownLogBean>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull List<DownLogBean> list) {
                                view.showLog(list);
                                Toast.makeText(context, "搜索到: " + list.size() + "条数据", Toast.LENGTH_SHORT).show();
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

                break;
            case SettingUtil.TYPE_THREAD:
                /** 抛砂 */

                dataRepository
                        .GetConstructionBoatThrowingSandList(ItemID, ShipAccount, StartTime, EndTime, Creator)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<ThreadSandLogBean>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull List<ThreadSandLogBean> threadSandLogBeen) {
                                view.showThreadLog(threadSandLogBeen);
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

                break;
        }


    }
}
