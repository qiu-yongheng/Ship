package com.kc.shiptransport.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.AppListBean;
import com.kc.shiptransport.data.bean.AttendanceAuditCommitBean;
import com.kc.shiptransport.data.bean.AttendanceTypeBean;
import com.kc.shiptransport.data.bean.CommitImgListBean;
import com.kc.shiptransport.data.bean.CommitResultBean;
import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.LoginResult;
import com.kc.shiptransport.data.bean.PartitionSBBean;
import com.kc.shiptransport.data.bean.RecordListBean;
import com.kc.shiptransport.data.bean.RecordedSandUpdataBean;
import com.kc.shiptransport.data.bean.SampleCommitResult;
import com.kc.shiptransport.data.bean.SampleShowDatesBean;
import com.kc.shiptransport.data.bean.SampleUpdataBean;
import com.kc.shiptransport.data.bean.ScanCommitBean;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.data.bean.ScannerListBean;
import com.kc.shiptransport.data.bean.ShipBean;
import com.kc.shiptransport.data.bean.StoneSourceBean;
import com.kc.shiptransport.data.bean.SubcontractorBean;
import com.kc.shiptransport.data.bean.SubmitBean;
import com.kc.shiptransport.data.bean.TaskVolumeBean;
import com.kc.shiptransport.data.bean.VoyageDetailBean;
import com.kc.shiptransport.data.bean.downlog.DownLogBean;
import com.kc.shiptransport.data.bean.threadsandlog.ThreadSandLogBean;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.db.AppList;
import com.kc.shiptransport.db.AttendanceRecordList;
import com.kc.shiptransport.db.AttendanceType;
import com.kc.shiptransport.db.CommitShip;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.RecordList;
import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.db.SampleImageList;
import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.db.ScannerImage;
import com.kc.shiptransport.db.StoneSource;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.TaskVolume;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.db.amount.AmountDetail;
import com.kc.shiptransport.db.down.StopList;
import com.kc.shiptransport.db.down.StopOption;
import com.kc.shiptransport.db.exitapplication.ExitDetail;
import com.kc.shiptransport.db.exitapplication.ExitList;
import com.kc.shiptransport.db.partition.PartitionNum;
import com.kc.shiptransport.db.ship.Ship;
import com.kc.shiptransport.db.ship.ShipList;
import com.kc.shiptransport.db.supply.SupplyDetail;
import com.kc.shiptransport.db.threadsand.Layered;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.db.userinfo.UserInfo;
import com.kc.shiptransport.db.voyage.PerfectBoatRecordInfo;
import com.kc.shiptransport.db.voyage.WashStoneSource;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.FileUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;
import com.umeng.analytics.MobclickAgent;

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

import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import top.zibin.luban.Luban;

import static org.litepal.LitePalApplication.getContext;
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
        String subcontractorInfo = null;
        try {
            subcontractorInfo = mRemoteDataSource.getSubcontractorInfo(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        String shipInfo = null;
        try {
            shipInfo = mRemoteDataSource.getShipInfo(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ShipBean> list = gson.fromJson(shipInfo, new TypeToken<List<ShipBean>>() {
        }.getType());
        if (list != null && !list.isEmpty()) {
            DataSupport.deleteAll(Ship.class);
            //            for (ShipBean listBean : list) {
            //                Ship ship = new Ship();
            //                ship.setItemID(listBean.getItemID());
            //                ship.setShipID(listBean.getShipID());
            //                ship.setShipAccount(listBean.getShipAccount());
            //                ship.setShipName(listBean.getShipName());
            //                ship.setShipType(listBean.getShipType());
            //                ship.setMaxSandSupplyCount(listBean.getMaxSandSupplyCount());
            //                ship.setCapacity(listBean.getCapacity());
            //                ship.setDeckGauge(listBean.getDeckGauge());
            //                ship.setSelected("0");
            //                ship.save();
            //            }
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
    public Observable<Integer[]> getDayCount() {
        return Observable.create(new ObservableOnSubscribe<Integer[]>() {
            @Override
            public void subscribe(ObservableEmitter<Integer[]> e) throws Exception {
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
                    Integer[] integers = new Integer[]{day_0, day_1, day_2, day_3, day_4, day_5, day_6};

                    e.onNext(integers);
                    e.onComplete();
                }
            }
        });
    }

    /**
     * 根据类型获取每日计划量
     *
     * @param type
     * @return
     */
    @Override
    public Observable<Integer[]> getDayCount(final int type) {
        return Observable.create(new ObservableOnSubscribe<Integer[]>() {
            @Override
            public void subscribe(ObservableEmitter<Integer[]> e) throws Exception {
                // 初始化每日计划量
                reset();

                if (type == SettingUtil.TYPE_RECORDEDSAND) {
                    /** 过砂记录 */
                    List<RecordList> recordLists = DataSupport.findAll(RecordList.class);

                    for (RecordList recordList : recordLists) {
                        switch (Integer.valueOf(recordList.getPosition()) % 7) {
                            case 0:
                                day_0 += Double.valueOf(recordList.getSandSupplyCount());
                                break;
                            case 1:
                                day_1 += Double.valueOf(recordList.getSandSupplyCount());
                                break;
                            case 2:
                                day_2 += Double.valueOf(recordList.getSandSupplyCount());
                                break;
                            case 3:
                                day_3 += Double.valueOf(recordList.getSandSupplyCount());
                                break;
                            case 4:
                                day_4 += Double.valueOf(recordList.getSandSupplyCount());
                                break;
                            case 5:
                                day_5 += Double.valueOf(recordList.getSandSupplyCount());
                                break;
                            case 6:
                                day_6 += Double.valueOf(recordList.getSandSupplyCount());
                                break;
                        }
                    }

                } else if (type == SettingUtil.TYPE_SAMPLE) {
                    /** 验砂取样 */
                    List<SandSample> sampleList = DataSupport.findAll(SandSample.class);

                    for (SandSample sample : sampleList) {
                        switch (Integer.valueOf(sample.getPosition()) % 7) {
                            case 0:
                                day_0 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 1:
                                day_1 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 2:
                                day_2 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 3:
                                day_3 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 4:
                                day_4 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 5:
                                day_5 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 6:
                                day_6 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                        }
                    }

                } else if (type == SettingUtil.TYPE_EXIT_APPLICATION) {
                    /** 退场申请 */
                    List<ExitList> sampleList = DataSupport.findAll(ExitList.class);

                    for (ExitList sample : sampleList) {
                        switch (Integer.valueOf(sample.getPosition()) % 7) {
                            case 0:
                                day_0 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 1:
                                day_1 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 2:
                                day_2 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 3:
                                day_3 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 4:
                                day_4 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 5:
                                day_5 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                            case 6:
                                day_6 += Double.valueOf(sample.getSandSupplyCount());
                                break;
                        }
                    }
                } else {
                    List<WeekTask> weekLists;
                    if (type == SettingUtil.TYPE_SUPPLY || type == SettingUtil.TYPE_AMOUNT) { // 验砂 或 量方
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
                }

                Integer[] integers = new Integer[]{day_0, day_1, day_2, day_3, day_4, day_5, day_6};

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
                List<WeekTask> lists = gson.fromJson(weekTaskInfo, new TypeToken<List<WeekTask>>() {
                }.getType());

                Log.d("==", "一周计划数量: " + lists.size());

                /* 3. 初始化数据库 */
                DataSupport.deleteAll(WeekTask.class);

                Log.d("==", "初始化数据库后: " + findAll(WeekTask.class).size());

                /* 4. 保存数据到数据库 */
                DataSupport.saveAll(lists);

                Log.d("==", "保存数据到数据库后: " + findAll(WeekTask.class).size());






                /* 5. 根据日期对数据进行分类 */
                List<List<WeekTask>> totalLists = new ArrayList<>();

                Set set = new HashSet();
                for (WeekTask bean : lists) {
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
                v.put("isSelected", 0);
                DataSupport.updateAll(ShipList.class, v);

                Log.d("==", "重置后, 选择数量: " + DataSupport.where("isSelected = ?", "1").find(ShipList.class).size());


                // 根据计划, 设置ship select = 1
                ContentValues values = new ContentValues();
                values.put("isSelected", 1);
                for (WeekTask weekTask : weekLists) {
                    DataSupport.updateAll(ShipList.class, values, "ShipAccount = ?", weekTask.getShipAccount());
                }

                Log.d("==", "设置后, 选择数量: " + DataSupport.where("isSelected = ?", "1").find(ShipList.class).size());
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
                List<WeekTask> lists = gson.fromJson(weekTaskInfo, new TypeToken<List<WeekTask>>() {
                }.getType());

                Log.d("==", "一周计划数量: " + lists.size());

                /* 3. 初始化数据库 */
                DataSupport.deleteAll(WeekTask.class);

                Log.d("==", "初始化数据库后: " + findAll(WeekTask.class).size());

                /* 4. 保存数据到数据库 */
                DataSupport.saveAll(lists);

                Log.d("==", "保存数据到数据库后: " + findAll(WeekTask.class).size());






                /* 5. 根据日期对数据进行分类 */
                List<List<WeekTask>> totalLists = new ArrayList<>();

                Set set = new HashSet();
                for (WeekTask bean : lists) {
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
                v.put("isSelected", 0);
                DataSupport.updateAll(ShipList.class, v);

                Log.d("==", "重置后, 选择数量: " + DataSupport.where("isSelected = ?", "1").find(ShipList.class).size());


                // 根据计划, 设置ship select = 1
                ContentValues values = new ContentValues();
                values.put("isSelected", 1);
                for (WeekTask weekTask : weekLists) {
                    DataSupport.updateAll(ShipList.class, values, "ShipAccount = ?", weekTask.getShipAccount());
                }

                Log.d("==", "设置后, 选择数量: " + DataSupport.where("isSelected = ?", "1").find(ShipList.class).size());
                Log.d("==", "----PlanPresenter: 从数据库获取数据, 重置ship的选择状态----");


                // 通知presenter从DB获取数据
                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 查询船舶数据
     *
     * @return
     */
    @Override
    public Observable<List<Ship>> getShipCategory() {
        return Observable.create(new ObservableOnSubscribe<List<Ship>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Ship>> e) throws Exception {
                // 激进查询
                List<Ship> all = DataSupport.order("ShipType asc").find(Ship.class, true);

                e.onNext(all);
                e.onComplete();
            }
        });
    }

    /**
     * 1.6返回供砂明细,根据ItemID获取对应的数据接口
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
                    List<Acceptance> lists = gson.fromJson(data, new TypeToken<List<Acceptance>>() {
                    }.getType());
                    Acceptance accepBean = lists.get(0);

                    // 3. 保存到数据库 TODO 先删除item对应数据, 再插入
                    int i = DataSupport.deleteAll(Acceptance.class, "ItemID = ?", String.valueOf(itemID));
                    Log.d("==", "删除item: " + i);
                    accepBean.save();

                    // 4. 返回存到数据库的数据
                    e.onNext(accepBean);
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
     * @return
     */
    @Override
    public Observable<Integer> updateForReceptionSandTime(final int itemID, final String ReceptionSandTime) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d("==", "第一");
                String result = mRemoteDataSource.UpdateForReceptionSandTime(itemID, ReceptionSandTime);
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
                    /** 过砂记录, IsFinish = 0的个数 */
                    num = DataSupport.where("IsFinish = ?", "0").count(RecordList.class);
                } else if (type == SettingUtil.TYPE_VOYAGEINFO) { // 航次信息完善
                    /** 航次信息完善, IsPerfect = 0的个数 */
                    num = DataSupport.where("IsPerfect = ?", "0").count(WeekTask.class);
                } else if (type == SettingUtil.TYPE_SAMPLE) { // 验砂取样
                    /** 验砂取样 */
                    num = DataSupport.where("IsSandSampling = ?", "0").count(SandSample.class);
                } else if (type == SettingUtil.TYPE_SUPPLY) { // 验砂
                    /** 验砂 */
                    num = DataSupport.where("IsReceptionSandTime = ?", "0").count(WeekTask.class);
                } else if (type == SettingUtil.TYPE_AMOUNT) { // 量方
                    /** 量方 */
                    num = DataSupport.where("IsTheAmountOfTime = ?", "0").count(WeekTask.class);
                } else if (type == SettingUtil.TYPE_SCANNER) {
                    /** 扫描件 */
                    num = DataSupport.where("IsFinshReceptionSandAttachment = ?", "0").count(WeekTask.class);
                } else if (type == SettingUtil.TYPE_EXIT_APPLICATION) {
                    /** 退场申请 */
                    num = DataSupport.where("IsExit = ?", "0").count(ExitList.class);
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
                            }
                            //                            else if (type == SettingUtil.TYPE_SUPPLY) { // 验砂
                            //                                time = weektask.getPreAcceptanceTime();
                            //                                time2 = weektask.getReceptionSandTime();
                            //                                if ((time != null && !time.equals("")) && (time2 == null || time2.equals(""))) {
                            //                                    num++;
                            //                                }
                            //                            }
                            //                            else if (type == SettingUtil.TYPE_AMOUNT) { // 量方
                            //                                time = weektask.getPreAcceptanceTime();
                            //                                time2 = weektask.getTheAmountOfTime();
                            //                                if ((time != null && !time.equals("")) && (time2 == null || time2.equals(""))) {
                            //                                    num++;
                            //                                }
                            //                            }
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
    public Observable<Integer> getPlanMeasure(final String date) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                int d = 0;
                List<WeekTask> list = DataSupport.where("PlanDay = ?", date).find(WeekTask.class);
                for (WeekTask weekTask : list) {
                    int sandSupplyCount = Integer.valueOf(weekTask.getSandSupplyCount());
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
     * @param value
     * @return
     */
    @Override
    public Observable<Integer> InsertPreAcceptanceEvaluation(final int itemID, final int rbcomplete, final int rbtimely, final String currentDate, final String shipNum, final int subcontractorInterimApproachPlanID, final Acceptance value) {
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
                jsonObject.put("ShipAccount", value.getShipAccount());
                jsonObject.put("SubcontractorAccount", value.getSubcontractorAccount());
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
    public Observable<Ship> getShipByType(final String type) {
        return Observable.create(new ObservableOnSubscribe<Ship>() {
            @Override
            public void subscribe(ObservableEmitter<Ship> e) throws Exception {
                List<Ship> list = DataSupport.where("ShipType = ?", type).find(Ship.class, true);
                e.onNext(list.get(0));
                e.onComplete();
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
                values.put("isSelected", 0);
                DataSupport.updateAll(ShipList.class, values, "ShipType = ?", type);

                /* 2. 根据当前日期, type查询计划数据, 全部设置为select = 1 */
                List<WeekTask> weekTasks = DataSupport.where("PlanDay = ? and ShipType = ?", date, type).find(WeekTask.class);
                ContentValues v = new ContentValues();
                v.put("isSelected", 1);
                for (WeekTask weekTask : weekTasks) {
                    DataSupport.updateAll(ShipList.class, v, "ShipAccount = ?", weekTask.getShipAccount());
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

                        if (!DataSupport.where("isSelected = ? and ShipAccount = ?", "0", weekTask.getShipAccount()).find(ShipList.class).isEmpty()) {
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
                List<Ship> list = gson.fromJson(shipInfo, new TypeToken<List<Ship>>() {
                }.getType());

                // 初始化数据库
                DataSupport.deleteAll(Ship.class);
                DataSupport.deleteAll(ShipList.class);

                // 保存数据
                for (Ship ship : list) {
                    ship.save();
                    List<ShipList> shipList = ship.getShipList();
                    for (ShipList bean : shipList) {
                        bean.setShip(ship);
                        bean.save();
                    }
                }

                // 激进查询, 不能设置id (应该是gson解析成对象时, 默认给id设值, 如果是手动添加数据到数据库, 可以设置id)

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

                // 初始化数据库
                DataSupport.deleteAll(Subcontractor.class);

                // 保存用户信息
                Subcontractor subcontractor = new Subcontractor();
                subcontractor.setSubcontractorName(loginResult.getUserName());
                subcontractor.setSubcontractorAccount(loginResult.getUserID());
                subcontractor.save();

                DataSupport.deleteAll(User.class);
                // 保存
                User user = new User();
                user.setUserID(loginResult.getUserID());
                user.setUserName(loginResult.getUserName());
                user.save();

                // 统计登录
                MobclickAgent.onProfileSignIn(username);

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
    public Observable<Integer> getTaskVolume(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
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
                int f = 0;
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


                    Integer appID = Integer.valueOf(bean.getAppID());
                    switch (appID) {
                        case 1:
                            // 电子海图
                            list.setIcon_id(R.mipmap.map);
                            break;
                        case 2:
                            // 供砂管理
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 3:
                            // 施工记录
                            list.setIcon_id(R.mipmap.log);
                            break;
                        case 4:
                            // 施工照片
                            list.setIcon_id(R.mipmap.supply_sand);
                            break;
                        case 5:
                            // 安全管理
                            list.setIcon_id(R.mipmap.save);
                            break;
                        case 6:
                            // 数据分析
                            list.setIcon_id(R.mipmap.data);
                            break;
                        case 7:
                            // 生产指令
                            list.setIcon_id(R.mipmap.msg);
                            break;
                        case 8:
                            // 航线管理
                            list.setIcon_id(R.mipmap.ship_monitor);
                            break;
                        case 9:
                            // 通讯录
                            list.setIcon_id(R.mipmap.acceptance);
                            break;
                        case 10:
                            // 分包商进场计划
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 11:
                            // 待验收航次
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 12:
                            // 待验砂船次
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 13:
                            // 量方管理
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 14:
                            // 分包商航次信息完善
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 15:
                            // 分包商航次完善扫描件
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 16:
                            // 验砂取样
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 17:
                            // 过砂记录
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 18:
                            // 考勤管理
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 19:
                            // 考勤打卡
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 20:
                            // 考勤审核
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 21:
                            // 退场申请
                            list.setIcon_id(R.mipmap.plan);
                            break;
                    }

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
    public Observable<Integer[]> getDemandDayCount(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<Integer[]>() {
            @Override
            public void subscribe(ObservableEmitter<Integer[]> e) throws Exception {
                List<String> dates = CalendarUtil.getdateOfWeek("yyyy-MM-dd", jumpWeek);

                Integer[] doubles = new Integer[7];

                for (int i = 0; i < dates.size(); i++) {
                    List<TaskVolume> taskVolumes = DataSupport.where("Date like ?", dates.get(i) + "%").find(TaskVolume.class);
                    if (taskVolumes != null && !taskVolumes.isEmpty()) {
                        doubles[i] = taskVolumes.get(0).getAllBoatSum();
                    } else {
                        doubles[i] = 0;

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
     * @param subcontractorAccount
     * @param Capacity
     * @param DeckGauge
     * @param Deduction            @return
     */
    @Override
    public Observable<Boolean> InsertTheAmountOfSideRecord(final int itemID,
                                                           final String TheAmountOfTime,
                                                           final String subcontractorAccount,
                                                           final int SubcontractorInterimApproachPlanID,
                                                           final String ShipAccount,
                                                           final String Capacity,
                                                           final String DeckGauge,
                                                           final String Deduction,
                                                           final String Creator) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                // 把数据解析成json
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ItemID", itemID);
                jsonObject.put("TheAmountOfTime", TheAmountOfTime);
                jsonObject.put("SubcontractorAccount", subcontractorAccount);
                jsonObject.put("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);
                jsonObject.put("ShipAccount", ShipAccount);
                jsonObject.put("Capacity", Capacity);
                jsonObject.put("DeckGauge", DeckGauge);
                jsonObject.put("Deduction", Deduction);
                jsonObject.put("Creator", Creator);

                jsonArray.put(jsonObject);


                String json = jsonArray.toString();
                Log.d("==", "量方: " + json);


                String result = mRemoteDataSource.InsertTheAmountOfSideRecord(json);
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
    public Observable<Boolean> InsertPerfectBoatRecord(final VoyageDetailBean bean) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ItemID", bean.getItemID());
                jsonObject.put("SubcontractorInterimApproachPlanID", bean.getSubcontractorInterimApproachPlanID());

                List<VoyageDetailBean.ColumnsBean> columns = bean.getColumns();

                for (VoyageDetailBean.ColumnsBean columnsBean : columns) {
                    if (columnsBean.getControlType().equals("select")) {
                        String value = columnsBean.getValue();
                        String[] split = value.split(";");
                        if (split.length > 1) {
                            jsonObject.put(columnsBean.getColumnName(), split[1]);
                        } else {
                            jsonObject.put(columnsBean.getColumnName(), split[0]);
                        }
                    } else {
                        jsonObject.put(columnsBean.getColumnName(), columnsBean.getValue());
                    }
                }

                jsonObject.put("Creator", findAll(Subcontractor.class).get(0).getSubcontractorAccount());

                jsonArray.put(jsonObject);

                String json = jsonArray.toString();

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
                List<ConstructionBoat> list = gson.fromJson(result, new TypeToken<List<ConstructionBoat>>() {
                }.getType());

                if (!list.isEmpty()) {
                    DataSupport.deleteAll(ConstructionBoat.class);
                    // 保存到数据库
                    DataSupport.saveAll(list);
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
                int i = DataSupport.deleteAll(WeekTask.class, "PreAcceptanceTime IS NULL");

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
    public Observable<Boolean> getOverSandRecordList(final int jumpWeek, final String account) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                /* 开始时间 */
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
                /* 结束时间 */
                String endDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY, jumpWeek);

                Log.d("==", "请求日期: " + startDay + "-" + endDay + ", 账号: " + account);

                /* 1. 获取请求数据 */
                String recordList = mRemoteDataSource.GetOverSandRecordList(account, startDay, endDay);

                Log.d("==", "过砂记录任务详情: " + recordList);

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
                List<SandSample> lists = gson.fromJson(result, new TypeToken<List<SandSample>>() {
                }.getType());

                /* 3. 初始化数据库 */
                deleteAll(SandSample.class);

                /* 4. 保存数据到数据库 */
                DataSupport.saveAll(lists);

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
    public Observable<PerfectBoatRecordInfo> getPerfectBoatRecordByItemID(final WeekTask weekTask, final boolean isNetwork) {
        return Observable.create(new ObservableOnSubscribe<PerfectBoatRecordInfo>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<PerfectBoatRecordInfo> e) throws Exception {

                // 1. 发送网络请求
                String result = mRemoteDataSource.GetPerfectBoatRecordByItemID(String.valueOf(weekTask.getPerfectBoatRecordID()));
                // 2. 解析数据
                List<PerfectBoatRecordInfo> lists = gson.fromJson(result, new TypeToken<List<PerfectBoatRecordInfo>>() {
                }.getType());

                deleteAll(PerfectBoatRecordInfo.class);
                PerfectBoatRecordInfo bean = lists.get(0);
                bean.save();
                e.onComplete();
            }
        });
    }

    /**
     * 根据itemID获取扫描图片数据
     *
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
     *
     * @param file
     * @return
     */
    @Override
    public Observable<File> compressWithRx(final Context context, final File file) {
        return Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<File> e) throws Exception {
                e.onNext(Luban.with(context).load(file).get());
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取要提交图片的数据
     *
     * @param sandSample
     * @return
     */
    @Override
    public Observable<List<SampleImageList>> getCommitImageList(final SandSample sandSample) {
        return Observable.create(new ObservableOnSubscribe<List<SampleImageList>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<SampleImageList>> e) throws Exception {
                // 获取保存的数据
                List<SampleImageList> all = DataSupport.where("ItemID = ?", String.valueOf(sandSample.getItemID())).find(SampleImageList.class);

                if (all.isEmpty()) {
                    e.onError(new RuntimeException("没有图片进行提交"));
                } else {
                    e.onNext(all);
                }

                e.onComplete();
            }
        });
    }

    /**
     * 提交图片
     *
     * @param commitList
     * @return
     */
    @Override
    public Observable<Boolean> commitImage(final SampleImageList commitList) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                File file = new File(commitList.getFilePath());
                byte[] bytes = FileUtil.File2byte(file);

                // 发送网络请求, 提交图片
                String result = mRemoteDataSource.UploadFile(new String(Base64.encode(bytes, Base64.DEFAULT)), commitList.getSuffixName(), commitList.getFileName());

                // 解析返回的数据
                SampleCommitResult commitResult = gson.fromJson(result, SampleCommitResult.class);

                // 更新图片网络路径
                if (commitResult.getMessage() == 1) {
                    commitList.setNetPath(commitResult.getFilePath());
                    commitList.setItemGuid(commitResult.getItemGuid());
                    commitList.save();
                }

                e.onNext(commitResult.getMessage() == 1);

            }
        });
    }

    /**
     * 根据position获取过砂记录
     *
     * @param position
     * @return
     */
    @Override
    public Observable<RecordList> getRecordListForPosition(final int position) {
        return Observable.create(new ObservableOnSubscribe<RecordList>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<RecordList> e) throws Exception {
                List<RecordList> recordLists = DataSupport.where("position = ?", String.valueOf(position)).find(RecordList.class);
                if (recordLists.isEmpty()) {
                    e.onError(new RuntimeException("获取数据失败！"));
                } else {
                    e.onNext(recordLists.get(0));
                }

                e.onComplete();

            }
        });
    }

    /**
     * 根据进场ID, 获取分包商航次完善扫描件类型数据
     *
     * @return
     */
    @Override
    public Observable<List<ScannerListBean>> getScannerType(final int subID) {
        return Observable.create(new ObservableOnSubscribe<List<ScannerListBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ScannerListBean>> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.GetSubcontractorPerfectBoatScannerAttachmentRecordBySubcontractorInterimApproachPlanID(subID);
                // 解析数据
                if (TextUtils.isEmpty(result)) {
                    e.onError(new RuntimeException("服务器异常"));
                } else {
                    List<ScannerListBean> list = gson.fromJson(result, new TypeToken<List<ScannerListBean>>() {
                    }.getType());
                    e.onNext(list);
                }

                e.onComplete();
            }
        });
    }

    /**
     * 提交过砂记录
     *
     * @param bean
     * @return
     */
    @Override
    public Observable<Boolean> InsertOverSandRecord(final RecordedSandUpdataBean bean) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 把对象转换成json
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("ItemID", bean.getItemID());
                jsonObject.put("SubcontractorInterimApproachPlanID", bean.getSubcontractorInterimApproachPlanID());
                jsonObject.put("SandHandlingShipID", bean.getSandHandlingShipID());
                jsonObject.put("ConstructionShipID", bean.getConstructionShipID());
                jsonObject.put("StartTime", bean.getStartTime());
                jsonObject.put("EndTime", bean.getEndTime());
                jsonObject.put("BeforeOverSandDraft1", bean.getBeforeOverSandDraft1());
                jsonObject.put("BeforeOverSandDraft2", bean.getBeforeOverSandDraft2());
                jsonObject.put("BeforeOverSandDraft3", bean.getBeforeOverSandDraft3());
                jsonObject.put("BeforeOverSandDraft4", bean.getBeforeOverSandDraft4());
                jsonObject.put("AfterOverSandDraft1", bean.getAfterOverSandDraft1());
                jsonObject.put("AfterOverSandDraft2", bean.getAfterOverSandDraft2());
                jsonObject.put("AfterOverSandDraft3", bean.getAfterOverSandDraft3());
                jsonObject.put("AfterOverSandDraft4", bean.getAfterOverSandDraft4());
                jsonObject.put("ActualAmountOfSand", bean.getActualAmountOfSand());
                jsonObject.put("IsFinish", bean.getIsFinish());
                jsonObject.put("Creator", bean.getCreator());

                jsonArray.put(jsonObject);

                Log.d("==", "提交过砂记录: " + jsonArray.toString());

                String result = mRemoteDataSource.InsertOverSandRecord(jsonArray.toString());

                CommitResultBean resultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(resultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 根据进场计划ID获取过砂记录明细（多条）
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    @Override
    public Observable<List<RecordedSandShowList>> GetOverSandRecordBySubcontractorInterimApproachPlanID(final int SubcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<List<RecordedSandShowList>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<RecordedSandShowList>> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.GetOverSandRecordBySubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID);

                // 解析数据
                List<RecordedSandShowList> lists = gson.fromJson(result, new TypeToken<List<RecordedSandShowList>>() {
                }.getType());

                e.onNext(lists);
                e.onComplete();
            }
        });
    }

    /**
     * 获取考勤类型
     *
     * @return
     */
    @Override
    public Observable<List<AttendanceType>> GetAttendanceTypeList() {
        return Observable.create(new ObservableOnSubscribe<List<AttendanceType>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AttendanceType>> e) throws Exception {
                //                List<AttendanceType> all = DataSupport.findAll(AttendanceType.class);
                //                if (all.isEmpty()) {
                // 发送网络请求
                String result = mRemoteDataSource.GetAttendanceTypeList();

                // 解析数据
                List<AttendanceTypeBean> list = gson.fromJson(result, new TypeToken<List<AttendanceTypeBean>>() {
                }.getType());

                if (!list.isEmpty()) {
                    DataSupport.deleteAll(AttendanceType.class);

                    for (AttendanceTypeBean bean : list) {
                        AttendanceType type = new AttendanceType();
                        type.setItemID(bean.getItemID());
                        type.setName(bean.getName());
                        type.save();
                    }
                }
                e.onNext(DataSupport.findAll(AttendanceType.class));
                //                } else {
                //                    e.onNext(all);
                //                }

                e.onComplete();
            }
        });
    }

    /**
     * 提交考勤数据
     *
     * @return
     */
    @Override
    public Observable<Boolean> InsertAttendanceRecord(final String ItemID,
                                                      final int AttendanceTypeID,
                                                      final String Creator,
                                                      final String Remark,
                                                      final String time) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 将数据解析成json
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ItemID", ItemID);
                jsonObject.put("AttendanceTypeID", AttendanceTypeID);
                jsonObject.put("AttendanceTime", time);
                jsonObject.put("Creator", Creator);
                jsonObject.put("Remark", Remark);
                jsonArray.put(jsonObject);

                Log.d("==", "提交考勤数据: " + jsonArray.toString());

                String result = mRemoteDataSource.InsertAttendanceRecord(jsonArray.toString());

                CommitResultBean commitResultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(commitResultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.26 获取考勤记录
     *
     * @return
     */
    @Override
    public Observable<List<AttendanceRecordList>> GetAttendanceRecords(final int itemID, final String account, final String startDate, final String endDate) {
        return Observable.create(new ObservableOnSubscribe<List<AttendanceRecordList>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AttendanceRecordList>> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.GetAttendanceRecords(itemID, account, startDate, endDate);

                // 解析数据
                List<AttendanceRecordList> list = gson.fromJson(result, new TypeToken<List<AttendanceRecordList>>() {
                }.getType());

                // 初始化数据库
                DataSupport.deleteAll(AttendanceRecordList.class);

                // 保存数据
                DataSupport.saveAll(list);

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 根据进场ID获取验砂取样详细
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    @Override
    public Observable<SampleShowDatesBean> GetSandSamplingBySubcontractorInterimApproachPlanID(final int SubcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<SampleShowDatesBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<SampleShowDatesBean> e) throws Exception {
                // 先判断本地是否有数据没有提交 (进场ID对应)
                String data = SharePreferenceUtil.getString(getContext(), String.valueOf(SubcontractorInterimApproachPlanID), "");

                if (TextUtils.isEmpty(data)) { // 如果没有本地缓存
                    // 根据进场ID发送网络请求
                    String result = mRemoteDataSource.GetSandSamplingBySubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID);

                    // 解析数据
                    List<SampleShowDatesBean> list = gson.fromJson(result, new TypeToken<List<SampleShowDatesBean>>() {
                    }.getType());

                    SampleShowDatesBean sampleShowDatesBean = list.get(0);
                    // 保存当前进场ID
                    sampleShowDatesBean.setSubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID);

                    e.onNext(sampleShowDatesBean);
                } else {
                    // 本地有缓存
                    SampleShowDatesBean sampleShowDatesBean = gson.fromJson(data, SampleShowDatesBean.class);

                    e.onNext(sampleShowDatesBean);
                }

                e.onComplete();
            }
        });
    }

    /**
     * 提交验砂取样数据
     *
     * @param bean
     * @return
     */
    @Override
    public Observable<Boolean> InsertSandSampling(final SampleShowDatesBean bean) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 创建要提交的对象
                SampleUpdataBean sampleUpdataBean = new SampleUpdataBean();
                // 设置进场ID
                sampleUpdataBean.setItemID(String.valueOf(bean.getItemID()));
                sampleUpdataBean.setSubcontractorInterimApproachPlanID(String.valueOf(bean.getSubcontractorInterimApproachPlanID()));
                sampleUpdataBean.setConstructionBoatAccount(bean.getConstructionBoatAccount());
                sampleUpdataBean.setCreator(DataSupport.findAll(Subcontractor.class).get(0).getSubcontractorAccount());
                sampleUpdataBean.setBatch(bean.getBatch());
                sampleUpdataBean.setSandSamplingDate(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));

                // 创建取样编号集合
                sampleUpdataBean.setSandSamplingNumRecordList(new ArrayList<SampleUpdataBean.SandSamplingNumRecordListBean>());

                // 遍历SampleShowDatesBean, 录入数据
                List<SampleShowDatesBean.SandSamplingNumRecordListBean> sandSamplingNumRecordList = bean.getSandSamplingNumRecordList();
                for (int i = 0; i < sandSamplingNumRecordList.size(); i++) {

                    SampleUpdataBean.SandSamplingNumRecordListBean numRecordListBean = new SampleUpdataBean.SandSamplingNumRecordListBean();
                    numRecordListBean.setItemID(String.valueOf(sandSamplingNumRecordList.get(i).getItemID()));
                    numRecordListBean.setSandSamplingID(String.valueOf(sandSamplingNumRecordList.get(i).getSandSamplingID()));
                    numRecordListBean.setSamplingNum(sandSamplingNumRecordList.get(i).getSamplingNum());
                    numRecordListBean.setConstructionBoatAccount(sandSamplingNumRecordList.get(i).getConstructionBoatAccount());

                    // 创建图片集合
                    numRecordListBean.setSandSamplingAttachmentRecordList(new ArrayList<SampleUpdataBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean>());

                    // 查询position对应的图片数据
                    List<SampleImageList> image_1 = DataSupport.where("position = ? and img_x = ?", String.valueOf(i), String.valueOf(SettingUtil.HOLDER_IMAGE_1)).find(SampleImageList.class);
                    List<SampleImageList> image_2 = DataSupport.where("position = ? and img_x = ?", String.valueOf(i), String.valueOf(SettingUtil.HOLDER_IMAGE_2)).find(SampleImageList.class);

                    List<SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean> sandSamplingAttachmentRecordList = sandSamplingNumRecordList.get(i).getSandSamplingAttachmentRecordList();

                    if (!image_1.isEmpty()) {
                        // 图片1
                        SampleUpdataBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean imageListBean_1 = new SampleUpdataBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean();
                        imageListBean_1.setSandSamplingID(String.valueOf(sandSamplingAttachmentRecordList.get(0).getSandSamplingID()));
                        imageListBean_1.setItemID(String.valueOf(sandSamplingAttachmentRecordList.get(0).getItemID()));
                        imageListBean_1.setFileName(image_1.get(0).getFileName());
                        imageListBean_1.setSuffixName(image_1.get(0).getSuffixName());
                        imageListBean_1.setSandSamplingNumID(String.valueOf(sandSamplingAttachmentRecordList.get(0).getSandSamplingNumID()));
                        imageListBean_1.setFilePath(image_1.get(0).getNetPath());
                        imageListBean_1.setConstructionBoatAccount(image_1.get(0).getConstructionBoatAccount());

                        // 把图片保存到集合中
                        numRecordListBean.getSandSamplingAttachmentRecordList().add(imageListBean_1);
                    }

                    if (!image_2.isEmpty()) {
                        // 图片2
                        SampleUpdataBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean imageListBean_2 = new SampleUpdataBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean();
                        imageListBean_2.setSandSamplingID(String.valueOf(sandSamplingAttachmentRecordList.get(1).getSandSamplingID()));
                        imageListBean_2.setItemID(String.valueOf(sandSamplingAttachmentRecordList.get(1).getItemID()));
                        imageListBean_2.setFileName(image_2.get(0).getFileName());
                        imageListBean_2.setSuffixName(image_2.get(0).getSuffixName());
                        imageListBean_2.setSandSamplingNumID(String.valueOf(sandSamplingAttachmentRecordList.get(1).getSandSamplingNumID()));
                        imageListBean_2.setFilePath(image_2.get(0).getNetPath());
                        imageListBean_2.setConstructionBoatAccount(image_2.get(0).getConstructionBoatAccount());

                        // 把图片保存到集合中
                        numRecordListBean.getSandSamplingAttachmentRecordList().add(imageListBean_2);
                    }

                    // 把验砂取样编号数据保存到集合中
                    sampleUpdataBean.getSandSamplingNumRecordList().add(numRecordListBean);
                }

                // 把对象解析成json数据
                String json = gson.toJson(sampleUpdataBean);

                Log.d("==", "提交图片: " + json);

                // 发送网络请求
                String result = mRemoteDataSource.InsertSandSampling(json);

                CommitResultBean commitResultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(commitResultBean.getMessage() == 1);

                e.onComplete();
            }
        });
    }

    /**
     * 根据AppPID获取要显示的图标
     *
     * @param AppPID
     * @return
     */
    @Override
    public Observable<List<AppList>> getAppList(final int AppPID) {
        return Observable.create(new ObservableOnSubscribe<List<AppList>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AppList>> e) throws Exception {
                // 查询数据库数据, 并排序
                List<AppList> appLists = DataSupport.where("AppPID = ?", String.valueOf(AppPID)).order("SortNum asc").find(AppList.class);

                e.onNext(appLists);
                e.onComplete();
            }
        });
    }

    /**
     * 提交扫描件图片
     *
     * @param bean
     * @return
     */
    @Override
    public Observable<Boolean> InsertSubcontractorPerfectBoatScannerAttachment(final ScanCommitBean bean) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 解析数据成json
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("ItemID", bean.getItemID());
                jsonObject.put("SubcontractorInterimApproachPlanID", bean.getSubcontractorInterimApproachPlanID());
                jsonObject.put("SubcontractorPerfectBoatScannerAttachmentTypeID", bean.getSubcontractorPerfectBoatScannerAttachmentTypeID());
                jsonObject.put("SubcontractorAccount", bean.getSubcontractorAccount());
                jsonObject.put("ConstructionBoatAccount", bean.getConstructionBoatAccount());
                jsonObject.put("FileName", bean.getFileName());
                jsonObject.put("SuffixName", bean.getSuffixName());
                jsonObject.put("Creator", bean.getCreator());

                jsonArray.put(jsonObject);

                Log.d("==", "提交扫描件图片: " + jsonArray.toString());

                String json = jsonArray.toString();

                // 提交图片
                String result = mRemoteDataSource.InsertSubcontractorPerfectBoatScannerAttachment(json, bean.getBase64img());

                CommitResultBean resultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(resultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 选择多张图片回调后, 把数据解析成将要提交的集合
     *
     * @param imageMultipleResultEvent
     * @return
     */
    @Override
    public Observable<List<ScanCommitBean>> getScanImgList(final ImageMultipleResultEvent imageMultipleResultEvent, final int subID, final int typeID, final String shipAccount) {
        return Observable.create(new ObservableOnSubscribe<List<ScanCommitBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ScanCommitBean>> e) throws Exception {
                // 多选回调
                List<MediaBean> result = imageMultipleResultEvent.getResult();
                // 获取分包商账号
                Subcontractor subcontractor = DataSupport.findAll(Subcontractor.class).get(0);
                // 创建任务队列
                List<ScanCommitBean> commitBeanList = new ArrayList<ScanCommitBean>();

                // 保存图片数据到队列中
                for (MediaBean bean : result) {
                    ScanCommitBean commitBean = new ScanCommitBean();
                    // 条目ID, 默认0
                    commitBean.setItemID(0);
                    // 进场ID
                    commitBean.setSubcontractorInterimApproachPlanID(subID);
                    // 类型ID
                    commitBean.setSubcontractorPerfectBoatScannerAttachmentTypeID(typeID);
                    // 分包商账号
                    commitBean.setSubcontractorAccount(subcontractor.getSubcontractorAccount());
                    // 船舶账号
                    commitBean.setConstructionBoatAccount(shipAccount);

                    // 图片名
                    String title = bean.getTitle();

                    // 图片类型
                    String mimeType = bean.getMimeType();
                    String[] split = mimeType.split("/");
                    String suffixName = split[split.length - 1];

                    commitBean.setFileName(title + "." + suffixName);
                    commitBean.setSuffixName(suffixName);
                    commitBean.setCreator(subcontractor.getSubcontractorAccount());

                    File file = new File(bean.getOriginalPath());
                    byte[] bytes = FileUtil.File2byte(file);
                    commitBean.setBase64img(new String(Base64.encode(bytes, Base64.DEFAULT)));

                    commitBeanList.add(commitBean);
                }

                e.onNext(commitBeanList);
                e.onComplete();
            }
        });
    }

    /**
     * 根据类型获取图片
     *
     * @param subID
     * @param typeID
     * @return
     */
    @Override
    public Observable<List<ScannerImgListByTypeBean>> GetSubcontractorPerfectBoatScannerAttachmentRecordByAttachmentTypeID(final int subID, final int typeID) {
        return Observable.create(new ObservableOnSubscribe<List<ScannerImgListByTypeBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ScannerImgListByTypeBean>> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.GetSubcontractorPerfectBoatScannerAttachmentRecordByAttachmentTypeID(subID, typeID);

                // 解析数据
                List<ScannerImgListByTypeBean> list = gson.fromJson(result, new TypeToken<List<ScannerImgListByTypeBean>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.30	 删除分包商航次完善扫描件表(图片信息)
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteSubcontractorPerfectBoatScannerAttachmentByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.DeleteSubcontractorPerfectBoatScannerAttachmentByItemID(ItemID);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.33	 获取料源石场数据
     *
     * @return
     */
    @Override
    public Observable<List<StoneSource>> getStoneSource() {
        return Observable.create(new ObservableOnSubscribe<List<StoneSource>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<StoneSource>> e) throws Exception {
                // TODO 获取本地缓存, 判断是否需要网络请求
                List<StoneSource> all = DataSupport.findAll(StoneSource.class);

                // 发送网络请求
                String result = mRemoteDataSource.GetStoneSource();

                // 解析数据
                List<StoneSourceBean> list = gson.fromJson(result, new TypeToken<List<StoneSourceBean>>() {
                }.getType());

                // 判断是否需要保存到本地
                if (all.size() != list.size() && !list.isEmpty()) {
                    // 数据不一致, 重新存储
                    DataSupport.deleteAll(StoneSource.class);
                    for (StoneSourceBean bean : list) {
                        StoneSource stoneSource = new StoneSource();
                        stoneSource.setItemID(bean.getItemID());
                        stoneSource.setName(bean.getName());
                        stoneSource.setAddress(bean.getAddress());
                        stoneSource.setMileage(bean.getMileage());
                        stoneSource.setSortNum(bean.getSortNum());
                        stoneSource.save();
                    }
                }

                List<StoneSource> sources = DataSupport.order("SortNum asc").find(StoneSource.class);

                e.onNext(sources);
                e.onComplete();
            }
        });
    }

    /**
     * 1.35 获取量方信息数据
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    @Override
    public Observable<AmountDetail> GetTheAmountOfSideRecordBySubcontractorInterimApproachPlanID(final int SubcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<AmountDetail>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<AmountDetail> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.GetTheAmountOfSideRecordBySubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID);

                // 解析数据
                List<AmountDetail> list = gson.fromJson(result, new TypeToken<List<AmountDetail>>() {
                }.getType());

                e.onNext(list.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 1.27 提交考勤审核数据
     *
     * @return
     */
    @Override
    public Observable<Boolean> InsertAttendanceCheckRecord(final List<AttendanceRecordList> list) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                /** 解析数据 */
                AttendanceAuditCommitBean commitBean = new AttendanceAuditCommitBean();
                commitBean.setCreator(list.get(0).getCreator());
                commitBean.setIsCheck(String.valueOf(list.get(0).getIsCheck()));

                commitBean.setAttendanceCheckList(new ArrayList<AttendanceAuditCommitBean.AttendanceCheckListBean>());
                for (AttendanceRecordList bean : list) {
                    AttendanceAuditCommitBean.AttendanceCheckListBean listBean = new AttendanceAuditCommitBean.AttendanceCheckListBean();
                    listBean.setItemID(String.valueOf(0));
                    listBean.setAttendanceID(String.valueOf(bean.getItemID()));
                    listBean.setRemark(bean.getRemark());
                    commitBean.getAttendanceCheckList().add(listBean);
                }

                // 把数据解析成json
                String json = gson.toJson(commitBean);

                Log.d("==", "考勤审核: " + json);

                // 发送网络请求
                String result = mRemoteDataSource.InsertAttendanceCheckRecord(json);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.36 提交量方图片数据
     *
     * @param json
     * @param ByteDataStr
     * @return
     */
    @Override
    public Observable<Boolean> InsertTheAmountOfSideAttachment(final String json, final String ByteDataStr) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.InsertTheAmountOfSideAttachment(json, ByteDataStr);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 创建图片提交任务
     *
     * @param imageMultipleResultEvent
     * @param itemID
     * @param creator
     * @return
     */
    @Override
    public Observable<List<CommitImgListBean>> getImgList(final ImageMultipleResultEvent imageMultipleResultEvent, final int itemID, final String creator) {
        return Observable.create(new ObservableOnSubscribe<List<CommitImgListBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<CommitImgListBean>> e) throws Exception {
                // 创建集合保存数据
                List<CommitImgListBean> lists = new ArrayList<>();
                // 获取选中图片
                List<MediaBean> mediaBeanList = imageMultipleResultEvent.getResult();


                for (MediaBean bean : mediaBeanList) {
                    // 创建对象
                    CommitImgListBean imgListBean = new CommitImgListBean();


                    // 图片名
                    String title = bean.getTitle();
                    // 图片类型
                    String mimeType = bean.getMimeType();
                    String[] split = mimeType.split("/");
                    String suffixName = split[split.length - 1];
                    String filename = title + "." + suffixName;
                    // 解析图片
                    File file = new File(bean.getOriginalPath());
                    byte[] bytes = FileUtil.File2byte(file);
                    String ByteDataStr = new String(Base64.encode(bytes, Base64.DEFAULT));


                    // 解析成json
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("SubcontractorInterimApproachPlanID", itemID);
                    jsonObject.put("FileName", filename);
                    jsonObject.put("SuffixName", suffixName);
                    jsonObject.put("Creator", creator);
                    jsonArray.put(jsonObject);
                    String json = jsonArray.toString();


                    // 保存数据
                    imgListBean.setJson(json);
                    imgListBean.setByteDataStr(ByteDataStr);

                    lists.add(imgListBean);
                }

                e.onNext(lists);
                e.onComplete();
            }
        });
    }

    /**
     * 1.34 获取洗石场所在地数据
     *
     * @return
     */
    @Override
    public Observable<List<WashStoneSource>> GetWashStoreAddressOptions() {
        return Observable.create(new ObservableOnSubscribe<List<WashStoneSource>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<WashStoneSource>> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.GetWashStoreAddressOptions();

                // 解析数据
                List<WashStoneSource> list = gson.fromJson(result, new TypeToken<List<WashStoneSource>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.37 删除量方图片数据
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteTheAmountOfSideAttachmentByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.DeleteTheAmountOfSideAttachmentByItemID(ItemID);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.37提交验砂图片数据
     *
     * @param json
     * @param ByteDataStr
     * @return
     */
    @Override
    public Observable<Boolean> InsertReceptionSandAttachment(final String json, final String ByteDataStr) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.InsertReceptionSandAttachment(json, ByteDataStr);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.39 删除验砂图片数据
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteReceptionSandAttachmentByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.DeleteReceptionSandAttachmentByItemID(ItemID);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.40 根据进场计划ID获取验砂数据
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    @Override
    public Observable<SupplyDetail> GetReceptionSandBySubcontractorInterimApproachPlanID(final int SubcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<SupplyDetail>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<SupplyDetail> e) throws Exception {
                String result = mRemoteDataSource.GetReceptionSandBySubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID);

                List<SupplyDetail> list = gson.fromJson(result, new TypeToken<List<SupplyDetail>>() {
                }.getType());

                e.onNext(list.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 删除过砂取样
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteSandSamplingNumRecordByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteSandSamplingNumRecordByItemID(ItemID);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.17获取对应的航次完善信息明细
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    @Override
    public Observable<VoyageDetailBean> GetPerfectBoatRecordBySubcontractorInterimApproachPlanID(final int SubcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<VoyageDetailBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<VoyageDetailBean> e) throws Exception {
                String result = mRemoteDataSource.GetPerfectBoatRecordBySubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID);

                List<VoyageDetailBean> list = gson.fromJson(result, new TypeToken<List<VoyageDetailBean>>() {
                }.getType());

                e.onNext(list.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 1.42 获取停工因素选项数据
     *
     * @return
     */
    @Override
    public Observable<List<StopOption>> GetStopOptions() {
        return Observable.create(new ObservableOnSubscribe<List<StopOption>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<StopOption>> e) throws Exception {
                String result = mRemoteDataSource.GetStopOptions();

                List<StopList> lists = gson.fromJson(result, new TypeToken<List<StopList>>() {
                }.getType());

                // 初始化DB
                DataSupport.deleteAll(StopOption.class);

                for (StopList list : lists) {
                    List<StopList.OptionListBean> optionList = list.getOptionList();
                    // 获取类型
                    String type = list.getOptionType();
                    StopOption stopOption1 = new StopOption();
                    stopOption1.setOptionType(type);
                    stopOption1.save();

                    for (StopList.OptionListBean bean : optionList) {
                        StopOption stopOption = new StopOption();
                        stopOption.setOptionType(type);
                        stopOption.setItemID(bean.getItemID());
                        stopOption.setName(bean.getName());
                        stopOption.save();
                    }

                }

                // 查询数据 (排序)
                List<StopOption> all = DataSupport.order("OptionType asc").find(StopOption.class);


                e.onNext(all);
                e.onComplete();
            }
        });
    }

    /**
     * 1.43 提交施工日志（停工）数据
     *
     * @return
     */
    @Override
    public Observable<Boolean> InsertConstructionBoatStopDaily(final int ItemID, final String ShipAccount, final String StartTime, final String EndTime, final String Creator, final int StopTypeID, final String remark) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ItemID", ItemID);
                jsonObject.put("ShipAccount", ShipAccount);
                jsonObject.put("StartTime", StartTime);
                jsonObject.put("EndTime", EndTime);
                jsonObject.put("Creator", Creator);
                jsonObject.put("StopTypeID", StopTypeID);
                jsonObject.put("Remark", remark);

                jsonArray.put(jsonObject);

                String json = jsonArray.toString();

                String result = mRemoteDataSource.InsertConstructionBoatStopDaily(json);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.45 获取当天施工日志（开始时间默认值）
     *
     * @param CurrentDate
     * @param CurrentBoatAccount
     * @return
     */
    @Override
    public Observable<LogCurrentDateBean> GetConstructionBoatDefaultStartTime(final String CurrentDate, final String CurrentBoatAccount) {
        return Observable.create(new ObservableOnSubscribe<LogCurrentDateBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<LogCurrentDateBean> e) throws Exception {
                String result = mRemoteDataSource.GetConstructionBoatDefaultStartTime(CurrentDate, CurrentBoatAccount);

                List<LogCurrentDateBean> list = gson.fromJson(result, new TypeToken<List<LogCurrentDateBean>>() {
                }.getType());

                if (list == null || list.isEmpty()) {
                    e.onError(new RuntimeException("获取数据失败"));
                } else {
                    e.onNext(list.get(0));
                }

                e.onComplete();
            }
        });
    }

    /**
     * 根据账号, 获取抛沙分区
     *
     * @param userAccount
     * @return
     */
    @Override
    public Observable<List<PartitionNum>> getPartitionNum(final String userAccount) {
        return Observable.create(new ObservableOnSubscribe<List<PartitionNum>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<PartitionNum>> e) throws Exception {
                List<PartitionNum> numList = DataSupport.where("userAccount = ?", userAccount).find(PartitionNum.class);

                if (numList.isEmpty()) {
                    // 如果没有数据, 新增一条
                    PartitionNum num = new PartitionNum();
                    num.setUserAccount(userAccount);
                    numList.add(num);

                    DataSupport.saveAll(numList);

                    e.onNext(numList);
                } else {
                    // 有数据, 返回
                    e.onNext(numList);
                }

                e.onComplete();
            }
        });
    }

    /**
     * 获取抛砂分区数据
     *
     * @param account
     * @return
     */
    @Override
    public Observable<PartitionSBBean> getPartitionSBBean(final String account) {
        return Observable.create(new ObservableOnSubscribe<PartitionSBBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<PartitionSBBean> e) throws Exception {
                List<PartitionNum> numList = DataSupport.where("userAccount = ? and num is not null", account).find(PartitionNum.class);

                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < numList.size(); i++) {
                    String num = numList.get(i).getNum();
                    if (TextUtils.isEmpty(num)) {
                        return;
                    }

                    sb.append(num);
                    if (i != (numList.size() - 1)) {
                        sb.append(";");
                    }
                }

                PartitionSBBean sbBean = new PartitionSBBean();
                sbBean.setPartition(sb.toString());
                sbBean.setSize(numList.size());

                e.onNext(sbBean);
                e.onComplete();
            }
        });
    }

    /**
     * 1.46 提交施工日志（抛砂）数据
     *
     * @param json
     * @return
     */
    @Override
    public Observable<Boolean> InsertConstructionBoatThrowingSandRecord(final String json) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.InsertConstructionBoatThrowingSandRecord(json);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 获取抛砂分层
     *
     * @return
     */
    @Override
    public Observable<Boolean> GetConstructionLayerOptions() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.GetConstructionLayerOptions();

                List<Layered> list = gson.fromJson(result, new TypeToken<List<Layered>>() {
                }.getType());

                DataSupport.deleteAll(Layered.class);

                DataSupport.saveAll(list);

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 1.44 获取施工日志（停工）数据
     *
     * @param ItemID
     * @param ShipAccount
     * @param StartTime
     * @param EndTime
     * @param StopTypeID
     * @param Creator
     * @return
     */
    @Override
    public Observable<List<DownLogBean>> GetConstructionBoatStopDaily(final int ItemID, final String ShipAccount, final String StartTime, final String EndTime, final String StopTypeID, final String Creator) {
        return Observable.create(new ObservableOnSubscribe<List<DownLogBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<DownLogBean>> e) throws Exception {
                String result = mRemoteDataSource.GetConstructionBoatStopDaily(ItemID, ShipAccount, StartTime, EndTime, StopTypeID, Creator);

                List<DownLogBean> list = gson.fromJson(result, new TypeToken<List<DownLogBean>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.48	 获取施工日志（抛砂）数据
     *
     * @param ItemID
     * @param ShipAccount
     * @param StartTime
     * @param EndTime
     * @param Creator
     * @return
     */
    @Override
    public Observable<List<ThreadSandLogBean>> GetConstructionBoatThrowingSandList(final int ItemID, final String ShipAccount, final String StartTime, final String EndTime, final String Creator) {
        return Observable.create(new ObservableOnSubscribe<List<ThreadSandLogBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ThreadSandLogBean>> e) throws Exception {
                String result = mRemoteDataSource.GetConstructionBoatThrowingSandList(ItemID, ShipAccount, StartTime, EndTime, Creator);

                List<ThreadSandLogBean> list = gson.fromJson(result, new TypeToken<List<ThreadSandLogBean>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.49 获取可以进行退场申请的数据
     *
     * @return
     */
    @Override
    public Observable<Boolean> GetExitApplicationList(final int jumpWeek, final String account) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                /* 开始时间 */
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
                /* 结束时间 */
                String endDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY, jumpWeek);

                Log.d("==", "请求日期: " + startDay + "-" + endDay);

                // 发送网络请求
                String result = mRemoteDataSource.GetExitApplicationList(account, startDay, endDay);

                // 解析数据
                List<ExitList> lists = gson.fromJson(result, new TypeToken<List<ExitList>>() {
                }.getType());

                /* 3. 初始化数据库 */
                deleteAll(ExitList.class);

                /* 4. 保存数据到数据库 */
                DataSupport.saveAll(lists);

                /* 5. 对数据进行排序 */

                // 1. 创建一个二维集合存放分类好的数据
                List<List<ExitList>> totalLists = new ArrayList<>();

                // 2. 查询数据
                List<ExitList> recordLists = DataSupport.findAll(ExitList.class);

                // 3. 按照时间进行分类
                Set set = new HashSet();
                for (ExitList bean : recordLists) {
                    String planDay = bean.getPlanDay();
                    if (set.contains(planDay)) {

                    } else {
                        set.add(planDay);
                        totalLists.add(DataSupport.where("PlanDay = ?", planDay).find(ExitList.class));
                    }
                }

                // 4. 更新position
                for (List<ExitList> list : totalLists) {
                    for (int i = 1; i <= list.size(); i++) {
                        // 更新数据
                        ExitList record = list.get(i - 1);
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
     * 3.1 修改密码
     *
     * @param LoginName
     * @param OldPassword
     * @param NewPassword
     * @return
     */
    @Override
    public Observable<Boolean> ChangeUserPassword(final String LoginName, final String OldPassword, final String NewPassword) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.ChangeUserPassword(LoginName, OldPassword, NewPassword);

                CommitResultBean commitResultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(commitResultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.51 根据退场ItemID,获取退场申请的数据
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    @Override
    public Observable<ExitDetail> GetExitApplicationRecordByItemID(final int SubcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<ExitDetail>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ExitDetail> e) throws Exception {
                String result = mRemoteDataSource.GetExitApplicationRecordByItemID(SubcontractorInterimApproachPlanID);
                List<ExitDetail> list = gson.fromJson(result, new TypeToken<List<ExitDetail>>() {
                }.getType());

                // 初始化数据库
                DataSupport.deleteAll(ExitDetail.class);

                // 保存数据
                ExitDetail exitDetail = list.get(0);
                exitDetail.save();

                e.onNext(exitDetail);
                e.onComplete();
            }
        });
    }

    /**
     * 1.53 删除退场申请图片数据
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteExitApplicationAttachmentByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteExitApplicationAttachmentByItemID(ItemID);
                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);
                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.52 提交退场申请图片数据
     *
     * @param json
     * @param ByteDataStr
     * @return
     */
    @Override
    public Observable<Boolean> InsertExitApplicationAttachment(final String json, final String ByteDataStr) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.InsertExitApplicationAttachment(json, ByteDataStr);
                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);
                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.50 提交退场申请数据
     *
     * @return
     */
    @Override
    public Observable<Boolean> InsertExitApplicationRecord(final int ItemID, final String ExitTime, final String Creator, final String Remark, final String RemnantAmount, final int SubcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ItemID", ItemID);
                jsonObject.put("ExitTime", ExitTime);
                jsonObject.put("Creator", Creator);
                jsonObject.put("Remark", Remark);
                jsonObject.put("RemnantAmount", RemnantAmount);
                jsonObject.put("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);

                jsonArray.put(jsonObject);

                String json = jsonArray.toString();


                String result = mRemoteDataSource.InsertExitApplicationRecord(json);
                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);
                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 3.2 修改用户信息
     *
     * @param LoginName
     * @param Department
     * @param Email
     * @param title
     * @param Mobile
     * @param TelephoneNumber
     * @param sex
     * @return
     */
    @Override
    public Observable<Boolean> ChangeUserData(final String LoginName, final String Department, final String Email, final String title, final String Mobile, final String TelephoneNumber, final String sex) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 解析json
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("LoginName", LoginName);
                jsonObject.put("Department", Department);
                jsonObject.put("Email", Email);
                jsonObject.put("Sex", (TextUtils.isEmpty(sex) ? "" : sex));
                jsonObject.put("Title", title);
                jsonObject.put("Mobile", Mobile);
                jsonObject.put("TelephoneNumber", TelephoneNumber);

                jsonArray.put(jsonObject);

                String json = jsonArray.toString();

                Log.d("==", "用户信息修改: " + json);

                String result = mRemoteDataSource.ChangeUserData(json);

                CommitResultBean commitResultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(commitResultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 3.3 获取用户信息
     *
     * @param LoginName
     * @return
     */
    @Override
    public Observable<Boolean> GetUserDataByLoginName(final String LoginName) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.GetUserDataByLoginName(LoginName);

                Log.d("==", "用户信息: " + result);

                List<UserInfo> list = gson.fromJson(result, new TypeToken<List<UserInfo>>() {
                }.getType());

                // 初始化数据库
                DataSupport.deleteAll(UserInfo.class);

                // 保持到数据库
                boolean save = list.get(0).save();

                e.onNext(save);
                e.onComplete();
            }
        });
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

