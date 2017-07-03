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
     * ItemID : 415
     * ShipAccount : yhzh3393
     * ShipName : 粤惠州货3393
     * SubcontractorAccount : xgyj
     * SubcontractorName : 香港怡景
     * ShipItemNum : 2017060801
     * TheAmountOfTime : 2017-07-01 00:00:00
     * Capacity : 1400
     * DeckGauge : 1600
     * Deduction : 100
     * Creator : yflf
     * SystemDate : 2017-07-03 15:48:23
     * TotalCompleteRide : 2
     * TotalCompleteSquare : 3200
     * AvgSquare : 1600
     * TheAmountOfSideAttachmentList : [{"ItemID":"","FileName":"","FilePath":""}]
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
    private List<TheAmountOfSideAttachmentListBean> TheAmountOfSideAttachmentList;

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

    public List<TheAmountOfSideAttachmentListBean> getTheAmountOfSideAttachmentList() {
        return TheAmountOfSideAttachmentList;
    }

    public void setTheAmountOfSideAttachmentList(List<TheAmountOfSideAttachmentListBean> TheAmountOfSideAttachmentList) {
        this.TheAmountOfSideAttachmentList = TheAmountOfSideAttachmentList;
    }

    public static class TheAmountOfSideAttachmentListBean {
        /**
         * ItemID :
         * FileName :
         * FilePath :
         */

        private String ItemID;
        private String FileName;
        private String FilePath;

        public String getItemID() {
            return ItemID;
        }

        public void setItemID(String ItemID) {
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
