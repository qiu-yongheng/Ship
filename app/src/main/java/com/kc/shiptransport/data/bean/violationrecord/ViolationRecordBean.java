package com.kc.shiptransport.data.bean.violationrecord;

/**
 * @author 邱永恒
 * @time 2017/12/4  16:48
 * @desc ${TODD}
 */

public class ViolationRecordBean {

    /**
     * rownumber : 1
     * ItemID : 2
     * ViolationDate : 2017-12-02
     * ViolationType : abc
     * Remark : 1233
     * ViolationUnit : 粤广州货2633
     * ViolationUnitAccount : ygzh2633
     * SystemDate : 2017-12-01T13:46:01
     * ModifyTime : 2017-12-01T13:46:01
     * SystemDateForStr : 2017-12-01 13:46:01
     * ModifyTimeForStr : 2017-12-01 13:46:01
     * Creator : chenxinxian
     * CreatorName : 陈新贤
     */

    private int rownumber;
    private int ItemID;
    private String ViolationDate;
    private String ViolationType;
    private String Remark;
    private String ViolationUnit;
    private String ViolationUnitAccount;
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

    public String getViolationDate() {
        return ViolationDate;
    }

    public void setViolationDate(String ViolationDate) {
        this.ViolationDate = ViolationDate;
    }

    public String getViolationType() {
        return ViolationType;
    }

    public void setViolationType(String ViolationType) {
        this.ViolationType = ViolationType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getViolationUnit() {
        return ViolationUnit;
    }

    public void setViolationUnit(String ViolationUnit) {
        this.ViolationUnit = ViolationUnit;
    }

    public String getViolationUnitAccount() {
        return ViolationUnitAccount;
    }

    public void setViolationUnitAccount(String ViolationUnitAccount) {
        this.ViolationUnitAccount = ViolationUnitAccount;
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
