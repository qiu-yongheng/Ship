package com.kc.shiptransport.mvp.boatinquireadd;

import com.kc.shiptransport.data.bean.boatinquire.BoatInquireCommitBean;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireDetailBean;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireItemBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/29  14:51
 * @desc ${TODD}
 */

public interface BoatInquireAddContract {
    interface View extends BaseView<Presenter> {
        void showDetailData(BoatInquireDetailBean bean);
        void showInquireItem(List<BoatInquireItemBean> list);
        void showCommitResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void getDetailData(int itemID);
        void getInquireItem();
        void commit(BoatInquireCommitBean bean);
    }
}
