package com.kc.shiptransport.mvp.acceptancedetail;

import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author qiuyongheng
 * @time 2017/6/1  11:17
 * @desc ${TODD}
 */

public interface AcceptanceDetailContract {
    interface View extends BaseView<Presenter> {
        /* 显示供沙船的详细信息 */
        void showShipDetail(Acceptance value);
        /* 显示验收时间 */
        void showAcceptanceTime(String currentDate);
        /* 是否显示加载框 */
        void showLoading(boolean active);
        /* 显示错误 */
        void showError();
        void showCommitError();
        void showCommitResult(boolean active);
        void showCancle();
    }

    interface Presenter extends BasePresenter {
        void getShipDetail(int itemID);
        void getAcceptanceTime();
        void commit(int SubcontractorInterimApproachPlanID, String time, int itemID, int rbcomplete, int rbtimely, String shipnum);
        void cancle();
        void start(int itemID);
        void doEvaluation();
    }
}
