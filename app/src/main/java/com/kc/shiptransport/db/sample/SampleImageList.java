package com.kc.shiptransport.db.sample;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/29  18:26
 * @desc 保存要提交的图片数据
 */

public class SampleImageList extends DataSupport{
    private int itemID; // 唯一条目ID
    private int SandSamplingID; // 进场计划ID
    private int SandSamplingNumID; // 取样编号ID
    private String filePath; // 本地图片路径
    private String netPath; // 网络图片路径
    private String suffixName; // 后缀名
    private String fileName; // 文件名
    private String ConstructionBoatAccount; // 施工船舶
    private String ByteDataStr; // 图片Base64
    private int position; // 取样编号在列表中的位置

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getByteDataStr() {
        return ByteDataStr;
    }

    public void setByteDataStr(String byteDataStr) {
        ByteDataStr = byteDataStr;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getSandSamplingID() {
        return SandSamplingID;
    }

    public void setSandSamplingID(int sandSamplingID) {
        SandSamplingID = sandSamplingID;
    }

    public int getSandSamplingNumID() {
        return SandSamplingNumID;
    }

    public void setSandSamplingNumID(int sandSamplingNumID) {
        SandSamplingNumID = sandSamplingNumID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getNetPath() {
        return netPath;
    }

    public void setNetPath(String netPath) {
        this.netPath = netPath;
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

    public String getConstructionBoatAccount() {
        return ConstructionBoatAccount;
    }

    public void setConstructionBoatAccount(String constructionBoatAccount) {
        ConstructionBoatAccount = constructionBoatAccount;
    }
}
