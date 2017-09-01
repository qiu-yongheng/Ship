package com.kc.shiptransport.db.amount;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/3  15:10
 * @desc 量方详情
 */

public class AmountDetail extends DataSupport{

    /**
     * ItemID : 3012
     * ShipAccount : yhzh3393
     * ShipName : 粤惠州货3393
     * SubcontractorAccount : xgyj
     * SubcontractorName : 香港怡景
     * ShipItemNum : 2017083101
     * TheAmountOfTime : 2017-07-01 00:00:00
     * Capacity : 1400
     * DeckGauge : 1600
     * Deduction : 100
     * Creator : yflf
     * SystemDate : 2017-08-31 13:47:50
     * TotalCompleteRide : 0
     * TotalCompleteSquare : 0
     * AvgSquare : 0
     * LaserQuantitySand : 10
     * TheAmountOfType : 人工量砂
     * TheAmountOfPersonnelAccount : liangfangrenyuan1
     * TheAmountOfPersonnelName : 量方人员1
     * Remark : test
     * TheAmountOfSideAttachmentList : [{"ItemID":1030,"FileName":"IMG_20170724164913.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170728/4399f25f-1e5d-4876-affe-49517fb9c5c7.jpeg"},{"ItemID":28,"FileName":"Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170720/283da871-9552-4a53-b846-2daeb134d77c.png"}]
     */

    private int ItemID;
    private String ShipAccount;
    private String ShipName;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String ShipItemNum;
    private String TheAmountOfTime;
    private String Capacity;
    private String DeckGauge;
    private String Deduction;
    private String Creator;
    private String SystemDate;
    private String TotalCompleteRide;
    private String TotalCompleteSquare;
    private String AvgSquare;
    private String LaserQuantitySand;
    private String TheAmountOfType;
    private String TheAmountOfPersonnelAccount;
    private String TheAmountOfPersonnelName;
    private String Remark;
    private int IsSumbitted;
    private List<TheAmountOfSideAttachmentListBean> TheAmountOfSideAttachmentList;

    public int getIsSumbitted() {
        return IsSumbitted;
    }

    public void setIsSumbitted(int isSumbitted) {
        IsSumbitted = isSumbitted;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
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

    public String getShipItemNum() {
        return ShipItemNum;
    }

    public void setShipItemNum(String ShipItemNum) {
        this.ShipItemNum = ShipItemNum;
    }

    public String getTheAmountOfTime() {
        return TheAmountOfTime;
    }

    public void setTheAmountOfTime(String TheAmountOfTime) {
        this.TheAmountOfTime = TheAmountOfTime;
    }

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String Capacity) {
        this.Capacity = Capacity;
    }

    public String getDeckGauge() {
        return DeckGauge;
    }

    public void setDeckGauge(String DeckGauge) {
        this.DeckGauge = DeckGauge;
    }

    public String getDeduction() {
        return Deduction;
    }

    public void setDeduction(String Deduction) {
        this.Deduction = Deduction;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
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

    public String getLaserQuantitySand() {
        return LaserQuantitySand;
    }

    public void setLaserQuantitySand(String LaserQuantitySand) {
        this.LaserQuantitySand = LaserQuantitySand;
    }

    public String getTheAmountOfType() {
        return TheAmountOfType;
    }

    public void setTheAmountOfType(String TheAmountOfType) {
        this.TheAmountOfType = TheAmountOfType;
    }

    public String getTheAmountOfPersonnelAccount() {
        return TheAmountOfPersonnelAccount;
    }

    public void setTheAmountOfPersonnelAccount(String TheAmountOfPersonnelAccount) {
        this.TheAmountOfPersonnelAccount = TheAmountOfPersonnelAccount;
    }

    public String getTheAmountOfPersonnelName() {
        return TheAmountOfPersonnelName;
    }

    public void setTheAmountOfPersonnelName(String TheAmountOfPersonnelName) {
        this.TheAmountOfPersonnelName = TheAmountOfPersonnelName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public List<TheAmountOfSideAttachmentListBean> getTheAmountOfSideAttachmentList() {
        return TheAmountOfSideAttachmentList;
    }

    public void setTheAmountOfSideAttachmentList(List<TheAmountOfSideAttachmentListBean> TheAmountOfSideAttachmentList) {
        this.TheAmountOfSideAttachmentList = TheAmountOfSideAttachmentList;
    }

    public static class TheAmountOfSideAttachmentListBean {
        /**
         * ItemID : 1030
         * FileName : IMG_20170724164913.jpeg
         * FilePath : https://cchk3.kingwi.org/Files/20170728/4399f25f-1e5d-4876-affe-49517fb9c5c7.jpeg
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
