package com.kc.shiptransport.db.hse;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/11/23  13:48
 * @desc ${TODD}
 */

public class HseCheckShip extends DataSupport{

    /**
     * ShipAccount : ygzh0928
     * ShipName : 粤广州货0928
     */

    private String ShipAccount;
    private String ShipName;

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
}
