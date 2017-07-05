package com.kc.shiptransport.mvp.supplydetail;

import com.kc.shiptransport.data.bean.CommitImgListBean;
import com.kc.shiptransport.db.supply.SupplyDetail;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * @author qiuyongheng
 * @time 2017/6/1  11:17
 * @desc ${TODD}
 */

public interface SupplyDetailContract {
    interface View extends BaseView<Presenter> {
        /* 显示供沙船的详细信息 */
        void showShipDetail(SupplyDetail value);
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


        void showProgress(int max);

        void updateProgress();

        void hideProgress();

        void showDeleteResult(boolean isSuccess);

        void showImgList(SupplyDetail value);
    }

    interface Presenter extends BasePresenter {
        void getShipDetail(int itemID);

        void getShipDetailList(int itemID);

        void getSupplyTime();
        void getTotalVolume(String ship, String deck);
        void commit(int itemID, String ReceptionSandTime);
        void start(int itemID);

        void getCommitImgList(ImageMultipleResultEvent imageMultipleResultEvent, int itemID, String creator);

        void commitImg(CommitImgListBean amountImgListBean);

        void deleteImgForItemID(int ItemID);
    }
}
