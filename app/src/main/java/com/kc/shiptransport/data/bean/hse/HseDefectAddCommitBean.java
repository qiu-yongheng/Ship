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

    private String ItemID;
    private String HSECheckedRecordID;
    private String DefectTypeID;
    private String DefectItem;
    private String RectificationDeadline;
    private String Creator;
    private String Remark;
    private List<AttachmentListBean> AttachmentList;

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
    }

    public String getHSECheckedRecordID() {
        return HSECheckedRecordID;
    }

    public void setHSECheckedRecordID(String HSECheckedRecordID) {
        this.HSECheckedRecordID = HSECheckedRecordID;
    }

    public String getDefectTypeID() {
        return DefectTypeID;
    }

    public void setDefectTypeID(String DefectTypeID) {
        this.DefectTypeID = DefectTypeID;
    }

    public String getDefectItem() {
        return DefectItem;
    }

    public void setDefectItem(String DefectItem) {
        this.DefectItem = DefectItem;
    }

    public String getRectificationDeadline() {
        return RectificationDeadline;
    }

    public void setRectificationDeadline(String RectificationDeadline) {
        this.RectificationDeadline = RectificationDeadline;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
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
