package com.kc.shiptransport.mvp.boatinquire;

import com.kc.shiptransport.data.bean.boatinquire.BoatInquireBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/29  10:08
 * @desc ${TODD}
 */

public interface BoatInquireContract {
    interface View extends BaseView<Presenter> {
        void showDatas(List<BoatInquireBean> list, boolean isFirst);
        void showSyncResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void getDatas(int pageSize, int pageCount, BoatInquireBean bean,String startDate, String endDate, boolean isShow);
    }
}
