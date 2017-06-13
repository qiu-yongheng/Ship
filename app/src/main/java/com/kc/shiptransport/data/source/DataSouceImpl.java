package com.kc.shiptransport.data.source;

import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.db.WeekTask;

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
     * 网络请求
     * 请求选中周的任务计划
     *
     * @param jumpWeek
     * @return
     */
    Observable<Boolean> doRefresh(int jumpWeek);

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
     * @param Capacity
     * @param DeckGauge
     * @return
     */
    Observable<Integer> updateForReceptionSandTime(int itemID, String ReceptionSandTime, String Capacity, String DeckGauge);

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
    Observable<Integer> getStayNum(String type);

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
     */
    Observable<Float[]> getDemandDayCount();

    /**
     * 获取分包商名字
     * @return
     */
    Observable<String> getSubcontractorName();
}
