package com.kc.shiptransport.db.analysis;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/7/27  17:04
 * @desc 供砂过程总表
 */

public class AnalysisDetail extends DataSupport{

    /**
     * ItemID : 4564
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试供应商
     * PlanDay : 2017-07-30
     * ShipAccount : tl8988
     * ShipName : 天力8988
     * ShipType : A类
     * DeadweightTon : 100
     * MaxTakeInWater :
     * MMSI :
     * SandSupplyCount : 0
     * PerfectBoatRecordList : {"LoadingDate":"2017-07-28 08:57:00","Captain":"法国红酒","CaptainPhone":"1254789","StoreName":"恒利石业","AIS_MMSI_Num":"","CompartmentQuantity":"3","GoodsName":"功夫","DeadweightTons":"100","WashStoreAddress":"测试地址1","LeaveStoreTime":"2017-07-28 08:58:00","ClearanceEndTime":"2017-07-28 08:58:00","ArrivaOfAnchorageTime":"2017-07-28 08:58:00","Receiver":"C3206 香港机场三跑主回填项目 振华-中交-疏浚联营","MaterialClassification":"细砂（0~5mm）","PerfectBoatTime":"2017-07-28 08:58:41"}
     * PerfectBoatScannerRecordList : [{"SubcontractorInterimApproachPlanID":"4564","SubcontractorPerfectBoatScannerAttachmentTypeID":"1","SubcontractorPerfectBoatScannerAttachmentTypeName":"碎石粉装船记录表","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"1039","FileName":"Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/a16ba35d-c41c-4da3-8458-a81371a1c906.png"},{"ItemID":"1040","FileName":"IMG_20170724164913.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170728/55f25039-6ae2-4054-a7ed-feb7a2ba2cf2.jpeg"}]},{"SubcontractorInterimApproachPlanID":"4564","SubcontractorPerfectBoatScannerAttachmentTypeID":"2","SubcontractorPerfectBoatScannerAttachmentTypeName":"预验收质量记录表","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"1041","FileName":"Screenshot_2017-07-06-09-42-46-001_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/e1244bed-f5cb-4c46-9730-9be9c964146d.png"},{"ItemID":"1042","FileName":"Screenshot_2017-07-05-16-07-02-683_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/9b107518-0d48-4e94-9283-5c7010f32ce7.png"}]},{"SubcontractorInterimApproachPlanID":"4564","SubcontractorPerfectBoatScannerAttachmentTypeID":"3","SubcontractorPerfectBoatScannerAttachmentTypeName":"装舱现场照片","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"1043","FileName":"IMG_20170420_085709.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/7b0a7e8c-d964-41d8-ad11-735ee7581cec.png"},{"ItemID":"1044","FileName":"Screenshot_2017-07-05-15-02-32-511_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/06904637-0af2-4779-9d3c-07bdbf7b74f2.png"}]},{"SubcontractorInterimApproachPlanID":"4564","SubcontractorPerfectBoatScannerAttachmentTypeID":"4","SubcontractorPerfectBoatScannerAttachmentTypeName":"选择计划航线图","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"1045","FileName":"Screenshot_2017-07-06-09-42-46-001_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/96e53c92-fe4f-48c8-8854-f4a01a1c7d1f.png"},{"ItemID":"1046","FileName":"Screenshot_2017-07-05-16-07-02-683_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/a3baa704-41ce-47f2-ad8a-2f2afd5e58eb.png"}]},{"SubcontractorInterimApproachPlanID":"4564","SubcontractorPerfectBoatScannerAttachmentTypeID":"5","SubcontractorPerfectBoatScannerAttachmentTypeName":"海关放行通知照片","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"1047","FileName":"1497861063923.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/ba70ebf8-18ad-4a7b-adbf-03f5861dcad2.png"},{"ItemID":"1048","FileName":"Screenshot_2017-07-05-15-02-32-511_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/7c87887f-b8bc-4aca-9c00-c6d59a5d4560.png"}]},{"SubcontractorInterimApproachPlanID":"4564","SubcontractorPerfectBoatScannerAttachmentTypeID":"6","SubcontractorPerfectBoatScannerAttachmentTypeName":"仓单","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"1049","FileName":"IMG_20170420_085709_1.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/af8f9586-975d-44de-b3fb-1dc586028ad2.png"},{"ItemID":"1050","FileName":"Screenshot_2017-07-05-16-07-02-683_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/4cf8d3b4-48e8-47ba-af15-ff00c731439b.png"}]},{"SubcontractorInterimApproachPlanID":"4564","SubcontractorPerfectBoatScannerAttachmentTypeID":"7","SubcontractorPerfectBoatScannerAttachmentTypeName":"计划航线图","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"1051","FileName":"1497861364341.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/3af72453-f48d-4df4-9b73-c5ef0474765e.png"},{"ItemID":"1052","FileName":"Screenshot_2017-06-05-14-55-57-935_com.kc.shiptransport.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/a7025c17-7451-4c60-96e3-5708b89354c0.png"}]},{"SubcontractorInterimApproachPlanID":"4564","SubcontractorPerfectBoatScannerAttachmentTypeID":"8","SubcontractorPerfectBoatScannerAttachmentTypeName":"航行对比图","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"1053","FileName":"1498011307944.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/06eb2b7b-529a-45ff-a88c-416eb5372467.png"},{"ItemID":"1054","FileName":"1497861364341.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/ebbe7eaa-bc04-4c33-9aed-4de9bf9a2e99.png"}]},{"SubcontractorInterimApproachPlanID":"4564","SubcontractorPerfectBoatScannerAttachmentTypeID":"9","SubcontractorPerfectBoatScannerAttachmentTypeName":"托运单","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"1055","FileName":"1498011307944.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/fe4f3cad-1423-4401-b80f-37a58e6a7d5d.png"},{"ItemID":"1056","FileName":"KCHITS012-2017062110251330.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/cdb03510-de69-4de2-9690-2fc19696bb4f.png"}]},{"SubcontractorInterimApproachPlanID":"4564","SubcontractorPerfectBoatScannerAttachmentTypeID":"10","SubcontractorPerfectBoatScannerAttachmentTypeName":"送货单","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"1057","FileName":"IMG_20170620155737_870.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170728/95873209-29d5-46fd-a4af-04ff26677f08.jpeg"},{"ItemID":"1058","FileName":"1497953453503.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/3df97f18-1292-4205-9432-c0ee9506e17c.png"}]},{"SubcontractorInterimApproachPlanID":"4564","SubcontractorPerfectBoatScannerAttachmentTypeID":"11","SubcontractorPerfectBoatScannerAttachmentTypeName":"海关单","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"1059","FileName":"1497861364341.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/d8f766bf-ae57-4ba8-a772-69aba74299c1.png"},{"ItemID":"1060","FileName":"Screenshot_2017-06-05-14-55-57-935_com.kc.shiptransport.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/aacbea85-4f40-4a9a-9528-1467d811a0de.png"}]}]
     * PreAcceptanceRecordList : {"PreAcceptanceTime":"2017-07-28 09:00:00","ShipItemNum":"2017072801","MaterialIntegrity":"5","MaterialTimeliness":"5"}
     * TheAmountOfTimeRecordList : {"Capacity":"1600","DeckGauge":"1400","Deduction":"250","TotalAmount":"2750","TheAmountOfTime":"2017-07-28 09:00:00","TheAmountOfSideAttachmentList":[{"ItemID":1029,"FileName":"Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/54df6581-8a27-437c-804e-b807ca2ad330.png"},{"ItemID":1030,"FileName":"IMG_20170724164913.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170728/4399f25f-1e5d-4876-affe-49517fb9c5c7.jpeg"}]}
     * ReceptionSandRecordList : {"ReceptionSandTime":"2017/7/28 9:01:00","ReceptionSandRecordAttachmentList":[{"ItemID":2019,"FileName":"Screenshot_2017-07-07-15-48-56-421_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/9fe6b80d-bdef-428a-8f77-9fc78cb2e833.png"},{"ItemID":2020,"FileName":"Screenshot_2017-07-06-09-42-46-001_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/9e89b26c-f3da-4571-9bec-d906b3497f68.png"}]}
     * SandSamplingRecordList : [{"ItemID":2068,"Creator":"csfbs","SandSamplingDate":"2017-07-28 09:02:00","SubcontractorInterimApproachPlanID":4564,"ConstructionBoatAccount":"","ShipName":"","Batch":"GG","IsExit":"0","SandSamplingNumRecordList":[{"ItemID":2050,"SandSamplingID":2068,"SamplingNum":"GGA","SandSamplingAttachmentRecordList":[{"ItemID":2080,"SandSamplingID":2068,"SandSamplingNumID":2050,"FileName":"IMG_20170724164913.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170728/c56eb1e1-d11f-4d33-a32b-93f0f4d3ec4d.jpeg"},{"ItemID":2081,"SandSamplingID":2068,"SandSamplingNumID":2050,"FileName":"Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/5039afc6-240f-45dc-80ed-03e71491946a.png"}],"ConstructionBoatAccount":"","ConstructionBoatAccountName":""},{"ItemID":2051,"SandSamplingID":2068,"SamplingNum":"GGB","SandSamplingAttachmentRecordList":[{"ItemID":2082,"SandSamplingID":2068,"SandSamplingNumID":2051,"FileName":"Screenshot_2017-07-07-15-48-56-421_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/f1b8bcf5-81e9-4e23-81ac-56ae1ef302f9.png"},{"ItemID":2083,"SandSamplingID":2068,"SandSamplingNumID":2051,"FileName":"IMG_20170420_085709.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/70d95403-ccd5-48f3-a37d-459ffbfeba66.png"}],"ConstructionBoatAccount":"","ConstructionBoatAccountName":""},{"ItemID":2052,"SandSamplingID":2068,"SamplingNum":"GGC","SandSamplingAttachmentRecordList":[{"ItemID":2084,"SandSamplingID":2068,"SandSamplingNumID":2052,"FileName":"1497861364341.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/2584b7de-a1d3-497b-a20f-ce5d7d600cd3.png"},{"ItemID":2085,"SandSamplingID":2068,"SandSamplingNumID":2052,"FileName":"1497861063923.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/e911711c-b87f-4c2b-9648-551726ff65b2.png"}],"ConstructionBoatAccount":"","ConstructionBoatAccountName":""}]}]
     * OverSandRecordList : [{"ItemID":"3007","SubcontractorInterimApproachPlanID":"3007","SandHandlingShipID":"tl8988","SandHandlingShipName":"天力8988","ConstructionShipID":"dcoc3","ConstructionShipName":"DCOC3","IsFinish":"0","Creator":"csfbs","StartTime":"2017-07-27 09:02:00","EndTime":"2017-07-28 09:03:00","BeforeOverSandDraft1":"0","BeforeOverSandDraft2":"0","BeforeOverSandDraft3":"0","BeforeOverSandDraft4":"0","AfterOverSandDraft1":"0","AfterOverSandDraft2":"0","AfterOverSandDraft3":"0","AfterOverSandDraft4":"0","ActualAmountOfSand":"0"},{"ItemID":"3008","SubcontractorInterimApproachPlanID":"3008","SandHandlingShipID":"tl8988","SandHandlingShipName":"天力8988","ConstructionShipID":"cssgcb","ConstructionShipName":"测试施工船舶","IsFinish":"1","Creator":"csfbs","StartTime":"2017-07-26 09:03:00","EndTime":"2017-07-28 09:03:00","BeforeOverSandDraft1":"0","BeforeOverSandDraft2":"0","BeforeOverSandDraft3":"0","BeforeOverSandDraft4":"0","AfterOverSandDraft1":"0","AfterOverSandDraft2":"0","AfterOverSandDraft3":"0","AfterOverSandDraft4":"0","ActualAmountOfSand":"0"}]
     * ExitApplicationRecordList : {"ExitTime":"2017/7/28 9:05:00","RemnantAmount":"55","Remark":"嘎嘎嘎","ExitApplicationRecordAttachmentList":[{"ItemID":2002,"FileName":"Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/5434bb9e-7c9e-46a0-94da-f735bc1c0e1c.png"},{"ItemID":2003,"FileName":"IMG_20170724164913.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170728/62eb35df-9f72-4ea1-b4bc-2876b57faced.jpeg"}]}
     */

    private int ItemID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String ShipType;
    private String DeadweightTon;
    private String MaxTakeInWater;
    private String MMSI;
    private String SandSupplyCount;
    private PerfectBoatRecordListBean PerfectBoatRecordList;
    private PreAcceptanceRecordListBean PreAcceptanceRecordList;
    private TheAmountOfTimeRecordListBean TheAmountOfTimeRecordList;
    private ReceptionSandRecordListBean ReceptionSandRecordList;
    private ExitApplicationRecordListBean ExitApplicationRecordList;
    private List<PerfectBoatScannerRecordListBean> PerfectBoatScannerRecordList;
    private List<SandSamplingRecordListBean> SandSamplingRecordList;
    private List<OverSandRecordListBean> OverSandRecordList;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
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

    public String getPlanDay() {
        return PlanDay;
    }

    public void setPlanDay(String PlanDay) {
        this.PlanDay = PlanDay;
    }

    public String getShipAccount() {
        return ShipAccount;
    }

    public void setShipAccount(String ShipAccount) {
        this.ShipAccount = ShipAccount;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String ShipName) {
        this.ShipName = ShipName;
    }

    public String getShipType() {
        return ShipType;
    }

    public void setShipType(String ShipType) {
        this.ShipType = ShipType;
    }

    public String getDeadweightTon() {
        return DeadweightTon;
    }

    public void setDeadweightTon(String DeadweightTon) {
        this.DeadweightTon = DeadweightTon;
    }

    public String getMaxTakeInWater() {
        return MaxTakeInWater;
    }

    public void setMaxTakeInWater(String MaxTakeInWater) {
        this.MaxTakeInWater = MaxTakeInWater;
    }

    public String getMMSI() {
        return MMSI;
    }

    public void setMMSI(String MMSI) {
        this.MMSI = MMSI;
    }

    public String getSandSupplyCount() {
        return SandSupplyCount;
    }

    public void setSandSupplyCount(String SandSupplyCount) {
        this.SandSupplyCount = SandSupplyCount;
    }

    public PerfectBoatRecordListBean getPerfectBoatRecordList() {
        return PerfectBoatRecordList;
    }

    public void setPerfectBoatRecordList(PerfectBoatRecordListBean PerfectBoatRecordList) {
        this.PerfectBoatRecordList = PerfectBoatRecordList;
    }

    public PreAcceptanceRecordListBean getPreAcceptanceRecordList() {
        return PreAcceptanceRecordList;
    }

    public void setPreAcceptanceRecordList(PreAcceptanceRecordListBean PreAcceptanceRecordList) {
        this.PreAcceptanceRecordList = PreAcceptanceRecordList;
    }

    public TheAmountOfTimeRecordListBean getTheAmountOfTimeRecordList() {
        return TheAmountOfTimeRecordList;
    }

    public void setTheAmountOfTimeRecordList(TheAmountOfTimeRecordListBean TheAmountOfTimeRecordList) {
        this.TheAmountOfTimeRecordList = TheAmountOfTimeRecordList;
    }

    public ReceptionSandRecordListBean getReceptionSandRecordList() {
        return ReceptionSandRecordList;
    }

    public void setReceptionSandRecordList(ReceptionSandRecordListBean ReceptionSandRecordList) {
        this.ReceptionSandRecordList = ReceptionSandRecordList;
    }

    public ExitApplicationRecordListBean getExitApplicationRecordList() {
        return ExitApplicationRecordList;
    }

    public void setExitApplicationRecordList(ExitApplicationRecordListBean ExitApplicationRecordList) {
        this.ExitApplicationRecordList = ExitApplicationRecordList;
    }

    public List<PerfectBoatScannerRecordListBean> getPerfectBoatScannerRecordList() {
        return PerfectBoatScannerRecordList;
    }

    public void setPerfectBoatScannerRecordList(List<PerfectBoatScannerRecordListBean> PerfectBoatScannerRecordList) {
        this.PerfectBoatScannerRecordList = PerfectBoatScannerRecordList;
    }

    public List<SandSamplingRecordListBean> getSandSamplingRecordList() {
        return SandSamplingRecordList;
    }

    public void setSandSamplingRecordList(List<SandSamplingRecordListBean> SandSamplingRecordList) {
        this.SandSamplingRecordList = SandSamplingRecordList;
    }

    public List<OverSandRecordListBean> getOverSandRecordList() {
        return OverSandRecordList;
    }

    public void setOverSandRecordList(List<OverSandRecordListBean> OverSandRecordList) {
        this.OverSandRecordList = OverSandRecordList;
    }

    public static class PerfectBoatRecordListBean {
        /**
         * LoadingDate : 2017-07-28 08:57:00
         * Captain : 法国红酒
         * CaptainPhone : 1254789
         * StoreName : 恒利石业
         * AIS_MMSI_Num :
         * CompartmentQuantity : 3
         * GoodsName : 功夫
         * DeadweightTons : 100
         * WashStoreAddress : 测试地址1
         * LeaveStoreTime : 2017-07-28 08:58:00
         * ClearanceEndTime : 2017-07-28 08:58:00
         * ArrivaOfAnchorageTime : 2017-07-28 08:58:00
         * Receiver : C3206 香港机场三跑主回填项目 振华-中交-疏浚联营
         * MaterialClassification : 细砂（0~5mm）
         * PerfectBoatTime : 2017-07-28 08:58:41
         */

        private String LoadingDate;
        private String Captain;
        private String CaptainPhone;
        private String StoreName;
        private String AIS_MMSI_Num;
        private String CompartmentQuantity;
        private String GoodsName;
        private String DeadweightTons;
        private String WashStoreAddress;
        private String LeaveStoreTime;
        private String ClearanceEndTime;
        private String ArrivaOfAnchorageTime;
        private String Receiver;
        private String MaterialClassification;
        private String PerfectBoatTime;

        public String getLoadingDate() {
            return LoadingDate;
        }

        public void setLoadingDate(String LoadingDate) {
            this.LoadingDate = LoadingDate;
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

        public String getWashStoreAddress() {
            return WashStoreAddress;
        }

        public void setWashStoreAddress(String WashStoreAddress) {
            this.WashStoreAddress = WashStoreAddress;
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

        public String getPerfectBoatTime() {
            return PerfectBoatTime;
        }

        public void setPerfectBoatTime(String PerfectBoatTime) {
            this.PerfectBoatTime = PerfectBoatTime;
        }
    }

    public static class PreAcceptanceRecordListBean {
        /**
         * PreAcceptanceTime : 2017-07-28 09:00:00
         * ShipItemNum : 2017072801
         * MaterialIntegrity : 5
         * MaterialTimeliness : 5
         */

        private String PreAcceptanceTime;
        private String ShipItemNum;
        private String MaterialIntegrity;
        private String MaterialTimeliness;

        public String getPreAcceptanceTime() {
            return PreAcceptanceTime;
        }

        public void setPreAcceptanceTime(String PreAcceptanceTime) {
            this.PreAcceptanceTime = PreAcceptanceTime;
        }

        public String getShipItemNum() {
            return ShipItemNum;
        }

        public void setShipItemNum(String ShipItemNum) {
            this.ShipItemNum = ShipItemNum;
        }

        public String getMaterialIntegrity() {
            return MaterialIntegrity;
        }

        public void setMaterialIntegrity(String MaterialIntegrity) {
            this.MaterialIntegrity = MaterialIntegrity;
        }

        public String getMaterialTimeliness() {
            return MaterialTimeliness;
        }

        public void setMaterialTimeliness(String MaterialTimeliness) {
            this.MaterialTimeliness = MaterialTimeliness;
        }
    }

    public static class TheAmountOfTimeRecordListBean {
        /**
         * Capacity : 1600
         * DeckGauge : 1400
         * Deduction : 250
         * TotalAmount : 2750
         * TheAmountOfTime : 2017-07-28 09:00:00
         * TheAmountOfSideAttachmentList : [{"ItemID":1029,"FileName":"Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/54df6581-8a27-437c-804e-b807ca2ad330.png"},{"ItemID":1030,"FileName":"IMG_20170724164913.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170728/4399f25f-1e5d-4876-affe-49517fb9c5c7.jpeg"}]
         */

        private String Capacity;
        private String DeckGauge;
        private String Deduction;
        private String TotalAmount;
        private String TheAmountOfTime;
        private List<TheAmountOfSideAttachmentListBean> TheAmountOfSideAttachmentList;

        public String getCapacity() {
            return Capacity;
        }

        public void setCapacity(String Capacity) {
            this.Capacity = Capacity;
        }

        public String getDeckGauge() {
            return DeckGauge;
        }

        public void setDeckGauge(String DeckGauge) {
            this.DeckGauge = DeckGauge;
        }

        public String getDeduction() {
            return Deduction;
        }

        public void setDeduction(String Deduction) {
            this.Deduction = Deduction;
        }

        public String getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(String TotalAmount) {
            this.TotalAmount = TotalAmount;
        }

        public String getTheAmountOfTime() {
            return TheAmountOfTime;
        }

        public void setTheAmountOfTime(String TheAmountOfTime) {
            this.TheAmountOfTime = TheAmountOfTime;
        }

        public List<TheAmountOfSideAttachmentListBean> getTheAmountOfSideAttachmentList() {
            return TheAmountOfSideAttachmentList;
        }

        public void setTheAmountOfSideAttachmentList(List<TheAmountOfSideAttachmentListBean> TheAmountOfSideAttachmentList) {
            this.TheAmountOfSideAttachmentList = TheAmountOfSideAttachmentList;
        }

        public static class TheAmountOfSideAttachmentListBean {
            /**
             * ItemID : 1029
             * FileName : Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png
             * FilePath : https://cchk3.kingwi.org/Files/20170728/54df6581-8a27-437c-804e-b807ca2ad330.png
             */

            private int ItemID;
            private String FileName;
            private String FilePath;

            public int getItemID() {
                return ItemID;
            }

            public void setItemID(int ItemID) {
                this.ItemID = ItemID;
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
        }
    }

    public static class ReceptionSandRecordListBean {
        /**
         * ReceptionSandTime : 2017/7/28 9:01:00
         * ReceptionSandRecordAttachmentList : [{"ItemID":2019,"FileName":"Screenshot_2017-07-07-15-48-56-421_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/9fe6b80d-bdef-428a-8f77-9fc78cb2e833.png"},{"ItemID":2020,"FileName":"Screenshot_2017-07-06-09-42-46-001_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/9e89b26c-f3da-4571-9bec-d906b3497f68.png"}]
         */

        private String ReceptionSandTime;
        private List<ReceptionSandRecordAttachmentListBean> ReceptionSandRecordAttachmentList;

        public String getReceptionSandTime() {
            return ReceptionSandTime;
        }

        public void setReceptionSandTime(String ReceptionSandTime) {
            this.ReceptionSandTime = ReceptionSandTime;
        }

        public List<ReceptionSandRecordAttachmentListBean> getReceptionSandRecordAttachmentList() {
            return ReceptionSandRecordAttachmentList;
        }

        public void setReceptionSandRecordAttachmentList(List<ReceptionSandRecordAttachmentListBean> ReceptionSandRecordAttachmentList) {
            this.ReceptionSandRecordAttachmentList = ReceptionSandRecordAttachmentList;
        }

        public static class ReceptionSandRecordAttachmentListBean {
            /**
             * ItemID : 2019
             * FileName : Screenshot_2017-07-07-15-48-56-421_com.kc.shiptransport.clothes.png
             * FilePath : https://cchk3.kingwi.org/Files/20170728/9fe6b80d-bdef-428a-8f77-9fc78cb2e833.png
             */

            private int ItemID;
            private String FileName;
            private String FilePath;

            public int getItemID() {
                return ItemID;
            }

            public void setItemID(int ItemID) {
                this.ItemID = ItemID;
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
        }
    }

    public static class ExitApplicationRecordListBean {
        /**
         * ExitTime : 2017/7/28 9:05:00
         * RemnantAmount : 55
         * Remark : 嘎嘎嘎
         * ExitApplicationRecordAttachmentList : [{"ItemID":2002,"FileName":"Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/5434bb9e-7c9e-46a0-94da-f735bc1c0e1c.png"},{"ItemID":2003,"FileName":"IMG_20170724164913.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170728/62eb35df-9f72-4ea1-b4bc-2876b57faced.jpeg"}]
         */

        private String ExitTime;
        private String RemnantAmount;
        private String Remark;
        private List<ExitApplicationRecordAttachmentListBean> ExitApplicationRecordAttachmentList;

        public String getExitTime() {
            return ExitTime;
        }

        public void setExitTime(String ExitTime) {
            this.ExitTime = ExitTime;
        }

        public String getRemnantAmount() {
            return RemnantAmount;
        }

        public void setRemnantAmount(String RemnantAmount) {
            this.RemnantAmount = RemnantAmount;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public List<ExitApplicationRecordAttachmentListBean> getExitApplicationRecordAttachmentList() {
            return ExitApplicationRecordAttachmentList;
        }

        public void setExitApplicationRecordAttachmentList(List<ExitApplicationRecordAttachmentListBean> ExitApplicationRecordAttachmentList) {
            this.ExitApplicationRecordAttachmentList = ExitApplicationRecordAttachmentList;
        }

        public static class ExitApplicationRecordAttachmentListBean {
            /**
             * ItemID : 2002
             * FileName : Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png
             * FilePath : https://cchk3.kingwi.org/Files/20170728/5434bb9e-7c9e-46a0-94da-f735bc1c0e1c.png
             */

            private int ItemID;
            private String FileName;
            private String FilePath;

            public int getItemID() {
                return ItemID;
            }

            public void setItemID(int ItemID) {
                this.ItemID = ItemID;
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
        }
    }

    public static class PerfectBoatScannerRecordListBean {
        /**
         * SubcontractorInterimApproachPlanID : 4564
         * SubcontractorPerfectBoatScannerAttachmentTypeID : 1
         * SubcontractorPerfectBoatScannerAttachmentTypeName : 碎石粉装船记录表
         * PerfectBoatScannerRecordAttachmentList : [{"ItemID":"1039","FileName":"Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/a16ba35d-c41c-4da3-8458-a81371a1c906.png"},{"ItemID":"1040","FileName":"IMG_20170724164913.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170728/55f25039-6ae2-4054-a7ed-feb7a2ba2cf2.jpeg"}]
         */

        private String SubcontractorInterimApproachPlanID;
        private String SubcontractorPerfectBoatScannerAttachmentTypeID;
        private String SubcontractorPerfectBoatScannerAttachmentTypeName;
        private List<PerfectBoatScannerRecordAttachmentListBean> PerfectBoatScannerRecordAttachmentList;

        public String getSubcontractorInterimApproachPlanID() {
            return SubcontractorInterimApproachPlanID;
        }

        public void setSubcontractorInterimApproachPlanID(String SubcontractorInterimApproachPlanID) {
            this.SubcontractorInterimApproachPlanID = SubcontractorInterimApproachPlanID;
        }

        public String getSubcontractorPerfectBoatScannerAttachmentTypeID() {
            return SubcontractorPerfectBoatScannerAttachmentTypeID;
        }

        public void setSubcontractorPerfectBoatScannerAttachmentTypeID(String SubcontractorPerfectBoatScannerAttachmentTypeID) {
            this.SubcontractorPerfectBoatScannerAttachmentTypeID = SubcontractorPerfectBoatScannerAttachmentTypeID;
        }

        public String getSubcontractorPerfectBoatScannerAttachmentTypeName() {
            return SubcontractorPerfectBoatScannerAttachmentTypeName;
        }

        public void setSubcontractorPerfectBoatScannerAttachmentTypeName(String SubcontractorPerfectBoatScannerAttachmentTypeName) {
            this.SubcontractorPerfectBoatScannerAttachmentTypeName = SubcontractorPerfectBoatScannerAttachmentTypeName;
        }

        public List<PerfectBoatScannerRecordAttachmentListBean> getPerfectBoatScannerRecordAttachmentList() {
            return PerfectBoatScannerRecordAttachmentList;
        }

        public void setPerfectBoatScannerRecordAttachmentList(List<PerfectBoatScannerRecordAttachmentListBean> PerfectBoatScannerRecordAttachmentList) {
            this.PerfectBoatScannerRecordAttachmentList = PerfectBoatScannerRecordAttachmentList;
        }

        public static class PerfectBoatScannerRecordAttachmentListBean {
            /**
             * ItemID : 1039
             * FileName : Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png
             * FilePath : https://cchk3.kingwi.org/Files/20170728/a16ba35d-c41c-4da3-8458-a81371a1c906.png
             */

            private String ItemID;
            private String FileName;
            private String FilePath;

            public String getItemID() {
                return ItemID;
            }

            public void setItemID(String ItemID) {
                this.ItemID = ItemID;
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
        }
    }

    public static class SandSamplingRecordListBean {
        /**
         * ItemID : 2068
         * Creator : csfbs
         * SandSamplingDate : 2017-07-28 09:02:00
         * SubcontractorInterimApproachPlanID : 4564
         * ConstructionBoatAccount :
         * ShipName :
         * Batch : GG
         * IsExit : 0
         * SandSamplingNumRecordList : [{"ItemID":2050,"SandSamplingID":2068,"SamplingNum":"GGA","SandSamplingAttachmentRecordList":[{"ItemID":2080,"SandSamplingID":2068,"SandSamplingNumID":2050,"FileName":"IMG_20170724164913.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170728/c56eb1e1-d11f-4d33-a32b-93f0f4d3ec4d.jpeg"},{"ItemID":2081,"SandSamplingID":2068,"SandSamplingNumID":2050,"FileName":"Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/5039afc6-240f-45dc-80ed-03e71491946a.png"}],"ConstructionBoatAccount":"","ConstructionBoatAccountName":""},{"ItemID":2051,"SandSamplingID":2068,"SamplingNum":"GGB","SandSamplingAttachmentRecordList":[{"ItemID":2082,"SandSamplingID":2068,"SandSamplingNumID":2051,"FileName":"Screenshot_2017-07-07-15-48-56-421_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/f1b8bcf5-81e9-4e23-81ac-56ae1ef302f9.png"},{"ItemID":2083,"SandSamplingID":2068,"SandSamplingNumID":2051,"FileName":"IMG_20170420_085709.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/70d95403-ccd5-48f3-a37d-459ffbfeba66.png"}],"ConstructionBoatAccount":"","ConstructionBoatAccountName":""},{"ItemID":2052,"SandSamplingID":2068,"SamplingNum":"GGC","SandSamplingAttachmentRecordList":[{"ItemID":2084,"SandSamplingID":2068,"SandSamplingNumID":2052,"FileName":"1497861364341.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/2584b7de-a1d3-497b-a20f-ce5d7d600cd3.png"},{"ItemID":2085,"SandSamplingID":2068,"SandSamplingNumID":2052,"FileName":"1497861063923.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/e911711c-b87f-4c2b-9648-551726ff65b2.png"}],"ConstructionBoatAccount":"","ConstructionBoatAccountName":""}]
         */

        private int ItemID;
        private String Creator;
        private String SandSamplingDate;
        private int SubcontractorInterimApproachPlanID;
        private String ConstructionBoatAccount;
        private String ShipName;
        private String Batch;
        private String IsExit;
        private List<SandSamplingNumRecordListBean> SandSamplingNumRecordList;

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

        public String getCreator() {
            return Creator;
        }

        public void setCreator(String Creator) {
            this.Creator = Creator;
        }

        public String getSandSamplingDate() {
            return SandSamplingDate;
        }

        public void setSandSamplingDate(String SandSamplingDate) {
            this.SandSamplingDate = SandSamplingDate;
        }

        public int getSubcontractorInterimApproachPlanID() {
            return SubcontractorInterimApproachPlanID;
        }

        public void setSubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) {
            this.SubcontractorInterimApproachPlanID = SubcontractorInterimApproachPlanID;
        }

        public String getConstructionBoatAccount() {
            return ConstructionBoatAccount;
        }

        public void setConstructionBoatAccount(String ConstructionBoatAccount) {
            this.ConstructionBoatAccount = ConstructionBoatAccount;
        }

        public String getShipName() {
            return ShipName;
        }

        public void setShipName(String ShipName) {
            this.ShipName = ShipName;
        }

        public String getBatch() {
            return Batch;
        }

        public void setBatch(String Batch) {
            this.Batch = Batch;
        }

        public String getIsExit() {
            return IsExit;
        }

        public void setIsExit(String IsExit) {
            this.IsExit = IsExit;
        }

        public List<SandSamplingNumRecordListBean> getSandSamplingNumRecordList() {
            return SandSamplingNumRecordList;
        }

        public void setSandSamplingNumRecordList(List<SandSamplingNumRecordListBean> SandSamplingNumRecordList) {
            this.SandSamplingNumRecordList = SandSamplingNumRecordList;
        }

        public static class SandSamplingNumRecordListBean {
            /**
             * ItemID : 2050
             * SandSamplingID : 2068
             * SamplingNum : GGA
             * SandSamplingAttachmentRecordList : [{"ItemID":2080,"SandSamplingID":2068,"SandSamplingNumID":2050,"FileName":"IMG_20170724164913.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170728/c56eb1e1-d11f-4d33-a32b-93f0f4d3ec4d.jpeg"},{"ItemID":2081,"SandSamplingID":2068,"SandSamplingNumID":2050,"FileName":"Screenshot_2017-07-13-15-15-36-374_com.kc.shiptransport.clothes.png","FilePath":"https://cchk3.kingwi.org/Files/20170728/5039afc6-240f-45dc-80ed-03e71491946a.png"}]
             * ConstructionBoatAccount :
             * ConstructionBoatAccountName :
             */

            private int ItemID;
            private int SandSamplingID;
            private String SamplingNum;
            private String ConstructionBoatAccount;
            private String ConstructionBoatAccountName;
            private List<SandSamplingAttachmentRecordListBean> SandSamplingAttachmentRecordList;

            public int getItemID() {
                return ItemID;
            }

            public void setItemID(int ItemID) {
                this.ItemID = ItemID;
            }

            public int getSandSamplingID() {
                return SandSamplingID;
            }

            public void setSandSamplingID(int SandSamplingID) {
                this.SandSamplingID = SandSamplingID;
            }

            public String getSamplingNum() {
                return SamplingNum;
            }

            public void setSamplingNum(String SamplingNum) {
                this.SamplingNum = SamplingNum;
            }

            public String getConstructionBoatAccount() {
                return ConstructionBoatAccount;
            }

            public void setConstructionBoatAccount(String ConstructionBoatAccount) {
                this.ConstructionBoatAccount = ConstructionBoatAccount;
            }

            public String getConstructionBoatAccountName() {
                return ConstructionBoatAccountName;
            }

            public void setConstructionBoatAccountName(String ConstructionBoatAccountName) {
                this.ConstructionBoatAccountName = ConstructionBoatAccountName;
            }

            public List<SandSamplingAttachmentRecordListBean> getSandSamplingAttachmentRecordList() {
                return SandSamplingAttachmentRecordList;
            }

            public void setSandSamplingAttachmentRecordList(List<SandSamplingAttachmentRecordListBean> SandSamplingAttachmentRecordList) {
                this.SandSamplingAttachmentRecordList = SandSamplingAttachmentRecordList;
            }

            public static class SandSamplingAttachmentRecordListBean {
                /**
                 * ItemID : 2080
                 * SandSamplingID : 2068
                 * SandSamplingNumID : 2050
                 * FileName : IMG_20170724164913.jpeg
                 * FilePath : https://cchk3.kingwi.org/Files/20170728/c56eb1e1-d11f-4d33-a32b-93f0f4d3ec4d.jpeg
                 */

                private int ItemID;
                private int SandSamplingID;
                private int SandSamplingNumID;
                private String FileName;
                private String FilePath;

                public int getItemID() {
                    return ItemID;
                }

                public void setItemID(int ItemID) {
                    this.ItemID = ItemID;
                }

                public int getSandSamplingID() {
                    return SandSamplingID;
                }

                public void setSandSamplingID(int SandSamplingID) {
                    this.SandSamplingID = SandSamplingID;
                }

                public int getSandSamplingNumID() {
                    return SandSamplingNumID;
                }

                public void setSandSamplingNumID(int SandSamplingNumID) {
                    this.SandSamplingNumID = SandSamplingNumID;
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
            }
        }
    }

    public static class OverSandRecordListBean {
        /**
         * ItemID : 3007
         * SubcontractorInterimApproachPlanID : 3007
         * SandHandlingShipID : tl8988
         * SandHandlingShipName : 天力8988
         * ConstructionShipID : dcoc3
         * ConstructionShipName : DCOC3
         * IsFinish : 0
         * Creator : csfbs
         * StartTime : 2017-07-27 09:02:00
         * EndTime : 2017-07-28 09:03:00
         * BeforeOverSandDraft1 : 0
         * BeforeOverSandDraft2 : 0
         * BeforeOverSandDraft3 : 0
         * BeforeOverSandDraft4 : 0
         * AfterOverSandDraft1 : 0
         * AfterOverSandDraft2 : 0
         * AfterOverSandDraft3 : 0
         * AfterOverSandDraft4 : 0
         * ActualAmountOfSand : 0
         */

        private String ItemID;
        private String SubcontractorInterimApproachPlanID;
        private String SandHandlingShipID;
        private String SandHandlingShipName;
        private String ConstructionShipID;
        private String ConstructionShipName;
        private String IsFinish;
        private String Creator;
        private String StartTime;
        private String EndTime;
        private String BeforeOverSandDraft1;
        private String BeforeOverSandDraft2;
        private String BeforeOverSandDraft3;
        private String BeforeOverSandDraft4;
        private String AfterOverSandDraft1;
        private String AfterOverSandDraft2;
        private String AfterOverSandDraft3;
        private String AfterOverSandDraft4;
        private String ActualAmountOfSand;

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

        public String getSandHandlingShipID() {
            return SandHandlingShipID;
        }

        public void setSandHandlingShipID(String SandHandlingShipID) {
            this.SandHandlingShipID = SandHandlingShipID;
        }

        public String getSandHandlingShipName() {
            return SandHandlingShipName;
        }

        public void setSandHandlingShipName(String SandHandlingShipName) {
            this.SandHandlingShipName = SandHandlingShipName;
        }

        public String getConstructionShipID() {
            return ConstructionShipID;
        }

        public void setConstructionShipID(String ConstructionShipID) {
            this.ConstructionShipID = ConstructionShipID;
        }

        public String getConstructionShipName() {
            return ConstructionShipName;
        }

        public void setConstructionShipName(String ConstructionShipName) {
            this.ConstructionShipName = ConstructionShipName;
        }

        public String getIsFinish() {
            return IsFinish;
        }

        public void setIsFinish(String IsFinish) {
            this.IsFinish = IsFinish;
        }

        public String getCreator() {
            return Creator;
        }

        public void setCreator(String Creator) {
            this.Creator = Creator;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public String getBeforeOverSandDraft1() {
            return BeforeOverSandDraft1;
        }

        public void setBeforeOverSandDraft1(String BeforeOverSandDraft1) {
            this.BeforeOverSandDraft1 = BeforeOverSandDraft1;
        }

        public String getBeforeOverSandDraft2() {
            return BeforeOverSandDraft2;
        }

        public void setBeforeOverSandDraft2(String BeforeOverSandDraft2) {
            this.BeforeOverSandDraft2 = BeforeOverSandDraft2;
        }

        public String getBeforeOverSandDraft3() {
            return BeforeOverSandDraft3;
        }

        public void setBeforeOverSandDraft3(String BeforeOverSandDraft3) {
            this.BeforeOverSandDraft3 = BeforeOverSandDraft3;
        }

        public String getBeforeOverSandDraft4() {
            return BeforeOverSandDraft4;
        }

        public void setBeforeOverSandDraft4(String BeforeOverSandDraft4) {
            this.BeforeOverSandDraft4 = BeforeOverSandDraft4;
        }

        public String getAfterOverSandDraft1() {
            return AfterOverSandDraft1;
        }

        public void setAfterOverSandDraft1(String AfterOverSandDraft1) {
            this.AfterOverSandDraft1 = AfterOverSandDraft1;
        }

        public String getAfterOverSandDraft2() {
            return AfterOverSandDraft2;
        }

        public void setAfterOverSandDraft2(String AfterOverSandDraft2) {
            this.AfterOverSandDraft2 = AfterOverSandDraft2;
        }

        public String getAfterOverSandDraft3() {
            return AfterOverSandDraft3;
        }

        public void setAfterOverSandDraft3(String AfterOverSandDraft3) {
            this.AfterOverSandDraft3 = AfterOverSandDraft3;
        }

        public String getAfterOverSandDraft4() {
            return AfterOverSandDraft4;
        }

        public void setAfterOverSandDraft4(String AfterOverSandDraft4) {
            this.AfterOverSandDraft4 = AfterOverSandDraft4;
        }

        public String getActualAmountOfSand() {
            return ActualAmountOfSand;
        }

        public void setActualAmountOfSand(String ActualAmountOfSand) {
            this.ActualAmountOfSand = ActualAmountOfSand;
        }
    }
}
