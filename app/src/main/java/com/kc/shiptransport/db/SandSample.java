package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/20  13:42
 * @desc 验砂取样
 */

public class SandSample extends DataSupport{
    /**
     * ItemID : 507
     * SubcontractorAccount : yflf
     * SubcontractorName : 誉丰联发
     * PlanDay : 2017-06-09
     * ShipAccount : ydgh0768
     * ShipName : 粤东莞货0768
     * ShipType : A类
     * SandSupplyCount : 3500
     * Capacity : 2
     * DeckGauge : 0
     * ReceptionSandTime : null
     * PreAcceptanceTime : 2017-06-08 17:20:00
     * ShipItemNum : 2017060804
     */
    private String position;

    private int ItemID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private String SandSupplyCount;
    private String Capacity;
    private String DeckGauge;
    private String ReceptionSandTime;
    private String PreAcceptanceTime;
    private String ShipItemNum;
    private int SandSamplingID; // 验砂取样编号, 如果提交过数据, 就不为0
    private int IsSandSampling; // 1代表已取样，0代表未取样（有三个取样编号，则代表已经取样）

    public int getIsSandSampling() {
        return IsSandSampling;
    }

    public void setIsSandSampling(int isSandSampling) {
        IsSandSampling = isSandSampling;
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

    public String getSandSupplyCount() {
        return SandSupplyCount;
    }

    public void setSandSupplyCount(String sandSupplyCount) {
        SandSupplyCount = sandSupplyCount;
    }

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String capacity) {
        Capacity = capacity;
    }

    public String getDeckGauge() {
        return DeckGauge;
    }

    public void setDeckGauge(String deckGauge) {
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

    public String getShipItemNum() {
        return ShipItemNum;
    }

    public void setShipItemNum(String shipItemNum) {
        ShipItemNum = shipItemNum;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSandSamplingID() {
        return SandSamplingID;
    }

    public void setSandSamplingID(int sandSamplingID) {
        SandSamplingID = sandSamplingID;
    }
}
