package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/5/19  12:32
 * @desc ${TODD}
 */

public class WeekTaskBean {

    /**
     * ItemID : 2
     * SubcontractorAccount : yflf
     * PlanDay : 2017-05-20
     * ShipAccount : zd386
     * ShipName : 振东386
     * ShipType : A类
     * SandSupplyCount : 3000
     */

    private int ItemID;
    private String SubcontractorAccount;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private String SandSupplyCount;
    // 验砂时间
    private String ReceptionSandTime;
    // 预验收时间
    private String PassReceptionSandTime;
    // 分包商评价ID
    private int SandSubcontractorPreAcceptanceEvaluationID;

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

    public void setReceptionSandTime(String receptionSandTime) {
        ReceptionSandTime = receptionSandTime;
    }

    public String getPassReceptionSandTime() {
        return PassReceptionSandTime;
    }

    public void setPassReceptionSandTime(String passReceptionSandTime) {
        PassReceptionSandTime = passReceptionSandTime;
    }

    public int getSandSubcontractorPreAcceptanceEvaluationID() {
        return SandSubcontractorPreAcceptanceEvaluationID;
    }

    public void setSandSubcontractorPreAcceptanceEvaluationID(int sandSubcontractorPreAcceptanceEvaluationID) {
        SandSubcontractorPreAcceptanceEvaluationID = sandSubcontractorPreAcceptanceEvaluationID;
    }
}
