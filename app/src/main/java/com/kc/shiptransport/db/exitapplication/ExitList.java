package com.kc.shiptransport.db.exitapplication;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/7/12  13:40
 * @desc 退场计划
 */

public class ExitList extends DataSupport{

    /**
     * ItemID : 3561
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试分包商
     * PlanDay : 2017-07-05
     * ShipAccount : bsj618
     * ShipName : 博石机618
     * ShipType : B类
     * SandSupplyCount : 0
     * IsPerfect : 0
     * TotalCompleteRide : 1
     * TotalCompleteSquare : 1800
     * AvgSquare : 1800
     * TheAmountOfTime : 2017-07-29T17:22:00
     * IsOverSand : 1
     * OverSandTime : 2017-07-05 18:02:47
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
    private int IsPerfect;
    private int TotalCompleteRide;
    private String TotalCompleteSquare;
    private String AvgSquare;
    private String TheAmountOfTime;
    private int IsOverSand;
    private String OverSandTime;

    private int IsExit;
    private String ExitTime;

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

    public int getIsPerfect() {
        return IsPerfect;
    }

    public void setIsPerfect(int IsPerfect) {
        this.IsPerfect = IsPerfect;
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

    public String getTheAmountOfTime() {
        return TheAmountOfTime;
    }

    public void setTheAmountOfTime(String TheAmountOfTime) {
        this.TheAmountOfTime = TheAmountOfTime;
    }

    public int getIsOverSand() {
        return IsOverSand;
    }

    public void setIsOverSand(int IsOverSand) {
        this.IsOverSand = IsOverSand;
    }

    public String getOverSandTime() {
        return OverSandTime;
    }

    public void setOverSandTime(String OverSandTime) {
        this.OverSandTime = OverSandTime;
    }

    public int getIsExit() {
        return IsExit;
    }

    public void setIsExit(int isExit) {
        IsExit = isExit;
    }

    public String getExitTime() {
        return ExitTime;
    }

    public void setExitTime(String exitTime) {
        ExitTime = exitTime;
    }
}
