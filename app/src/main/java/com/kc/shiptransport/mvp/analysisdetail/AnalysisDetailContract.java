package com.kc.shiptransport.mvp.analysisdetail;

import com.kc.shiptransport.db.analysis.AnalysisDetail;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author 邱永恒
 * @time 2017/7/27  13:42
 * @desc ${TODD}
 */

public interface AnalysisDetailContract {
    interface View extends BaseView<Presenter> {
        void showDetail(AnalysisDetail detail);
    }

    interface Presenter extends BasePresenter {
        void getDetail(int itemID);
    }
}
