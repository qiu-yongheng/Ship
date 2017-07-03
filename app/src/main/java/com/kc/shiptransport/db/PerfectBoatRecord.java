package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/21  14:07
 * @desc 航次完善信息
 */

public class PerfectBoatRecord extends DataSupport{
    /**
     * ItemID :
     * SubcontractorInterimApproachPlanID : 415
     * Captain : 陈1
     * CaptainPhone : 15914331849
     * LoadingDate : 2017-06-08 19:30
     * AIS_MMSI_Num : 123
     * CompartmentQuantity : 3
     * GoodsName : abc
     * DeadweightTons : 100
     * StoreID : 1
     * WashStoreAddressID : 1
     * LeaveStoreTime : 2017-06-08 19:30
     * ClearanceEndTime : 2017-06-08 19:30
     * ArrivaOfAnchorageTime : 2017-06-08 19:30
     * MaterialClassification : 细砂（0~3mm）
     * Receiver : xxx
     * Creator : yflf
     */

    private String ItemID;
    private String SubcontractorInterimApproachPlanID;
    private String Captain;
    private String CaptainPhone;
    private String LoadingDate;
    private String AIS_MMSI_Num;
    private String CompartmentQuantity;
    private String GoodsName;
    private String DeadweightTons;
    private String StoreID;
    private String WashStoreAddressID;
    private String LeaveStoreTime;
    private String ClearanceEndTime;
    private String ArrivaOfAnchorageTime;
    private String MaterialClassification;
    private String Receiver;
    private String Creator;

    private int sp_stone_source_position; // 料源石场
    private int sp_material_position; // 材料分类

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
    }

    public String getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(String SubcontractorInterimApproachPlanID) {
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

    public String getLoadingDate() {
        return LoadingDate;
    }

    public void setLoadingDate(String LoadingDate) {
        this.LoadingDate = LoadingDate;
    }

    public String getAIS_MMSI_Num() {
        return AIS_MMSI_Num;
    }

    public void setAIS_MMSI_Num(String AIS_MMSI_Num) {
        this.AIS_MMSI_Num = AIS_MMSI_Num;
    }

    public String getCompartmentQuantity() {
        return CompartmentQuantity;
    }

    public void setCompartmentQuantity(String CompartmentQuantity) {
        this.CompartmentQuantity = CompartmentQuantity;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String GoodsName) {
        this.GoodsName = GoodsName;
    }

    public String getDeadweightTons() {
        return DeadweightTons;
    }

    public void setDeadweightTons(String DeadweightTons) {
        this.DeadweightTons = DeadweightTons;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String StoreID) {
        this.StoreID = StoreID;
    }

    public String getWashStoreAddressID() {
        return WashStoreAddressID;
    }

    public void setWashStoreAddressID(String WashStoreAddressID) {
        this.WashStoreAddressID = WashStoreAddressID;
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

    public String getMaterialClassification() {
        return MaterialClassification;
    }

    public void setMaterialClassification(String MaterialClassification) {
        this.MaterialClassification = MaterialClassification;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public int getSp_stone_source_position() {
        return sp_stone_source_position;
    }

    public void setSp_stone_source_position(int sp_stone_source_position) {
        this.sp_stone_source_position = sp_stone_source_position;
    }

    public int getSp_material_position() {
        return sp_material_position;
    }

    public void setSp_material_position(int sp_material_position) {
        this.sp_material_position = sp_material_position;
    }
}
