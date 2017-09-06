package com.kc.shiptransport.db.supply;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/4  13:54
 * @desc 验砂
 */

public class SupplyDetail extends DataSupport{

    /**
     * ItemID : 415
     * ShipAccount : yhzh3393
     * ShipName : 粤惠州货3393
     * SubcontractorAccount : xgyj
     * SubcontractorName : 香港怡景
     * ShipItemNum : 2017060801
     * ReceptionSandTime : 2017-06-02 00:00:00
     * SystemDate : 2017/5/26 0:00:00
     * TotalCompleteRide : 2
     * TotalCompleteSquare : 3200
     * AvgSquare : 1600
     * Status : 1
     * ReceptionSandAttachmentList : [{"ItemID":1,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/b0d42941-cee8-4a0c-afb9-0c1917f051d4.png"}]
     * ReceptionSandBoatNameAttachmentList : [{"ItemID":1,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/b0d42941-cee8-4a0c-afb9-0c1917f051d4.png"}]
     */

    private String ItemID;
    private String SubcontractorInterimApproachPlanID;
    private String ShipAccount;
    private String ShipName;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String ShipItemNum;
    private String ReceptionSandTime;
    private String SystemDate;
    private String TotalCompleteRide;
    private String TotalCompleteSquare;
    private String AvgSquare;
    private String Status;
    private List<ReceptionSandAttachmentListBean> ReceptionSandAttachmentList;
    private List<ReceptionSandBoatNameAttachmentListBean> ReceptionSandBoatNameAttachmentList;

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
    }

    public String getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(String subcontractorInterimApproachPlanID) {
        SubcontractorInterimApproachPlanID = subcontractorInterimApproachPlanID;
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

    public String getReceptionSandTime() {
        return ReceptionSandTime;
    }

    public void setReceptionSandTime(String ReceptionSandTime) {
        this.ReceptionSandTime = ReceptionSandTime;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public List<ReceptionSandAttachmentListBean> getReceptionSandAttachmentList() {
        return ReceptionSandAttachmentList;
    }

    public void setReceptionSandAttachmentList(List<ReceptionSandAttachmentListBean> ReceptionSandAttachmentList) {
        this.ReceptionSandAttachmentList = ReceptionSandAttachmentList;
    }

    public List<ReceptionSandBoatNameAttachmentListBean> getReceptionSandBoatNameAttachmentList() {
        return ReceptionSandBoatNameAttachmentList;
    }

    public void setReceptionSandBoatNameAttachmentList(List<ReceptionSandBoatNameAttachmentListBean> ReceptionSandBoatNameAttachmentList) {
        this.ReceptionSandBoatNameAttachmentList = ReceptionSandBoatNameAttachmentList;
    }

    public static class ReceptionSandAttachmentListBean {
        /**
         * ItemID : 1
         * FileName : abc.png
         * FilePath : https://cchk3.kingwi.org/Files/20170704/b0d42941-cee8-4a0c-afb9-0c1917f051d4.png
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

    public static class ReceptionSandBoatNameAttachmentListBean {
        /**
         * ItemID : 1
         * FileName : abc.png
         * FilePath : https://cchk3.kingwi.org/Files/20170704/b0d42941-cee8-4a0c-afb9-0c1917f051d4.png
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
