package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/6/27  16:17
 * @desc 扫描图片类型列表
 */

public class ScannerListBean {

    /**
     * SubcontractorInterimApproachPlanID : 415
     * SubcontractorPerfectBoatScannerAttachmentTypeID : 1
     * SubcontractorPerfectBoatScannerAttachmentTypeName : 碎石粉装船记录表
     * SubcontractorAccount : yflf
     * SubcontractorName : 誉丰联发
     * ConstructionBoatAccount : jx6
     * ConstructionBoatName : 吉星6
     * AttachmentCount : 2
     * DefalutAttachmentCount : 3
     * SystemDate : 2017-06-30 18:06:23
     */

    private int SubcontractorInterimApproachPlanID;
    private int SubcontractorPerfectBoatScannerAttachmentTypeID;
    private String SubcontractorPerfectBoatScannerAttachmentTypeName;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String ConstructionBoatAccount;
    private String ConstructionBoatName;
    private int AttachmentCount;
    private int DefalutAttachmentCount;
    private String SystemDate;
    private String PreAcceptanceEvaluationRemark;
    private int PreAcceptanceEvaluationStatus;

    public String getPreAcceptanceEvaluationRemark() {
        return PreAcceptanceEvaluationRemark;
    }

    public void setPreAcceptanceEvaluationRemark(String preAcceptanceEvaluationRemark) {
        PreAcceptanceEvaluationRemark = preAcceptanceEvaluationRemark;
    }

    public int getPreAcceptanceEvaluationStatus() {
        return PreAcceptanceEvaluationStatus;
    }

    public void setPreAcceptanceEvaluationStatus(int preAcceptanceEvaluationStatus) {
        PreAcceptanceEvaluationStatus = preAcceptanceEvaluationStatus;
    }

    public int getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) {
        this.SubcontractorInterimApproachPlanID = SubcontractorInterimApproachPlanID;
    }

    public int getSubcontractorPerfectBoatScannerAttachmentTypeID() {
        return SubcontractorPerfectBoatScannerAttachmentTypeID;
    }

    public void setSubcontractorPerfectBoatScannerAttachmentTypeID(int SubcontractorPerfectBoatScannerAttachmentTypeID) {
        this.SubcontractorPerfectBoatScannerAttachmentTypeID = SubcontractorPerfectBoatScannerAttachmentTypeID;
    }

    public String getSubcontractorPerfectBoatScannerAttachmentTypeName() {
        return SubcontractorPerfectBoatScannerAttachmentTypeName;
    }

    public void setSubcontractorPerfectBoatScannerAttachmentTypeName(String SubcontractorPerfectBoatScannerAttachmentTypeName) {
        this.SubcontractorPerfectBoatScannerAttachmentTypeName = SubcontractorPerfectBoatScannerAttachmentTypeName;
    }

    public String getSubcontractorAccount() {
        return SubcontractorAccount;
    }

    public void setSubcontractorAccount(String SubcontractorAccount) {
        this.SubcontractorAccount = SubcontractorAccount;
    }

    public String getSubcontractorName() {
        return SubcontractorName;
    }

    public void setSubcontractorName(String SubcontractorName) {
        this.SubcontractorName = SubcontractorName;
    }

    public String getConstructionBoatAccount() {
        return ConstructionBoatAccount;
    }

    public void setConstructionBoatAccount(String ConstructionBoatAccount) {
        this.ConstructionBoatAccount = ConstructionBoatAccount;
    }

    public String getConstructionBoatName() {
        return ConstructionBoatName;
    }

    public void setConstructionBoatName(String ConstructionBoatName) {
        this.ConstructionBoatName = ConstructionBoatName;
    }

    public int getAttachmentCount() {
        return AttachmentCount;
    }

    public void setAttachmentCount(int AttachmentCount) {
        this.AttachmentCount = AttachmentCount;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }

    public int getDefalutAttachmentCount() {
        return DefalutAttachmentCount;
    }

    public void setDefalutAttachmentCount(int defalutAttachmentCount) {
        DefalutAttachmentCount = defalutAttachmentCount;
    }
}
