package com.kc.shiptransport.mvp.downtimelog;

import com.kc.shiptransport.data.bean.downlog.DownLogBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/10  15:08
 * @desc ${TODD}
 */

public interface DowntimeLogContract {
    interface View extends BaseView<Presenter> {
        void showLog(List<DownLogBean> list);
    }

    interface Presenter extends BasePresenter {
        void scanner(int ItemID, String ShipAccount, String StartTime, String EndTime, String StopTypeID, String Creator);
    }
}
