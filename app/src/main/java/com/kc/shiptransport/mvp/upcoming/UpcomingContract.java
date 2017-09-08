package com.kc.shiptransport.mvp.upcoming;

import com.kc.shiptransport.db.backlog.BackLog;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/9/8  15:57
 * @desc ${TODD}
 */

public interface UpcomingContract {
    interface View extends BaseView<Presenter> {
        void showPending(List<BackLog> list);
    }

    interface Presenter extends BasePresenter {
        void getPending(int PageSize, int PageCount);
    }
}
