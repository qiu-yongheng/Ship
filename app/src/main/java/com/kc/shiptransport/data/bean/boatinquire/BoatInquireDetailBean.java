package com.kc.shiptransport.data.bean.boatinquire;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/30  8:54
 * @desc ${TODD}
 */

public class BoatInquireDetailBean {

    /**
     * ItemID : 5
     * ShipAccount : csgscb
     * ShipName : 测试供砂船舶
     * CheckDate : 2017/11/28 0:00:00
     * CreatorName : 测试用户
     * Creator : csyh
     * Remark : abc
     * List : [{"ItemID":"13","SelfCheckItemID":"1","SelfCheckItemName":"船员已按要求船舶安全帽，救生衣及安全鞋","CheckedResult":"1"},{"ItemID":"14","SelfCheckItemID":"2","SelfCheckItemName":"皮带桥梁已四面围封，砂料不会从桥梁中漏出","CheckedResult":"1"}]
     */

    private int ItemID;
    private String ShipAccount;
    private String ShipName;
    private String CheckDate;
    private String CreatorName;
    private String Creator;
    private String Remark;
    private java.util.List<ListBean> List;

    public BoatInquireDetailBean(int itemID, String shipAccount, String shipName, String checkDate, String creatorName, String creator, String remark, java.util.List<ListBean> list) {
        ItemID = itemID;
        ShipAccount = shipAccount;
        ShipName = shipName;
        CheckDate = checkDate;
        CreatorName = creatorName;
        Creator = creator;
        Remark = remark;
        List = list;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
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

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String CheckDate) {
        this.CheckDate = CheckDate;
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public void setCreatorName(String CreatorName) {
        this.CreatorName = CreatorName;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {
        /**
         * ItemID : 13
         * SelfCheckItemID : 1
         * SelfCheckItemName : 船员已按要求船舶安全帽，救生衣及安全鞋
         * CheckedResult : 1
         */

        private int ItemID;
        private int SelfCheckItemID;
        private String SelfCheckItemName;
        private int CheckedResult;

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

        public int getSelfCheckItemID() {
            return SelfCheckItemID;
        }

        public void setSelfCheckItemID(int SelfCheckItemID) {
            this.SelfCheckItemID = SelfCheckItemID;
        }

        public String getSelfCheckItemName() {
            return SelfCheckItemName;
        }

        public void setSelfCheckItemName(String SelfCheckItemName) {
            this.SelfCheckItemName = SelfCheckItemName;
        }

        public int getCheckedResult() {
            return CheckedResult;
        }

        public void setCheckedResult(int CheckedResult) {
            this.CheckedResult = CheckedResult;
        }
    }
}
