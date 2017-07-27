package com.kc.shiptransport.mvp.analysis;

import com.kc.shiptransport.db.analysis.ProgressTrack;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/7/26  13:57
 * @desc ${TODD}
 */

public interface AnalysisContract {
    interface View extends BaseView<Presenter> {
        void showSearchResult(List<ProgressTrack> list);
    }

    interface Presenter extends BasePresenter {
        void search(String startTime, String endTime, String subID, String shipID);
    }
}
