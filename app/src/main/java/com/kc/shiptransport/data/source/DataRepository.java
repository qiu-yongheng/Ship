package com.kc.shiptransport.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.R;
import com.kc.shiptransport.app.App;
import com.kc.shiptransport.data.bean.AppListBean;
import com.kc.shiptransport.data.bean.AttendanceAuditCommitBean;
import com.kc.shiptransport.data.bean.AttendanceTypeBean;
import com.kc.shiptransport.data.bean.CommitImgListBean;
import com.kc.shiptransport.data.bean.CommitResultBean;
import com.kc.shiptransport.data.bean.IsAllowEdit;
import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.LoginResult;
import com.kc.shiptransport.data.bean.PartitionSBBean;
import com.kc.shiptransport.data.bean.RecordListBean;
import com.kc.shiptransport.data.bean.RecordedSandUpdataBean;
import com.kc.shiptransport.data.bean.SampleCommitResult;
import com.kc.shiptransport.data.bean.SampleUpdataBean;
import com.kc.shiptransport.data.bean.CommitPictureBean;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.data.bean.ScannerListBean;
import com.kc.shiptransport.data.bean.ShipBean;
import com.kc.shiptransport.data.bean.StoneSourceBean;
import com.kc.shiptransport.data.bean.SubcontractorBean;
import com.kc.shiptransport.data.bean.SubmitBean;
import com.kc.shiptransport.data.bean.TaskVolumeBean;
import com.kc.shiptransport.data.bean.VoyageDetailBean;
import com.kc.shiptransport.data.bean.acceptanceinfo.AcceptanceInfoBean;
import com.kc.shiptransport.data.bean.album.ConstructionAlbumBean;
import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireBean;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireCommitBean;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireDetailBean;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireItemBean;
import com.kc.shiptransport.data.bean.certificatesupervision.CertificateBean;
import com.kc.shiptransport.data.bean.downlog.DownLogBean;
import com.kc.shiptransport.data.bean.hse.HseCheckAddBean;
import com.kc.shiptransport.data.bean.hse.HseCheckListBean;
import com.kc.shiptransport.data.bean.hse.HseCheckSelectBean;
import com.kc.shiptransport.data.bean.hse.HseDefectAddCommitBean;
import com.kc.shiptransport.data.bean.hse.HseDefectDeadlineBean;
import com.kc.shiptransport.data.bean.hse.HseDefectDetailBean;
import com.kc.shiptransport.data.bean.hse.HseDefectListBean;
import com.kc.shiptransport.data.bean.hse.HseDefectTypeBean;
import com.kc.shiptransport.data.bean.hse.rectification.HseRectificationBean;
import com.kc.shiptransport.data.bean.hse.rectification.HseRectificationCommitBean;
import com.kc.shiptransport.data.bean.img.ImgCommitBean;
import com.kc.shiptransport.data.bean.img.ImgCommitResultBean;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.data.bean.threadsandlog.IsCurrentData;
import com.kc.shiptransport.data.bean.threadsandlog.ThreadSandLogBean;
import com.kc.shiptransport.data.bean.todayplan.TodayPlanBean;
import com.kc.shiptransport.data.bean.violationrecord.ViolationRecordBean;
import com.kc.shiptransport.data.source.remote.RemoteDataSource;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.db.AppList;
import com.kc.shiptransport.db.AttendanceRecordList;
import com.kc.shiptransport.db.AttendanceType;
import com.kc.shiptransport.db.CommitShip;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.RecordList;
import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.db.ScannerImage;
import com.kc.shiptransport.db.StoneSource;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.TaskVolume;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.db.acceptanceevaluation.AcceptanceEvaluationList;
import com.kc.shiptransport.db.acceptancerank.Rank;
import com.kc.shiptransport.db.amount.AmountDetail;
import com.kc.shiptransport.db.amount.AmountOption;
import com.kc.shiptransport.db.analysis.AnalysisDetail;
import com.kc.shiptransport.db.analysis.ProgressTrack;
import com.kc.shiptransport.db.backlog.BackLog;
import com.kc.shiptransport.db.backlog.ListBean;
import com.kc.shiptransport.db.bcf.BCFLog;
import com.kc.shiptransport.db.bcf.BCFShip;
import com.kc.shiptransport.db.bcf.BCFThread;
import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.db.down.StopList;
import com.kc.shiptransport.db.down.StopOption;
import com.kc.shiptransport.db.exitapplication.ExitDetail;
import com.kc.shiptransport.db.exitapplication.ExitList;
import com.kc.shiptransport.db.exitassessor.ExitAssessor;
import com.kc.shiptransport.db.hse.HseCheckShip;
import com.kc.shiptransport.db.hse.HseDefectDeadline;
import com.kc.shiptransport.db.hse.HseDefectType;
import com.kc.shiptransport.db.logmanager.LogManagerList;
import com.kc.shiptransport.db.partition.PartitionNum;
import com.kc.shiptransport.db.pump.PumpShip;
import com.kc.shiptransport.db.sample.SampleData;
import com.kc.shiptransport.db.sample.SampleImageList;
import com.kc.shiptransport.db.sample.SandSamplingNumRecordListBean;
import com.kc.shiptransport.db.ship.Ship;
import com.kc.shiptransport.db.ship.ShipList;
import com.kc.shiptransport.db.supply.SupplyDetail;
import com.kc.shiptransport.db.thread.ThreadShip;
import com.kc.shiptransport.db.threadsand.Layered;
import com.kc.shiptransport.db.threadsand.ThreadDetailInfo;
import com.kc.shiptransport.db.user.Department;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.db.userinfo.UserInfo;
import com.kc.shiptransport.db.versionupdate.VersionUpdate;
import com.kc.shiptransport.db.voyage.PerfectBoatRecordInfo;
import com.kc.shiptransport.db.voyage.WashStoneSource;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.FileUtil;
import com.kc.shiptransport.util.JsonUtil;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.SettingUtil;
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

        Log.d("info", "供应商json: " + subcontractorInfo);

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

    /**
     * 获取所有供应商列表
     *
     * @return
     */
    @Override
    public Observable<List<SubcontractorList>> getSubcontractorList() {
        return Observable.create(new ObservableOnSubscribe<List<SubcontractorList>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<SubcontractorList>> e) throws Exception {
                String result = mRemoteDataSource.getSubcontractorInfo("");

                List<SubcontractorList> list = gson.fromJson(result, new TypeToken<List<SubcontractorList>>() {
                }.getType());

                // 初始化数据库
                DataSupport.deleteAll(SubcontractorList.class);

                // 保存到数据库
                DataSupport.saveAll(list);

                e.onNext(list);
                e.onComplete();
            }
        });
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
        }
    }

    @Override
    public Observable<String> getTitle(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                // 从数据库获取供应商
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
    public Observable<List<WeekTask>> getWeekTask(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<List<WeekTask>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WeekTask>> e) throws Exception {
                // 从数据库获取一周任务分配数据 (每次请求, 初始化表, 所以只有一周的数据)
                List<WeekTask> all = DataSupport.findAll(WeekTask.class);
                e.onNext(all);
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
                    /** 退场申请 只要获取到的数据, 都可以进行申请 */
                    List<ExitAssessor> sampleList = DataSupport.findAll(ExitAssessor.class);

                    for (ExitAssessor sample : sampleList) {
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
                } else if (type == SettingUtil.TYPE_EXIT_ASSESSOR) {
                    /** 退场审核 */
                    List<ExitAssessor> exitList = DataSupport.where("IsSumbitted = ?", "1").find(ExitAssessor.class);

                    for (ExitAssessor exitAssessor : exitList) {
                        switch (Integer.valueOf(exitAssessor.getPosition()) % 7) {
                            case 0:
                                day_0 += Double.valueOf(exitAssessor.getSandSupplyCount());
                                break;
                            case 1:
                                day_1 += Double.valueOf(exitAssessor.getSandSupplyCount());
                                break;
                            case 2:
                                day_2 += Double.valueOf(exitAssessor.getSandSupplyCount());
                                break;
                            case 3:
                                day_3 += Double.valueOf(exitAssessor.getSandSupplyCount());
                                break;
                            case 4:
                                day_4 += Double.valueOf(exitAssessor.getSandSupplyCount());
                                break;
                            case 5:
                                day_5 += Double.valueOf(exitAssessor.getSandSupplyCount());
                                break;
                            case 6:
                                day_6 += Double.valueOf(exitAssessor.getSandSupplyCount());
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
                /* 获取供应商账号 */
                String subcontractorAccount = findAll(Subcontractor.class).get(0).getSubcontractorAccount();
                /* 开始时间 */
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
                /* 结束时间 */
                String endDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY, jumpWeek);

                LogUtil.d("请求日期: " + startDay + "-" + endDay + "\n" + subcontractorAccount);

                /* 1. 获取请求数据 */
                String weekTaskInfo = mRemoteDataSource.getWeekTaskInfo(subcontractorAccount, startDay, endDay);

                LogUtil.d("weektask: " + weekTaskInfo);


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

                LogUtil.d("预验收数据: \n" + weekTaskInfo);

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
     * @param userID
     * @param status
     * @param remark            @return
     */
    @Override
    public Observable<Integer> InsertReceptionSandRecord(final int itemID, final String ReceptionSandTime, final String userID, final int status, final String remark) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ItemID", 0);
                jsonObject.put("SubcontractorInterimApproachPlanID", itemID);
                jsonObject.put("ReceptionSandTime", ReceptionSandTime);
                jsonObject.put("Creator", userID);
                jsonObject.put("Status", status);
                jsonObject.put("Remark", remark);

                jsonArray.put(jsonObject);
                String json = jsonArray.toString();
                LogUtil.d("提交验砂结果: \n" + json);


                String result = mRemoteDataSource.InsertReceptionSandRecord(json);

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
                    //num = DataSupport.where("IsFinish = ?", "0").count(RecordList.class);
                    num = DataSupport.where("IsFullImages = ?", "0").count(RecordList.class);
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
                    /** 退场申请 IsSumbitted申请提交状态 */
                    num = DataSupport.where("IsSumbitted = ?", "0").count(ExitAssessor.class);
                } else if (type == SettingUtil.TYPE_EXIT_ASSESSOR) {
                    /** 退场审核 IsExit审核状态 */
                    //                    num = DataSupport.where("IsExit = ? and IsSumbitted = ?", "0", "1").count(ExitAssessor.class);
                    num = DataSupport.where("IsExit = ?", "0").count(ExitAssessor.class);
                } else if (type == SettingUtil.TYPE_ACCEPT) {
                    /** 验收 1通过, 0保存, -1不通过(不显示) */
                    // TODO
                    num = DataSupport.where("PreAcceptanceEvaluationStatus = ?", "0").count(WeekTask.class);
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
     * @param ItemID                             评价ID
     * @param MaterialIntegrity                  材料完整性
     * @param MaterialTimeliness                 材料及时性
     * @param PreAcceptanceTime                  时间
     * @param SubcontractorInterimApproachPlanID 任务ID
     * @param value
     * @return
     */
    @Override
    public Observable<Integer> InsertPreAcceptanceEvaluation(final int ItemID,
                                                             final int MaterialIntegrity,
                                                             final int MaterialTimeliness,
                                                             final String PreAcceptanceTime,
                                                             final int SubcontractorInterimApproachPlanID,
                                                             final AcceptanceInfoBean value,
                                                             final int Status,
                                                             final String Remark) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                String creator = DataSupport.findAll(User.class).get(0).getUserID();

                /* 1. 创建对象, 封装要提交的数据 */
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ItemID", ItemID == 0 ? "" : String.valueOf(ItemID));
                jsonObject.put("MaterialIntegrity", String.valueOf(MaterialIntegrity));
                jsonObject.put("MaterialTimeliness", String.valueOf(MaterialTimeliness));
                jsonObject.put("PreAcceptanceTime", PreAcceptanceTime);
                jsonObject.put("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);
                jsonObject.put("ShipAccount", value.getShipAccount());
                jsonObject.put("SubcontractorAccount", value.getSubcontractorAccount());
                jsonObject.put("Creator", creator);
                jsonObject.put("Status", Status);
                jsonObject.put("Remark", Remark);
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
     * 获取供应商信息
     *
     * @param username 供应商账号名, 如果填null, 获取所有供应商列表
     * @return
     */
    @Override
    public Observable<Boolean> getSubcontractor(final String username) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                // 1. 获取数据, 缓存到数据库
                String subcontractorInfo = mRemoteDataSource.getSubcontractorInfo(username);
                Log.d("info", "供应商json: " + subcontractorInfo);
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
     * @param username 供应商账号名, 如果填null, 获取所有船舶列表
     * @return
     */
    @Override
    public Observable<Boolean> getShip(final String username) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                // 2. 获取数据, 缓存到数据库
                String shipInfo = mRemoteDataSource.getShipInfo(username);
                LogUtil.json(shipInfo);
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
                LogUtil.json(loginInfo);

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
     * 获取供应商预计划量
     *
     * @return
     */
    @Override
    public Observable<Integer> getTaskVolume(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                /* 获取供应商账号 */
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
                LogUtil.json(appList);
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
                            // 供应商进场计划
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
                            // 供应商航次信息完善
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 15:
                            // 供应商航次完善扫描件
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
                        case 22:
                            // 供砂进度跟踪
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 23:
                            // 预验收反馈
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 24:
                            // 供应商排行
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 25:
                            // 退场审核
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 26:
                            // 退场反馈
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 27:
                            // 明日来船计划
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        case 28:
                            // BCF来船
                            list.setIcon_id(R.mipmap.plan);
                            break;
                        default:
                            // 设置默认图标
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
     * 获取供应商名字
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
                                                           final String Creator,
                                                           final float LaserQuantitySand,
                                                           final String TheAmountOfPersonnelID,
                                                           final String TheAmountOfType,
                                                           final int IsSumbitted,
                                                           final String Remark) {
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
                jsonObject.put("LaserQuantitySand", LaserQuantitySand);
                jsonObject.put("TheAmountOfPersonnelAccount", TheAmountOfPersonnelID);
                jsonObject.put("TheAmountOfType", TheAmountOfType);
                jsonObject.put("IsSumbitted", IsSumbitted);
                jsonObject.put("Remark", Remark);


                jsonArray.put(jsonObject);


                String json = jsonArray.toString();
                LogUtil.d(SubcontractorInterimApproachPlanID + "量方提交数据: \n" + json);


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
                        } else if (split.length == 1) {
                            jsonObject.put(columnsBean.getColumnName(), split[0]);
                        } else {
                            jsonObject.put(columnsBean.getColumnName(), "");
                        }
                    } else {
                        jsonObject.put(columnsBean.getColumnName(), columnsBean.getValue());
                    }
                }

                jsonObject.put("Creator", findAll(Subcontractor.class).get(0).getSubcontractorAccount());

                // 保存 or 提交
                jsonObject.put("IsSumbitted", bean.getIsSumbitted());

                jsonArray.put(jsonObject);

                String json = jsonArray.toString();

                LogUtil.d("信息完善请求json: " + json);


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
    public Observable<SandSample> getSampleTaskForItemID(final int position) {
        return Observable.create(new ObservableOnSubscribe<SandSample>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<SandSample> e) throws Exception {
                List<SandSample> sandSamples = DataSupport.where("ItemID = ?", String.valueOf(position)).find(SandSample.class);
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
     * 获取当前登录的供应商信息
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
                    e.onError(new RuntimeException("获取供应商信息失败"));
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
                    for (int i = 0; i < list.size(); i++) {
                        ConstructionBoat boat = list.get(i);
                        boat.setPosition(i + 1);
                        boat.save();
                    }
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
    public Observable<Boolean> getWeekTaskSort(final int typeSupply, final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String condition = "";
                switch (typeSupply) {
                    case SettingUtil.TYPE_AMOUNT:
                        /** 量方管理, 验收后才能操作 */
                        condition = "PreAcceptanceEvaluationStatus != 1";
                        break;
                    case SettingUtil.TYPE_SUPPLY:
                        /** 验砂管理, 量方后才能操作*/
                        // TODO: 需求, 量方
                        condition = "PreAcceptanceEvaluationStatus != 1";
                        break;
                }
                // 1. 删除未验收的数据 PreAcceptanceEvaluationStatus != 1
                int i = DataSupport.deleteAll(WeekTask.class, condition);

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
                    record.setIsFullImages(bean.getIsFullImages());
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
     * 获取供应商对应进场计划
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

                Log.d("==", "验砂取样数据: " + result);

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


                e.onNext(all);
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

                // 发送网络请求, 提交图片
                String result = mRemoteDataSource.UploadFile(commitList.getByteDataStr(), commitList.getSuffixName(), commitList.getFileName());

                // 解析返回的数据
                SampleCommitResult commitResult = gson.fromJson(result, SampleCommitResult.class);

                // 更新图片网络路径
                if (commitResult.getMessage() == 1) {
                    commitList.setNetPath(commitResult.getFilePath());
                    commitList.save();
                }

                e.onNext(commitResult.getMessage() == 1);

            }
        });
    }

    /**
     * 根据position获取过砂记录
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<RecordList> getRecordListForItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<RecordList>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<RecordList> e) throws Exception {
                List<RecordList> recordLists = DataSupport.where("ItemID = ?", String.valueOf(itemID)).find(RecordList.class);
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
     * 根据进场ID, 获取供应商航次完善扫描件类型数据
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
                LogUtil.d("根据进场ID, 获取供应商航次完善扫描件类型数据: \n" + result);

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

                LogUtil.d("提交过砂记录: \n" + jsonArray.toString());

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

                LogUtil.d(SubcontractorInterimApproachPlanID + "\n根据进场计划ID获取过砂记录明细（多条）\n" + result);

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
    public Observable<List<AttendanceRecordList>> GetAttendanceRecords(final int itemID, final String account, final String startDate, final String endDate, final String Auditor) {
        return Observable.create(new ObservableOnSubscribe<List<AttendanceRecordList>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AttendanceRecordList>> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.GetAttendanceRecords(itemID, account, startDate, endDate, Auditor);

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
     * 1.23根据进场计划ID获取验砂取样信息明细
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    @Override
    public Observable<SampleData> GetSandSamplingBySubcontractorInterimApproachPlanID(final int SubcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<SampleData>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<SampleData> e) throws Exception {
                // 根据进场ID发送网络请求
                String result = mRemoteDataSource.GetSandSamplingBySubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID);

                LogUtil.d(SubcontractorInterimApproachPlanID + "\n1.23根据进场计划ID获取验砂取样信息明细: \n" + result);

                // 解析数据
                List<SampleData> list = gson.fromJson(result, new TypeToken<List<SampleData>>() {
                }.getType());

                SampleData sampleData = list.get(0);
                sampleData.setSubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID);

                // TODO: 保存到数据库
                DataSupport.deleteAll(SampleData.class);
                DataSupport.deleteAll(SandSamplingNumRecordListBean.class);
                List<SandSamplingNumRecordListBean> numRecordList = sampleData.getSandSamplingNumRecordList();
                DataSupport.saveAll(numRecordList);
                sampleData.save();

                e.onNext(sampleData);
                e.onComplete();
            }
        });
    }

    /**
     * 提交验砂取样数据
     *
     * @return
     */
    @Override
    public Observable<Boolean> InsertSandSampling() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                /** 1. 创建要提交的对象 */
                SampleUpdataBean sampleUpdataBean = new SampleUpdataBean();

                /** 2. 查询要提交的基本数据 */
                List<SampleData> sampleDatas = DataSupport.where().find(SampleData.class, true);
                SampleData bean = sampleDatas.get(0);

                /** 3. 设置数据 */
                // 设置条目ID
                sampleUpdataBean.setItemID(String.valueOf(bean.getItemID()));
                // 设置进场ID
                sampleUpdataBean.setSubcontractorInterimApproachPlanID(String.valueOf(bean.getSubcontractorInterimApproachPlanID()));
                // 设置施工船舶
                sampleUpdataBean.setConstructionBoatAccount(bean.getConstructionBoatAccount());
                // 设置创建者
                sampleUpdataBean.setCreator(DataSupport.findAll(Subcontractor.class).get(0).getSubcontractorAccount());
                // 设置BATCH
                sampleUpdataBean.setBatch(bean.getBatch());
                // 设置NQAA
                sampleUpdataBean.setNQAA(bean.getNQAA());
                // 设置取样时间
                sampleUpdataBean.setSandSamplingDate(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));

                // 创建取样编号集合 (空集合)
                sampleUpdataBean.setSandSamplingNumRecordList(new ArrayList<SampleUpdataBean.SandSamplingNumRecordListBean>());

                // 遍历SampleShowDatesBean, 录入数据 (获取保存的取样编号数据)
                List<SandSamplingNumRecordListBean> sandSamplingNumRecordList = DataSupport.order("SamplingNum asc").find(SandSamplingNumRecordListBean.class);
                for (int i = 0; i < sandSamplingNumRecordList.size(); i++) {
                    // 创建一个取样编号 (容器)
                    SampleUpdataBean.SandSamplingNumRecordListBean numRecordListBean = new SampleUpdataBean.SandSamplingNumRecordListBean();
                    // 设置编号条目ID
                    numRecordListBean.setItemID(String.valueOf(sandSamplingNumRecordList.get(i).getItemID()));
                    // 设置验砂取样条目ID
                    numRecordListBean.setSandSamplingID(String.valueOf(sandSamplingNumRecordList.get(i).getSandSamplingID()));
                    // 设置编号
                    numRecordListBean.setSamplingNum(sandSamplingNumRecordList.get(i).getSamplingNum());
                    // 设置施工船舶
                    numRecordListBean.setConstructionBoatAccount(sandSamplingNumRecordList.get(i).getConstructionBoatAccount());

                    // 创建图片集合 (空集合)
                    numRecordListBean.setSandSamplingAttachmentRecordList(new ArrayList<SampleUpdataBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean>());

                    // 根据进场ID, 取样编号ID, 取样列表position, 查询待提交的图片数据
                    List<SampleImageList> imageLists = DataSupport.where("SandSamplingID = ? and SandSamplingNumID = ? and position = ?",
                            String.valueOf(bean.getSubcontractorInterimApproachPlanID()),
                            String.valueOf(sandSamplingNumRecordList.get(i).getItemID()),
                            String.valueOf(i))
                            .find(SampleImageList.class);

                    /** 保存图片到指定取样编号中 */
                    for (SampleImageList imageList : imageLists) {
                        // 新建一个图片对象, 保存要提交的数据
                        SampleUpdataBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean imgBean = new SampleUpdataBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean();
                        // 条目ID
                        imgBean.setItemID(String.valueOf(imageList.getItemID()));
                        // 进场ID
                        imgBean.setSandSamplingID(String.valueOf(imageList.getSandSamplingID()));
                        // 编号ID
                        imgBean.setSandSamplingNumID(String.valueOf(imageList.getSandSamplingNumID()));
                        // 文件名
                        imgBean.setFileName(imageList.getFileName());
                        // 后缀名
                        imgBean.setSuffixName(imageList.getSuffixName());
                        // 网络路径
                        imgBean.setFilePath(imageList.getNetPath());
                        // 施工船
                        imgBean.setConstructionBoatAccount(imageList.getConstructionBoatAccount());

                        // 把图片保存到集合中
                        numRecordListBean.getSandSamplingAttachmentRecordList().add(imgBean);
                    }

                    /** 把验砂取样编号数据保存到集合中 */
                    sampleUpdataBean.getSandSamplingNumRecordList().add(numRecordListBean);
                }

                // 把对象解析成json数据
                String json = gson.toJson(sampleUpdataBean);

                LogUtil.d("验砂取样提交总json: \n" + json);

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
    public Observable<Boolean> InsertSubcontractorPerfectBoatScannerAttachment(final CommitPictureBean bean) {
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

                LogUtil.d("提交扫描件图片: " + jsonArray.toString());

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
    public Observable<List<CommitPictureBean>> getScanImgList(final ImageMultipleResultEvent imageMultipleResultEvent, final int subID, final int typeID, final String shipAccount) {
        return Observable.create(new ObservableOnSubscribe<List<CommitPictureBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<CommitPictureBean>> e) throws Exception {
                // 多选回调
                List<MediaBean> result = imageMultipleResultEvent.getResult();
                // 获取供应商账号
                Subcontractor subcontractor = DataSupport.findAll(Subcontractor.class).get(0);
                // 创建任务队列
                List<CommitPictureBean> commitBeanList = new ArrayList<CommitPictureBean>();

                // 保存图片数据到队列中
                for (MediaBean bean : result) {
                    CommitPictureBean commitBean = new CommitPictureBean();
                    // 条目ID, 默认0
                    commitBean.setItemID(0);
                    // 进场ID
                    commitBean.setSubcontractorInterimApproachPlanID(subID);
                    // 类型ID
                    commitBean.setSubcontractorPerfectBoatScannerAttachmentTypeID(typeID);
                    // 供应商账号
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

                    LogUtil.d("扫描件图片路径: " + bean.getOriginalPath());

                    File file = new File(bean.getOriginalPath());
                    LogUtil.d(file.getAbsolutePath() + "原始长度: " + file.length()/1024 + "kb");
                    File tagFile = Luban.with(App.getAppContext()).load(file).get();
                    LogUtil.d(tagFile.getAbsolutePath() + "压缩长度: " + tagFile.length()/1024 + "kb");
                    byte[] bytes = FileUtil.File2byte(tagFile);
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
                LogUtil.d("进场计划ID: " + subID + "\n图片类型ID: " + typeID + "\n图片信息: " + result);

                // 解析数据
                List<ScannerImgListByTypeBean> list = gson.fromJson(result, new TypeToken<List<ScannerImgListByTypeBean>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.30	 删除供应商航次完善扫描件表(图片信息)
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

                LogUtil.d(SubcontractorInterimApproachPlanID + "\n1.35 获取量方信息数据: \n" + result);

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
                    LogUtil.d(file.getAbsolutePath() + "原始长度: " + file.length()/1024 + "kb");
                    File tagFile = Luban.with(App.getAppContext()).load(file).get();
                    LogUtil.d(tagFile.getAbsolutePath() + "压缩长度: " + tagFile.length()/1024 + "kb");
                    byte[] bytes = FileUtil.File2byte(tagFile);
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

                LogUtil.d(SubcontractorInterimApproachPlanID + "\n1.40 根据进场计划ID获取验砂数据: \n" + result);

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

                LogUtil.d(SubcontractorInterimApproachPlanID + " 信息完善: \n" + result);

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
    public Observable<Boolean> InsertConstructionBoatStopDaily(final int ItemID, final String ShipAccount, final String StartTime, final String EndTime, final String Creator, final int StopTypeID, final String remark, final String pumpShipID) {
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
                jsonObject.put("PumpShipID", pumpShipID);

                jsonArray.put(jsonObject);

                String json = jsonArray.toString();

                LogUtil.d("1.43 提交施工日志（停工）数据json: \n" + json);

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
                List<PartitionNum> numList = DataSupport.where("userAccount = ? and num is not null and num != ?", userAccount, "").find(PartitionNum.class);

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
                List<PartitionNum> numList = DataSupport.where("userAccount = ? and num is not null and num != ?", account, "").find(PartitionNum.class);

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

                LogUtil.d("1.44 获取施工日志（停工）数据result: \n" + result);

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

                LogUtil.d(ItemID + "\n 1.48 获取施工日志（抛砂）数据result: \n" + result);

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
    public Observable<Boolean> GetExitApplyPendingApplicationList(final int jumpWeek, final String account) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                /* 开始时间 */
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
                /* 结束时间 */
                String endDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY, jumpWeek);

                JSONObject root = new JSONObject();
                JSONObject Condition = new JSONObject();

                JSONArray Column = new JSONArray();

                // 供应商账号
                JSONObject object1 = new JSONObject();
                object1.put("Name", "SubcontractorAccount");
                object1.put("Type", "string");
                object1.put("Format", "Equal");
                object1.put("Value", account);

                // 退场时间时间
                JSONObject object2 = new JSONObject();
                object2.put("Name", "PlanDay");
                object2.put("Type", "datetime");

                JSONArray array2 = new JSONArray();

                JSONObject object21 = new JSONObject();
                object21.put("Min", startDay);
                JSONObject object22 = new JSONObject();
                object22.put("Max", endDay);

                array2.put(object21);
                array2.put(object22);

                object2.put("Value", array2);

                // 保存2个对象到object
                if (!TextUtils.isEmpty(account)) {
                    Column.put(object1);
                }
                if (!TextUtils.isEmpty(startDay) || !TextUtils.isEmpty(endDay)) {
                    Column.put(object2);
                }

                // 保存object到Condition
                Condition.put("Column", Column);

                // 保存到root
                root.put("Condition", Condition);

                String json = root.toString();


                LogUtil.d(account + "\n1.49 获取可以进行退场申请的数据 json: \n" + json);

                // 发送网络请求
                String result = mRemoteDataSource.GetExitApplyPendingApplicationList(10000, 1, json);

                LogUtil.d("退场申请数据: \n" + result);

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
    public Observable<ExitDetail> GetExitApplicationRecordBySubcontractorInterimApproachPlanID(final int SubcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<ExitDetail>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ExitDetail> e) throws Exception {
                String result = mRemoteDataSource.GetExitApplicationRecordBySubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID);

                LogUtil.d(SubcontractorInterimApproachPlanID + "\n1.51 根据退场ItemID,获取退场申请的数据: \n" + result);
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
    public Observable<Boolean> InsertExitApplicationRecord(final int ItemID,
                                                           final String ExitTime,
                                                           final String Creator,
                                                           final String Remark,
                                                           final String RemnantAmount,
                                                           final int SubcontractorInterimApproachPlanID,
                                                           final int isSumbitted,
                                                           final int status) {
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
                jsonObject.put("IsSumbitted", isSumbitted);
                jsonObject.put("Status", status);


                jsonArray.put(jsonObject);

                String json = jsonArray.toString();

                LogUtil.d(SubcontractorInterimApproachPlanID + "\n1.50 提交退场申请数据json: \n" + json);


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
     * @param DisplayName
     * @param DepartmentID
     * @param Email
     * @param title
     * @param Mobile
     * @param TelephoneNumber
     * @param sex
     * @return
     */
    @Override
    public Observable<Boolean> ChangeUserData(final String LoginName, final String DisplayName, final int DepartmentID, final String Email, final String title, final String Mobile, final String TelephoneNumber, final String sex) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 解析json
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("LoginName", LoginName);
                jsonObject.put("DisplayName", DisplayName);
                jsonObject.put("DepartmentID", DepartmentID);
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

                LogUtil.json(result);

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
     * 3.4 获取部门信息
     *
     * @return
     */
    @Override
    public Observable<Boolean> GetDepartmentsOptions() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.GetDepartmentsOptions();

                LogUtil.json(result);

                List<Department> list = gson.fromJson(result, new TypeToken<List<Department>>() {
                }.getType());

                // 初始化数据库表
                DataSupport.deleteAll(Department.class);

                // 保存到数据库
                DataSupport.saveAll(list);

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 1.18 获取对应的过砂记录信息明细
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<RecordedSandShowList> GetOverSandRecordByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<RecordedSandShowList>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<RecordedSandShowList> e) throws Exception {
                String result = mRemoteDataSource.GetOverSandRecordByItemID(itemID);

                List<RecordedSandShowList> lists = gson.fromJson(result, new TypeToken<List<RecordedSandShowList>>() {
                }.getType());

                e.onNext(lists.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 2.5 获取供应商进场计划进度跟踪
     *
     * @param SubcontractorAccount
     * @param ShipName
     * @param StartDate
     * @param EndDate
     * @return
     */
    @Override
    public Observable<List<ProgressTrack>> GetSubcontractorInterimApproachPlanProgressTracking(final String SubcontractorAccount, final String ShipName, final String StartDate, final String EndDate) {
        return Observable.create(new ObservableOnSubscribe<List<ProgressTrack>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ProgressTrack>> e) throws Exception {
                String result = mRemoteDataSource.GetSubcontractorInterimApproachPlanProgressTracking(SubcontractorAccount, ShipName, StartDate, EndDate);

                List<ProgressTrack> list = gson.fromJson(result, new TypeToken<List<ProgressTrack>>() {
                }.getType());

                DataSupport.deleteAll(ProgressTrack.class);
                DataSupport.saveAll(list);

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 2.4 根据进场计划ID，获取供砂过程总表
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    @Override
    public Observable<AnalysisDetail> GetAllDetailBySubcontractorInterimApproachPlanID(final int SubcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<AnalysisDetail>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<AnalysisDetail> e) throws Exception {
                String result = mRemoteDataSource.GetAllDetailBySubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID);

                LogUtil.d(SubcontractorInterimApproachPlanID + "供砂进度跟踪: \n" + result);

                List<AnalysisDetail> list = gson.fromJson(result, new TypeToken<List<AnalysisDetail>>() {
                }.getType());

                e.onNext(list.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 1.54 获取供应商预验收评价数据
     *
     * @param PageSize
     * @param PageCount
     * @param startTime
     * @param endTime
     * @param subShipAccount
     * @return
     */
    @Override
    public Observable<List<AcceptanceEvaluationList>> GetPreAcceptanceEvaluationList(final int PageSize, final int PageCount, final String startTime, final String endTime, final String subShipAccount) {
        return Observable.create(new ObservableOnSubscribe<List<AcceptanceEvaluationList>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AcceptanceEvaluationList>> e) throws Exception {
                JSONObject root = new JSONObject();
                JSONObject Condition = new JSONObject();

                JSONArray Column = new JSONArray();

                // 供应商账号
                JSONObject object1 = new JSONObject();
                User user = DataSupport.findAll(User.class).get(0);
                object1.put("Name", "SubcontractorAccount");
                object1.put("Type", "string");
                object1.put("Format", "Equal");
                object1.put("Value", user.getUserID());

                // 验收时间
                JSONObject object2 = new JSONObject();
                object2.put("Name", "PreAcceptanceTime");
                object2.put("Type", "datetime");

                JSONArray array2 = new JSONArray();

                JSONObject object21 = new JSONObject();
                object21.put("Min", startTime);
                JSONObject object22 = new JSONObject();
                object22.put("Max", endTime);

                array2.put(object21);
                array2.put(object22);

                object2.put("Value", array2);

                // 供砂船舶
                JSONObject object3 = new JSONObject();
                object3.put("Name", "ShipName"); // TODO 需要修改
                object3.put("Type", "string");
                object3.put("Format", "Equal");
                object3.put("Value", subShipAccount);

                // 保存3个对象到object
                Column.put(object1);
                if (!TextUtils.isEmpty(startTime) || !TextUtils.isEmpty(endTime)) {
                    Column.put(object2);
                }

                if (!TextUtils.isEmpty(subShipAccount)) {
                    Column.put(object3);
                }

                // 保存object到Condition
                Condition.put("Column", Column);

                // 保存到root
                root.put("Condition", Condition);

                String conditionJson = root.toString();

                Log.d("==", "申请获取供应商预验收评价数据json: " + conditionJson);

                String result = mRemoteDataSource.GetPreAcceptanceEvaluationList(PageSize, PageCount, conditionJson);

                Log.d("==", "供应商预验收评价: " + result);
                List<AcceptanceEvaluationList> list = gson.fromJson(result, new TypeToken<List<AcceptanceEvaluationList>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.55 获取供应商评分排行榜
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Observable<List<Rank>> GetSubcontractorPreAcceptanceEvaluationRanking(final String startTime, final String endTime) {
        return Observable.create(new ObservableOnSubscribe<List<Rank>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Rank>> e) throws Exception {
                String json = "{\"Condition\":{\"Column\":[{\"Name\":\"PlanDay\",\"Type\":\"datetime\",\"Value\":[{\"Min\":\"" + startTime + "\"},{\"Max\":\"" + endTime + "\"}]}]}}";
                String result = mRemoteDataSource.GetSubcontractorPreAcceptanceEvaluationRanking(json);
                Log.d("==", "供应商评分排行: " + result);


                List<Rank> list = gson.fromJson(result, new TypeToken<List<Rank>>() {
                }.getType());

                DataSupport.deleteAll(Rank.class);
                DataSupport.saveAll(list);

                List<Rank> lists = DataSupport.order("AvgTotalScore desc").find(Rank.class);

                e.onNext(lists);
                e.onComplete();
            }
        });
    }

    /**
     * 4.1 获取当前app最新版本
     *
     * @param version
     * @return
     */
    @Override
    public Observable<VersionUpdate> GetNewVersion(final int version) {
        return Observable.create(new ObservableOnSubscribe<VersionUpdate>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<VersionUpdate> e) throws Exception {
                String result = mRemoteDataSource.GetNewVersion(String.valueOf(version));

                List<VersionUpdate> list = gson.fromJson(result, new TypeToken<List<VersionUpdate>>() {
                }.getType());

                e.onNext(list.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 3.5 获取所有用户信息（通讯录数据源）
     *
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     */
    @Override
    public Observable<List<Contacts>> GetMembers(final int PageSize, final int PageCount, final String ConditionJson) {
        return Observable.create(new ObservableOnSubscribe<List<Contacts>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Contacts>> e) throws Exception {
                String result = mRemoteDataSource.GetMembers(PageSize, PageCount, ConditionJson);

                Log.d("==", "通讯录: " + result);
                List<Contacts> list = gson.fromJson(result, new TypeToken<List<Contacts>>() {
                }.getType());

                DataSupport.deleteAll(Contacts.class);
                DataSupport.saveAll(list);

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.56 提交分包商航次完善扫描件（用于确认提交）
     *
     * @param ItemID
     * @param Creator
     * @param SubcontractorInterimApproachPlanID
     * @param IsSumbitted
     * @param Remark
     * @return
     */
    @Override
    public Observable<Boolean> InsertSubcontractorPerfectBoatScannerRecord(final String ItemID, final String Creator, final int SubcontractorInterimApproachPlanID, final int IsSumbitted, final String Remark) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ItemID", ItemID);
                jsonObject.put("Creator", Creator);
                jsonObject.put("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);
                jsonObject.put("IsSumbitted", IsSumbitted);
                jsonObject.put("Remark", Remark);

                jsonArray.put(jsonObject);

                String json = jsonArray.toString();

                LogUtil.d(json);

                String result = mRemoteDataSource.InsertSubcontractorPerfectBoatScannerRecord(json);

                LogUtil.d("1.56 提交分包商航次完善扫描件result: " + result);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.61 判断是否允许新增或者修改进场计划数据
     *
     * @param Date
     * @return
     */
    @Override
    public Observable<Boolean> IsAllowEditPlanData(final String Date) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.IsAllowEditPlanData(Date);
                IsAllowEdit isAllowEdit = gson.fromJson(result, IsAllowEdit.class);
                e.onNext(isAllowEdit.getReturnValue() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.57 根据进场计划ID获取分包商预验收评价数据
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    @Override
    public Observable<AcceptanceInfoBean> GetSandSubcontractorPreAcceptanceEvaluationBySubcontractorInterimApproachPlanID(final int SubcontractorInterimApproachPlanID) {
        return Observable.create(new ObservableOnSubscribe<AcceptanceInfoBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<AcceptanceInfoBean> e) throws Exception {
                String result = mRemoteDataSource.GetSandSubcontractorPreAcceptanceEvaluationBySubcontractorInterimApproachPlanID(SubcontractorInterimApproachPlanID);
                LogUtil.d("1.57 根据进场计划ID获取分包商预验收评价数据: \n进场ID: " + SubcontractorInterimApproachPlanID + "\n" + result);

                List<AcceptanceInfoBean> list = gson.fromJson(result, new TypeToken<List<AcceptanceInfoBean>>() {
                }.getType());

                e.onNext(list.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 1.60 获取量方人员信息数据
     *
     * @return
     */
    @Override
    public Observable<List<AmountOption>> GetTheAmountOfPersonnelOptions() {
        return Observable.create(new ObservableOnSubscribe<List<AmountOption>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AmountOption>> e) throws Exception {
                String result = mRemoteDataSource.GetTheAmountOfPersonnelOptions();
                LogUtil.d("1.60 获取量方人员信息数据: \n" + result);
                List<AmountOption> list = gson.fromJson(result, new TypeToken<List<AmountOption>>() {
                }.getType());

                DataSupport.saveAll(list);
                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.58 提交验砂管理船名照片(图片信息)
     *
     * @param json
     * @param ByteDataStr
     * @return
     */
    @Override
    public Observable<Boolean> InsertReceptionSandBoatNameAttachment(final String json, final String ByteDataStr) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.InsertReceptionSandBoatNameAttachment(json, ByteDataStr);

                LogUtil.d("1.58 提交验砂管理船名照片(图片信息): \n" + result);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);
                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.59 删除验砂管理船名照片(图片信息)
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteReceptionSandBoatNameAttachmentByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteReceptionSandBoatNameAttachmentByItemID(ItemID);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);
                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.63 获取可以进行退场审核的数据
     *
     * @param PageSize
     * @param PageCount
     * @param jumpWeek
     * @param account
     * @return
     */
    @Override
    public Observable<Boolean> GetExitAuditPendingApplicationRecords(final int PageSize, final int PageCount, final int jumpWeek, final String account) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                 /* 开始时间 */
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
                /* 结束时间 */
                String endDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY, jumpWeek);

                JSONObject root = new JSONObject();
                JSONObject Condition = new JSONObject();

                JSONArray Column = new JSONArray();

                // 供应商账号
                JSONObject object1 = new JSONObject();
                object1.put("Name", "SubcontractorAccount");
                object1.put("Type", "string");
                object1.put("Format", "Equal");
                object1.put("Value", account);

                // 退场时间时间
                JSONObject object2 = new JSONObject();
                object2.put("Name", "PlanDay");
                object2.put("Type", "datetime");

                JSONArray array2 = new JSONArray();

                JSONObject object21 = new JSONObject();
                object21.put("Min", startDay);
                JSONObject object22 = new JSONObject();
                object22.put("Max", endDay);

                array2.put(object21);
                array2.put(object22);

                object2.put("Value", array2);

                // 保存2个对象到object
                if (!TextUtils.isEmpty(account)) {
                    Column.put(object1);
                }
                if (!TextUtils.isEmpty(startDay) || !TextUtils.isEmpty(endDay)) {
                    Column.put(object2);
                }

                // 保存object到Condition
                Condition.put("Column", Column);

                // 保存到root
                root.put("Condition", Condition);

                String json = root.toString();

                LogUtil.d(account + "\n1.63 获取可以进行退场审核的数据 json: \n" + json);

                String result = mRemoteDataSource.GetExitAuditPendingApplicationRecords(PageSize, PageCount, json);

                LogUtil.d("1.63 获取可以进行退场审核的数据: \n" + result);

                List<ExitAssessor> lists = gson.fromJson(result, new TypeToken<List<ExitAssessor>>() {
                }.getType());




                /* 3. 初始化数据库 */
                deleteAll(ExitAssessor.class);

                /* 4. 保存数据到数据库 */
                DataSupport.saveAll(lists);

                /* 5. 对数据进行排序 */

                // 1. 创建一个二维集合存放分类好的数据
                List<List<ExitAssessor>> totalLists = new ArrayList<>();

                // 2. 查询数据
                List<ExitAssessor> recordLists = DataSupport.findAll(ExitAssessor.class);

                // 3. 按照时间进行分类
                Set set = new HashSet();
                for (ExitAssessor bean : recordLists) {
                    String planDay = bean.getPlanDay();
                    if (set.contains(planDay)) {

                    } else {
                        set.add(planDay);
                        totalLists.add(DataSupport.where("PlanDay = ?", planDay).find(ExitAssessor.class));
                    }
                }

                // 4. 更新position
                for (List<ExitAssessor> list : totalLists) {
                    for (int i = 1; i <= list.size(); i++) {
                        // 更新数据
                        ExitAssessor record = list.get(i - 1);
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
     * 替换成: 1.49 获取退场数据(退场申请，退场审核列表)
     *
     * @param PageSize
     * @param PageCount
     * @param startTime
     * @param endTime
     * @param shipAccount
     * @return
     */
    @Override
    public Observable<List<ExitAssessor>> GetExitAuditedApplicationRecords(final int PageSize, final int PageCount, final String startTime, final String endTime, final String shipAccount) {
        return Observable.create(new ObservableOnSubscribe<List<ExitAssessor>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ExitAssessor>> e) throws Exception {
                JSONObject root = new JSONObject();
                JSONObject Condition = new JSONObject();

                JSONArray Column = new JSONArray();

                // 供应商账号
                JSONObject object1 = new JSONObject();
                User user = DataSupport.findAll(User.class).get(0);
                object1.put("Name", "SubcontractorAccount");
                object1.put("Type", "string");
                object1.put("Format", "Equal");
                object1.put("Value", user.getUserID());

                // 验收时间
                JSONObject object2 = new JSONObject();
                object2.put("Name", "PlanDay");
                object2.put("Type", "datetime");

                JSONArray array2 = new JSONArray();

                JSONObject object21 = new JSONObject();
                object21.put("Min", startTime);
                JSONObject object22 = new JSONObject();
                object22.put("Max", endTime);

                array2.put(object21);
                array2.put(object22);

                object2.put("Value", array2);

                // 供砂船舶
                JSONObject object3 = new JSONObject();
                object3.put("Name", "ShipName"); // TODO 需要修改
                object3.put("Type", "string");
                object3.put("Format", "Equal");
                object3.put("Value", shipAccount);

                // 保存3个对象到object
                Column.put(object1);
                if (!TextUtils.isEmpty(startTime) || !TextUtils.isEmpty(endTime)) {
                    Column.put(object2);
                }

                if (!TextUtils.isEmpty(shipAccount)) {
                    Column.put(object3);
                }

                // 保存object到Condition
                Condition.put("Column", Column);

                // 保存到root
                root.put("Condition", Condition);

                String json = root.toString();

                LogUtil.d("pageSize: " + PageSize + ", pageCount: " + PageCount + ", startTime: " + startTime + ", endTime: " + endTime + ", shipAccount: " + shipAccount + "\njson: " + json);

                // 1.49 获取退场数据(退场申请，退场审核列表)
                String result = mRemoteDataSource.GetExitApplicationList(PageSize, PageCount, json);

                LogUtil.d("1.49 获取退场数据(退场申请，退场审核列表): " + result);

                List<ExitAssessor> list = gson.fromJson(result, new TypeToken<List<ExitAssessor>>() {
                }.getType());

                // 初始化
                DataSupport.deleteAll(ExitAssessor.class);
                DataSupport.saveAll(list);

                // 筛选 isExit = 1 的数据
                List<ExitAssessor> exitAssessorList = DataSupport.where("IsExit = ?", "1").find(ExitAssessor.class);

                e.onNext(exitAssessorList);
                e.onComplete();
            }
        });
    }

    /**
     * 1.66 获取用户待办信息
     *
     * @param PageSize
     * @param PageCount
     * @return
     */
    @Override
    public Observable<List<BackLog>> GetPendingTaskList(final int PageSize, final int PageCount) {
        return Observable.create(new ObservableOnSubscribe<List<BackLog>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<BackLog>> e) throws Exception {
                JSONObject root = new JSONObject();
                JSONObject Condition = new JSONObject();

                JSONArray Column = new JSONArray();

                // 供应商账号
                JSONObject object1 = new JSONObject();
                User user = DataSupport.findAll(User.class).get(0);
                object1.put("Name", "SubcontractorAccount");
                object1.put("Type", "string");
                object1.put("Format", "Equal");
                object1.put("Value", user.getUserID());


                // 保存3个对象到object
                Column.put(object1);

                // 保存object到Condition
                Condition.put("Column", Column);

                // 保存到root
                root.put("Condition", Condition);

                String json = root.toString();

                LogUtil.d(user.getUserID() + "\n1.66 获取用户待办信息: \n" + json);

                String result = mRemoteDataSource.GetPendingTaskList(PageSize, PageCount, json, user.getUserID());

                LogUtil.d(user.getUserID() + "\n待办信息详情: \n" + result);
                List<BackLog> list = gson.fromJson(result, new TypeToken<List<BackLog>>() {
                }.getType());

                // 保存数据到数据库
                DataSupport.deleteAll(BackLog.class);
                DataSupport.deleteAll(ListBean.class);

                for (BackLog backLog : list) {
                    backLog.save();
                    List<ListBean> backLogList = backLog.getList();
                    DataSupport.saveAll(backLogList);
                }


                /** 网络更新待办数据后, 状态改成从DB获取数据 */
                //                SharePreferenceUtil.saveInt(App.getAppContext(), SettingUtil.SP_KEY_UPCOMING, SettingUtil.UPCOMING_DB);
                //
                //                LogUtil.d("待办数据获取路径: " + SharePreferenceUtil.getInt(App.getAppContext(), SettingUtil.SP_KEY_UPCOMING));

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.67根据ItemID删除过砂记录信息
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteOverSandRecordByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteOverSandRecordByItemID(ItemID);
                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 根据ID, 获取待办任务
     *
     * @param pendingID
     * @return
     */
    @Override
    public Observable<BackLog> getUpcomingList(final String pendingID) {
        return Observable.create(new ObservableOnSubscribe<BackLog>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<BackLog> e) throws Exception {
                // TODO
                List<BackLog> backLogs = DataSupport.where("PendingID = ?", pendingID).find(BackLog.class, true);

                e.onNext(backLogs.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 从数据库获取待办数据
     *
     * @return
     */
    @Override
    public Observable<List<BackLog>> getPendingForDB() {
        return Observable.create(new ObservableOnSubscribe<List<BackLog>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<BackLog>> e) throws Exception {
                List<BackLog> all = DataSupport.findAll(BackLog.class, true);

                e.onNext(all);
                e.onComplete();
            }
        });
    }

    /**
     * 获取PDF提交数据
     *
     * @param path
     * @param subID
     * @param typeID
     * @param shipAccount
     * @return
     */
    @Override
    public Observable<CommitPictureBean> getPDFCommit(final String path, final int subID, final int typeID, final String shipAccount) {
        return Observable.create(new ObservableOnSubscribe<CommitPictureBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<CommitPictureBean> e) throws Exception {
                // 获取供应商账号
                Subcontractor subcontractor = DataSupport.findAll(Subcontractor.class).get(0);

                CommitPictureBean commitBean = new CommitPictureBean();
                // 条目ID, 默认0
                commitBean.setItemID(0);
                // 进场ID
                commitBean.setSubcontractorInterimApproachPlanID(subID);
                // 类型ID
                commitBean.setSubcontractorPerfectBoatScannerAttachmentTypeID(typeID);
                // 供应商账号
                commitBean.setSubcontractorAccount(subcontractor.getSubcontractorAccount());
                // 船舶账号
                commitBean.setConstructionBoatAccount(shipAccount);

                // 文件名
                String[] fileNames = path.split("/");
                String fileName = fileNames[fileNames.length - 1];

                // 文件类型
                String[] split = fileName.split("\\.");
                String suffixName = split[split.length - 1];

                commitBean.setFileName(fileName);
                commitBean.setSuffixName(suffixName);
                commitBean.setCreator(subcontractor.getSubcontractorAccount());

                File file = new File(path);
                byte[] bytes = FileUtil.File2byte(file);
                LogUtil.d("扫描件上传文件原大小: " + (bytes.length / 1024) + "KB");
                commitBean.setBase64img(new String(Base64.encode(bytes, Base64.DEFAULT)));
                LogUtil.d("扫描件上传文件转换后大小: " + (commitBean.getBase64img().length() / 1024) + "KB");

                e.onNext(commitBean);
                e.onComplete();
            }
        });
    }

    /**
     * 1.68 获取施工船舶明细数据
     *
     * @param PageSize
     * @param PageCount
     * @param startTime
     * @param endTime
     * @param shipAccount
     * @param Creator
     * @return
     */
    @Override
    public Observable<List<LogManagerList>> GetConstructionBoatDailyList(final int PageSize, final int PageCount, final String startTime, final String endTime, final String shipAccount, final String Creator, final String threadType) {
        return Observable.create(new ObservableOnSubscribe<List<LogManagerList>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<LogManagerList>> e) throws Exception {
                JSONObject root = new JSONObject();
                JSONObject Condition = new JSONObject();

                JSONArray Column = new JSONArray();

                // 创建者账号
                JSONObject object1 = new JSONObject();
                object1.put("Name", "Creator");
                object1.put("Type", "string");
                object1.put("Format", "Equal");
                object1.put("Value", Creator);

                // 日期
                JSONObject object2 = new JSONObject();
                object2.put("Name", "Date");
                object2.put("Type", "datetime");

                JSONArray array2 = new JSONArray();

                JSONObject object21 = new JSONObject();
                object21.put("Min", startTime);
                JSONObject object22 = new JSONObject();
                object22.put("Max", endTime);

                array2.put(object21);
                array2.put(object22);

                object2.put("Value", array2);

                // 供砂船舶
                JSONObject object3 = new JSONObject();
                object3.put("Name", "ShipName"); // TODO 需要修改
                object3.put("Type", "string");
                object3.put("Format", "Equal");
                object3.put("Value", shipAccount);

                // 施工类型
                JSONObject object4 = new JSONObject();
                object4.put("Name", "ConstructionType");
                object4.put("Type", "string");
                object4.put("Format", "Equal");
                object4.put("Value", threadType);

                // 保存3个对象到object
                if (!TextUtils.isEmpty(Creator)) {
                    Column.put(object1);
                }

                if (!TextUtils.isEmpty(startTime) || !TextUtils.isEmpty(endTime)) {
                    Column.put(object2);
                }

                if (!TextUtils.isEmpty(shipAccount)) {
                    Column.put(object3);
                }

                if (!TextUtils.isEmpty(threadType)) {
                    Column.put(object4);
                }

                // 保存object到Condition
                Condition.put("Column", Column);

                // 保存到root
                root.put("Condition", Condition);

                String json = root.toString();

                LogUtil.d("1.68 获取施工船舶明细数据json: \n" + json);

                String result = mRemoteDataSource.GetConstructionBoatDailyList(PageSize, PageCount, json);

                LogUtil.d("1.68 获取施工船舶明细数据result: \n" + result);

                List<LogManagerList> lists = gson.fromJson(result, new TypeToken<List<LogManagerList>>() {
                }.getType());

                DataSupport.deleteAll(LogManagerList.class);
                DataSupport.saveAll(lists);

                List<LogManagerList> logManagerLists = DataSupport.order("EndTime desc").find(LogManagerList.class);

                e.onNext(logManagerLists);
                e.onComplete();
            }
        });
    }

    /**
     * 1.69 删除船舶日志停工数据
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteConstructionBoatStopDailyByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteConstructionBoatStopDailyByItemID(ItemID);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.70 删除船舶抛砂数据
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteConstructionBoatThrowingSandRecordsByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteConstructionBoatThrowingSandRecordsByItemID(ItemID);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.49 获取退场数据(退场申请，退场审核列表)
     *
     * @param PageSize
     * @param PageCount
     * @param jumpWeek
     * @param account
     * @param isExitApplication
     * @return
     */
    @Override
    public Observable<Boolean> GetExitApplicationList(final int PageSize, final int PageCount, final int jumpWeek, final String account, final boolean isExitApplication) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                 /* 开始时间 */
                String startDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY, jumpWeek);
                /* 结束时间 */
                String endDay = CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY, jumpWeek);

                JSONObject root = new JSONObject();
                JSONObject Condition = new JSONObject();

                JSONArray Column = new JSONArray();

                // 供应商账号
                JSONObject object1 = new JSONObject();
                object1.put("Name", "SubcontractorAccount");
                object1.put("Type", "string");
                object1.put("Format", "Equal");
                object1.put("Value", account);

                // 退场时间时间
                JSONObject object2 = new JSONObject();
                object2.put("Name", "PlanDay");
                object2.put("Type", "datetime");

                JSONArray array2 = new JSONArray();

                JSONObject object21 = new JSONObject();
                object21.put("Min", startDay);
                JSONObject object22 = new JSONObject();
                object22.put("Max", endDay);

                array2.put(object21);
                array2.put(object22);

                object2.put("Value", array2);

                // 保存2个对象到object
                if (!TextUtils.isEmpty(account)) {
                    Column.put(object1);
                }
                if (!TextUtils.isEmpty(startDay) || !TextUtils.isEmpty(endDay)) {
                    Column.put(object2);
                }

                // 保存object到Condition
                Condition.put("Column", Column);

                // 保存到root
                root.put("Condition", Condition);

                String json = root.toString();

                LogUtil.d(account + "\n1.49 获取退场数据(退场申请，退场审核列表) json: \n" + json);

                String result = mRemoteDataSource.GetExitApplicationList(PageSize, PageCount, json);

                LogUtil.d("1.49 获取退场数据(退场申请，退场审核列表): \n" + result);

                List<ExitAssessor> lists = gson.fromJson(result, new TypeToken<List<ExitAssessor>>() {
                }.getType());




                /* 3. 初始化数据库 */
                deleteAll(ExitAssessor.class);

                /* 4. 保存数据到数据库 */
                DataSupport.saveAll(lists);

                /* 5. 对数据进行排序 */

                // 1. 创建一个二维集合存放分类好的数据
                List<List<ExitAssessor>> totalLists = new ArrayList<>();

                // 2. 查询数据
                List<ExitAssessor> recordLists = DataSupport.findAll(ExitAssessor.class);

                // 3. 按照时间进行分类
                Set set = new HashSet();
                for (ExitAssessor bean : recordLists) {
                    String planDay = bean.getPlanDay();
                    if (set.contains(planDay)) {

                    } else {
                        set.add(planDay);
                        //                        if (isExitApplication) {
                        totalLists.add(DataSupport.where("PlanDay = ?", planDay).find(ExitAssessor.class));
                        //                        } else {
                        //                            totalLists.add(DataSupport.where("PlanDay = ? and IsSumbitted = ?", planDay, "1").find(ExitAssessor.class));
                        //                        }
                    }
                }

                // 4. 更新position
                for (List<ExitAssessor> list : totalLists) {
                    for (int i = 1; i <= list.size(); i++) {
                        // 更新数据
                        ExitAssessor record = list.get(i - 1);
                        record.setPosition(String.valueOf(dateToPosition(record.getPlanDay(), i, jumpWeek)));
                        record.save();
                    }
                }

                List<ExitAssessor> all = DataSupport.findAll(ExitAssessor.class);

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 搜索联系人
     *
     * @param keyWords
     * @return
     */
    @Override
    public Observable<List<Contacts>> searchContacts(final String keyWords) {
        return Observable.create(new ObservableOnSubscribe<List<Contacts>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Contacts>> e) throws Exception {
                if (TextUtils.isEmpty(keyWords)) {
                    e.onNext(new ArrayList<Contacts>());

                } else {
                    List<Contacts> list = DataSupport
                            .where("EnglishName like ? or Department like ? and LoginName is not null and LoginName != ?", "%" + keyWords + "%", "%" + keyWords + "%", "")
                            .find(Contacts.class);

                    e.onNext(list);
                }
                e.onComplete();
            }
        });
    }

    /**
     * 统计每日船舶数
     *
     * @param jumpWeek
     * @return
     */
    @Override
    public Observable<List<Integer>> getDemanDayShipCount(final int jumpWeek) {
        return Observable.create(new ObservableOnSubscribe<List<Integer>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Integer>> e) throws Exception {
                List<String> dates = CalendarUtil.getdateOfWeek("yyyy-MM-dd", jumpWeek);
                ArrayList<Integer> list = new ArrayList<>();

                for (int i = 0; i < dates.size(); i++) {
                    List<WeekTask> weekTasks = DataSupport.where("PlanDay like ?", dates.get(i) + "%").find(WeekTask.class);
                    list.add(weekTasks.size());
                }

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * base统计每日船舶数
     *
     * @param jumpWeek
     * @param type
     * @return
     */
    @Override
    public Observable<List<Integer>> getBaseDayShipCount(final int jumpWeek, final int type) {
        return Observable.create(new ObservableOnSubscribe<List<Integer>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Integer>> e) throws Exception {
                List<String> dates = CalendarUtil.getdateOfWeek("yyyy-MM-dd", jumpWeek);
                ArrayList<Integer> list = new ArrayList<>();
                int num = 0;

                for (int i = 0; i < dates.size(); i++) {
                    /** 根据类型计算每日船次 */
                    if (type == SettingUtil.TYPE_RECORDEDSAND) { // 过砂记录
                        /** 过砂记录 */
                        num = DataSupport.where("PlanDay like ?", dates.get(i) + "%").count(RecordList.class);
                        list.add(num);
                    } else if (type == SettingUtil.TYPE_VOYAGEINFO) { // 航次信息完善
                        /** 航次信息完善 */
                        num = DataSupport.where("PlanDay like ?", dates.get(i) + "%").count(WeekTask.class);
                        list.add(num);
                    } else if (type == SettingUtil.TYPE_SAMPLE) { // 验砂取样
                        /** 验砂取样 */
                        num = DataSupport.where("PlanDay like ?", dates.get(i) + "%").count(SandSample.class);
                        list.add(num);
                    } else if (type == SettingUtil.TYPE_SUPPLY) { // 验砂
                        /** 验砂 */
                        num = DataSupport.where("PlanDay like ?", dates.get(i) + "%").count(WeekTask.class);
                        list.add(num);
                    } else if (type == SettingUtil.TYPE_AMOUNT) { // 量方
                        /** 量方 */
                        num = DataSupport.where("PlanDay like ?", dates.get(i) + "%").count(WeekTask.class);
                        list.add(num);
                    } else if (type == SettingUtil.TYPE_SCANNER) {
                        /** 扫描件 */
                        num = DataSupport.where("PlanDay like ?", dates.get(i) + "%").count(WeekTask.class);
                        list.add(num);
                    } else if (type == SettingUtil.TYPE_EXIT_APPLICATION) {
                        /** 退场申请 IsSumbitted申请提交状态 */
                        num = DataSupport.where("PlanDay like ?", dates.get(i) + "%").count(ExitAssessor.class);
                        list.add(num);
                    } else if (type == SettingUtil.TYPE_EXIT_ASSESSOR) {
                        /** 退场审核 IsExit审核状态 */
                        //                        num = DataSupport.where("PlanDay like ? and IsSumbitted = ?", dates.get(i) + "%", "1").count(ExitAssessor.class);
                        num = DataSupport.where("PlanDay like ?", dates.get(i) + "%").count(ExitAssessor.class);
                        list.add(num);
                    } else if (type == SettingUtil.TYPE_ACCEPT) {
                        /** 验收 1通过, 0保存, -1不通过(不显示) */
                        // TODO
                        num = DataSupport.where("PlanDay like ?", dates.get(i) + "%").count(WeekTask.class);
                        list.add(num);
                    }
                }

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.68 获取供砂船航次信息数据(近7天)
     *
     * @param PageSize
     * @param PageCount
     * @param currentDate
     * @return
     */
    @Override
    public Observable<Boolean> GetBoatShipItemNum(final int PageSize, final int PageCount, final String shipAccount, final String currentDate) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                JSONObject root = new JSONObject();
                JSONObject Condition = new JSONObject();

                JSONArray Column = new JSONArray();

                // 供砂船账号
                JSONObject object1 = new JSONObject();
                object1.put("Name", "ShipAccount");
                object1.put("Type", "string");
                //object1.put("Format", "Equal");
                object1.put("Value", shipAccount);

                // 供砂航次
                JSONObject object2 = new JSONObject();
                object2.put("Name", "ShipItemNum");
                object2.put("Type", "float");
                object2.put("Format", "");

                JSONArray array2 = new JSONArray();

                String offsetDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, currentDate, Calendar.DAY_OF_YEAR, -7);
                String s = offsetDate.replaceAll("-", "");
                String num = currentDate.replaceAll("-", "");

                JSONObject object21 = new JSONObject();
                object21.put("Min", s + "01");
                JSONObject object22 = new JSONObject();
                object22.put("Max", num + "99");

                array2.put(object21);
                array2.put(object22);
                object2.put("Value", array2);

                // 验收时间
                JSONObject object3 = new JSONObject();
                object3.put("Name", "PreAcceptanceTime");
                object3.put("Type", "datetime");

                JSONArray array3 = new JSONArray();

                JSONObject object31 = new JSONObject();
                object31.put("Min", offsetDate + " 00:00:00");
                JSONObject object32 = new JSONObject();
                object32.put("Max", currentDate);

                array3.put(object31);
                array3.put(object32);
                object3.put("Value", array3);


                // 保存3个对象到object
                if (!TextUtils.isEmpty(shipAccount)) {
                    // TODO: 不根据供砂船过滤
                    //Column.put(object1);
                }

                if (!TextUtils.isEmpty(currentDate)) {
                    // TODO: 2017/11/16修改需求, 不根据船舶航次进行过滤
                    //Column.put(object2);
                }

                if (!TextUtils.isEmpty(currentDate)) {
                    Column.put(object3);
                }

                // 保存object到Condition
                Condition.put("Column", Column);

                // 保存到root
                root.put("Condition", Condition);

                String json = root.toString();

                LogUtil.d("1.68 获取供砂船航次信息数据(近7天)json: \n" + json);

                // TODO: 暂时根据供砂航次进行过滤
                String result = mRemoteDataSource.GetBoatShipItemNum(PageSize, PageCount, json);

                LogUtil.d("1.68 获取供砂船航次信息数据(近7天): \n" + result);

                List<ThreadShip> list = gson.fromJson(result, new TypeToken<List<ThreadShip>>() {
                }.getType());

                DataSupport.deleteAll(ThreadShip.class);
                DataSupport.saveAll(list);

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 1.24 删除验砂取样图片数据
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteSandSamplingAttachmentByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteSandSamplingAttachmentByItemID(itemID);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 获取验砂取样图片列表
     *
     * @param imageMultipleResultEvent
     * @param p_position
     * @return
     */
    @Override
    public Observable<List<SampleImageList>> getSampleImgList(final ImageMultipleResultEvent imageMultipleResultEvent,
                                                              final int SandSamplingID,
                                                              final int SandSamplingNumID,
                                                              final String ConstructionBoatAccount, final int p_position) {
        return Observable.create(new ObservableOnSubscribe<List<SampleImageList>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<SampleImageList>> e) throws Exception {
                ArrayList<SampleImageList> lists = new ArrayList<>();
                // 获取选中图片
                List<MediaBean> mediaBeanList = imageMultipleResultEvent.getResult();

                for (MediaBean bean : mediaBeanList) {
                    // 创建对象
                    SampleImageList image = new SampleImageList();

                    // 条目ID
                    image.setItemID(0);
                    // 进场ID
                    image.setSandSamplingID(SandSamplingID);
                    // 取样编号ID
                    image.setSandSamplingNumID(SandSamplingNumID);
                    // 本地图片路径
                    image.setFilePath(bean.getOriginalPath());
                    // TODO: 网络图片路径
                    // 文件名
                    String title = bean.getTitle();
                    String mimeType = bean.getMimeType();
                    String[] split = mimeType.split("/");
                    String suffixName = split[split.length - 1];
                    String filename = title + "." + suffixName;
                    image.setFileName(filename);
                    // 后缀名
                    image.setSuffixName(suffixName);
                    // 施工船舶
                    image.setConstructionBoatAccount(ConstructionBoatAccount);
                    // 解析图片
                    File file = new File(bean.getOriginalPath());
                    LogUtil.d(file.getAbsolutePath() + "原始长度: " + file.length()/1024 + "kb");
                    File tagFile = Luban.with(App.getAppContext()).load(file).get();
                    LogUtil.d(tagFile.getAbsolutePath() + "压缩长度: " + tagFile.length()/1024 + "kb");
                    byte[] bytes = FileUtil.File2byte(tagFile);
                    String ByteDataStr = new String(Base64.encode(bytes, Base64.DEFAULT));
                    image.setByteDataStr(ByteDataStr);
                    // 图片在取样列表中的位置
                    image.setPosition(p_position);

                    // 保存
                    image.save();
                    lists.add(image);
                }

                e.onNext(lists);
                e.onComplete();
            }
        });
    }

    /**
     * 1.69 提交BCF供砂来船数据
     *
     * @param ItemID
     * @param SandHandlingShipID
     * @param SubcontractorAccount
     * @param TotalAmount
     * @param Remark
     * @param Creator
     * @param Date
     * @return
     */
    @Override
    public Observable<Boolean> InsertBCFToShipRecord(final int ItemID, final String SandHandlingShipID, final String SubcontractorAccount, final float TotalAmount, final String Remark, final String Creator, final String Date) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ItemID", ItemID);
                jsonObject.put("SandHandlingShipID", SandHandlingShipID);
                jsonObject.put("SubcontractorAccount", SubcontractorAccount);
                jsonObject.put("TotalAmount", TotalAmount);
                jsonObject.put("Remark", Remark);
                jsonObject.put("Creator", Creator);
                jsonObject.put("Date", Date);

                jsonArray.put(jsonObject);

                String json = jsonArray.toString();

                LogUtil.d("1.69 提交BCF供砂来船数据json: \n" + json);

                String result = mRemoteDataSource.InsertBCFToShipRecord(json);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.70 提交BCF抛砂数据（施工日志抛砂）
     *
     * @param json
     * @return
     */
    @Override
    public Observable<Boolean> InsertBCFBoatThrowingSandRecord(final String json) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.InsertBCFBoatThrowingSandRecord(json);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.71 获取BCF来砂船舶的明细数据
     *
     * @param PageSize
     * @param PageCount
     * @param startTime
     * @param endTime
     * @param subAccount
     * @return
     */
    @Override
    public Observable<List<BCFLog>> GetBCFToShipRecords(final int PageSize, final int PageCount, final String startTime, final String endTime, final String subAccount) {
        return Observable.create(new ObservableOnSubscribe<List<BCFLog>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<BCFLog>> e) throws Exception {
                JSONObject root = new JSONObject();
                JSONObject Condition = new JSONObject();

                JSONArray Column = new JSONArray();

                // 供应商账号
                JSONObject object1 = new JSONObject();
                object1.put("Name", "SubcontractorAccount");
                object1.put("Type", "string");
                object1.put("Format", "Equal");
                object1.put("Value", subAccount);

                // 时间
                JSONObject object2 = new JSONObject();
                object2.put("Name", "Date");
                object2.put("Type", "datetime");

                JSONArray array2 = new JSONArray();

                JSONObject object21 = new JSONObject();
                object21.put("Min", startTime);
                JSONObject object22 = new JSONObject();
                object22.put("Max", endTime);

                array2.put(object21);
                array2.put(object22);
                object2.put("Value", array2);

                // 保存2个对象到object
                if (!TextUtils.isEmpty(subAccount)) {
                    Column.put(object1);
                }
                if (!TextUtils.isEmpty(startTime) || !TextUtils.isEmpty(endTime)) {
                    Column.put(object2);
                }

                // 保存object到Condition
                Condition.put("Column", Column);

                // 保存到root
                root.put("Condition", Condition);

                String json = root.toString();

                LogUtil.d("1.71 获取BCF来砂船舶的明细数据json: \n" + json);

                String result = mRemoteDataSource.GetBCFToShipRecords(PageSize, PageCount, json);

                LogUtil.d("1.71 获取BCF来砂船舶的明细数据result: \n" + result);

                List<BCFLog> list = gson.fromJson(result, new TypeToken<List<BCFLog>>() {
                }.getType());

                DataSupport.deleteAll(BCFLog.class);
                DataSupport.saveAll(list);

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.72 获取BCF来砂船舶（抛砂）的明细数据
     *
     * @param PageSize
     * @param PageCount
     * @param startTime
     * @param endTime
     * @param shipAccount
     * @return
     */
    @Override
    public Observable<List<BCFThread>> GetBCFBoatList(final int PageSize, final int PageCount, final String startTime, final String endTime, final String shipAccount) {
        return Observable.create(new ObservableOnSubscribe<List<BCFThread>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<BCFThread>> e) throws Exception {
                JSONObject root = new JSONObject();
                JSONObject Condition = new JSONObject();

                JSONArray Column = new JSONArray();

                // 供应商账号
                JSONObject object1 = new JSONObject();
                object1.put("Name", "ShipName");
                object1.put("Type", "string");
                object1.put("Format", "Equal");
                object1.put("Value", shipAccount);

                // 时间
                JSONObject object2 = new JSONObject();
                object2.put("Name", "Date");
                object2.put("Type", "datetime");

                JSONArray array2 = new JSONArray();

                JSONObject object21 = new JSONObject();
                object21.put("Min", startTime);
                JSONObject object22 = new JSONObject();
                object22.put("Max", endTime);

                array2.put(object21);
                array2.put(object22);

                object2.put("Value", array2);

                // 保存2个对象到object
                if (!TextUtils.isEmpty(shipAccount)) {
                    Column.put(object1);
                }
                if (!TextUtils.isEmpty(startTime) || !TextUtils.isEmpty(endTime)) {
                    Column.put(object2);
                }

                // 保存object到Condition
                Condition.put("Column", Column);

                // 保存到root
                root.put("Condition", Condition);

                String json = root.toString();

                LogUtil.d("1.72 获取BCF来砂船舶（抛砂）的明细数据json: \n" + json);

                String result = mRemoteDataSource.GetBCFBoatList(PageSize, PageCount, json);

                LogUtil.d("1.72 获取BCF来砂船舶（抛砂）的明细数据result: \n" + result);

                List<BCFThread> list = gson.fromJson(result, new TypeToken<List<BCFThread>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.73 获取BCF来砂船舶数据
     *
     * @param PageSize
     * @param PageCount
     * @param shipAccount
     * @return
     */
    @Override
    public Observable<Boolean> GetBCFToShipInfo(final int PageSize, final int PageCount, String shipAccount) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.GetBCFToShipInfo(PageSize, PageCount, "");

                LogUtil.d("1.73 获取BCF来砂船舶数据result: \n" + result);

                List<BCFShip> list = gson.fromJson(result, new TypeToken<List<BCFShip>>() {
                }.getType());

                DataSupport.deleteAll(BCFShip.class);
                DataSupport.saveAll(list);

                e.onNext(true);
                e.onComplete();
            }
        });
    }

    /**
     * 1.74 删除BCF来砂船舶日志数据
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteBCFToShipRecordsByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteBCFToShipRecordsByItemID(ItemID);

                LogUtil.d(ItemID + "\n1.74 删除BCF来砂船舶日志数据result: \n" + result);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.74 删除BCF船舶日志(抛砂日志)数据
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteBCFBoatThrowingSandRecordsByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteBCFBoatThrowingSandRecordsByItemID(ItemID);

                LogUtil.d(ItemID + "\n1.74 删除BCF船舶日志(抛砂日志)数据result: \n" + result);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.75 根据ItemID获取施工日志（抛砂）数据 (BCF来砂)
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<ThreadDetailInfo> GetBCFBoatThrowingSandRecordByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<ThreadDetailInfo>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ThreadDetailInfo> e) throws Exception {
                String result = mRemoteDataSource.GetBCFBoatThrowingSandRecordByItemID(itemID);

                LogUtil.d(itemID + "\n1.75 根据ItemID获取施工日志（抛砂）数据 (BCF来砂)result: \n" + result);

                List<ThreadDetailInfo> list = gson.fromJson(result, new TypeToken<List<ThreadDetailInfo>>() {
                }.getType());

                e.onNext(list.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 1.76 根据ItemID获取施工日志（抛砂）数据
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<ThreadDetailInfo> GetConstructionBoatThrowingSandRecordByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<ThreadDetailInfo>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ThreadDetailInfo> e) throws Exception {
                String result = mRemoteDataSource.GetConstructionBoatThrowingSandRecordByItemID(itemID);

                LogUtil.d(itemID + "\n1.76 根据ItemID获取施工日志（抛砂）数据json: \n" + result);

                List<ThreadDetailInfo> list = gson.fromJson(result, new TypeToken<List<ThreadDetailInfo>>() {
                }.getType());

                e.onNext(list.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 1.77 获取泵砂船数据
     *
     * @param PageSize
     * @param PageCount
     * @param shipAccount
     * @return
     */
    @Override
    public Observable<List<PumpShip>> GetPumpShipInfo(final int PageSize, final int PageCount, final String shipAccount) {
        return Observable.create(new ObservableOnSubscribe<List<PumpShip>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<PumpShip>> e) throws Exception {
                JSONObject root = new JSONObject();
                JSONObject Condition = new JSONObject();

                JSONArray Column = new JSONArray();

                // 泵砂船账号
                JSONObject object1 = new JSONObject();
                object1.put("Name", "ShipAccount");
                object1.put("Type", "string");
                object1.put("Format", "Equal");
                object1.put("Value", shipAccount);

                // 保存2个对象到object
                if (!TextUtils.isEmpty(shipAccount)) {
                    Column.put(object1);
                }

                // 保存object到Condition
                Condition.put("Column", Column);

                // 保存到root
                root.put("Condition", Condition);

                String json = root.toString();

                LogUtil.d("1.77 获取泵砂船数据json: \n" + json);

                String result = mRemoteDataSource.GetPumpShipInfo(PageSize, PageCount, json);

                LogUtil.d("1.77 获取泵砂船数据result: \n" + result);

                List<PumpShip> list = gson.fromJson(result, new TypeToken<List<PumpShip>>() {
                }.getType());

                DataSupport.deleteAll(PumpShip.class);
                DataSupport.saveAll(list);

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.79 验证当前施工船舶数据是否在已有的数据范围
     *
     * @param ShipAccount
     * @param StartTime
     * @param EndTime
     * @return
     */
    @Override
    public Observable<Boolean> IsCurrentDataInTimeRangeForBoatDaily(final String ShipAccount, final String StartTime, final String EndTime) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.IsCurrentDataInTimeRangeForBoatDaily(ShipAccount, StartTime, EndTime);

                LogUtil.d("shipAccount = " + ShipAccount + ", startTime = " + StartTime + ", endTime = " + EndTime + "\n是否允许提交施工日报: " + result);
                IsCurrentData isCurrentData = gson.fromJson(result, IsCurrentData.class);

                e.onNext(isCurrentData.getReturnValue() == 0);
                e.onComplete();
            }
        });
    }

    /**
     * 2.9 今日来船数据统计分析
     *
     * @param CurrentDate
     * @return
     */
    @Override
    public Observable<TodayPlanBean> GetToShipByCurrentDateAnalysis(final String CurrentDate) {
        return Observable.create(new ObservableOnSubscribe<TodayPlanBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<TodayPlanBean> e) throws Exception {
                String result = mRemoteDataSource.GetToShipByCurrentDateAnalysis(CurrentDate);
                LogUtil.d(CurrentDate + "\n2.9 今日来船数据统计分析result: " + result);

                TodayPlanBean todayPlanBean = gson.fromJson(result, TodayPlanBean.class);
                e.onNext(todayPlanBean);
                e.onComplete();
            }
        });
    }

    /**
     * 6.5 获取HSE检查记录数据
     *
     * @param PageSize
     * @param PageCount
     * @param bean
     * @return
     */
    @Override
    public Observable<List<HseCheckListBean>> GetSafeHSECheckedRecords(final int PageSize, final int PageCount, final HseCheckSelectBean bean) {
        return Observable.create(new ObservableOnSubscribe<List<HseCheckListBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HseCheckListBean>> e) throws Exception {
                // 检查时间 (start = currentTime, end = offsetTime)
                JSONObject checkedTime = JsonUtil.creatorJsonObjectArray("CheckedTime", JsonUtil.TYPE_DATETIME, bean.getStartDate(), bean.getEndDate());
                // 当前用户
                JSONObject creator = JsonUtil.creatorJsonObject("Creator", JsonUtil.TYPE_STRING, bean.getCreator());
                // 受检船舶
                JSONObject checkedShipAccount = JsonUtil.creatorJsonObject("CheckedShipAccount", JsonUtil.TYPE_STRING, bean.getCheckedShipAccount());
                String json = JsonUtil.spliceJson(checkedTime, creator, checkedShipAccount);

                LogUtil.t("6.5 获取HSE检查记录数据: \n");
                LogUtil.json(json);

                String result = mRemoteDataSource.GetSafeHSECheckedRecords(PageSize, PageCount, json);

                LogUtil.t("6.5 获取HSE检查记录数据result: \n");
                LogUtil.json(result);


                List<HseCheckListBean> list = gson.fromJson(result, new TypeToken<List<HseCheckListBean>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 6.3 获取受检船舶数据（包含供砂船舶，施工船舶）
     *
     * @return
     */
    @Override
    public Observable<List<HseCheckShip>> GetSafeCheckedShip() {
        return Observable.create(new ObservableOnSubscribe<List<HseCheckShip>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HseCheckShip>> e) throws Exception {
                String result = mRemoteDataSource.GetSafeCheckedShip();
                LogUtil.t("6.3 获取受检船舶数据（包含供砂船舶，施工船舶） result:\n");
                LogUtil.json(result);

                List<HseCheckShip> list = gson.fromJson(result, new TypeToken<List<HseCheckShip>>() {
                }.getType());

                DataSupport.deleteAll(HseCheckShip.class);
                DataSupport.saveAll(list);

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 6.4 提交HSE检查记录数据
     *
     * @param list
     * @return
     */
    @Override
    public Observable<Boolean> InsertSafeHSECheckedRecord(final List<HseCheckAddBean> list) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String json = gson.toJson(list);
                LogUtil.json(json);
                String result = mRemoteDataSource.InsertSafeHSECheckedRecord(json);
                LogUtil.t("6.4 提交HSE检查记录数据result: \n");
                LogUtil.json(result);

                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 6.7 根据ItemID删除HSE检查记录数据
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteSafeHSECheckedRecordByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteSafeHSECheckedRecordByItemID(ItemID);
                CommitResultBean bean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(bean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 6.6 根据ItemID获取HSE检查记录数据
     *
     * @param ItemID
     * @return
     */
    @Override
    public Observable<HseCheckListBean> GetSafeHSECheckedRecordByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<HseCheckListBean>() {
            @Override
            public void subscribe(ObservableEmitter<HseCheckListBean> e) throws Exception {
                String result = mRemoteDataSource.GetSafeHSECheckedRecordByItemID(ItemID);
                LogUtil.t(ItemID + "HSE检查记录: \n");
                LogUtil.json(result);

                List<HseCheckListBean> list = gson.fromJson(result, new TypeToken<List<HseCheckListBean>>() {
                }.getType());

                e.onNext(list.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 6.2 获取缺陷类别数据
     *
     * @param PageSize
     * @param PageCount
     * @param bean
     * @return
     */
    @Override
    public Observable<List<HseDefectType>> GetSafeDefectType(final int PageSize, final int PageCount, HseDefectTypeBean bean) {
        return Observable.create(new ObservableOnSubscribe<List<HseDefectType>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HseDefectType>> e) throws Exception {
                // 查询所有
                String result = mRemoteDataSource.GetSafeDefectType(PageSize, PageCount, "");

                List<HseDefectType> list = gson.fromJson(result, new TypeToken<List<HseDefectType>>() {
                }.getType());

                DataSupport.deleteAll(HseDefectType.class);
                DataSupport.saveAll(list);

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 6.19 整改期限基础信息
     *
     * @param PageSize
     * @param PageCount
     * @param bean
     * @return
     */
    @Override
    public Observable<List<HseDefectDeadline>> GetSafeRectificationDeadlineOptions(final int PageSize, final int PageCount, HseDefectDeadlineBean bean) {
        return Observable.create(new ObservableOnSubscribe<List<HseDefectDeadline>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HseDefectDeadline>> e) throws Exception {
                // 查询所有
                String result = mRemoteDataSource.GetSafeRectificationDeadlineOptions(PageSize, PageCount, "");

                List<HseDefectDeadline> list = gson.fromJson(result, new TypeToken<List<HseDefectDeadline>>() {
                }.getType());

                DataSupport.deleteAll(HseDefectDeadline.class);
                DataSupport.saveAll(list);

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 6.22 获取所有缺陷纪录（包含待处理，已处理）
     *
     * @param PageSize
     * @param PageCount
     * @param bean
     * @return
     */
    @Override
    public Observable<List<HseDefectListBean>> GetSafeDefectRecords(final int PageSize, final int PageCount, final HseDefectListBean bean, final String startDate, final String endDate) {
        return Observable.create(new ObservableOnSubscribe<List<HseDefectListBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HseDefectListBean>> e) throws Exception {
                // 检查记录ID
                JSONObject hseCheckedRecordID = JsonUtil.creatorJsonObject("HSECheckedRecordID", JsonUtil.TYPE_STRING, bean.getHSECheckedRecordID() == 0 ? "" : String.valueOf(bean.getHSECheckedRecordID()));
                // 缺陷类别
                JSONObject defectTypeID = JsonUtil.creatorJsonObject("DefectTypeID", JsonUtil.TYPE_STRING, bean.getDefectTypeID() == 0 ? "" : String.valueOf(bean.getDefectTypeID()));
                // 缺陷项目
                JSONObject defectItem = JsonUtil.creatorJsonObject("DefectItem", JsonUtil.TYPE_STRING, bean.getDefectItem());
                // 整改期限
                JSONObject rectificationDeadline = JsonUtil.creatorJsonObject("RectificationDeadline", JsonUtil.TYPE_STRING, bean.getRectificationDeadline() == 0 ? "" : String.valueOf(bean.getRectificationDeadline()));
                // 处理状态
                JSONObject isSubmitted = JsonUtil.creatorJsonObject("IsSubmitted", JsonUtil.TYPE_STRING, bean.getIsSubmitted() == 100 ? "" : String.valueOf(bean.getIsSubmitted()));
                // 当前船舶账号
                JSONObject checkedShipAccount = JsonUtil.creatorJsonObject("CheckedShipAccount", JsonUtil.TYPE_STRING, bean.getCheckedShipAccount());
                // 时间
                JSONObject systemDate = JsonUtil.creatorJsonObjectArray("SystemDate", JsonUtil.TYPE_DATETIME, startDate, endDate);


                String json = JsonUtil.spliceJson(hseCheckedRecordID, defectTypeID, defectItem, rectificationDeadline, isSubmitted, checkedShipAccount, systemDate);
                LogUtil.t(bean.getHSECheckedRecordID() + ", 6.22 获取所有缺陷纪录（包含待处理，已处理）\n");
                LogUtil.json(json);

                String result = mRemoteDataSource.GetSafeDefectRecords(PageSize, PageCount, json);

                LogUtil.t(bean.getHSECheckedRecordID() + ", 6.22 获取所有缺陷纪录（包含待处理，已处理）result: \n");
                LogUtil.json(result);

                List<HseDefectListBean> list = gson.fromJson(result, new TypeToken<List<HseDefectListBean>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 生产待提交图片数据
     *
     * @param imgList
     * @return
     */
    @Override
    public Observable<ImgCommitBean> getImgCommitBean(final ImgList imgList, final Context context) {
        return Observable.create(new ObservableOnSubscribe<ImgCommitBean>() {
            @Override
            public void subscribe(ObservableEmitter<ImgCommitBean> e) throws Exception {
                long start = System.currentTimeMillis();
                // 文件名
                String fileName = imgList.getPath().substring(imgList.getPath().lastIndexOf("/") + 1);
                // 文件后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
                // base64
                File file = new File(imgList.getPath());
                LogUtil.d(file.getAbsolutePath() + "原始长度: " + file.length()/1024 + "kb");
                File tagFile = Luban.with(context).load(file).get();
                LogUtil.d(tagFile.getAbsolutePath() + "压缩长度: " + tagFile.length()/1024 + "kb");

                byte[] bytes = FileUtil.File2byte(tagFile);
                String byteDataStr = new String(Base64.encode(bytes, Base64.DEFAULT));

                ImgCommitBean imgCommitBean = new ImgCommitBean(byteDataStr, suffixName, fileName);
                long end = System.currentTimeMillis();
                LogUtil.e("待提交图片: " + imgCommitBean + ", 耗时: " + (end - start));
                e.onNext(imgCommitBean);
                e.onComplete();
            }
        });
    }

    /**
     * 1.21	 上传图片接口
     *
     * @param bean
     * @return
     */
    @Override
    public Observable<ImgCommitResultBean> UploadFile(final ImgCommitBean bean) {
        return Observable.create(new ObservableOnSubscribe<ImgCommitResultBean>() {
            @Override
            public void subscribe(ObservableEmitter<ImgCommitResultBean> e) throws Exception {
                String result = mRemoteDataSource.UploadFile(bean.getByteDataStr(), bean.getSuffixName(), bean.getFileName());

                LogUtil.t(bean.getFileName() + ", 1.21 上传图片接口result: \n");
                LogUtil.json(result);

                ImgCommitResultBean resultBean = gson.fromJson(result, ImgCommitResultBean.class);

                e.onNext(resultBean);
                e.onComplete();
            }
        });
    }

    /**
     * 6.10 提交HSE检查缺陷记录数据
     *
     * @param bean
     * @param imgCommitResultBeans
     * @return
     */
    @Override
    public Observable<Boolean> InsertSafeDefectRecord(final HseDefectAddCommitBean bean, final List<ImgCommitResultBean> imgCommitResultBeans) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                List<HseDefectAddCommitBean.AttachmentListBean> list = new ArrayList<>();
                for (ImgCommitResultBean bean : imgCommitResultBeans) {
                    HseDefectAddCommitBean.AttachmentListBean attachmentListBean = new HseDefectAddCommitBean.AttachmentListBean();
                    attachmentListBean.setFileName(bean.getFileName());
                    attachmentListBean.setFilePath(bean.getFilePath());
                    list.add(attachmentListBean);
                }

                bean.setAttachmentList(list);

                String json = gson.toJson(bean);

                LogUtil.t(bean.getItemID() + ", 6.10 提交HSE检查缺陷记录数据\n");
                LogUtil.json(json);

                String result = mRemoteDataSource.InsertSafeDefectRecord(json);
                LogUtil.t(bean.getItemID() + ", 6.10 提交HSE检查缺陷记录数据result: \n");
                LogUtil.json(result);

                CommitResultBean resultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(resultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 6.6根据ItemID删除缺陷记录图片
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteSafeDefectAttachmentRecordByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteSafeDefectAttachmentRecordByItemID(itemID);
                LogUtil.t(itemID + ", 6.6根据ItemID删除缺陷记录图片\n");
                LogUtil.json(result);

                CommitResultBean resultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(resultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 6.23 根据缺陷记录ItemID,获取缺陷记录数据，包含图片信息
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<HseDefectDetailBean> GetSafeDefectRecordByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<HseDefectDetailBean>() {
            @Override
            public void subscribe(ObservableEmitter<HseDefectDetailBean> e) throws Exception {
                String result = mRemoteDataSource.GetSafeDefectRecordByItemID(itemID);
                LogUtil.t(itemID + ", 6.23 根据缺陷记录ItemID,获取缺陷记录数据，包含图片信息result:\n");
                LogUtil.json(result);

                HseDefectDetailBean bean = gson.fromJson(result, HseDefectDetailBean.class);

                e.onNext(bean);
                e.onComplete();
            }
        });
    }

    /**
     * 6.11 根据ItemID 删除HSE检查缺陷记录数据
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteDefectRecordByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteDefectRecordByItemID(itemID);
                LogUtil.t(itemID + ", 6.11 根据ItemID 删除HSE检查缺陷记录数据result: \n");
                LogUtil.json(result);

                CommitResultBean commitResultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(commitResultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 6.17 根据ItemID删除整改记录数据
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteSafeRectificationRecordByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteSafeRectificationRecordByItemID(itemID);

                LogUtil.t(itemID + ", 6.17 根据ItemID删除整改记录数据result: \n");
                LogUtil.json(result);

                CommitResultBean commitResultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(commitResultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 6.16 提交整改记录数据
     *
     * @param bean
     * @param imgCommitResultBeans
     * @return
     */
    @Override
    public Observable<Boolean> InsertSafeRectificationRecord(final HseRectificationCommitBean bean, final List<ImgCommitResultBean> imgCommitResultBeans) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                List<HseRectificationCommitBean.AttachmentListBean> list = new ArrayList<>();
                for (ImgCommitResultBean bean : imgCommitResultBeans) {
                    HseRectificationCommitBean.AttachmentListBean attachmentListBean = new HseRectificationCommitBean.AttachmentListBean();
                    attachmentListBean.setFileName(bean.getFileName());
                    attachmentListBean.setFilePath(bean.getFilePath());
                    list.add(attachmentListBean);
                }

                bean.setAttachmentList(list);

                String json = gson.toJson(bean);

                LogUtil.t(bean.getItemID() + ", 6.16 提交整改记录数据\n");
                LogUtil.json(json);

                String result = mRemoteDataSource.InsertSafeRectificationRecord(json);
                LogUtil.t(bean.getItemID() + ", 6.16 提交整改记录数据result: \n");
                LogUtil.json(result);

                CommitResultBean resultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(resultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 6.14根据ItemID删除整改记录图片
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteSafeRectificationAttachmentRecordByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteSafeRectificationAttachmentRecordByItemID(itemID);
                LogUtil.t(itemID + ", 6.14根据ItemID删除整改记录图片result: \n");
                LogUtil.json(result);

                CommitResultBean commitResultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(commitResultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 6.25 根据缺陷记录ItemID,获取缺陷记录数据，包含图片信息
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<HseRectificationBean> GetSafeRectificationRecordByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<HseRectificationBean>() {
            @Override
            public void subscribe(ObservableEmitter<HseRectificationBean> e) throws Exception {
                String result = mRemoteDataSource.GetSafeRectificationRecordByItemID(itemID);
                LogUtil.t(itemID + ", 6.25 根据缺陷记录ItemID,获取缺陷记录数据，包含图片信息result: \n");
                LogUtil.json(result);

                HseRectificationBean hseRectificationBean = gson.fromJson(result, HseRectificationBean.class);

                e.onNext(hseRectificationBean);
                e.onComplete();
            }
        });
    }

    /**
     * 6.19 获取砂船自查记录（包含自查明细项）
     *
     * @param pageSize
     * @param pageCount
     * @param bean
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public Observable<List<BoatInquireBean>> GetSafeShipSelfCheckRecordsAnalysis(final int pageSize, final int pageCount, final BoatInquireBean bean, final String startDate, final String endDate) {
        return Observable.create(new ObservableOnSubscribe<List<BoatInquireBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BoatInquireBean>> e) throws Exception {
                // 检查日期
                JSONObject checkDate = JsonUtil.creatorJsonObjectArray("CheckDate", JsonUtil.TYPE_DATETIME, startDate, endDate);
                // 船舶账号
                JSONObject shipAccount = JsonUtil.creatorJsonObject("ShipAccount", JsonUtil.TYPE_STRING, bean.getShipAccount());
                // 检查人员
                JSONObject creator = JsonUtil.creatorJsonObject("Creator", JsonUtil.TYPE_STRING, bean.getCreator());

                String json = JsonUtil.spliceJson(checkDate, shipAccount, creator);
                LogUtil.t("6.19 获取砂船自查记录（包含自查明细项）json: \n");
                LogUtil.json(json);

                String result = mRemoteDataSource.GetSafeShipSelfCheckRecordsAnalysis(pageSize, pageCount, json);

                LogUtil.t("6.19 获取砂船自查记录（包含自查明细项）result: \n");
                LogUtil.json(result);

                List<BoatInquireBean> list = gson.fromJson(result, new TypeToken<List<BoatInquireBean>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 6.26 根据ItemID,获取砂船自查记录（包含自查明细项）
     *
     * @param itemID
     * @return
     */
    @Override
    public Observable<BoatInquireDetailBean> GetSafeShipSelfCheckDetailRecordByItemID(final int itemID) {
        return Observable.create(new ObservableOnSubscribe<BoatInquireDetailBean>() {
            @Override
            public void subscribe(ObservableEmitter<BoatInquireDetailBean> e) throws Exception {
                String result = mRemoteDataSource.GetSafeShipSelfCheckDetailRecordByItemID(itemID);
                LogUtil.t(itemID + "--> 6.26 根据ItemID,获取砂船自查记录（包含自查明细项）result: \n");
                LogUtil.json(result);

                List<BoatInquireDetailBean> list = gson.fromJson(result, new TypeToken<List<BoatInquireDetailBean>>() {
                }.getType());

                e.onNext(list.get(0));
                e.onComplete();
            }
        });
    }

    /**
     * 6.1 砂船自查数据基础信息
     *
     * @return
     */
    @Override
    public Observable<List<BoatInquireItemBean>> GetSafeShipSelfCheckItems() {
        return Observable.create(new ObservableOnSubscribe<List<BoatInquireItemBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BoatInquireItemBean>> e) throws Exception {
                String result = mRemoteDataSource.GetSafeShipSelfCheckItems(10000, 1, "");
                LogUtil.t("6.1 砂船自查数据基础信息");
                LogUtil.json(result);

                List<BoatInquireItemBean> list = gson.fromJson(result, new TypeToken<List<BoatInquireItemBean>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 6.18 提交砂船自查数据
     *
     * @param bean
     * @return
     */
    @Override
    public Observable<Boolean> InsertSafeShipSelfCheckRecord(final BoatInquireCommitBean bean) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String json = gson.toJson(bean);
                LogUtil.t("6.18 提交砂船自查数据\n");
                LogUtil.json(json);

                String result = mRemoteDataSource.InsertSafeShipSelfCheckRecord(json);
                LogUtil.t("6.18 提交砂船自查数据result: \n");
                LogUtil.json(result);

                CommitResultBean commitResultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(commitResultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 6.24 获取安全管理_持证监督数据
     *
     * @param pageSize
     * @param pageCount
     * @return
     */
    @Override
    public Observable<List<CertificateBean>> GetSafeCertificateSupervision(int pageSize, int pageCount) {
        return Observable.create(new ObservableOnSubscribe<List<CertificateBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CertificateBean>> e) throws Exception {
                String result = mRemoteDataSource.GetSafeCertificateSupervision(10000, 1, "");
                List<CertificateBean> list = gson.fromJson(result, new TypeToken<List<CertificateBean>>() {
                }.getType());

                DataSupport.deleteAll(CertificateBean.class);
                DataSupport.saveAll(list);

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 搜索持证监督记录
     * 1. 船舶 ShipName ShipAccount
     * 2. 供应商 SubcontractorName SubcontractorAccount
     * 3. 姓名 UserName
     * 4. 持证情况 CertificateOptions
     *
     * @param msg
     * @return
     */
    @Override
    public Observable<List<CertificateBean>> searchCertificate(final String msg, final boolean isSearchAll) {
        return Observable.create(new ObservableOnSubscribe<List<CertificateBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CertificateBean>> e) throws Exception {
                List<CertificateBean> list = new ArrayList<>();
                if (isSearchAll) {
                    list = DataSupport.findAll(CertificateBean.class);
                } else if (!isSearchAll && !TextUtils.isEmpty(msg)) {
                    list = DataSupport
                            .where("ShipName like ? " +
                                            "or ShipAccount like ? " +
                                            "or SubcontractorName like ? " +
                                            "or SubcontractorAccount like ? " +
                                            "or UserName like ? " +
                                            "or CertificateOptions like ?",
                                    "%" + msg + "%",
                                    "%" + msg + "%",
                                    "%" + msg + "%",
                                    "%" + msg + "%",
                                    "%" + msg + "%",
                                    "%" + msg + "%")
                            .find(CertificateBean.class);
                }
                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 6.27 获取安全管理_违规记录数据
     * @param pageSize
     * @param pageCount
     * @return
     */
    @Override
    public Observable<List<ViolationRecordBean>> GetSafeViolationRecords(final int pageSize, final int pageCount) {
        return Observable.create(new ObservableOnSubscribe<List<ViolationRecordBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ViolationRecordBean>> e) throws Exception {
                String result = mRemoteDataSource.GetSafeViolationRecords(pageSize, pageCount, "");

                LogUtil.t("6.27 获取安全管理_违规记录数据");
                LogUtil.json(result);

                List<ViolationRecordBean> list = gson.fromJson(result, new TypeToken<List<ViolationRecordBean>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 搜索受检船舶
     * @param msg
     * @param isSearchAll
     * @return
     */
    @Override
    public Observable<List<HseCheckShip>> searchHseCheckShip(final String msg, final boolean isSearchAll) {
        return Observable.create(new ObservableOnSubscribe<List<HseCheckShip>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HseCheckShip>> e) throws Exception {
                List<HseCheckShip> list = new ArrayList<>();
                if (isSearchAll) {
                    list = DataSupport.findAll(HseCheckShip.class);
                } else if (!isSearchAll && !TextUtils.isEmpty(msg)) {
                    list = DataSupport
                            .where("ShipName like ? or ShipAccount like ? " ,  "%" + msg + "%", "%" + msg + "%")
                            .find(HseCheckShip.class);
                }
                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.119	根据进场计划ID获取过砂图片列表（多条）
     * @param subID
     * @return
     */
    @Override
    public Observable<List<ScannerImgListByTypeBean>> GetOverSandAttachmentRecordsBySubcontractorInterimApproachPlanID(final int subID) {
        return Observable.create(new ObservableOnSubscribe<List<ScannerImgListByTypeBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ScannerImgListByTypeBean>> e) throws Exception {
                // 发送网络请求
                String result = mRemoteDataSource.GetOverSandAttachmentRecordsBySubcontractorInterimApproachPlanID(subID);
                LogUtil.d("过砂进场计划ID: " + subID + "\n图片信息: " + result);

                // 解析数据
                List<ScannerImgListByTypeBean> list = gson.fromJson(result, new TypeToken<List<ScannerImgListByTypeBean>>() {
                }.getType());

                e.onNext(list);
                e.onComplete();
            }
        });
    }

    /**
     * 1.118	提交供砂图片数据
     * @param bean
     * @return
     */
    @Override
    public Observable<Boolean> InsertOverSandAttachment(final CommitPictureBean bean) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                // 解析数据成json
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("SubcontractorInterimApproachPlanID", bean.getSubcontractorInterimApproachPlanID());
                jsonObject.put("FileName", bean.getFileName());
                jsonObject.put("SuffixName", bean.getSuffixName());
                jsonObject.put("Creator", bean.getCreator());

                jsonArray.put(jsonObject);


                String json = jsonArray.toString();
                LogUtil.d("提交过砂图片: " + json);

                // 提交图片
                String result = mRemoteDataSource.InsertOverSandAttachment(json, bean.getBase64img());

                CommitResultBean resultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(resultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.120	删除过砂图片数据
     * @param ItemID
     * @return
     */
    @Override
    public Observable<Boolean> DeleteOverSandAttachmentRecordsByItemID(final int ItemID) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteOverSandAttachmentRecordsByItemID(ItemID);
                CommitResultBean resultBean = gson.fromJson(result, CommitResultBean.class);

                e.onNext(resultBean.getMessage() == 1);
                e.onComplete();
            }
        });
    }

    /**
     * 1.124	获取施工相册数据
     * @param PageSize
     * @param PageCount
     * @return
     */
    @Override
    public Observable<ConstructionAlbumBean> GetConstructionPictureAlbumRecordsData(final int PageSize, final int PageCount) {
        return Observable.create(new ObservableOnSubscribe<ConstructionAlbumBean>() {
            @Override
            public void subscribe(ObservableEmitter<ConstructionAlbumBean> e) throws Exception {
                String json = mRemoteDataSource.GetConstructionPictureAlbumRecordsData(PageSize, PageCount, "");
                LogUtil.d("1.124\t获取施工相册数据: " + json);
                ConstructionAlbumBean albumBean = gson.fromJson(json, ConstructionAlbumBean.class);
                e.onNext(albumBean);
                e.onComplete();
            }
        });
    }

    /**
     * 1.126	添加/更新施工相册数据
     * @param Table
     * @param itemID
     * @param albumName
     * @param remark
     * @return
     */
    @Override
    public Observable<Boolean> InsertTable(final String Table, final int itemID, final String albumName, final String remark) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String creator = DataSupport.findAll(User.class).get(0).getUserID();
                String json = JsonUtil.getAlbumJson(itemID, albumName, remark, creator);
                LogUtil.d("1.126\t添加/更新施工相册数据: " + json);
                String result = mRemoteDataSource.InsertTable(Table, json);
                LogUtil.d("1.126\t添加/更新施工相册数据result: " + result);
                e.onNext(getRequestResult(result));
                e.onComplete();
            }
        });
    }

    /**
     * 1.127	删除施工相册数据
     * @param Table
     * @param ItemID
     * @param SubTable
     * @param AssociatedColumn
     * @return
     */
    @Override
    public Observable<Boolean> DeleteTable(final String Table, final String ItemID, final String SubTable, final String AssociatedColumn) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.DeleteTable(Table, ItemID, SubTable, AssociatedColumn);
                e.onNext(getRequestResult(result));
                e.onComplete();
            }
        });
    }

    /**
     * 1.125	获取施工相册对应的图片数据
     * @param pageSize
     * @param pageCount
     * @param albumID
     * @return
     */
    @Override
    public Observable<ConstructionAlbumPictureBean> GetConstructionPictureAttachmentRecordsData(final int pageSize, final int pageCount, final int albumID) {
        return Observable.create(new ObservableOnSubscribe<ConstructionAlbumPictureBean>() {
            @Override
            public void subscribe(ObservableEmitter<ConstructionAlbumPictureBean> e) throws Exception {
                String json = JsonUtil.spliceJson(JsonUtil.creatorJsonObject("AlbumID", JsonUtil.TYPE_STRING, String.valueOf(albumID)));
                LogUtil.d("1.125\t获取施工相册对应的图片数据: " + json);
                String result = mRemoteDataSource.GetConstructionPictureAttachmentRecordsData(pageSize, pageCount, json);
                LogUtil.d("1.125\t获取施工相册对应的图片数据result: " + result);
                ConstructionAlbumPictureBean bean = gson.fromJson(result, ConstructionAlbumPictureBean.class);
                e.onNext(bean);
                e.onComplete();
            }
        });
    }

    /**
     * 1.123	提交施工图片信息数据
     * @param json
     * @param ByteDataStr
     * @return
     */
    @Override
    public Observable<Boolean> InsertConstructionPictureAttachment(final String json, final String ByteDataStr) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                String result = mRemoteDataSource.InsertConstructionPictureAttachment(json, ByteDataStr);
                e.onNext(getRequestResult(result));
                e.onComplete();
            }
        });
    }

    private boolean getRequestResult(String result) {
        return gson.fromJson(result, CommitResultBean.class).getMessage() == 1;
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
    public void dataSort(int jumpWeek) {
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

