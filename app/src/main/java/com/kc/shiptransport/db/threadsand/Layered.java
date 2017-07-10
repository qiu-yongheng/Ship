package com.kc.shiptransport.db.threadsand;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/7/10  10:36
 * @desc 抛砂分层
 */

public class Layered extends DataSupport{

    /**
     * ItemID : 1
     * LayerName : 第一层
     * SortNum : 1
     */

    private int ItemID;
    private String LayerName;
    private int SortNum;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public String getLayerName() {
        return LayerName;
    }

    public void setLayerName(String LayerName) {
        this.LayerName = LayerName;
    }

    public int getSortNum() {
        return SortNum;
    }

    public void setSortNum(int SortNum) {
        this.SortNum = SortNum;
    }
}
