package com.kc.shiptransport.data.source;

import android.content.Context;

import com.kc.shiptransport.data.bean.CommitImgListBean;
import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.PartitionSBBean;
import com.kc.shiptransport.data.bean.RecordedSandUpdataBean;
import com.kc.shiptransport.data.bean.SampleShowDatesBean;
import com.kc.shiptransport.data.bean.ScanCommitBean;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.data.bean.ScannerListBean;
import com.kc.shiptransport.data.bean.VoyageDetailBean;
import com.kc.shiptransport.data.bean.acceptanceinfo.AcceptanceInfoBean;
import com.kc.shiptransport.data.bean.downlog.DownLogBean;
import com.kc.shiptransport.data.bean.threadsandlog.ThreadSandLogBean;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.db.AppList;
import com.kc.shiptransport.db.AttendanceRecordList;
import com.kc.shiptransport.db.AttendanceType;
import com.kc.shiptransport.db.RecordList;
import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.db.SampleImageList;
import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.db.ScannerImage;
import com.kc.shiptransport.db.StoneSource;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.db.acceptanceevaluation.AcceptanceEvaluationList;
import com.kc.shiptransport.db.acceptancerank.Rank;
import com.kc.shiptransport.db.amount.AmountDetail;
import com.kc.shiptransport.db.amount.AmountOption;
import com.kc.shiptransport.db.analysis.AnalysisDetail;
import com.kc.shiptransport.db.analysis.ProgressTrack;
import com.kc.shiptransport.db.backlog.BackLog;
import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.db.down.StopOption;
import com.kc.shiptransport.db.exitapplication.ExitDetail;
import com.kc.shiptransport.db.exitassessor.ExitAssessor;
import com.kc.shiptransport.db.logmanager.LogManagerList;
import com.kc.shiptransport.db.partition.PartitionNum;
import com.kc.shiptransport.db.ship.Ship;
import com.kc.shiptransport.db.supply.SupplyDetail;
import com.kc.shiptransport.db.versionupdate.VersionUpdate;
import com.kc.shiptransport.db.voyage.PerfectBoatRecordInfo;
import com.kc.shiptransport.db.voyage.WashStoneSource;

import java.io.File;
import java.util.List;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import io.reactivex.Observable;

/**
 * @author 邱永恒
 * @time 2017/5/31 20:56
 * @desc 提供数据model的操作接口
 */

public interface DataSouceImpl {

    /**
     * 获取供应商信息, 并缓存到数据库
     */
    void getSubcontractorInfo(String username);

    /**
     * 获取所有供应商列表
     *
     * @return
     */
    Observable<List<SubcontractorList>> getSubcontractorList();

    /**
     * 获取船舶信息, 并缓存到数据库
     */
    void getShipInfo(String username);

    /**
     * 获取标题 日期 + 供应商
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
     * @param jumpWeek
     */
    Observable<List<WeekTask>> getWeekTask(int jumpWeek);

    /**
     * 计算每天任务总量
     *
     * @return
     */
    Observable<Integer[]> getDayCount();

    /**
     * 计算每天任务总量
     *
     * @return
     */
    Observable<Integer[]> getDayCount(int type);

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
    Observable<List<Ship>> getShipCategory();

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
     * @param userID
     *@param status
     * @param remark @return
     */
    Observable<Integer> InsertReceptionSandRecord(int itemID, String ReceptionSandTime, String userID, int status, String remark);

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
    Observable<Integer> getPlanMeasure(String date);

    /**
     * 供应商评价
     *
     * @param ItemID                             评价ID
     * @param MaterialIntegrity                         材料完整性
     * @param MaterialTimeliness                           材料及时性
     * @param PreAcceptanceTime                        时间
     * @param SubcontractorInterimApproachPlanID 任务ID
     * @param value
     * @return
     */
    Observable<Integer> InsertPreAcceptanceEvaluation(int ItemID,
                                                      int MaterialIntegrity,
                                                      int MaterialTimeliness,
                                                      String PreAcceptanceTime,
                                                      int SubcontractorInterimApproachPlanID,
                                                      AcceptanceInfoBean value,
                                                      int Status,
                                                      String Remark);

    /**
     * 根据类型获取船舶列表
     *
     * @param type
     * @return
     */
    Observable<Ship> getShipByType(String type);

    /**
     * 取消修改
     * 1. 根据type查询船舶数据, 全部设置 select = 0
     * 2. 根据当前日期, type查询计划数据, 全部设置为select = 1
     *
     * @param type
     * @param date
     * @return
     */
    Observable<Boolean> doCancle(String type, String date);

    /**
     * 发送网络请求
     * 1. 判断新计划数据: select = 1, itemID = ""
     * 2. 判断取消的数据: select = 0, itemID != ""
     *
     * @param type
     * @param date
     * @return
     */
    Observable<String> doCommit(String type, String date);

    /**
     * 获取供应商信息
     *
     * @param username 供应商账号名, 如果填null, 获取所有供应商列表
     * @return
     */
    Observable<Boolean> getSubcontractor(String username);

    /**
     * 获取船舶信息
     *
     * @param username 供应商账号名, 如果填null, 获取所有船舶列表
     * @return
     */
    Observable<Boolean> getShip(String username);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    Observable<Boolean> login(String username, String password);

    /**
     * 获取供应商预计划量
     *
     * @return
     */
    Observable<Integer> getTaskVolume(int jumpWeek);
    //    Observable<TaskVolume> getTaskVolume(String account, String startDate, String endDate);

    /**
     * 根据账号获取可以显示的模块
     *
     * @param account
     * @return
     */
    Observable<Boolean> getAppList(String account);

    /**
     * 获取每日任务需求
     *
     * @param jumpWeek
     * @return
     */
    Observable<Integer[]> getDemandDayCount(int jumpWeek);

    /**
     * 获取供应商名字
     *
     * @return
     */
    Observable<String> getSubcontractorName();

    /**
     * 更新量方数据
     *
     * @return
     */
    Observable<Boolean> InsertTheAmountOfSideRecord(int itemID,
                                                    String TheAmountOfTime,
                                                    String subcontractorAccount, int SubcontractorInterimApproachPlanID,
                                                    String ShipAccount,
                                                    String Capacity,
                                                    String DeckGauge,
                                                    String Deduction,
                                                    String Creator,
                                                    float LaserQuantitySand,
                                                    String TheAmountOfPersonnelID,
                                                    String TheAmountOfType,
                                                    int IsSumbitted,
                                                    String Remark);

    /**
     * 信息完善
     *
     * @param bean
     * @return
     */
    Observable<Boolean> InsertPerfectBoatRecord(VoyageDetailBean bean);

    /**
     * 根据position获取对应的计划
     */
    Observable<WeekTask> getWeekTaskForPosition(int position);

    /**
     * 根据position获取对应的计划
     */
    Observable<SandSample> getSampleTaskForItemID(int position);

    /**
     * 获取当前登录的供应商
     *
     * @return
     */
    Observable<Subcontractor> getCurrentSubcontractor();

    /**
     * 获取施工船舶
     *
     * @return
     */
    Observable<Boolean> GetConstructionBoat();

    /**
     * 删除未验收的数据
     * 对数据库数据进行重新排序
     *
     * @return
     */
    Observable<Boolean> getWeekTaskSort(int typeSupply, int jumpWeek);

    /**
     * 获取过砂记录
     *
     * @param jumpWeek
     * @return
     */
    Observable<Boolean> getOverSandRecordList(int jumpWeek, String account);

    /**
     * 1.13获取验砂取样信息
     *
     * @return
     */
    Observable<Boolean> getSandSamplingList(int jumpWeek, String account);

    /**
     * 1.17获取对应的航次完善信息明细
     *
     * @param weekTask
     * @return
     */
    Observable<PerfectBoatRecordInfo> getPerfectBoatRecordByItemID(WeekTask weekTask, boolean isNetwork);

    /**
     * 根据itemID获取扫描图片数据
     *
     * @param weekTask
     * @return
     */
    Observable<ScannerImage> getScannerImageByItemID(WeekTask weekTask);

    /**
     * 压缩图片
     *
     * @param file
     * @return
     */
    Observable<File> compressWithRx(Context context, File file);

    /**
     * 获取要提交图片的数据
     *
     * @param sandSample
     * @return
     */
    Observable<List<SampleImageList>> getCommitImageList(SandSample sandSample);

    /**
     * 提交图片
     *
     * @param commitList
     * @return
     */
    Observable<Boolean> commitImage(SampleImageList commitList);

    /**
     * 根据position获取过砂记录
     *
     * @param itemID
     * @return
     */
    Observable<RecordList> getRecordListForItemID(int itemID);

    /**
     * 获取供应商航次完善扫描件类型数据
     *
     * @return
     */
    Observable<List<ScannerListBean>> getScannerType(int subID);

    /**
     * 提交过砂记录
     *
     * @return
     */
    Observable<Boolean> InsertOverSandRecord(RecordedSandUpdataBean bean);

    /**
     * 根据进场计划ID获取过砂记录明细（多条）
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    Observable<List<RecordedSandShowList>> GetOverSandRecordBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID);

    /**
     * 获取考勤类型
     *
     * @return
     */
    Observable<List<AttendanceType>> GetAttendanceTypeList();

    /**
     * 提交考勤数据
     *
     * @return
     */
    Observable<Boolean> InsertAttendanceRecord(String ItemID, int AttendanceTypeID, String Creator, String Remark, String time);

    /**
     * 获取考勤记录
     *
     * @return
     */
    Observable<List<AttendanceRecordList>> GetAttendanceRecords(final int itemID, final String account, final String startDate, final String endDate, String Auditor);

    /**
     * 1.23根据进场计划ID获取验砂取样信息明细
     *
     * @param SubcontractorInterimApproachPlanID
     * @param isSandSampling
     * @param isExit
     * @return
     */
    Observable<SampleShowDatesBean> GetSandSamplingBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID, boolean isSandSampling, boolean isExit);

    /**
     * 提交验砂取样数据
     *
     * @param bean
     * @return
     */
    Observable<Boolean> InsertSandSampling(SampleShowDatesBean bean);

    /**
     * 根据AppPID获取要显示的图标列表
     *
     * @return
     */
    Observable<List<AppList>> getAppList(int AppPID);

    /**
     * 提交扫描件图片
     *
     * @param bean
     * @return
     */
    Observable<Boolean> InsertSubcontractorPerfectBoatScannerAttachment(ScanCommitBean bean);

    /**
     * 选择多张图片回调后, 把数据解析成将要提交的集合
     *
     * @return
     */
    Observable<List<ScanCommitBean>> getScanImgList(ImageMultipleResultEvent imageMultipleResultEvent, int subID, int typeID, String shipAccount);

    /**
     * 根据类型获取图片
     *
     * @param subID
     * @param typeID
     * @return
     */
    Observable<List<ScannerImgListByTypeBean>> GetSubcontractorPerfectBoatScannerAttachmentRecordByAttachmentTypeID(int subID, int typeID);

    /**
     * 1.30	 删除供应商航次完善扫描件表(图片信息)
     *
     * @param ItemID
     * @return
     */
    Observable<Boolean> DeleteSubcontractorPerfectBoatScannerAttachmentByItemID(int ItemID);

    /**
     * 1.33	 获取料源石场数据
     *
     * @return
     */
    Observable<List<StoneSource>> getStoneSource();

    /**
     * 1.35 获取量方信息数据
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    Observable<AmountDetail> GetTheAmountOfSideRecordBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID);

    /**
     * 1.27 提交考勤审核数据
     *
     * @return
     */
    Observable<Boolean> InsertAttendanceCheckRecord(List<AttendanceRecordList> list);

    /**
     * 1.36 提交量方图片数据
     *
     * @param json
     * @param ByteDataStr
     * @return
     */
    Observable<Boolean> InsertTheAmountOfSideAttachment(String json, String ByteDataStr);

    /**
     * 创建量方图片任务
     *
     * @param imageMultipleResultEvent
     * @param itemID
     * @param creator
     * @return
     */
    Observable<List<CommitImgListBean>> getImgList(ImageMultipleResultEvent imageMultipleResultEvent, int itemID, String creator);

    /**
     * 1.34 获取洗石场所在地数据
     *
     * @return
     */
    Observable<List<WashStoneSource>> GetWashStoreAddressOptions();

    /**
     * 1.37 删除量方图片数据
     *
     * @param ItemID
     * @return
     */
    Observable<Boolean> DeleteTheAmountOfSideAttachmentByItemID(int ItemID);

    /**
     * 1.37提交验砂图片数据
     *
     * @param json
     * @param ByteDataStr
     * @return
     */
    Observable<Boolean> InsertReceptionSandAttachment(String json, String ByteDataStr);

    /**
     * 1.39 删除验砂图片数据
     *
     * @param ItemID
     * @return
     */
    Observable<Boolean> DeleteReceptionSandAttachmentByItemID(int ItemID);

    /**
     * 1.40 根据进场计划ID获取验砂数据
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    Observable<SupplyDetail> GetReceptionSandBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID);

    /**
     * 删除过砂取样
     *
     * @param ItemID
     * @return
     */
    Observable<Boolean> DeleteSandSamplingNumRecordByItemID(int ItemID);

    /**
     * 1.17获取对应的航次完善信息明细
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    Observable<VoyageDetailBean> GetPerfectBoatRecordBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID);

    /**
     * 1.42 获取停工因素选项数据
     *
     * @return
     */
    Observable<List<StopOption>> GetStopOptions();

    /**
     * 1.43 提交施工日志（停工）数据
     *
     * @return
     */
    Observable<Boolean> InsertConstructionBoatStopDaily(int ItemID, String ShipAccount, String StartTime, String EndTime, String Creator, int StopTypeID, String remark);

    /**
     * 1.45 获取当天施工日志（开始时间默认值）
     *
     * @param CurrentDate
     * @param CurrentBoatAccount
     * @return
     */
    Observable<LogCurrentDateBean> GetConstructionBoatDefaultStartTime(String CurrentDate, String CurrentBoatAccount);

    /**
     * 根据账号, 获取抛沙分区
     *
     * @param userAccount
     * @return
     */
    Observable<List<PartitionNum>> getPartitionNum(String userAccount);

    /**
     * 获取抛砂分区数据
     *
     * @param account
     * @return
     */
    Observable<PartitionSBBean> getPartitionSBBean(String account);

    /**
     * 1.46 提交施工日志（抛砂）数据
     *
     * @param json
     * @return
     */
    Observable<Boolean> InsertConstructionBoatThrowingSandRecord(String json);

    /**
     * 获取抛砂分层
     *
     * @return
     */
    Observable<Boolean> GetConstructionLayerOptions();

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
    Observable<List<DownLogBean>> GetConstructionBoatStopDaily(int ItemID, String ShipAccount, String StartTime, String EndTime, String StopTypeID, String Creator);

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
    Observable<List<ThreadSandLogBean>> GetConstructionBoatThrowingSandList(int ItemID, String ShipAccount, String StartTime, String EndTime, String Creator);

    /**
     * 1.49 获取可以进行退场申请的数据
     *
     * @return
     */
    Observable<Boolean> GetExitApplyPendingApplicationList(int jumpWeek, String account);

    /**
     * 3.1 修改密码
     *
     * @param LoginName
     * @param OldPassword
     * @param NewPassword
     * @return
     */
    Observable<Boolean> ChangeUserPassword(String LoginName, String OldPassword, String NewPassword);

    /**
     * 1.51 根据退场ItemID,获取退场申请的数据
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    Observable<ExitDetail> GetExitApplicationRecordBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID);

    /**
     * 1.53 删除退场申请图片数据
     *
     * @param ItemID
     * @return
     */
    Observable<Boolean> DeleteExitApplicationAttachmentByItemID(int ItemID);

    /**
     * 1.52 提交退场申请图片数据
     *
     * @param json
     * @param ByteDataStr
     * @return
     */
    Observable<Boolean> InsertExitApplicationAttachment(String json, String ByteDataStr);

    /**
     * 1.50 提交退场申请数据
     *
     * @return
     */
    Observable<Boolean> InsertExitApplicationRecord(int ItemID, String ExitTime, String Creator, String Remark, String RemnantAmount, int SubcontractorInterimApproachPlanID, int isSumbitted, int status);

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
    Observable<Boolean> ChangeUserData(String LoginName, String DisplayName, int DepartmentID, String Email, String title, String Mobile, String TelephoneNumber, String sex);

    /**
     * 3.3 获取用户信息
     *
     * @param LoginName
     * @return
     */
    Observable<Boolean> GetUserDataByLoginName(String LoginName);

    /**
     * 3.4 获取部门信息
     *
     * @return
     */
    Observable<Boolean> GetDepartmentsOptions();

    /**
     * 1.18 获取对应的过砂记录信息明细
     *
     * @param itemID
     * @return
     */
    Observable<RecordedSandShowList> GetOverSandRecordByItemID(int itemID);

    /**
     * 2.5 获取供应商进场计划进度跟踪
     *
     * @param SubcontractorAccount
     * @param ShipName
     * @param StartDate
     * @param EndDate
     * @return
     */
    Observable<List<ProgressTrack>> GetSubcontractorInterimApproachPlanProgressTracking(String SubcontractorAccount, String ShipName, String StartDate, String EndDate);

    /**
     * 2.4 根据进场计划ID，获取供砂过程总表
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    Observable<AnalysisDetail> GetAllDetailBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID);

    /**
     * 1.54 获取供应商预验收评价数据
     *
     * @param PageSize
     * @param PageCount
     * @return
     */
    Observable<List<AcceptanceEvaluationList>> GetPreAcceptanceEvaluationList(int PageSize, int PageCount, String startTime, String endTime, String subShipAccount);

    /**
     * 1.55 获取供应商评分排行榜
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Observable<List<Rank>> GetSubcontractorPreAcceptanceEvaluationRanking(String startTime, String endTime);

    /**
     * 4.1 获取当前app最新版本
     *
     * @param version
     * @return
     */
    Observable<VersionUpdate> GetNewVersion(int version);

    /**
     * 3.5 获取所有用户信息（通讯录数据源）
     *
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     */
    Observable<List<Contacts>> GetMembers(int PageSize, int PageCount, String ConditionJson);

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
    Observable<Boolean> InsertSubcontractorPerfectBoatScannerRecord(String ItemID, String Creator, int SubcontractorInterimApproachPlanID, int IsSumbitted, String Remark);

    /**
     * 1.61 判断是否允许新增或者修改进场计划数据
     * @return
     */
    Observable<Boolean> IsAllowEditPlanData(String Date);

    /**
     * 1.57 根据进场计划ID获取分包商预验收评价数据
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    Observable<AcceptanceInfoBean> GetSandSubcontractorPreAcceptanceEvaluationBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID);

    /**
     * 1.60 获取量方人员信息数据
     * @return
     */
    Observable<List<AmountOption>> GetTheAmountOfPersonnelOptions();

    /**
     * 1.58 提交验砂管理船名照片(图片信息)
     * @param json
     * @param ByteDataStr
     * @return
     */
    Observable<Boolean> InsertReceptionSandBoatNameAttachment(String json, String ByteDataStr);

    /**
     * 1.59 删除验砂管理船名照片(图片信息)
     * @param ItemID
     * @return
     */
    Observable<Boolean> DeleteReceptionSandBoatNameAttachmentByItemID(int ItemID);

    /**
     * 1.63 获取可以进行退场审核的数据
     * @param PageSize
     * @param PageCount
     * @param jumpWeek
     * @param account
     * @return
     */
    Observable<Boolean> GetExitAuditPendingApplicationRecords(int PageSize, int PageCount, int jumpWeek, String account);

    /**
     * 1.65 获取退场离场反馈信息
     * @param PageSize
     * @param PageCount
     * @param startTime
     * @param endTime
     * @param shipAccount
     * @return
     */
    Observable<List<ExitAssessor>> GetExitAuditedApplicationRecords(int PageSize, int PageCount, String startTime, String endTime, String shipAccount);

    /**
     * 1.66 获取用户待办信息
     * @return
     */
    Observable<List<BackLog>> GetPendingTaskList(int PageSize, int PageCount);

    /**
     * 1.67 根据ItemID删除过砂记录信息
     * @param ItemID
     * @return
     */
    Observable<Boolean> DeleteOverSandRecordByItemID(int ItemID);

    /**
     * 根据ID, 获取待办任务
     * @param pendingID
     * @return
     */
    Observable<BackLog> getUpcomingList(String pendingID);

    /**
     * 从数据库获取数据
     * @return
     */
    Observable<List<BackLog>> getPendingForDB();

    /**
     * 获取PDF提交数据
     * @param path
     * @param subID
     * @param typeID
     * @param shipAccount
     * @return
     */
    Observable<ScanCommitBean> getPDFCommit(String path, int subID, int typeID, String shipAccount);

    /**
     * 1.68 获取施工船舶明细数据
     * @param PageSize
     * @param PageCount
     * @param startTime
     * @param endTime
     * @param shipAccount
     * @param Creator
     * @return
     */
    Observable<List<LogManagerList>> GetConstructionBoatDailyList(int PageSize, int PageCount, String startTime, String endTime, String shipAccount, String Creator);

    /**
     * 1.69 删除船舶日志停工数据
     * @param ItemID
     * @return
     */
    Observable<Boolean> DeleteConstructionBoatStopDailyByItemID(int ItemID);

    /**
     * 1.70 删除船舶抛砂数据
     * @param ItemID
     * @return
     */
    Observable<Boolean> DeleteConstructionBoatThrowingSandRecordsByItemID(int ItemID);

    /**
     * 1.49 获取退场数据(退场申请，退场审核列表)
     * @param PageSize
     * @param PageCount
     * @param jumpWeek
     * @param account
     * @return
     */
    Observable<Boolean> GetExitApplicationList(int PageSize, int PageCount, int jumpWeek, String account);

    /**
     * 搜索联系人
     * @param keyWords
     * @return
     */
    Observable<List<Contacts>> searchContacts(String keyWords);

    /**
     * 统计每日船舶数
     * @param jumpWeek
     * @return
     */
    Observable<List<Integer>> getDemanDayShipCount(int jumpWeek);

    /**
     * base统计每日船舶数
     * @param jumpWeek
     * @param type
     * @return
     */
    Observable<List<Integer>> getBaseDayShipCount(int jumpWeek, int type);

    /**
     * 1.68 获取供砂船航次信息数据(近7天)
     * @param PageSize
     * @param PageCount
     * @return
     */
    Observable<Boolean> GetBoatShipItemNum(int PageSize, int PageCount, String shipAccount);
}
