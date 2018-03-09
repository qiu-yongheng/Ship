package com.kc.shiptransport.mvp.acceptancedetail;

import com.kc.shiptransport.data.bean.CommitPictureBean;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.data.bean.acceptanceinfo.AcceptanceInfoBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * @author qiuyongheng
 * @time 2017/6/1  11:17
 * @desc ${TODD}
 */

public interface AcceptanceDetailContract {
    interface View extends BaseView<Presenter> {
        /* 显示供沙船的详细信息 */
        void showShipDetail(AcceptanceInfoBean value);
        /* 显示验收时间 */
        void showAcceptanceTime(String currentDate);
        /* 是否显示加载框 */
        void showLoading(boolean active);
        /* 显示错误 */
        void showError();
        void showCommitError();
        void showCommitResult(boolean active);
        void showCancle();
        void showImgList(List<ScannerImgListByTypeBean> scannerImgListByTypeBeen);

        void showProgress(int max);
        void hideProgress();
        void updateProgress();
        void showDeleteResult(boolean isSuccess);
        void showImgList(AcceptanceInfoBean value);
    }

    interface Presenter extends BasePresenter {
        void getShipDetail(int itemID);
        void getAcceptanceTime();
        void commit(int SubcontractorInterimApproachPlanID, String time, int itemID, int rbcomplete, int rbtimely, AcceptanceInfoBean value, int Status, String Remark);
        void cancle();
        void start(int itemID);
        void doEvaluation();
        void deleteImg(int itemID);
        void commitImg(ImageMultipleResultEvent imageMultipleResultEvent, int subID, int typeID, String shipAccount);
        void imgCommit(CommitPictureBean bean);
        void updateImgDetail(int itemID);
    }
}
