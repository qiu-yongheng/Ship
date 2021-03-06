package com.kc.shiptransport.mvp.amountdetail;

import android.content.Context;
import android.widget.Toast;

import com.kc.shiptransport.data.bean.CommitImgListBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.amount.AmountDetail;
import com.kc.shiptransport.db.amount.AmountOption;
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
 * @time 2017/6/14  8:58
 * @desc ${TODD}
 */

public class AmountDetailPresenter implements AmountDetailContract.Presenter{
    private final Context context;
    private final AmountDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public AmountDetailPresenter(Context context, AmountDetailContract.View view, DataRepository dataRepository) {
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
    public void getShipDetail(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetTheAmountOfSideRecordBySubcontractorInterimApproachPlanID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AmountDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(AmountDetail value) {
                        view.showShipDetail(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError("获取数据失败");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void getShipDetailList(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetTheAmountOfSideRecordBySubcontractorInterimApproachPlanID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AmountDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(AmountDetail value) {
                        view.showImgList(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError("获取数据失败");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void getSupplyTime() {
        view.showSupplyTime(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));
    }

    @Override
    public void getTotalVolume(String ship, String deck, String capacity) {
        try {
            double i1 = ship.equals("") ? 0 : Double.valueOf(ship);
            double i2 = deck.equals("") ? 0 : Double.valueOf(deck);
            double i3 = (capacity == null ||capacity.equals(""))  ? 0 : Double.valueOf(capacity);
            view.showTotalVolume(String.valueOf(i2 + i3 - i1));
        } catch (Exception e) {
            Toast.makeText(context, "请输入数字", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void commit(final int itemID,
                       final String TheAmountOfTime,
                       final String subcontractorAccount,
                       final int SubcontractorInterimApproachPlanID,
                       final String ShipAccount,
                       final String Capacity,
                       final String DeckGauge,
                       final String Deduction,
                       final String Creator,
                       final float LaserQuantitySand,
                       final String TheAmountOfPersonnelID,
                       final String TheAmountOfType,
                       final int IsSumbitted,
                       final String Remark) {
        view.showLoading(true);
        dataRepository
                .InsertTheAmountOfSideRecord(itemID,
                        TheAmountOfTime,
                        subcontractorAccount,
                        SubcontractorInterimApproachPlanID,
                        ShipAccount,
                        Capacity,
                        DeckGauge,
                        Deduction,
                        Creator,
                        LaserQuantitySand,
                        TheAmountOfPersonnelID,
                        TheAmountOfType,
                        IsSumbitted,
                        Remark)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> apply(Boolean aBoolean) throws Exception {
                        // 提交成功后, 重新加载列表数据, 并过滤未验收的数据
                        if (aBoolean) {
                            return dataRepository.doRefresh(SharePreferenceUtil.getInt(context, SettingUtil.WEEK_JUMP),
                                    SharePreferenceUtil.getString(context, SettingUtil.SUBCONTRACTOR_ACCOUNT, ""));
                        } else {
                            return null;
                        }
                    }
                })
                .flatMap(new Function<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> apply(Boolean aBoolean) throws Exception {
                        // 删除未验收数据后, 进行重新排序
                        return dataRepository.getWeekTaskSort(SettingUtil.TYPE_AMOUNT, SharePreferenceUtil.getInt(context, SettingUtil.WEEK_JUMP));
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Boolean value) {
                        view.showCommitResult(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError("提交失败");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    @Override
    public void start(int itemID) {
        getShipDetail(itemID);
        getSupplyTime();
        getAmountOption();
    }

    /**
     * 提交图片
     * @param imageMultipleResultEvent
     * @param itemID
     * @param creator
     */
    @Override
    public void getCommitImgList(ImageMultipleResultEvent imageMultipleResultEvent, int itemID, String creator) {
        view.showLoading(true);
        dataRepository
                .getImgList(imageMultipleResultEvent, itemID, creator)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<CommitImgListBean>>() {
                    @Override
                    public void accept(@NonNull List<CommitImgListBean> amountImgListBeen) throws Exception {
                        // 设置最大进度
                        view.showProgress(amountImgListBeen.size());
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
                    public Boolean apply(@NonNull CommitImgListBean amountImgListBean) throws Exception {
                        // 提交单张图片
                        commitImg(amountImgListBean);
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
     * 提交单张图片
     * @param amountImgListBean
     */
    @Override
    public void commitImg(CommitImgListBean amountImgListBean) {
        dataRepository
                .InsertTheAmountOfSideAttachment(amountImgListBean.getJson(), amountImgListBean.getByteDataStr())
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

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 删除图片
     * @param ItemID
     */
    @Override
    public void deleteImgForItemID(int ItemID) {
        view.showLoading(true);
        dataRepository
                .DeleteTheAmountOfSideAttachmentByItemID(ItemID)
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
                        view.showError("删除失败, 请重试");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 获取量方人员
     */
    @Override
    public void getAmountOption() {
        dataRepository
                .GetTheAmountOfPersonnelOptions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AmountOption>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<AmountOption> list) {
                        view.showAmountOption(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        List<AmountOption> all = DataSupport.findAll(AmountOption.class);
                        if (!all.isEmpty()) {
                            view.showAmountOption(all);
                        } else {
                            view.showError("获取量方人员失败");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
