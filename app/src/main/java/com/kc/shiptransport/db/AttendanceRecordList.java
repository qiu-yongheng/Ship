package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/6/28 23:12
 * @desc 考勤记录
 */

public class AttendanceRecordList extends DataSupport{

    /**
     * ItemID : 3
     * AttendanceTypeID : 2
     * AttendanceTypeName : 晚班
     * Creator : yflf
     * SystemDate : 2017-06-25T12:58:29.547
     * Remark : xxx
     */

    private int ItemID;
    private int AttendanceTypeID;
    private String AttendanceTypeName;
    private String Creator;
    private String SystemDate;
    private String Remark;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public int getAttendanceTypeID() {
        return AttendanceTypeID;
    }

    public void setAttendanceTypeID(int AttendanceTypeID) {
        this.AttendanceTypeID = AttendanceTypeID;
    }

    public String getAttendanceTypeName() {
        return AttendanceTypeName;
    }

    public void setAttendanceTypeName(String AttendanceTypeName) {
        this.AttendanceTypeName = AttendanceTypeName;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
}
