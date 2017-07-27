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
     * ItemID : 415
     * SubcontractorAccount : xgyj
     * SubcontractorName : 香港怡景
     * PlanDay : 2017-05-26
     * ShipAccount : yhzh3393
     * ShipName : 粤惠州货3393
     * ShipType : B类
     * DeadweightTon : 0
     * MaxTakeInWater :
     * MMSI :
     * SandSupplyCount : 2500
     * PerfectBoatRecordList : {"LoadingDate":"2017-06-08 19:30:00","Captain":"陈1","CaptainPhone":"15914331849","StoreName":"泰盛石场","AIS_MMSI_Num":"123","CompartmentQuantity":"3","GoodsName":"abc","DeadweightTons":"100","WashStoreAddress":"测试地址1","LeaveStoreTime":"2017-06-08 19:30:00","ClearanceEndTime":"2017-06-08 19:30:00","ArrivaOfAnchorageTime":"2017-06-08 19:30:00","Receiver":"xxx","MaterialClassification":"细砂（0~3mm）","PerfectBoatTime":"2017-07-02 17:09:54"}
     * PerfectBoatScannerRecordList : [{"SubcontractorInterimApproachPlanID":"415","SubcontractorPerfectBoatScannerAttachmentTypeID":"1","SubcontractorPerfectBoatScannerAttachmentTypeName":"碎石粉装船记录表","PerfectBoatScannerRecordAttachmentList":[{"ItemID":"2","FileName":"1498011307944.png","FilePath":"https://cchk3.kingwi.org/Files/20170630/0a33774a-bd0e-4b15-9ee0-027ed2135e5e.png"},{"ItemID":"3","FileName":"1497953453503.png","FilePath":"https://cchk3.kingwi.org/Files/20170630/3f6eca7c-246c-40e6-9f4d-96cec255c818.png"}]},{"SubcontractorInterimApproachPlanID":"415","SubcontractorPerfectBoatScannerAttachmentTypeID":"2","SubcontractorPerfectBoatScannerAttachmentTypeName":"预验收质量记录表","PerfectBoatScannerRecordAttachmentList":[]},{"SubcontractorInterimApproachPlanID":"415","SubcontractorPerfectBoatScannerAttachmentTypeID":"3","SubcontractorPerfectBoatScannerAttachmentTypeName":"装舱现场照片","PerfectBoatScannerRecordAttachmentList":[]},{"SubcontractorInterimApproachPlanID":"415","SubcontractorPerfectBoatScannerAttachmentTypeID":"4","SubcontractorPerfectBoatScannerAttachmentTypeName":"选择计划航线图","PerfectBoatScannerRecordAttachmentList":[]},{"SubcontractorInterimApproachPlanID":"415","SubcontractorPerfectBoatScannerAttachmentTypeID":"5","SubcontractorPerfectBoatScannerAttachmentTypeName":"海关放行通知照片","PerfectBoatScannerRecordAttachmentList":[]},{"SubcontractorInterimApproachPlanID":"415","SubcontractorPerfectBoatScannerAttachmentTypeID":"6","SubcontractorPerfectBoatScannerAttachmentTypeName":"仓单","PerfectBoatScannerRecordAttachmentList":[]},{"SubcontractorInterimApproachPlanID":"415","SubcontractorPerfectBoatScannerAttachmentTypeID":"7","SubcontractorPerfectBoatScannerAttachmentTypeName":"计划航线图","PerfectBoatScannerRecordAttachmentList":[]},{"SubcontractorInterimApproachPlanID":"415","SubcontractorPerfectBoatScannerAttachmentTypeID":"8","SubcontractorPerfectBoatScannerAttachmentTypeName":"航行对比图","PerfectBoatScannerRecordAttachmentList":[]},{"SubcontractorInterimApproachPlanID":"415","SubcontractorPerfectBoatScannerAttachmentTypeID":"9","SubcontractorPerfectBoatScannerAttachmentTypeName":"托运单","PerfectBoatScannerRecordAttachmentList":[]},{"SubcontractorInterimApproachPlanID":"415","SubcontractorPerfectBoatScannerAttachmentTypeID":"10","SubcontractorPerfectBoatScannerAttachmentTypeName":"送货单","PerfectBoatScannerRecordAttachmentList":[]},{"SubcontractorInterimApproachPlanID":"415","SubcontractorPerfectBoatScannerAttachmentTypeID":"11","SubcontractorPerfectBoatScannerAttachmentTypeName":"海关单","PerfectBoatScannerRecordAttachmentList":[]}]
     * PreAcceptanceRecordList : {"PreAcceptanceTime":"","ShipItemNum":"","MaterialIntegrity":"","MaterialTimeliness":""}
     * TheAmountOfTimeRecordList : [{"Capacity":"","DeckGauge":"","Deduction":"","TotalAmount":"","TheAmountOfTime":"","TheAmountOfSideAttachmentList":[]}]
     * ReceptionSandRecordList : [{"ReceptionSandTime":"2017/6/2 0:00:00","ReceptionSandRecordAttachmentList":[{"ItemID":1,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/b0d42941-cee8-4a0c-afb9-0c1917f051d4.png"},{"ItemID":2,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/a309804a-1567-418b-a6ab-4322a161a84c.png"},{"ItemID":3,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/81f17384-e85e-495a-90e1-686fe2c841df.png"}]}]
     * SandSamplingRecordList : [{"ItemID":1052,"Creator":"yflf","SandSamplingDate":"2017-02-17 10:47:00","SubcontractorInterimApproachPlanID":415,"ConstructionBoatAccount":"jx6","ShipName":"吉星6","Batch":"","IsExit":"0","SandSamplingNumRecordList":[]}]
     * OverSandRecordList : [{"ItemID":"1002","SubcontractorInterimApproachPlanID":"1002","SandHandlingShipID":"jx1","SandHandlingShipName":"","ConstructionShipID":"jx1","ConstructionShipName":"吉星1","IsFinish":"1","Creator":"yflf","StartTime":"2017-06-08 00:00:00","EndTime":"2017-06-10 00:00:00","BeforeOverSandDraft1":"12.5","BeforeOverSandDraft2":"15.5","BeforeOverSandDraft3":"23","BeforeOverSandDraft4":"20","AfterOverSandDraft1":"11","AfterOverSandDraft2":"22","AfterOverSandDraft3":"33","AfterOverSandDraft4":"44","ActualAmountOfSand":"120"}]
     * ExitApplicationRecordList : [{"ExitTime":"2017-07-06 17:00:00","RemnantAmount":"120","ExitApplicationRecordAttachmentList":[{"ItemID":1,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170713/98202811-0e55-41d3-b4ab-82434a4e3608.png"},{"ItemID":2,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170713/eaf25704-d4c9-4f8f-a936-9d98f90cd05b.png"}]}]
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
    private List<PerfectBoatScannerRecordListBean> PerfectBoatScannerRecordList;
    private List<TheAmountOfTimeRecordListBean> TheAmountOfTimeRecordList;
    private List<ReceptionSandRecordListBean> ReceptionSandRecordList;
    private List<SandSamplingRecordListBean> SandSamplingRecordList;
    private List<OverSandRecordListBean> OverSandRecordList;
    private List<ExitApplicationRecordListBean> ExitApplicationRecordList;

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

    public List<PerfectBoatScannerRecordListBean> getPerfectBoatScannerRecordList() {
        return PerfectBoatScannerRecordList;
    }

    public void setPerfectBoatScannerRecordList(List<PerfectBoatScannerRecordListBean> PerfectBoatScannerRecordList) {
        this.PerfectBoatScannerRecordList = PerfectBoatScannerRecordList;
    }

    public List<TheAmountOfTimeRecordListBean> getTheAmountOfTimeRecordList() {
        return TheAmountOfTimeRecordList;
    }

    public void setTheAmountOfTimeRecordList(List<TheAmountOfTimeRecordListBean> TheAmountOfTimeRecordList) {
        this.TheAmountOfTimeRecordList = TheAmountOfTimeRecordList;
    }

    public List<ReceptionSandRecordListBean> getReceptionSandRecordList() {
        return ReceptionSandRecordList;
    }

    public void setReceptionSandRecordList(List<ReceptionSandRecordListBean> ReceptionSandRecordList) {
        this.ReceptionSandRecordList = ReceptionSandRecordList;
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

    public List<ExitApplicationRecordListBean> getExitApplicationRecordList() {
        return ExitApplicationRecordList;
    }

    public void setExitApplicationRecordList(List<ExitApplicationRecordListBean> ExitApplicationRecordList) {
        this.ExitApplicationRecordList = ExitApplicationRecordList;
    }

    public static class PerfectBoatRecordListBean {
        /**
         * LoadingDate : 2017-06-08 19:30:00
         * Captain : 陈1
         * CaptainPhone : 15914331849
         * StoreName : 泰盛石场
         * AIS_MMSI_Num : 123
         * CompartmentQuantity : 3
         * GoodsName : abc
         * DeadweightTons : 100
         * WashStoreAddress : 测试地址1
         * LeaveStoreTime : 2017-06-08 19:30:00
         * ClearanceEndTime : 2017-06-08 19:30:00
         * ArrivaOfAnchorageTime : 2017-06-08 19:30:00
         * Receiver : xxx
         * MaterialClassification : 细砂（0~3mm）
         * PerfectBoatTime : 2017-07-02 17:09:54
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
         * PreAcceptanceTime :
         * ShipItemNum :
         * MaterialIntegrity :
         * MaterialTimeliness :
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

    public static class PerfectBoatScannerRecordListBean {
        /**
         * SubcontractorInterimApproachPlanID : 415
         * SubcontractorPerfectBoatScannerAttachmentTypeID : 1
         * SubcontractorPerfectBoatScannerAttachmentTypeName : 碎石粉装船记录表
         * PerfectBoatScannerRecordAttachmentList : [{"ItemID":"2","FileName":"1498011307944.png","FilePath":"https://cchk3.kingwi.org/Files/20170630/0a33774a-bd0e-4b15-9ee0-027ed2135e5e.png"},{"ItemID":"3","FileName":"1497953453503.png","FilePath":"https://cchk3.kingwi.org/Files/20170630/3f6eca7c-246c-40e6-9f4d-96cec255c818.png"}]
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
             * ItemID : 2
             * FileName : 1498011307944.png
             * FilePath : https://cchk3.kingwi.org/Files/20170630/0a33774a-bd0e-4b15-9ee0-027ed2135e5e.png
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

    public static class TheAmountOfTimeRecordListBean {
        /**
         * Capacity :
         * DeckGauge :
         * Deduction :
         * TotalAmount :
         * TheAmountOfTime :
         * TheAmountOfSideAttachmentList : []
         */

        private String Capacity;
        private String DeckGauge;
        private String Deduction;
        private String TotalAmount;
        private String TheAmountOfTime;
        private List<?> TheAmountOfSideAttachmentList;

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

        public List<?> getTheAmountOfSideAttachmentList() {
            return TheAmountOfSideAttachmentList;
        }

        public void setTheAmountOfSideAttachmentList(List<?> TheAmountOfSideAttachmentList) {
            this.TheAmountOfSideAttachmentList = TheAmountOfSideAttachmentList;
        }
    }

    public static class ReceptionSandRecordListBean {
        /**
         * ReceptionSandTime : 2017/6/2 0:00:00
         * ReceptionSandRecordAttachmentList : [{"ItemID":1,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/b0d42941-cee8-4a0c-afb9-0c1917f051d4.png"},{"ItemID":2,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/a309804a-1567-418b-a6ab-4322a161a84c.png"},{"ItemID":3,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/81f17384-e85e-495a-90e1-686fe2c841df.png"}]
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
             * ItemID : 1
             * FileName : abc.png
             * FilePath : https://cchk3.kingwi.org/Files/20170704/b0d42941-cee8-4a0c-afb9-0c1917f051d4.png
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

    public static class SandSamplingRecordListBean {
        /**
         * ItemID : 1052
         * Creator : yflf
         * SandSamplingDate : 2017-02-17 10:47:00
         * SubcontractorInterimApproachPlanID : 415
         * ConstructionBoatAccount : jx6
         * ShipName : 吉星6
         * Batch :
         * IsExit : 0
         * SandSamplingNumRecordList : []
         */

        private int ItemID;
        private String Creator;
        private String SandSamplingDate;
        private int SubcontractorInterimApproachPlanID;
        private String ConstructionBoatAccount;
        private String ShipName;
        private String Batch;
        private String IsExit;
        private List<?> SandSamplingNumRecordList;

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

        public List<?> getSandSamplingNumRecordList() {
            return SandSamplingNumRecordList;
        }

        public void setSandSamplingNumRecordList(List<?> SandSamplingNumRecordList) {
            this.SandSamplingNumRecordList = SandSamplingNumRecordList;
        }
    }

    public static class OverSandRecordListBean {
        /**
         * ItemID : 1002
         * SubcontractorInterimApproachPlanID : 1002
         * SandHandlingShipID : jx1
         * SandHandlingShipName :
         * ConstructionShipID : jx1
         * ConstructionShipName : 吉星1
         * IsFinish : 1
         * Creator : yflf
         * StartTime : 2017-06-08 00:00:00
         * EndTime : 2017-06-10 00:00:00
         * BeforeOverSandDraft1 : 12.5
         * BeforeOverSandDraft2 : 15.5
         * BeforeOverSandDraft3 : 23
         * BeforeOverSandDraft4 : 20
         * AfterOverSandDraft1 : 11
         * AfterOverSandDraft2 : 22
         * AfterOverSandDraft3 : 33
         * AfterOverSandDraft4 : 44
         * ActualAmountOfSand : 120
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

    public static class ExitApplicationRecordListBean {
        /**
         * ExitTime : 2017-07-06 17:00:00
         * RemnantAmount : 120
         * ExitApplicationRecordAttachmentList : [{"ItemID":1,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170713/98202811-0e55-41d3-b4ab-82434a4e3608.png"},{"ItemID":2,"FileName":"abc.png","FilePath":"https://cchk3.kingwi.org/Files/20170713/eaf25704-d4c9-4f8f-a936-9d98f90cd05b.png"}]
         */

        private String ExitTime;
        private String RemnantAmount;
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

        public List<ExitApplicationRecordAttachmentListBean> getExitApplicationRecordAttachmentList() {
            return ExitApplicationRecordAttachmentList;
        }

        public void setExitApplicationRecordAttachmentList(List<ExitApplicationRecordAttachmentListBean> ExitApplicationRecordAttachmentList) {
            this.ExitApplicationRecordAttachmentList = ExitApplicationRecordAttachmentList;
        }

        public static class ExitApplicationRecordAttachmentListBean {
            /**
             * ItemID : 1
             * FileName : abc.png
             * FilePath : https://cchk3.kingwi.org/Files/20170713/98202811-0e55-41d3-b4ab-82434a4e3608.png
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
}
