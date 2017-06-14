package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/5/19  12:32
 * @desc ${TODD}
 */

public class WeekTaskBean {

    /**
     * ItemID : 510
     * SubcontractorAccount : yflf
     * SubcontractorName : 誉丰联发
     * PlanDay : 2017-06-09
     * ShipAccount : shh233
     * ShipName : 順宏海233
     * ShipType : A类
     * SandSupplyCount : 3000.0
     * Capacity : null
     * DeckGauge : null
     * ReceptionSandTime : null
     * PreAcceptanceTime : null
     * SandSubcontractorPreAcceptanceEvaluationID : null
     *  DefaultCapacity  : null
     * defaultDeckGauge : null
     */

    private int ItemID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private double SandSupplyCount;
    private Float Capacity;
    private Float DeckGauge;
    private String ReceptionSandTime;
    private String PreAcceptanceTime;
    private String TheAmountOfTime; // 量方时间
    private int SandSubcontractorPreAcceptanceEvaluationID;
    private Float DefaultCapacity;
    private Float defaultDeckGauge;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public String getSubcontractorAccount() {
        return SubcontractorAccount;
    }

    public void setSubcontractorAccount(String subcontractorAccount) {
        SubcontractorAccount = subcontractorAccount;
    }

    public String getSubcontractorName() {
        return SubcontractorName;
    }

    public void setSubcontractorName(String subcontractorName) {
        SubcontractorName = subcontractorName;
    }

    public String getPlanDay() {
        return PlanDay;
    }

    public void setPlanDay(String planDay) {
        PlanDay = planDay;
    }

    public String getShipAccount() {
        return ShipAccount;
    }

    public void setShipAccount(String shipAccount) {
        ShipAccount = shipAccount;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String shipName) {
        ShipName = shipName;
    }

    public String getShipType() {
        return ShipType;
    }

    public void setShipType(String shipType) {
        ShipType = shipType;
    }

    public double getSandSupplyCount() {
        return SandSupplyCount;
    }

    public void setSandSupplyCount(double sandSupplyCount) {
        SandSupplyCount = sandSupplyCount;
    }

    public Float getCapacity() {
        return Capacity;
    }

    public void setCapacity(Float capacity) {
        Capacity = capacity;
    }

    public Float getDeckGauge() {
        return DeckGauge;
    }

    public void setDeckGauge(Float deckGauge) {
        DeckGauge = deckGauge;
    }

    public String getReceptionSandTime() {
        return ReceptionSandTime;
    }

    public void setReceptionSandTime(String receptionSandTime) {
        ReceptionSandTime = receptionSandTime;
    }

    public String getPreAcceptanceTime() {
        return PreAcceptanceTime;
    }

    public void setPreAcceptanceTime(String preAcceptanceTime) {
        PreAcceptanceTime = preAcceptanceTime;
    }

    public int getSandSubcontractorPreAcceptanceEvaluationID() {
        return SandSubcontractorPreAcceptanceEvaluationID;
    }

    public void setSandSubcontractorPreAcceptanceEvaluationID(int sandSubcontractorPreAcceptanceEvaluationID) {
        SandSubcontractorPreAcceptanceEvaluationID = sandSubcontractorPreAcceptanceEvaluationID;
    }

    public Float getDefaultCapacity() {
        return DefaultCapacity;
    }

    public void setDefaultCapacity(Float defaultCapacity) {
        DefaultCapacity = defaultCapacity;
    }

    public String getTheAmountOfTime() {
        return TheAmountOfTime;
    }

    public void setTheAmountOfTime(String theAmountOfTime) {
        TheAmountOfTime = theAmountOfTime;
    }

    public Float getDefaultDeckGauge() {
        return defaultDeckGauge;
    }

    public void setDefaultDeckGauge(Float defaultDeckGauge) {
        this.defaultDeckGauge = defaultDeckGauge;
    }
}
