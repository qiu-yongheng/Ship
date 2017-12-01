package com.kc.shiptransport.data.bean.boatinquire;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/30  16:28
 * @desc ${TODD}
 */

public class BoatInquireCommitBean {

    /**
     * ItemID :
     * ShipAccount : csgscb
     * CheckDate : 2017-11-28
     * Creator : csyh
     * Remark : abc
     * List : [{"SelfCheckItemID":"1","CheckedResult":"1"},{"SelfCheckItemID":"2","CheckedResult":"1"}]
     */

    private int ItemID;
    private String ShipAccount;
    private String CheckDate;
    private String Creator;
    private String Remark;
    private List<ItemBean> List;

    public BoatInquireCommitBean(int itemID, String shipAccount, String checkDate, String creator, String remark, java.util.List<ItemBean> list) {
        ItemID = itemID;
        ShipAccount = shipAccount;
        CheckDate = checkDate;
        Creator = creator;
        Remark = remark;
        List = list;
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

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String CheckDate) {
        this.CheckDate = CheckDate;
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

    public List<ItemBean> getList() {
        return List;
    }

    public void setList(List<ItemBean> List) {
        this.List = List;
    }
}
