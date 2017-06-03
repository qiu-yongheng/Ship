package com.kc.shiptransport.data.source;

import android.content.ContentValues;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.data.bean.AcceptanceBean;
import com.kc.shiptransport.data.bean.CommitResultBean;
import com.kc.shiptransport.data.bean.ShipBean;
import com.kc.shiptransport.data.bean.SubcontractorBean;
import com.kc.shiptransport.data.bean.WeekTaskBean;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.Acceptance;
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

/**
 * @author 邱永恒
 * @time 2017/5/31 21:05
 * @desc ${TODO}
 */

public class DataRepository implements DataSouceImpl{
    private Gson gson = new Gson();
    private RemoteDataSource mRemoteDataSource = new RemoteDataSource();
    private int day_0 = 0;
    private int day_1 = 0;
    private int day_2 = 0;
    private int day_3 = 0;
    private int day_4 = 0;
    private int day_5 = 0;
    private int day_6 = 0;



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
                List<Subcontractor> subcontractorList = DataSupport.findAll(Subcontractor.class);
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
            }
        });
    }

    @Override
    public Observable<List<WeekTask>> getWeekTask() {
        return Observable.create(new ObservableOnSubscribe<List<WeekTask>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WeekTask>> e) throws Exception {
                // 从数据库获取一周任务分配数据 (每次请求, 初始化表, 所以只有一周的数据)
                List<WeekTask> weekLists = DataSupport.findAll(WeekTask.class);
                Log.d("==", "计划数量: " + weekLists.size());
                e.onNext(weekLists);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Integer[]> getDayCount() {
        return Observable.create(new ObservableOnSubscribe<Integer[]>() {
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
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<WeekTaskBean>> doRefresh(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<List<WeekTaskBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WeekTaskBean>> e) throws Exception {
                String subcontractorAccount = DataSupport.findAll(Subcontractor.class).get(0).getSubcontractorAccount();
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
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

                Log.d("==", "初始化数据库后: " + DataSupport.findAll(WeekTask.class).size());

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

                Log.d("==", "保存数据到数据库后: " + DataSupport.findAll(WeekTask.class).size());

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


                // 从数据库获取一周任务分配数据
                List<WeekTask> weekLists = DataSupport.findAll(WeekTask.class);
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
                List<Ship> all = DataSupport.findAll(Ship.class);
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

    @Override
    public Observable<Acceptance> getAcceptanceByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<Acceptance>() {
            @Override
            public void subscribe(ObservableEmitter<Acceptance> e) throws Exception {
                // 1. 发送网络请求
                String data = mRemoteDataSource.getAcceptanceByItemID(itemID);
                Log.d("==", data);

                // 2. 解析成对象
                List<AcceptanceBean> lists = gson.fromJson(data, new TypeToken<List<AcceptanceBean>>() {
                }.getType());
                AcceptanceBean accepBean = lists.get(0);

                // 3. 保存到数据库
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
                accep.save();
                // 4. 返回存到数据库的数据
                e.onNext(accep);
                e.onComplete();
            }
        });
    }

    /**
     * 提交验沙结果
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
                String result = mRemoteDataSource.UpdateForReceptionSandTime(itemID, ReceptionSandTime, Capacity, DeckGauge);
                Log.d("==", result);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(Integer.valueOf(bean.getMessage()));
                e.onComplete();
            }
        });
    }

    /**
     * 提交验收结果
     * @param itemID
     * @param PassReceptionSandTime
     * @return
     */
    @Override
    public Observable<Integer> UpdateForPassReceptionSandTime(final int itemID, final String PassReceptionSandTime) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                String result = mRemoteDataSource.UpdateForPassReceptionSandTime(itemID, PassReceptionSandTime);
                Log.d("==", result);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(Integer.valueOf(bean.getMessage()));
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
