package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/5/18  14:19
 * @desc 一周任务分配
 */

public class WeekTask extends DataSupport{
    private int id;
    private String position;
    private String selected;


    /**
     * ItemID : 1152
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试分包商
     * PlanDay : 2017-07-03
     * ShipAccount : tl368
     * ShipName : 天力368
     * ShipType : B类
     * SandSupplyCount : 2500
     * ReceptionSandTime : 2017-07-05T19:57:00
     * PreAcceptanceTime : 2017-07-05 19:54:00
     * SandSubcontractorPreAcceptanceEvaluationID : 30
     * DefaultCapacity : 1688
     * defaultDeckGauge : 793.43
     * IsPerfect : 1
     * IsReceptionSandTime : 1
     * PerfectBoatItemCount : 14
     * PerfectBoatRecordID : 11
     * IsTheAmountOfTime : 0
     */

    private int ItemID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private String SandSupplyCount;
    /** 验砂时间 */
    private String ReceptionSandTime;
    /** 验收时间 */
    private String PreAcceptanceTime;
    /** 分包商预验收评价ID */
    private int SandSubcontractorPreAcceptanceEvaluationID;
    private String DefaultCapacity;
    private String defaultDeckGauge;
    /** 信息是否完善, 1完善, 0未完善 */
    private int IsPerfect;
    /** 1代表已验砂，0代表未验砂 */
    private int IsReceptionSandTime;
    /** 已填写字段数量 */
    private int PerfectBoatItemCount;
    /** 完善船次条目编号 */
    private int PerfectBoatRecordID;
    /** 1 已量方 0 未量方 */
    private int IsTheAmountOfTime;
    /** 1 已完善扫描件 0 未完善扫描件 */
    private int IsFinshReceptionSandAttachment;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public String getSubcontractorAccount() {
        return SubcontractorAccount;
    }

    public void setSubcontractorAccount(String SubcontractorAccount) {
        this.SubcontractorAccount = SubcontractorAccount;
    }

    public String getSubcontractorName() {
        return SubcontractorName;
    }

    public void setSubcontractorName(String SubcontractorName) {
        this.SubcontractorName = SubcontractorName;
    }

    public String getPlanDay() {
        return PlanDay;
    }

    public void setPlanDay(String PlanDay) {
        this.PlanDay = PlanDay;
    }

    public String getShipAccount() {
        return ShipAccount;
    }

    public void setShipAccount(String ShipAccount) {
        this.ShipAccount = ShipAccount;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String ShipName) {
        this.ShipName = ShipName;
    }

    public String getShipType() {
        return ShipType;
    }

    public void setShipType(String ShipType) {
        this.ShipType = ShipType;
    }

    public String getSandSupplyCount() {
        return SandSupplyCount;
    }

    public void setSandSupplyCount(String SandSupplyCount) {
        this.SandSupplyCount = SandSupplyCount;
    }

    public String getReceptionSandTime() {
        return ReceptionSandTime;
    }

    public void setReceptionSandTime(String ReceptionSandTime) {
        this.ReceptionSandTime = ReceptionSandTime;
    }

    public String getPreAcceptanceTime() {
        return PreAcceptanceTime;
    }

    public void setPreAcceptanceTime(String PreAcceptanceTime) {
        this.PreAcceptanceTime = PreAcceptanceTime;
    }

    public int getSandSubcontractorPreAcceptanceEvaluationID() {
        return SandSubcontractorPreAcceptanceEvaluationID;
    }

    public void setSandSubcontractorPreAcceptanceEvaluationID(int SandSubcontractorPreAcceptanceEvaluationID) {
        this.SandSubcontractorPreAcceptanceEvaluationID = SandSubcontractorPreAcceptanceEvaluationID;
    }

    public String getDefaultCapacity() {
        return DefaultCapacity;
    }

    public void setDefaultCapacity(String DefaultCapacity) {
        this.DefaultCapacity = DefaultCapacity;
    }

    public String getDefaultDeckGauge() {
        return defaultDeckGauge;
    }

    public void setDefaultDeckGauge(String defaultDeckGauge) {
        this.defaultDeckGauge = defaultDeckGauge;
    }

    public int getIsPerfect() {
        return IsPerfect;
    }

    public void setIsPerfect(int IsPerfect) {
        this.IsPerfect = IsPerfect;
    }

    public int getIsReceptionSandTime() {
        return IsReceptionSandTime;
    }

    public void setIsReceptionSandTime(int IsReceptionSandTime) {
        this.IsReceptionSandTime = IsReceptionSandTime;
    }

    public int getPerfectBoatItemCount() {
        return PerfectBoatItemCount;
    }

    public void setPerfectBoatItemCount(int PerfectBoatItemCount) {
        this.PerfectBoatItemCount = PerfectBoatItemCount;
    }

    public int getPerfectBoatRecordID() {
        return PerfectBoatRecordID;
    }

    public void setPerfectBoatRecordID(int PerfectBoatRecordID) {
        this.PerfectBoatRecordID = PerfectBoatRecordID;
    }

    public int getIsTheAmountOfTime() {
        return IsTheAmountOfTime;
    }

    public void setIsTheAmountOfTime(int IsTheAmountOfTime) {
        this.IsTheAmountOfTime = IsTheAmountOfTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public int getIsFinshReceptionSandAttachment() {
        return IsFinshReceptionSandAttachment;
    }

    public void setIsFinshReceptionSandAttachment(int isFinshReceptionSandAttachment) {
        IsFinshReceptionSandAttachment = isFinshReceptionSandAttachment;
    }
}
