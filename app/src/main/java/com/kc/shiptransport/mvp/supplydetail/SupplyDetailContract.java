package com.kc.shiptransport.mvp.supplydetail;

import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author qiuyongheng
 * @time 2017/6/1  11:17
 * @desc ${TODD}
 */

public interface SupplyDetailContract {
    interface View extends BaseView<Presenter> {
        /* 显示供沙船的详细信息 */
        void showShipDetail(Acceptance value);
        /* 显示验沙时间 */
        void showSupplyTime(String currentDate);
        /* 计算 */
        void showTotalVolume(String volume);
        /* 是否显示加载框 */
        void showLoading(boolean active);
        /* 显示错误 */
        void showError();
        void showCommitError();
        void showCommitResult(boolean active);
    }

    interface Presenter extends BasePresenter {
        void getShipDetail(int itemID);
        void getSupplyTime();
        void getTotalVolume(String ship, String deck);
        void commit(int itemID, String ReceptionSandTime, String Batch);
        void start(int itemID);
    }
}
