package com.kc.shiptransport.data.bean;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/29  11:00
 * @desc 验砂取样信息明细
 */

public class SampleShowDatesBean {
    /**
     * ItemID : 1052
     * Creator : yflf
     * SandSamplingDate : 2017-02-17 10:47:00
     * SubcontractorInterimApproachPlanID : 415
     * ConstructionBoatAccount : jx6
     * ShipName : 吉星6
     * SandSamplingNumRecordList : [{"ItemID":1002,"SandSamplingID":1052,"SamplingNum":"xx022222","SandSamplingAttachmentRecordList":[{"ItemID":1002,"SandSamplingID":1052,"SandSamplingNumID":1002,"FileName":"aa.jpg","FilePath":"/20170628/qwqewqe1.jpg"},{"ItemID":1003,"SandSamplingID":1052,"SandSamplingNumID":1002,"FileName":"aa.jpg","FilePath":"/20170629/qwqewqe2.jpg"},{"ItemID":1004,"SandSamplingID":1052,"SandSamplingNumID":1002,"FileName":"aa.jpg","FilePath":"/20170628/qwqewqe3.jpg"}]}]
     */

    // 条目编号
    private int ItemID;
    // 创建者ID
    private String Creator;
    // 验砂取样时间
    private String SandSamplingDate;
    // 进场计划ID
    private int SubcontractorInterimApproachPlanID;
    // 施工船舶账号
    private String ConstructionBoatAccount;
    // 船舶名称
    private String ShipName;
    // 验砂取样数组
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

    public List<SandSamplingNumRecordListBean> getSandSamplingNumRecordList() {
        return SandSamplingNumRecordList;
    }

    public void setSandSamplingNumRecordList(List<SandSamplingNumRecordListBean> SandSamplingNumRecordList) {
        this.SandSamplingNumRecordList = SandSamplingNumRecordList;
    }

    public static class SandSamplingNumRecordListBean {
        /**
         * ItemID : 1002
         * SandSamplingID : 1052
         * SamplingNum : xx022222
         * SandSamplingAttachmentRecordList : [{"ItemID":1002,"SandSamplingID":1052,"SandSamplingNumID":1002,"FileName":"aa.jpg","FilePath":"/20170628/qwqewqe1.jpg"},{"ItemID":1003,"SandSamplingID":1052,"SandSamplingNumID":1002,"FileName":"aa.jpg","FilePath":"/20170629/qwqewqe2.jpg"},{"ItemID":1004,"SandSamplingID":1052,"SandSamplingNumID":1002,"FileName":"aa.jpg","FilePath":"/20170628/qwqewqe3.jpg"}]
         */

        // 验砂取样编号
        private int ItemID;
        // 验砂取样条目ID
        private int SandSamplingID;
        // 船舶名称
        private String SamplingNum;
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

        public List<SandSamplingAttachmentRecordListBean> getSandSamplingAttachmentRecordList() {
            return SandSamplingAttachmentRecordList;
        }

        public void setSandSamplingAttachmentRecordList(List<SandSamplingAttachmentRecordListBean> SandSamplingAttachmentRecordList) {
            this.SandSamplingAttachmentRecordList = SandSamplingAttachmentRecordList;
        }

        public static class SandSamplingAttachmentRecordListBean {
            /**
             * ItemID : 1002
             * SandSamplingID : 1052
             * SandSamplingNumID : 1002
             * FileName : aa.jpg
             * FilePath : /20170628/qwqewqe1.jpg
             */

            // 验砂取样图片条目ID
            private int ItemID;
            // 验砂取样条目ID
            private int SandSamplingID;
            // 验砂取样编号条目ID
            private int SandSamplingNumID;
            // 文件名
            private String FileName;
            // 文件路径
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
