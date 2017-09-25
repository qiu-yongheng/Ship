package com.kc.shiptransport.data.bean.img;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 邱永恒
 * @time 2017/9/19  8:52
 * @desc 图片列表
 */

public class ImgList implements Parcelable {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected ImgList(Parcel in) {
        path = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(path);
    }

    public ImgList() {
    }

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
