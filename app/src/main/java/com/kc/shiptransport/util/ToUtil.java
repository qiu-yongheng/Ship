package com.kc.shiptransport.util;

import com.kc.shiptransport.data.bean.boatinquire.BoatInquireDetailBean;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireItemBean;
import com.kc.shiptransport.data.bean.boatinquire.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/30  10:46
 * @desc ${TODD}
 */

public class ToUtil {
    public static List<ItemBean> toItemBeanList1(List<BoatInquireItemBean> list) {
        List<ItemBean> itemBeans = new ArrayList<>();
        for (BoatInquireItemBean bean : list) {
            itemBeans.add(new ItemBean(bean.getItemID(), bean.getName(), 0));
        }
        return itemBeans;
    }

    public static List<ItemBean> toItemBeanList2(List<BoatInquireDetailBean.ListBean> list) {
        List<ItemBean> itemBeans = new ArrayList<>();
        for (BoatInquireDetailBean.ListBean bean : list) {
            itemBeans.add(new ItemBean(bean.getSelfCheckItemID(), bean.getSelfCheckItemName(), bean.getCheckedResult()));
        }
        return itemBeans;
    }
}
