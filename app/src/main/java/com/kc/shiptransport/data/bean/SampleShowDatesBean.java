package com.kc.shiptransport.data.bean;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/29  11:00
 * @desc 验砂取样信息明细
 */

public class SampleShowDatesBean {

    /**
     * ItemID : 0
     * Creator :
     * SandSamplingDate :
     * SubcontractorInterimApproachPlanID : 0
     * ConstructionBoatAccount :
     * ShipName :
     * Batch :
     * NQAA :
     * SandSamplingNumRecordList : [{"ItemID":0,"SandSamplingID":0,"SamplingNum":0,"ConstructionBoatAccount":"","ConstructionBoatAccountName":"","SandSamplingAttachmentRecordList":[{"ItemID":0,"SandSamplingID":0,"SandSamplingNumID":0,"FileName":"","FilePath":""},{"ItemID":0,"SandSamplingID":0,"SandSamplingNumID":0,"FileName":"","FilePath":""}]},{"ItemID":0,"SandSamplingID":0,"SamplingNum":0,"ConstructionBoatAccount":"","ConstructionBoatAccountName":"","SandSamplingAttachmentRecordList":[{"ItemID":0,"SandSamplingID":0,"SandSamplingNumID":0,"FileName":"","FilePath":""},{"ItemID":0,"SandSamplingID":0,"SandSamplingNumID":0,"FileName":"","FilePath":""}]},{"ItemID":0,"SandSamplingID":0,"SamplingNum":0,"ConstructionBoatAccount":"","ConstructionBoatAccountName":"","SandSamplingAttachmentRecordList":[{"ItemID":0,"SandSamplingID":0,"SandSamplingNumID":0,"FileName":"","FilePath":""},{"ItemID":0,"SandSamplingID":0,"SandSamplingNumID":0,"FileName":"","FilePath":""}]}]
     */

    private int ItemID;
    private String Creator;
    private String SandSamplingDate;
    private int SubcontractorInterimApproachPlanID;
    private String ConstructionBoatAccount;
    private String ShipName;
    private String Batch;
    private String NQAA;
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

    public String getNQAA() {
        return NQAA;
    }

    public void setNQAA(String NQAA) {
        this.NQAA = NQAA;
    }

    public List<SandSamplingNumRecordListBean> getSandSamplingNumRecordList() {
        return SandSamplingNumRecordList;
    }

    public void setSandSamplingNumRecordList(List<SandSamplingNumRecordListBean> SandSamplingNumRecordList) {
        this.SandSamplingNumRecordList = SandSamplingNumRecordList;
    }

    public static class SandSamplingNumRecordListBean {
        /**
         * ItemID : 0
         * SandSamplingID : 0
         * SamplingNum : 0
         * ConstructionBoatAccount :
         * ConstructionBoatAccountName :
         * SandSamplingAttachmentRecordList : [{"ItemID":0,"SandSamplingID":0,"SandSamplingNumID":0,"FileName":"","FilePath":""},{"ItemID":0,"SandSamplingID":0,"SandSamplingNumID":0,"FileName":"","FilePath":""}]
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
             * ItemID : 0
             * SandSamplingID : 0
             * SandSamplingNumID : 0
             * FileName :
             * FilePath :
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
