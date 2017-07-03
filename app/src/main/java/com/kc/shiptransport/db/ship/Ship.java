package com.kc.shiptransport.db.ship;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/16  15:38
 * @desc ${TODD}
 */

public class Ship extends DataSupport{
    /**
     * ShipType : A类
     * Remark : A类说明
     * ShipList : [{"ShipID":"ygzh4866","ShipAccount":"ygzh4866","ShipName":"粤广州货4866","MaxSandSupplyCount":"0","Capacity":"1600","DeckGauge":"1400"}]
     */
    private String ShipType;
    private String Remark;

    // 一个ship 对应多个 shiplist
    private List<ShipList> ShipList = new ArrayList<>();

    public String getShipType() {
        return ShipType;
    }

    public void setShipType(String ShipType) {
        this.ShipType = ShipType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public List<ShipList> getShipList() {
        return ShipList;
    }

    public void setShipList(List<ShipList> ShipList) {
        this.ShipList = ShipList;
    }
}
