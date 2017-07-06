package com.kc.shiptransport.db.down;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/7/6  19:32
 * @desc 1.42停工因素选项数据
 */

public class StopOption extends DataSupport{
    private String OptionType;
    private int ItemID;
    private String Name;

    public String getOptionType() {
        return OptionType;
    }

    public void setOptionType(String optionType) {
        OptionType = optionType;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
