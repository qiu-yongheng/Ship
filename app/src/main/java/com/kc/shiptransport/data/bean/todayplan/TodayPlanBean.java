package com.kc.shiptransport.data.bean.todayplan;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/2  16:04
 * @desc ${TODD}
 */

public class TodayPlanBean {

    /**
     * Date : 2017-10-30
     * AllShipCount : 18
     * ShipTypeList : [{"ShipType":"A类","Count":"14"},{"ShipType":"B类","Count":"3"},{"ShipType":"C类","Count":"1"}]
     * MaterialClassificationList : [{"MaterialClassification":"粗料（非严格筛分）","Count":"10"},{"MaterialClassification":"粗料（严格筛分）","Count":"3"},{"MaterialClassification":"定制砂","Count":"1"},{"MaterialClassification":"堆载砂","Count":"4"}]
     * GoodsNameList : [{"GoodsName":"Granular Fill Material","Count":"4"},{"GoodsName":"碎石粉","Count":"14"}]
     * SubcontractorList : [{"SubcontractorName":"超荣","TotalAmount":"8494","ShipList":[{"ShipName":"粤广州货2366","ShipType":"A类","MaterialClassification":"粗料（非严格筛分）","MEZ":"24米以下","TotalAmount":"3271","GoodsName":"碎石粉","StatusValue":"已退场","HAMUMaxHeight":"21.5"},{"ShipName":"粤广州货3966","ShipType":"A类","MaterialClassification":"粗料（非严格筛分）","MEZ":"24米以下","TotalAmount":"3171","GoodsName":"碎石粉","StatusValue":"已退场","HAMUMaxHeight":"21.5"},{"ShipName":"粤惠州货3393","ShipType":"A类","MaterialClassification":"粗料（非严格筛分）","MEZ":"未办理","TotalAmount":"2052","GoodsName":"碎石粉","StatusValue":"已退场","HAMUMaxHeight":"18.1"}]},{"SubcontractorName":"港峰工程","TotalAmount":"2325","ShipList":[{"ShipName":"粤东莞货0788","ShipType":"A类","MaterialClassification":"粗料（非严格筛分）","MEZ":"未办理","TotalAmount":"2325","GoodsName":"碎石粉","StatusValue":"已退场","HAMUMaxHeight":"18.5"}]},{"SubcontractorName":"广东益通","TotalAmount":"11173","ShipList":[{"ShipName":"粤广州货0928","ShipType":"A类","MaterialClassification":"堆载砂","MEZ":"24米以下","TotalAmount":"3332","GoodsName":"Granular Fill Material","StatusValue":"已退场","HAMUMaxHeight":"21.9"},{"ShipName":"粤广州货1022","ShipType":"A类","MaterialClassification":"堆载砂","MEZ":"未办理","TotalAmount":"1890","GoodsName":"Granular Fill Material","StatusValue":"已退场","HAMUMaxHeight":"18.3"},{"ShipName":"粤广州货1662","ShipType":"A类","MaterialClassification":"堆载砂","MEZ":"未办理","TotalAmount":"2854","GoodsName":"Granular Fill Material","StatusValue":"已退场","HAMUMaxHeight":"21.1"},{"ShipName":"粤东莞货0811","ShipType":"A类","MaterialClassification":"堆载砂","MEZ":"16.6米以下","TotalAmount":"3097","GoodsName":"Granular Fill Material","StatusValue":"已退场","HAMUMaxHeight":"19.4"}]},{"SubcontractorName":"珠海通茂","TotalAmount":"1811","ShipList":[{"ShipName":"粤惠州货883","ShipType":"B类","MaterialClassification":"粗料（非严格筛分）","MEZ":"未办理","TotalAmount":"1811","GoodsName":"碎石粉","StatusValue":"已退场","HAMUMaxHeight":"19.4"}]}]
     */

    private String Date;
    private String AllShipCount;
    private List<ShipTypeListBean> ShipTypeList;
    private List<MaterialClassificationListBean> MaterialClassificationList;
    private List<GoodsNameListBean> GoodsNameList;
    private List<SubcontractorListBean> SubcontractorList;

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getAllShipCount() {
        return AllShipCount;
    }

    public void setAllShipCount(String AllShipCount) {
        this.AllShipCount = AllShipCount;
    }

    public List<ShipTypeListBean> getShipTypeList() {
        return ShipTypeList;
    }

    public void setShipTypeList(List<ShipTypeListBean> ShipTypeList) {
        this.ShipTypeList = ShipTypeList;
    }

    public List<MaterialClassificationListBean> getMaterialClassificationList() {
        return MaterialClassificationList;
    }

    public void setMaterialClassificationList(List<MaterialClassificationListBean> MaterialClassificationList) {
        this.MaterialClassificationList = MaterialClassificationList;
    }

    public List<GoodsNameListBean> getGoodsNameList() {
        return GoodsNameList;
    }

    public void setGoodsNameList(List<GoodsNameListBean> GoodsNameList) {
        this.GoodsNameList = GoodsNameList;
    }

    public List<SubcontractorListBean> getSubcontractorList() {
        return SubcontractorList;
    }

    public void setSubcontractorList(List<SubcontractorListBean> SubcontractorList) {
        this.SubcontractorList = SubcontractorList;
    }

    public static class ShipTypeListBean {
        /**
         * ShipType : A类
         * Count : 14
         */

        private String ShipType;
        private String Count;

        public String getShipType() {
            return ShipType;
        }

        public void setShipType(String ShipType) {
            this.ShipType = ShipType;
        }

        public String getCount() {
            return Count;
        }

        public void setCount(String Count) {
            this.Count = Count;
        }
    }

    public static class MaterialClassificationListBean {
        /**
         * MaterialClassification : 粗料（非严格筛分）
         * Count : 10
         */

        private String MaterialClassification;
        private String Count;

        public String getMaterialClassification() {
            return MaterialClassification;
        }

        public void setMaterialClassification(String MaterialClassification) {
            this.MaterialClassification = MaterialClassification;
        }

        public String getCount() {
            return Count;
        }

        public void setCount(String Count) {
            this.Count = Count;
        }
    }

    public static class GoodsNameListBean {
        /**
         * GoodsName : Granular Fill Material
         * Count : 4
         */

        private String GoodsName;
        private String Count;

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public String getCount() {
            return Count;
        }

        public void setCount(String Count) {
            this.Count = Count;
        }
    }

    public static class SubcontractorListBean {
        /**
         * SubcontractorName : 超荣
         * TotalAmount : 8494
         * ShipList : [{"ShipName":"粤广州货2366","ShipType":"A类","MaterialClassification":"粗料（非严格筛分）","MEZ":"24米以下","TotalAmount":"3271","GoodsName":"碎石粉","StatusValue":"已退场","HAMUMaxHeight":"21.5"},{"ShipName":"粤广州货3966","ShipType":"A类","MaterialClassification":"粗料（非严格筛分）","MEZ":"24米以下","TotalAmount":"3171","GoodsName":"碎石粉","StatusValue":"已退场","HAMUMaxHeight":"21.5"},{"ShipName":"粤惠州货3393","ShipType":"A类","MaterialClassification":"粗料（非严格筛分）","MEZ":"未办理","TotalAmount":"2052","GoodsName":"碎石粉","StatusValue":"已退场","HAMUMaxHeight":"18.1"}]
         */

        private String SubcontractorName;
        private String TotalAmount;
        private List<ShipListBean> ShipList;

        public String getSubcontractorName() {
            return SubcontractorName;
        }

        public void setSubcontractorName(String SubcontractorName) {
            this.SubcontractorName = SubcontractorName;
        }

        public String getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(String TotalAmount) {
            this.TotalAmount = TotalAmount;
        }

        public List<ShipListBean> getShipList() {
            return ShipList;
        }

        public void setShipList(List<ShipListBean> ShipList) {
            this.ShipList = ShipList;
        }

        public static class ShipListBean {
            /**
             * ShipName : 粤广州货2366
             * ShipType : A类
             * MaterialClassification : 粗料（非严格筛分）
             * MEZ : 24米以下
             * TotalAmount : 3271
             * GoodsName : 碎石粉
             * StatusValue : 已退场
             * HAMUMaxHeight : 21.5
             */

            private String ShipName;
            private String ShipType;
            private String MaterialClassification;
            private String MEZ;
            private String TotalAmount;
            private String GoodsName;
            private String StatusValue;
            private String HAMUMaxHeight;

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

            public String getMaterialClassification() {
                return MaterialClassification;
            }

            public void setMaterialClassification(String MaterialClassification) {
                this.MaterialClassification = MaterialClassification;
            }

            public String getMEZ() {
                return MEZ;
            }

            public void setMEZ(String MEZ) {
                this.MEZ = MEZ;
            }

            public String getTotalAmount() {
                return TotalAmount;
            }

            public void setTotalAmount(String TotalAmount) {
                this.TotalAmount = TotalAmount;
            }

            public String getGoodsName() {
                return GoodsName;
            }

            public void setGoodsName(String GoodsName) {
                this.GoodsName = GoodsName;
            }

            public String getStatusValue() {
                return StatusValue;
            }

            public void setStatusValue(String StatusValue) {
                this.StatusValue = StatusValue;
            }

            public String getHAMUMaxHeight() {
                return HAMUMaxHeight;
            }

            public void setHAMUMaxHeight(String HAMUMaxHeight) {
                this.HAMUMaxHeight = HAMUMaxHeight;
            }
        }
    }
}
