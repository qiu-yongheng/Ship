package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/20  13:42
 * @desc 验砂取样
 */

public class SandSample extends DataSupport{

    /**
     * ItemID : 6564
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试分包商
     * PlanDay : 2017-08-17
     * ShipAccount : csgscb
     * ShipName : 测试供砂船舶
     * ShipType : A类
     * SandSupplyCount : 3000
     * ReceptionSandTime : 2017-08-18 17:18:00
     * PreAcceptanceTime : 2017-08-18 17:18:00
     * ShipItemNum : 2017081801
     * IsExit : 1
     * ExitTime : 2017-08-18T17:19:00
     * SandSamplingID : 0
     * IsSandSampling : 0
     */

    private int ItemID;
    private String position;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private String SandSupplyCount;
    private String ReceptionSandTime;
    private String PreAcceptanceTime;
    private String ShipItemNum;
    private int IsExit;
    private String ExitTime;
    private int SandSamplingID;
    private int IsSandSampling;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getShipItemNum() {
        return ShipItemNum;
    }

    public void setShipItemNum(String ShipItemNum) {
        this.ShipItemNum = ShipItemNum;
    }

    public int getIsExit() {
        return IsExit;
    }

    public void setIsExit(int IsExit) {
        this.IsExit = IsExit;
    }

    public String getExitTime() {
        return ExitTime;
    }

    public void setExitTime(String ExitTime) {
        this.ExitTime = ExitTime;
    }

    public int getSandSamplingID() {
        return SandSamplingID;
    }

    public void setSandSamplingID(int SandSamplingID) {
        this.SandSamplingID = SandSamplingID;
    }

    public int getIsSandSampling() {
        return IsSandSampling;
    }

    public void setIsSandSampling(int IsSandSampling) {
        this.IsSandSampling = IsSandSampling;
    }
}
