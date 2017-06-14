package com.kc.shiptransport.mvp.amountdetail;

import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:57
 * @desc ${TODD}
 */

public interface AmountDetailContract {
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
        void showError(String msg);
        void showCommitResult(boolean active);
    }

    interface Presenter extends BasePresenter {
        void getShipDetail(int itemID);
        void getSupplyTime();
        void getTotalVolume(String ship, String deck, String capacity);
        void commit(int itemID, String TheAmountOfTime, String Capacity, String DeckGauge, String Deduction);
        void start(int itemID);
    }
}
