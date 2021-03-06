package com.kc.shiptransport.mvp.exitapplicationassessor;

import android.content.Context;

import com.kc.shiptransport.data.bean.CommitImgListBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.backlog.BackLog;
import com.kc.shiptransport.db.exitapplication.ExitDetail;

import java.util.List;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
 * @author 邱永恒
 * @time 2017/8/31  15:47
 * @desc ${TODD}
 */

public class ExitApplicationAssessorPresenter implements ExitApplicationAssessorContract.Presenter{
    private final Context context;
    private final ExitApplicationAssessorContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public ExitApplicationAssessorPresenter(Context context, ExitApplicationAssessorContract.View view, DataRepository dataRepository) {
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
     * 获取审核数据, 共用1.51接口
     * @param SubcontractorInterimApproachPlanID
     * @param type
     */
    @Override
    public void getDates(int SubcontractorInterimApproachPlanID, final int type) {
        view.showLoading(true);
        dataRepository
                .GetExitApplicationRecordBySubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ExitDetail>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ExitDetail exitDetail) {
                        if (type == 0) {
                            // 第一次进来, 刷新所有数据
                            view.showDates(exitDetail);
                        } else if (type == 1) {
                            // 只刷新图片
                            view.showImgList(exitDetail);
                        }
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

    @Override
    public void commit(int ItemID, String ExitTime, String Creator, String Remark, String RemnantAmount, int SubcontractorInterimApproachPlanID, int isSumbitted, int status) {
        view.showLoading(true);
        dataRepository
                .InsertExitApplicationRecord(ItemID, ExitTime, Creator, Remark, RemnantAmount, SubcontractorInterimApproachPlanID, isSumbitted, status)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Boolean, ObservableSource<List<BackLog>>>() {
                    @Override
                    public ObservableSource<List<BackLog>> apply(@NonNull Boolean aBoolean) throws Exception {
                        // 更新待办
                        return dataRepository.GetPendingTaskList(10000, 1);
                    }
                })
                .flatMap(new Function<List<BackLog>, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull final List<BackLog> list) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Boolean>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                                if (list.isEmpty()) {
                                    e.onNext(false);
                                } else {
                                    e.onNext(true);
                                }

                                e.onComplete();
                            }
                        });
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
                        view.showCommitResult(aBoolean);
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

    @Override
    public void deleteImgForItemID(int itemID) {
        view.showLoading(true);
        dataRepository
                .DeleteExitApplicationAttachmentByItemID(itemID)
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
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     *
     * @param imageMultipleResultEvent
     * @param itemID
     * @param subcontractorAccount
     */
    @Override
    public void getCommitImgList(ImageMultipleResultEvent imageMultipleResultEvent, int itemID, String subcontractorAccount) {
        view.showLoading(true);
        dataRepository
                .getImgList(imageMultipleResultEvent, itemID, subcontractorAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<CommitImgListBean>>() {
                    @Override
                    public void accept(@NonNull List<CommitImgListBean> imgListBeen) throws Exception {
                        // 设置最大进度
                        view.showProgress(imgListBeen.size());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<CommitImgListBean>, ObservableSource<CommitImgListBean>>() {
                    @Override
                    public ObservableSource<CommitImgListBean> apply(@NonNull List<CommitImgListBean> amountImgListBeen) throws Exception {
                        // 遍历
                        return Observable.fromIterable(amountImgListBeen);
                    }
                })
                .map(new Function<CommitImgListBean, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CommitImgListBean imgListBean) throws Exception {
                        // 提交单张图片
                        commitImg(imgListBean);
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
     * 提交图片
     * @param bean
     */
    @Override
    public void commitImg(CommitImgListBean bean) {
        dataRepository
                .InsertExitApplicationAttachment(bean.getJson(), bean.getByteDataStr())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (aBoolean) {
                            view.updateProgress();
                        } else {
                            // 提交失败, 保存
                            view.showError("图片提交失败, 请重试");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // 提交失败, 保存
                        view.showError("图片提交失败, 请重试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
