package com.kc.shiptransport.mvp.basemvp;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/13  15:43
 * @desc ${TODD}
 */

public abstract class BaseMvpPresenter implements BaseMvpContract.Presenter {
    protected final Context context;
    protected final BaseMvpContract.View view;
    protected final DataRepository dataRepository;
    protected final CompositeDisposable compositeDisposable;
    private int jumpWeek;
    private int type;
    private String account;

    public BaseMvpPresenter(Context context, BaseMvpContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * 获取标题
     */
    @Override
    public void subscribe() {
        getTitle();
        getTitleOtherInfo();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getTitle() {
        view.showTitle(getTit());
    }

    // 获取标题
    protected abstract String getTit();


    /**
     * 获取操作人
     */
    @Override
    public void getTitleOtherInfo() {
        dataRepository
                .getSubcontractorName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String value) {
                        view.showTitleOtherInfo(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError("分包商信息获取失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取时间
     *
     * @param jumpWeek
     */
    @Override
    public void getTime(int jumpWeek) {
        dataRepository
                .getCurrentMouthDate(jumpWeek)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String value) {
                        view.showTime(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 根据类型显示 待验收船
     *
     * @param type
     */
    @Override
    public void getStayInfo(int type) {
        // TODO 待完善船次
        dataRepository
                .getStayNum(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        view.showStayInfo(String.valueOf(value));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getWeekTask(int jumpWeek) {

    }

    /**
     * 每日计划量统计
     */
    @Override
    public void getDayCount() {
        dataRepository
                .getDayCount(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer[] value) {
                        // 计算总计划量
                        view.showDayCount(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 刷新数据
     *
     * @param jumpWeek
     */
    @Override
    public void doRefresh(final int jumpWeek, final int type, String account) {
        this.type = type;
        view.showLoading(true);
        Observable<Boolean> observable = null;
        switch (type) {
            case SettingUtil.TYPE_SUPPLY: // 验砂
            case SettingUtil.TYPE_AMOUNT: // TODO 量方 (目前还未抽离, 暂时放在前面)
                observable = dataRepository
                        .doRefresh(jumpWeek)
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Function<Boolean, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> apply(Boolean aBoolean) throws Exception {
                                // 删除未验收数据后, 进行重新排序
                                return dataRepository.getWeekTaskSort(jumpWeek);
                            }
                        });
                break;
            case SettingUtil.TYPE_ACCEPT: // TODO 验收 (目前没有用到)
            case SettingUtil.TYPE_SCANNER: // 扫描件
                observable = dataRepository
                        .doRefresh(jumpWeek)
                        .subscribeOn(Schedulers.io());
                break;

            case SettingUtil.TYPE_RECORDEDSAND: // 过砂记录
                observable = dataRepository
                        .getOverSandRecordList(jumpWeek, account)
                        .subscribeOn(Schedulers.io());
                break;

            case SettingUtil.TYPE_SAMPLE: // 验砂取样
                observable = dataRepository
                        .getSandSamplingList(jumpWeek, account)
                        .subscribeOn(Schedulers.io());
                break;

            case SettingUtil.TYPE_VOYAGEINFO: // 信息完善
                observable = dataRepository
                        .doRefresh(jumpWeek)
                        .subscribeOn(Schedulers.io());
                break;
            case SettingUtil.TYPE_EXIT_APPLICATION:
                /** 退场申请 */
                observable = dataRepository
                        .GetExitApplicationList(jumpWeek, account)
                        .subscribeOn(Schedulers.io());
                break;

        }

        /*  */
        assert observable != null;
        observable
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 统计每日计划量
                            getDayCount();
                        }
                    }
                })
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        // 获取待验收船次
                        getStayInfo(type);
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
                        // 获取一周日期数据
                        List<String> dates = CalendarUtil.getdateOfWeek("dd", jumpWeek);
                        view.showWeekTask(dates);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError("数据获取失败!");
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 获取所有分包商
     */
    @Override
    public void getSubcontractorList() {
        view.showLoading(true);
        Observable.create(new ObservableOnSubscribe<List<SubcontractorList>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SubcontractorList>> e) throws Exception {
                dataRepository.getSubcontractorInfo("");
                // 从数据库获取分包商
                List<SubcontractorList> subcontractorList = DataSupport.findAll(SubcontractorList.class);

                e.onNext(subcontractorList);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SubcontractorList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<SubcontractorList> value) {
                        view.showSpinner(value);
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

    /**
     * 获取数据
     *
     * @param jumpWeek
     * @param type
     * @param account
     */
    @Override
    public void start(int jumpWeek, int type, String account) {
        synchronized (BaseMvpPresenter.class) {
            this.jumpWeek = jumpWeek;
            this.type = type;
            this.account = account;
            getTime(jumpWeek);
            doRefresh(jumpWeek, type, account);
        }
    }
}
