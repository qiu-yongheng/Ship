package com.kc.shiptransport.db.pump;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/9/28  16:48
 * @desc ${TODD}
 */

public class PumpShip extends DataSupport{

    /**
     * rownumber : 1
     * ItemID : 1
     * ShipName : ship1
     * ShipNum : ship1
     * SortNum : 1
     * ShipType : 1
     * ShipHeight : 1
     * HeavyDraft : 1
     * LightlyDrafted : 1
     * MMSI :
     */

    private int rownumber;
    private int ItemID;
    private String ShipName;
    private String ShipNum;
    private int SortNum;
    private String ShipType;
    private String ShipHeight;
    private String HeavyDraft;
    private String LightlyDrafted;
    private String MMSI;

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

    public String getShipNum() {
        return ShipNum;
    }

    public void setShipNum(String ShipNum) {
        this.ShipNum = ShipNum;
    }

    public int getSortNum() {
        return SortNum;
    }

    public void setSortNum(int SortNum) {
        this.SortNum = SortNum;
    }

    public String getShipType() {
        return ShipType;
    }

    public void setShipType(String ShipType) {
        this.ShipType = ShipType;
    }

    public String getShipHeight() {
        return ShipHeight;
    }

    public void setShipHeight(String ShipHeight) {
        this.ShipHeight = ShipHeight;
    }

    public String getHeavyDraft() {
        return HeavyDraft;
    }

    public void setHeavyDraft(String HeavyDraft) {
        this.HeavyDraft = HeavyDraft;
    }

    public String getLightlyDrafted() {
        return LightlyDrafted;
    }

    public void setLightlyDrafted(String LightlyDrafted) {
        this.LightlyDrafted = LightlyDrafted;
    }

    public String getMMSI() {
        return MMSI;
    }

    public void setMMSI(String MMSI) {
        this.MMSI = MMSI;
    }
}
