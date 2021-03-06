package com.kc.shiptransport.mvp.upcominglist;

import com.kc.shiptransport.db.backlog.BackLog;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author 邱永恒
 * @time 2017/9/8  18:15
 * @desc ${TODD}
 */

public interface UpcomingListContract {
    interface View extends BaseView<Presenter> {
        void showUpcomingList(BackLog list);
    }

    interface Presenter extends BasePresenter {
        void getUpcomingList(String ID);

        void getPending(int PageSize, int PageCount, String pendingID);
    }
}
