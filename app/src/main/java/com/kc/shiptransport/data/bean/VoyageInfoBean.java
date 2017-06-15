package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/6/15  11:00
 * @desc ${TODD}
 */

public class VoyageInfoBean {
    public final String key_ItemID = "ItemID";
    public final String key_SubcontractorInterimApproachPlanID = "SubcontractorInterimApproachPlanID";
    public final String key_LoadingPlace = "LoadingPlace";
    public final String key_LoadingDate = "LoadingDate";
    public final String key_BaseNumber = "BaseNumber";
    public final String key_SourceOfSource = "SourceOfSource";
    public final String key_StartLoadingTime = "StartLoadingTime";
    public final String key_EndLoadingTime = "EndLoadingTime";
    public final String key_ArrivedAtTheDockTime = "ArrivedAtTheDockTime";
    public final String key_LeaveTheDockTime = "LeaveTheDockTime";
    public final String key_ArrivaOfAnchorageTime = "ArrivaOfAnchorageTime";
    public final String key_ClearanceTime = "ClearanceTime";
    public final String key_MaterialClassification = "MaterialClassification";
    public final String key_Creator = "Creator";

    // 条目编号
    private String ItemID;
    // 进场计划ID
    private String SubcontractorInterimApproachPlanID;
    // 装船地点
    private String LoadingPlace;
    // 装船日期
    private String LoadingDate;
    // 基样编号
    private String BaseNumber;
    // 料源来源地
    private String SourceOfSource;
    // 开始装船时间
    private String StartLoadingTime;
    // 结束装船时间
    private String EndLoadingTime;
    // 抵达码头时间
    private String ArrivedAtTheDockTime;
    // 离开码头时间
    private String LeaveTheDockTime;
    // 叨叨锚地时间
    private String ArrivaOfAnchorageTime;
    // 清关时间
    private String ClearanceTime;
    // 材料分类
    private String MaterialClassification;
    // 当前登录账号
    private String Creator;

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(String subcontractorInterimApproachPlanID) {
        SubcontractorInterimApproachPlanID = subcontractorInterimApproachPlanID;
    }

    public String getLoadingPlace() {
        return LoadingPlace;
    }

    public void setLoadingPlace(String loadingPlace) {
        LoadingPlace = loadingPlace;
    }

    public String getLoadingDate() {
        return LoadingDate;
    }

    public void setLoadingDate(String loadingDate) {
        LoadingDate = loadingDate;
    }

    public String getBaseNumber() {
        return BaseNumber;
    }

    public void setBaseNumber(String baseNumber) {
        BaseNumber = baseNumber;
    }

    public String getSourceOfSource() {
        return SourceOfSource;
    }

    public void setSourceOfSource(String sourceOfSource) {
        SourceOfSource = sourceOfSource;
    }

    public String getStartLoadingTime() {
        return StartLoadingTime;
    }

    public void setStartLoadingTime(String startLoadingTime) {
        StartLoadingTime = startLoadingTime;
    }

    public String getEndLoadingTime() {
        return EndLoadingTime;
    }

    public void setEndLoadingTime(String endLoadingTime) {
        EndLoadingTime = endLoadingTime;
    }

    public String getArrivedAtTheDockTime() {
        return ArrivedAtTheDockTime;
    }

    public void setArrivedAtTheDockTime(String arrivedAtTheDockTime) {
        ArrivedAtTheDockTime = arrivedAtTheDockTime;
    }

    public String getLeaveTheDockTime() {
        return LeaveTheDockTime;
    }

    public void setLeaveTheDockTime(String leaveTheDockTime) {
        LeaveTheDockTime = leaveTheDockTime;
    }

    public String getArrivaOfAnchorageTime() {
        return ArrivaOfAnchorageTime;
    }

    public void setArrivaOfAnchorageTime(String arrivaOfAnchorageTime) {
        ArrivaOfAnchorageTime = arrivaOfAnchorageTime;
    }

    public String getClearanceTime() {
        return ClearanceTime;
    }

    public void setClearanceTime(String clearanceTime) {
        ClearanceTime = clearanceTime;
    }

    public String getMaterialClassification() {
        return MaterialClassification;
    }

    public void setMaterialClassification(String materialClassification) {
        MaterialClassification = materialClassification;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }
}
