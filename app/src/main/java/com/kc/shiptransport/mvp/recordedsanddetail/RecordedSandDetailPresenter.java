package com.kc.shiptransport.mvp.recordedsanddetail;

import android.content.Context;

import com.kc.shiptransport.data.bean.RecordedSandUpdataBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.RecordList;
import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/19  11:30
 * @desc ${TODD}
 */

public class RecordedSandDetailPresenter implements RecordedSandDetailContract.Presenter {
    private final Context context;
    private final RecordedSandDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public RecordedSandDetailPresenter(Context context, RecordedSandDetailContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        getSpinner();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getSpinner() {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<String>> e) throws Exception {
                List<ConstructionBoat> boats = DataSupport.order("position asc").find(ConstructionBoat.class);
                List<String> datas = new ArrayList<>();

                datas.add("请选择施工船");

                for (ConstructionBoat boat : boats) {
                    datas.add(boat.getShipName());
                }

                e.onNext(datas);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<String> list) {
                        view.showSpinner(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getShip(int itemID) {
        dataRepository
                .getRecordListForItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecordList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull RecordList recordList) {
                        view.showShip(recordList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void commit(RecordedSandUpdataBean bean) {
        view.showLoadding(true);
        dataRepository
                .InsertOverSandRecord(bean)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 提交成功, 同步数据
                            return dataRepository.getOverSandRecordList(SharePreferenceUtil.getInt(context, SettingUtil.WEEK_JUMP_BASE),
                                    SharePreferenceUtil.getString(context, SettingUtil.SUBCONTRACTOR_ACCOUNT, ""));
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
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showResult(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoadding(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoadding(false);
                    }
                });
    }

    /**
     * 获取需要修改的数据
     * @param itemID
     */
    @Override
    public void getDetail(int itemID) {
        view.showLoadding(true);
        dataRepository
                .GetOverSandRecordByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecordedSandShowList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull RecordedSandShowList recordedSandShowList) {
                        view.showDetail(recordedSandShowList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoadding(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoadding(false);
                    }
                });
    }
}
