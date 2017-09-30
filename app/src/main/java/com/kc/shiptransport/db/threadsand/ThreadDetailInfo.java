package com.kc.shiptransport.db.threadsand;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/9/28  14:58
 * @desc ${TODD}
 */

public class ThreadDetailInfo extends DataSupport{

    /**
     * ItemID : 2
     * ShipAccount : jx1
     * ShipName : 吉星1
     * ShipItemNum : 2017092106
     * PartitionNameArr :
     * PartitionCount : 0
     * LayerID :
     * LayerName : 第一层
     * Creator : csyh1
     * CreatorName :
     * Quantity : 201
     * StartTime : 2017/9/22 3:11:00
     * EndTime : 2017/9/22 15:29:00
     * SystemDate : 2017/9/22 13:58:51
     * SandHandlingShipID : ydgh0768
     * SandHandlingShipName : 粤东莞货0768
     * IsClearance : 0
     * TotalMinuteForOneItem : 738
     * TotalQuantityForOneItem : 200.9999826
     * AvgQuantityForOneMinute : 0.2723577
     * TotalQuantity : 402
     * AvgQuantityForOnePartition : 0
     * PartitionRecordList : [{"ItemID":3,"LayerID":"1","LayerName":"第一层","PartitionName":"erty1234"},{"ItemID":4,"LayerID":"1","LayerName":"第一层","PartitionName":"erty123"}]
     */

    private int ItemID;
    private String ShipAccount;
    private String ShipName;
    private String ShipItemNum;
    private String PartitionNameArr;
    private int PartitionCount;
    private String LayerID;
    private String LayerName;
    private String Creator;
    private String CreatorName;
    private String Quantity;
    private String StartTime;
    private String EndTime;
    private String SystemDate;
    private String SandHandlingShipID;
    private String SandHandlingShipName;
    private int IsClearance;
    private String TotalMinuteForOneItem;
    private String TotalQuantityForOneItem;
    private String AvgQuantityForOneMinute;
    private String TotalQuantity;
    private String AvgQuantityForOnePartition;
    private String PumpShipID;
    private String PumpShipName;
    private String Remark;


    private List<PartitionRecordListBean> PartitionRecordList;

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getPumpShipID() {
        return PumpShipID;
    }

    public void setPumpShipID(String pumpShipID) {
        PumpShipID = pumpShipID;
    }

    public String getPumpShipName() {
        return PumpShipName;
    }

    public void setPumpShipName(String pumpShipName) {
        PumpShipName = pumpShipName;
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

    public String getShipItemNum() {
        return ShipItemNum;
    }

    public void setShipItemNum(String ShipItemNum) {
        this.ShipItemNum = ShipItemNum;
    }

    public String getPartitionNameArr() {
        return PartitionNameArr;
    }

    public void setPartitionNameArr(String PartitionNameArr) {
        this.PartitionNameArr = PartitionNameArr;
    }

    public int getPartitionCount() {
        return PartitionCount;
    }

    public void setPartitionCount(int PartitionCount) {
        this.PartitionCount = PartitionCount;
    }

    public String getLayerID() {
        return LayerID;
    }

    public void setLayerID(String LayerID) {
        this.LayerID = LayerID;
    }

    public String getLayerName() {
        return LayerName;
    }

    public void setLayerName(String LayerName) {
        this.LayerName = LayerName;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public void setCreatorName(String CreatorName) {
        this.CreatorName = CreatorName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }

    public String getSandHandlingShipID() {
        return SandHandlingShipID;
    }

    public void setSandHandlingShipID(String SandHandlingShipID) {
        this.SandHandlingShipID = SandHandlingShipID;
    }

    public String getSandHandlingShipName() {
        return SandHandlingShipName;
    }

    public void setSandHandlingShipName(String SandHandlingShipName) {
        this.SandHandlingShipName = SandHandlingShipName;
    }

    public int getIsClearance() {
        return IsClearance;
    }

    public void setIsClearance(int IsClearance) {
        this.IsClearance = IsClearance;
    }

    public String getTotalMinuteForOneItem() {
        return TotalMinuteForOneItem;
    }

    public void setTotalMinuteForOneItem(String TotalMinuteForOneItem) {
        this.TotalMinuteForOneItem = TotalMinuteForOneItem;
    }

    public String getTotalQuantityForOneItem() {
        return TotalQuantityForOneItem;
    }

    public void setTotalQuantityForOneItem(String TotalQuantityForOneItem) {
        this.TotalQuantityForOneItem = TotalQuantityForOneItem;
    }

    public String getAvgQuantityForOneMinute() {
        return AvgQuantityForOneMinute;
    }

    public void setAvgQuantityForOneMinute(String AvgQuantityForOneMinute) {
        this.AvgQuantityForOneMinute = AvgQuantityForOneMinute;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String TotalQuantity) {
        this.TotalQuantity = TotalQuantity;
    }

    public String getAvgQuantityForOnePartition() {
        return AvgQuantityForOnePartition;
    }

    public void setAvgQuantityForOnePartition(String AvgQuantityForOnePartition) {
        this.AvgQuantityForOnePartition = AvgQuantityForOnePartition;
    }

    public List<PartitionRecordListBean> getPartitionRecordList() {
        return PartitionRecordList;
    }

    public void setPartitionRecordList(List<PartitionRecordListBean> PartitionRecordList) {
        this.PartitionRecordList = PartitionRecordList;
    }

    public static class PartitionRecordListBean {
        /**
         * ItemID : 3
         * LayerID : 1
         * LayerName : 第一层
         * PartitionName : erty1234
         */

        private String LayerID;
        private String LayerName;
        private String PartitionName;


        public String getLayerID() {
            return LayerID;
        }

        public void setLayerID(String LayerID) {
            this.LayerID = LayerID;
        }

        public String getLayerName() {
            return LayerName;
        }

        public void setLayerName(String LayerName) {
            this.LayerName = LayerName;
        }

        public String getPartitionName() {
            return PartitionName;
        }

        public void setPartitionName(String PartitionName) {
            this.PartitionName = PartitionName;
        }
    }
}
