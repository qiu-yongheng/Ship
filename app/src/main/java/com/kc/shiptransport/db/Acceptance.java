package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/2  11:01
 * @desc 1.6返回供砂明细,根据ItemID获取对应的数据接口
 */

public class Acceptance extends DataSupport{

    /**
     * ItemID : 3561
     * SubmitDate : 2017-07-04
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试供应商
     * PlanDay : 2017-07-05
     * ShipAccount : bsj618
     * ShipName : 博石机618
     * ShipType : B类
     * DeadweightTon : 0
     * MaxTakeInWater : null
     * SandSupplyCount : 0
     * SystemDate : 2017-07-04T17:37:15.387
     * ReceptionSandTime : null
     * PreAcceptanceTime : 2017-07-05 08:59:00
     * MaterialIntegrity : 3
     * MaterialTimeliness : 5
     * CurrentTide : 0
     * ShipItemNum : 2017070501
     * DefaultCapacity : 1600
     * DefaultDeckGauge : 1400
     * batch : null
     * TotalCompleteRide : 1
     * TotalCompleteSquare : 1300
     * AvgSquare : 1300
     * IsReceptionSandTime : 0
     * IsSandSampling : 0
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
    private Float MaterialIntegrity;
    private Float MaterialTimeliness;
    private String CurrentTide;
    private String ShipItemNum;
    private String DefaultCapacity;
    private String DefaultDeckGauge;
    private String batch;
    private int TotalCompleteRide;
    private String TotalCompleteSquare;
    private String AvgSquare;
    private int IsReceptionSandTime;
    private int IsSandSampling;

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

    public Float getMaterialIntegrity() {
        return MaterialIntegrity;
    }

    public void setMaterialIntegrity(Float MaterialIntegrity) {
        this.MaterialIntegrity = MaterialIntegrity;
    }

    public Float getMaterialTimeliness() {
        return MaterialTimeliness;
    }

    public void setMaterialTimeliness(Float MaterialTimeliness) {
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
}
