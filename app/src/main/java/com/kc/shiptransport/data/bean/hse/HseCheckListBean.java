package com.kc.shiptransport.data.bean.hse;

/**
 * @author 邱永恒
 * @time 2017/11/23  9:01
 * @desc ${TODD}
 */

public class HseCheckListBean {

    /**
     * rownumber : 1
     * ItemID : 1
     * CheckedTime : 2017-11-22 18:30:00
     * CheckedShipAccount : csgscb
     * CheckedShipName : 测试供砂船舶
     * Remark : abc
     * Creator : csyh
     * CreatorName : 测试用户
     * SystemDate : 2017-11-22 14:06:53
     */

    private int rownumber;
    private int ItemID;
    private String CheckedTime;
    private String CheckedShipAccount;
    private String CheckedShipName;
    private String Remark;
    private String Creator;
    private String CreatorName;
    private String SystemDate;

    public HseCheckListBean(int rownumber, int itemID, String checkedTime, String checkedShipAccount, String checkedShipName, String remark, String creator, String creatorName, String systemDate) {
        this.rownumber = rownumber;
        ItemID = itemID;
        CheckedTime = checkedTime;
        CheckedShipAccount = checkedShipAccount;
        CheckedShipName = checkedShipName;
        Remark = remark;
        Creator = creator;
        CreatorName = creatorName;
        SystemDate = systemDate;
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

    public String getCheckedTime() {
        return CheckedTime;
    }

    public void setCheckedTime(String CheckedTime) {
        this.CheckedTime = CheckedTime;
    }

    public String getCheckedShipAccount() {
        return CheckedShipAccount;
    }

    public void setCheckedShipAccount(String CheckedShipAccount) {
        this.CheckedShipAccount = CheckedShipAccount;
    }

    public String getCheckedShipName() {
        return CheckedShipName;
    }

    public void setCheckedShipName(String CheckedShipName) {
        this.CheckedShipName = CheckedShipName;
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

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }
}
