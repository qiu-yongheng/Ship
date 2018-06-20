package com.kc.shiptransport.data.bean.device.maintenance;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/6/20  22:12
 * @desc
 */

public class DeviceMaintenanceListBean {
    /**
     * AllRowCount : 1
     * AllPageCount : 1
     * Data : [{"rownumber":1,"ItemID":1,"StartTime":"2018-06-11 15:36:28","EndTime":"2018-06-12 18:36:00","ShipAccount":"jx1","ShipName":"吉星1","LastMaintenanceTime":"2018-06-12 18:36:00","MaintenanceProgram":"保养项目","MaintenanceType":"保养类型","TotalOperationTime":12.2,"NextMaintenanceTime":"2018-06-12 18:36:00","Attachment":"https://cchk3.kingwi.org/Files/20180330/bfbdbc9b-4d73-4c39-8dcc-439d297e0ef2.jpeg,https://cchk3.kingwi.org/Files/20180423/961a60d4-794e-4f3a-acee-b68feeb80410.jpg,","Creator":"wd"}]
     */

    private int AllRowCount;
    private int AllPageCount;
    private List<DeviceMaintenanceBean> Data;

    public int getAllRowCount() { return AllRowCount;}

    public void setAllRowCount(int AllRowCount) { this.AllRowCount = AllRowCount;}

    public int getAllPageCount() { return AllPageCount;}

    public void setAllPageCount(int AllPageCount) { this.AllPageCount = AllPageCount;}

    public List<DeviceMaintenanceBean> getData() { return Data;}

    public void setData(List<DeviceMaintenanceBean> Data) { this.Data = Data;}
}
