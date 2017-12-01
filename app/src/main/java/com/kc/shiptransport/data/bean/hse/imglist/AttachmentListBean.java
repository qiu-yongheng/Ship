package com.kc.shiptransport.data.bean.hse.imglist;

/**
 * @author 邱永恒
 * @time 2017/11/28  18:06
 * @desc ${TODD}
 */

public class AttachmentListBean {
    /**
     * ItemID : 1
     * DefectRecordID : 4
     * FilePath : https://cchk3.kingwi.org/Files/20171013/aca74d8c-33f4-499d-abd9-ab044cb8ce7a.jpeg
     * FileName : xxx
     */

    private int ItemID;
    private int DefectRecordID;
    private String FilePath;
    private String FileName;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public int getDefectRecordID() {
        return DefectRecordID;
    }

    public void setDefectRecordID(int DefectRecordID) {
        this.DefectRecordID = DefectRecordID;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }
}
