package com.kc.shiptransport.db.down;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/6  19:34
 * @desc ${TODD}
 */

public class StopList extends DataSupport{

    /**
     * OptionType : 生产性停歇
     * OptionList : [{"ItemID":"1","Name":"工人休息"},{"ItemID":"2","Name":"正常过砂"}]
     */

    private String OptionType;
    private List<OptionListBean> OptionList;

    public String getOptionType() {
        return OptionType;
    }

    public void setOptionType(String OptionType) {
        this.OptionType = OptionType;
    }

    public List<OptionListBean> getOptionList() {
        return OptionList;
    }

    public void setOptionList(List<OptionListBean> OptionList) {
        this.OptionList = OptionList;
    }

    public static class OptionListBean {
        /**
         * ItemID : 1
         * Name : 工人休息
         */

        private int ItemID;
        private String Name;

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
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
