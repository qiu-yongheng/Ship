package com.kc.shiptransport.db.voyage;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/21  14:07
 * @desc 航次完善信息
 */

public class PerfectBoatRecordInfo extends DataSupport{

    /**
     * ItemID : 1
     * SubcontractorInterimApproachPlanID : 415
     * Captain : 陈1
     * CaptainPhone : 15914331849
     * StoreID : 1
     * StoreName : 泰盛石场
     * AIS_MMSI_Num : 123
     * GoodsName : abc
     * WashStoreAddressID : 1
     * WashStoreAddress : 测试地址1
     * Receiver : xxx
     * MaterialClassification : 细砂（0~3mm）
     * Creator : yflf
     * LoadingDate : 2017-06-08 19:30:00
     * CompartmentQuantity : 3
     * DeadweightTons : 100
     * LeaveStoreTime : 2017-06-08 19:30:00
     * ClearanceEndTime : 2017-06-08 19:30:00
     * ArrivaOfAnchorageTime : 2017-06-08 19:30:00
     * SystemDate : 2017-07-02 17:09:54
     * PerfectBoatItemCount : 14
     * IsPerfect : 1
     */

    private int ItemID;
    private int SubcontractorInterimApproachPlanID;
    private String Captain;
    private String CaptainPhone;
    private int StoreID;
    private String StoreName;
    private String AIS_MMSI_Num;
    private String GoodsName;
    private int WashStoreAddressID;
    private String WashStoreAddress;
    private String Receiver;
    private String MaterialClassification;
    private String Creator;
    private String LoadingDate;
    private String CompartmentQuantity;
    private String DeadweightTons;
    private String LeaveStoreTime;
    private String ClearanceEndTime;
    private String ArrivaOfAnchorageTime;
    private String SystemDate;
    private int PerfectBoatItemCount;
    private int IsPerfect;

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

    public String getCaptain() {
        return Captain;
    }

    public void setCaptain(String Captain) {
        this.Captain = Captain;
    }

    public String getCaptainPhone() {
        return CaptainPhone;
    }

    public void setCaptainPhone(String CaptainPhone) {
        this.CaptainPhone = CaptainPhone;
    }

    public int getStoreID() {
        return StoreID;
    }

    public void setStoreID(int StoreID) {
        this.StoreID = StoreID;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    public String getAIS_MMSI_Num() {
        return AIS_MMSI_Num;
    }

    public void setAIS_MMSI_Num(String AIS_MMSI_Num) {
        this.AIS_MMSI_Num = AIS_MMSI_Num;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String GoodsName) {
        this.GoodsName = GoodsName;
    }

    public int getWashStoreAddressID() {
        return WashStoreAddressID;
    }

    public void setWashStoreAddressID(int WashStoreAddressID) {
        this.WashStoreAddressID = WashStoreAddressID;
    }

    public String getWashStoreAddress() {
        return WashStoreAddress;
    }

    public void setWashStoreAddress(String WashStoreAddress) {
        this.WashStoreAddress = WashStoreAddress;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getMaterialClassification() {
        return MaterialClassification;
    }

    public void setMaterialClassification(String MaterialClassification) {
        this.MaterialClassification = MaterialClassification;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getLoadingDate() {
        return LoadingDate;
    }

    public void setLoadingDate(String LoadingDate) {
        this.LoadingDate = LoadingDate;
    }

    public String getCompartmentQuantity() {
        return CompartmentQuantity;
    }

    public void setCompartmentQuantity(String CompartmentQuantity) {
        this.CompartmentQuantity = CompartmentQuantity;
    }

    public String getDeadweightTons() {
        return DeadweightTons;
    }

    public void setDeadweightTons(String DeadweightTons) {
        this.DeadweightTons = DeadweightTons;
    }

    public String getLeaveStoreTime() {
        return LeaveStoreTime;
    }

    public void setLeaveStoreTime(String LeaveStoreTime) {
        this.LeaveStoreTime = LeaveStoreTime;
    }

    public String getClearanceEndTime() {
        return ClearanceEndTime;
    }

    public void setClearanceEndTime(String ClearanceEndTime) {
        this.ClearanceEndTime = ClearanceEndTime;
    }

    public String getArrivaOfAnchorageTime() {
        return ArrivaOfAnchorageTime;
    }

    public void setArrivaOfAnchorageTime(String ArrivaOfAnchorageTime) {
        this.ArrivaOfAnchorageTime = ArrivaOfAnchorageTime;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }

    public int getPerfectBoatItemCount() {
        return PerfectBoatItemCount;
    }

    public void setPerfectBoatItemCount(int PerfectBoatItemCount) {
        this.PerfectBoatItemCount = PerfectBoatItemCount;
    }

    public int getIsPerfect() {
        return IsPerfect;
    }

    public void setIsPerfect(int IsPerfect) {
        this.IsPerfect = IsPerfect;
    }
}
