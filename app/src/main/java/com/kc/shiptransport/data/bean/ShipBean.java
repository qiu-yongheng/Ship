package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/5/18  10:04
 * @desc ${TODD}
 */

public class ShipBean {

    /**
     * ItemID : 1
     * ShipID : Num1
     * ShipName : 誉丰联发
     * ShipType : 抛沙
     * MaxSandSupplyCount : 2000
     */

    private String ItemID;
    private String ShipID;
    private String ShipName;
    private String ShipType;
    private String MaxSandSupplyCount;

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
