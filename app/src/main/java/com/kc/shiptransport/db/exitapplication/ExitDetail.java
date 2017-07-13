package com.kc.shiptransport.db.exitapplication;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/7/13  14:12
 * @desc 退场申请详情数据
 */


public class ExitDetail extends DataSupport{

    /**
     * SubcontractorInterimApproachPlanID : 415
     * SubcontractorAccount : xgyj
     * SubcontractorName : 香港怡景
     * PlanDay : 2017-05-26
     * ShipAccount : yhzh3393
     * ShipName : 粤惠州货3393
     * ShipType : B类
     * SandSupplyCount : 2500
     * ShipItemNum :
     * IsPerfect : 1
     * TotalCompleteRide : 0
     * TotalCompleteSquare : 0
     * AvgSquare : 0
     * OverSandTime : 2017-06-28 11:02:12
     * IsExit : 1
     * ExitTime : 2017-07-06 17:00:00
     * ExitApplicationID : 1
     * RemnantAmount : 100
     * Receiver : C3206 香港机场三跑主回填项目 振华-中交-疏浚联营
     * Captain : 陈1
     * CaptainPhone : 15914331849
     * CompartmentQuantity : 3
     * MaterialClassification : 细砂（0~3mm）
     * SystemDate : 2017-07-12 17:29:23
     * AttachmentList : [{"ItemID":1,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170713/98202811-0e55-41d3-b4ab-82434a4e3608.png"},{"ItemID":2,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170713/eaf25704-d4c9-4f8f-a936-9d98f90cd05b.png"}]
     */

    private String SubcontractorInterimApproachPlanID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private String SandSupplyCount;
    private String ShipItemNum;
    private String IsPerfect;
    private String TotalCompleteRide;
    private String TotalCompleteSquare;
    private String AvgSquare;
    private String OverSandTime;
    private String IsExit;
    private String ExitTime;
    private String ExitApplicationID;
    private String RemnantAmount;
    private String Receiver;
    private String Captain;
    private String CaptainPhone;
    private String CompartmentQuantity;
    private String MaterialClassification;
    private String SystemDate;
    private List<AttachmentListBean> AttachmentList;
    private String ArrivaOfAnchorageTime;

    public String getArrivaOfAnchorageTime() {
        return ArrivaOfAnchorageTime;
    }

    public void setArrivaOfAnchorageTime(String arrivaOfAnchorageTime) {
        ArrivaOfAnchorageTime = arrivaOfAnchorageTime;
    }

    public String getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(String SubcontractorInterimApproachPlanID) {
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

    public String getSandSupplyCount() {
        return SandSupplyCount;
    }

    public void setSandSupplyCount(String SandSupplyCount) {
        this.SandSupplyCount = SandSupplyCount;
    }

    public String getShipItemNum() {
        return ShipItemNum;
    }

    public void setShipItemNum(String ShipItemNum) {
        this.ShipItemNum = ShipItemNum;
    }

    public String getIsPerfect() {
        return IsPerfect;
    }

    public void setIsPerfect(String IsPerfect) {
        this.IsPerfect = IsPerfect;
    }

    public String getTotalCompleteRide() {
        return TotalCompleteRide;
    }

    public void setTotalCompleteRide(String TotalCompleteRide) {
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

    public String getOverSandTime() {
        return OverSandTime;
    }

    public void setOverSandTime(String OverSandTime) {
        this.OverSandTime = OverSandTime;
    }

    public String getIsExit() {
        return IsExit;
    }

    public void setIsExit(String IsExit) {
        this.IsExit = IsExit;
    }

    public String getExitTime() {
        return ExitTime;
    }

    public void setExitTime(String ExitTime) {
        this.ExitTime = ExitTime;
    }

    public String getExitApplicationID() {
        return ExitApplicationID;
    }

    public void setExitApplicationID(String ExitApplicationID) {
        this.ExitApplicationID = ExitApplicationID;
    }

    public String getRemnantAmount() {
        return RemnantAmount;
    }

    public void setRemnantAmount(String RemnantAmount) {
        this.RemnantAmount = RemnantAmount;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getCaptain() {
        return Captain;
    }

    public void setCaptain(String Captain) {
        this.Captain = Captain;
    }

    public String getCaptainPhone() {
        return CaptainPhone;
    }

    public void setCaptainPhone(String CaptainPhone) {
        this.CaptainPhone = CaptainPhone;
    }

    public String getCompartmentQuantity() {
        return CompartmentQuantity;
    }

    public void setCompartmentQuantity(String CompartmentQuantity) {
        this.CompartmentQuantity = CompartmentQuantity;
    }

    public String getMaterialClassification() {
        return MaterialClassification;
    }

    public void setMaterialClassification(String MaterialClassification) {
        this.MaterialClassification = MaterialClassification;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }

    public List<AttachmentListBean> getAttachmentList() {
        return AttachmentList;
    }

    public void setAttachmentList(List<AttachmentListBean> AttachmentList) {
        this.AttachmentList = AttachmentList;
    }

    public static class AttachmentListBean {
        /**
         * ItemID : 1
         * FileName : abc.png
         * FilePath : https://cchk3.kingwi.org/Files/20170713/98202811-0e55-41d3-b4ab-82434a4e3608.png
         */

        private int ItemID;
        private String FileName;
        private String FilePath;

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String FileName) {
            this.FileName = FileName;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }
    }
}
