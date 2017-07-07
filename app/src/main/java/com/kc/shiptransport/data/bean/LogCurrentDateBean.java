package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/7/7  14:45
 * @desc 1.45获取当天施工日志（停工,开始时间默认值）
 */

public class LogCurrentDateBean {

    /**
     * ShipAccount : jx6
     * ShipName : 吉星6
     * StartTime : 2017-07-08 00:00:000
     * EndTime : null
     */

    private String ShipAccount;
    private String ShipName;
    private String StartTime;
    private Object EndTime;

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

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public Object getEndTime() {
        return EndTime;
    }

    public void setEndTime(Object EndTime) {
        this.EndTime = EndTime;
    }
}
