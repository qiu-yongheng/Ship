package com.kc.shiptransport.db.bcf;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/9/27  14:48
 * @desc ${TODD}
 */

public class BCFShip extends DataSupport{

    /**
     * rownumber : 1
     * ItemID : 1
     * ShipName : ca21
     * ShipAccount : ca21
     * SortNum : 1
     */

    private int rownumber;
    private int ItemID;
    private String ShipName;
    private String ShipAccount;
    private int SortNum;

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

    public int getSortNum() {
        return SortNum;
    }

    public void setSortNum(int SortNum) {
        this.SortNum = SortNum;
    }
}
