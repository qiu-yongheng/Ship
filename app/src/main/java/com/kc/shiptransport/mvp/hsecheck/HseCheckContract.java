package com.kc.shiptransport.mvp.hsecheck;

import com.kc.shiptransport.data.bean.hse.HseCheckListBean;
import com.kc.shiptransport.data.bean.hse.HseCheckSelectBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/22  10:32
 * @desc ${TODD}
 */

public interface HseCheckContract {
    interface View extends BaseView<Presenter> {
        void showDates(List<HseCheckListBean> hseCheckListBeans, boolean isFirst);
        void showDeleteResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void getDates(int PAGESIZE, int PAGECOUNT, HseCheckSelectBean bean, boolean isShowDialog);
        void delete(int itemID);
    }
}
