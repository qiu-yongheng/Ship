package com.kc.shiptransport.db.ship;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/7/3  11:24
 * @desc ${TODD}
 */

public class ShipList extends DataSupport {
    /**
     * ShipID : ygzh4866
     * ShipAccount : ygzh4866
     * ShipName : 粤广州货4866
     * MaxSandSupplyCount : 0
     * Capacity : 1600
     * DeckGauge : 1400
     */
    private String ShipID;
    private String ShipAccount;
    private String ShipName;
    private String MaxSandSupplyCount;
    private String Capacity;
    private String DeckGauge;

    private int isSelected;

    // 一个shiplist 对应一个ship
    private Ship ship;

    public String getShipID() {
        return ShipID;
    }

    public void setShipID(String ShipID) {
        this.ShipID = ShipID;
    }

    public String getShipAccount() {
        return ShipAccount;
    }

    public void setShipAccount(String ShipAccount) {
        this.ShipAccount = ShipAccount;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String ShipName) {
        this.ShipName = ShipName;
    }

    public String getMaxSandSupplyCount() {
        return MaxSandSupplyCount;
    }

    public void setMaxSandSupplyCount(String MaxSandSupplyCount) {
        this.MaxSandSupplyCount = MaxSandSupplyCount;
    }

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String Capacity) {
        this.Capacity = Capacity;
    }

    public String getDeckGauge() {
        return DeckGauge;
    }

    public void setDeckGauge(String DeckGauge) {
        this.DeckGauge = DeckGauge;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
