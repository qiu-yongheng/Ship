package com.kc.shiptransport.db.analysis;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/7/27  10:21
 * @desc 供应商进场计划进度跟踪
 */

public class ProgressTrack extends DataSupport{

    /**
     * ItemID : 7635
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试分包商
     * PlanDay : 2017-09-29
     * ShipAccount : csgscb
     * ShipName : 测试供砂船舶
     * ShipType : A类
     * SandSupplyCount : 3000
     * StatusValue : 待验砂
     */

    private int ItemID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private int SandSupplyCount;
    private String StatusValue;

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

    public int getSandSupplyCount() {
        return SandSupplyCount;
    }

    public void setSandSupplyCount(int SandSupplyCount) {
        this.SandSupplyCount = SandSupplyCount;
    }

    public String getStatusValue() {
        return StatusValue;
    }

    public void setStatusValue(String StatusValue) {
        this.StatusValue = StatusValue;
    }
}
