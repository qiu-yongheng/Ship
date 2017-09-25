package com.kc.shiptransport.db.bcf;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/9/25  16:51
 * @desc ${TODD}
 */

public class BCFLog extends DataSupport{

    /**
     * rownumber : 1
     * ItemID : 4
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试分包商
     * SandHandlingShipID : tl8988
     * SandHandlingShipName : 天力8988
     * Date : 2017-09-25 14:23:00
     * TotalAmount : 250
     * Creator : csfbs
     * CreatorName : 测试分包商
     * SystemDate : 2017-09-25 14:23:36
     * ShipItemNum : 2017092501
     * Remark : ggg
     */

    private int rownumber;
    private int ItemID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String SandHandlingShipID;
    private String SandHandlingShipName;
    private String Date;
    private String TotalAmount;
    private String Creator;
    private String CreatorName;
    private String SystemDate;
    private String ShipItemNum;
    private String Remark;

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

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String TotalAmount) {
        this.TotalAmount = TotalAmount;
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

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
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
}
