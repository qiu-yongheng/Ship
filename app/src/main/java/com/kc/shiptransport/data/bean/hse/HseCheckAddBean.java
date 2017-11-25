package com.kc.shiptransport.data.bean.hse;

/**
 * @author 邱永恒
 * @time 2017/11/23  15:20
 * @desc ${TODD}
 */

public class HseCheckAddBean {

    /**
     * ItemID :
     * CheckedTime : 2017-11-22 18:30
     * CheckedShipAccount : csgscb
     * Creator : csyh
     * Remark : abc
     */
    private String ItemID;
    private String CheckedTime;
    private String CheckedShipAccount;
    private String Creator;
    private String Remark;

    public HseCheckAddBean(String itemID, String checkedTime, String checkedShipAccount, String creator, String remark) {
        ItemID = itemID;
        CheckedTime = checkedTime;
        CheckedShipAccount = checkedShipAccount;
        Creator = creator;
        Remark = remark;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String ItemID) {
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

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
}
