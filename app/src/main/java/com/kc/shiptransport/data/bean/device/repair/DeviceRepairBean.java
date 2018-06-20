package com.kc.shiptransport.data.bean.device.repair;

import android.os.Parcelable;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/6/16  15:01
 * @desc 设备维修
 */

public class DeviceRepairBean {

    /**
     * AllRowCount : 1
     * AllPageCount : 1
     * Data : [{"rownumber":1,"ItemID":1,"StartTime":"2018-06-11T15:36:28","EndTime":"2018-06-12T18:36:02","ShipAccount":"jx1","ShipName":"吉星1","RepairProject":"修理项目","RepairProcess":"修理过程","Repairman":"修理人员","Attachment":"https://cchk3.kingwi.org/Files/20180330/bfbdbc9b-4d73-4c39-8dcc-439d297e0ef2.jpeg,https://cchk3.kingwi.org/Files/20180423/961a60d4-794e-4f3a-acee-b68feeb80410.jpg,","Creator":"wd","Remark":"备注"}]
     */

    private int AllRowCount;
    private int AllPageCount;
    private List<DeviceRepairDetailBean> Data;

    public int getAllRowCount() { return AllRowCount;}

    public void setAllRowCount(int AllRowCount) { this.AllRowCount = AllRowCount;}

    public int getAllPageCount() { return AllPageCount;}

    public void setAllPageCount(int AllPageCount) { this.AllPageCount = AllPageCount;}

    public List<DeviceRepairDetailBean> getData() { return Data;}

    public void setData(List<DeviceRepairDetailBean> Data) { this.Data = Data;}

}
