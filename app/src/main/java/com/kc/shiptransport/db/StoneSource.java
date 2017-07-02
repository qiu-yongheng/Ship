package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/7/2 8:32
 * @desc 获取料源石场数据
 */

public class StoneSource extends DataSupport{

    /**
     * ItemID : 1
     * Name : 泰盛石场
     * Address : 新会崖门
     * Mileage : 0
     * SortNum : 1
     */

    private int ItemID;
    private String Name;
    private String Address;
    private int Mileage;
    private int SortNum;

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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getMileage() {
        return Mileage;
    }

    public void setMileage(int Mileage) {
        this.Mileage = Mileage;
    }

    public int getSortNum() {
        return SortNum;
    }

    public void setSortNum(int SortNum) {
        this.SortNum = SortNum;
    }
}
