package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/2  11:01
 * @desc 1.6返回供砂明细,根据ItemID获取对应的数据接口
 */

public class Acceptance extends DataSupport{

    /**
     * ItemID : 415
     * SubmitDate : 2017-05-26
     * SubcontractorAccount : xgyj
     * SubcontractorName : 香港怡景
     * PlanDay : 2017-05-26
     * ShipAccount : yhzh3393
     * ShipName : 粤惠州货3393
     * ShipType : B类
     * DeadweightTon : 0
     * MaxTakeInWater : null
     * SandSupplyCount : 2500
     * SystemDate : 2017-05-26 00:00:00
     * ReceptionSandTime : null
     * PreAcceptanceTime : 2017-06-08 00:00:00
     * MaterialIntegrity : 3
     * MaterialTimeliness : 5
     * CurrentTide : 1.08
     * ShipItemNum : 2017083101
     * DefaultCapacity : 1600
     * DefaultDeckGauge : 1400
     * batch : null
     * TotalCompleteRide : 0
     * TotalCompleteSquare : 0
     * AvgSquare : 0
     * IsAllowUpdatePlan : 0
     * IsSumbittedPerfectBoat : 0
     * IsPerfect : 1
     * IsFinshPerfectBoatScannerAttachment : 0
     * IsSumbittedPerfectBoatScanner : 0
     * PreAcceptanceEvaluationStatus : -1
     * IsAllowPreAcceptanceEvaluation : 0
     * IsTheAmountOfTime : 1
     * IsReceptionSandTime : 0
     * IsSandSampling : 1
     * IsOverSand : 1
     */

    private int ItemID;
    private String SubmitDate;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private String DeadweightTon;
    private String MaxTakeInWater;
    private String SandSupplyCount;
    private String SystemDate;
    private String ReceptionSandTime;
    private String PreAcceptanceTime;
    private float MaterialIntegrity;
    private float MaterialTimeliness;
    private String CurrentTide;
    private String ShipItemNum;
    private String DefaultCapacity;
    private String DefaultDeckGauge;
    private String batch;
    private int TotalCompleteRide;
    private String TotalCompleteSquare;
    private String AvgSquare;
    private int IsAllowUpdatePlan;
    private int IsSumbittedPerfectBoat;
    private int IsPerfect;
    private int IsFinshPerfectBoatScannerAttachment;
    private int IsSumbittedPerfectBoatScanner;
    private int PreAcceptanceEvaluationStatus;
    private int IsAllowPreAcceptanceEvaluation;
    private int IsTheAmountOfTime;
    private int IsReceptionSandTime;
    private int IsSandSampling;
    private int IsOverSand;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public String getSubmitDate() {
        return SubmitDate;
    }

    public void setSubmitDate(String SubmitDate) {
        this.SubmitDate = SubmitDate;
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

    public String getDeadweightTon() {
        return DeadweightTon;
    }

    public void setDeadweightTon(String DeadweightTon) {
        this.DeadweightTon = DeadweightTon;
    }

    public String getMaxTakeInWater() {
        return MaxTakeInWater;
    }

    public void setMaxTakeInWater(String MaxTakeInWater) {
        this.MaxTakeInWater = MaxTakeInWater;
    }

    public String getSandSupplyCount() {
        return SandSupplyCount;
    }

    public void setSandSupplyCount(String SandSupplyCount) {
        this.SandSupplyCount = SandSupplyCount;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
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

    public float getMaterialIntegrity() {
        return MaterialIntegrity;
    }

    public void setMaterialIntegrity(float MaterialIntegrity) {
        this.MaterialIntegrity = MaterialIntegrity;
    }

    public float getMaterialTimeliness() {
        return MaterialTimeliness;
    }

    public void setMaterialTimeliness(float MaterialTimeliness) {
        this.MaterialTimeliness = MaterialTimeliness;
    }

    public String getCurrentTide() {
        return CurrentTide;
    }

    public void setCurrentTide(String CurrentTide) {
        this.CurrentTide = CurrentTide;
    }

    public String getShipItemNum() {
        return ShipItemNum;
    }

    public void setShipItemNum(String ShipItemNum) {
        this.ShipItemNum = ShipItemNum;
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public int getTotalCompleteRide() {
        return TotalCompleteRide;
    }

    public void setTotalCompleteRide(int TotalCompleteRide) {
        this.TotalCompleteRide = TotalCompleteRide;
    }

    public String getTotalCompleteSquare() {
        return TotalCompleteSquare;
    }

    public void setTotalCompleteSquare(String TotalCompleteSquare) {
        this.TotalCompleteSquare = TotalCompleteSquare;
    }

    public String getAvgSquare() {
        return AvgSquare;
    }

    public void setAvgSquare(String AvgSquare) {
        this.AvgSquare = AvgSquare;
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
