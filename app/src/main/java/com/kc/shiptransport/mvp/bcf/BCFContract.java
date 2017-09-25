package com.kc.shiptransport.mvp.bcf;

import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author 邱永恒
 * @time 2017/9/25  9:31
 * @desc ${TODD}
 */

public interface BCFContract {
    interface View extends BaseView<Presenter> {
        void showSandShipResult(boolean isSuccess);
        void showCommitResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void getSandShip();
        void commit(int ItemID, String SandHandlingShipID, String SubcontractorAccount, float TotalAmount, String Remark, String Creator, String Date);
    }
}
