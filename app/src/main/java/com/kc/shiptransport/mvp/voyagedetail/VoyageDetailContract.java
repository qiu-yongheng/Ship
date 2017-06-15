package com.kc.shiptransport.mvp.voyagedetail;

import com.kc.shiptransport.data.bean.VoyageInfoBean;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author qiuyongheng
 * @time 2017/6/13  17:14
 * @desc ${TODD}
 */

public interface VoyageDetailContract {
    interface View extends BaseView<Presenter> {
        void showItemID(String itemID);
        void showSubcontractor(Subcontractor sub);
        void showError(String msg);
        void showCommitResult(boolean isSuccess);
        void showLoading(boolean isShow);
    }

    interface Presenter extends BasePresenter {
        void getItemIDForPosition(int position);
        void getSubcontractor();
        void doCommit(VoyageInfoBean bean);
    }
}
