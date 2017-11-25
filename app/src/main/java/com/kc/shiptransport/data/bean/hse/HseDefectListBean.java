package com.kc.shiptransport.data.bean.hse;

/**
 * @author 邱永恒
 * @time 2017/11/25  15:24
 * @desc ${TODD}
 */

public class HseDefectListBean {

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

    public HseDefectListBean(int HSECheckedRecordID, int defectTypeID, String defectItem, int rectificationDeadline, String creator, int isSubmitted) {
        this.HSECheckedRecordID = HSECheckedRecordID;
        DefectTypeID = defectTypeID;
        DefectItem = defectItem;
        RectificationDeadline = rectificationDeadline;
        Creator = creator;
        IsSubmitted = isSubmitted;
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
}
