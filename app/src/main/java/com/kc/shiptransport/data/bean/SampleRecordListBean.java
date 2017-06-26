package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/6/20  16:52
 * @desc 保存itemID对应的图片
 */

public class SampleRecordListBean{
    private int ItemID;
    private String image_1;
    private String image_2;
    private String sample_num;
    private int isUpdate;

    private String name_1;
    private String type_1;

    private String name_2;
    private String type_2;



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

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getName_1() {
        return name_1;
    }

    public void setName_1(String name_1) {
        this.name_1 = name_1;
    }

    public String getType_1() {
        return type_1;
    }

    public void setType_1(String type_1) {
        this.type_1 = type_1;
    }

    public String getName_2() {
        return name_2;
    }

    public void setName_2(String name_2) {
        this.name_2 = name_2;
    }

    public String getType_2() {
        return type_2;
    }

    public void setType_2(String type_2) {
        this.type_2 = type_2;
    }
}
