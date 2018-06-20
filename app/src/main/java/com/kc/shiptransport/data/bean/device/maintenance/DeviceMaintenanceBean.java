package com.kc.shiptransport.data.bean.device.maintenance;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2018/6/20  22:12
 * @desc
 */

public class DeviceMaintenanceBean extends DataSupport{
    /**
     * rownumber : 1
     * ItemID : 1
     * StartTime : 2018-06-11 15:36:28
     * EndTime : 2018-06-12 18:36:00
     * ShipAccount : jx1
     * ShipName : 吉星1
     * LastMaintenanceTime : 2018-06-12 18:36:00
     * MaintenanceProgram : 保养项目
     * MaintenanceType : 保养类型
     * TotalOperationTime : 12.2
     * NextMaintenanceTime : 2018-06-12 18:36:00
     * Attachment : https://cchk3.kingwi.org/Files/20180330/bfbdbc9b-4d73-4c39-8dcc-439d297e0ef2.jpeg,https://cchk3.kingwi.org/Files/20180423/961a60d4-794e-4f3a-acee-b68feeb80410.jpg,
     * Creator : wd
     */

    private int rownumber;
    private int ItemID;
    private String StartTime;
    private String EndTime;
    private String ShipAccount;
    private String ShipName;
    private String LastMaintenanceTime;
    private String MaintenanceProgram;
    private String MaintenanceType;
    private double TotalOperationTime;
    private String NextMaintenanceTime;
    private String Attachment;
    private String Creator;

    public int getRownumber() { return rownumber;}

    public void setRownumber(int rownumber) { this.rownumber = rownumber;}

    public int getItemID() { return ItemID;}

    public void setItemID(int ItemID) { this.ItemID = ItemID;}

    public String getStartTime() { return StartTime;}

    public void setStartTime(String StartTime) { this.StartTime = StartTime;}

    public String getEndTime() { return EndTime;}

    public void setEndTime(String EndTime) { this.EndTime = EndTime;}

    public String getShipAccount() { return ShipAccount;}

    public void setShipAccount(String ShipAccount) { this.ShipAccount = ShipAccount;}

    public String getShipName() { return ShipName;}

    public void setShipName(String ShipName) { this.ShipName = ShipName;}

    public String getLastMaintenanceTime() { return LastMaintenanceTime;}

    public void setLastMaintenanceTime(String LastMaintenanceTime) { this.LastMaintenanceTime = LastMaintenanceTime;}

    public String getMaintenanceProgram() { return MaintenanceProgram;}

    public void setMaintenanceProgram(String MaintenanceProgram) { this.MaintenanceProgram = MaintenanceProgram;}

    public String getMaintenanceType() { return MaintenanceType;}

    public void setMaintenanceType(String MaintenanceType) { this.MaintenanceType = MaintenanceType;}

    public double getTotalOperationTime() { return TotalOperationTime;}

    public void setTotalOperationTime(double TotalOperationTime) { this.TotalOperationTime = TotalOperationTime;}

    public String getNextMaintenanceTime() { return NextMaintenanceTime;}

    public void setNextMaintenanceTime(String NextMaintenanceTime) { this.NextMaintenanceTime = NextMaintenanceTime;}

    public String getAttachment() { return Attachment;}

    public void setAttachment(String Attachment) { this.Attachment = Attachment;}

    public String getCreator() { return Creator;}

    public void setCreator(String Creator) { this.Creator = Creator;}
}
