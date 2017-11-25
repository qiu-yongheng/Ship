package com.kc.shiptransport.db.logmanager;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/9/14  8:59
 * @desc ${TODD}
 */

public class LogManagerList extends DataSupport{

    /**
     * rownumber : 1
     * ItemID : 2038
     * ShipAccount : ftb23
     * ShipName : FTB23
     * StartTime : 2017-10-11 00:00:00
     * EndTime : 2017-10-11 04:30:00
     * StopTypeName : 设备故障
     * Date : 2017-10-11
     * Creator : csfbs
     * CreatorName : 测试分包商
     * ConstructionType : 停工
     * Quantity : 0
     * SandHandlingShipID :
     * SandHandlingShipName :
     * TotalMinuteForOneItem : 0
     * TotalQuantity : 0
     * TotalMinutes : 0
     * AvgQuantityForOneMinute : 0
     * TotalQuantityForOneItem : 0
     * AvgQuantityForOnePartition : 0
     * ShipItemNum :
     * Remark : ZXC
     * LayerDetails :
     * Num : 1
     * IsAllowEdit : 1
     * StartTimeHourAndMinute : 00:00
     * EndTimeHourAndMinute : 04:30
     */

    private int rownumber;
    private int ItemID;
    private String ShipAccount;
    private String ShipName;
    private String StartTime;
    private String EndTime;
    private String StopTypeName;
    private String Date;
    private String Creator;
    private String CreatorName;
    private String ConstructionType;
    private float Quantity;
    private String SandHandlingShipID;
    private String SandHandlingShipName;
    private float TotalMinuteForOneItem;
    private float TotalQuantity;
    private float TotalMinutes;
    private float AvgQuantityForOneMinute;
    private float TotalQuantityForOneItem;
    private float AvgQuantityForOnePartition;
    private String ShipItemNum;
    private String Remark;
    private String LayerDetails;
    private int Num;
    private int IsAllowEdit;
    private String StartTimeHourAndMinute;
    private String EndTimeHourAndMinute;

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

    public String getStopTypeName() {
        return StopTypeName;
    }

    public void setStopTypeName(String StopTypeName) {
        this.StopTypeName = StopTypeName;
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

    public float getQuantity() {
        return Quantity;
    }

    public void setQuantity(float Quantity) {
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

    public float getTotalMinuteForOneItem() {
        return TotalMinuteForOneItem;
    }

    public void setTotalMinuteForOneItem(float TotalMinuteForOneItem) {
        this.TotalMinuteForOneItem = TotalMinuteForOneItem;
    }

    public float getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(float TotalQuantity) {
        this.TotalQuantity = TotalQuantity;
    }

    public float getTotalMinutes() {
        return TotalMinutes;
    }

    public void setTotalMinutes(float TotalMinutes) {
        this.TotalMinutes = TotalMinutes;
    }

    public float getAvgQuantityForOneMinute() {
        return AvgQuantityForOneMinute;
    }

    public void setAvgQuantityForOneMinute(float AvgQuantityForOneMinute) {
        this.AvgQuantityForOneMinute = AvgQuantityForOneMinute;
    }

    public float getTotalQuantityForOneItem() {
        return TotalQuantityForOneItem;
    }

    public void setTotalQuantityForOneItem(float TotalQuantityForOneItem) {
        this.TotalQuantityForOneItem = TotalQuantityForOneItem;
    }

    public float getAvgQuantityForOnePartition() {
        return AvgQuantityForOnePartition;
    }

    public void setAvgQuantityForOnePartition(float AvgQuantityForOnePartition) {
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

    public String getLayerDetails() {
        return LayerDetails;
    }

    public void setLayerDetails(String LayerDetails) {
        this.LayerDetails = LayerDetails;
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

    public String getStartTimeHourAndMinute() {
        return StartTimeHourAndMinute;
    }

    public void setStartTimeHourAndMinute(String StartTimeHourAndMinute) {
        this.StartTimeHourAndMinute = StartTimeHourAndMinute;
    }

    public String getEndTimeHourAndMinute() {
        return EndTimeHourAndMinute;
    }

    public void setEndTimeHourAndMinute(String EndTimeHourAndMinute) {
        this.EndTimeHourAndMinute = EndTimeHourAndMinute;
    }
}
