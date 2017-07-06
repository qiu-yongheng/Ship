package com.kc.shiptransport.mvp.voyagedetail;

import com.kc.shiptransport.data.bean.VoyageDetailBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author qiuyongheng
 * @time 2017/6/13  17:14
 * @desc ${TODD}
 */

public interface VoyageDetailContract {
    interface View extends BaseView<Presenter> {
        void showVoyageDates(VoyageDetailBean bean);
        void showCommitResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void getVoyageDates(int position, int type);
        void commit(VoyageDetailBean bean);
    }
}
