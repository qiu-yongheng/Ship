package com.kc.shiptransport.data.bean.hse;

import java.io.Serializable;
import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/25  15:24
 * @desc ${TODD}
 */

public class HseDefectListBean implements Serializable{

    /**
     * rownumber : 1
     * ItemID : 3
     * HSECheckedRecordID : 2
     * DefectTypeID : 3
     * DefectTypeName : 消防设备
     * DefectItem : xx
     * RectificationDeadline : 3
     * RectificationDeadlineName : 3天已内
     * Remark : abc
     * Creator : csyh
     * CreatorName : 测试用户
     * SystemDate : 2017-11-22 15:34:40
     * IsSubmitted : 0
     */

    private int rownumber;
    private int ItemID;
    private int HSECheckedRecordID;
    private String CheckedShipAccount;
    private String CheckedShipName;
    private int DefectTypeID;
    private String DefectTypeName;
    private String DefectItem;
    private int RectificationDeadline;
    private String RectificationDeadlineName;
    private String Remark;
    private String Creator;
    private String CreatorName;
    private String SystemDate;
    private int IsSubmitted;
    private String RectificationTime;
    private String RectificationRemark;
    private int RectificationRecordID;
    private String DeadlineTime;
    private List<DefectAttachmentList> DefectAttachmentList;

    /**
     * HSE缺陷
     * @param HSECheckedRecordID
     * @param defectTypeID
     * @param defectItem
     * @param rectificationDeadline
     * @param creator
     * @param isSubmitted
     */
    public HseDefectListBean(int HSECheckedRecordID, int defectTypeID, String defectItem, int rectificationDeadline, String creator, int isSubmitted) {
        this.HSECheckedRecordID = HSECheckedRecordID;
        DefectTypeID = defectTypeID;
        DefectItem = defectItem;
        RectificationDeadline = rectificationDeadline;
        Creator = creator;
        IsSubmitted = isSubmitted;
    }

    public HseDefectListBean(int rownumber, int itemID, int HSECheckedRecordID, String checkedShipAccount, String checkedShipName, int defectTypeID, String defectTypeName, String defectItem, int rectificationDeadline, String rectificationDeadlineName, String remark, String creator, String creatorName, String systemDate, int isSubmitted, String rectificationTime, String rectificationRemark, int rectificationRecordID, String deadlineTime) {
        this.rownumber = rownumber;
        ItemID = itemID;
        this.HSECheckedRecordID = HSECheckedRecordID;
        CheckedShipAccount = checkedShipAccount;
        CheckedShipName = checkedShipName;
        DefectTypeID = defectTypeID;
        DefectTypeName = defectTypeName;
        DefectItem = defectItem;
        RectificationDeadline = rectificationDeadline;
        RectificationDeadlineName = rectificationDeadlineName;
        Remark = remark;
        Creator = creator;
        CreatorName = creatorName;
        SystemDate = systemDate;
        IsSubmitted = isSubmitted;
        RectificationTime = rectificationTime;
        RectificationRemark = rectificationRemark;
        RectificationRecordID = rectificationRecordID;
        DeadlineTime = deadlineTime;
    }

    public String getCheckedShipAccount() {
        return CheckedShipAccount;
    }

    public void setCheckedShipAccount(String checkedShipAccount) {
        CheckedShipAccount = checkedShipAccount;
    }

    public String getCheckedShipName() {
        return CheckedShipName;
    }

    public void setCheckedShipName(String checkedShipName) {
        CheckedShipName = checkedShipName;
    }

    public int getRownumber() {
        return rownumber;
    }

    public void setRownumber(int rownumber) {
        this.rownumber = rownumber;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
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

    public void setDefectTypeID(int DefectTypeID) {
        this.DefectTypeID = DefectTypeID;
    }

    public String getDefectTypeName() {
        return DefectTypeName;
    }

    public void setDefectTypeName(String DefectTypeName) {
        this.DefectTypeName = DefectTypeName;
    }

    public String getDefectItem() {
        return DefectItem;
    }

    public void setDefectItem(String DefectItem) {
        this.DefectItem = DefectItem;
    }

    public int getRectificationDeadline() {
        return RectificationDeadline;
    }

    public void setRectificationDeadline(int RectificationDeadline) {
        this.RectificationDeadline = RectificationDeadline;
    }

    public String getRectificationDeadlineName() {
        return RectificationDeadlineName;
    }

    public void setRectificationDeadlineName(String RectificationDeadlineName) {
        this.RectificationDeadlineName = RectificationDeadlineName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public void setCreatorName(String CreatorName) {
        this.CreatorName = CreatorName;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }

    public int getIsSubmitted() {
        return IsSubmitted;
    }

    public void setIsSubmitted(int IsSubmitted) {
        this.IsSubmitted = IsSubmitted;
    }

    public String getRectificationTime() {
        return RectificationTime;
    }

    public void setRectificationTime(String rectificationTime) {
        RectificationTime = rectificationTime;
    }

    public String getRectificationRemark() {
        return RectificationRemark;
    }

    public void setRectificationRemark(String rectificationRemark) {
        RectificationRemark = rectificationRemark;
    }

    public int getRectificationRecordID() {
        return RectificationRecordID;
    }

    public void setRectificationRecordID(int rectificationRecordID) {
        RectificationRecordID = rectificationRecordID;
    }

    public String getDeadlineTime() {
        return DeadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        DeadlineTime = deadlineTime;
    }

    public List<HseDefectListBean.DefectAttachmentList> getDefectAttachmentList() {
        return DefectAttachmentList;
    }

    public void setDefectAttachmentList(List<HseDefectListBean.DefectAttachmentList> defectAttachmentList) {
        DefectAttachmentList = defectAttachmentList;
    }

    public static class DefectAttachmentList {
        private int ItemID;
        private int DefectRecordID;
        private String FilePath;
        private String FileName;

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int itemID) {
            ItemID = itemID;
        }

        public int getDefectRecordID() {
            return DefectRecordID;
        }

        public void setDefectRecordID(int defectRecordID) {
            DefectRecordID = defectRecordID;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String filePath) {
            FilePath = filePath;
        }

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String fileName) {
            FileName = fileName;
        }
    }
}
