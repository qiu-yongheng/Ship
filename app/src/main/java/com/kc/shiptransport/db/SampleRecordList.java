package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/20  16:52
 * @desc 保存itemID对应的图片
 */

public class SampleRecordList extends DataSupport{
    private int ItemID;
    private String image_1;
    private String image_2;
    private String sample_num;
    private int position;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public String getImage_1() {
        return image_1;
    }

    public void setImage_1(String image_1) {
        this.image_1 = image_1;
    }

    public String getImage_2() {
        return image_2;
    }

    public void setImage_2(String image_2) {
        this.image_2 = image_2;
    }

    public String getSample_num() {
        return sample_num;
    }

    public void setSample_num(String sample_num) {
        this.sample_num = sample_num;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
