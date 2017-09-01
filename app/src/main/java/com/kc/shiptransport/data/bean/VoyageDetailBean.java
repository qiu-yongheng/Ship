package com.kc.shiptransport.data.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/4  20:23
 * @desc 信息完善详情
 */

public class VoyageDetailBean extends DataSupport{
    /**
     * ItemID : 3561
     * ShipAccount : bsj618
     * ShipName : 博石机618
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试供应商
     * SystemDate :
     * Creator :
     * StoreName :
     * WashStoreAddress :
     * PerfectBoatItemCount :
     * IsPerfect :
     * Columns : [{"Label":"船长姓名","ColumnName":"Captain","value":"","ControlType":"text","arr":{}},{"Label":"船长电话","ColumnName":"CaptainPhone","value":"","ControlType":"text","arr":{}},{"Label":"装舱时间","ColumnName":"Captain","value":"","ControlType":"datetime","arr":{}},{"Label":"AIS MMSI No.","ColumnName":"AIS_MMSI_Num","value":"","ControlType":"text","arr":{}},{"Label":"隔舱数量","ColumnName":"CompartmentQuantity","value":"","ControlType":"text","arr":{}},{"Label":"货物名称","ColumnName":"GoodsName","value":"","ControlType":"text","arr":{}},{"Label":"载重吨","ColumnName":"DeadweightTons","value":"","ControlType":"text","arr":{}},{"Label":"石场名称","ColumnName":"StoreName","value":"","ControlType":"select","arr":[{"ItemID":"1","Name":"泰盛石场"},{"ItemID":"2","Name":"大泽永鑫"},{"ItemID":"3","Name":"恒利石业"},{"ItemID":"4","Name":"白鹤汛石场"},{"ItemID":"5","Name":"文丰石场"},{"ItemID":"6","Name":"烟墩山石场"},{"ItemID":"7","Name":"合力石场"},{"ItemID":"8","Name":"兴迅花岗岩石场"},{"ItemID":"9","Name":"怡湾砂石场"},{"ItemID":"10","Name":"富隆石业"},{"ItemID":"11","Name":"玮泰石业"},{"ItemID":"12","Name":"BCF堆场"}]},{"Label":"洗石场所在地","ColumnName":"WashStoreAddress","value":"","ControlType":"select","arr":[{"ItemID":"1","Name":"测试地址1"},{"ItemID":"2","Name":"测试地址2"},{"ItemID":"3","Name":"测试地址3"}]},{"Label":"离开石场时间","ColumnName":"LeaveStoreTime","value":"","ControlType":"datetime","arr":{}},{"Label":"清关结束时间","ColumnName":"ClearanceEndTime","value":"","ControlType":"datetime","arr":{}},{"Label":"到达锚地时间","ColumnName":"ArrivaOfAnchorageTime","value":"","ControlType":"datetime","arr":{}},{"Label":"材料分类","ColumnName":"MaterialClassification","value":"","ControlType":"text","arr":[{"ItemID":"（细砂（0~3mm）","Name":"（细砂（0~3mm）"},{"ItemID":"细砂（0~5mm）","Name":"细砂（0~5mm）"},{"ItemID":"粗砂","Name":"粗砂"}]}]
     */

    private String ItemID;
    private int SubcontractorInterimApproachPlanID;
    private String ShipAccount;
    private String ShipName;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String SystemDate;
    private String Creator;
    private String StoreName;
    private String WashStoreAddress;
    private String PerfectBoatItemCount;
    private String IsPerfect; // 1代表已完善，0代表未完善
    private String IsSumbitted; // 1代表已提交，0代表保存
    private List<ColumnsBean> Columns;


    public int getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(int subcontractorInterimApproachPlanID) {
        SubcontractorInterimApproachPlanID = subcontractorInterimApproachPlanID;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
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

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    public String getWashStoreAddress() {
        return WashStoreAddress;
    }

    public void setWashStoreAddress(String WashStoreAddress) {
        this.WashStoreAddress = WashStoreAddress;
    }

    public String getPerfectBoatItemCount() {
        return PerfectBoatItemCount;
    }

    public void setPerfectBoatItemCount(String PerfectBoatItemCount) {
        this.PerfectBoatItemCount = PerfectBoatItemCount;
    }

    public String getIsPerfect() {
        return IsPerfect;
    }

    public void setIsPerfect(String IsPerfect) {
        this.IsPerfect = IsPerfect;
    }

    public String getIsSumbitted() {
        return IsSumbitted;
    }

    public void setIsSumbitted(String isSumbitted) {
        IsSumbitted = isSumbitted;
    }

    public List<ColumnsBean> getColumns() {
        return Columns;
    }

    public void setColumns(List<ColumnsBean> Columns) {
        this.Columns = Columns;
    }

    public static class ColumnsBean {
        /**
         * Label : 船长姓名
         * ColumnName : Captain
         * value :
         * ControlType : text
         * arr : {}
         */

        private String Label;
        private String ColumnName;
        private String value;
        private String ControlType;
        private List<ArrBean> arr;
        private String data = "";

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getLabel() {
            return Label;
        }

        public void setLabel(String Label) {
            this.Label = Label;
        }

        public String getColumnName() {
            return ColumnName;
        }

        public void setColumnName(String ColumnName) {
            this.ColumnName = ColumnName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getControlType() {
            return ControlType;
        }

        public void setControlType(String ControlType) {
            this.ControlType = ControlType;
        }

        public List<ArrBean> getArr() {
            return arr;
        }

        public void setArr(List<ArrBean> arr) {
            this.arr = arr;
        }

        public static class ArrBean {
            /**
             * ItemID : 1
             * Name : 泰盛石场
             */

            private String ItemID;
            private String Name;

            public String getItemID() {
                return ItemID;
            }

            public void setItemID(String ItemID) {
                this.ItemID = ItemID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }
    }
}
