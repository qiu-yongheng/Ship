package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/5/18  14:19
 * @desc 一周任务分配
 */

public class WeekTask extends DataSupport{
    private String position;
    private String selected;


    /**
     * ItemID : 6564
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试分包商
     * PlanDay : 2017-08-17
     * ShipAccount : csgscb
     * ShipName : 测试供砂船舶
     * ShipType : A类
     * SandSupplyCount : 3000
     * ReceptionSandTime : null
     * PreAcceptanceTime : null
     * SandSubcontractorPreAcceptanceEvaluationID : null
     * DefaultCapacity : 1700
     * DefaultDeckGauge : 1400
     * IsAllowUpdatePlan : 0
     * IsSumbittedPerfectBoat : 1
     * PerfectBoatItemCount : 15
     * PerfectBoatRecordID : 1020
     * IsPerfect : 1
     * IsFinshPerfectBoatScannerAttachment : 0
     * IsSumbittedPerfectBoatScanner : 0
     * PreAcceptanceEvaluationStatus : 0
     * IsAllowPreAcceptanceEvaluation : 0
     * IsTheAmountOfTime : 0
     * IsReceptionSandTime : 0
     * IsSandSampling : 0
     * IsOverSand : 1
     */

    private int ItemID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private String SandSupplyCount;
    private String ReceptionSandTime;
    private String PreAcceptanceTime;
    private int SandSubcontractorPreAcceptanceEvaluationID;
    private String DefaultCapacity;
    private String DefaultDeckGauge;
    private int IsAllowUpdatePlan;
    private int IsSumbittedPerfectBoat;
    private int PerfectBoatItemCount;
    private int PerfectBoatRecordID;
    private int IsPerfect;
    private int IsFinshPerfectBoatScannerAttachment;
    private int IsSumbittedPerfectBoatScanner;
    private int PreAcceptanceEvaluationStatus;
    private int IsAllowPreAcceptanceEvaluation;
    private int IsTheAmountOfTime;
    private int IsReceptionSandTime;
    private int IsSandSampling;
    private int IsOverSand;

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
        return DefaultDeckGauge;
    }

    public void setDefaultDeckGauge(String DefaultDeckGauge) {
        this.DefaultDeckGauge = DefaultDeckGauge;
    }

    public int getIsAllowUpdatePlan() {
        return IsAllowUpdatePlan;
    }

    public void setIsAllowUpdatePlan(int IsAllowUpdatePlan) {
        this.IsAllowUpdatePlan = IsAllowUpdatePlan;
    }

    public int getIsSumbittedPerfectBoat() {
        return IsSumbittedPerfectBoat;
    }

    public void setIsSumbittedPerfectBoat(int IsSumbittedPerfectBoat) {
        this.IsSumbittedPerfectBoat = IsSumbittedPerfectBoat;
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

    public int getIsPerfect() {
        return IsPerfect;
    }

    public void setIsPerfect(int IsPerfect) {
        this.IsPerfect = IsPerfect;
    }

    public int getIsFinshPerfectBoatScannerAttachment() {
        return IsFinshPerfectBoatScannerAttachment;
    }

    public void setIsFinshPerfectBoatScannerAttachment(int IsFinshPerfectBoatScannerAttachment) {
        this.IsFinshPerfectBoatScannerAttachment = IsFinshPerfectBoatScannerAttachment;
    }

    public int getIsSumbittedPerfectBoatScanner() {
        return IsSumbittedPerfectBoatScanner;
    }

    public void setIsSumbittedPerfectBoatScanner(int IsSumbittedPerfectBoatScanner) {
        this.IsSumbittedPerfectBoatScanner = IsSumbittedPerfectBoatScanner;
    }

    public int getPreAcceptanceEvaluationStatus() {
        return PreAcceptanceEvaluationStatus;
    }

    public void setPreAcceptanceEvaluationStatus(int PreAcceptanceEvaluationStatus) {
        this.PreAcceptanceEvaluationStatus = PreAcceptanceEvaluationStatus;
    }

    public int getIsAllowPreAcceptanceEvaluation() {
        return IsAllowPreAcceptanceEvaluation;
    }

    public void setIsAllowPreAcceptanceEvaluation(int IsAllowPreAcceptanceEvaluation) {
        this.IsAllowPreAcceptanceEvaluation = IsAllowPreAcceptanceEvaluation;
    }

    public int getIsTheAmountOfTime() {
        return IsTheAmountOfTime;
    }

    public void setIsTheAmountOfTime(int IsTheAmountOfTime) {
        this.IsTheAmountOfTime = IsTheAmountOfTime;
    }

    public int getIsReceptionSandTime() {
        return IsReceptionSandTime;
    }

    public void setIsReceptionSandTime(int IsReceptionSandTime) {
        this.IsReceptionSandTime = IsReceptionSandTime;
    }

    public int getIsSandSampling() {
        return IsSandSampling;
    }

    public void setIsSandSampling(int IsSandSampling) {
        this.IsSandSampling = IsSandSampling;
    }

    public int getIsOverSand() {
        return IsOverSand;
    }

    public void setIsOverSand(int IsOverSand) {
        this.IsOverSand = IsOverSand;
    }
}
