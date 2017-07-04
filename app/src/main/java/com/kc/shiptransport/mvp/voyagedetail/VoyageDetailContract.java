package com.kc.shiptransport.mvp.voyagedetail;

import com.kc.shiptransport.data.bean.VoyageInfoBean;
import com.kc.shiptransport.db.StoneSource;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.db.voyage.PerfectBoatRecordInfo;
import com.kc.shiptransport.db.voyage.WashStoneSource;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/13  17:14
 * @desc ${TODD}
 */

public interface VoyageDetailContract {
    interface View extends BaseView<Presenter> {
        void showItemID(WeekTask itemID);
        void showSubcontractor(Subcontractor sub);
        void showError(String msg);
        void showCommitResult(boolean isSuccess);
        void showLoading(boolean isShow);
        void showShipName(String name);
        void showDatas(PerfectBoatRecordInfo perfectBoatRecord, boolean isPerfect);
        void showStoneSource(List<StoneSource> list);
        void showWashStoneSource(List<WashStoneSource> list);
    }

    interface Presenter extends BasePresenter {
        void getItemIDForPosition(int position);
        void getSubcontractor();
        void doCommit(VoyageInfoBean bean);
        void getShipName();
        void start(int position);
        void getVoyageDetailForItemID(int position);
        void getStoneSource();
        void getWashStoreSource();
    }
}
