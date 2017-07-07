package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/6/28 23:12
 * @desc 考勤记录
 */

public class AttendanceRecordList extends DataSupport {

    /**
     * ItemID : 1009
     * AttendanceTypeID : 2
     * AttendanceTypeName : 晚班
     * Creator : yflf
     * SystemDate : 2017-06-29 14:15:40
     * Remark : xxx
     * AttendanceTime : 2017-06-29 11:57:00
     * IsCheck : 0
     * RemarkForCheck : null
     * SystemDateForCheck : null
     */

    private int ItemID;
    private int AttendanceTypeID;
    private String AttendanceTypeName;
    private String Creator;
    private String CreatorName;
    private String SystemDate;
    private String Remark;
    private String AttendanceTime;
    private int IsCheck;
    private String RemarkForCheck;
    private String SystemDateForCheck;

    /**
     * 标记是否选中, 选中, 不显示审核通过与不通过的按钮
     */
    private int isSelect = 0;

    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public void setCreatorName(String creatorName) {
        CreatorName = creatorName;
    }

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

    public String getAttendanceTime() {
        return AttendanceTime;
    }

    public void setAttendanceTime(String AttendanceTime) {
        this.AttendanceTime = AttendanceTime;
    }

    public int getIsCheck() {
        return IsCheck;
    }

    public void setIsCheck(int IsCheck) {
        this.IsCheck = IsCheck;
    }

    public String getRemarkForCheck() {
        return RemarkForCheck;
    }

    public void setRemarkForCheck(String RemarkForCheck) {
        this.RemarkForCheck = RemarkForCheck;
    }

    public String getSystemDateForCheck() {
        return SystemDateForCheck;
    }

    public void setSystemDateForCheck(String SystemDateForCheck) {
        this.SystemDateForCheck = SystemDateForCheck;
    }
}
