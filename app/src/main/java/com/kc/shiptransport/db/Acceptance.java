package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/2  11:01
 * @desc ${TODD}
 */

public class Acceptance extends DataSupport{
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
    private String Capacity;
    private String DeckGauge;
    private int Deduction;
    private String ReceptionSandTime;
    private String PassReceptionSandTime;
    private String TheAmountOfTime; // 量方时间
    private Float MaterialIntegrity; // 材料完整性
    private Float MaterialTimeliness; // 材料及时性
    private int TotalCompleteRide;
    private String TotalCompleteSquare;
    private String AvgSquare;
    private String CurrentTide;
    private String ShipItemNum;
    private Float DefaultCapacity; // 默认舱容
    private Float defaultDeckGauge; // 默认甲板方
    private String batch; //batch

    private String isSupply; // "1" : 已提交 "0" : 未提交
    private String isAcceptance; // "1" : 已提交 "0" : 未提交


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

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String Capacity) {
        this.Capacity = Capacity;
    }

    public String getDeckGauge() {
        return DeckGauge;
    }

    public void setDeckGauge(String DeckGauge) {
        this.DeckGauge = DeckGauge;
    }

    public String getReceptionSandTime() {
        return ReceptionSandTime;
    }

    public void setReceptionSandTime(String ReceptionSandTime) {
        this.ReceptionSandTime = ReceptionSandTime;
    }

    public String getPassReceptionSandTime() {
        return PassReceptionSandTime;
    }

    public void setPassReceptionSandTime(String PassReceptionSandTime) {
        this.PassReceptionSandTime = PassReceptionSandTime;
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

    public String getCurrentTide() {
        return CurrentTide;
    }

    public void setCurrentTide(String CurrentTide) {
        this.CurrentTide = CurrentTide;
    }

    public String getIsSupply() {
        return isSupply;
    }

    public void setIsSupply(String isSupply) {
        this.isSupply = isSupply;
    }

    public String getIsAcceptance() {
        return isAcceptance;
    }

    public void setIsAcceptance(String isAcceptance) {
        this.isAcceptance = isAcceptance;
    }

    public String getShipItemNum() {
        return ShipItemNum;
    }

    public void setShipItemNum(String shipItemNum) {
        ShipItemNum = shipItemNum;
    }

    public Float getDefaultCapacity() {
        return DefaultCapacity;
    }

    public void setDefaultCapacity(Float defaultCapacity) {
        DefaultCapacity = defaultCapacity;
    }

    public Float getDefaultDeckGauge() {
        return defaultDeckGauge;
    }

    public void setDefaultDeckGauge(Float defaultDeckGauge) {
        this.defaultDeckGauge = defaultDeckGauge;
    }

    public int getDeduction() {
        return Deduction;
    }

    public void setDeduction(int deduction) {
        Deduction = deduction;
    }

    public String getTheAmountOfTime() {
        return TheAmountOfTime;
    }

    public void setTheAmountOfTime(String theAmountOfTime) {
        TheAmountOfTime = theAmountOfTime;
    }

    public Float getMaterialIntegrity() {
        return MaterialIntegrity;
    }

    public void setMaterialIntegrity(Float materialIntegrity) {
        MaterialIntegrity = materialIntegrity;
    }

    public Float getMaterialTimeliness() {
        return MaterialTimeliness;
    }

    public void setMaterialTimeliness(Float materialTimeliness) {
        MaterialTimeliness = materialTimeliness;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
