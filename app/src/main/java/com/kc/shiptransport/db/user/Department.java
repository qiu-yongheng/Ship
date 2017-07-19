package com.kc.shiptransport.db.user;

import com.bigkoo.pickerview.model.IPickerViewData;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/7/19  13:46
 * @desc 部门数据
 */

public class Department extends DataSupport implements IPickerViewData{

    /**
     * ItemID : 1
     * DepartmentName : 测试部门1
     * SortNum : 1
     */

    private int ItemID;
    private String DepartmentName;
    private int SortNum;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

    public int getSortNum() {
        return SortNum;
    }

    public void setSortNum(int SortNum) {
        this.SortNum = SortNum;
    }

    @Override
    public String getPickerViewText() {
        return DepartmentName;
    }
}
