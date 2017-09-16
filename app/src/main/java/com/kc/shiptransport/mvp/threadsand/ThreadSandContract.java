package com.kc.shiptransport.mvp.threadsand;

import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.PartitionSBBean;
import com.kc.shiptransport.data.bean.threadsandlog.ThreadSandLogBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/7  8:47
 * @desc ${TODD}
 */

public interface ThreadSandContract {
    interface View extends BaseView<Presenter> {
        void showStartDate(LogCurrentDateBean bean);
        void showPartition(PartitionSBBean bean);
        void showCommitResult(boolean isSuccess);
        void showDetailData(List<ThreadSandLogBean> threadSandLogBeen);
    }

    interface Presenter extends BasePresenter {
        void getDates(String CurrentDate, String CurrentBoatAccount);
        void getPartition(String userID);
        void commit(String json);
        void getDetailData(int itemID);
    }
}
