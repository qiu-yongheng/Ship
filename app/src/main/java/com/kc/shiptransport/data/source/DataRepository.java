package com.kc.shiptransport.data.source;

import android.content.ContentValues;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.data.bean.AcceptanceBean;
import com.kc.shiptransport.data.bean.AppListBean;
import com.kc.shiptransport.data.bean.CommitResultBean;
import com.kc.shiptransport.data.bean.LoginResult;
import com.kc.shiptransport.data.bean.ShipBean;
import com.kc.shiptransport.data.bean.SubcontractorBean;
import com.kc.shiptransport.data.bean.SubmitBean;
import com.kc.shiptransport.data.bean.TaskVolumeBean;
import com.kc.shiptransport.data.bean.WeekTaskBean;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.db.AppList;
import com.kc.shiptransport.db.CommitShip;
import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.TaskVolume;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;

import org.json.JSONArray;
import org.json.JSONObject;
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
            DataSupport.deleteAll(Subcontractor.class);
            for (SubcontractorBean bean : ls) {
                Subcontractor subcontractor = new Subcontractor();
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
                    weekTask.setPlanDay(bean.getPlanDay()); // 保存计划时间
                    weekTask.setShipAccount(bean.getShipAccount()); //船舶账号
                    weekTask.setShipType(bean.getShipType()); // 船舶类型
                    weekTask.setShipName(bean.getShipName()); // 船舶名字
                    weekTask.setSandSupplyCount(bean.getSandSupplyCount()); // 船舶最大运沙量
                    weekTask.setReceptionSandTime(bean.getReceptionSandTime()); // 验收时间
                    weekTask.setPassReceptionSandTime(bean.getPassReceptionSandTime()); // 验砂时间
                    weekTask.setSandSubcontractorPreAcceptanceEvaluationID(bean.getSandSubcontractorPreAcceptanceEvaluationID()); // 分包商评价ID
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
     *
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
                    accep.setReceptionSandTime(accepBean.getReceptionSandTime());
                    accep.setPassReceptionSandTime(accepBean.getPassReceptionSandTime());
                    accep.setTotalCompleteRide(accepBean.getTotalCompleteRide());
                    accep.setTotalCompleteSquare(accepBean.getTotalCompleteSquare());
                    accep.setAvgSquare(accepBean.getAvgSquare());
                    accep.setCurrentTide(accepBean.getCurrentTide());
                    accep.setShipItemNum(accepBean.getShipItemNum());
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
     * @param Capacity
     * @param DeckGauge
     * @return
     */
    @Override
    public Observable<Integer> updateForReceptionSandTime(final int itemID, final String ReceptionSandTime, final String Capacity, final String DeckGauge) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d("==", "第一");
                String result = mRemoteDataSource.UpdateForReceptionSandTime(itemID, ReceptionSandTime, Capacity, DeckGauge);
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
    public Observable<Integer> getStayNum(final String type) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                int num = 0;
                // 1. 获取一周任务
                List<WeekTask> weekTasks = findAll(WeekTask.class);

                // 2. 统计未验收任务
                if (weekTasks != null && !weekTasks.isEmpty()) {
                    for (WeekTask weektask : weekTasks) {
                        String time = null;
                        if (type.equals(SettingUtil.ACCEPTANCE)) {
                            time = weektask.getPassReceptionSandTime();
                        } else if (type.equals(SettingUtil.SUPPLY)) {
                            time = weektask.getReceptionSandTime();
                        }

                        if (time == null || time.equals("")) {
                            num++;
                        }
                    }
                }

                e.onNext(num);
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
                    String sandSupplyCount = weekTask.getSandSupplyCount();
                    d += Double.valueOf(sandSupplyCount);
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
                jsonObject.put("PassReceptionSandTime", currentDate);
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

                e.onNext(resultBean.getMessage());
                e.onComplete();
            }
        });
    }

    /**
     * 获取分包商信息
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
                if (ls != null && !ls.isEmpty()) {
                    // 清空数据
                    DataSupport.deleteAll(Subcontractor.class);
                    for (SubcontractorBean bean : ls) {
                        Subcontractor subcontractor = new Subcontractor();
                        subcontractor.setSubcontractorAccount(bean.getSubcontractorAccount());
                        subcontractor.setSubcontractorName(bean.getSubcontractorName());
                        subcontractor.save();
                    }
                }

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 获取船舶信息
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
                    list.save();
                }

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 每日需求
     * @return
     */
    @Override
    public Observable<Float[]> getDemandDayCount() {
        return Observable.create(new ObservableOnSubscribe<Float[]>() {
            @Override
            public void subscribe(ObservableEmitter<Float[]> e) throws Exception {
                List<TaskVolume> all = DataSupport.order("Date asc").find(TaskVolume.class);

            }
        });
    }

    /**
     * 获取分包商名字
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

