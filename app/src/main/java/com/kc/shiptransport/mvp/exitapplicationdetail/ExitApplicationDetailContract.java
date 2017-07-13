package com.kc.shiptransport.mvp.exitapplicationdetail;

import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * @author qiuyongheng
 * @time 2017/7/12  10:00
 * @desc ${TODD}
 */

public interface ExitApplicationDetailContract {
    interface View extends BaseView<Presenter> {
        void showCommitResult(boolean isSuccess);
        void showDates();
    }

    interface Presenter extends BasePresenter {
        void getDates(int SubcontractorInterimApproachPlanID);
        void commit();
        // 根据图片ID删除图片
        void deleteImgForItemID(int itemID);
        // 把图片解析成上传任务
        void getCommitImgList(ImageMultipleResultEvent imageMultipleResultEvent, int itemID, String subcontractorAccount);
    }
}
