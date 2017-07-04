package com.kc.shiptransport.mvp.sampledetail;

import android.content.Context;
import android.util.Log;

import com.kc.shiptransport.data.bean.SampleShowDatesBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.SampleImageList;
import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.util.SharePreferenceUtil;

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
        dataRepository
                .getCommitImageList(sandSample) // 解析缓存的数据, 如果IsUpdate = 0, 添加到队列
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<SampleImageList>>() {
                    @Override
                    public void accept(@NonNull List<SampleImageList> sampleCommitLists) throws Exception {
                        // 设置最大进度
                        view.showProgress(sampleCommitLists.size());
                    }
                })
                .flatMap(new Function<List<SampleImageList>, ObservableSource<SampleImageList>>() {
                    @Override
                    public ObservableSource<SampleImageList> apply(@NonNull List<SampleImageList> sampleCommitLists) throws Exception {
                        // 遍历集合
                        return Observable.fromIterable(sampleCommitLists);
                    }
                })
                .map(new Function<SampleImageList, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull SampleImageList commitList) throws Exception {
                        // 上传图片
                        upImage(commitList);
                        return true;
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
                        Log.d("==", "上传图片");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                        Log.d("==", "上传图片完成");
                        // 上传json数据
                        view.showImageUpdataResult();
                    }
                });
    }

    /**
     * 上传图片
     * @param sampleImageList
     */
    @Override
    public void upImage(final SampleImageList sampleImageList) {
        dataRepository
                .commitImage(sampleImageList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        // 进度条 + 1
                        view.updateProgress();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // TODO 保存到任务队列
                        Log.d("==", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取要显示的数据
     * @param position
     */
    @Override
    public void getDates(int position) {
        view.showLoading(true);
        dataRepository
                .getSampleTaskForPosition(position) // 根据进场ID获取数据
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<SandSample, ObservableSource<SampleShowDatesBean>>() {
                    @Override
                    public ObservableSource<SampleShowDatesBean> apply(@NonNull SandSample sandSample) throws Exception {
                        // 获取要显示的数据
                        return dataRepository.GetSandSamplingBySubcontractorInterimApproachPlanID(sandSample.getItemID());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SampleShowDatesBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull SampleShowDatesBean sampleShowDatesBean) {
                        view.showDetailList(sampleShowDatesBean);
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

    /**
     * 图片提交完后, 提交json
     * @param sampleShowDates
     */
    @Override
    public void commitJson(final SampleShowDatesBean sampleShowDates) {
        view.showLoading(true);
        dataRepository
                .InsertSandSampling(sampleShowDates)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Boolean, ObservableSource<SampleShowDatesBean>>() {
                    @Override
                    public ObservableSource<SampleShowDatesBean> apply(@NonNull Boolean aBoolean) throws Exception {
                        // 删除缓存
                        // 成功后, 删除缓存
                        SharePreferenceUtil.saveString(context, String.valueOf(sampleShowDates.getSubcontractorInterimApproachPlanID()), "");

                        int i = DataSupport.deleteAll(SampleImageList.class, "itemID = ?", String.valueOf(sampleShowDates.getSubcontractorInterimApproachPlanID()));

                        // 重新获取数据
                        return dataRepository.GetSandSamplingBySubcontractorInterimApproachPlanID(sampleShowDates.getSubcontractorInterimApproachPlanID());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SampleShowDatesBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull SampleShowDatesBean bean) {
                        view.showDetailList(bean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                    }

                    @Override
                    public void onComplete() {
                        view.showImageUpdataResult();
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void deleteItem(int ItemID) {
        view.showLoading(true);
        dataRepository
                .DeleteSandSamplingNumRecordByItemID(ItemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showDeleteResult(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }
}
