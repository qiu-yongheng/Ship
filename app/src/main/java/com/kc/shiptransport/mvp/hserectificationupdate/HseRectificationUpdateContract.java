package com.kc.shiptransport.mvp.hserectificationupdate;

import com.kc.shiptransport.data.bean.hse.HseDefectDetailBean;
import com.kc.shiptransport.data.bean.hse.rectification.HseRectificationBean;
import com.kc.shiptransport.data.bean.hse.rectification.HseRectificationCommitBean;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/28  14:17
 * @desc ${TODD}
 */

public interface HseRectificationUpdateContract {
    interface View extends BaseView<Presenter> {
        void showCommitResult(boolean isSuccess);

        void showProgressDialog(String title, int max);
        void updateProgressDialog(String title, int len);
        void hideProgressDialog();
        void showDetailData(HseRectificationBean bean);

        void showDefectDetailData(HseDefectDetailBean bean);
    }

    interface Presenter extends BasePresenter {
        void commit(HseRectificationCommitBean bean, List<ImgList> addList, List<ImgList> deleteList);
        void getDetailData(int itemID);
        void getDefectDetailData(int itemID);
    }
}
