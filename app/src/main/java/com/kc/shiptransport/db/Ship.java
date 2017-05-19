package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/5/16  15:38
 * @desc ${TODD}
 */

public class Ship extends DataSupport{

    /**
     * ItemID : 1
     * ShipID : Num1
     * ShipName : 誉丰联发
     * ShipType : 抛沙
     * MaxSandSupplyCount : 2000
     */
    private int id;
    private String ItemID;
    private String ShipID;
    private String ShipName;
    private String ShipType;
    private String MaxSandSupplyCount;
    private String selected;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
    }

    public String getShipID() {
        return ShipID;
    }

    public void setShipID(String ShipID) {
        this.ShipID = ShipID;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String ShipName) {
        this.ShipName = ShipName;
    }

    public String getShipType() {
        return ShipType;
    }

    public void setShipType(String ShipType) {
        this.ShipType = ShipType;
    }

    public String getMaxSandSupplyCount() {
        return MaxSandSupplyCount;
    }

    public void setMaxSandSupplyCount(String MaxSandSupplyCount) {
        this.MaxSandSupplyCount = MaxSandSupplyCount;
    }
}
