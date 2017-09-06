package com.kc.shiptransport.db.exitassessor;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/9/6  17:34
 * @desc ${TODD}
 */

public class ExitAssessor extends DataSupport{

    /**
     * rownumber : 1
     * ItemID : 3003
     * SubcontractorInterimApproachPlanID : 415
     * SubcontractorAccount : xgyj
     * SubcontractorName : 香港怡景
     * PlanDay : 2017-05-26
     * ShipAccount : yhzh3393
     * ShipName : 粤惠州货3393
     * ShipType : B类
     * ShipItemNum : 2017083101
     * Captain : 陈1
     * NationPhone : 1123
     * InternalPhone : 1123
     * ArrivaOfAnchorageTime : 2017-06-08 19:30:00
     * CompartmentQuantity : 3
     * MaterialClassification : 细砂（0~3mm）
     * SandSupplyCount : 2500.0
     * IsPerfect : 1
     * IsOverSand : 1
     * OverSandTime : 2017-06-28T11:02:12.447
     * ExitTime : 2017-07-06 17:00:00
     * Creator : yflf
     * IsSumbitted : 1
     * IsExit : 0
     * RemnantAmount : 120
     * SystemDate : 2017-05-26 00:00:00
     * Remark :
     */

    private int rownumber;
    private int ItemID;
    private int SubcontractorInterimApproachPlanID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private String ShipItemNum;
    private String Captain;
    private String NationPhone;
    private String InternalPhone;
    private String ArrivaOfAnchorageTime;
    private int CompartmentQuantity;
    private String MaterialClassification;
    private double SandSupplyCount;
    private int IsPerfect;
    private int IsOverSand;
    private String OverSandTime;
    private String ExitTime;
    private String Creator;
    private int IsSumbitted;
    private int IsExit;
    private String RemnantAmount;
    private String SystemDate;
    private String Remark;

    public int getRownumber() {
        return rownumber;
    }

    public void setRownumber(int rownumber) {
        this.rownumber = rownumber;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public int getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) {
        this.SubcontractorInterimApproachPlanID = SubcontractorInterimApproachPlanID;
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

    public String getShipItemNum() {
        return ShipItemNum;
    }

    public void setShipItemNum(String ShipItemNum) {
        this.ShipItemNum = ShipItemNum;
    }

    public String getCaptain() {
        return Captain;
    }

    public void setCaptain(String Captain) {
        this.Captain = Captain;
    }

    public String getNationPhone() {
        return NationPhone;
    }

    public void setNationPhone(String NationPhone) {
        this.NationPhone = NationPhone;
    }

    public String getInternalPhone() {
        return InternalPhone;
    }

    public void setInternalPhone(String InternalPhone) {
        this.InternalPhone = InternalPhone;
    }

    public String getArrivaOfAnchorageTime() {
        return ArrivaOfAnchorageTime;
    }

    public void setArrivaOfAnchorageTime(String ArrivaOfAnchorageTime) {
        this.ArrivaOfAnchorageTime = ArrivaOfAnchorageTime;
    }

    public int getCompartmentQuantity() {
        return CompartmentQuantity;
    }

    public void setCompartmentQuantity(int CompartmentQuantity) {
        this.CompartmentQuantity = CompartmentQuantity;
    }

    public String getMaterialClassification() {
        return MaterialClassification;
    }

    public void setMaterialClassification(String MaterialClassification) {
        this.MaterialClassification = MaterialClassification;
    }

    public double getSandSupplyCount() {
        return SandSupplyCount;
    }

    public void setSandSupplyCount(double SandSupplyCount) {
        this.SandSupplyCount = SandSupplyCount;
    }

    public int getIsPerfect() {
        return IsPerfect;
    }

    public void setIsPerfect(int IsPerfect) {
        this.IsPerfect = IsPerfect;
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

    public String getExitTime() {
        return ExitTime;
    }

    public void setExitTime(String ExitTime) {
        this.ExitTime = ExitTime;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public int getIsSumbitted() {
        return IsSumbitted;
    }

    public void setIsSumbitted(int IsSumbitted) {
        this.IsSumbitted = IsSumbitted;
    }

    public int getIsExit() {
        return IsExit;
    }

    public void setIsExit(int IsExit) {
        this.IsExit = IsExit;
    }

    public String getRemnantAmount() {
        return RemnantAmount;
    }

    public void setRemnantAmount(String RemnantAmount) {
        this.RemnantAmount = RemnantAmount;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
}
