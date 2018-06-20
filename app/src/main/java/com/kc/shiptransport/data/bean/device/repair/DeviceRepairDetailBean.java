package com.kc.shiptransport.data.bean.device.repair;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 邱永恒
 * @time 2018/6/17  12:45
 * @desc
 */

public class DeviceRepairDetailBean implements Parcelable {
    public static final Parcelable.Creator<DeviceRepairDetailBean> CREATOR = new Creator<DeviceRepairDetailBean>() {
        @Override
        public DeviceRepairDetailBean createFromParcel(Parcel in) {
            return new DeviceRepairDetailBean(in);
        }

        @Override
        public DeviceRepairDetailBean[] newArray(int size) {
            return new DeviceRepairDetailBean[size];
        }
    };
    /**
     * rownumber : 1
     * ItemID : 1
     * StartTime : 2018-06-11T15:36:28
     * EndTime : 2018-06-12T18:36:02
     * ShipAccount : jx1
     * ShipName : 吉星1
     * RepairProject : 修理项目
     * RepairProcess : 修理过程
     * Repairman : 修理人员
     * Attachment : https://cchk3.kingwi.org/Files/20180330/bfbdbc9b-4d73-4c39-8dcc-439d297e0ef2.jpeg,https://cchk3.kingwi.org/Files/20180423/961a60d4-794e-4f3a-acee-b68feeb80410.jpg,
     * Creator : wd
     * Remark : 备注
     */

    private int rownumber;
    private int ItemID;
    private String StartTime;
    private String EndTime;
    private String ShipAccount;
    private String ShipName;
    private String RepairProject;
    private String RepairProcess;
    private String Repairman;
    private String Attachment;
    private String Creator;
    private String Remark;

    protected DeviceRepairDetailBean(Parcel in) {
        rownumber = in.readInt();
        ItemID = in.readInt();
        StartTime = in.readString();
        EndTime = in.readString();
        ShipAccount = in.readString();
        ShipName = in.readString();
        RepairProject = in.readString();
        RepairProcess = in.readString();
        Repairman = in.readString();
        Attachment = in.readString();
        Creator = in.readString();
        Remark = in.readString();
    }

    public int getRownumber() { return rownumber;}

    public void setRownumber(int rownumber) { this.rownumber = rownumber;}

    public int getItemID() { return ItemID;}

    public void setItemID(int ItemID) { this.ItemID = ItemID;}

    public String getStartTime() { return StartTime;}

    public void setStartTime(String StartTime) { this.StartTime = StartTime;}

    public String getEndTime() { return EndTime;}

    public void setEndTime(String EndTime) { this.EndTime = EndTime;}

    public String getShipAccount() { return ShipAccount;}

    public void setShipAccount(String ShipAccount) { this.ShipAccount = ShipAccount;}

    public String getShipName() { return ShipName;}

    public void setShipName(String ShipName) { this.ShipName = ShipName;}

    public String getRepairProject() { return RepairProject;}

    public void setRepairProject(String RepairProject) { this.RepairProject = RepairProject;}

    public String getRepairProcess() { return RepairProcess;}

    public void setRepairProcess(String RepairProcess) { this.RepairProcess = RepairProcess;}

    public String getRepairman() { return Repairman;}

    public void setRepairman(String Repairman) { this.Repairman = Repairman;}

    public String getAttachment() { return Attachment;}

    public void setAttachment(String Attachment) { this.Attachment = Attachment;}

    public String getCreator() { return Creator;}

    public void setCreator(String Creator) { this.Creator = Creator;}

    public String getRemark() { return Remark;}

    public void setRemark(String Remark) { this.Remark = Remark;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rownumber);
        dest.writeInt(ItemID);
        dest.writeString(StartTime);
        dest.writeString(EndTime);
        dest.writeString(ShipAccount);
        dest.writeString(ShipName);
        dest.writeString(RepairProject);
        dest.writeString(RepairProcess);
        dest.writeString(Repairman);
        dest.writeString(Attachment);
        dest.writeString(Creator);
        dest.writeString(Remark);
    }
}
