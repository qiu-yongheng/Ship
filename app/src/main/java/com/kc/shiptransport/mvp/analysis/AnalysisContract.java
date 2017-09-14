package com.kc.shiptransport.mvp.analysis;

import com.kc.shiptransport.db.acceptanceevaluation.AcceptanceEvaluationList;
import com.kc.shiptransport.db.acceptancerank.Rank;
import com.kc.shiptransport.db.analysis.ProgressTrack;
import com.kc.shiptransport.db.exitfeedback.ExitFeedBack;
import com.kc.shiptransport.db.logmanager.LogManagerList;
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
        void showEvaluation(List<AcceptanceEvaluationList> list);
        void showRank(List<Rank> list);
        void showExitFeedBack(List<ExitFeedBack> list);
        void showLogManager(List<LogManagerList> list);
        void showDeleteLogResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void search(String startTime, String endTime, String subID, String shipID);
        void getEvaluation(int pageSize, int pageCount, String startTime, String endTime, String subShipAccount, boolean showLoading);
        void getRank(String startTime, String endTime);
        void getExitFeedBack(int pageSize, int pageCount, String startTime, String endTime, String shipAccount, boolean showLoading);
        void getLogManager(int pageSize, int pageCount, String startTime, String endTime, String shipAccount);
        void deleteStopLog(int itemID);
        void deleteThreadLog(int itemID);
    }
}
