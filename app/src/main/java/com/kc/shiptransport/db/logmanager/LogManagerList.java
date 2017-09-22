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
     * ItemID : 19
     * ShipAccount : ftb25
     * ShipName : FTB25
     * StartTime : 2017-07-14T00:00:00
     * EndTime : 2017-07-14T12:58:00
     * StopTypeName : 正常过砂
     * Date : 2017-07-14
     * Creator : csfbs
     * CreatorName : 测试分包商
     * Num : 1
     * IsAllowEdit : 1
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
    private int Num;
    private int IsAllowEdit;
    private String ConstructionType;
    private float Quantity; // 工程量
    private String SandHandlingShipID; // 供砂船舶账号
    private String SandHandlingShipName; // 供砂船舶名称

    public float getQuantity() {
        return Quantity;
    }

    public void setQuantity(float quantity) {
        Quantity = quantity;
    }

    public String getSandHandlingShipID() {
        return SandHandlingShipID;
    }

    public void setSandHandlingShipID(String sandHandlingShipID) {
        SandHandlingShipID = sandHandlingShipID;
    }

    public String getSandHandlingShipName() {
        return SandHandlingShipName;
    }

    public void setSandHandlingShipName(String sandHandlingShipName) {
        SandHandlingShipName = sandHandlingShipName;
    }

    public String getConstructionType() {
        return ConstructionType;
    }

    public void setConstructionType(String constructionType) {
        ConstructionType = constructionType;
    }

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
