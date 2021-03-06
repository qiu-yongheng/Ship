package com.kc.shiptransport.mvp.exitapplicationassessor;

import com.kc.shiptransport.data.bean.CommitImgListBean;
import com.kc.shiptransport.db.exitapplication.ExitDetail;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * @author 邱永恒
 * @time 2017/8/31  15:45
 * @desc ${TODD}
 */

public interface ExitApplicationAssessorContract {
    interface View extends BaseView<Presenter> {
        void showCommitResult(boolean isSuccess);
        void showDates(ExitDetail bean);

        void showProgress(int size);

        void updateProgress();

        void showDeleteResult(Boolean aBoolean);

        void showImgList(ExitDetail exitDetail);
    }

    interface Presenter extends BasePresenter {
        void getDates(int SubcontractorInterimApproachPlanID, int type);
        void commit(int ItemID, String ExitTime, String Creator, String Remark, String RemnantAmount, int SubcontractorInterimApproachPlanID, int isSumbitted, int status);
        // 根据图片ID删除图片
        void deleteImgForItemID(int itemID);
        // 把图片解析成上传任务
        void getCommitImgList(ImageMultipleResultEvent imageMultipleResultEvent, int itemID, String subcontractorAccount);
        void commitImg(CommitImgListBean bean);
    }
}
