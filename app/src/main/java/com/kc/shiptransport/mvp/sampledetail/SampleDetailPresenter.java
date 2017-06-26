package com.kc.shiptransport.mvp.sampledetail;

import android.content.Context;

import com.kc.shiptransport.data.bean.SampleCommitList;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.SandSample;

import java.util.List;

import io.reactivex.Observable;
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
 * @time 2017/6/14  10:58
 * @desc ${TODD}
 */

public class SampleDetailPresenter implements SampleDetailContract.Presenter {
    private final Context context;
    private final SampleDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public SampleDetailPresenter(Context context, SampleDetailContract.View view, DataRepository dataRepository) {
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
    public void getShipInfo(int position) {
        dataRepository
                .getSampleTaskForPosition(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SandSample>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull SandSample sandSample) {
                        view.showTime(sandSample.getPlanDay());
                        view.showShipName(sandSample.getShipName());
                        view.showShipNumber(sandSample.getShipItemNum());
                        view.showItemID(sandSample);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void start(int position) {
        getShipInfo(position);
    }

    /**
     * 提交
     * @param sandSample
     */
    @Override
    public void commit(final SandSample sandSample) {
        view.showLoading(true);
        dataRepository
                .getCommitImageList(sandSample) // 解析缓存的数据, 如果IsUpdate = 0, 添加到队列
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<SampleCommitList>, ObservableSource<SampleCommitList>>() {
                    @Override
                    public ObservableSource<SampleCommitList> apply(@NonNull List<SampleCommitList> sampleCommitLists) throws Exception {
                        // 遍历集合
                        return Observable.fromIterable(sampleCommitLists);
                    }
                })
                .flatMap(new Function<SampleCommitList, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull SampleCommitList sampleCommitList) throws Exception {
                        // 上传图片
                        return dataRepository.commitImage(sampleCommitList);
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

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }
}
