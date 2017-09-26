package com.kc.shiptransport.db.bcf;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/9/26  9:00
 * @desc ${TODD}
 */

public class BCFThread extends DataSupport{

    /**
     * rownumber : 1
     * ItemID : 4
     * ShipAccount : jx3
     * ShipName : 吉星3
     * StartTime : 2017-09-25 00:00:00
     * EndTime : 2017-09-25 14:34:00
     * Date : 2017-09-25
     * Creator : csfbs
     * CreatorName : 测试分包商
     * ConstructionType : 抛砂
     * Quantity : 0
     * SandHandlingShipID : tl8988
     * SandHandlingShipName : 天力8988
     * TotalMinuteForOneItem : 874
     * TotalQuantity : 0
     * TotalMinutes : 2615
     * AvgQuantityForOneMinute : 0
     * TotalQuantityForOneItem : 0
     * AvgQuantityForOnePartition : 0
     * ShipItemNum : 2017092501
     * Remark :
     * Num : 1
     * IsAllowEdit : 1
     */

    private int rownumber;
    private int ItemID;
    private String ShipAccount;
    private String ShipName;
    private String StartTime;
    private String EndTime;
    private String Date;
    private String Creator;
    private String CreatorName;
    private String ConstructionType;
    private String Quantity;
    private String SandHandlingShipID;
    private String SandHandlingShipName;
    private String TotalMinuteForOneItem;
    private String TotalQuantity;
    private String TotalMinutes;
    private String AvgQuantityForOneMinute;
    private String TotalQuantityForOneItem;
    private String AvgQuantityForOnePartition;
    private String ShipItemNum;
    private String Remark;
    private int Num;
    private int IsAllowEdit;

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

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
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

    public String getConstructionType() {
        return ConstructionType;
    }

    public void setConstructionType(String ConstructionType) {
        this.ConstructionType = ConstructionType;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
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

    public String getTotalMinuteForOneItem() {
        return TotalMinuteForOneItem;
    }

    public void setTotalMinuteForOneItem(String TotalMinuteForOneItem) {
        this.TotalMinuteForOneItem = TotalMinuteForOneItem;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String TotalQuantity) {
        this.TotalQuantity = TotalQuantity;
    }

    public String getTotalMinutes() {
        return TotalMinutes;
    }

    public void setTotalMinutes(String TotalMinutes) {
        this.TotalMinutes = TotalMinutes;
    }

    public String getAvgQuantityForOneMinute() {
        return AvgQuantityForOneMinute;
    }

    public void setAvgQuantityForOneMinute(String AvgQuantityForOneMinute) {
        this.AvgQuantityForOneMinute = AvgQuantityForOneMinute;
    }

    public String getTotalQuantityForOneItem() {
        return TotalQuantityForOneItem;
    }

    public void setTotalQuantityForOneItem(String TotalQuantityForOneItem) {
        this.TotalQuantityForOneItem = TotalQuantityForOneItem;
    }

    public String getAvgQuantityForOnePartition() {
        return AvgQuantityForOnePartition;
    }

    public void setAvgQuantityForOnePartition(String AvgQuantityForOnePartition) {
        this.AvgQuantityForOnePartition = AvgQuantityForOnePartition;
    }

    public String getShipItemNum() {
        return ShipItemNum;
    }

    public void setShipItemNum(String ShipItemNum) {
        this.ShipItemNum = ShipItemNum;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int Num) {
        this.Num = Num;
    }

    public int getIsAllowEdit() {
        return IsAllowEdit;
    }

    public void setIsAllowEdit(int IsAllowEdit) {
        this.IsAllowEdit = IsAllowEdit;
    }
}
