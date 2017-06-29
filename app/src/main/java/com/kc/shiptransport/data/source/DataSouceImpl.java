package com.kc.shiptransport.data.source;

import android.content.Context;

import com.kc.shiptransport.data.bean.RecordedSandUpdataBean;
import com.kc.shiptransport.data.bean.SampleShowDatesBean;
import com.kc.shiptransport.data.bean.ScannerListBean;
import com.kc.shiptransport.data.bean.VoyageInfoBean;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.db.AttendanceRecordList;
import com.kc.shiptransport.db.AttendanceType;
import com.kc.shiptransport.db.PerfectBoatRecord;
import com.kc.shiptransport.db.RecordList;
import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.db.SampleImageList;
import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.db.ScannerImage;
import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.WeekTask;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author 邱永恒
 * @time 2017/5/31 20:56
 * @desc 提供数据model的操作接口
 */

public interface DataSouceImpl {

    /**
     * 获取分包商信息, 并缓存到数据库
     */
    void getSubcontractorInfo(String username);

    /**
     * 获取船舶信息, 并缓存到数据库
     */
    void getShipInfo(String username);

    /**
     * 获取标题 日期 + 分包商
     *
     * @param jumpWeek
     * @return
     */
    Observable<String> getTitle(int jumpWeek);

    /**
     * 获取当前月时间
     *
     * @param jumpWeek
     * @return
     */
    Observable<String> getCurrentMouthDate(int jumpWeek);

    /**
     * 从本地数据库获取任务
     *
     * @return
     */
    Observable<List<WeekTask>> getWeekTask();

    /**
     * 计算每天任务总量
     *
     * @return
     */
    Observable<Double[]> getDayCount();

    /**
     * 计算每天任务总量
     *
     * @return
     */
    Observable<Double[]> getDayCount(int type);

    /**
     * 网络请求
     * 请求选中周的任务计划
     *
     * @param jumpWeek
     * @return
     */
    Observable<Boolean> doRefresh(int jumpWeek);

    /**
     * 网络请求
     * 请求选中周的任务计划
     *
     * @param jumpWeek
     * @param account
     * @return
     */
    Observable<Boolean> doRefresh(int jumpWeek, String account);

    /**
     * 根据船的类型对数据进行分类
     *
     * @return
     */
    Observable<List<List<Ship>>> getShipCategory();

    /**
     * 根据itemID获取验收明细
     *
     * @param itemID
     * @return
     */
    Observable<Acceptance> getAcceptanceByItemID(int itemID, boolean isCashe);

    /**
     * 提交验沙审核结果
     *
     * @param itemID
     * @param ReceptionSandTime
     * @param Batch
     * @return
     */
    Observable<Integer> updateForReceptionSandTime(int itemID, String ReceptionSandTime, String Batch);

//    /**
//     * 提交验收审核结果
//     *
//     * @param itemID
//     * @param PassReceptionSandTime
//     * @return
//     */
//    Observable<Integer> UpdateForPassReceptionSandTime(int itemID, String PassReceptionSandTime);

    /**
     * 统计未验收的船的数量
     *
     * @param type
     * @return
     */
    Observable<Integer> getStayNum(int type);

    /**
     * 获取指定日期计划船舶数量
     *
     * @param date
     * @return
     */
    Observable<Integer> getShipCount(String date);

    /**
     * 获取指定日期计划量
     *
     * @param date
     * @return
     */
    Observable<Double> getPlanMeasure(String date);

    /**
     * 分包商评价
     *
     * @param itemID                             评价ID
     * @param rbcomplete                         材料完整性
     * @param rbtimely                           材料及时性
     * @param currentDate                        时间
     * @param shipNum                            船次编号
     * @param subcontractorInterimApproachPlanID 任务ID
     * @return
     */
    Observable<Integer> InsertPreAcceptanceEvaluation(int itemID, int rbcomplete, int rbtimely, String currentDate, String shipNum, int subcontractorInterimApproachPlanID);

    /**
     * 根据类型获取船舶列表
     *
     * @param type
     * @return
     */
    Observable<List<Ship>> getShipByType(String type);

    /**
     * 取消修改
     * 1. 根据type查询船舶数据, 全部设置 select = 0
     * 2. 根据当前日期, type查询计划数据, 全部设置为select = 1
     * @param type
     * @param date
     * @return
     */
    Observable<Boolean> doCancle(String type, String date);

    /**
     * 发送网络请求
     * 1. 判断新计划数据: select = 1, itemID = ""
     * 2. 判断取消的数据: select = 0, itemID != ""
     * @param type
     * @param date
     * @return
     */
    Observable<String> doCommit(String type, String date);

    /**
     * 获取分包商信息
     * @param username 分包商账号名, 如果填null, 获取所有分包商列表
     * @return
     */
    Observable<Boolean> getSubcontractor(String username);

    /**
     * 获取船舶信息
     * @param username 分包商账号名, 如果填null, 获取所有船舶列表
     * @return
     */
    Observable<Boolean> getShip(String username);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    Observable<Boolean> login(String username, String password);

    /**
     * 获取分包商预计划量
     * @return
     */
    Observable<Float> getTaskVolume(int jumpWeek);
//    Observable<TaskVolume> getTaskVolume(String account, String startDate, String endDate);

    /**
     * 根据账号获取可以显示的模块
     * @param account
     * @return
     */
    Observable<Boolean> getAppList(String account);

    /**
     * 获取每日任务需求
     * @return
     * @param jumpWeek
     */
    Observable<Double[]> getDemandDayCount(int jumpWeek);

    /**
     * 获取分包商名字
     * @return
     */
    Observable<String> getSubcontractorName();

    /**
     * 更新量方数据
     * @return
     */
    Observable<Boolean> UpdateTheAmountOfSideData(int itemID, String TheAmountOfTime, String Capacity, String DeckGauge, String Deduction);

    /**
     * 信息完善
     * @return
     */
    Observable<Boolean> InsertPerfectBoatRecord(VoyageInfoBean bean);

    /**
     * 根据position获取对应的计划
     */
    Observable<WeekTask> getWeekTaskForPosition(int position);

    /**
     * 根据position获取对应的计划
     */
    Observable<SandSample> getSampleTaskForPosition(int position);

    /**
     * 获取当前登录的分包商
     * @return
     */
    Observable<Subcontractor> getCurrentSubcontractor();

    /**
     * 获取施工船舶
     * @return
     */
    Observable<Boolean> GetConstructionBoat();

    /**
     * 删除未验收的数据
     * 对数据库数据进行重新排序
     * @return
     */
    Observable<Boolean> getWeekTaskSort(int jumpWeek);

    /**
     * 获取过砂记录
     * @param jumpWeek
     * @return
     */
    Observable<Boolean> getOverSandRecordList(int jumpWeek, String account);

    /**
     * 1.13获取验砂取样信息
     * @return
     */
    Observable<Boolean> getSandSamplingList(int jumpWeek, String account);

    /**
     * 1.17获取对应的航次完善信息明细
     * @param weekTask
     * @return
     */
    Observable<PerfectBoatRecord> getPerfectBoatRecordByItemID(WeekTask weekTask, boolean isNetwork);

    /**
     * 根据itemID获取扫描图片数据
     * @param weekTask
     * @return
     */
    Observable<ScannerImage> getScannerImageByItemID(WeekTask weekTask);

    /**
     * 压缩图片
     * @param file
     * @return
     */
    Observable<File> compressWithRx(Context context, File file);

    /**
     * 获取要提交图片的数据
     * @param sandSample
     * @return
     */
    Observable<List<SampleImageList>> getCommitImageList(SandSample sandSample);

    /**
     * 提交图片
     * @param commitList
     * @return
     */
    Observable<Boolean> commitImage(SampleImageList commitList);

    /**
     * 根据position获取过砂记录
     * @param position
     * @return
     */
    Observable<RecordList> getRecordListForPosition(int position);

    /**
     * 获取分包商航次完善扫描件类型数据
     * @return
     */
    Observable<List<ScannerListBean>> getScannerType();

    /**
     * 提交过砂记录
     * @return
     */
    Observable<Boolean> InsertOverSandRecord(RecordedSandUpdataBean bean);

    /**
     * 根据进场计划ID获取过砂记录明细（多条）
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    Observable<List<RecordedSandShowList>> GetOverSandRecordBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID);

    /**
     * 获取考勤类型
     * @return
     */
    Observable<List<AttendanceType>> GetAttendanceTypeList();

    /**
     * 提交考勤数据
     * @return
     */
    Observable<Boolean> InsertAttendanceRecord(String ItemID, int AttendanceTypeID, String Creator, String Remark, String time);

    /**
     * 获取考勤记录
     * @return
     */
    Observable<List<AttendanceRecordList>> GetAttendanceRecords(String time);

    /**
     * 根据进场ID获取验砂取样详细
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    Observable<SampleShowDatesBean> GetSandSamplingBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID);

    /**
     * 提交验砂取样数据
     * @param bean
     * @return
     */
    Observable<Boolean> InsertSandSampling(SampleShowDatesBean bean);
}
