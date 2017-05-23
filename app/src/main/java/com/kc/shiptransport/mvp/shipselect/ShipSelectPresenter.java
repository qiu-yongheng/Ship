package com.kc.shiptransport.mvp.shipselect;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.data.bean.CommitResultBean;
import com.kc.shiptransport.data.bean.SubmitBean;
import com.kc.shiptransport.data.bean.WeekTaskBean;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.CommitShip;
import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.util.CalendarUtil;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
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
 * @time 2017/5/18  22:48
 * @desc ${TODD}
 */

public class ShipSelectPresenter implements ShipSelectContract.Presenter {
    private final Context context;
    private final ShipSelectContract.View view;
    private final Gson gson;
    private final RemoteDataSource remoteDataSource;

    public ShipSelectPresenter(Context context, ShipSelectContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        gson = new Gson();
        remoteDataSource = new RemoteDataSource();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    /**
     * 根据type获取数据
     *
     * @param type
     */
    @Override
    public void getShip(final String type) {
        Observable.create(new ObservableOnSubscribe<List<Ship>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Ship>> e) throws Exception {
                List<Ship> list = DataSupport.where("ShipType = ?", type).find(Ship.class);
                e.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Ship>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Ship> value) {
                        view.showShip(value);
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
     * 取消修改
     * 1. 根据type查询船舶数据, 全部设置 select = 0
     * 2. 根据当前日期, type查询计划数据, 全部设置为select = 1
     *
     * @param type
     */
    @Override
    public void doCancle(final String type, final String date) {
        Observable.create(new ObservableOnSubscribe<List<Ship>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Ship>> e) throws Exception {
                /* 1. 设置指定type类型的船, select = 0 */
                ContentValues values = new ContentValues();
                values.put("Selected", "0");
                DataSupport.updateAll(Ship.class, values, "ShipType = ?", type);

                /* 2. 根据当前日期, type查询计划数据, 全部设置为select = 1 */
                List<WeekTask> weekTasks = DataSupport.where("PlanDay = ? and ShipType = ?", date, type).find(WeekTask.class);
                ContentValues v = new ContentValues();
                v.put("Selected", "1");
                for (WeekTask weekTask : weekTasks) {
                    DataSupport.updateAll(Ship.class, v, "ShipAccount = ?", weekTask.getShipAccount());
                }

                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Ship>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Ship> value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        view.cancle();
                    }
                });
    }

    /**
     * 发送网络请求
     * 1. 判断新计划数据: select = 1, itemID = ""
     * 2. 判断取消的数据: select = 0, itemID != ""
     */
    @Override
    public void doCommit(final String type, final String date) {
        view.showLoading(true);
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                /* 1. 创建要提交的对象 */
                SubmitBean bean = new SubmitBean(); // 创建一个对象
                bean.setSubmitDate(CalendarUtil.getCurrentDate("yyyy-MM-dd")); // 提交时间
                bean.setSubcontractorAccount(DataSupport.findAll(Subcontractor.class).get(0).getSubcontractorAccount()); // 提交账号

                List<Integer> removeItemIDS = new ArrayList<>();

                /**
                 * 先查询weektask, 如果没有计划, 保存""
                 */
                List<WeekTask> weekTasks = DataSupport.where("PlanDay = ? and ShipType = ?", date, type).find(WeekTask.class);
                if (!weekTasks.isEmpty()) {
                    // 判断该任务对应的ship是否选中
                    for (WeekTask weekTask : weekTasks) {

                        if (!DataSupport.where("Selected = ? and ShipAccount = ?", "0", weekTask.getShipAccount()).find(Ship.class).isEmpty()) {
                            // 被取消的任务
                            removeItemIDS.add(weekTask.getItemID());
                        }
                    }

                    if (!removeItemIDS.isEmpty()) {
                        // 将list格式化为规定的格式
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < removeItemIDS.size(); i++) {
                            sb.append(removeItemIDS.get(i));
                            if (i != removeItemIDS.size() - 1) {
                                sb.append(",");
                            }
                        }

                        bean.setRemoveItemIDS(sb.toString());
                    } else {
                        bean.setRemoveItemIDS("");
                    }

                } else {
                    bean.setRemoveItemIDS("");
                }


                List<SubmitBean.ListBean> list = new ArrayList<>(); // 新增计划列表
                List<CommitShip> commitShips = DataSupport.where("PlanDay = ? and ShipType = ?", date, type).find(CommitShip.class);
                if (!commitShips.isEmpty()) {
                    for (CommitShip commitShip : commitShips) {
                             /* 有船舶选中, 创建对象, 添加到集合 */
                        SubmitBean.ListBean listBean = new SubmitBean.ListBean();

                        listBean.setItemID("");
                        listBean.setPlanDay(date);
                        listBean.setShipAccount(commitShip.getShipAccount());
                        listBean.setSandSupplyCount(commitShip.getMaxSandSupplyCount());

                        list.add(listBean);
                    }
                    bean.setList(list); // 计划列表
                } else {
                         /* 没有船舶选中, 上传空集合 */
                    bean.setList(list); // 计划列表
                }


                //                List<Ship> ships = DataSupport.where("Selected = ? and ShipType = ?", "1", type).find(Ship.class); // TODO 查询所有选中的船(类型筛选)
                //                List<Ship> removeShips = new ArrayList<Ship>();
                //                /* 判断当前是否有计划 */
                //                if (!weekTasks.isEmpty()) {
                //                    /* 有计划,  */
                //                    for (WeekTask weekTask : weekTasks) {
                //                        for (Ship ship : ships) {
                //                            if (ship.getShipAccount().equals(weekTask.getShipAccount())) {
                //                                // 从集合中删除
                //                                removeShips.add(ship);
                //                            }
                //                        }
                //                    }
                //                    ships.removeAll(removeShips); // TODO 删除已经计划的船
                //                }
                //
                //                 /* 判断是否有船舶选中 */
                //                if (!ships.isEmpty()) {
                //                    for (Ship ship : ships) {
                //                            /* 有船舶选中, 创建对象, 添加到集合 */
                //                        SubmitBean.ListBean listBean = new SubmitBean.ListBean();
                //
                //                        listBean.setItemID("");
                //                        listBean.setPlanDay(date);
                //                        listBean.setShipAccount(ship.getShipAccount());
                //                        listBean.setSandSupplyCount(ship.getMaxSandSupplyCount());
                //
                //                        list.add(listBean);
                //                    }
                //                    bean.setList(list); // 计划列表
                //                } else {
                //                        /* 没有船舶选中, 上传空集合 */
                //                    bean.setList(list); // 计划列表
                //                }

                /* 2. 把对象转换成json字符串 */
                String json = gson.toJson(bean);
                Log.d("==", json);


                /* 4. 提交数据后, 删除缓存 */
                int i = DataSupport.deleteAll(CommitShip.class, "PlanDay = ? and ShipType = ?", date, type);
                Log.d("==", "删除缓存: " + i);




                /* 3. 上传数据 */
                String result = remoteDataSource.commitWeekTask(json);


                e.onNext(result);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        CommitResultBean resultBean = gson.fromJson(value, CommitResultBean.class);
                        if (resultBean.getMessage().equals("1")) {
                            view.showCommitSuccess();
                            view.changeDailogInfo();
                        } else {
                            view.showError();
                            view.showLoading(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("==", e.toString());
                        // 服务器返回null报错, 暂时屏蔽
                        //view.showLoading(false);
                        //view.showError();
                        view.showCommitSuccess();
                        view.changeDailogInfo();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void doRefresh(final String SubcontractorAccount, final String StartDay, final String EndDay, final int jumpWeek) {
        Observable.create(new ObservableOnSubscribe<List<WeekTaskBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WeekTaskBean>> e) throws Exception {
                /* 1. 获取请求数据 */
                String weekTaskInfo = remoteDataSource.getWeekTaskInfo(SubcontractorAccount, StartDay, EndDay);

                /* 2. 解析数据成对象 */
                List<WeekTaskBean> lists = gson.fromJson(weekTaskInfo, new TypeToken<List<WeekTaskBean>>() {
                }.getType());

                /* 3. 初始化数据库 */
                DataSupport.deleteAll(WeekTask.class);

                /* 4. 保存数据到数据库 */
                for (WeekTaskBean bean : lists) {
                    WeekTask weekTask = new WeekTask();
                    weekTask.setItemID(bean.getItemID()); // 保存itemID
                    weekTask.setSubcontractorAccount(bean.getSubcontractorAccount()); // 保存账号名
                    weekTask.setPlanDay(bean.getPlanDay()); // 保存计划时间
                    weekTask.setShipAccount(bean.getShipAccount()); //船舶账号
                    weekTask.setShipType(bean.getShipType()); // 船舶类型
                    weekTask.setShipName(bean.getShipName()); // 船舶名字
                    weekTask.setSandSupplyCount(bean.getSandSupplyCount()); // 船舶最大运沙量
                    weekTask.save();
                }

                /* 5. 根据日期对数据进行分类 */
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

                /* 6. 更新position */
                for (List<WeekTask> list : totalLists) {
                    for (int i = 1; i <= list.size(); i++) {
                        // 更新数据
                        WeekTask weekTask = list.get(i - 1);
                        weekTask.setPosition(String.valueOf(dateToPosition(weekTask.getPlanDay(), i, jumpWeek)));
                        //                        weekTask.updateAll("ItemID = ?", String.valueOf(weekTask.getItemID()));
                        weekTask.save();
                    }
                }

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
                        view.showLoading(false);
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                        view.showSuccess();
                        view.navigateToPlanSet();
                    }
                });
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
