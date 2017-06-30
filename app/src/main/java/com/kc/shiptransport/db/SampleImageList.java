package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/29  18:26
 * @desc 保存要提交的图片数据
 */

public class SampleImageList extends DataSupport{
    private int itemID;
    private int position;
    private int img_x;
    // 本地图片路径
    private String filePath;
    // 网络图片路径
    private String netPath;
    // ItemGuid
    private String ItemGuid;
    private String suffixName;
    private String fileName;


    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getImg_x() {
        return img_x;
    }

    public void setImg_x(int img_x) {
        this.img_x = img_x;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSuffixName() {
        return suffixName;
    }

    public void setSuffixName(String suffixName) {
        this.suffixName = suffixName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNetPath() {
        return netPath;
    }

    public void setNetPath(String netPath) {
        this.netPath = netPath;
    }

    public String getItemGuid() {
        return ItemGuid;
    }

    public void setItemGuid(String itemGuid) {
        ItemGuid = itemGuid;
    }
}
