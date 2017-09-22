package com.kc.shiptransport.data.bean.threadsandlog;

/**
 * @author 邱永恒
 * @time 2017/7/10 20:59
 * @desc 抛砂日志
 */

public class ThreadSandLogBean {

    /**
     * ItemID : 3032
     * ShipAccount : jx3
     * ShipName : 吉星3
     * ShipItemNum : 2017091810
     * PartitionNameArr : 干活好尴尬
     * PartitionCount : 2
     * LayerID : 2
     * LayerName : 第二层
     * Remark :
     * Creator : csfbs
     * CreatorName : 测试分包商
     * Quantity : 0
     * StartTime : 2017-09-21 00:00:00
     * EndTime : 2017-09-21 12:00:00
     * SystemDate : 2017-09-21 12:01:03
     * SandHandlingShipID : tl8988
     * SandHandlingShipName : 天力8988
     * IsClearance : 0
     */

    private int ItemID;
    private String ShipAccount;
    private String ShipName;
    private String ShipItemNum;
    private String PartitionNameArr;
    private int PartitionCount;
    private String LayerID;
    private String LayerName;
    private String Remark;
    private String Creator;
    private String CreatorName;
    private String Quantity;
    private String StartTime;
    private String EndTime;
    private String SystemDate;
    private String SandHandlingShipID;
    private String SandHandlingShipName;
    private int IsClearance;

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

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
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
}
