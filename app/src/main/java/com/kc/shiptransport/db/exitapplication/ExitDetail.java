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
     * ItemID : 1002
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
     * ArrivaOfAnchorageTime : 2017/6/8 19:30:00
     * CompartmentQuantity : 3
     * MaterialClassification : 细砂（0~3mm）
     * SandSupplyCount : 2500
     * IsPerfect : 1
     * IsOverSand : 1
     * OverSandTime : 2017/6/28 11:02:12
     * ExitTime : 2017/7/6 17:00:00
     * Creator : yflf
     * IsSumbitted : 1
     * IsExit : 0
     * RemnantAmount : 120
     * Remark :
     * SystemDate : 2017-05-26 00:00:00
     * AttachmentList : [{"ItemID":1,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170713/98202811-0e55-41d3-b4ab-82434a4e3608.png"},{"ItemID":2,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170713/eaf25704-d4c9-4f8f-a936-9d98f90cd05b.png"},{"ItemID":3002,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170818/902afe9c-d748-4368-b840-f9b927809ccb.png"}]
     */

    private String ItemID;
    private String SubcontractorInterimApproachPlanID;
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
    private String CompartmentQuantity;
    private String MaterialClassification;
    private String SandSupplyCount;
    private String IsPerfect;
    private String IsOverSand;
    private String OverSandTime;
    private String ExitTime;
    private String Creator;
    private String IsSumbitted;
    private String IsExit;
    private String RemnantAmount;
    private String Remark;
    private String SystemDate;
    private String TotalAmount;
    private List<AttachmentListBean> AttachmentList;

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
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

    public String getSandSupplyCount() {
        return SandSupplyCount;
    }

    public void setSandSupplyCount(String SandSupplyCount) {
        this.SandSupplyCount = SandSupplyCount;
    }

    public String getIsPerfect() {
        return IsPerfect;
    }

    public void setIsPerfect(String IsPerfect) {
        this.IsPerfect = IsPerfect;
    }

    public String getIsOverSand() {
        return IsOverSand;
    }

    public void setIsOverSand(String IsOverSand) {
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

    public String getIsSumbitted() {
        return IsSumbitted;
    }

    public void setIsSumbitted(String IsSumbitted) {
        this.IsSumbitted = IsSumbitted;
    }

    public String getIsExit() {
        return IsExit;
    }

    public void setIsExit(String IsExit) {
        this.IsExit = IsExit;
    }

    public String getRemnantAmount() {
        return RemnantAmount;
    }

    public void setRemnantAmount(String RemnantAmount) {
        this.RemnantAmount = RemnantAmount;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
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
