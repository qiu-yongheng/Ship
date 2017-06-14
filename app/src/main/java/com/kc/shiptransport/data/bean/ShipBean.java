package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/5/18  10:04
 * @desc ${TODD}
 */

public class ShipBean {

    /**
     * ItemID : 1
     * ShipID : zd881
     * ShipAccount : shipd
     * ShipName : 船舶D
     * ShipType : B类
     * MaxSandSupplyCount : 2000
     */

    private String ItemID;
    private String ShipID;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private String MaxSandSupplyCount;
    private float Capacity; // 舱容
    private float DeckGauge; // 甲板方

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

    public float getCapacity() {
        return Capacity;
    }

    public void setCapacity(float capacity) {
        Capacity = capacity;
    }

    public float getDeckGauge() {
        return DeckGauge;
    }

    public void setDeckGauge(float deckGauge) {
        DeckGauge = deckGauge;
    }
}
