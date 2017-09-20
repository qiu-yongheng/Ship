package com.kc.shiptransport.db.thread;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/9/20  19:43
 * @desc ${TODD}
 */

public class ThreadShip extends DataSupport{

    /**
     * rownumber : 1
     * ShipAccount : csgscb
     * ShipName : 测试供砂船舶
     * ShipItemNum : 2017091811
     */

    private int rownumber;
    private String ShipAccount;
    private String ShipName;
    private String ShipItemNum;

    public int getRownumber() {
        return rownumber;
    }

    public void setRownumber(int rownumber) {
        this.rownumber = rownumber;
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

    public String getShipItemNum() {
        return ShipItemNum;
    }

    public void setShipItemNum(String ShipItemNum) {
        this.ShipItemNum = ShipItemNum;
    }
}
