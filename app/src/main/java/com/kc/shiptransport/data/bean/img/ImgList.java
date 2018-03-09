package com.kc.shiptransport.data.bean.img;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 邱永恒
 * @time 2017/9/19  8:52
 * @desc 图片显示列表
 */

public class ImgList implements Parcelable {
    /**
     * 图片ID
     */
    private int itemID;
    /**
     * 图片路径
     */
    private String path;
    /**
     * 图片摘要(在相册使用)
     */
    private String remark;

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected ImgList(Parcel in) {
        itemID = in.readInt();
        path = in.readString();
        remark = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(itemID);
        parcel.writeString(path);
        parcel.writeString(remark);
    }

    public ImgList() {}

    public static final Parcelable.Creator<ImgList> CREATOR = new Creator<ImgList>() {
        @Override
        public ImgList createFromParcel(Parcel in) {
            return new ImgList(in);
        }

        @Override
        public ImgList[] newArray(int size) {
            return new ImgList[size];
        }
    };
}
