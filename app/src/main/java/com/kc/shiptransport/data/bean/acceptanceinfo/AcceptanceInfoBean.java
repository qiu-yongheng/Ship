package com.kc.shiptransport.data.bean.acceptanceinfo;

/**
 * @author 邱永恒
 * @time 2017/9/4  11:50
 * @desc 1.57 根据进场计划ID获取分包商预验收评价数据
 */

public class AcceptanceInfoBean {

    /**
     * ItemID : 6056
     * MaterialIntegrity : 3
     * MaterialTimeliness : 5
     * PreAcceptanceTime : 2017-06-08 00:00:00
     * ShipItemNum : 2017083001
     * SubcontractorInterimApproachPlanID : 415
     * SystemDate : 2017-08-30 11:17:30
     * Status : 1
     * Remark : test
     * TotalCompleteRide : 0
     * TotalCompleteSquare : 0
     * AvgSquare : 0
     * CurrentTide : 0.95
     * MaxTakeInWater : null
     * SubcontractorAccount : xgyj
     * SubcontractorName : 香港怡景
     * ShipAccount : yhzh3393
     * ShipName : 粤惠州货3393
     */

    private int ItemID;
    private float MaterialIntegrity;
    private float MaterialTimeliness;
    private String PreAcceptanceTime;
    private String ShipItemNum;
    private int SubcontractorInterimApproachPlanID;
    private String SystemDate;
    private int Status;
    private String Remark;
    private int TotalCompleteRide;
    private String TotalCompleteSquare;
    private String AvgSquare;
    private String CurrentTide;
    private String MaxTakeInWater;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String ShipAccount;
    private String ShipName;
    private String SubcontractorPerfectBoatScannerTime;

    public String getSubcontractorPerfectBoatScannerTime() {
        return SubcontractorPerfectBoatScannerTime;
    }

    public void setSubcontractorPerfectBoatScannerTime(String subcontractorPerfectBoatScannerTime) {
        SubcontractorPerfectBoatScannerTime = subcontractorPerfectBoatScannerTime;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public float getMaterialIntegrity() {
        return MaterialIntegrity;
    }

    public void setMaterialIntegrity(float MaterialIntegrity) {
        this.MaterialIntegrity = MaterialIntegrity;
    }

    public float getMaterialTimeliness() {
        return MaterialTimeliness;
    }

    public void setMaterialTimeliness(float MaterialTimeliness) {
        this.MaterialTimeliness = MaterialTimeliness;
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

    public int getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) {
        this.SubcontractorInterimApproachPlanID = SubcontractorInterimApproachPlanID;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
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

    public String getMaxTakeInWater() {
        return MaxTakeInWater;
    }

    public void setMaxTakeInWater(String MaxTakeInWater) {
        this.MaxTakeInWater = MaxTakeInWater;
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
}
