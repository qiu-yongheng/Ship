package com.kc.shiptransport.data.bean.hse;

import com.kc.shiptransport.data.bean.hse.imglist.AttachmentListBean;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/27  16:26
 * @desc 根据itemID获取缺陷详情
 */

public class HseDefectDetailBean {

    /**
     * ItemID : 4
     * HSECheckedRecordID : 1
     * DefectTypeID : 4
     * DefectTypeName : 警报信号
     * DefectItem : xssx
     * RectificationDeadline : 7
     * RectificationDeadlineName : 7天已内
     * Remark : abc
     * CreatorName : 测试用户
     * Creator : csyh
     * IsSubmitted : 0
     * SystemDate : 2017-11-24 15:18:35
     * AttachmentList : [{"ItemID":"1","DefectRecordID":"4","FilePath":"https://cchk3.kingwi.org/Files/20171013/aca74d8c-33f4-499d-abd9-ab044cb8ce7a.jpeg","FileName":"xxx"},{"ItemID":"2","DefectRecordID":"4","FilePath":"https://cchk3.kingwi.org/Files/20171013/aca74d8c-33f4-499d-abd9-ab044cb8ce7a.jpeg","FileName":"xxx"}]
     */

    private int ItemID;
    private int HSECheckedRecordID;
    private int DefectTypeID;
    private String DefectTypeName;
    private String DefectItem;
    private int RectificationDeadline;
    private String RectificationDeadlineName;
    private String Remark;
    private String CreatorName;
    private String Creator;
    private String IsSubmitted;
    private String SystemDate;
    private List<AttachmentListBean> AttachmentList;

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

    public String getDefectTypeName() {
        return DefectTypeName;
    }

    public void setDefectTypeName(String defectTypeName) {
        DefectTypeName = defectTypeName;
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

    public String getRectificationDeadlineName() {
        return RectificationDeadlineName;
    }

    public void setRectificationDeadlineName(String rectificationDeadlineName) {
        RectificationDeadlineName = rectificationDeadlineName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public void setCreatorName(String creatorName) {
        CreatorName = creatorName;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getIsSubmitted() {
        return IsSubmitted;
    }

    public void setIsSubmitted(String isSubmitted) {
        IsSubmitted = isSubmitted;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String systemDate) {
        SystemDate = systemDate;
    }

    public List<AttachmentListBean> getAttachmentList() {
        return AttachmentList;
    }

    public void setAttachmentList(List<AttachmentListBean> AttachmentList) {
        this.AttachmentList = AttachmentList;
    }
}
