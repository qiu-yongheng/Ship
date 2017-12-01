package com.kc.shiptransport.data.bean.hse.rectification;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/28  15:59
 * @desc HSE整改提交数据
 */

public class HseRectificationCommitBean {

    /**
     * ItemID :
     * DefectRecordID : 2
     * HSECheckedRecordID : 1
     * RectificationTime : 2017-11-27 10:48
     * Creator : csyh
     * Remark : abc
     * IsSubmitted : 1
     * AttachmentList : [{"FileName":"xxx","FilePath":"https://cchk3.kingwi.org/Files/20171013/aca74d8c-33f4-499d-abd9-ab044cb8ce7a.jpeg"},{"FileName":"xxx","FilePath":"https://cchk3.kingwi.org/Files/20171013/aca74d8c-33f4-499d-abd9-ab044cb8ce7a.jpeg"}]
     */

    private int ItemID;
    private int DefectRecordID;
    private int HSECheckedRecordID;
    private String RectificationTime;
    private String Creator;
    private String Remark;
    private int IsSubmitted;
    private List<AttachmentListBean> AttachmentList;

    public HseRectificationCommitBean(int itemID, int defectRecordID, int HSECheckedRecordID, String rectificationTime, String creator, String remark, int isSubmitted, List<AttachmentListBean> attachmentList) {
        ItemID = itemID;
        DefectRecordID = defectRecordID;
        this.HSECheckedRecordID = HSECheckedRecordID;
        RectificationTime = rectificationTime;
        Creator = creator;
        Remark = remark;
        IsSubmitted = isSubmitted;
        AttachmentList = attachmentList;
    }

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

    public int getHSECheckedRecordID() {
        return HSECheckedRecordID;
    }

    public void setHSECheckedRecordID(int HSECheckedRecordID) {
        this.HSECheckedRecordID = HSECheckedRecordID;
    }

    public String getRectificationTime() {
        return RectificationTime;
    }

    public void setRectificationTime(String RectificationTime) {
        this.RectificationTime = RectificationTime;
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

    public int getIsSubmitted() {
        return IsSubmitted;
    }

    public void setIsSubmitted(int IsSubmitted) {
        this.IsSubmitted = IsSubmitted;
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
