package com.kc.shiptransport.mvp.acceptancedetail;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
    public void getShipDetail(int itemID) {
        view.showLoading(true);
        dataRepository
                .getAcceptanceByItemID(itemID, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Acceptance>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Acceptance value) {
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
    public void getAcceptanceTime() {
        view.showAcceptanceTime(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));
    }

    /**
     * 1. 提交验收评价
     * 2. 同步数据
     * 3. 更新供砂任务明细
     * @param SubcontractorInterimApproachPlanID 进场ID
     * @param time 审核时间
     * @param itemID 评价ID
     * @param rbcomplete 材料完整性
     * @param rbtimely 材料及时性
     * @param shipnum 船次编号
     */
    @Override
    public void commit(final int SubcontractorInterimApproachPlanID, String time, final int itemID, final int rbcomplete, final int rbtimely, final String shipnum) {
        view.showLoading(true);
        dataRepository
                .InsertPreAcceptanceEvaluation(itemID, rbcomplete, rbtimely, time, shipnum, SubcontractorInterimApproachPlanID)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, Observable<Boolean>>() { // 同步
                    @Override
                    public Observable<Boolean> apply(Integer integer) throws Exception {
                        // 更新当前选中的分包商计划
                        if (integer == success) {
                            return dataRepository.doRefresh(SharePreferenceUtil.getInt(context, SettingUtil.WEEK_JUMP_ACCEPTANCE),
                                    SharePreferenceUtil.getString(context, SettingUtil.SUBCONTRACTOR_ACCOUNT, ""));
                        } else {
                            return null;
                        }
                    }
                })
                .flatMap(new Function<Boolean, Observable<Acceptance>>() { // 更新
                    @Override
                    public Observable<Acceptance> apply(Boolean aBoolean) throws Exception {
                        return dataRepository.getAcceptanceByItemID(SubcontractorInterimApproachPlanID, false);
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
}
