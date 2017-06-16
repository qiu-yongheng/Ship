package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/16  16:41
 * @desc 施工船舶
 */

public class ConstructionBoat extends DataSupport{

    /**
     * ShipNum : dcoc3
     * ShipName : DCOC3
     */

    private String ShipNum;
    private String ShipName;

    public String getShipNum() {
        return ShipNum;
    }

    public void setShipNum(String shipNum) {
        ShipNum = shipNum;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String shipName) {
        ShipName = shipName;
    }
}
