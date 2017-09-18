package com.kc.shiptransport.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 邱永恒
 * @time 2017/7/1 17:11
 * @desc 根据类型获取图片
 */

public class ScannerImgListByTypeBean implements Parcelable {

    /**
     * ItemID : 2
     * SubcontractorInterimApproachPlanID : 415
     * SubcontractorPerfectBoatScannerAttachmentTypeID : 1
     * SubcontractorPerfectBoatScannerAttachmentTypeName : 碎石粉装船记录表
     * SubcontractorAccount : yflf
     * SubcontractorName : 誉丰联发
     * ConstructionBoatAccount : jx6
     * ConstructionBoatName : 吉星6
     * FileName : 1498011307944.png
     * FilePath : https://cchk3.kingwi.org/Files/20170630/0a33774a-bd0e-4b15-9ee0-027ed2135e5e.png
     * SystemDate : 2017-06-30 18:06:23
     */

    private int ItemID;
    private int SubcontractorInterimApproachPlanID;
    private int SubcontractorPerfectBoatScannerAttachmentTypeID;
    private String SubcontractorPerfectBoatScannerAttachmentTypeName;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String ConstructionBoatAccount;
    private String ConstructionBoatName;
    private String FileName;
    private String FilePath;
    private String SystemDate;



    public static final Creator<ScannerImgListByTypeBean> CREATOR = new Creator<ScannerImgListByTypeBean>() {
        @Override
        public ScannerImgListByTypeBean createFromParcel(Parcel in) {
            return new ScannerImgListByTypeBean(in);
        }

        @Override
        public ScannerImgListByTypeBean[] newArray(int size) {
            return new ScannerImgListByTypeBean[size];
        }
    };

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
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

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.ItemID);
        parcel.writeInt(this.SubcontractorInterimApproachPlanID);
        parcel.writeInt(this.SubcontractorPerfectBoatScannerAttachmentTypeID);
        parcel.writeString(this.SubcontractorPerfectBoatScannerAttachmentTypeName);
        parcel.writeString(this.SubcontractorAccount);
        parcel.writeString(this.SubcontractorName);
        parcel.writeString(this.ConstructionBoatAccount);
        parcel.writeString(this.ConstructionBoatName);
        parcel.writeString(this.FileName);
        parcel.writeString(this.FilePath);
        parcel.writeString(this.SystemDate);
    }

    protected ScannerImgListByTypeBean(Parcel in) {
        ItemID = in.readInt();
        SubcontractorInterimApproachPlanID = in.readInt();
        SubcontractorPerfectBoatScannerAttachmentTypeID = in.readInt();
        SubcontractorPerfectBoatScannerAttachmentTypeName = in.readString();
        SubcontractorAccount = in.readString();
        SubcontractorName = in.readString();
        ConstructionBoatAccount = in.readString();
        ConstructionBoatName = in.readString();
        FileName = in.readString();
        FilePath = in.readString();
        SystemDate = in.readString();
    }
}
