package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/6/28  15:30
 * @desc 考勤类型
 */

public class AttendanceTypeBean {

    /**
     * ItemID : 1
     * Name : 白班
     */

    private int ItemID;
    private String Name;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
