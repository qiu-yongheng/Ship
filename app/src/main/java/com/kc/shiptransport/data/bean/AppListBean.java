package com.kc.shiptransport.data.bean;

/**
 * @author 邱永恒
 * @time 2017/6/10 22:27
 * @desc ${TODO}
 */

public class AppListBean {

    /**
     * AppID : 1
     * AppPID : 0
     * AppName : 电子地图
     * AppUrl : null
     */

    private String AppID;
    private String AppPID;
    private String AppName;
    private Object AppUrl;

    public String getAppID() {
        return AppID;
    }

    public void setAppID(String AppID) {
        this.AppID = AppID;
    }

    public String getAppPID() {
        return AppPID;
    }

    public void setAppPID(String AppPID) {
        this.AppPID = AppPID;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String AppName) {
        this.AppName = AppName;
    }

    public Object getAppUrl() {
        return AppUrl;
    }

    public void setAppUrl(Object AppUrl) {
        this.AppUrl = AppUrl;
    }
}
