package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/6/21  14:07
 * @desc 航次完善信息
 */

public class PerfectBoatRecordBean {

    /**
     * ItemID : 2
     * SubcontractorInterimApproachPlanID : 415
     * LoadingPlace : abc
     * LoadingDate : 2017-06-08 00:00:00
     * BaseNumber : xx
     * SourceOfSource : xxx
     * StartLoadingTime : 2017-06-08 00:00:00
     * EndLoadingTime : 2017-06-10 00:00:00
     * ArrivedAtTheDockTime : 2017-06-08 19:00:00
     * LeaveTheDockTime : 2017-06-08 19:30:00
     * ArrivaOfAnchorageTime : 2017-06-08 19:30:00
     * ClearanceTime : 2017-06-08 19:30:00
     * MaterialClassification : xxx
     * Creator : yflf
     * SystemDate : 2017-06-19T17:58:34.513
     * PerfectBoatItemCount : 11
     * IsPerfect : 1
     */

    private int ItemID;
    private int SubcontractorInterimApproachPlanID;
    private String LoadingPlace;
    private String LoadingDate;
    private String BaseNumber;
    private String SourceOfSource;
    private String StartLoadingTime;
    private String EndLoadingTime;
    private String ArrivedAtTheDockTime;
    private String LeaveTheDockTime;
    private String ArrivaOfAnchorageTime;
    private String ClearanceTime;
    private String MaterialClassification;
    private String Creator;
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

    public String getLoadingPlace() {
        return LoadingPlace;
    }

    public void setLoadingPlace(String LoadingPlace) {
        this.LoadingPlace = LoadingPlace;
    }

    public String getLoadingDate() {
        return LoadingDate;
    }

    public void setLoadingDate(String LoadingDate) {
        this.LoadingDate = LoadingDate;
    }

    public String getBaseNumber() {
        return BaseNumber;
    }

    public void setBaseNumber(String BaseNumber) {
        this.BaseNumber = BaseNumber;
    }

    public String getSourceOfSource() {
        return SourceOfSource;
    }

    public void setSourceOfSource(String SourceOfSource) {
        this.SourceOfSource = SourceOfSource;
    }

    public String getStartLoadingTime() {
        return StartLoadingTime;
    }

    public void setStartLoadingTime(String StartLoadingTime) {
        this.StartLoadingTime = StartLoadingTime;
    }

    public String getEndLoadingTime() {
        return EndLoadingTime;
    }

    public void setEndLoadingTime(String EndLoadingTime) {
        this.EndLoadingTime = EndLoadingTime;
    }

    public String getArrivedAtTheDockTime() {
        return ArrivedAtTheDockTime;
    }

    public void setArrivedAtTheDockTime(String ArrivedAtTheDockTime) {
        this.ArrivedAtTheDockTime = ArrivedAtTheDockTime;
    }

    public String getLeaveTheDockTime() {
        return LeaveTheDockTime;
    }

    public void setLeaveTheDockTime(String LeaveTheDockTime) {
        this.LeaveTheDockTime = LeaveTheDockTime;
    }

    public String getArrivaOfAnchorageTime() {
        return ArrivaOfAnchorageTime;
    }

    public void setArrivaOfAnchorageTime(String ArrivaOfAnchorageTime) {
        this.ArrivaOfAnchorageTime = ArrivaOfAnchorageTime;
    }

    public String getClearanceTime() {
        return ClearanceTime;
    }

    public void setClearanceTime(String ClearanceTime) {
        this.ClearanceTime = ClearanceTime;
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
