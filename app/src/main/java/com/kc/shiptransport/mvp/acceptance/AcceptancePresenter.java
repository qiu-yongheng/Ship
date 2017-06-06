package com.kc.shiptransport.mvp.acceptance;

import android.content.Context;
import android.util.Log;

import com.kc.shiptransport.data.bean.WeekTaskBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.util.CalendarUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/1  15:12
 * @desc ${TODD}
 */

public class AcceptancePresenter implements AcceptanceContract.Presenter {
    private final Context context;
    private final AcceptanceContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public AcceptancePresenter(Context context, AcceptanceContract.View view) {
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
     * 验收人名字
     */
    @Override
    public void getAcceptanceManName() {
        List<Subcontractor> subcontractorList = DataSupport.findAll(Subcontractor.class);
        view.showAcceptanceMan(subcontractorList.get(0).getSubcontractorName());
    }

    @Override
    public void getCurrentDate(int jumpWeek) {
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
                        view.showCurrentDate(value);
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
     * 待验收航次
     */
    @Override
    public void getStayAcceptanceShip() {
        int num = 0;
        // 1. 获取一周任务
        List<WeekTask> weekTasks = DataSupport.findAll(WeekTask.class);

        // 2. 获取一验收任务
        if (weekTasks != null) {
            for (WeekTask weektask : weekTasks) {
                List<Acceptance> acceptances = DataSupport.where("isAcceptance = ? and ItemID = ?", "1", String.valueOf(weektask.getItemID())).find(Acceptance.class);
                if (!acceptances.isEmpty()) {
                    num++;
                }
            }


            view.showStayAcceptanceShip(String.valueOf(weekTasks.size() - num));
        }
    }

    @Override
    public void getWeekTask(final int jumpWeek) {
        dataRepository
                .getWeekTask()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WeekTask>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<WeekTask> value) {
                        // 获取一周日期数据
                        List<String> dates = CalendarUtil.getdateOfWeek("dd", jumpWeek);
                        Log.d("==", dates.toString());
                        view.showWeekTask(dates, value);
                        view.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        // 统计每日计划量
                        getDayCount();

                        // 统计未验收量
                        getStayAcceptanceShip();
                    }
                });
    }

    @Override
    public void getDayCount() {
        dataRepository
                .getDayCount()
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

    @Override
    public void doRefresh(final int jumpWeek) {
        view.showLoading(true);
        dataRepository
                .doRefresh(jumpWeek)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WeekTaskBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<WeekTaskBean> value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        getWeekTask(jumpWeek);
                    }
                });
    }

    @Override
    public void start(int jumpWeek) {
        // 1. 获取当前月
        getCurrentDate(jumpWeek);

        // 2. 刷新数据
        doRefresh(jumpWeek);

        // 3. 验收人
        getAcceptanceManName();

        // 4. 获取所有分包商
        getSubcontractor();
    }

    @Override
    public void getSubcontractor() {
        Observable.create(new ObservableOnSubscribe<List<Subcontractor>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Subcontractor>> e) throws Exception {
                dataRepository.getSubcontractorInfo("");
                // 从数据库获取分包商
                List<Subcontractor> subcontractorList = DataSupport.findAll(Subcontractor.class);

                e.onNext(subcontractorList);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Subcontractor>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Subcontractor> value) {
                        view.showSpinner(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}