package com.kc.shiptransport.mvp.supplydetail;

import android.content.Context;
import android.widget.Toast;

import com.kc.shiptransport.data.bean.CommitImgListBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.db.supply.SupplyDetail;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

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
 * @time 2017/6/1  11:18
 * @desc ${TODD}
 */

public class SupplyDetailPresenter implements SupplyDetailContract.Presenter {
    private final Context context;
    private final SupplyDetailContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;
    private final int success = 1;
    private final int fault = 0;

    public SupplyDetailPresenter(Context context, SupplyDetailContract.View view) {
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

    /**
     * 获取供沙详情
     * 1. 先从DB获取
     * 2. 如果没有缓存, 发送网络请求
     */
    @Override
    public void getShipDetail(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetReceptionSandBySubcontractorInterimApproachPlanID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SupplyDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(SupplyDetail value) {
                        view.showShipDetail(value);
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
    public void getSupplyTime() {
        view.showSupplyTime(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));
    }

    @Override
    public void getTotalVolume(String ship, String deck) {
        try {
            double i1 = ship.equals("") ? 0 : Double.valueOf(ship);
            double i2 = deck.equals("") ? 0 : Double.valueOf(deck);
            view.showTotalVolume(String.valueOf(i1 + i2));
        } catch (Exception e) {
            Toast.makeText(context, "请输入数字", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 提交
     */
    @Override
    public void commit(final int itemID, String ReceptionSandTime) {
        view.showLoading(true);
        dataRepository
                .updateForReceptionSandTime(itemID, ReceptionSandTime)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, Observable<Boolean>>() { // 同步
                    @Override
                    public Observable<Boolean> apply(Integer integer) throws Exception {
                        if (integer == success) {
                            return dataRepository.doRefresh(SharePreferenceUtil.getInt(context, SettingUtil.WEEK_JUMP_PLAN),
                                    SharePreferenceUtil.getString(context, SettingUtil.SUBCONTRACTOR_ACCOUNT, ""));
                        } else {
                            return null;
                        }
                    }
                })
                .flatMap(new Function<Boolean, Observable<Acceptance>>() { // 更新
                    @Override
                    public Observable<Acceptance> apply(Boolean aBoolean) throws Exception {
                        return dataRepository.getAcceptanceByItemID(itemID, false);
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
    public void start(int itemID) {
        // 1.
        getShipDetail(itemID);

        // 2.
        getSupplyTime();
    }

    /**
     * 提交图片
     * @param imageMultipleResultEvent
     * @param itemID
     * @param creator
     */
    @Override
    public void getCommitImgList(ImageMultipleResultEvent imageMultipleResultEvent, int itemID, String creator) {
        dataRepository
                .getAmountImgList(imageMultipleResultEvent, itemID, creator)
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
                .InsertReceptionSandAttachment(amountImgListBean.getJson(), amountImgListBean.getByteDataStr())
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

    /**
     * 删除图片
     * @param ItemID
     */
    @Override
    public void deleteImgForItemID(int ItemID) {
        view.showLoading(true);
        dataRepository
                .DeleteReceptionSandAttachmentByItemID(ItemID)
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
}
