package com.kc.shiptransport.data.bean.downlog;

/**
 * @author qiuyongheng
 * @time 2017/7/10  15:26
 * @desc 停工日志
 */

public class DownLogBean {

    /**
     * ItemID : 0
     * ShipAccount : jx6
     * ShipName : 吉星6
     * StopTypeID : 1
     * StopTypeName : 工人休息
     * Creator : yflf
     * CreatorName : 誉丰联发
     * StartTime : 2017-07-06 17:00:00
     * EndTime : 2017-07-06 17:00:00
     * SystemDate : 2017-07-06 17:05:15
     */

    private int ItemID;
    private String ShipAccount;
    private String ShipName;
    private String StopTypeID;
    private String StopTypeName;
    private String Creator;
    private String CreatorName;
    private String StartTime;
    private String EndTime;
    private String SystemDate;
    private String Remark;
    private String PumpShipID;
    private String PumpShipName;

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

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
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

    public String getStopTypeID() {
        return StopTypeID;
    }

    public void setStopTypeID(String StopTypeID) {
        this.StopTypeID = StopTypeID;
    }

    public String getStopTypeName() {
        return StopTypeName;
    }

    public void setStopTypeName(String StopTypeName) {
        this.StopTypeName = StopTypeName;
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
}
