package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/27  16:17
 * @desc 扫描图片列表
 */

public class ScannerList extends DataSupport{

    /**
     * ItemID : 1
     * Name : 碎石粉装船记录表
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
