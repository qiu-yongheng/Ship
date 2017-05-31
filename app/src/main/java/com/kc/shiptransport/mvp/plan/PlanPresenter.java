package com.kc.shiptransport.mvp.plan;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kc.shiptransport.data.bean.WeekTaskBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.util.CalendarUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

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
    private final DataRepository mDataRepository;

    public PlanPresenter(Context context, PlanContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        remoteDataSource = new RemoteDataSource();
        mDataRepository = new DataRepository();
        gson = new Gson();
    }

    @Override
    public void start(int jumpWeek) {
        // 1. 获取分包商, 周
        getTitle(jumpWeek);

        // 2. 获取当前月
        getCurrentDate(jumpWeek);
    }

    @Override
    public void subscribe() {
        // 1. 获取分包商, 周
        //getTitle();

        // 2. 获取当前月
        //getCurrentDate();

        // TODO 3. 任务量
        getTaskVolume();

        // TODO 4. 任务要求
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
    public void getTitle(final int jumpWeek) {
        //        Observable.create(new ObservableOnSubscribe<String>() {
        //            @Override
        //            public void subscribe(ObservableEmitter<String> e) throws Exception {
        //                // 从数据库获取分包商
        //                List<Subcontractor> subcontractorList = DataSupport.findAll(Subcontractor.class);
        //                int weekOfYearNum = CalendarUtil.getWeekOfYearNum(jumpWeek);
        //                e.onNext(subcontractorList.get(0).getSubcontractorName() + "-" + weekOfYearNum + "周进场计划");
        //            }
        //        })
        mDataRepository
                .getTitle(jumpWeek)
                .subscribeOn(Schedulers.io())
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
    public void getCurrentDate(final int jumpWeek) {
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                String date = CalendarUtil.getMonthOfYear(jumpWeek);
//                e.onNext(date);
//            }
//        })
        mDataRepository
                .getCurrentMouthDate(jumpWeek)
                .subscribeOn(Schedulers.io())
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

    /**
     * 获取任务量
     */
    @Override
    public void getTaskVolume() {

    }

    /**
     * 获取任务要求
     */
    @Override
    public void getTaskRequire() {

    }

    /**
     * 计算总任务量
     *
     * @param integers
     */
    @Override
    public void getTotalTaskVolume(Integer[] integers) {
        int total = 0;
        for (Integer integer : integers) {
            total += integer;
        }
        view.showTotalTaskVolume(total);
    }

    /**
     * 从本地数据库获取计划表
     */
    @Override
    public void getWeekTask(final int jumpWeek) {
        //        Log.d("==", "----PlanPresenter: 从数据库获取数据, 重置ship的选择状态----");
//        Observable.create(new ObservableOnSubscribe<List<WeekTask>>() {
//            @Override
//            public void subscribe(ObservableEmitter<List<WeekTask>> e) throws Exception {
//                // 从数据库获取一周任务分配数据 (每次请求, 初始化表, 所以只有一周的数据)
//                List<WeekTask> weekLists = DataSupport.findAll(WeekTask.class);
//                Log.d("==", "计划数量: " + weekLists.size());
//                e.onNext(weekLists);
//                //
//                //                // 重置ship的选择状态
//                //                ContentValues v = new ContentValues();
//                //                v.put("Selected", "0");
//                //                DataSupport.updateAll(Ship.class, v);
//                //
//                //                Log.d("==", "重置后, 选择数量: " + DataSupport.where("Selected = ?", "1").find(Ship.class).size());
//                //
//                //
//                //                // 根据计划, 设置ship select = 1
//                //                ContentValues values = new ContentValues();
//                //                values.put("Selected", "1");
//                //                for (WeekTask weekTask : weekLists) {
//                //                    DataSupport.updateAll(Ship.class, values, "ShipAccount = ?", weekTask.getShipAccount());
//                //                }
//                //
//                //                Log.d("==", "设置后, 选择数量: " + DataSupport.where("Selected = ?", "1").find(Ship.class).size());
//                //                Log.d("==", "----PlanPresenter: 从数据库获取数据, 重置ship的选择状态----");
//                // 完成
//                e.onComplete();
//            }
//        })
        mDataRepository
                .getWeekTask()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WeekTask>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

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

                    }

                    @Override
                    public void onComplete() {
                        // 统计每日计划量
                        getDayCount();
                    }
                });
    }

    /**
     * 计算每天任务总量
     */
    @Override
    public void getDayCount() {
//        Observable.create(new ObservableOnSubscribe<Integer[]>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer[]> e) throws Exception {
//                reset();
//                // 从数据库获取一周任务分配数据
//                List<WeekTask> weekLists = DataSupport.findAll(WeekTask.class);
//                for (WeekTask weekTask : weekLists) {
//                    switch (Integer.valueOf(weekTask.getPosition()) % 7) {
//                        case 0:
//                            day_0 += Integer.valueOf(weekTask.getSandSupplyCount());
//                            break;
//                        case 1:
//                            day_1 += Integer.valueOf(weekTask.getSandSupplyCount());
//                            break;
//                        case 2:
//                            day_2 += Integer.valueOf(weekTask.getSandSupplyCount());
//                            break;
//                        case 3:
//                            day_3 += Integer.valueOf(weekTask.getSandSupplyCount());
//                            break;
//                        case 4:
//                            day_4 += Integer.valueOf(weekTask.getSandSupplyCount());
//                            break;
//                        case 5:
//                            day_5 += Integer.valueOf(weekTask.getSandSupplyCount());
//                            break;
//                        case 6:
//                            day_6 += Integer.valueOf(weekTask.getSandSupplyCount());
//                            break;
//                    }
//                }
//                Integer[] integers = new Integer[]{day_0, day_1, day_2, day_3, day_4, day_5, day_6};
//
//                e.onNext(integers);
//                e.onComplete();
//            }
//        })
        mDataRepository
                .getDayCount()
                .subscribeOn(Schedulers.io())
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
    public void doRefresh(final String SubcontractorAccount, final String StartDay, final String EndDay, final int jumpWeek) {
        view.showLoading(true);
//        Observable.create(new ObservableOnSubscribe<List<WeekTaskBean>>() {
//            @Override
//            public void subscribe(ObservableEmitter<List<WeekTaskBean>> e) throws Exception {
//                Log.d("==", "请求日期: " + StartDay + "-" + EndDay);
//                /* 1. 获取请求数据 */
//                String weekTaskInfo = remoteDataSource.getWeekTaskInfo(SubcontractorAccount, StartDay, EndDay);
//
//                /* 2. 解析数据成对象 */
//                List<WeekTaskBean> lists = gson.fromJson(weekTaskInfo, new TypeToken<List<WeekTaskBean>>() {
//                }.getType());
//
//                /* 3. 初始化数据库 */
//                DataSupport.deleteAll(WeekTask.class);
//
//                /* 4. 保存数据到数据库 */
//                for (WeekTaskBean bean : lists) {
//                    WeekTask weekTask = new WeekTask();
//                    weekTask.setItemID(bean.getItemID()); // 保存itemID
//                    weekTask.setSubcontractorAccount(bean.getSubcontractorAccount()); // 保存账号名
//                    weekTask.setPlanDay(bean.getPlanDay()); // 保存计划时间
//                    weekTask.setShipAccount(bean.getShipAccount()); //船舶账号
//                    weekTask.setShipType(bean.getShipType()); // 船舶类型
//                    weekTask.setShipName(bean.getShipName()); // 船舶名字
//                    weekTask.setSandSupplyCount(bean.getSandSupplyCount()); // 船舶最大运沙量
//                    weekTask.save();
//                }
//
//                /* 5. 根据日期对数据进行分类 */
//                List<List<WeekTask>> totalLists = new ArrayList<>();
//                Set set = new HashSet();
//                for (WeekTaskBean bean : lists) {
//                    String planDay = bean.getPlanDay();
//                    if (set.contains(planDay)) {
//
//                    } else {
//                        set.add(planDay);
//                        totalLists.add(DataSupport.where("PlanDay = ?", planDay).find(WeekTask.class));
//                    }
//                }
//
//                /* 6. 更新position */
//                for (List<WeekTask> list : totalLists) {
//                    for (int i = 1; i <= list.size(); i++) {
//                        // 更新数据
//                        WeekTask weekTask = list.get(i - 1);
//                        weekTask.setPosition(String.valueOf(dateToPosition(weekTask.getPlanDay(), i, jumpWeek)));
//                        //                        weekTask.updateAll("ItemID = ?", String.valueOf(weekTask.getItemID()));
//                        weekTask.save();
//                    }
//                }
//
//
//                // 从数据库获取一周任务分配数据
//                List<WeekTask> weekLists = DataSupport.findAll(WeekTask.class);
//                Log.d("==", "计划数量: " + weekLists.size());
//
//                // 重置ship的选择状态
//                ContentValues v = new ContentValues();
//                v.put("Selected", "0");
//                DataSupport.updateAll(Ship.class, v);
//
//                Log.d("==", "重置后, 选择数量: " + DataSupport.where("Selected = ?", "1").find(Ship.class).size());
//
//
//                // 根据计划, 设置ship select = 1
//                ContentValues values = new ContentValues();
//                values.put("Selected", "1");
//                for (WeekTask weekTask : weekLists) {
//                    DataSupport.updateAll(Ship.class, values, "ShipAccount = ?", weekTask.getShipAccount());
//                }
//
//                Log.d("==", "设置后, 选择数量: " + DataSupport.where("Selected = ?", "1").find(Ship.class).size());
//                Log.d("==", "----PlanPresenter: 从数据库获取数据, 重置ship的选择状态----");
//
//                // 通知presenter从DB获取数据
//                e.onComplete();
//
//            }
//        })
        mDataRepository
                .doRefresh(SubcontractorAccount, StartDay, EndDay, jumpWeek)
                .subscribeOn(Schedulers.io())
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
                        getWeekTask(jumpWeek);
                    }
                });
    }


    private void reset() {
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
    private int dateToPosition(String date, int itemID, int jumpWeek) {
        long dataBetween = 0;
        try {
            // 获取时间差
            dataBetween = CalendarUtil.getDataBetween(CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek), date);
            Log.d("==", "" + dataBetween);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) (dataBetween + 7 * itemID);
    }
}
