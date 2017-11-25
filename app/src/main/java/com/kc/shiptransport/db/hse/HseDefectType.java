package com.kc.shiptransport.db.hse;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/11/24  14:10
 * @desc ${TODD}
 */

public class HseDefectType extends DataSupport{

    /**
     * rownumber : 1
     * ItemID : 1
     * Name : 船员已按要求船舶安全帽，救生衣及安全鞋
     * SortNum : 1
     */

    private int rownumber;
    private int ItemID;
    private String Name;
    private int SortNum;

    public int getRownumber() {
        return rownumber;
    }

    public void setRownumber(int rownumber) {
        this.rownumber = rownumber;
    }

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

    public int getSortNum() {
        return SortNum;
    }

    public void setSortNum(int SortNum) {
        this.SortNum = SortNum;
    }
}
