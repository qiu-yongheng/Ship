package com.kc.shiptransport.db.voyage;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/7/4  9:07
 * @desc 洗石场
 */

public class WashStoneSource extends DataSupport{

    /**
     * ItemID : 1
     * WashStoreAddress : 测试地址1
     * SortNum : 1
     */

    private int ItemID;
    private String WashStoreAddress;
    private int SortNum;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public String getWashStoreAddress() {
        return WashStoreAddress;
    }

    public void setWashStoreAddress(String WashStoreAddress) {
        this.WashStoreAddress = WashStoreAddress;
    }

    public int getSortNum() {
        return SortNum;
    }

    public void setSortNum(int SortNum) {
        this.SortNum = SortNum;
    }
}
