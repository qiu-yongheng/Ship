package com.kc.shiptransport.data.bean.device.repair;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2018/6/16  15:53
 * @desc
 */

public class DeviceShipBean extends DataSupport {
    /**
     * rownumber : 1
     * ShipAccount : yz105
     * ShipName : 永照105
     * ShipType : 1
     */

    private int rownumber;
    private String ShipAccount;
    private String ShipName;
    private int ShipType;

    public int getRownumber() {
        return rownumber;
    }

    public DeviceShipBean setRownumber(int rownumber) {
        this.rownumber = rownumber;
        return this;
    }

    public String getShipAccount() {
        return ShipAccount == null ? "" : ShipAccount;
    }

    public DeviceShipBean setShipAccount(String shipAccount) {
        ShipAccount = shipAccount;
        return this;
    }

    public String getShipName() {
        return ShipName == null ? "" : ShipName;
    }

    public DeviceShipBean setShipName(String shipName) {
        ShipName = shipName;
        return this;
    }

    public int getShipType() {
        return ShipType;
    }

    public DeviceShipBean setShipType(int shipType) {
        ShipType = shipType;
        return this;
    }
}
