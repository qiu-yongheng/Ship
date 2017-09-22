package com.kc.shiptransport.mvp.sampledetail;

import android.content.Context;
import android.util.Log;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.db.sample.SampleData;
import com.kc.shiptransport.db.sample.SampleImageList;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
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

    /**
     * 根据position获取进场计划数据
     *
     * @param position
     */
    @Override
    public void getShipInfo(int position) {
        dataRepository
                .getSampleTaskForItemID(position)
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
     *
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
                        if (sampleCommitLists.isEmpty()) {
                            // 直接提交json
                            view.startCommit();
                        } else {
                            // 设置最大进度
                            view.showProgress(sampleCommitLists.size());
                        }
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
     * 1.23根据进场计划ID获取验砂取样信息明细
     *  @param itemID
     *
     */
    @Override
    public void getDates(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetSandSamplingBySubcontractorInterimApproachPlanID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SampleData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull SampleData sampleShowDatesBean) {
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
     * 删除取样编号
     * @param ItemID
     */
    @Override
    public void deleteNumForItemID(int ItemID, final int p_position) {
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
                        view.showDeleteNumForItemID(aBoolean, p_position);
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

    /**
     * 删除图片
     * @param itemID
     * @param p_position
     * @param position
     */
    @Override
    public void deleteImgForItemID(int itemID, final int p_position, final int position) {
        view.showLoading(true);
        dataRepository
                .DeleteSandSamplingAttachmentByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showDeleteImgResult(aBoolean, p_position, position);
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

    /**
     * 提交图片
     * @param imageMultipleResultEvent
     * @param SandSamplingID
     * @param SandSamplingNumID
     * @param ConstructionBoatAccount
     * @param p_position
     */
    @Override
    public void commitImgList(ImageMultipleResultEvent imageMultipleResultEvent, int SandSamplingID, int SandSamplingNumID, String ConstructionBoatAccount, int p_position) {
        view.showLoading(true);
        dataRepository
                .getSampleImgList(imageMultipleResultEvent, SandSamplingID, SandSamplingNumID, ConstructionBoatAccount, p_position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<SampleImageList>>() {
                    @Override
                    public void accept(@NonNull List<SampleImageList> sampleImageLists) throws Exception {
                        // 设置最大进度
                        view.showProgress(sampleImageLists.size());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<SampleImageList>, ObservableSource<SampleImageList>>() {
                    @Override
                    public ObservableSource<SampleImageList> apply(@NonNull List<SampleImageList> sampleImageLists) throws Exception {
                        // 遍历
                        return Observable.fromIterable(sampleImageLists);
                    }
                })
                .map(new Function<SampleImageList, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull SampleImageList sampleImageList) throws Exception {
                        // 提交单张图片
                        upImage(sampleImageList);
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
     * 上传图片
     *
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
     * 图片提交完后, 提交json
     *
     */
    @Override
    public void commitJson(final int itemID) {
        view.showLoading(true);
        dataRepository
                .InsertSandSampling()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Boolean, ObservableSource<SampleData>>() {
                    @Override
                    public ObservableSource<SampleData> apply(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 成功后, 删除图片提交缓存
                            DataSupport.deleteAll(SampleImageList.class);
                        }

                        // 重新获取数据
                        return dataRepository.GetSandSamplingBySubcontractorInterimApproachPlanID(itemID);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SampleData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull SampleData bean) {
                        //view.showDetailList(bean);
                        // 返回
                        view.showCommitReturn();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        // 提交失败, 清空图片提交缓存
                        DataSupport.deleteAll(SampleImageList.class);
                    }

                    @Override
                    public void onComplete() {
                        view.showError("提交成功");
                        view.showImageUpdataResult();
                        view.showLoading(false);
                    }
                });
    }
}
