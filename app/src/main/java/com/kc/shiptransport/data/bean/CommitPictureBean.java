package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/6/30  16:57
 * @desc 保存提交图片信息的对象
 */

public class CommitPictureBean {

    /**
     * ItemID :
     * SubcontractorInterimApproachPlanID : 415
     * SubcontractorPerfectBoatScannerAttachmentTypeID : 3
     * SubcontractorAccount : yflf
     * ConstructionBoatAccount : jx6
     * FileName : abc.jpg
     * SuffixName : jpg
     * Creator : yflf
     */

    private int ItemID;
    private int SubcontractorInterimApproachPlanID;
    private int SubcontractorPerfectBoatScannerAttachmentTypeID;
    private String SubcontractorAccount;
    private String ConstructionBoatAccount;
    private String FileName;
    private String SuffixName;
    private String Creator;
    private String base64img;
    private int albumID;
    private String summary;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(int subcontractorInterimApproachPlanID) {
        SubcontractorInterimApproachPlanID = subcontractorInterimApproachPlanID;
    }

    public int getSubcontractorPerfectBoatScannerAttachmentTypeID() {
        return SubcontractorPerfectBoatScannerAttachmentTypeID;
    }

    public void setSubcontractorPerfectBoatScannerAttachmentTypeID(int subcontractorPerfectBoatScannerAttachmentTypeID) {
        SubcontractorPerfectBoatScannerAttachmentTypeID = subcontractorPerfectBoatScannerAttachmentTypeID;
    }

    public String getSubcontractorAccount() {
        return SubcontractorAccount;
    }

    public void setSubcontractorAccount(String subcontractorAccount) {
        SubcontractorAccount = subcontractorAccount;
    }

    public String getConstructionBoatAccount() {
        return ConstructionBoatAccount;
    }

    public void setConstructionBoatAccount(String constructionBoatAccount) {
        ConstructionBoatAccount = constructionBoatAccount;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getSuffixName() {
        return SuffixName;
    }

    public void setSuffixName(String suffixName) {
        SuffixName = suffixName;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getBase64img() {
        return base64img;
    }

    public void setBase64img(String base64img) {
        this.base64img = base64img;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
