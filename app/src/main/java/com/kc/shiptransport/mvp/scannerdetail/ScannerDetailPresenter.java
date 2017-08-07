package com.kc.shiptransport.mvp.scannerdetail;

import android.content.Context;

import com.kc.shiptransport.data.bean.ScannerListBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.WeekTask;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:58
 * @desc ${TODD}
 */

public class ScannerDetailPresenter implements ScannerDetailContract.Presenter{
    private final Context context;
    private final ScannerDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public ScannerDetailPresenter(Context context, ScannerDetailContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getTitle(int position) {
        dataRepository
                .getWeekTaskForPosition(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeekTask>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull WeekTask weekTask) {
                        view.showTitle(weekTask);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取扫描件类型
     */
    @Override
    public void getScannerType(int position, int type) {
        view.showLoading(true);
        Observable<List<ScannerListBean>> observable = null;
        if (type == 0) {
            // position 编辑
            observable = dataRepository
                    .getWeekTaskForPosition(position)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<WeekTask>() {
                        @Override
                        public void accept(@NonNull WeekTask weekTask) throws Exception {
                            view.showTitle(weekTask);
                        }
                    })
                    .observeOn(Schedulers.io())
                    .flatMap(new Function<WeekTask, ObservableSource<List<ScannerListBean>>>() {
                        @Override
                        public ObservableSource<List<ScannerListBean>> apply(@NonNull WeekTask weekTask) throws Exception {
                            return dataRepository.getScannerType(weekTask.getItemID());
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        } else if (type == 1) {
            // itemID 查看
            List<WeekTask> list = DataSupport.where("ItemID = ?", String.valueOf(position)).find(WeekTask.class);
            view.showTitle(list.get(0));

            observable = dataRepository
                    .getScannerType(position)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        if (observable != null) {
            observable
                    .subscribe(new Observer<List<ScannerListBean>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(@NonNull List<ScannerListBean> scannerImage) {
                            view.showDatas(scannerImage);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            view.showLoading(false);
                            view.showError("数据获取失败");
                        }

                        @Override
                        public void onComplete() {
                            view.showLoading(false);
                        }
                    });
        } else {
            view.showError("数据获取失败");
        }

    }

    @Override
    public void start(int position, int type) {
        // 获取船名
        //getTitle(position);
        // 获取扫描件类型
        getScannerType(position, type);
    }
}
