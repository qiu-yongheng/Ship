package com.kc.shiptransport.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.data.bean.AcceptanceBean;
import com.kc.shiptransport.data.bean.AppListBean;
import com.kc.shiptransport.data.bean.CommitResultBean;
import com.kc.shiptransport.data.bean.ConstructionBoatBean;
import com.kc.shiptransport.data.bean.LoginResult;
import com.kc.shiptransport.data.bean.PerfectBoatRecordBean;
import com.kc.shiptransport.data.bean.RecordListBean;
import com.kc.shiptransport.data.bean.SandSampleBean;
import com.kc.shiptransport.data.bean.ShipBean;
import com.kc.shiptransport.data.bean.SubcontractorBean;
import com.kc.shiptransport.data.bean.SubmitBean;
import com.kc.shiptransport.data.bean.TaskVolumeBean;
import com.kc.shiptransport.data.bean.VoyageInfoBean;
import com.kc.shiptransport.data.bean.WeekTaskBean;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.db.AppList;
import com.kc.shiptransport.db.CommitShip;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.PerfectBoatRecord;
import com.kc.shiptransport.db.RecordList;
import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.db.ScannerImage;
import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.TaskVolume;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.MyJSONObject;
import com.kc.shiptransport.util.SettingUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

import static org.litepal.crud.DataSupport.deleteAll;
import static org.litepal.crud.DataSupport.findAll;

/**
 * @author 邱永恒
 * @time 2017/5/31 21:05
 * @desc ${TODO}
 */

public class DataRepository implements DataSouceImpl {
    private Gson gson = new Gson();
    private RemoteDataSource mRemoteDataSource = new RemoteDataSource();
    private double day_0 = 0;
    private double day_1 = 0;
    private double day_2 = 0;
    private double day_3 = 0;
    private double day_4 = 0;
    private double day_5 = 0;
    private double day_6 = 0;


    @Override
    public void getSubcontractorInfo(String username) {
        // 1. 获取数据, 缓存到数据库
        String subcontractorInfo = mRemoteDataSource.getSubcontractorInfo(username);
        Log.d("info", "分包商json: " + subcontractorInfo);
        // 保存到数据库
        List<SubcontractorBean> ls = gson.fromJson(subcontractorInfo, new TypeToken<List<SubcontractorBean>>() {
        }.getType());
        if (ls != null && !ls.isEmpty()) {
            // 清空数据
            DataSupport.deleteAll(SubcontractorList.class);
            for (SubcontractorBean bean : ls) {
                SubcontractorList subcontractor = new SubcontractorList();
                subcontractor.setSubcontractorAccount(bean.getSubcontractorAccount());
                subcontractor.setSubcontractorName(bean.getSubcontractorName());
                subcontractor.save();
            }
        }
    }

    @Override
    public void getShipInfo(String username) {
        // 2. 获取数据, 缓存到数据库
        String shipInfo = mRemoteDataSource.getShipInfo(username);
        List<ShipBean> list = gson.fromJson(shipInfo, new TypeToken<List<ShipBean>>() {
        }.getType());
        if (list != null && !list.isEmpty()) {
            DataSupport.deleteAll(Ship.class);
            for (ShipBean listBean : list) {
                Ship ship = new Ship();
                ship.setItemID(listBean.getItemID());
                ship.setShipID(listBean.getShipID());
                ship.setShipAccount(listBean.getShipAccount());
                ship.setShipName(listBean.getShipName());
                ship.setShipType(listBean.getShipType());
                ship.setMaxSandSupplyCount(listBean.getMaxSandSupplyCount());
                ship.setCapacity(listBean.getCapacity());
                ship.setDeckGauge(listBean.getDeckGauge());
                ship.setSelected("0");
                ship.save();
            }
        }
    }

    @Override
    public Observable<String> getTitle(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                // 从数据库获取分包商
                List<Subcontractor> subcontractorList = findAll(Subcontractor.class);
                int weekOfYearNum = CalendarUtil.getWeekOfYearNum(jumpWeek);
                e.onNext(subcontractorList.get(0).getSubcontractorName() + "-" + weekOfYearNum + "周进场计划");
            }
        });
    }

    @Override
    public Observable<String> getCurrentMouthDate(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String date = CalendarUtil.getMonthOfYear(jumpWeek);
                e.onNext(date);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<WeekTask>> getWeekTask() {
        return Observable.create(new ObservableOnSubscribe<List<WeekTask>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WeekTask>> e) throws Exception {
                // 从数据库获取一周任务分配数据 (每次请求, 初始化表, 所以只有一周的数据)
                List<WeekTask> weekLists = findAll(WeekTask.class);
                Log.d("==", "计划数量: " + weekLists.size());
                e.onNext(weekLists);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Double[]> getDayCount() {
        return Observable.create(new ObservableOnSubscribe<Double[]>() {
            @Override
            public void subscribe(ObservableEmitter<Double[]> e) throws Exception {
                synchronized (DataRepository.class) {
                    reset();
                    // 从数据库获取一周任务分配数据
                    List<WeekTask> weekLists = findAll(WeekTask.class);
                    for (WeekTask weekTask : weekLists) {
                        switch (Integer.valueOf(weekTask.getPosition()) % 7) {
                            case 0:
                                day_0 += Double.valueOf(weekTask.getSandSupplyCount());
                                break;
                            case 1:
                                day_1 += Double.valueOf(weekTask.getSandSupplyCount());
                                break;
                            case 2:
                                day_2 += Double.valueOf(weekTask.getSandSupplyCount());
                                break;
                            case 3:
                                day_3 += Double.valueOf(weekTask.getSandSupplyCount());
                                break;
                            case 4:
                                day_4 += Double.valueOf(weekTask.getSandSupplyCount());
                                break;
                            case 5:
                                day_5 += Double.valueOf(weekTask.getSandSupplyCount());
                                break;
                            case 6:
                                day_6 += Double.valueOf(weekTask.getSandSupplyCount());
                                break;
                        }
                    }
                    Double[] integers = new Double[]{day_0, day_1, day_2, day_3, day_4, day_5, day_6};

                    e.onNext(integers);
                    e.onComplete();
                }
            }
        });
    }

    @Override
    public Observable<Double[]> getDayCount(final int type) {
        return Observable.create(new ObservableOnSubscribe<Double[]>() {
            @Override
            public void subscribe(ObservableEmitter<Double[]> e) throws Exception {
                reset();
                List<WeekTask> weekLists;
                if (type == SettingUtil.TYPE_SUPPLY || type == SettingUtil.TYPE_AMOUNT) {
                    weekLists = DataSupport.where("PreAcceptanceTime IS NOT NULL").find(WeekTask.class);
                } else {
                    // 从数据库获取一周任务分配数据
                    weekLists = findAll(WeekTask.class);
                }

                for (WeekTask weekTask : weekLists) {
                    switch (Integer.valueOf(weekTask.getPosition()) % 7) {
                        case 0:
                            day_0 += Double.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 1:
                            day_1 += Double.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 2:
                            day_2 += Double.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 3:
                            day_3 += Double.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 4:
                            day_4 += Double.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 5:
                            day_5 += Double.valueOf(weekTask.getSandSupplyCount());
                            break;
                        case 6:
                            day_6 += Double.valueOf(weekTask.getSandSupplyCount());
                            break;
                    }
                }
                Double[] integers = new Double[]{day_0, day_1, day_2, day_3, day_4, day_5, day_6};

                e.onNext(integers);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Boolean> doRefresh(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                Log.d("==", "第二");
                /* 获取分包商账号 */
                String subcontractorAccount = findAll(Subcontractor.class).get(0).getSubcontractorAccount();
                /* 开始时间 */
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
                /* 结束时间 */
                String endDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY, jumpWeek);

                Log.d("==", "请求日期: " + startDay + "-" + endDay);

                /* 1. 获取请求数据 */
                String weekTaskInfo = mRemoteDataSource.getWeekTaskInfo(subcontractorAccount, startDay, endDay);

                /* 2. 解析数据成对象 */
                List<WeekTaskBean> lists = gson.fromJson(weekTaskInfo, new TypeToken<List<WeekTaskBean>>() {
                }.getType());

                Log.d("==", "一周计划数量: " + lists.size());

                /* 3. 初始化数据库 */
                DataSupport.deleteAll(WeekTask.class);

                Log.d("==", "初始化数据库后: " + findAll(WeekTask.class).size());

                /* 4. 保存数据到数据库 */
                for (WeekTaskBean bean : lists) {
                    WeekTask weekTask = new WeekTask();
                    weekTask.setItemID(bean.getItemID()); // 保存itemID
                    weekTask.setSubcontractorAccount(bean.getSubcontractorAccount()); // 保存账号名
                    weekTask.setSubcontractorName(bean.getSubcontractorName()); // 用户名
                    weekTask.setPlanDay(bean.getPlanDay()); // 保存计划时间
                    weekTask.setShipAccount(bean.getShipAccount()); //船舶账号
                    weekTask.setShipType(bean.getShipType()); // 船舶类型
                    weekTask.setShipName(bean.getShipName()); // 船舶名字
                    weekTask.setSandSupplyCount(bean.getSandSupplyCount()); // 船舶最大运沙量
                    weekTask.setReceptionSandTime(bean.getReceptionSandTime()); // 验砂时间
                    weekTask.setPreAcceptanceTime(bean.getPreAcceptanceTime()); // 验收时间
                    weekTask.setTheAmountOfTime(bean.getTheAmountOfTime()); // 量方时间
                    weekTask.setSandSubcontractorPreAcceptanceEvaluationID(bean.getSandSubcontractorPreAcceptanceEvaluationID()); // 分包商评价ID
                    weekTask.setCapacity(bean.getCapacity()); // 舱容
                    weekTask.setDeckGauge(bean.getDeckGauge()); // 甲板方
                    weekTask.setDefaultCapacity(bean.getDefaultCapacity());
                    weekTask.setDefaultDeckGauge(bean.getDefaultDeckGauge());
                    weekTask.setIsPerfect(bean.getIsPerfect()); // 是否完善信息
                    weekTask.setPerfectBoatItemCount(bean.getPerfectBoatItemCount()); // 已填写字段数量
                    weekTask.setPerfectBoatRecordID(bean.getPerfectBoatRecordID()); // 船次信息完善条目ID
                    weekTask.save();
                }

                Log.d("==", "保存数据到数据库后: " + findAll(WeekTask.class).size());






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
                        weekTask.save();
                    }
                }


                // 从数据库获取一周任务分配数据
                List<WeekTask> weekLists = findAll(WeekTask.class);
                Log.d("==", "计划数量: " + weekLists.size());

                // 重置ship的选择状态
                ContentValues v = new ContentValues();
                v.put("Selected", "0");
                DataSupport.updateAll(Ship.class, v);

                Log.d("==", "重置后, 选择数量: " + DataSupport.where("Selected = ?", "1").find(Ship.class).size());


                // 根据计划, 设置ship select = 1
                ContentValues values = new ContentValues();
                values.put("Selected", "1");
                for (WeekTask weekTask : weekLists) {
                    DataSupport.updateAll(Ship.class, values, "ShipAccount = ?", weekTask.getShipAccount());
                }

                Log.d("==", "设置后, 选择数量: " + DataSupport.where("Selected = ?", "1").find(Ship.class).size());
                Log.d("==", "----PlanPresenter: 从数据库获取数据, 重置ship的选择状态----");


                // 通知presenter从DB获取数据
                e.onNext(true);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Boolean> doRefresh(final int jumpWeek, final String account) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                Log.d("==", "第二");
                /* 开始时间 */
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
                /* 结束时间 */
                String endDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY, jumpWeek);

                Log.d("==", "请求日期: " + startDay + "-" + endDay);

                /* 1. 获取请求数据 */
                String weekTaskInfo = mRemoteDataSource.getWeekTaskInfo(account, startDay, endDay);

                /* 2. 解析数据成对象 */
                List<WeekTaskBean> lists = gson.fromJson(weekTaskInfo, new TypeToken<List<WeekTaskBean>>() {
                }.getType());

                Log.d("==", "一周计划数量: " + lists.size());

                /* 3. 初始化数据库 */
                DataSupport.deleteAll(WeekTask.class);

                Log.d("==", "初始化数据库后: " + findAll(WeekTask.class).size());

                /* 4. 保存数据到数据库 */
                for (WeekTaskBean bean : lists) {
                    WeekTask weekTask = new WeekTask();
                    weekTask.setItemID(bean.getItemID()); // 保存itemID
                    weekTask.setSubcontractorAccount(bean.getSubcontractorAccount()); // 保存账号名
                    weekTask.setSubcontractorName(bean.getSubcontractorName()); // 用户名
                    weekTask.setPlanDay(bean.getPlanDay()); // 保存计划时间
                    weekTask.setShipAccount(bean.getShipAccount()); //船舶账号
                    weekTask.setShipType(bean.getShipType()); // 船舶类型
                    weekTask.setShipName(bean.getShipName()); // 船舶名字
                    weekTask.setSandSupplyCount(bean.getSandSupplyCount()); // 船舶最大运沙量
                    weekTask.setReceptionSandTime(bean.getReceptionSandTime()); // 验砂时间
                    weekTask.setPreAcceptanceTime(bean.getPreAcceptanceTime()); // 验收时间
                    weekTask.setTheAmountOfTime(bean.getTheAmountOfTime()); // 量方时间
                    weekTask.setSandSubcontractorPreAcceptanceEvaluationID(bean.getSandSubcontractorPreAcceptanceEvaluationID()); // 分包商评价ID
                    weekTask.setCapacity(bean.getCapacity()); // 舱容
                    weekTask.setDeckGauge(bean.getDeckGauge()); // 甲板方
                    weekTask.setDefaultCapacity(bean.getDefaultCapacity());
                    weekTask.setDefaultDeckGauge(bean.getDefaultDeckGauge());
                    weekTask.save();
                }

                Log.d("==", "保存数据到数据库后: " + findAll(WeekTask.class).size());






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
                        weekTask.save();
                    }
                }


                // 从数据库获取一周任务分配数据
                List<WeekTask> weekLists = findAll(WeekTask.class);
                Log.d("==", "计划数量: " + weekLists.size());

                // 重置ship的选择状态
                ContentValues v = new ContentValues();
                v.put("Selected", "0");
                DataSupport.updateAll(Ship.class, v);

                Log.d("==", "重置后, 选择数量: " + DataSupport.where("Selected = ?", "1").find(Ship.class).size());


                // 根据计划, 设置ship select = 1
                ContentValues values = new ContentValues();
                values.put("Selected", "1");
                for (WeekTask weekTask : weekLists) {
                    DataSupport.updateAll(Ship.class, values, "ShipAccount = ?", weekTask.getShipAccount());
                }

                Log.d("==", "设置后, 选择数量: " + DataSupport.where("Selected = ?", "1").find(Ship.class).size());
                Log.d("==", "----PlanPresenter: 从数据库获取数据, 重置ship的选择状态----");


                // 通知presenter从DB获取数据
                e.onNext(true);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<List<Ship>>> getShipCategory() {
        return Observable.create(new ObservableOnSubscribe<List<List<Ship>>>() {
            @Override
            public void subscribe(ObservableEmitter<List<List<Ship>>> e) throws Exception {
                List<List<Ship>> lists = new ArrayList<>();
                List<Ship> all = DataSupport.order("ShipType asc").find(Ship.class);
                // 根据type进行分类
                Set set = new HashSet();
                for (Ship ship : all) {
                    String type = ship.getShipType();
                    if (set.contains(type)) {

                    } else {
                        set.add(type);
                        lists.add(DataSupport.where("ShipType = ?", type).find(Ship.class));
                    }
                }

                e.onNext(lists);
            }
        });
    }

    /**
     * @param itemID
     * @param isCashe 是否从缓存获取数据, true: 优先从DB获取     false: 优先从网络获取
     * @return
     */
    @Override
    public Observable<Acceptance> getAcceptanceByItemID(final int itemID, final boolean isCashe) {
        return Observable.create(new ObservableOnSubscribe<Acceptance>() {
            @Override
            public void subscribe(ObservableEmitter<Acceptance> e) throws Exception {
                /* 判断本地是否有缓存 */
                List<Acceptance> acceptances = DataSupport.where("ItemID = ?", String.valueOf(itemID)).find(Acceptance.class);

                if (acceptances != null && !acceptances.isEmpty() && isCashe) {
                    e.onNext(acceptances.get(0));

                } else {
                    // 1. 发送网络请求
                    String data = mRemoteDataSource.getAcceptanceByItemID(itemID);
                    Log.d("==", data);

                    // 2. 解析成对象
                    List<AcceptanceBean> lists = gson.fromJson(data, new TypeToken<List<AcceptanceBean>>() {
                    }.getType());
                    AcceptanceBean accepBean = lists.get(0);

                    // 3. 保存到数据库 TODO 先删除item对应数据, 再插入
                    int i = DataSupport.deleteAll(Acceptance.class, "ItemID = ?", String.valueOf(itemID));
                    Log.d("==", "删除item: " + i);
                    Acceptance accep = new Acceptance();
                    /**
                     * ItemID : 396
                     * SubmitDate : 2017-05-24
                     * SubcontractorAccount : slgj
                     * SubcontractorName : 森菱国际
                     * PlanDay : 2017-05-25
                     * ShipAccount : ygzh0708
                     * ShipName : 粤广州货0708
                     * ShipType : B类
                     * DeadweightTon : 2962
                     * MaxTakeInWater : 0
                     * SandSupplyCount : 3000
                     * SystemDate : 2017-05-26
                     * Capacity : null
                     * DeckGauge : null
                     * ReceptionSandTime : null
                     * PassReceptionSandTime : null
                     * TotalCompleteRide : 0
                     * TotalCompleteSquare : 0
                     * AvgSquare : 0
                     * CurrentTide : 0
                     */
                    accep.setItemID(accepBean.getItemID());
                    accep.setSubmitDate(accepBean.getSubmitDate());
                    accep.setSubcontractorAccount(accepBean.getSubcontractorAccount());
                    accep.setSubcontractorName(accepBean.getSubcontractorName());
                    accep.setPlanDay(accepBean.getPlanDay());
                    accep.setShipAccount(accepBean.getShipAccount());
                    accep.setShipName(accepBean.getShipName());
                    accep.setShipType(accepBean.getShipType());
                    accep.setDeadweightTon(accepBean.getDeadweightTon());
                    accep.setMaxTakeInWater(accepBean.getMaxTakeInWater());
                    accep.setSandSupplyCount(accepBean.getSandSupplyCount());
                    accep.setSystemDate(accepBean.getSystemDate());
                    accep.setCapacity(accepBean.getCapacity());
                    accep.setDeckGauge(accepBean.getDeckGauge());
                    accep.setDeduction(accepBean.getDeduction());
                    accep.setReceptionSandTime(accepBean.getReceptionSandTime());
                    accep.setPassReceptionSandTime(accepBean.getPreAcceptanceTime());
                    accep.setTheAmountOfTime(accepBean.getTheAmountOfTime()); // 量方时间
                    accep.setMaterialIntegrity(accepBean.getMaterialIntegrity());
                    accep.setMaterialTimeliness(accepBean.getMaterialTimeliness());
                    accep.setTotalCompleteRide(accepBean.getTotalCompleteRide());
                    accep.setTotalCompleteSquare(accepBean.getTotalCompleteSquare());
                    accep.setAvgSquare(accepBean.getAvgSquare());
                    accep.setCurrentTide(accepBean.getCurrentTide());
                    accep.setShipItemNum(accepBean.getShipItemNum());
                    accep.setDefaultCapacity(accepBean.getDefaultCapacity());
                    accep.setDefaultDeckGauge(accepBean.getDefaultDeckGauge());
                    accep.setBatch(accepBean.getBatch());
                    accep.save();
                    // 4. 返回存到数据库的数据
                    e.onNext(accep);
                }

                e.onComplete();
            }
        });
    }

    /**
     * 提交验沙结果
     *
     * @param itemID
     * @param ReceptionSandTime
     * @param Batch
     * @return
     */
    @Override
    public Observable<Integer> updateForReceptionSandTime(final int itemID, final String ReceptionSandTime, final String Batch) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d("==", "第一");
                String result = mRemoteDataSource.UpdateForReceptionSandTime(itemID, ReceptionSandTime, Batch);
                Log.d("==", result);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(Integer.valueOf(bean.getMessage()));
                e.onComplete();
            }
        });
    }

    //    /**
    //     * 提交验收结果
    //     *
    //     * @param itemID
    //     * @param PassReceptionSandTime
    //     * @return
    //     */
    //    @Override
    //    public Observable<Integer> UpdateForPassReceptionSandTime(final int itemID, final String PassReceptionSandTime) {
    //        return Observable.create(new ObservableOnSubscribe<Integer>() {
    //            @Override
    //            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
    //                Log.d("==", "第一");
    //                String result = mRemoteDataSource.UpdateForPassReceptionSandTime(itemID, PassReceptionSandTime);
    //                Log.d("==", result);
    //
    //                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);
    //
    //                e.onNext(Integer.valueOf(bean.getMessage()));
    //                e.onComplete();
    //            }
    //        });
    //    }

    /**
     * 统计未验收船的数量
     *
     * @param type
     * @return
     */
    @Override
    public Observable<Integer> getStayNum(final int type) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                int num = 0;

                /** 根据类型计算没有做相关处理的船次 */
                if (type == SettingUtil.TYPE_RECORDEDSAND) { // 过砂记录
                    // 过砂记录, IsFinish = 0的个数
                    num = DataSupport.where("IsFinish = ?", "0").count(RecordList.class);

                } else if (type == SettingUtil.TYPE_VOYAGEINFO) { // 航次信息完善
                    // 航次信息完善, IsPerfect = 0的个数
                    num = DataSupport.where("IsPerfect = ?", "0").count(WeekTask.class);
                } else if (type == SettingUtil.TYPE_SAMPLE) { // 验砂取样

                } else {
                    // 1. 获取一周任务
                    List<WeekTask> weekTasks = DataSupport.findAll(WeekTask.class);

                    // 2. 统计未验收任务
                    if (weekTasks != null && !weekTasks.isEmpty()) {
                        for (WeekTask weektask : weekTasks) {
                            String time = null;
                            String time2 = null;
                            if (type == SettingUtil.TYPE_ACCEPT) { // 验收
                                time = weektask.getPreAcceptanceTime();
                                if (time == null || time.equals("")) {
                                    num++;
                                }
                            } else if (type == SettingUtil.TYPE_SUPPLY) { // 验砂
                                time = weektask.getPreAcceptanceTime();
                                time2 = weektask.getReceptionSandTime();
                                if ((time != null && !time.equals("")) && (time2 == null || time2.equals(""))) {
                                    num++;
                                }
                            } else if (type == SettingUtil.TYPE_AMOUNT) { // 量方
                                time = weektask.getPreAcceptanceTime();
                                time2 = weektask.getTheAmountOfTime();
                                if ((time != null && !time.equals("")) && (time2 == null || time2.equals(""))) {
                                    num++;
                                }
                            }
                        }
                    }
                }

                e.onNext(num);
                e.onComplete();
            }
        });
    }

    /**
     * 获取指定日期计划船舶数量
     *
     * @param date
     * @return
     */
    @Override
    public Observable<Integer> getShipCount(final String date) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                List<WeekTask> list = DataSupport.where("PlanDay = ?", date).find(WeekTask.class);
                e.onNext(list.size());
                e.onComplete();
            }
        });
    }

    /**
     * 获取指定日期计划量
     *
     * @param date
     * @return
     */
    @Override
    public Observable<Double> getPlanMeasure(final String date) {
        return Observable.create(new ObservableOnSubscribe<Double>() {
            @Override
            public void subscribe(ObservableEmitter<Double> e) throws Exception {
                double d = 0;
                List<WeekTask> list = DataSupport.where("PlanDay = ?", date).find(WeekTask.class);
                for (WeekTask weekTask : list) {
                    double sandSupplyCount = weekTask.getSandSupplyCount();
                    d += sandSupplyCount;
                }
                e.onNext(d);
                e.onComplete();
            }
        });
    }

    /**
     * 评价
     *
     * @param itemID                             评价ID
     * @param rbcomplete                         材料完整性
     * @param rbtimely                           材料及时性
     * @param currentDate                        时间
     * @param shipNum                            船次编号
     * @param subcontractorInterimApproachPlanID 任务ID
     * @return
     */
    @Override
    public Observable<Integer> InsertPreAcceptanceEvaluation(final int itemID, final int rbcomplete, final int rbtimely, final String currentDate, final String shipNum, final int subcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                /* 1. 创建对象, 封装要提交的数据 */
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ItemID", itemID == 0 ? "" : String.valueOf(itemID));
                jsonObject.put("MaterialIntegrity", String.valueOf(rbcomplete));
                jsonObject.put("MaterialTimeliness", String.valueOf(rbtimely));
                jsonObject.put("PreAcceptanceTime", currentDate);
                jsonObject.put("SubcontractorInterimApproachPlanID", subcontractorInterimApproachPlanID);
                jsonArray.put(jsonObject);

                /* 2. 转换成json */
                String json = jsonArray.toString();
                Log.d("==", "评价提交数据: " + json);

                /* 3. 发送网络请求 */
                String s = mRemoteDataSource.InsertPreAcceptanceEvaluation(json);
                Log.d("==", "评价提交结果: " + s);

                /* 4. 数据解析 */
                CommitResultBean bean = gson.fromJson(s, CommitResultBean.class);

                /* 5. 返回结果 */
                e.onNext(Integer.valueOf(bean.getMessage()));
                e.onComplete();
            }
        });
    }

    /**
     * 根据类型获取船舶列表
     *
     * @param type
     * @return
     */
    @Override
    public Observable<List<Ship>> getShipByType(final String type) {
        return Observable.create(new ObservableOnSubscribe<List<Ship>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Ship>> e) throws Exception {
                List<Ship> list = DataSupport.where("ShipType = ?", type).find(Ship.class);
                e.onNext(list);
            }
        });
    }

    /**
     * 取消修改
     * 1. 根据type查询船舶数据, 全部设置 select = 0
     * 2. 根据当前日期, type查询计划数据, 全部设置为select = 1
     *
     * @param type
     * @param date
     * @return
     */
    @Override
    public Observable<Boolean> doCancle(final String type, final String date) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
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

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 发送网络请求
     * 1. 判断新计划数据: select = 1, itemID = ""
     * 2. 判断取消的数据: select = 0, itemID != ""
     *
     * @param type
     * @param date
     * @return
     */
    @Override
    public Observable<String> doCommit(final String type, final String date) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                /* 1. 创建要提交的对象 */
                SubmitBean bean = new SubmitBean(); // 创建一个对象
                bean.setSubmitDate(CalendarUtil.getCurrentDate("yyyy-MM-dd")); // 提交时间
                bean.setSubcontractorAccount(findAll(Subcontractor.class).get(0).getSubcontractorAccount()); // 提交账号

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

                /* 2. 把对象转换成json字符串 */
                String json = gson.toJson(bean);
                Log.d("==", json);


                /* 4. 提交数据后, 删除缓存 */
                int i = DataSupport.deleteAll(CommitShip.class, "PlanDay = ? and ShipType = ?", date, type);
                Log.d("==", "删除缓存: " + i);




                /* 3. 上传数据 */
                String result = mRemoteDataSource.commitWeekTask(json);


                CommitResultBean resultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(String.valueOf(resultBean.getMessage()));
                e.onComplete();
            }
        });
    }

    /**
     * 获取分包商信息
     *
     * @param username 分包商账号名, 如果填null, 获取所有分包商列表
     * @return
     */
    @Override
    public Observable<Boolean> getSubcontractor(final String username) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                // 1. 获取数据, 缓存到数据库
                String subcontractorInfo = mRemoteDataSource.getSubcontractorInfo(username);
                Log.d("info", "分包商json: " + subcontractorInfo);
                // 保存到数据库
                List<SubcontractorBean> ls = gson.fromJson(subcontractorInfo, new TypeToken<List<SubcontractorBean>>() {
                }.getType());
                // 清空数据
                DataSupport.deleteAll(Subcontractor.class);
                if (ls != null && !ls.isEmpty()) {
                    for (SubcontractorBean bean : ls) {
                        Subcontractor subcontractor = new Subcontractor();
                        subcontractor.setSubcontractorAccount(bean.getSubcontractorAccount());
                        subcontractor.setSubcontractorName(bean.getSubcontractorName());
                        subcontractor.save();
                    }
                } else {
                    //e.onError(new RuntimeException("用户信息不存在!"));
                    Subcontractor subcontractor = new Subcontractor();
                    subcontractor.setSubcontractorAccount(username);
                    subcontractor.setSubcontractorName(username);
                    subcontractor.save();
                }

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 获取船舶信息
     *
     * @param username 分包商账号名, 如果填null, 获取所有船舶列表
     * @return
     */
    @Override
    public Observable<Boolean> getShip(final String username) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                // 2. 获取数据, 缓存到数据库
                String shipInfo = mRemoteDataSource.getShipInfo(username);
                Log.d("ship", shipInfo);
                List<ShipBean> list = gson.fromJson(shipInfo, new TypeToken<List<ShipBean>>() {
                }.getType());
                if (list != null && !list.isEmpty()) {
                    DataSupport.deleteAll(Ship.class);
                    for (ShipBean listBean : list) {
                        Ship ship = new Ship();
                        ship.setItemID(listBean.getItemID());
                        ship.setShipID(listBean.getShipID());
                        ship.setShipAccount(listBean.getShipAccount());
                        ship.setShipName(listBean.getShipName());
                        ship.setShipType(listBean.getShipType());
                        ship.setMaxSandSupplyCount(listBean.getMaxSandSupplyCount());
                        ship.setSelected("0");
                        ship.save();
                    }
                }

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Observable<Boolean> login(final String username, final String password) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String loginInfo = mRemoteDataSource.getLoginInfo(username, password);
                LoginResult loginResult = gson.fromJson(loginInfo, LoginResult.class);
                e.onNext(loginResult.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 获取分包商预计划量
     *
     * @return
     */
    @Override
    public Observable<Float> getTaskVolume(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<Float>() {
            @Override
            public void subscribe(ObservableEmitter<Float> e) throws Exception {
                /* 获取分包商账号 */
                String subcontractorAccount = findAll(Subcontractor.class).get(0).getSubcontractorAccount();
                /* 开始时间 */
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
                /* 结束时间 */
                String endDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY, jumpWeek);

                String result = mRemoteDataSource.PublicSubcontractorSandPlanList(subcontractorAccount, startDay, endDay);
                List<TaskVolumeBean> lists = gson.fromJson(result, new TypeToken<List<TaskVolumeBean>>() {
                }.getType());
                // 初始化表
                DataSupport.deleteAll(TaskVolume.class);
                float f = 0;
                for (TaskVolumeBean bean : lists) {
                    // 保存数据到数据库
                    TaskVolume taskPlanList = new TaskVolume();
                    taskPlanList.setItemID(bean.getItemID());
                    taskPlanList.setSubcontractorAccount(bean.getSubcontractorAccount());
                    taskPlanList.setSubcontractorName(bean.getSubcontractorName());
                    taskPlanList.setDate(bean.getDate());
                    taskPlanList.setBoatA(bean.getBoatA());
                    taskPlanList.setBoatB(bean.getBoatB());
                    taskPlanList.setBoatC(bean.getBoatC());
                    taskPlanList.setBoatD(bean.getBoatD());
                    taskPlanList.setAllBoatSum(bean.getAllBoatSum());
                    taskPlanList.save();
                    f += bean.getAllBoatSum();
                }

                e.onNext(f);
                e.onComplete();
            }
        });
    }

    /**
     * 根据账号获取可以显示的模块
     *
     * @param account
     * @return
     */
    @Override
    public Observable<Boolean> getAppList(final String account) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                // 发送网络请求
                String appList = mRemoteDataSource.getAppList(account);
                Log.d("==", appList);
                // 解析数据
                List<AppListBean> lists = gson.fromJson(appList, new TypeToken<List<AppListBean>>() {
                }.getType());
                // 保存数据到数据库
                DataSupport.deleteAll(AppList.class);
                for (AppListBean bean : lists) {
                    AppList list = new AppList();
                    list.setAppID(bean.getAppID());
                    list.setAppPID(bean.getAppPID());
                    list.setAppName(bean.getAppName());
                    list.setAppUrl(bean.getAppUrl());
                    list.setSortNum(bean.getSortNum());
                    list.save();
                }

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 每日需求
     *
     * @param jumpWeek
     * @return
     */
    @Override
    public Observable<Double[]> getDemandDayCount(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<Double[]>() {
            @Override
            public void subscribe(ObservableEmitter<Double[]> e) throws Exception {
                List<String> dates = CalendarUtil.getdateOfWeek("yyyy-MM-dd", jumpWeek);

                Double[] doubles = new Double[7];

                for (int i = 0; i < dates.size(); i++) {
                    List<TaskVolume> taskVolumes = DataSupport.where("Date like ?", dates.get(i) + "%").find(TaskVolume.class);
                    if (taskVolumes != null && !taskVolumes.isEmpty()) {
                        doubles[i] = taskVolumes.get(0).getAllBoatSum();
                    } else {
                        doubles[i] = 0.0;

                        // 保存到数据库
                        //                        TaskVolume taskVolume = new TaskVolume();
                        //                        taskVolume.setDate(dates.get(i));
                        //                        taskVolume.setBoatA(0);
                        //                        taskVolume.setBoatB(0);
                        //                        taskVolume.setBoatC(0);
                        //                        taskVolume.setBoatD(0);
                        //                        taskVolume.setAllBoatSum(0);
                        //                        taskVolume.save();
                    }
                }

                e.onNext(doubles);
                e.onComplete();
            }
        });
    }

    /**
     * 获取分包商名字
     *
     * @return
     */
    @Override
    public Observable<String> getSubcontractorName() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
                if (!all.isEmpty()) {
                    e.onNext(all.get(0).getSubcontractorName());
                } else {
                    e.onNext("");
                }
                e.onComplete();
            }
        });
    }

    /**
     * 更新量方数据
     *
     * @param itemID
     * @param TheAmountOfTime
     * @param Capacity
     * @param DeckGauge
     * @param Deduction
     * @return
     */
    @Override
    public Observable<Boolean> UpdateTheAmountOfSideData(final int itemID, final String TheAmountOfTime, final String Capacity, final String DeckGauge, final String Deduction) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.UpdateTheAmountOfSideData(itemID, TheAmountOfTime, Capacity, DeckGauge, Deduction);
                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);
                e.onNext(Integer.valueOf(bean.getMessage()) == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 信息完善
     *
     * @param bean
     * @return
     */
    @Override
    public Observable<Boolean> InsertPerfectBoatRecord(final VoyageInfoBean bean) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                //JSONArray jsonArray = new JSONArray();
                MyJSONObject jsonObject = new MyJSONObject();
                jsonObject.put(bean.key_ItemID, bean.getItemID());
                jsonObject.put(bean.key_SubcontractorInterimApproachPlanID, bean.getSubcontractorInterimApproachPlanID());
                jsonObject.put(bean.key_LoadingPlace, bean.getLoadingPlace());
                jsonObject.put(bean.key_LoadingDate, bean.getLoadingDate());
                jsonObject.put(bean.key_BaseNumber, bean.getBaseNumber());
                jsonObject.put(bean.key_SourceOfSource, bean.getSourceOfSource());
                jsonObject.put(bean.key_StartLoadingTime, bean.getStartLoadingTime());
                jsonObject.put(bean.key_EndLoadingTime, bean.getEndLoadingTime());
                jsonObject.put(bean.key_ArrivedAtTheDockTime, bean.getArrivedAtTheDockTime());
                jsonObject.put(bean.key_LeaveTheDockTime, bean.getLeaveTheDockTime());
                jsonObject.put(bean.key_ArrivaOfAnchorageTime, bean.getArrivaOfAnchorageTime());
                jsonObject.put(bean.key_ClearanceTime, bean.getClearanceTime());
                jsonObject.put(bean.key_MaterialClassification, bean.getMaterialClassification());
                jsonObject.put(bean.key_Creator, bean.getCreator());

                //jsonArray.put(object);


                // 解析成json
                String json = "[" + jsonObject.toString() + "]";
                Log.d("==", "信息完善请求json: " + json);


                // 发送网络请求
                String result = mRemoteDataSource.InsertPerfectBoatRecord(json);
                Log.d("==", "信息完善请求结果: " + result);
                CommitResultBean resultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(resultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 根据position获取对应的计划
     *
     * @param position
     * @return
     */
    @Override
    public Observable<WeekTask> getWeekTaskForPosition(final int position) {
        return Observable.create(new ObservableOnSubscribe<WeekTask>() {
            @Override
            public void subscribe(ObservableEmitter<WeekTask> e) throws Exception {
                List<WeekTask> tasks = DataSupport.where("position = ?", String.valueOf(position)).find(WeekTask.class);
                if (tasks.isEmpty()) {
                    e.onError(new Exception("数据获取失败"));
                } else {
                    e.onNext(tasks.get(0));
                }

                e.onComplete();
            }
        });
    }

    /**
     * 根据position获取对应的计划
     *
     * @param position
     * @return
     */
    @Override
    public Observable<SandSample> getSampleTaskForPosition(final int position) {
        return Observable.create(new ObservableOnSubscribe<SandSample>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<SandSample> e) throws Exception {
                List<SandSample> sandSamples = DataSupport.where("position = ?", String.valueOf(position)).find(SandSample.class);
                if (sandSamples.isEmpty()) {
                    e.onNext(null);
                } else {
                    e.onNext(sandSamples.get(0));
                }

                e.onComplete();
            }
        });
    }

    /**
     * 获取当前登录的分包商信息
     *
     * @return
     */
    @Override
    public Observable<Subcontractor> getCurrentSubcontractor() {
        return Observable.create(new ObservableOnSubscribe<Subcontractor>() {
            @Override
            public void subscribe(ObservableEmitter<Subcontractor> e) throws Exception {
                List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
                if (!all.isEmpty()) {
                    e.onNext(all.get(0));
                } else {
                    e.onError(new RuntimeException("获取分包商信息失败"));
                }
                e.onComplete();
            }
        });
    }

    /**
     * 获取施工船舶
     *
     * @return
     */
    @Override
    public Observable<Boolean> GetConstructionBoat() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.GetConstructionBoat();
                List<ConstructionBoatBean> list = gson.fromJson(result, new TypeToken<List<ConstructionBoatBean>>() {
                }.getType());

                // 保存到数据库
                for (ConstructionBoatBean boatBean : list) {
                    ConstructionBoat boat = new ConstructionBoat();
                    boat.setShipNum(boatBean.getShipNum());
                    boat.setShipName(boatBean.getShipName());
                    boat.save();
                }

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 删除未验收的数据
     * 对数据库数据进行排序
     *
     * @return
     */
    @Override
    public Observable<Boolean> getWeekTaskSort(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                // 1. 删除未验收的数据
                DataSupport.deleteAll(WeekTask.class, "PreAcceptanceTime IS NULL");

                dataSort(jumpWeek);

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 获取过砂记录
     *
     * @param jumpWeek
     * @return
     */
    @Override
    public Observable<Boolean> getOverSandRecordList(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                /* 获取分包商账号 */
                String subcontractorAccount = findAll(Subcontractor.class).get(0).getSubcontractorAccount();
                /* 开始时间 */
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
                /* 结束时间 */
                String endDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY, jumpWeek);

                Log.d("==", "请求日期: " + startDay + "-" + endDay);

                /* 1. 获取请求数据 */
                String recordList = mRemoteDataSource.GetOverSandRecordList(subcontractorAccount, startDay, endDay);

                /* 2. 解析数据成对象 */
                List<RecordListBean> lists = gson.fromJson(recordList, new TypeToken<List<RecordListBean>>() {
                }.getType());

                /* 3. 初始化数据库 */
                deleteAll(RecordList.class);

                /* 4. 保存数据到数据库 */
                for (RecordListBean bean : lists) {
                    RecordList record = new RecordList();
                    record.setItemID(bean.getItemID());
                    record.setSubcontractorAccount(bean.getSubcontractorAccount());
                    record.setSubcontractorName(bean.getSubcontractorName());
                    record.setPlanDay(bean.getPlanDay());
                    record.setShipAccount(bean.getShipAccount());
                    record.setShipName(bean.getShipName());
                    record.setShipType(bean.getShipType());
                    record.setSandSupplyCount(bean.getSandSupplyCount());
                    record.setCapacity(bean.getCapacity());
                    record.setDeckGauge(bean.getDeckGauge());
                    record.setReceptionSandTime(bean.getReceptionSandTime());
                    record.setPreAcceptanceTime(bean.getPreAcceptanceTime());
                    record.setShipItemNum(bean.getShipItemNum());
                    record.setIsFinish(bean.getIsFinish());
                    record.save();
                }

                /* 5. 对数据进行排序 */

                // 1. 创建一个二维集合存放分类好的数据
                List<List<RecordList>> totalLists = new ArrayList<>();

                // 2. 查询数据
                List<RecordList> recordLists = DataSupport.findAll(RecordList.class);

                // 3. 按照时间进行分类
                Set set = new HashSet();
                for (RecordList bean : recordLists) {
                    String planDay = bean.getPlanDay();
                    if (set.contains(planDay)) {

                    } else {
                        set.add(planDay);
                        totalLists.add(DataSupport.where("PlanDay = ?", planDay).find(RecordList.class));
                    }
                }

                // 4. 更新position
                for (List<RecordList> list : totalLists) {
                    for (int i = 1; i <= list.size(); i++) {
                        // 更新数据
                        RecordList record = list.get(i - 1);
                        record.setPosition(String.valueOf(dateToPosition(record.getPlanDay(), i, jumpWeek)));
                        record.save();
                    }
                }

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 获取验砂取样信息
     * 获取分包商对应进场计划
     * 验收后才显示
     *
     * @param jumpWeek
     * @param account
     * @return
     */
    @Override
    public Observable<Boolean> getSandSamplingList(final int jumpWeek, final String account) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                /* 开始时间 */
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
                /* 结束时间 */
                String endDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY, jumpWeek);

                Log.d("==", "请求日期: " + startDay + "-" + endDay);

                // 发送网络请求
                String result = mRemoteDataSource.GetSandSamplingList(account, startDay, endDay, endDay);

                // 解析数据
                List<SandSampleBean> lists = gson.fromJson(result, new TypeToken<List<SandSampleBean>>() {
                }.getType());

                /* 3. 初始化数据库 */
                deleteAll(SandSample.class);

                /* 4. 保存数据到数据库 */
                for (SandSampleBean bean : lists) {
                    SandSample sandSample = new SandSample();
                    sandSample.setItemID(bean.getItemID());
                    sandSample.setSubcontractorAccount(bean.getSubcontractorAccount());
                    sandSample.setSubcontractorName(bean.getSubcontractorName());
                    sandSample.setPlanDay(bean.getPlanDay());
                    sandSample.setShipAccount(bean.getShipAccount());
                    sandSample.setShipName(bean.getShipName());
                    sandSample.setShipType(bean.getShipType());
                    sandSample.setSandSupplyCount(bean.getSandSupplyCount());
                    sandSample.setCapacity(bean.getCapacity());
                    sandSample.setDeckGauge(bean.getDeckGauge());
                    sandSample.setReceptionSandTime(bean.getReceptionSandTime());
                    sandSample.setPreAcceptanceTime(bean.getPreAcceptanceTime());
                    sandSample.setShipItemNum(bean.getShipItemNum());
                    sandSample.save();
                }

                /* 5. 对数据进行排序 */

                // 1. 创建一个二维集合存放分类好的数据
                List<List<SandSample>> totalLists = new ArrayList<>();

                // 2. 查询数据
                List<SandSample> recordLists = DataSupport.findAll(SandSample.class);

                // 3. 按照时间进行分类
                Set set = new HashSet();
                for (SandSample bean : recordLists) {
                    String planDay = bean.getPlanDay();
                    if (set.contains(planDay)) {

                    } else {
                        set.add(planDay);
                        totalLists.add(DataSupport.where("PlanDay = ?", planDay).find(SandSample.class));
                    }
                }

                // 4. 更新position
                for (List<SandSample> list : totalLists) {
                    for (int i = 1; i <= list.size(); i++) {
                        // 更新数据
                        SandSample record = list.get(i - 1);
                        record.setPosition(String.valueOf(dateToPosition(record.getPlanDay(), i, jumpWeek)));
                        record.save();
                    }
                }

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 1.17获取对应的航次完善信息明细
     *
     * @param weekTask
     * @param isNetwork
     * @return
     */
    @Override
    public Observable<PerfectBoatRecord> getPerfectBoatRecordByItemID(final WeekTask weekTask, final boolean isNetwork) {
        return Observable.create(new ObservableOnSubscribe<PerfectBoatRecord>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<PerfectBoatRecord> e) throws Exception {
                /** 判断本地是否有缓存 */
                List<PerfectBoatRecord> perfectBoatRecords = DataSupport.where("SubcontractorInterimApproachPlanID = ?", String.valueOf(weekTask.getItemID())).find(PerfectBoatRecord.class);
                if (!perfectBoatRecords.isEmpty() && isNetwork) {
                    // 有缓存, 且已经提交过数据

                    // 更新本地数据完善标识
                    perfectBoatRecords.get(0).setIsPerfect(weekTask.getIsPerfect());
                    // 更新条目ID
                    perfectBoatRecords.get(0).setItemID(weekTask.getPerfectBoatRecordID());
                    perfectBoatRecords.get(0).save();
                    e.onNext(perfectBoatRecords.get(0));
                } else if (perfectBoatRecords.isEmpty() && !isNetwork) {
                    // 没有缓存, 没有提交过数据
                    PerfectBoatRecord perfectBoatRecord = new PerfectBoatRecord();
                    perfectBoatRecord.setSubcontractorInterimApproachPlanID(weekTask.getItemID()); // 进场ID
                    perfectBoatRecord.setCreator(weekTask.getSubcontractorAccount()); // 登录账号
                    perfectBoatRecord.save();
                    e.onNext(perfectBoatRecord);
                } else if (perfectBoatRecords.isEmpty() && isNetwork) {
                    // 没有缓存, 且已经提交过数据

                    // 1. 发送网络请求
                    String result = mRemoteDataSource.GetPerfectBoatRecordByItemID(String.valueOf(weekTask.getPerfectBoatRecordID()));
                    // 2. 解析数据
                    List<PerfectBoatRecordBean> lists = gson.fromJson(result, new TypeToken<List<PerfectBoatRecordBean>>() {
                    }.getType());
                    PerfectBoatRecordBean bean = lists.get(0);
                    // 3. 删除数据库中SubcontractorInterimApproachPlanID对应的数据
                    DataSupport.deleteAll(PerfectBoatRecord.class, "ItemID = ?", String.valueOf(bean.getItemID()));
                    // 4. 保存数据到数据库
                    PerfectBoatRecord perfectBoatRecord = new PerfectBoatRecord();
                    perfectBoatRecord.setItemID(bean.getItemID());
                    perfectBoatRecord.setSubcontractorInterimApproachPlanID(bean.getSubcontractorInterimApproachPlanID());
                    perfectBoatRecord.setLoadingPlace(bean.getLoadingPlace());
                    perfectBoatRecord.setLoadingDate(bean.getLoadingDate());
                    perfectBoatRecord.setBaseNumber(bean.getBaseNumber());
                    perfectBoatRecord.setSourceOfSource(bean.getSourceOfSource());
                    perfectBoatRecord.setStartLoadingTime(bean.getStartLoadingTime());
                    perfectBoatRecord.setEndLoadingTime(bean.getEndLoadingTime());
                    perfectBoatRecord.setArrivedAtTheDockTime(bean.getArrivedAtTheDockTime());
                    perfectBoatRecord.setLeaveTheDockTime(bean.getLeaveTheDockTime());
                    perfectBoatRecord.setArrivaOfAnchorageTime(bean.getArrivaOfAnchorageTime());
                    perfectBoatRecord.setClearanceTime(bean.getClearanceTime());
                    perfectBoatRecord.setMaterialClassification(bean.getMaterialClassification());
                    perfectBoatRecord.setCreator(bean.getCreator());
                    perfectBoatRecord.setSystemDate(bean.getSystemDate());
                    perfectBoatRecord.setPerfectBoatItemCount(bean.getPerfectBoatItemCount());
                    perfectBoatRecord.setIsPerfect(bean.getIsPerfect());
                    perfectBoatRecord.save();

                    e.onNext(perfectBoatRecord);
                }

                e.onComplete();
            }
        });
    }

    /**
     * 根据itemID获取扫描图片数据
     * @param weekTask
     * @return
     */
    @Override
    public Observable<ScannerImage> getScannerImageByItemID(final WeekTask weekTask) {
        return Observable.create(new ObservableOnSubscribe<ScannerImage>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ScannerImage> e) throws Exception {
                /*
                * 一. 未完善:
                *   1. 查询本地缓存
                *       # 有缓存, 直接返回
                *       # 没有缓存
                *           * 在数据库创建一条新的缓存
                * 二. 已完善:
                *   1. 全部从网络获取图片显示
                * */

                // 1. 查询本地缓存
                List<ScannerImage> images = DataSupport.where("ItemID = ?", String.valueOf(weekTask.getItemID())).find(ScannerImage.class);

                // 2. 判断缓存是否为空
                if (!images.isEmpty()) {
                    // 有缓存
                    e.onNext(images.get(0));
                } else {
                    // 没有缓存
                    ScannerImage scannerImage = new ScannerImage();
                    scannerImage.setItemID(weekTask.getItemID());
                    scannerImage.save();
                    e.onNext(scannerImage);
                }

                e.onComplete();
            }
        });
    }

    /**
     * 压缩图片
     * @param file
     * @return
     */
    @Override
    public Flowable<File> compressWithRx(final Context context, File file) {
        return Flowable.just(file)
                .observeOn(Schedulers.io())
                .map(new Function<File, File>() {
                    @Override public File apply(@NonNull File file) throws Exception {
                        return Luban.with(context).load(file).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     *
     */
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) (dataBetween + 7 * itemID);
    }

    /**
     * 对数据进行重新排序
     *
     * @param jumpWeek
     */
    private void dataSort(int jumpWeek) {
        // 1. 创建一个二维集合存放分类好的数据
        List<List<WeekTask>> totalLists = new ArrayList<>();

        // 2. 查询数据
        List<WeekTask> weekTasks = DataSupport.findAll(WeekTask.class);

        // 3. 按照时间进行分类
        Set set = new HashSet();
        for (WeekTask bean : weekTasks) {
            String planDay = bean.getPlanDay();
            if (set.contains(planDay)) {

            } else {
                set.add(planDay);
                totalLists.add(DataSupport.where("PlanDay = ?", planDay).find(WeekTask.class));
            }
        }

        // 4. 更新position
        for (List<WeekTask> list : totalLists) {
            for (int i = 1; i <= list.size(); i++) {
                // 更新数据
                WeekTask weekTask = list.get(i - 1);
                weekTask.setPosition(String.valueOf(dateToPosition(weekTask.getPlanDay(), i, jumpWeek)));
                weekTask.save();
            }
        }
    }
}

