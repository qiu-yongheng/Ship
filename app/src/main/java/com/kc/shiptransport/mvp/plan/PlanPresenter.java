package com.kc.shiptransport.mvp.plan;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.data.bean.SubmitBean;
import com.kc.shiptransport.data.bean.WeekTaskBean;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.util.CalendarUtil;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/5/17  10:53
 * @desc ${TODD}
 */

public class PlanPresenter implements PlanContract.Presenter {
    private final Context context;
    private final PlanContract.View view;
    private final RemoteDataSource remoteDataSource;
    private final Gson gson;
    private int day_0 = 0;
    private int day_1 = 0;
    private int day_2 = 0;
    private int day_3 = 0;
    private int day_4 = 0;
    private int day_5 = 0;
    private int day_6 = 0;

    public PlanPresenter(Context context, PlanContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        remoteDataSource = new RemoteDataSource();
        gson = new Gson();
    }

    @Override
    public void subscribe() {
        // 1. 获取分包商, 周
        getTitle();

        // 2. 获取当前月
        getCurrentDate();

        // 3. 任务量
        getTaskVolume();

        // 4. 任务要求
        getTaskRequire();

        // 5. 本周任务分配 (先网络请求, 再DB)
        //getWeekTask();
    }

    @Override
    public void unsubscribe() {

    }

    /**
     * 获取标题数据
     */
    @Override
    public void getTitle() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                // 从数据库获取分包商
                List<Subcontractor> subcontractorList = DataSupport.findAll(Subcontractor.class);
                Calendar calendar = Calendar.getInstance();
                int week_of_year = calendar.get(Calendar.WEEK_OF_YEAR);
                e.onNext(subcontractorList.get(0).getSubcontractorName() + "-" + week_of_year + "周进场计划");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        view.showTitle(value);
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
     * 获取当前月数据
     */
    @Override
    public void getCurrentDate() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月");
                String date = sDateFormat.format(new java.util.Date());
                e.onNext(date);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

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

    @Override
    public void getTaskVolume() {

    }

    @Override
    public void getTaskRequire() {

    }

    @Override
    public void getTotalTaskVolume(Integer[] integers) {
        int total = 0;
        for (int i = 0; i < integers.length; i++) {
            total += integers[i];
        }
        view.showTotalTaskVolume(total);
    }

    /**
     * 从本地获取计划表
     */
    @Override
    public void getWeekTask() {
        Observable.create(new ObservableOnSubscribe<List<WeekTask>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WeekTask>> e) throws Exception {
                // 从数据库获取一周任务分配数据
                List<WeekTask> weekLists = DataSupport.findAll(WeekTask.class);
                e.onNext(weekLists);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WeekTask>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<WeekTask> value) {
                        // 获取一周日期数据
                        List<String> dates = CalendarUtil.getdateOfWeek("dd");
                        view.showWeekTask(dates, value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        // 统计每日计划量
                        getDayCount();
                    }
                });
    }

    @Override
    public void getDayCount() {
        Observable.create(new ObservableOnSubscribe<Integer[]>() {
            @Override
            public void subscribe(ObservableEmitter<Integer[]> e) throws Exception {
                reset();
                // 从数据库获取一周任务分配数据
                List<WeekTask> weekLists = DataSupport.findAll(WeekTask.class);
                for (WeekTask weekTask : weekLists) {
                    switch (Integer.valueOf(weekTask.getPosition()) % 7) {
                        case 0:
                            day_0 += Integer.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 1:
                            day_1 += Integer.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 2:
                            day_2 += Integer.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 3:
                            day_3 += Integer.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 4:
                            day_4 += Integer.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 5:
                            day_5 += Integer.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 6:
                            day_6 += Integer.valueOf(weekTask.getSandSupplyCount());
                            break;
                    }
                }
                Integer[] integers = new Integer[]{day_0, day_1, day_2, day_3, day_4, day_5, day_6};

                e.onNext(integers);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer[] value) {
                        // 计算总计划量
                        view.showDayCount(value);
                        getTotalTaskVolume(value);
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
     * 提交数据
     */
    @Override
    public void doCommit() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                // 获取weektask DB数据
                List<WeekTask> all = DataSupport.findAll(WeekTask.class);

                //                "SubmitDate":"2017-05-15",
                //                        "SubcontractorAccount":"yflf",
                //                        "RemoveItemIDS":"3,4",
                SubmitBean bean = new SubmitBean();
                bean.setSubmitDate(CalendarUtil.getCurrentDate("yyyy-MM-dd"));
                bean.setSubcontractorAccount(DataSupport.findAll(Subcontractor.class).get(0).getSubcontractorAccount());
                List<SubmitBean.ListBean> list = new ArrayList<>();

                bean.setList(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {

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
     * 网络请求
     * 保存数据到数据库
     *
     * @param SubcontractorAccount
     * @param StartDay
     * @param EndDay
     */
    @Override
    public void doRefresh(final String SubcontractorAccount, final String StartDay, final String EndDay) {
        view.showLoading(true);
        Observable.create(new ObservableOnSubscribe<List<WeekTaskBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WeekTaskBean>> e) throws Exception {

                String weekTaskInfo = remoteDataSource.getWeekTaskInfo(SubcontractorAccount, StartDay, EndDay);

                // 1. 解析数据
                List<WeekTaskBean> lists = gson.fromJson(weekTaskInfo, new TypeToken<List<WeekTaskBean>>() {
                }.getType());

                // 2. 初始化数据库
                DataSupport.deleteAll(WeekTask.class);

                // 3. 保存数据到数据库
                for (WeekTaskBean bean : lists) {
                    WeekTask weekTask = new WeekTask();
                    weekTask.setItemID(bean.getItemID());
                    weekTask.setSubcontractorAccount(bean.getSubcontractorAccount());
                    weekTask.setPlanDay(bean.getPlanDay());
                    weekTask.setShipAccount(bean.getShipAccount());
                    weekTask.setShipType(bean.getShipType());
                    weekTask.setShipName(bean.getShipName());
                    weekTask.setSandSupplyCount(bean.getSandSupplyCount());
                    //weekTask.setPosition(String.valueOf(dateToPosition(bean.getPlanDay(), bean.getItemID()))); // 设置position
                    weekTask.save();
                }

                // 4. 根据日期对数据进行分类
                List<List<WeekTask>> totalLists = new ArrayList<>();
                Set set = new HashSet();
                for (WeekTaskBean bean : lists) {
                    String planDay = bean.getPlanDay();
                    if (set.contains(planDay)) {

                    } else {
                        set.add(planDay);
                        totalLists.add(DataSupport.where("PlanDay = ?", planDay).find(WeekTask.class));
                    }
                }

                // 6. 更新position
                for (List<WeekTask> list : totalLists) {
                    for (int i = 1 ; i <= list.size() ; i++) {
                        // 更新数据
                        WeekTask weekTask = list.get(i-1);
                        weekTask.setPosition(String.valueOf(dateToPosition(weekTask.getPlanDay(), i)));
                        weekTask.updateAll("ItemID = ?", String.valueOf(weekTask.getItemID()));
                    }
                }

                // 根据任务选中的船, 设置船select为1



                // 通知presenter从DB获取数据
                e.onComplete();

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WeekTaskBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<WeekTaskBean> value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        getWeekTask();
                        view.showLoading(false);
                    }
                });
    }


    public void reset() {
        day_0 = 0;
        day_1 = 0;
        day_2 = 0;
        day_3 = 0;
        day_4 = 0;
        day_5 = 0;
        day_6 = 0;
    }

    /**
     * 把日期, itemID转换成list position
     *
     * @param date
     * @param itemID
     */
    public int dateToPosition(String date, int itemID) {
        long dataBetween = 0;
        try {
             dataBetween = CalendarUtil.getDataBetween(CalendarUtil.getCurrentDate("yyyy-MM-dd"), date);
            Log.d("==", "" + dataBetween);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) (dataBetween + 7 * itemID);
    }
}
