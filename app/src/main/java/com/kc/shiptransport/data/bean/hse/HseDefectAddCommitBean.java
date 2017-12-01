package com.kc.shiptransport.data.bean.hse;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/25  17:30
 * @desc ${TODD}
 */

public class HseDefectAddCommitBean {

    /**
     * ItemID :
     * HSECheckedRecordID : 1
     * DefectTypeID : 4
     * DefectItem : xssx
     * RectificationDeadline : 7
     * Creator : csyh
     * Remark : abc
     * AttachmentList : [{"FileName":"xxx","FilePath":"https://cchk3.kingwi.org/Files/20171013/aca74d8c-33f4-499d-abd9-ab044cb8ce7a.jpeg"},{"FileName":"xxx","FilePath":"https://cchk3.kingwi.org/Files/20171013/aca74d8c-33f4-499d-abd9-ab044cb8ce7a.jpeg"}]
     */

    private int ItemID;
    private int HSECheckedRecordID;
    private int DefectTypeID;
    private String DefectItem;
    private int RectificationDeadline;
    private String Creator;
    private String Remark;
    private List<AttachmentListBean> AttachmentList;

    public HseDefectAddCommitBean(int itemID, int HSECheckedRecordID, int defectTypeID, String defectItem, int rectificationDeadline, String creator, String remark) {
        ItemID = itemID;
        this.HSECheckedRecordID = HSECheckedRecordID;
        DefectTypeID = defectTypeID;
        DefectItem = defectItem;
        RectificationDeadline = rectificationDeadline;
        Creator = creator;
        Remark = remark;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getHSECheckedRecordID() {
        return HSECheckedRecordID;
    }

    public void setHSECheckedRecordID(int HSECheckedRecordID) {
        this.HSECheckedRecordID = HSECheckedRecordID;
    }

    public int getDefectTypeID() {
        return DefectTypeID;
    }

    public void setDefectTypeID(int defectTypeID) {
        DefectTypeID = defectTypeID;
    }

    public String getDefectItem() {
        return DefectItem;
    }

    public void setDefectItem(String defectItem) {
        DefectItem = defectItem;
    }

    public int getRectificationDeadline() {
        return RectificationDeadline;
    }

    public void setRectificationDeadline(int rectificationDeadline) {
        RectificationDeadline = rectificationDeadline;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public List<AttachmentListBean> getAttachmentList() {
        return AttachmentList;
    }

    public void setAttachmentList(List<AttachmentListBean> AttachmentList) {
        this.AttachmentList = AttachmentList;
    }

    public static class AttachmentListBean {
        /**
         * FileName : xxx
         * FilePath : https://cchk3.kingwi.org/Files/20171013/aca74d8c-33f4-499d-abd9-ab044cb8ce7a.jpeg
         */

        private String FileName;
        private String FilePath;

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String FileName) {
            this.FileName = FileName;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }
    }
}
