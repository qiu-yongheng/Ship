package com.kc.shiptransport.mvp.downtime;

import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.downlog.DownLogBean;
import com.kc.shiptransport.db.down.StopOption;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/6  17:18
 * @desc ${TODD}
 */

public interface DowntimeContract {
    interface View extends BaseView<Presenter> {
        void showGetStopOptions(List<StopOption> stopOptions);
        void showStopResult(boolean isSuccess);
        void showStartDate(LogCurrentDateBean bean);
        void showDetailData(List<DownLogBean> list);
    }

    interface Presenter extends BasePresenter {
        void getStopOptions();
        void getStartDate(String CurrentDate, String CurrentBoatAccount);
        void stop(int itemID, String userID, String startTime, String endTime, String id, int type, String remark, String pumpShipID, boolean isUpdate);
        void getDetailData(int itemID);
    }
}
