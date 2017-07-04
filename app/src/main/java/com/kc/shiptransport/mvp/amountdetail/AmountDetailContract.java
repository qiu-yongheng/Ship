package com.kc.shiptransport.mvp.amountdetail;

import com.kc.shiptransport.data.bean.CommitImgListBean;
import com.kc.shiptransport.db.amount.AmountDetail;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:57
 * @desc ${TODD}
 */

public interface AmountDetailContract {
    interface View extends BaseView<Presenter> {
        /* 显示供沙船的详细信息 */
        void showShipDetail(AmountDetail value);

        /* 显示验沙时间 */
        void showSupplyTime(String currentDate);

        /* 计算 */
        void showTotalVolume(String volume);

        /* 是否显示加载框 */
        void showLoading(boolean active);

        /* 显示错误 */
        void showError(String msg);

        void showCommitResult(boolean active);

        void showProgress(int max);

        void updateProgress();

        void hideProgress();

        void showDeleteResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void getShipDetail(int itemID);

        void getSupplyTime();

        void getTotalVolume(String ship, String deck, String capacity);

        void commit(int itemID,
                    String TheAmountOfTime,
                    String subcontractorAccount,
                    int SubcontractorInterimApproachPlanID,
                    String ShipAccount,
                    String Capacity,
                    String DeckGauge,
                    String Deduction,
                    String Creator);

        void start(int itemID);

        void getCommitImgList(ImageMultipleResultEvent imageMultipleResultEvent, int itemID, String creator);

        void commitImg(CommitImgListBean amountImgListBean);

        void deleteImgForItemID(int ItemID);
    }
}
