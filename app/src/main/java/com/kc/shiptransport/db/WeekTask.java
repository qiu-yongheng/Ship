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

    private int ItemID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private int SandSupplyCount;
    private Float Capacity;
    private Float DeckGauge;
    private String ReceptionSandTime; // 验砂时间
    private String PreAcceptanceTime; // 验收时间
    private String TheAmountOfTime; // 量方时间
    private int SandSubcontractorPreAcceptanceEvaluationID;
    private Float DefaultCapacity;
    private Float defaultDeckGauge;
    private int IsPerfect; // 信息是否完善, 1完善, 0未完善
    private int PerfectBoatItemCount; // 已填写字段数量
    private int PerfectBoatRecordID; // 完善船次条目编号

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

    public int getSandSupplyCount() {
        return SandSupplyCount;
    }

    public void setSandSupplyCount(int sandSupplyCount) {
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

    public Float getDefaultDeckGauge() {
        return defaultDeckGauge;
    }

    public void setDefaultDeckGauge(Float defaultDeckGauge) {
        this.defaultDeckGauge = defaultDeckGauge;
    }

    public String getTheAmountOfTime() {
        return TheAmountOfTime;
    }

    public void setTheAmountOfTime(String theAmountOfTime) {
        TheAmountOfTime = theAmountOfTime;
    }

    public int getIsPerfect() {
        return IsPerfect;
    }

    public void setIsPerfect(int isPerfect) {
        IsPerfect = isPerfect;
    }

    public int getPerfectBoatItemCount() {
        return PerfectBoatItemCount;
    }

    public void setPerfectBoatItemCount(int perfectBoatItemCount) {
        PerfectBoatItemCount = perfectBoatItemCount;
    }

    public int getPerfectBoatRecordID() {
        return PerfectBoatRecordID;
    }

    public void setPerfectBoatRecordID(int perfectBoatRecordID) {
        PerfectBoatRecordID = perfectBoatRecordID;
    }
}
