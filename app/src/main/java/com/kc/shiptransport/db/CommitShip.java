package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/5/22  16:53
 * @desc 保存准备提交的数据
 */

public class CommitShip extends DataSupport{
    /**
     * ItemID : 1
     * ShipID : zd881
     * ShipAccount : shipd
     * ShipName : 船舶D
     * ShipType : B类
     * MaxSandSupplyCount : 2000
     */

    private int id;
    private String ItemID;
    private String ShipAccount;
    private String MaxSandSupplyCount;
    private String PlanDay;
    private String ShipType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getShipAccount() {
        return ShipAccount;
    }

    public void setShipAccount(String shipAccount) {
        ShipAccount = shipAccount;
    }

    public String getMaxSandSupplyCount() {
        return MaxSandSupplyCount;
    }

    public void setMaxSandSupplyCount(String maxSandSupplyCount) {
        MaxSandSupplyCount = maxSandSupplyCount;
    }

    public String getPlanDay() {
        return PlanDay;
    }

    public void setPlanDay(String planDay) {
        PlanDay = planDay;
    }

    public String getShipType() {
        return ShipType;
    }

    public void setShipType(String shipType) {
        ShipType = shipType;
    }
}
