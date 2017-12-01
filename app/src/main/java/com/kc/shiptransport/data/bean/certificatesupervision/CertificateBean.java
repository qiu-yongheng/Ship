package com.kc.shiptransport.data.bean.certificatesupervision;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/12/1  9:07
 * @desc 持证监督
 */

public class CertificateBean extends DataSupport{

    /**
     * rownumber : 1
     * ItemID : 3
     * ShipName : 博石机818
     * ShipAccount : bsj818
     * UserName : 周小宾
     * SubcontractorName : 港峰工程
     * SubcontractorAccount : gfgc
     * Remark : 222
     * CertificateOptions : 蓝卡
     * SystemDate : 2017-11-24T12:11:11
     * ModifyTime : 2017-11-24T12:11:38
     * SystemDateForStr : 2017-11-24 12:11:11
     * ModifyTimeForStr : 2017-11-24 12:11:38
     * Creator : chenxinxian
     * CreatorName : 陈新贤
     */

    private int rownumber;
    private int ItemID;
    private String ShipName;
    private String ShipAccount;
    private String UserName;
    private String SubcontractorName;
    private String SubcontractorAccount;
    private String Remark;
    private String CertificateOptions;
    private String SystemDate;
    private String ModifyTime;
    private String SystemDateForStr;
    private String ModifyTimeForStr;
    private String Creator;
    private String CreatorName;

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

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String ShipName) {
        this.ShipName = ShipName;
    }

    public String getShipAccount() {
        return ShipAccount;
    }

    public void setShipAccount(String ShipAccount) {
        this.ShipAccount = ShipAccount;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getSubcontractorName() {
        return SubcontractorName;
    }

    public void setSubcontractorName(String SubcontractorName) {
        this.SubcontractorName = SubcontractorName;
    }

    public String getSubcontractorAccount() {
        return SubcontractorAccount;
    }

    public void setSubcontractorAccount(String SubcontractorAccount) {
        this.SubcontractorAccount = SubcontractorAccount;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getCertificateOptions() {
        return CertificateOptions;
    }

    public void setCertificateOptions(String CertificateOptions) {
        this.CertificateOptions = CertificateOptions;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }

    public String getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(String ModifyTime) {
        this.ModifyTime = ModifyTime;
    }

    public String getSystemDateForStr() {
        return SystemDateForStr;
    }

    public void setSystemDateForStr(String SystemDateForStr) {
        this.SystemDateForStr = SystemDateForStr;
    }

    public String getModifyTimeForStr() {
        return ModifyTimeForStr;
    }

    public void setModifyTimeForStr(String ModifyTimeForStr) {
        this.ModifyTimeForStr = ModifyTimeForStr;
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
}
