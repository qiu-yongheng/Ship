package com.kc.shiptransport.db.exitfeedback;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/9/8  9:07
 * @desc ${TODD}
 */

public class ExitFeedBack extends DataSupport{

    /**
     * rownumber : 1
     * ItemID : 3007
     * SubcontractorInterimApproachPlanID : 6578
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试分包商
     * PlanDay : 2017-09-03
     * ShipAccount : bsj618
     * ShipName : 博石机618
     * ShipType : B类
     * ShipItemNum : 2017090504
     * Captain : hhh
     * NationPhone : 111
     * InternalPhone : 222
     * ArrivaOfAnchorageTime : 2017-09-01 21:46:00
     * CompartmentQuantity : 0
     * MaterialClassification : 粗砂
     * SandSupplyCount : 0.0
     * IsPerfect : 1
     * IsOverSand : 1
     * OverSandTime : 2017-09-06T16:43:48.223
     * ExitTime : 2017-09-06 16:53:00
     * Creator : csfbs
     * IsSumbitted : 1
     * IsExit : 1
     * RemnantAmount : 100
     * SystemDate : 2017-09-01 16:07:27
     * Remark : 哈哈
     * TotalAmount : 250
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
    private String TotalAmount;

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

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String TotalAmount) {
        this.TotalAmount = TotalAmount;
    }
}
