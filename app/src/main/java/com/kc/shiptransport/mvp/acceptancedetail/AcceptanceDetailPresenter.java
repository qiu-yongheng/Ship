package com.kc.shiptransport.mvp.acceptancedetail;

import android.content.Context;
import android.util.Log;

import com.kc.shiptransport.data.bean.ScanCommitBean;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.data.bean.acceptanceinfo.AcceptanceInfoBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.db.backlog.BackLog;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

import org.litepal.crud.DataSupport;

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
 * @author qiuyongheng
 * @time 2017/6/1  16:09
 * @desc ${TODD}
 */

public class AcceptanceDetailPresenter implements AcceptanceDetailContract.Presenter {
    private final Context context;
    private final AcceptanceDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;
    private final int success = 1;
    private final int fault = 0;

    public AcceptanceDetailPresenter(Context context, AcceptanceDetailContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        dataRepository = new DataRepository();
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
    public void getShipDetail(final int itemID) {
        view.showLoading(true);

        dataRepository
                .GetSandSubcontractorPreAcceptanceEvaluationBySubcontractorInterimApproachPlanID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<AcceptanceInfoBean>() {
                    @Override
                    public void accept(@NonNull AcceptanceInfoBean acceptance) throws Exception {
                        // 返回进场计划详细信息
                        view.showShipDetail(acceptance);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<AcceptanceInfoBean, ObservableSource<List<ScannerImgListByTypeBean>>>() {
                    @Override
                    public ObservableSource<List<ScannerImgListByTypeBean>> apply(@NonNull AcceptanceInfoBean acceptance) throws Exception {
                        // 返回预验收图片
                        return dataRepository.GetSubcontractorPerfectBoatScannerAttachmentRecordByAttachmentTypeID(itemID, 2);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ScannerImgListByTypeBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<ScannerImgListByTypeBean> value) {
                        view.showImgList(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void getAcceptanceTime() {
        view.showAcceptanceTime(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));
    }

    /**
     * 1. 提交验收评价
     * 2. 同步数据
     * 3. 更新供砂任务明细
     *
     * @param SubcontractorInterimApproachPlanID 进场ID
     * @param time                               审核时间
     * @param itemID                             评价ID
     * @param rbcomplete                         材料完整性
     * @param rbtimely                           材料及时性
     * @param value
     */
    @Override
    public void commit(final int SubcontractorInterimApproachPlanID,
                       String time,
                       final int itemID,
                       final int rbcomplete,
                       final int rbtimely,
                       AcceptanceInfoBean value,
                       int Status,
                       String Remark) {
        view.showLoading(true);
        dataRepository
                .InsertPreAcceptanceEvaluation(itemID, rbcomplete, rbtimely, time, SubcontractorInterimApproachPlanID, value, Status, Remark)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, Observable<Boolean>>() { // 同步
                    @Override
                    public Observable<Boolean> apply(Integer integer) throws Exception {
                        // 更新当前选中的供应商计划
                        if (integer == success) {
                            return dataRepository.doRefresh(SharePreferenceUtil.getInt(context, SettingUtil.WEEK_JUMP),
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
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Boolean aBoolean) throws Exception {
                        /** 删除不能进行预验砂的任务 */
                        DataSupport.deleteAll(WeekTask.class, "IsAllowPreAcceptanceEvaluation = 0 and PreAcceptanceEvaluationStatus  != 1");
                        dataRepository.dataSort(SharePreferenceUtil.getInt(context, SettingUtil.WEEK_JUMP));
                        return aBoolean;
                    }
                })
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
                .flatMap(new Function<Boolean, Observable<Acceptance>>() { // 更新
                    @Override
                    public Observable<Acceptance> apply(Boolean aBoolean) throws Exception {
                        // 更新供砂明细
                        if (aBoolean) {
                            return dataRepository.getAcceptanceByItemID(SubcontractorInterimApproachPlanID, false);
                        } else {
                            return Observable.create(new ObservableOnSubscribe<Acceptance>() {
                                @Override
                                public void subscribe(@NonNull ObservableEmitter<Acceptance> e) throws Exception {
                                    e.onError(new Throwable("提交失败"));
                                }
                            });
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Acceptance>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Acceptance value) {
                        view.showCommitResult(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showCommitError();
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void cancle() {
        view.showCancle();
    }

    @Override
    public void start(int itemID) {
        getShipDetail(itemID);
        getAcceptanceTime();
    }

    @Override
    public void doEvaluation() {

    }

    @Override
    public void deleteImg(int itemID) {
        view.showLoading(true);
        dataRepository
                .DeleteSubcontractorPerfectBoatScannerAttachmentByItemID(itemID)
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
                        view.showError("图片删除失败, 请重试");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void commitImg(ImageMultipleResultEvent imageMultipleResultEvent, int subID, int typeID, String shipAccount) {
        view.showLoading(true);
        dataRepository
                .getScanImgList(imageMultipleResultEvent, subID, typeID, shipAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<ScanCommitBean>>() {
                    @Override
                    public void accept(@NonNull List<ScanCommitBean> scanCommitBeen) throws Exception {
                        // 设置最大进度
                        view.showProgress(scanCommitBeen.size());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<ScanCommitBean>, ObservableSource<ScanCommitBean>>() {
                    @Override
                    public ObservableSource<ScanCommitBean> apply(@NonNull List<ScanCommitBean> scanCommitBeen) throws Exception {
                        return Observable.fromIterable(scanCommitBeen);
                    }
                })
                .map(new Function<ScanCommitBean, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull ScanCommitBean scanCommitBean) throws Exception {
                        // 上传图片
                        imgCommit(scanCommitBean);
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
                        view.hideProgress();
                        Log.d("==", "上传图片完成");
                    }
                });
    }

    /**
     * 提交图片
     *
     * @param bean
     */
    @Override
    public void imgCommit(ScanCommitBean bean) {
        dataRepository
                .InsertSubcontractorPerfectBoatScannerAttachment(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
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

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 更新图片
     *
     * @param itemID
     */
    @Override
    public void updateImgDetail(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetSubcontractorPerfectBoatScannerAttachmentRecordByAttachmentTypeID(itemID, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ScannerImgListByTypeBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<ScannerImgListByTypeBean> scannerImgListByTypeBeen) {
                        view.showImgList(scannerImgListByTypeBeen);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError("删除图片失败, 请重试");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }
}
